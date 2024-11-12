package com.metaenlace.CitasMedicas.DTO;

import com.metaenlace.CitasMedicas.Entity.Diagnostico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CitaDTO {

    private int id;
    private LocalDateTime fecha_hora;

    private String motivo_cita;

    private PacienteDTO paciente;
    private MedicoDTO medico;

    @Override
    public String toString() {
        return "CitaDTO{" +
                "id=" + id +
                ", fechaHora=" + fecha_hora +
                ", motivoCita='" + motivo_cita + '\'' +
                ", paciente=" + paciente +
                ", medico=" + medico +
                '}';
    }
}
