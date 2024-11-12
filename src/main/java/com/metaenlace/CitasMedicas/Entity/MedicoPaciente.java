package com.metaenlace.CitasMedicas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Medico_Paciente", schema = "public")
public class MedicoPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "medico_id", referencedColumnName = "num_colegiado")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "NSS")
    private Paciente paciente;
}
