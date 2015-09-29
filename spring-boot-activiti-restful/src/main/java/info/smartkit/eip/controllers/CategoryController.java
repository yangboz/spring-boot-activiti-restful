package info.smartkit.eip.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import info.smartkit.eip.models.Category;
import info.smartkit.eip.models.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.smartkit.eip.dto.JsonObject;
import info.smartkit.eip.models.CategoryDao;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/category")
public class CategoryController {
	// ==============
	// PRIVATE FIELDS
	// ==============

	// Autowire an object of type CategoryDao
	@Autowired
	private CategoryDao _categoryDao;
	//
	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@PersistenceContext
	EntityManager em;
	// ==============
	// PUBLIC METHODS
	// ==============
	private void queryParentByChild() {
		
	}
	private void queryChildrenByParent() {
		
	}

//	@RequestMapping(method = RequestMethod.POST,params={"root","parentId"})
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "Response a string describing if the reimbursement category is successfully created or not.")
	public JsonObject create(@RequestBody @Valid Category category,
			@RequestParam(value = "root", defaultValue = "false") Boolean root,
			@RequestParam(value = "parentId", defaultValue = "1") long parentId) {
		if(root)//Root category
		{
		}else{//Children category
	        //
	        Category parent = em.find(Category.class, parentId);
	        category.setParent(parent);
		}
		//@JsonBackReference http://keenformatics.blogspot.sg/2013/08/how-to-solve-json-infinite-recursion.html
//		this.categoryRepository.save(category);
		return new JsonObject(_categoryDao.save(category));
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Response a list describing all of category that is successfully get or not.")
	public JsonObject list() {
		return new JsonObject(this.categoryRepository.findAll(new Sort(
				new Sort.Order(Sort.Direction.ASC, "date"))));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Response a string describing if the category id is successfully get or not.")
	public Category get(@PathVariable("id") long id) {
		return this.categoryRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "Response a string describing if the reimbursement category is successfully updated or not.")
	public Category update(@PathVariable("id") long id,
			@RequestBody @Valid Category category) {
		return categoryRepository.save(category);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(httpMethod = "DELETE", value = "Response a string describing if the category is successfully delete or not.")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.categoryRepository.delete(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
