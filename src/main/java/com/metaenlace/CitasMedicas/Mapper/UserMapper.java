package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}

