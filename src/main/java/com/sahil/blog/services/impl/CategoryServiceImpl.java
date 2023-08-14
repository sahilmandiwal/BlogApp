package com.sahil.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sahil.blog.entities.Category;
import com.sahil.blog.exceptions.ResourceNotFoundException;
import com.sahil.blog.payloads.CategoryDto;
import com.sahil.blog.payloads.CategoryResponse;
import com.sahil.blog.repositories.CategoryRepo;
import com.sahil.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

//==================================================================================================================================================
	// CREATE
	
	/**
	 *@apiNote This is method is for Creating Category
	 */
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("Initializing dao call for createCategory Started");
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category save = this.categoryRepo.save(category);
		logger.info("dao call completed ");

		return this.modelMapper.map(save, CategoryDto.class);
	}

//==================================================================================================================================================

	// UPDATE
/**
 *@apiNote This is method is for Updating Category
 */
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		logger.info("Initializing dao call for updateCategory Started");

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category update = this.categoryRepo.save(cat);
		logger.info("dao call completed ");

		return this.modelMapper.map(update, CategoryDto.class);
	}

// ==================================================================================================================================================

	// DELETE
/**
 *@apiNote This is method is for Delete Category 
 */
	@Override
	public void deleteCategory(Integer categoryId) {
		logger.info("Initializing dao call for deleteCategory Started");
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		logger.info("dao call completed ");
		this.categoryRepo.delete(cat);
	}
// ==================================================================================================================================================

	// GET
	
/**
 *@apiNote This is method is for Get Category 
 */
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		logger.info("Initializing dao call for getCategory Started");
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		logger.info("dao call completed ");
		return categoryDto;
	}

// ==================================================================================================================================================

	// GET ALL
	
	/**
	 *@apiNote This is method is for Getting All Category 
	 */
	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
		logger.info("Initializing dao call for getAllCategory Started");
		PageRequest of = PageRequest.of(pageNumber, pageSize);
		Page<Category> page = this.categoryRepo.findAll(of);
		List<CategoryDto> collect = page.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(collect);
		categoryResponse.setPageNumber(page.getNumber());
		categoryResponse.setPageSize(page.getSize());
		categoryResponse.setTotalElements(page.getTotalElements());
		categoryResponse.setTotalPages(page.getTotalPages());
		categoryResponse.setLastPage(page.isLast());
		logger.info("dao call completed ");
		return categoryResponse;
	}

}
