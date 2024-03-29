CREATE SCHEMA SavingMoneyUnina_schema;

SET search_path TO SavingMoneyUnina_schema, public;

CREATE DOMAIN tipo_numero_carta AS CHAR(16)
CONSTRAINT check_numero_carta CHECK (LENGTH(VALUE) = 16);

CREATE DOMAIN tipo_cvv AS VARCHAR(4)
CONSTRAINT check_cvv CHECK (LENGTH(VALUE) = 3 OR LENGTH(VALUE) = 4);

CREATE DOMAIN Enum_tipo_carta AS VARCHAR(7)
CONSTRAINT check_tipo_carta CHECK (VALUE IN ('Debito', 'Credito'));

CREATE DOMAIN Enum_tipo_transazione AS VARCHAR(7)
CONSTRAINT check_tipo_transazione CHECK (VALUE IN ('Entrata', 'Uscita'));

CREATE DOMAIN Enum_categoria AS VARCHAR(13)
CONSTRAINT check_categoria CHECK (VALUE IN ('Svago', 'Spese_mediche', 'Stipendio', 'Bollette'));

CREATE TABLE Famiglia(
	id_famiglia SERIAL,
	nome VARCHAR(20),
	
	CONSTRAINT pk_famiglia PRIMARY KEY (id_famiglia)
);

CREATE TABLE Utente(
	id_utente SERIAL,
	nome_utente VARCHAR(20),
	email VARCHAR(50),
	password VARCHAR(20) NOT NULL,
	nome VARCHAR(20) NOT NULL,
	cognome VARCHAR(20) NOT NULL,
	id_famiglia INT,
	
	CONSTRAINT pk_utente PRIMARY KEY (id_utente),
	CONSTRAINT unique_nome_utente UNIQUE (nome_utente),
	CONSTRAINT unique_email UNIQUE (email),
	CONSTRAINT fk_id_famiglia FOREIGN KEY (id_famiglia) REFERENCES Famiglia (id_famiglia)
	ON UPDATE CASCADE
);

CREATE TABLE Carta (
	numero tipo_numero_carta,
	cvv tipo_cvv NOT NULL,
	scadenza DATE NOT NULL,
	tipo_carta Enum_tipo_carta,
	saldo DECIMAL(10,2),
	id_utente INT NOT NULL,
	
	CONSTRAINT pk_numero PRIMARY KEY (numero),
	CONSTRAINT fk_id_utente FOREIGN KEY (id_utente) REFERENCES Utente (id_utente)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);

CREATE TABLE Gruppo(
	id_gruppo SERIAL,
	categoria Enum_categoria NOT NULL,
	nome VARCHAR(20),
	
	CONSTRAINT pk_gruppo PRIMARY KEY (id_gruppo)
);

CREATE TABLE Transazione(
	id_transazione SERIAL,
	data DATE NOT NULL DEFAULT CURRENT_DATE,
	importo DECIMAL(10,2) NOT NULL,
	tipo_transazione Enum_tipo_transazione,
	numero_carta tipo_numero_carta NOT NULL,
	id_gruppo INT,
	
	CONSTRAINT pk_id_transazione PRIMARY KEY (id_transazione),
	CONSTRAINT fk_numero_carta FOREIGN KEY (numero_carta) REFERENCES Carta (numero),
	CONSTRAINT fk_id_gruppo FOREIGN KEY (id_gruppo) REFERENCES Gruppo (id_gruppo)
	ON UPDATE CASCADE
);