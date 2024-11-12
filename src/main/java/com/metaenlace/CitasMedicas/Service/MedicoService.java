package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.MedicoDTO;

import java.util.List;


public interface MedicoService {

    public abstract List<MedicoDTO> getAllMedicos();

    public abstract MedicoDTO getMedicoById(int id);

    public abstract MedicoDTO saveMedico(MedicoDTO medicoDTO);

    public abstract MedicoDTO updateMedico(int id, MedicoDTO medicoDTO);

    public abstract boolean deleteMedico(int id);

    public MedicoDTO findByNumColegiado(String nss);
}
