package com.metaenlace.CitasMedicas.Repository;

import com.metaenlace.CitasMedicas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Serializable> {
    Optional<User> findByUsuario(String usuario);

    @Query("SELECT u FROM User u WHERE u.nombre LIKE %:nombre%")
    List<User> findByNombreContaining(@Param("nombre") String nombre);

    @Query("SELECT u FROM User u WHERE u.apellidos LIKE %:apellidos%")
    List<User> findByApellidosContaining(@Param("apellidos") String apellidos);
}