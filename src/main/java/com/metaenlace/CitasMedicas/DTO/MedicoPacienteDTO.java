package com.metaenlace.CitasMedicas.DTO;

import com.metaenlace.CitasMedicas.Entity.Medico;
import com.metaenlace.CitasMedicas.Entity.Paciente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicoPacienteDTO {

    private int id;
    private MedicoDTO medico;
    private PacienteDTO paciente;
}
