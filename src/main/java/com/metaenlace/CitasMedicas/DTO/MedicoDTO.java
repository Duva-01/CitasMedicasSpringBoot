package com.metaenlace.CitasMedicas.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicoDTO {

    private int id;

    private String nombre;
    private String apellidos;

    private String usuario;
    private String clave;

    private String num_colegiado;

    @Override
    public String toString() {
        return "MedicoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", num_colegiado='" + num_colegiado + '\'' +
                '}';
    }
}
