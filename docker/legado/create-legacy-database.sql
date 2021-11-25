BEGIN;

CREATE SCHEMA estabelecimentos;

CREATE TABLE IF NOT EXISTS estabelecimentos.tb_estabelecimento(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    nome text,
    cnpj bigint NOT NULL,
    cliente text,
    PRIMARY KEY (id)
);

-- polling
CREATE TABLE IF NOT EXISTS estabelecimentos.novo_estabelecimento(
    id      integer NOT NULL,
    nome    text,
    cnpj    bigint,
    cliente text
);

CREATE TYPE estabelecimentos.estabelecimento_type AS (
    id      integer,
    nome    text,
    cnpj    bigint,
    cliente text
);


CREATE OR REPLACE FUNCTION estabelecimentos.busca_estabelecimentos()
RETURNS SETOF estabelecimentos.novo_estabelecimento
AS $$
BEGIN
	RETURN QUERY
    SELECT id, nome, cnpj, cliente
    FROM   estabelecimentos.novo_estabelecimento
    LIMIT  2;
END $$
LANGUAGE PLPGSQL;


-- insere na tabela de polling
CREATE OR REPLACE FUNCTION estabelecimentos.novo_estabelecimento_criado()
RETURNS TRIGGER
AS $$
BEGIN
   INSERT INTO estabelecimentos.novo_estabelecimento (id, nome, cnpj, cliente)
   VALUES (NEW.id, UPPER(NEW.nome), NEW.cnpj, UPPER(NEW.cliente));

   RETURN NEW;
END $$
LANGUAGE PLPGSQL;


-- a cada estabelecimento criado
CREATE OR REPLACE TRIGGER notifica_novo_estabelecimento_criado
   AFTER INSERT
   ON estabelecimentos.tb_estabelecimento
   FOR EACH ROW
       EXECUTE PROCEDURE estabelecimentos.novo_estabelecimento_criado();

END;