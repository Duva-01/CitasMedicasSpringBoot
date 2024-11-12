package com.metaenlace.CitasMedicas.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteDTO {

    private int id;

    private String nombre;
    private String apellidos;

    private String usuario;
    private String clave;

    private String nss;
    private String num_tarjeta;
    private String telefono;
    private String direccion;

    @Override
    public String toString() {
        return "PacienteDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", NSS='" + nss + '\'' +
                ", num_tarjeta='" + num_tarjeta + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
