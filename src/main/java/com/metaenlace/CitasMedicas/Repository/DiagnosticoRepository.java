package com.metaenlace.CitasMedicas.Repository;

import com.metaenlace.CitasMedicas.Entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("diagnosticoRepository")
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Serializable> {

    @Query("SELECT d FROM Diagnostico d WHERE d.enfermedad LIKE %:enfermedad%")
    List<Diagnostico> findDiagnosticosByEnfermedad(@Param("enfermedad") String enfermedad);

    @Query("SELECT d FROM Diagnostico d WHERE d.valoracion_especialista LIKE %:valoracion%")
    List<Diagnostico> findDiagnosticosByValoracionEspecialista(@Param("valoracion") String valoracion);
}
