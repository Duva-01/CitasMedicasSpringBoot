package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.MedicoDTO;
import com.metaenlace.CitasMedicas.Entity.Medico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    MedicoDTO medicoToMedicoDTO(Medico medico);
    Medico medicoDTOToMedico(MedicoDTO medicoDTO);
}
