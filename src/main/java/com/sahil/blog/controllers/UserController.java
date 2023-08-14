package com.sahil.blog.controllers;

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
import com.sahil.blog.payloads.UserDto;
import com.sahil.blog.payloads.UserResponse;
import com.sahil.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

//=========================================================================================================
	// CREATE
/**
 * @apiNote this method is for Creating the User
 * @param userDto
 * 
 */
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		logger.info("Initializing request for Create user");
		UserDto createUserDto = this.userService.createUser(userDto);
		logger.info("Completed request for save user");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

//=========================================================================================================
	// UPDATE

	/**
	 * @apiNote This method is for Updating the User
	 * @param userDto
	 * @param uid
	 * 
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer uid) {
		logger.info("Initializing request for update user");
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		logger.info("Completed request for update user");

		return ResponseEntity.ok(updateUser);
	}

//=========================================================================================================
	// DELETE
/**
 * @apiNote this method is for Deleting Single User Record
 * @param userId
 * @return
 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		logger.info("Initializing request for delete user");
		this.userService.deleteUser(userId);
		logger.info("completed request for delete user");
		return new ResponseEntity<ApiResponse>(new ApiResponse("user delete successfull", true), HttpStatus.OK);
	}

//=========================================================================================================
	// GET ALL
	/**
	 * @apiNote this method if for To Getting All user Records.
	 * @param pageNumber
	 * @param pageSize
	 * 
	 */
	
	@GetMapping("/page")
	public ResponseEntity<UserResponse> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		logger.info("Initializing request for getAll user");
		UserResponse response = this.userService.getAllUser(pageNumber, pageSize);
		logger.info("completed request for getAll user");
		return ResponseEntity.ok(response);
	}

//=========================================================================================================

	// GET
	
	/**
	 * @apiNote this method if for To Get Single user Record
	 * @param userId
	 * @return
	 */

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId) {
		logger.info("Initializing request for getSingle user");
		UserDto userById = this.userService.getUserById(userId);
		logger.info("completed request for getSingle user");
		return ResponseEntity.ok(userById);
	}

//=========================================================================================================

}
