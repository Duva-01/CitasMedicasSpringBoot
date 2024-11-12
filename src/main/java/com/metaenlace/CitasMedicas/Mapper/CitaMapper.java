package com.metaenlace.CitasMedicas.Mapper;

import com.metaenlace.CitasMedicas.DTO.CitaDTO;
import com.metaenlace.CitasMedicas.Entity.Cita;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitaMapper {

    CitaDTO citaToCitaDTO(Cita cita);
    Cita citaDTOToCita(CitaDTO citaDTO);
}
