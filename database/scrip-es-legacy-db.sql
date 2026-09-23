-- CREAR BBDD.
CREATE DATABASE IF NOT EXISTS es_legacy_db;
USE es_legacy_db;

-- Tabla jugador.
CREATE TABLE Jugador (
    NombreUsuario VARCHAR(50) PRIMARY KEY,
    Contrasena VARCHAR(255) NOT NULL,
    NombreCompleto VARCHAR(200),
    Correo VARCHAR(150)
);

-- Tabla personaje.
CREATE TABLE Personaje (
    idPersonaje INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Clase VARCHAR(50),
    Rareza VARCHAR(5),
    Historia TEXT,
    AtaqueBasico VARCHAR(3),
    PuntosVida INT,
    Iniciativa VARCHAR(3),
    Origen VARCHAR(300),
    Arquetipo VARCHAR(300)
);

-- Tabla habilidad.
CREATE TABLE Habilidad (
    idHabilidad INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Categoria VARCHAR(50),
    Cooldown INT,
    Descripcion TEXT
);

-- Tabla enemigo.
CREATE TABLE Enemigo (
    idEnemigo INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    EjemplosHabilidades TEXT
);

-- Tabla objeto.
CREATE TABLE Objeto (
    idObjeto INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    Categoria VARCHAR(50),
    Precio INT
);

-- Tablas intermedias.
-- Tabla Personaje-Habilidad
CREATE TABLE Personaje_Habilidad (
    idPersonaje INT,
    idHabilidad INT,
    PRIMARY KEY (idPersonaje, idHabilidad),
    FOREIGN KEY (idPersonaje) REFERENCES Personaje(idPersonaje)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idHabilidad) REFERENCES Habilidad(idHabilidad)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Personaje-Objeto
CREATE TABLE Personaje_Objeto (
    idPersonaje INT,
    idObjeto INT,
    cantidad INT,
    equipado TINYINT(1),
    PRIMARY KEY (idPersonaje, idObjeto),
    FOREIGN KEY (idPersonaje) REFERENCES Personaje(idPersonaje)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idObjeto) REFERENCES Objeto(idObjeto)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Enemigo-Objeto
CREATE TABLE Enemigo_Objeto (
    idEnemigo INT,
    idObjeto INT,
    PRIMARY KEY (idEnemigo, idObjeto),
    FOREIGN KEY (idEnemigo) REFERENCES Enemigo(idEnemigo)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idObjeto) REFERENCES Objeto(idObjeto)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Personaje-Jugador
CREATE TABLE Personaje_Jugador (
    idPersonaje INT,
    NombreUsuario VARCHAR(50),
    PRIMARY KEY (idPersonaje, NombreUsuario),
    FOREIGN KEY (idPersonaje) REFERENCES Personaje(idPersonaje)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (NombreUsuario) REFERENCES Jugador(NombreUsuario)
        ON DELETE CASCADE ON UPDATE CASCADE
);
