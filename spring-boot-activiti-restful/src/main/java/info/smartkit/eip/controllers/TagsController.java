package info.smartkit.eip.controllers;

import javax.validation.Valid;

import info.smartkit.eip.dto.JsonObject;
import info.smartkit.eip.models.Tag;
import info.smartkit.eip.models.TagDao;
import info.smartkit.eip.models.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tags")
public class TagsController {
	// ==============
	// PRIVATE FIELDS
	// ==============

	// Autowire an object of type TagDao
	@Autowired
	private TagDao _tagDao;
	//
	private TagRepository tagRepository;

	@Autowired
	public TagsController(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	// ==============
	// PUBLIC METHODS
	// ==============

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "Response a string describing if the reimbursement tag is successfully created or not.")
	public Tag create(@RequestBody @Valid Tag tag) {
		return this.tagRepository.save(tag);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Response a list describing all of tag that is successfully get or not.")
	public JsonObject list() {
		return new JsonObject(this.tagRepository.findAll(new Sort(
				new Sort.Order(Sort.Direction.ASC, "date"))));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Response a string describing if the tag id is successfully get or not.")
	public Tag get(@PathVariable("id") long id) {
		return this.tagRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(httpMethod = "PUT", value = "Response a string describing if the reimbursement tag is successfully updated or not.")
	public Tag update(@PathVariable("id") long id, @RequestBody @Valid Tag tag) {
		return tagRepository.save(tag);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(httpMethod = "DELETE", value = "Response a string describing if the tag is successfully delete or not.")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.tagRepository.delete(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
