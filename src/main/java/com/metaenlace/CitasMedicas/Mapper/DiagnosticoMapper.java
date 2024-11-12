package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.DiagnosticoDTO;
import com.metaenlace.CitasMedicas.Entity.Diagnostico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiagnosticoMapper {

    DiagnosticoDTO diagnosticoToDiagnosticoDTO(Diagnostico diagnostico);
    Diagnostico diagnosticoDTOToDiagnostico(DiagnosticoDTO diagnosticoDTO);
}
