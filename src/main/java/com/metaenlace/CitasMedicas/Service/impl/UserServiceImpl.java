package com.metaenlace.CitasMedicas.Service.impl;

import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.User;
import com.metaenlace.CitasMedicas.Mapper.UserMapper;
import com.metaenlace.CitasMedicas.Repository.UserRepository;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);

    @Override
    public List<UserDTO> getAllUsers() {
        LOGGER.info("Retrieving all users from database");
        List<User> users = userRepository.findAll();
        LOGGER.info("Number of users found: " + users.size());

        return users.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(int id) {
        LOGGER.info("Fetching user with ID: " + id);
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::userToUserDTO).orElse(null);
    }

    @Override
    public Optional<UserDTO> getUserByUsuario(String usuario) {
        Optional<User> user = userRepository.findByUsuario(usuario);
        return user.map(userMapper::userToUserDTO);
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        LOGGER.info("Saving user: " + userDTO.toString());
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(int id, UserDTO userDTO) {
        LOGGER.info("Updating user with ID: " + id);
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = userMapper.userDTOToUser(userDTO);
            User updatedUser = userRepository.save(user);
            LOGGER.info("User with ID " + id + " successfully updated");
            return userMapper.userToUserDTO(updatedUser);
        } else {
            LOGGER.info("The user with ID: " + id + " doesn't exists");
            return null;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        LOGGER.info("Attempting to delete user with ID: " + id);

        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
                LOGGER.info("User with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete user with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("User with ID " + id + " does not exist.");
            return false;
        }
    }

    @Override
    public List<UserDTO> findByNombreContaining(String nombre) {
        LOGGER.info("Searching users by name containing: " + nombre);
        List<User> users = userRepository.findByNombreContaining(nombre);
        return users.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByApellidosContaining(String apellidos) {
        LOGGER.info("Searching users by surname containing: " + apellidos);
        List<User> users = userRepository.findByApellidosContaining(apellidos);
        return users.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }
}
