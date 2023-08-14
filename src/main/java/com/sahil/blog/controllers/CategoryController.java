package com.sahil.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahil.blog.payloads.ApiResponse;
import com.sahil.blog.payloads.CategoryDto;
import com.sahil.blog.payloads.CategoryResponse;
import com.sahil.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

//=========================================================================================================
	// Create

	/**
	 * @apiNote This method is for Creating new Category
	 * @param categoryDto used as a parameter
	 * @return
	 */

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("Intializing request to createCategory Started");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		logger.info("Completed request for createCategory method");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

//=========================================================================================================

	// Update
	/**
	 * @apiNote This method is for Updating The User
	 * @param categoryDto
	 * @param categoryId
	 * @return
	 */

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {
		logger.info("Intializing request to updateCategory Started");
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		logger.info("Completed request for updatecategory method ");
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}
//=========================================================================================================

	// Delete
	/**
	 * @apiNote This Method is for Deleting the single User
	 * @param categoryId
	 * @return
	 */
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		logger.info("Intializing request to deleteCategory Started");
		this.categoryService.deleteCategory(categoryId);
		logger.info("Completed request for deleteCategory method");
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted Successfully", true), HttpStatus.OK);
	}

//=========================================================================================================

	// Get
	/**
	 * @apiNote this method is for get Category
	 * @param categoryId
	 * @return
	 */

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		logger.info("Intializing request to getCategory Started");
		CategoryDto category = this.categoryService.getCategory(categoryId);
		logger.info("Completed request for getCategory method ");
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}

//=========================================================================================================

	// GetAll
	/**
	 * @apiNote this method is for the getting all categories
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/page")
	public ResponseEntity<CategoryResponse> getAllCategory(
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		logger.info("Intializing request to getAllCategory Started");
		CategoryResponse allCategories = this.categoryService.getAllCategories(pageNumber, pageSize);
		logger.info("Completed request for getAllCategory method");
		return ResponseEntity.ok(allCategories);
	}
//=========================================================================================================

}
