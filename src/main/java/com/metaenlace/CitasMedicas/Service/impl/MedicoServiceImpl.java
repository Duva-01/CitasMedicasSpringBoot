package com.metaenlace.CitasMedicas.Service.impl;


import com.metaenlace.CitasMedicas.DTO.MedicoDTO;
import com.metaenlace.CitasMedicas.DTO.PacienteDTO;
import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.Medico;
import com.metaenlace.CitasMedicas.Entity.Paciente;
import com.metaenlace.CitasMedicas.Mapper.MedicoMapper;
import com.metaenlace.CitasMedicas.Repository.MedicoRepository;
import com.metaenlace.CitasMedicas.Service.MedicoService;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("medicoService")
public class MedicoServiceImpl implements MedicoService {

    private static final Log LOGGER = LogFactory.getLog(MedicoServiceImpl.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("medicoRepository")
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;

    public List<MedicoDTO> getAllMedicos(){

        LOGGER.info("Retrieving all Medics from database");
        List<Medico> Medicos = medicoRepository.findAll();
        LOGGER.info("Number of Medics found: " + Medicos.size());

        return Medicos.stream()
                .map(medicoMapper::medicoToMedicoDTO)
                .collect(Collectors.toList());
    }

    public MedicoDTO getMedicoById(int id){
        LOGGER.info("Fetching Medic with ID: " + id);
        Optional<Medico> Medico = medicoRepository.findById(id);
        return Medico.map(medicoMapper::medicoToMedicoDTO).orElse(null);
    }

    public MedicoDTO saveMedico(MedicoDTO medicoDTO){

        LOGGER.info("Saving Medic: " + medicoDTO.toString());

        Optional<UserDTO> existingUser = userService.getUserByUsuario(medicoDTO.getUsuario());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already in use.");
        } else {

            Medico Medico = medicoMapper.medicoDTOToMedico(medicoDTO);
            Medico savedMedico = medicoRepository.save(Medico);

            return medicoMapper.medicoToMedicoDTO(savedMedico);
        }
    }

    public MedicoDTO updateMedico(int id, MedicoDTO medicoDTO){

        LOGGER.info("Updating Medic with ID: " + id);
        Optional<Medico> existingMedico = medicoRepository.findById(id);

        if (existingMedico.isPresent()) {
            Medico Medico = medicoMapper.medicoDTOToMedico(medicoDTO);
            Medico updatedMedico = medicoRepository.save(Medico);
            LOGGER.info("Medic with ID " + id + " successfully updated");
            return medicoMapper.medicoToMedicoDTO(updatedMedico);
        } else {
            LOGGER.info("The Medic with ID: " + id + " doesn't exists");
            return null;
        }
    }

    public boolean deleteMedico(int id){

        LOGGER.info("Attempting to delete Medic with ID: " + id);

        if (medicoRepository.existsById(id)) {
            try {
                medicoRepository.deleteById(id);
                LOGGER.info("Medic with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete Medic with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("Medic with ID " + id + " does not exist.");
            return false;
        }
    }

    public MedicoDTO findByNumColegiado(String numColegiado) {
        LOGGER.info("Searching doctor by num_colegiado: " + numColegiado);

        Optional<Medico> medico = medicoRepository.findByNumColegiado(numColegiado);
        return medico.map(medicoMapper::medicoToMedicoDTO).orElse(null);
    }
}
