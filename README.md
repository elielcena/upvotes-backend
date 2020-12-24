<h3 align="center">
  ♥️ Segware UpVotes Backend
</h3>

## :bulb: Sobre o projeto
Aplicação onde textos possam ser postados de forma livre e, estes textos, possam ter “upvotes”.

## 🚀 Tecnologias

- [Java 8](https://www.java.com/pt-BR/download/help/java8.html)
- [Spring](https://spring.io/)

### Pré-Requisitos

- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

## 💻 Iniciando

**É necessario configurar (url, usuario e senha) do banco de dados Postgres em /src/main/resources/application.properties**

**Clone o projeto e acesse o diretório /upvotes-frontend e inicie o projeto, conforme o comando abaixo**

```bash
$ git clone https://github.com/elielcena/upvotes-backend.git && cd "upvotes-backend" && mvn clean && mvn package && java -jar target/post-upvotes-1.0.0.jar
```

**Testes**
```bash
$ mvn test
```

## :page_with_curl: Rotas
- **FIND ALL BY DATA** 
  - `GET` `http://localhost:8080/api/v1/posts?data=2020-12-23`
  - `200` RESPONSE CODE
- **CREATE POST** 
  - `POST` `http://localhost:8080/api/v1/posts`
  - `201` RESPONSE CODE
  - Body
  ```json
    {
      "titulo": "Titulo da postagem",
      "descricao": "Conteúdo da postagem <br/> Spring Boot <br/>",
      "username": "elielcena",
      "upvotes": 1
    }
  ```
- **UP VOTE BY ID POST** 
  - `PUT` `http://localhost:8080/api/v1/posts/upVote`
  - `200` RESPONSE CODE
  - Body
  ```json
    {
        "idpost": 1,
        "operacao": "ADD"
    }
  ```
