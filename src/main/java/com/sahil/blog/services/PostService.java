package com.sahil.blog.services;

import java.util.List;

import com.sahil.blog.payloads.PostDto;
import com.sahil.blog.payloads.PostResponse;

public interface PostService {

	// create
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	public PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	public void deletePost(Integer postId);

	// get all post
	public PostResponse getAllPost(Integer postNumber,Integer postSize,String sortBy,String sortDir);

	// get single post
	public PostDto getPostById(Integer postId);

	// get all post by category
	public List<PostDto> getPostByCategory(Integer categoryId);

	// get all post by User
	public List<PostDto> getPostByUser(Integer userId);

	// search posts
	public List<PostDto> searchPost(String keyword);
}
