# Exemplo de API utilizando consulta a dados em batch e com uso de cache.

## Tecnologias

- Java 11
- Spring Boot
- Spring Web MVC
- Spring Validation
- Spring Data JPA
- Spring Cache
- Hibernate
- MySQL
- Postgres
- Redis
- Swagger
- Maven
- Docker

## Documentação 

- http://localhost:8080/swagger-ui.html

## Executar a aplicação

```shell
docker-compose up -d --build
```

## Parar a aplicação

```shell
docker-compose down
```

## Instruções

- Inserir dados na base de dados legado (Postgres)

```sql
insert into estabelecimentos.tb_estabelecimento(nome, cnpj, cliente)
values ('Mercado', 03782395000110, 'Lucas');
```

- Visualizar logs da aplicacao

```shell
docker logs spring-batch-cache-estabelecimento-1
```

- Dados devem estar no banco de dados principal após a execução da tarefa de batch

```shell
SELECT * from estabelecimento join cliente;
```


## Todo

- ⬜ Inserir somente uma vez o mesmo cliente
- ⬜ Implementar ItemSteam NovoEstabelecimentoItemPollingCleaner para criar checkpoint
- ⬜ Utilizar StoredProcedureItemReader
- ⬜ Tratar inserção com campo único já cadastrado (CNPJ)
- ⬜ Melhorar o Scheduler para permitir reinicio dos jobs
- ⬜ Desabilitar o contexto transacional ativo por padrão nos repositórios
- ⬜ Permitir a configuração do Hibernate para cada DataSource através do arquivo de configuração application.properties