package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.PacienteDTO;
import com.metaenlace.CitasMedicas.Exceptions.ResourceNotFoundException;
import com.metaenlace.CitasMedicas.Service.PacienteService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Log LOGGER = LogFactory.getLog(PacienteController.class);

    @Autowired
    @Qualifier("pacienteService")
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> getAllPacientes() {
        LOGGER.info("Fetching all patients");
        List<PacienteDTO> pacientes = pacienteService.getAllPacientes();
        if (pacientes.isEmpty()) {
            LOGGER.info("No patients found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + pacientes.size() + " patients.");
            return new ResponseEntity<>(pacientes, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable int id) {
        LOGGER.info("Fetching patient with ID: " + id);
        PacienteDTO paciente = pacienteService.getPacienteById(id);
        if (paciente != null) {
            return new ResponseEntity<>(paciente, HttpStatus.OK);
        } else {
            LOGGER.warn("Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bynss/{NSS}")
    public ResponseEntity<PacienteDTO> getPacienteByNSS(@PathVariable String NSS) {
        LOGGER.info("Searching patient by NSS: " + NSS);
        PacienteDTO paciente = pacienteService.findPacienteByNSS(NSS);
        if (paciente != null) {
            return new ResponseEntity<>(paciente, HttpStatus.OK);
        } else {
            LOGGER.warn("No patient found for NSS: " + NSS);
            throw new ResourceNotFoundException("No patient found for NSS: " + NSS);
        }
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> createPaciente(@RequestBody PacienteDTO pacienteDTO) {
        try {
            LOGGER.info("Creating new patient: " + pacienteDTO.toString());
            PacienteDTO createdPaciente = pacienteService.savePaciente(pacienteDTO);
            return new ResponseEntity<>(createdPaciente, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating patient: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(@PathVariable int id, @RequestBody PacienteDTO pacienteDTO) {
        LOGGER.info("Updating patient with ID: " + id);
        PacienteDTO updatedPaciente = pacienteService.updatePaciente(id, pacienteDTO);
        if (updatedPaciente != null) {
            LOGGER.info("Patient with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedPaciente, HttpStatus.OK);
        } else {
            LOGGER.warn("Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable int id) {
        LOGGER.info("Deleting patient with ID: " + id);
        boolean isDeleted = pacienteService.deletePaciente(id);
        if (isDeleted) {
            LOGGER.info("Patient with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
