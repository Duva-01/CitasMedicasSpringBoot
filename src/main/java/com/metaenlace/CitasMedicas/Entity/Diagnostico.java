package com.metaenlace.CitasMedicas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Diagnostico", schema = "public")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "valoracion_especialista", nullable = true, length = 255)
    private String valoracion_especialista;

    @Column(name = "enfermedad", nullable = true, length = 255)
    private String enfermedad;

    @OneToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id", nullable = false, unique = true)
    private Cita cita;

    @Override
    public String toString() {
        return "Diagnostico{" +
                "id=" + id +
                ", valoracion_especialista='" + valoracion_especialista + '\'' +
                ", enfermedad='" + enfermedad + '\'' +
                ", cita=" + cita +
                '}';
    }
}

