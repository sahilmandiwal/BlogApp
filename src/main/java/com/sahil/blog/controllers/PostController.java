package com.sahil.blog.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.sahil.blog.config.AppConstants;
import com.sahil.blog.payloads.ApiResponse;
import com.sahil.blog.payloads.PostDto;
import com.sahil.blog.payloads.PostResponse;
import com.sahil.blog.services.FileService;
import com.sahil.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	
	@Value("${project.image}")
	private String path;
	Logger logger = LoggerFactory.getLogger(PostController.class);

// ===========================================================================================================================

	// Create Post

	/**
	 * @apiNote This method is for Creating post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		logger.info("Intializing request to createPost Started ");
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		logger.info("Completed request for createPost method");
		return new ResponseEntity<PostDto>(HttpStatus.CREATED);
	}
// ===========================================================================================================================

	// Get Posts By User

	/**
	 * @apiNote This method is for Get Post By user
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		logger.info("Intializing request to UpdatePost Started");
		List<PostDto> posts = this.postService.getPostByUser(userId);
		logger.info("Completed request for getPostsByUser method");
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
//===========================================================================================================================	

	// Get Posts By Category

	/**
	 * @apiNote This method is for Get post by Category
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		logger.info("Intializing request to getPostsBtCategoryPost Started");
		List<PostDto> category = this.postService.getPostByCategory(categoryId);
		logger.info("Completed request for getPostsByCategory method");
		return new ResponseEntity<List<PostDto>>(category, HttpStatus.OK);
	}

//===========================================================================================================================	

	// Get All Post

	/**
	 * @apiNote This method is for Get All Post by postId
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		logger.info("Intializing request to getAllPost Started");
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		logger.info("Completed request for getAllPost method");
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}

//===========================================================================================================================	

	// Get Post By Id

	/**
	 * @apiNote This method is for Get Post by postId
	 * @param postId
	 * @return
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		logger.info("Intializing request to getPostById Started");
		PostDto postById = this.postService.getPostById(postId);
		logger.info("Completed request for getPostById method");
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

//===========================================================================================================================	
	// Delete Post

	/**
	 * @apiNote This method is for Delete Post by postId
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deleteById(@PathVariable Integer postId) {
		logger.info("Intializing request to deleteById Started");
		this.postService.deletePost(postId);
		logger.info("Completed request for deleteById method");
		return new ApiResponse("Post is successfully deleted!!", true);
	}
//============================================================================================================================

	// update post

	/**
	 * @apiNote This method is for update Post by postId
	 * @param postDto
	 * @param postId
	 * @return
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		logger.info("Intializing request to updatePostById Started");
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("Completed request for updatePostById method");
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

//===============================================================================================================================
	// SEARCHING
	/**
	 * 
	 * @param keywords
	 * @return
	 */
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> searchPost = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);
	}

//=================================================================================================================================

	// POST IMAGE UPLOAD
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId

	) throws Exception   {
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

}