package com.metaenlace.CitasMedicas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Medico", schema = "public")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Medico extends User {

    @Column(name = "num_colegiado", nullable = false, unique = true)
    private String num_colegiado;

    @ManyToMany
    @JoinTable(
            name = "Medico_Paciente",
            joinColumns = @JoinColumn(name = "medico_id", referencedColumnName = "num_colegiado"),
            inverseJoinColumns = @JoinColumn(name = "paciente_id", referencedColumnName = "NSS")
    )
    private Set<Paciente> pacientes = new HashSet<>();

}

