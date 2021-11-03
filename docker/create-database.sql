CREATE DATABASE IF NOT EXISTS estabelecimento;

CREATE TABLE IF NOT EXISTS `estabelecimento`.`estabelecimento` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `uuid` BINARY(255) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `cnpj` VARCHAR(45) NOT NULL,
  `criado_em` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;