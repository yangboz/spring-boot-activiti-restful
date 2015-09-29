package info.smartkit.eip.test.jpa;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.smartkit.eip.models.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * Integration test to run the application.
 *
 * @author Oliver Gierke
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JPATest.class)
@WebAppConfiguration
// Enable JMX so we can test the MBeans (you can't do this in a properties file)
//@TestPropertySource(properties = { "spring.jmx.enabled:true","spring.datasource.jmx-enabled:true" })
//@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class JPATest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceBeanPostProcessor() {
	    return new PersistenceAnnotationBeanPostProcessor();
	}
    
//    EntityManagerFactory emf = null;
	@PersistenceContext
	protected EntityManager em;
	
	private static Logger LOG = LoggerFactory.getLogger(JPATest.class);
    
    @Before
    public void before() {
//        //根据在persistence.xml中配置的persistence-unit name 创建EntityManagerFactory
//        emf = Persistence.createEntityManagerFactory("myJPA");
    	LOG.info("entityManager:"+em.toString());
    }
    
    /**
     * 保存父组织,级联保存子组织
     */
    @Test
    public void testAddParentCat() {
        
        //父组织
        Category chinaOrg = new Category();
        chinaOrg.setName("中国");
        chinaOrg.setIcon("CHINA");
        
        //子组织
        Category gdOrg = new Category();
        gdOrg.setName("广东");
        gdOrg.setIcon("GD");
        
        //子组织
        Category gxOrg = new Category();
        gxOrg.setName("广西");
        gxOrg.setIcon("GX");
        
        Set<Category> children = new HashSet<Category>();
        children.add(gdOrg);
        children.add(gxOrg);
        
        //添加子组织
        chinaOrg.setChildren(children);
        
//        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(chinaOrg);
        em.getTransaction().commit();
        em.close();
    }
    
    /**
     * 保存子组织
     */
    @Test
    public void testAddChildOrg() {
        
        
        Category gzOrg = new Category();
        gzOrg.setName("广州");
        gzOrg.setIcon("GZ");
        
        
//        EntityManager em = emf.createEntityManager();
        //找出广州所属的父组织广东
        Category parent = em.find(Category.class, 3L);
        //设置广州的父组织
        gzOrg.setParent(parent);
                
        em.getTransaction().begin();
        //保存广州
        em.persist(gzOrg);
        em.getTransaction().commit();
        em.close();
    }
    
    /**
     * 根据子组织查询父组织
     */
    @Test
    public void testQueryParentByChild() {
//        EntityManager em = emf.createEntityManager();
        //找出广州
        Category gzOrg = em.find(Category.class, 4L);
        //找出父组织
        Category parent = gzOrg.getParent();
        System.out.println("父组织名称："+parent.getName());
        em.close();
    }
    
    /**
     * 根据父组织查询子组织
     */
    @Test
    public void testQueryChildrenByParent() {
//        EntityManager em = emf.createEntityManager();
        //找出广东
        Category gdOrg = em.find(Category.class, 3L);
        //找出子组织
        Set<Category> children = gdOrg.getChildren();
        Iterator<Category> it = children.iterator();
        while(it.hasNext()) {
            Category  child = it.next();
            System.out.println("子组织名称："+child.getName());
        }
        em.close();
    }
    
        
    /**
     * 关闭EntityManagerFactory
     */
    @After
    public void after() {
//        if(null != emf) {
//            emf.close();
//        }
    }

}
