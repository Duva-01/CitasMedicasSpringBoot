package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.MedicoDTO;
import com.metaenlace.CitasMedicas.Exceptions.ResourceNotFoundException;
import com.metaenlace.CitasMedicas.Service.MedicoService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private static final Log LOGGER = LogFactory.getLog(MedicoController.class);

    @Autowired
    @Qualifier("medicoService")
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> getAllMedicos() {
        LOGGER.info("Fetching all Medics");
        List<MedicoDTO> medicos = medicoService.getAllMedicos();
        if (medicos.isEmpty()) {
            LOGGER.info("No medics found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + medicos.size() + " medics.");
            return new ResponseEntity<>(medicos, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> getMedicoById(@PathVariable int id) {
        LOGGER.info("Fetching Medic with ID: " + id);
        MedicoDTO medico = medicoService.getMedicoById(id);
        if (medico != null) {
            return new ResponseEntity<>(medico, HttpStatus.OK);
        } else {
            LOGGER.warn("Medic with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bynumcolegiado/{numColegiado}")
    public ResponseEntity<MedicoDTO> findByNumColegiado(@PathVariable String numColegiado) {
        LOGGER.info("Fetching medic by numColegiado: " + numColegiado);
        MedicoDTO medico = medicoService.findByNumColegiado(numColegiado);
        if (medico != null) {
            return new ResponseEntity<>(medico, HttpStatus.OK);
        } else {
            LOGGER.warn("No medic found by numColegiado: " + numColegiado);
            throw new ResourceNotFoundException("No medic found by numColegiado: " + numColegiado);
        }
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> createMedico(@RequestBody MedicoDTO medicoDTO) {
        try {
            LOGGER.info("Creating new Medic: " + medicoDTO.toString());
            MedicoDTO createdMedico = medicoService.saveMedico(medicoDTO);
            return new ResponseEntity<>(createdMedico, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating medic: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> updateMedico(@PathVariable int id, @RequestBody MedicoDTO medicoDTO) {
        LOGGER.info("Updating Medic with ID: " + id);
        MedicoDTO updatedMedico = medicoService.updateMedico(id, medicoDTO);
        if (updatedMedico != null) {
            LOGGER.info("Medic with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedMedico, HttpStatus.OK);
        } else {
            LOGGER.warn("Medic with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable int id) {
        LOGGER.info("Deleting Medic with ID: " + id);
        boolean isDeleted = medicoService.deleteMedico(id);
        if (isDeleted) {
            LOGGER.info("Medic with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("Medic with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
