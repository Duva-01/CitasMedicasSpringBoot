package com.metaenlace.CitasMedicas.Repository;

import com.metaenlace.CitasMedicas.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("citaRepository")
public interface CitaRepository extends JpaRepository<Cita, Serializable> {

    @Query("SELECT c FROM Cita c WHERE c.paciente.nss = :nss")
    List<Cita> findCitasByPacienteNss(@Param("nss") String nss);

    @Query("SELECT c FROM Cita c WHERE c.medico.num_colegiado = :num_colegiado")
    List<Cita> findCitasByMedicoNumColegiado(@Param("num_colegiado") String numColegiado);

}
