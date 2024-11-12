package com.metaenlace.CitasMedicas.Service.impl;

import com.metaenlace.CitasMedicas.DTO.DiagnosticoDTO;
import com.metaenlace.CitasMedicas.Entity.Diagnostico;
import com.metaenlace.CitasMedicas.Mapper.DiagnosticoMapper;
import com.metaenlace.CitasMedicas.Repository.DiagnosticoRepository;
import com.metaenlace.CitasMedicas.Service.DiagnosticoService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("diagnosticoService")
public class DiagnosticoServiceImpl implements DiagnosticoService {

    private static final Log LOGGER = LogFactory.getLog(DiagnosticoServiceImpl.class);

    @Autowired
    @Qualifier("diagnosticoRepository")
    private DiagnosticoRepository diagnosticoRepository;

    @Autowired
    private DiagnosticoMapper diagnosticoMapper;

    public List<DiagnosticoDTO> getAllDiagnosticos(){

        LOGGER.info("Retrieving all diagnoses from database");
        List<Diagnostico> diagnosticos = diagnosticoRepository.findAll();
        LOGGER.info("Number of diagnoses found: " + diagnosticos.size());

        return diagnosticos.stream()
                .map(diagnosticoMapper::diagnosticoToDiagnosticoDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoDTO getDiagnosticoById(int id){
        LOGGER.info("Fetching diagnosis with ID: " + id);
        Optional<Diagnostico> diagnostico = diagnosticoRepository.findById(id);
        return diagnostico.map(diagnosticoMapper::diagnosticoToDiagnosticoDTO).orElse(null);
    }

    public DiagnosticoDTO saveDiagnostico(DiagnosticoDTO diagnosticoDTO){

        LOGGER.info("Saving diagnosis: " + diagnosticoDTO.toString());
        try {
            Diagnostico diagnostico = diagnosticoMapper.diagnosticoDTOToDiagnostico(diagnosticoDTO);
            Diagnostico savedDiagnostico = diagnosticoRepository.save(diagnostico);
            return diagnosticoMapper.diagnosticoToDiagnosticoDTO(savedDiagnostico);
        }
        catch (Exception e) {
            LOGGER.error("Error while trying creating a diagnosis: " + e.getMessage());
            return null;
        }
    }

    public DiagnosticoDTO updateDiagnostico(int id, DiagnosticoDTO diagnosticoDTO){

        LOGGER.info("Updating diagnosis with ID: " + id);
        Optional<Diagnostico> existingDiagnostico = diagnosticoRepository.findById(id);

        if (existingDiagnostico.isPresent()) {
            Diagnostico diagnostico = diagnosticoMapper.diagnosticoDTOToDiagnostico(diagnosticoDTO);
            Diagnostico updatedDiagnostico = diagnosticoRepository.save(diagnostico);
            LOGGER.info("Diagnosis with ID " + id + " successfully updated");
            return diagnosticoMapper.diagnosticoToDiagnosticoDTO(updatedDiagnostico);
        } else {
            LOGGER.info("The diagnosis with ID: " + id + " doesn't exists");
            return null;
        }
    }

    public boolean deleteDiagnostico(int id){

        LOGGER.info("Attempting to delete diagnosis with ID: " + id);

        if (diagnosticoRepository.existsById(id)) {
            try {
                diagnosticoRepository.deleteById(id);
                LOGGER.info("Diagnosis with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete diagnosis with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("Diagnosis with ID " + id + " does not exist.");
            return false;
        }
    }

    public List<DiagnosticoDTO> findDiagnosticosByEnfermedad(String enfermedad) {
        LOGGER.info("Searching diagnosis by disease: " + enfermedad);
        return diagnosticoRepository.findDiagnosticosByEnfermedad(enfermedad).stream()
                .map(diagnosticoMapper::diagnosticoToDiagnosticoDTO)
                .collect(Collectors.toList());
    }

}
