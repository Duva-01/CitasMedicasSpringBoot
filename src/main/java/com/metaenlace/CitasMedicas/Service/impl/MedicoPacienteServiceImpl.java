package com.metaenlace.CitasMedicas.Service.impl;


import com.metaenlace.CitasMedicas.DTO.MedicoPacienteDTO;
import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.MedicoPaciente;
import com.metaenlace.CitasMedicas.Mapper.MedicoPacienteMapper;
import com.metaenlace.CitasMedicas.Repository.MedicoPacienteRepository;
import com.metaenlace.CitasMedicas.Service.MedicoPacienteService;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("medicoPacienteService")
public class MedicoPacienteServiceImpl implements MedicoPacienteService {

    private static final Log LOGGER = LogFactory.getLog(MedicoPacienteServiceImpl.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("medicoPacienteRepository")
    private MedicoPacienteRepository medicoPacienteRepository;

    @Autowired
    private MedicoPacienteMapper medicoPacienteMapper;

    public List<MedicoPacienteDTO> getAllMedicoPacientes(){

        LOGGER.info("Retrieving all Medics-Patients from database");
        List<MedicoPaciente> medicoPacientes = medicoPacienteRepository.findAll();
        LOGGER.info("Number of Medics-Patients found: " + medicoPacientes.size());

        return medicoPacientes.stream()
                .map(medicoPacienteMapper::medicoPacienteToMedicoPacienteDTO)
                .collect(Collectors.toList());
    }

    public MedicoPacienteDTO getMedicoPacienteById(int id){
        LOGGER.info("Fetching Medic-Patients with ID: " + id);
        Optional<MedicoPaciente> medicoPaciente = medicoPacienteRepository.findById(id);
        return medicoPaciente.map(medicoPacienteMapper::medicoPacienteToMedicoPacienteDTO).orElse(null);
    }

    public MedicoPacienteDTO saveMedicoPaciente(MedicoPacienteDTO medicoPacienteDTO){

        LOGGER.info("Saving Medic-Patient: " + medicoPacienteDTO.toString());

        Optional<UserDTO> existingMedic = userService.getUserByUsuario(medicoPacienteDTO.getMedico().getUsuario());
        Optional<UserDTO> existingPatient = userService.getUserByUsuario(medicoPacienteDTO.getPaciente().getUsuario());

        if (existingMedic.isEmpty() || existingPatient.isEmpty()) {
            throw new IllegalArgumentException("Error while saving Medic-Patient: one of them is null.");
        } else {

            MedicoPaciente medicoPaciente = medicoPacienteMapper.medicoPacienteDTOToMedicoPaciente(medicoPacienteDTO);
            MedicoPaciente savedMedicoPaciente = medicoPacienteRepository.save(medicoPaciente);

            return medicoPacienteMapper.medicoPacienteToMedicoPacienteDTO(savedMedicoPaciente);
        }
    }

    public MedicoPacienteDTO updateMedicoPaciente(int id, MedicoPacienteDTO medicoPacienteDTO){

        LOGGER.info("Updating Medic-Patient with ID: " + id);
        Optional<MedicoPaciente> existingMedicoPaciente = medicoPacienteRepository.findById(id);

        if (existingMedicoPaciente.isPresent()) {
            MedicoPaciente medicoPaciente = medicoPacienteMapper.medicoPacienteDTOToMedicoPaciente(medicoPacienteDTO);
            MedicoPaciente updatedMedicoPaciente = medicoPacienteRepository.save(medicoPaciente);
            LOGGER.info("Medic-Patient with ID " + id + " successfully updated");
            return medicoPacienteMapper.medicoPacienteToMedicoPacienteDTO(updatedMedicoPaciente);
        } else {
            LOGGER.info("The Medic-Patient with ID: " + id + " doesn't exists");
            return null;
        }
    }

    public boolean deleteMedicoPaciente(int id){

        LOGGER.info("Attempting to delete Medic-Patient with ID: " + id);

        if (medicoPacienteRepository.existsById(id)) {
            try {
                medicoPacienteRepository.deleteById(id);
                LOGGER.info("Medic-Patient with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete Medic-Patient with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("Medic-Patient with ID " + id + " does not exist.");
            return false;
        }
    }
}
