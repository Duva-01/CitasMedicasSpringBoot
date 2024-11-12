package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.PacienteDTO;
import com.metaenlace.CitasMedicas.Entity.Paciente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    PacienteDTO pacienteToPacienteDTO(Paciente paciente);
    Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO);
}
