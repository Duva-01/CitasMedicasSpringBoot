package com.metaenlace.CitasMedicas.Service.impl;


import com.metaenlace.CitasMedicas.DTO.PacienteDTO;
import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.Paciente;
import com.metaenlace.CitasMedicas.Entity.User;
import com.metaenlace.CitasMedicas.Mapper.PacienteMapper;
import com.metaenlace.CitasMedicas.Repository.PacienteRepository;
import com.metaenlace.CitasMedicas.Service.PacienteService;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("pacienteService")
public class PacienteServiceImpl implements PacienteService {

    private static final Log LOGGER = LogFactory.getLog(PacienteServiceImpl.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("pacienteRepository")
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    public List<PacienteDTO> getAllPacientes(){

        LOGGER.info("Retrieving all patients from database");
        List<Paciente> pacientes = pacienteRepository.findAll();
        LOGGER.info("Number of patients found: " + pacientes.size());

        return pacientes.stream()
                .map(pacienteMapper::pacienteToPacienteDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO getPacienteById(int id){
        LOGGER.info("Fetching patient with ID: " + id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(pacienteMapper::pacienteToPacienteDTO).orElse(null);
    }

    public PacienteDTO savePaciente(PacienteDTO pacienteDTO){

        LOGGER.info("Saving patient: " + pacienteDTO.toString());

        Optional<UserDTO> existingUser = userService.getUserByUsuario(pacienteDTO.getUsuario());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already in use.");
        } else {

            Paciente paciente = pacienteMapper.pacienteDTOToPaciente(pacienteDTO);
            Paciente savedPaciente = pacienteRepository.save(paciente);

            return pacienteMapper.pacienteToPacienteDTO(savedPaciente);
        }
    }

    public PacienteDTO updatePaciente(int id, PacienteDTO pacienteDTO){

        LOGGER.info("Updating patient with ID: " + id);
        Optional<Paciente> existingPaciente = pacienteRepository.findById(id);

        if (existingPaciente.isPresent()) {
            Paciente paciente = pacienteMapper.pacienteDTOToPaciente(pacienteDTO);
            Paciente updatedPaciente = pacienteRepository.save(paciente);
            LOGGER.info("Patient with ID " + id + " successfully updated");
            return pacienteMapper.pacienteToPacienteDTO(updatedPaciente);
        } else {
            LOGGER.info("The patient with ID: " + id + " doesn't exists");
            return null;
        }
    }

    public boolean deletePaciente(int id){

        LOGGER.info("Attempting to delete patient with ID: " + id);

        if (pacienteRepository.existsById(id)) {
            try {
                pacienteRepository.deleteById(id);
                LOGGER.info("Patient with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete patient with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("Patient with ID " + id + " does not exist.");
            return false;
        }
    }

    public PacienteDTO findPacienteByNSS(String NSS) {
        LOGGER.info("Searching patient by nss: " + NSS);
        Optional<Paciente> paciente = pacienteRepository.findByNss(NSS);
        return paciente.map(pacienteMapper::pacienteToPacienteDTO).orElse(null);
    }
}