package com.metaenlace.CitasMedicas.Service;

import com.metaenlace.CitasMedicas.DTO.DiagnosticoDTO;
import java.util.List;

public interface DiagnosticoService {

    public abstract List<DiagnosticoDTO> getAllDiagnosticos();

    public abstract DiagnosticoDTO getDiagnosticoById(int id);

    public abstract DiagnosticoDTO saveDiagnostico(DiagnosticoDTO diagnosticoDTO);

    public abstract DiagnosticoDTO updateDiagnostico(int id, DiagnosticoDTO diagnosticoDTO);

    public abstract boolean deleteDiagnostico(int id);

    public abstract List<DiagnosticoDTO> findDiagnosticosByEnfermedad(String enfermedad);
}
