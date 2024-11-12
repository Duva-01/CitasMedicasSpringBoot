package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {

    public abstract List<UserDTO> getAllUsers();

    public abstract UserDTO getUserById(int id);

    public abstract UserDTO saveUser(UserDTO userDTO);

    public abstract UserDTO updateUser(int id, UserDTO userDTO);

    public abstract boolean deleteUser(int id);

    public abstract Optional<UserDTO> getUserByUsuario(String usuario);

    public abstract List<UserDTO> findByNombreContaining(String nombre);

    public abstract List<UserDTO> findByApellidosContaining(String apellidos);
}
