package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.MedicoPacienteDTO;
import com.metaenlace.CitasMedicas.Entity.MedicoPaciente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicoPacienteMapper {

    MedicoPacienteDTO medicoPacienteToMedicoPacienteDTO(MedicoPaciente medicoPaciente);
    MedicoPaciente medicoPacienteDTOToMedicoPaciente(MedicoPacienteDTO medicoPacienteDTO);
}

