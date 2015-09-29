package info.smartkit.eip;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//Dynamic,@see: http://stacktrace.be/blog/2013/03/dynamic-process-creation-and-deployment-in-100-lines/ 
//@see:https://github.com/Activiti/activiti-unit-test-template/blob/master/src/test/java/org/activiti/MyUnitTest.java
public class ActivitiUnitTest {
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
//	@Deployment(resources = {"org/activiti/test/my-process.bpmn20.xml"})
	@Deployment(resources = {"processes/ReimbursementRequest.bpmn20.xml"})
	public void test() {
//		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("reimbursementRequest");
		assertNotNull(processInstance);
		
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals("Activiti is awesome!", task.getName());
	}
}
