package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Exceptions.ResourceNotFoundException;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        LOGGER.info("Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            LOGGER.info("No users found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + users.size() + " users.");
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        LOGGER.info("Fetching user with ID: " + id);
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            LOGGER.warn("User with ID: " + id + " not found.");
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            LOGGER.info("Creating new user: " + userDTO.toString());
            UserDTO createdUser = userService.saveUser(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        LOGGER.info("Updating user with ID: " + id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            LOGGER.info("User with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            LOGGER.warn("User with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        LOGGER.info("Deleting user with ID: " + id);
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            LOGGER.info("User with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("User with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<UserDTO>> getUsersByName(@RequestParam String nombre) {
        LOGGER.info("Searching users with name containing: " + nombre);
        List<UserDTO> users = userService.findByNombreContaining(nombre);
        if (users.isEmpty()) {
            LOGGER.info("No users found with name containing: " + nombre);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + users.size() + " users with name containing: " + nombre);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/searchBySurname")
    public ResponseEntity<List<UserDTO>> getUsersBySurname(@RequestParam String apellidos) {
        LOGGER.info("Searching users with surname containing: " + apellidos);
        List<UserDTO> users = userService.findByApellidosContaining(apellidos);
        if (users.isEmpty()) {
            LOGGER.info("No users found with surname containing: " + apellidos);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + users.size() + " users with surname containing: " + apellidos);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
}