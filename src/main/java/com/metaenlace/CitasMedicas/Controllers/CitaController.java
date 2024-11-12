package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.CitaDTO;
import com.metaenlace.CitasMedicas.Exceptions.ResourceNotFoundException;
import com.metaenlace.CitasMedicas.Service.CitaService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private static final Log LOGGER = LogFactory.getLog(CitaController.class);

    @Autowired
    @Qualifier("citaService")
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> getAllCitas() {
        LOGGER.info("Fetching all Medical appointments");
        List<CitaDTO> citas = citaService.getAllCitas();
        if (citas.isEmpty()) {
            LOGGER.info("No medical appointments found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + citas.size() + " Medical appointments.");
            return new ResponseEntity<>(citas, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> getCitaById(@PathVariable int id) {
        LOGGER.info("Fetching Medical appointment with ID: " + id);
        CitaDTO cita = citaService.getCitaById(id);
        if (cita != null) {
            return new ResponseEntity<>(cita, HttpStatus.OK);
        } else {
            LOGGER.warn("Medical appointment with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bypatient/{nss}")
    public ResponseEntity<List<CitaDTO>> getCitasByPacienteNss(@PathVariable String nss) {
        LOGGER.info("Fetching appointments for patient NSS: " + nss);
        List<CitaDTO> citas = citaService.getCitasByPacienteNss(nss);
        if (!citas.isEmpty()) {
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } else {
            LOGGER.warn("No appointments found for patient NSS: " + nss);
            throw new ResourceNotFoundException("No appointments found for patient NSS: " + nss);
        }
    }

    @GetMapping("/bymedic/{numColegiado}")
    public ResponseEntity<List<CitaDTO>> getCitasByMedicoNumColegiado(@PathVariable String numColegiado) {
        LOGGER.info("Fetching appointments for doctor number: " + numColegiado);
        List<CitaDTO> citas = citaService.getCitasByMedicoNumColegiado(numColegiado);
        if (!citas.isEmpty()) {
            return new ResponseEntity<>(citas, HttpStatus.OK);
        } else {
            LOGGER.warn("No appointments found for doctor number: " + numColegiado);
            throw new ResourceNotFoundException("No appointments found for doctor number: " + numColegiado);
        }
    }

    @PostMapping
    public ResponseEntity<CitaDTO> createCita(@RequestBody CitaDTO citaDTO) {
        try {
            LOGGER.info("Creating new Medical appointment: " + citaDTO.toString());
            CitaDTO createdCita = citaService.saveCita(citaDTO);
            if(createdCita != null){
                return new ResponseEntity<>(createdCita, HttpStatus.CREATED);
            }
            else {
                LOGGER.error("Error creating medical appointment");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            LOGGER.error("Error creating medical appointment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> updateCita(@PathVariable int id, @RequestBody CitaDTO citaDTO) {
        LOGGER.info("Updating Medical appointment with ID: " + id);
        CitaDTO updatedCita = citaService.updateCita(id, citaDTO);
        if (updatedCita != null) {
            LOGGER.info("Medical appointment with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedCita, HttpStatus.OK);
        } else {
            LOGGER.warn("Medical appointment with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable int id) {
        LOGGER.info("Deleting Medical appointment with ID: " + id);
        boolean isDeleted = citaService.deleteCita(id);
        if (isDeleted) {
            LOGGER.info("Medical appointment with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("Medical appointment with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
