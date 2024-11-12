package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.DiagnosticoDTO;
import com.metaenlace.CitasMedicas.Exceptions.ResourceNotFoundException;
import com.metaenlace.CitasMedicas.Service.DiagnosticoService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diagnosticos")
public class DiagnosticoController {

    private static final Log LOGGER = LogFactory.getLog(DiagnosticoController.class);

    @Autowired
    @Qualifier("diagnosticoService")
    private DiagnosticoService diagnosticoService;

    @GetMapping
    public ResponseEntity<List<DiagnosticoDTO>> getAllDiagnosticos() {
        LOGGER.info("Fetching all diagnoses");
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.getAllDiagnosticos();
        if (diagnosticos.isEmpty()) {
            LOGGER.info("No diagnoses found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + diagnosticos.size() + " diagnoses.");
            return new ResponseEntity<>(diagnosticos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoDTO> getDiagnosticoById(@PathVariable int id) {
        LOGGER.info("Fetching diagnosis with ID: " + id);
        DiagnosticoDTO diagnostico = diagnosticoService.getDiagnosticoById(id);
        if (diagnostico != null) {
            return new ResponseEntity<>(diagnostico, HttpStatus.OK);
        } else {
            LOGGER.warn("Diagnosis with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchbydisease")
    public ResponseEntity<List<DiagnosticoDTO>> getDiagnosticosByEnfermedad(@RequestParam String enfermedad) {
        LOGGER.info("Fetching diagnostics by disease: " + enfermedad);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.findDiagnosticosByEnfermedad(enfermedad);
        if (!diagnosticos.isEmpty()) {
            return new ResponseEntity<>(diagnosticos, HttpStatus.OK);
        } else {
            LOGGER.warn("No diagnostics found for disease: " + enfermedad);
            throw new ResourceNotFoundException("No diagnostics found for disease: " + enfermedad);
        }
    }

    @PostMapping
    public ResponseEntity<DiagnosticoDTO> createDiagnostico(@RequestBody DiagnosticoDTO diagnosticoDTO) {
        try {
            LOGGER.info("Creating new diagnosis: " + diagnosticoDTO.toString());
            DiagnosticoDTO createdDiagnostico = diagnosticoService.saveDiagnostico(diagnosticoDTO);

            if(createdDiagnostico != null){
                return new ResponseEntity<>(createdDiagnostico, HttpStatus.CREATED);
            }
            else {
                LOGGER.error("Error creating diagnosis");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("Error creating diagnosis: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticoDTO> updateDiagnostico(@PathVariable int id, @RequestBody DiagnosticoDTO diagnosticoDTO) {
        LOGGER.info("Updating diagnosis with ID: " + id);
        DiagnosticoDTO updatedDiagnostico = diagnosticoService.updateDiagnostico(id, diagnosticoDTO);
        if (updatedDiagnostico != null) {
            LOGGER.info("Diagnosis with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedDiagnostico, HttpStatus.OK);
        } else {
            LOGGER.warn("Diagnosis with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnostico(@PathVariable int id) {
        LOGGER.info("Deleting diagnosis with ID: " + id);
        boolean isDeleted = diagnosticoService.deleteDiagnostico(id);
        if (isDeleted) {
            LOGGER.info("Diagnosis with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("Diagnosis with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
