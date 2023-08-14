package com.sahil.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sahil.blog.entities.Category;
import com.sahil.blog.entities.Post;
import com.sahil.blog.entities.User;
import com.sahil.blog.exceptions.ResourceNotFoundException;
import com.sahil.blog.payloads.PostDto;
import com.sahil.blog.payloads.PostResponse;
import com.sahil.blog.repositories.CategoryRepo;
import com.sahil.blog.repositories.PostRepo;
import com.sahil.blog.repositories.UserRepo;
import com.sahil.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

//===========================================================================================================================	

	// Create Post

	/**
	 * @apiNote This is method is for Creating Post
	 */
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		logger.info("Initializing dao call for createPost Started");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "catgory id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		logger.info("dao call completed ");
		return this.modelMapper.map(newPost, PostDto.class);
	}

//===========================================================================================================================	
	// Update Post

	/**
	 * @apiNote This is method is for Updating Post
	 */
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		logger.info("Initializing dao call for updatePost Started");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post save = this.postRepo.save(post);
		PostDto post1 = this.modelMapper.map(save, PostDto.class);
		logger.info("dao call completed ");
		return post1;
	}
//===========================================================================================================================	

//Delete Post 

	/**
	 * @apiNote This is method is for Delete the post
	 */
	@Override
	public void deletePost(Integer postId) {
		logger.info("Initializing dao call for deletePost Started");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId ", postId));
		logger.info("dao call completed ");
		this.postRepo.delete(post);

	}
//===========================================================================================================================	

//Get All Post

	/**
	 * @apiNote This is method is for Getting All Post
	 */

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		logger.info("Initializing dao call for getAllPost Started");

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		PageRequest p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagepost = this.postRepo.findAll(p);
		List<Post> allposts = pagepost.getContent();
		List<PostDto> collect = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(collect);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElements(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastPage(pagepost.isLast());
		logger.info("dao call completed ");
		return postResponse;
	}
//===========================================================================================================================	

	// Get Post By Id

	/**
	 * @apiNote This is method is for Get Post
	 */
	@Override
	public PostDto getPostById(Integer postId) {
		logger.info("Initializing dao call for getPostById Started");
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId ", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		logger.info("dao call completed ");
		return postDto;
	}

//===========================================================================================================================	

//GetPostByCategory

	/**
	 * @apiNote This is method is for Get Post By Category
	 */
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		logger.info("Initializing dao call for getPostByCategory Started");
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category Id", categoryId));
		List<Post> posts = this.postRepo.findAllByCategory(category);
		List<PostDto> collect = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		logger.info("dao call completed ");
		return collect;
	}
//===========================================================================================================================	

//GetPotByUser

	/**
	 * @apiNote This is method is for Get Post by User
	 */
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		logger.info("Initializing dao call for getPostByUser Started");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		List<Post> user2 = this.postRepo.findAllByUser(user);
		List<PostDto> postDto = user2.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		logger.info("dao call completed ");
		return postDto;
	}
//===========================================================================================================================	

//Search Post
	/**
	 * 
	 */
	@Override
	public List<PostDto> searchPost(String keyword) {
List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDto;
	}

}
