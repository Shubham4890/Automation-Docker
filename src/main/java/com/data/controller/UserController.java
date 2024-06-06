package com.data.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.data.entity.User;
import com.data.repo.UserRepo;

@RestController
@RequestMapping("/rest")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	// Post
	@PostMapping("/save")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.OK).body("User Add Successfully !!!");

	}

	// Get
	@PostMapping("/getUser")
	public ResponseEntity<Object> getUser() {
	    List<User> userList = userRepo.findAll();

	    if (!userList.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.OK).body(userList);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found");
	    }
	}


	// Put
	@PostMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Integer id) {
        Map<String, String> errors = new HashMap<>();

        // Validate input fields
        if (user.getName() == null || user.getName().isEmpty() || !user.getName().matches("^[a-zA-Z\\s-]+$")) {
            errors.put("name", "Name cannot be null, empty, or contain invalid characters (only letters, spaces, and hyphens allowed)");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.put("email", "Email cannot be null or empty");
        } else if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.put("email", "Email should be valid");
        }
        if (user.getCity() == null || user.getCity().isEmpty() || !user.getCity().matches("^[a-zA-Z\\s-]+$")) {
            errors.put("city", "City cannot be null, empty, or contain invalid characters (only letters, spaces, and hyphens allowed)");
        }
        if (user.getAge() == null || user.getAge().isEmpty()) {
            errors.put("age", "Age cannot be null or empty");
        } else if (!user.getAge().matches("^[0-9]{1,3}$")) {
            errors.put("age", "Age should be a valid number");
        }
        if (user.getCountry() == null || user.getCountry().isEmpty() || !user.getCountry().matches("^[a-zA-Z\\s-]+$")) {
            errors.put("country", "Country cannot be null, empty, or contain invalid characters (only letters, spaces, and hyphens allowed)");
        }
        if (user.getState() == null || user.getState().isEmpty() || !user.getState().matches("^[a-zA-Z\\s-]+$")) {
            errors.put("state", "State cannot be null, empty, or contain invalid characters (only letters, spaces, and hyphens allowed)");
        }

        // Check for duplicate name
        Optional<User> userWithSameName = userRepo.findByName(user.getName());
        if (userWithSameName.isPresent() && userWithSameName.get().getId() != id) {
            errors.put("name", "Name is already taken");
        }

        // Check for duplicate email
        Optional<User> userWithSameEmail = userRepo.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent() && userWithSameEmail.get().getId() != id) {
            errors.put("email", "Email is already taken");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            user.setId(existingUser.get().getId());
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User details are updated successfully for id " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User details are not updated successfully for id " + id);
        }
    }
	/*
	 * @PostMapping("/update/{id}") public ResponseEntity<Object>
	 * updateUser(@RequestBody User user, @PathVariable Integer id) { Optional<User>
	 * users = userRepo.findById(id); if (users.isPresent()) {
	 * user.setId(users.get().getId()); userRepo.save(user); return
	 * ResponseEntity.status(HttpStatus.OK).
	 * body("User Details are updated successfully for id " + id); } else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND)
	 * .body("User Details are not updated successfully for id " + id); }
	 * 
	 * }
	 */

	/*
	 * // Delete
	 * 
	 * @DeleteMapping("/deleteAll") public ResponseEntity<Object> deleteAllUser() {
	 * userRepo.deleteAll(); return ResponseEntity.status(HttpStatus.OK).
	 * body("User Details are delete successfully "); }
	 * 
	 * // Delete-Id
	 * 
	 * @DeleteMapping("/delete/{id}") public ResponseEntity<Object>
	 * deleteById(@PathVariable Integer id) { Optional<User> users =
	 * userRepo.findById(id); if (users.isPresent()) { userRepo.deleteById(id);
	 * return ResponseEntity.status(HttpStatus.OK).
	 * body("User Details are deleted successfully for id " + id); } else { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND)
	 * .body("User Details are not deleted successfully for id " + id); } }
	 */

}
