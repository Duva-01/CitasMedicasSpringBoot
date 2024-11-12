package com.metaenlace.CitasMedicas.Service.impl;


import com.metaenlace.CitasMedicas.DTO.CitaDTO;
import com.metaenlace.CitasMedicas.DTO.UserDTO;
import com.metaenlace.CitasMedicas.Entity.Cita;
import com.metaenlace.CitasMedicas.Mapper.CitaMapper;
import com.metaenlace.CitasMedicas.Repository.CitaRepository;
import com.metaenlace.CitasMedicas.Service.CitaService;
import com.metaenlace.CitasMedicas.Service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("citaService")
public class CitaServiceImpl implements CitaService {

    private static final Log LOGGER = LogFactory.getLog(CitaServiceImpl.class);

    @Autowired
    @Qualifier("citaRepository")
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    public List<CitaDTO> getAllCitas(){

        LOGGER.info("Retrieving all medical appointments from database");
        List<Cita> citas = citaRepository.findAll();
        LOGGER.info("Number of medical appointments found: " + citas.size());

        return citas.stream()
                .map(citaMapper::citaToCitaDTO)
                .collect(Collectors.toList());
    }

    public CitaDTO getCitaById(int id){
        LOGGER.info("Fetching medical appointment with ID: " + id);
        Optional<Cita> Cita = citaRepository.findById(id);
        return Cita.map(citaMapper::citaToCitaDTO).orElse(null);
    }

    public CitaDTO saveCita(CitaDTO citaDTO){

        LOGGER.info("Saving medical appointment: " + citaDTO.toString());
        try {
            Cita cita = citaMapper.citaDTOToCita(citaDTO);
            Cita savedCita = citaRepository.save(cita);
            return citaMapper.citaToCitaDTO(savedCita);
        }
        catch (Exception e) {
            LOGGER.error("Error while trying creating a medical appointment: " + e.getMessage());
            return null;
        }
    }

    public CitaDTO updateCita(int id, CitaDTO citaDTO){

        LOGGER.info("Updating medical appointment with ID: " + id);
        Optional<Cita> existingCita = citaRepository.findById(id);

        if (existingCita.isPresent()) {
            Cita cita = citaMapper.citaDTOToCita(citaDTO);
            Cita updatedCita = citaRepository.save(cita);
            LOGGER.info("Medical appointment with ID " + id + " successfully updated");
            return citaMapper.citaToCitaDTO(updatedCita);
        } else {
            LOGGER.info("The Medical appointment with ID: " + id + " doesn't exists");
            return null;
        }
    }

    public boolean deleteCita(int id){

        LOGGER.info("Attempting to delete Medical appointment with ID: " + id);

        if (citaRepository.existsById(id)) {
            try {
                citaRepository.deleteById(id);
                LOGGER.info("Medical appointment with ID " + id + " successfully deleted.");
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while trying to delete Medical appointment with ID " + id + ": " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("Medical appointment with ID " + id + " does not exist.");
            return false;
        }
    }

    public List<CitaDTO> getCitasByPacienteNss(String nss) {
        LOGGER.info("Fetching appointments for patient NSS: " + nss);
        return citaRepository.findCitasByPacienteNss(nss).stream()
                .map(citaMapper::citaToCitaDTO)
                .collect(Collectors.toList());
    }

    public List<CitaDTO> getCitasByMedicoNumColegiado(String numColegiado) {
        LOGGER.info("Fetching appointments for doctor number: " + numColegiado);
        return citaRepository.findCitasByMedicoNumColegiado(numColegiado).stream()
                .map(citaMapper::citaToCitaDTO)
                .collect(Collectors.toList());
    }
}
