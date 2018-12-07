CREATE DATABASE sensor_logger_db;
CREATE SCHEMA sensor_logger;

CREATE TABLE sensor_logger.tb_locais(
  id_local SERIAL,
  nome VARCHAR(40) NOT NULL,
  cidade VARCHAR(40) NOT NULL,
  estado VARCHAR(2) NOT NULL,
  clima VARCHAR(40),
  PRIMARY KEY (id_local)
);

CREATE TABLE sensor_logger.tb_usuarios(
  id_pessoa SERIAL,
  username VARCHAR(20),
  password VARCHAR(255) NOT NULL,
  nome VARCHAR(40) NOT NULL,
  idade INTEGER NOT NULL,
  curso VARCHAR(40),
  privileges VARCHAR(20),
  PRIMARY KEY (username)
);

CREATE TABLE sensor_logger.tb_medidas(
  id_medida SERIAL,
  temp_ar NUMERIC(4,2),
  umid_ar NUMERIC(4,2),
  umid_solo NUMERIC(4,2),
  luminosidade NUMERIC(4,2),
  id_pessoa INTEGER,
  id_local INTEGER,
  PRIMARY KEY (id_medida),
  FOREIGN KEY (id_pessoa) REFERENCES sensor_logger.tb_usuarios (id_pessoa),
  FOREIGN KEY (id_local) REFERENCES sensor_logger.tb_locais (id_local)
);

CREATE TABLE sensor_logger.tb_log(
  id_log SERIAL,
  horario TIMESTAMP NOT NULL,
  operacao VARCHAR(40) NOT NULL,
  id_pessoa INTEGER,
  id_medida INTEGER,
  id_local INTEGER,
  PRIMARY KEY (id_log)
);
