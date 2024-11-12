SET search_path TO public;

CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    usuario VARCHAR(100) UNIQUE NOT NULL,
    clave VARCHAR(100) NOT NULL
);

CREATE TABLE Paciente (
    NSS VARCHAR(50) PRIMARY KEY,
    num_tarjeta VARCHAR(50) NOT NULL,
    telefono VARCHAR(50),
    direccion VARCHAR(200),
    usuario_id INT REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Medico (
    num_colegiado VARCHAR(50) PRIMARY KEY,
    usuario_id INT REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Medico_Paciente (
    id SERIAL PRIMARY KEY,
    medico_id VARCHAR(50) REFERENCES Medico(num_colegiado) ON DELETE CASCADE,
    paciente_id VARCHAR(50) REFERENCES Paciente(NSS) ON DELETE CASCADE
);

CREATE TABLE Cita (
    id SERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    motivo_cita VARCHAR(255) NOT NULL,
    paciente_id VARCHAR(50) REFERENCES Paciente(NSS) ON DELETE CASCADE,
    medico_id VARCHAR(50) REFERENCES Medico(num_colegiado) ON DELETE CASCADE
);

CREATE TABLE Diagnostico (
    id SERIAL PRIMARY KEY,
    valoracion_especialista VARCHAR(255),
    enfermedad VARCHAR(255),
    cita_id INT UNIQUE REFERENCES Cita(id) ON DELETE CASCADE
);

INSERT INTO Usuario (nombre, apellidos, usuario, clave)
VALUES ('Pablo', 'Gutierrez', 'pablog1', 'clave123');

INSERT INTO Usuario (nombre, apellidos, usuario, clave)
VALUES ('Alvaro', 'Garcia', 'alvarito34', 'clave123');

SELECT * FROM Usuario;

INSERT INTO Paciente (NSS, num_tarjeta, telefono, direccion, usuario_id)
VALUES ('12345678988', '9876543210', '555-1234', 'Calle Falsa 123, Ciudad', 20);

INSERT INTO Paciente (NSS, num_tarjeta, telefono, direccion, usuario_id)
VALUES ('987654321', '1234567890', '555-5678', 'Avenida Siempre Viva 742', 21);

SELECT * FROM Paciente;

INSERT INTO Usuario (nombre, apellidos, usuario, clave)
VALUES ('Maria', 'Rosales', 'marey1', 'clave123');

INSERT INTO Usuario (nombre, apellidos, usuario, clave)
VALUES ('Carlota', 'Guerrero', 'carlota5', 'clave123');

INSERT INTO Medico (num_colegiado, usuario_id)
VALUES ('12345678', 22);

INSERT INTO Medico (num_colegiado, usuario_id)
VALUES ('87654321', 23);


INSERT INTO Medico_Paciente (medico_id, paciente_id)
VALUES ('12345678', '12345678988');

INSERT INTO Medico_Paciente (medico_id, paciente_id)
VALUES ('12345678', '987654321');

INSERT INTO Medico_Paciente (medico_id, paciente_id)
VALUES ('87654321', '987654321');

SELECT * FROM Medico;

INSERT INTO Cita (fecha_hora, motivo_cita, paciente_id, medico_id)
VALUES ('2024-10-22 10:00:00', 'Consulta general', '123456789', '12345678');

INSERT INTO Cita (fecha_hora, motivo_cita, paciente_id, medico_id)
VALUES ('2024-10-23 11:00:00', 'Chequeo médico', '987654321', '87654321');

SELECT * FROM Cita;

INSERT INTO Diagnostico (valoracion_especialista, enfermedad, cita_id)
VALUES ('Valoración positiva', 'Gripe', 1);

INSERT INTO Diagnostico (valoracion_especialista, enfermedad, cita_id)
VALUES ('Valoración negativa', 'Migraña', 2);

SELECT * FROM Diagnostico;
