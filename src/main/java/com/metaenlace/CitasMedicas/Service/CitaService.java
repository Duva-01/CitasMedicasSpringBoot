package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.CitaDTO;

import java.util.List;


public interface CitaService {

    public abstract List<CitaDTO> getAllCitas();

    public abstract CitaDTO getCitaById(int id);

    public abstract CitaDTO saveCita(CitaDTO citaDTO);

    public abstract CitaDTO updateCita(int id, CitaDTO citaDTO);

    public abstract boolean deleteCita(int id);

    public abstract List<CitaDTO> getCitasByPacienteNss(String nss);

    public abstract List<CitaDTO> getCitasByMedicoNumColegiado(String numColegiado);
}
