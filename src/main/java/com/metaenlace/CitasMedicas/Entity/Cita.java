package com.metaenlace.CitasMedicas.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Cita", schema = "public")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fecha_hora;

    @Column(name = "motivo_cita", length = 255, nullable = false)
    private String motivo_cita;

    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "NSS", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", referencedColumnName = "num_colegiado", nullable = false)
    private Medico medico;

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)
    private Diagnostico diagnostico;

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fechaHora=" + fecha_hora +
                ", motivo_cita='" + motivo_cita + '\'' +
                ", paciente=" + paciente +
                ", medico=" + medico +
                '}';
    }
}
