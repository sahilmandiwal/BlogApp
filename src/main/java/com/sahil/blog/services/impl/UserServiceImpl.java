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

import com.sahil.blog.entities.User;
import com.sahil.blog.exceptions.ResourceNotFoundException;
import com.sahil.blog.payloads.UserDto;
import com.sahil.blog.payloads.UserResponse;
import com.sahil.blog.repositories.UserRepo;
import com.sahil.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//===========================================================================================================================	

	// CREATE
	
	/**
	 * @apiNote This is method is for Creating New User
	 * 
	 */

	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("Initializing dao call for createUser Started");
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		UserDto userToDto = this.userToDto(savedUser);
		logger.info(" dao call completed ");
		return userToDto;
	}

//===========================================================================================================================	

	// UPDATE
	
	/**
	 * @apiNote This is method is for Creating New User
	 * @exception if userId not valid 
	 */
	
	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		logger.info("Initializing dao call for updateUser Started");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		logger.info("dao call completed ");
		return userToDto1;
	}

//===========================================================================================================================	

	// GET

	/**
	 * @apiNote This Method is for  get User By userId
	 *@exception If userId is not valid
	 */
	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("Initializing dao call for Get single UserRecord By userId Started");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		UserDto userToDto = this.userToDto(user);
		logger.info("dao call completed");
		return userToDto;
	}

//===========================================================================================================================	

	// GET ALL

	/**
	 * @apiNote This Method is for  get AllUser
	 *
	 */
	
	@Override
	public UserResponse getAllUser(Integer pageNumber, Integer pageSize) {
		logger.info("Initializing dao call for Get All UserRecord Started");
		PageRequest p = PageRequest.of(pageNumber, pageSize);

		Page<User> pageUser = this.userRepo.findAll(p);
		List<User> content = pageUser.getContent();
		List<UserDto> collect = content.stream().map(User -> this.modelMapper.map(User, UserDto.class))
				.collect(Collectors.toList());

		UserResponse userResponse = new UserResponse();

		userResponse.setContent(collect);
		userResponse.setPageNumber(pageUser.getNumber());
		userResponse.setPageSize(pageUser.getSize());
		userResponse.setTotalElements(pageUser.getTotalElements());
		userResponse.setTotalPages(pageUser.getTotalPages());
		userResponse.setLastPage(pageUser.isLast());
		logger.info("dao call completed");
		return userResponse;
	}

//===========================================================================================================================	

	// DELTE

	/**
	 * @apiNote This method is for delete the single UserDto
	 * @exception if userId not valid
	 * @return nothing
	 */

	@Override
	public void deleteUser(Integer userId) {
		logger.info("Initializing dao call for Deleting Single user Started");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		logger.info("dao call Completed");
		this.userRepo.delete(user);

	}

//===========================================================================================================================	

//DTO

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);

//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getPassword());
		return userDto;
	}
//===========================================================================================================================	

}
