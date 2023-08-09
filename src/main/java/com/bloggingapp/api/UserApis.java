package com.bloggingapp.api;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapp.dto.ApiResponse;
import com.bloggingapp.dto.UserDto;
import com.bloggingapp.services.UserServices;
import com.bloggingapp.utility.GlobleResources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

/**
 * @author Prasad Pansare
 */
@RestController
@RequestMapping("/api/users")
public class UserApis {

	private Logger logger = GlobleResources.getLogger(UserApis.class);

	@Autowired
	private UserServices service;

	// POST-create user
	@PostMapping("/new")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userdto) {
		logger.info("Started addUser() function");

		UserDto dto = null;
		try {
			dto = this.service.addUser(userdto);

		} catch (Exception e) {

			System.out.println(e);
		}
		return new ResponseEntity<UserDto>(dto, HttpStatus.CREATED);

	}

	// PUT-Update Data

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Integer id) {
		UserDto updatedUser = null;
		logger.info("Started updateUser() function");

		try {
			updatedUser = this.service.updateUser(userDto, id);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedUser);

	}

	// DELETE-delete user
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse> userDeleted(@PathVariable("id") Integer id) {
		logger.info("Started userDeleted() function");

		try {
			this.service.deleteUser(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse("User deleted Succesfully", true), HttpStatus.OK);
	}

	// GET-get all user
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDto>> getAllUser() {
		logger.info("Started getAllUser() function");
		return ResponseEntity.ok(this.service.getAllUser());

	}

	// GET-single user
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserDto> getById(@PathVariable("id") Integer id) {
		try {

			logger.info("Started getById() function");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(this.service.getById(id));
	}
}
