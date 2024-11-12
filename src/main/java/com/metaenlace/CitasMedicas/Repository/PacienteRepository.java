package com.metaenlace.CitasMedicas.Repository;


import com.metaenlace.CitasMedicas.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository("pacienteRepository")
public interface PacienteRepository extends JpaRepository<Paciente, Serializable> {

    @Query("SELECT p FROM Paciente p WHERE p.nss = :nss")
    Optional<Paciente> findByNss(@Param("nss") String nss);

}
