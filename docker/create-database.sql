CREATE DATABASE IF NOT EXISTS estabelecimento;

CREATE TABLE IF NOT EXISTS `estabelecimento`.`estabelecimento` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `uuid` BINARY(16) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `cnpj` VARCHAR(45) NOT NULL,
  `criado_em` DATETIME(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE,
  UNIQUE INDEX `cnpj_UNIQUE` (`cnpj` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `estabelecimento`.`cliente` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `uuid` BINARY(16) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `estabelecimento_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE,
  FOREIGN KEY (`estabelecimento_id`) REFERENCES `estabelecimento`.`estabelecimento` (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;