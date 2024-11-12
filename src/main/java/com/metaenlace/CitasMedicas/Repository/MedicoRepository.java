package com.metaenlace.CitasMedicas.Repository;

import com.metaenlace.CitasMedicas.Entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository("medicoRepository")
public interface MedicoRepository extends JpaRepository<Medico, Serializable> {

    @Query("SELECT m FROM Medico m WHERE m.num_colegiado = :numColegiado")
    Optional<Medico> findByNumColegiado(@Param("numColegiado") String numColegiado);
}
