DROP TABLE IF EXISTS programare;
DROP TABLE IF EXISTS cabinet;
DROP TABLE IF EXISTS medic;
DROP TABLE IF EXISTS pacient;

CREATE TABLE pacient (
    id_pacient INTEGER PRIMARY KEY,
    nume VARCHAR(50) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    telefon VARCHAR(20),
    email VARCHAR(80),
    data_nasterii VARCHAR(20),
    adresa VARCHAR(100),
    contact_urgenta VARCHAR(20),
    gen VARCHAR(5)
);

CREATE TABLE medic (
    id_medic INTEGER PRIMARY KEY,
    nume VARCHAR(50) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    telefon VARCHAR(20),
    email VARCHAR(80),
    data_nasterii VARCHAR(20),
    adresa VARCHAR(100),
    salariu REAL,
    specializare VARCHAR(50)
);

CREATE TABLE cabinet (
    id_cabinet INTEGER PRIMARY KEY,
    numar_cabinet INTEGER NOT NULL,
    etaj INTEGER,
    capacitate INTEGER
);

CREATE TABLE programare (
    id_programare INTEGER PRIMARY KEY,
    id_pacient INTEGER NOT NULL,
    id_medic INTEGER NOT NULL,
    id_cabinet INTEGER NOT NULL,
    data_ora VARCHAR(30) NOT NULL,
    status VARCHAR(30),
    FOREIGN KEY (id_pacient) REFERENCES pacient(id_pacient),
    FOREIGN KEY (id_medic) REFERENCES medic(id_medic),
    FOREIGN KEY (id_cabinet) REFERENCES cabinet(id_cabinet)
);