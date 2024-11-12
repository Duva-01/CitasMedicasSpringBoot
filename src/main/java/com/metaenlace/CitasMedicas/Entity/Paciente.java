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
@Table(name = "Paciente", schema = "public")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Paciente extends User {

    @Column(name = "NSS", nullable = false, unique = true)
    private String nss;

    @Column(name = "num_tarjeta", nullable = false)
    private String num_tarjeta;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @ManyToMany(mappedBy = "pacientes")
    private Set<Medico> medicos = new HashSet<>();

}
