package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.PacienteDTO;

import java.util.List;

public interface PacienteService {

    public abstract List<PacienteDTO> getAllPacientes();

    public abstract PacienteDTO getPacienteById(int id);

    public abstract PacienteDTO savePaciente(PacienteDTO pacienteDTO);

    public abstract PacienteDTO updatePaciente(int id, PacienteDTO pacienteDTO);

    public abstract boolean deletePaciente(int id);

    public abstract PacienteDTO findPacienteByNSS(String numColegiado);
}
