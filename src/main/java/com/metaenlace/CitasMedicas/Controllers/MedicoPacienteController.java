package com.metaenlace.CitasMedicas.Controllers;

import com.metaenlace.CitasMedicas.DTO.MedicoPacienteDTO;
import com.metaenlace.CitasMedicas.Service.MedicoPacienteService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicospacientes")
public class MedicoPacienteController {

    private static final Log LOGGER = LogFactory.getLog(MedicoPacienteController.class);

    @Autowired
    @Qualifier("medicoPacienteService")
    private MedicoPacienteService medicoPacienteService;

    @GetMapping
    public ResponseEntity<List<MedicoPacienteDTO>> getAllMedicoPacientes() {
        LOGGER.info("Fetching all Medics-Patients");
        List<MedicoPacienteDTO> medicoPacientes = medicoPacienteService.getAllMedicoPacientes();
        if (medicoPacientes.isEmpty()) {
            LOGGER.info("No Medics-Patients found.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("Found " + medicoPacientes.size() + " Medics-Patients.");
            return new ResponseEntity<>(medicoPacientes, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoPacienteDTO> getMedicoPacienteById(@PathVariable int id) {
        LOGGER.info("Fetching Medic-Patients with ID: " + id);
        MedicoPacienteDTO medicoPaciente = medicoPacienteService.getMedicoPacienteById(id);
        if (medicoPaciente != null) {
            return new ResponseEntity<>(medicoPaciente, HttpStatus.OK);
        } else {
            LOGGER.warn("Medic-Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MedicoPacienteDTO> createMedicoPaciente(@RequestBody MedicoPacienteDTO medicoPacienteDTO) {
        try {
            LOGGER.info("Creating new Medic-Patient: " + medicoPacienteDTO.toString());
            MedicoPacienteDTO createdMedicoPaciente = medicoPacienteService.saveMedicoPaciente(medicoPacienteDTO);
            return new ResponseEntity<>(createdMedicoPaciente, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating Medic-Patient: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoPacienteDTO> updateMedicoPaciente(@PathVariable int id, @RequestBody MedicoPacienteDTO medicoPacienteDTO) {
        LOGGER.info("Updating Medic-Patient with ID: " + id);
        MedicoPacienteDTO updatedMedicoPaciente = medicoPacienteService.updateMedicoPaciente(id, medicoPacienteDTO);
        if (updatedMedicoPaciente != null) {
            LOGGER.info("Medic-Patient with ID: " + id + " updated successfully.");
            return new ResponseEntity<>(updatedMedicoPaciente, HttpStatus.OK);
        } else {
            LOGGER.warn("Medic-Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicoPaciente(@PathVariable int id) {
        LOGGER.info("Deleting Medic with ID: " + id);
        boolean isDeleted = medicoPacienteService.deleteMedicoPaciente(id);
        if (isDeleted) {
            LOGGER.info("Medic-Patient with ID: " + id + " deleted successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            LOGGER.warn("Medic-Patient with ID: " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
