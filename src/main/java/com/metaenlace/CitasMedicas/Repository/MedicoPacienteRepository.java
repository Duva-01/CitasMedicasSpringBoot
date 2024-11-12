package com.metaenlace.CitasMedicas.Repository;

import com.metaenlace.CitasMedicas.Entity.MedicoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


@Repository("medicoPacienteRepository")
public interface MedicoPacienteRepository extends JpaRepository<MedicoPaciente, Serializable> {
}
