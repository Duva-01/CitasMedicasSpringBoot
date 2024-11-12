package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.MedicoPacienteDTO;

import java.util.List;


public interface MedicoPacienteService {

    public abstract List<MedicoPacienteDTO> getAllMedicoPacientes();

    public abstract MedicoPacienteDTO getMedicoPacienteById(int id);

    public abstract MedicoPacienteDTO saveMedicoPaciente(MedicoPacienteDTO medicoPacienteDTO);

    public abstract MedicoPacienteDTO updateMedicoPaciente(int id, MedicoPacienteDTO medicoPacienteDTO);

    public abstract boolean deleteMedicoPaciente(int id);
}
