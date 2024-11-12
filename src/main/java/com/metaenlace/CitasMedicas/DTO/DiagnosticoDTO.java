package com.metaenlace.CitasMedicas.DTO;

import com.metaenlace.CitasMedicas.Entity.Cita;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosticoDTO {

    private int id;

    private String valoracion_especialista;

    private String enfermedad;

    private CitaDTO cita;

    @Override
    public String toString() {
        return "DiagnosticoDTO{" +
                "id=" + id +
                ", valoracion_especialista='" + valoracion_especialista + '\'' +
                ", enfermedad='" + enfermedad + '\'' +
                ", cita=" + cita +
                '}';
    }
}


