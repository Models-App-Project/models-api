# Models API

A Models API é uma aplicação RESTful desenvolvida com Spring Boot e JPA que permite cadastrar, consultar, atualizar e deletar informações sobre modelos. A API gerencia informações como nome, idade, altura, cor dos olhos, peso, cor do cabelo, cintura, quadril, busto e uma descrição da modelo.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

## Funcionalidades

- Cadastro de novas modelos
- Consulta de modelo por ID
- Listagem de todas as modelos
- Atualizaão de informações da modelo
- Remoção de modelo

## Pré-requisitos

Antes de começar, você precisará ter o seguinte instalado em sua máquina:

- JDK 17+
- Maven 3.6+
- Banco de dados PostgreSQL ou outro compatível com JPA (Para testes do deploy não necessário, pois está instânciado em núvem)

## Instalação

### 1. Clonar o repositório

``` bash
git clone https://github.com/seu-usuario/models-api.git
cd models-api
```

### 2. Configurar as variáveis de ambiente

Crie um arquivo *application.properties* ou configure variáveis de ambiente com as credenciais do banco de dados.

Exemplo de *application.properties*:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/modelsdb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Rodar o projeto

Execute o comando abaixo para compilar e iniciar a aplicação:

``` bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará dispinível em: [http://localhost:8080](http://localhost:8080)

## Endpoints

## Admin Controller Endpoints

### 1. Obter Administrador Logado

- **URL:** /adminUsers/getAdminLogged
- **Método:** GET

- **Descrição:** Retorna os dados do administrador logado atualmente.

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
    "id": 1,
    "username": "admin1",
    "email": "admin1@example.com",
    "roles": ["ROLE_ADMIN"]
}
````````


### 2. Obter Todos os Administradores

- **URL:** /adminUsers/getAdmin
- **Método:** GET

- **Descrição:** Retorna uma lista de todos os administradores registrados no sistema.

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
[
    {
        "id": 1,
        "username": "admin1",
        "email": "admin1@example.com"
    },
    {
        "id": 2,
        "username": "admin2",
        "email": "admin2@example.com"
    }
]
````````
- **Resposta de erro:**
  - **Status:** `204 NO COTENT`
  - **Body (JSON):**

```json
{
    "error": "No administrators found."
}
````````
]

### 3. Obter Todas as Modelos Registradas

- **URL:** /adminUsers/getAllModelsRegistred
- **Método:** GET

- **Descrição:** Retorna uma lista de todas as modelos registradas no sistema. Os filtros podem ser aplicados para buscar modelos específicas.

- **Parâmetros de corpo (Request Body):**

```json
{
    "name": "Alice",
    "age": 25
}
````````
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
[
    {
        "id": 1,
        "name": "Alice",
        "age": 25,
        "height": 1.75,
        "eyesColor": "Blue",
        "hairColor": "Blonde"
    },
    {
        "id": 2,
        "name": "Sophia",
        "age": 23,
        "height": 1.70,
        "eyesColor": "Green",
        "hairColor": "Brown"
    }
]
````````
### 4. Obter Modelo Registrado

- **URL:** /adminUsers/getModelRegistred
- **Método:** GET

- **Descrição:** Retorna os detalhes de uma modelo específica com base no ID ou filtros fornecidos.

- **Parâmetros de corpo (Request Body):**

```json
{
    "id": 1
}
````````
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
    "id": 1,
    "name": "Alice",
    "age": 25,
    "height": 1.75,
    "eyesColor": "Blue",
    "hairColor": "Blonde"
}
````````
### 5. Criar um Novo Administrador

- **URL:** /adminUsers/saveAdminUser
- **Método:** POST

- **Descrição:** Cria um novo administrador no sistema.

- **Parâmetros de corpo (Request Body):**

```json
{
    "username": "newAdmin",
    "email": "newAdmin@example.com",
    "password": "securePassword123"
}
````````
 - **Resposta de sucesso:**
  - **Status:** `201 CREATED`
  - **Body (JSON):**

```json
{
    "message": "Novo administrador criado: newAdmin"
}
````````
### 6. Atualizar um Administrador

- **URL:** /adminUsers/updateAdmin
- **Método:** PUT

- **Descrição:** Atualiza os dados de um administrador.

- **Parâmetros de corpo (Request Body):**

```json
{
    "id": 1,
    "username": "updatedAdmin",
    "email": "updatedAdmin@example.com"
}
````````
 - **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
    "message": "Administrador atualizado: updatedAdmin"
}
````````

### 7. Atualizar os Dados de uma Modelo

- **URL:** /adminUsers/updateModel
- **Método:** PUT

- **Descrição:** Atualiza os dados de uma modelo registrada, incluindo seu portfólio.

- **Parâmetros de corpo (Request Body):**

```json
{
    "id": 1,
    "name": "Alice Updated",
    "description": "Updated description",
    "portfolio": ["photo1.jpg", "photo2.jpg"]
}
````````
 - **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
    "message": "Modelo atualizado: Alice Updated"
}
````````
### 8. Excluir um Administrador

- **URL:** /adminUsers/deleteAdmin
- **Método:** DELETE

- **Descrição:** Remove um administrador do sistema.

- **Parâmetros de corpo (Request Body):**

```json
{
    "id": 1
}
````````
 - **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
  "message": "Administrador excluído com sucesso."
}

````````

### Códigos de Resposta Comuns

-   200 OK: Requisição processada com sucesso.
    
-   201 Created: Novo recurso criado com sucesso.
    
-   204 No Content: Nenhum conteúdo encontrado.
    
-   500 Internal Server Error: Ocorreu um erro inesperado no servidor.
    
## User Controller Endpoints

### 1. Criar um Novo Usuário

- **URL:** /users/create
- **Método:** POST

- **Descrição:** Adiciona um novo usuário ao sistema. Apenas administradores podem executar essa ação.

- **Parâmetros de corpo (Request Body):**

```json
{
    "username": "newUser",
    "email": "newUser@example.com",
    "password": "securePassword123"
}
````````
 - **Resposta de sucesso:**
  - **Status:** `201 CREATED`
  - **Body (JSON):**

```json
{
    "message": "Novo usuário criado: newUser"
}

````````
 - **Resposta de erro:**
  - **Status:** `429 Too Many Requests`
  - **Body (JSON):**

```json
{
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}

````````

### 2. Atualizar Usuário

- **URL:** /users/update
- **Método:** PUT

- **Descrição:** Atualiza os dados de um usuário existente no sistema. Apenas administradores e sub administradores têm permissão para essa ação.

- **Parâmetros de corpo (Request Body):**

```json
{
  "message": "Usuário atualizado: updatedUser"
}
````````
Permissões Necessárias:

Role: ADMINISTRADOR, SUB_ADMINISTRADOR
    
 - **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
[
  {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
  },
  {
    "id": 2,
    "username": "user2",
    "email": "user2@example.com"
  }
]


````````
 - **Resposta de erro:**
  - **Status:** `429 Too Many Requests`
  - **Body (JSON):**

```json
{
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}

````````
### 3. Obter Todos os Usuários

- **URL:** /users/getAllUsers
- **Método:** GET

- **Descrição:** Retorna uma lista de todos os usuários registrados no sistema.

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
[
    {
        "id": 1,
        "username": "user1",
        "email": "user1@example.com"
    },
    {
        "id": 2,
        "username": "user2",
        "email": "user2@example.com"
    }
]

````````
 - **Resposta de erro:**
  - **Status:** `429 Too Many Requests`
  - **Body (JSON):**

```json
{
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}

````````
### 4. Obter Usuário por ID

- **URL:** /users/getUser{id}
- **Método:** GET

- **Descrição:** Retorna os dados de um usuário específico com base no ID.

- **Parâmetros da URL:**
  - **id:** O identificador único do usuário.
  - Exemplo: `/users/1`
    
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
{
    "id": 1,
    "username": "user1",
    "email": "user1@example.com"
}
````````


### 5. Deletar Usuário

- **URL:** /users/deleteUser
- **Método:** DELETE

- **Descrição:** Remove um usuário do sistema. Apenas administradores têm permissão para essa ação.

- **Parâmetros de Corpo (Request Body):**
  - **JSON:**
  ```json
  {
      "id": 1
  }
  
Permissões Necessárias:
Role: ADMINISTRADOR

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
    {
    "message": "Usuário excluído com sucesso."
}
````````

- **Resposta de erro:**
  - **Status:** `429 Too Many Requests`
  - **Body (JSON):**

```json
{
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}
````````

### Códigos de Resposta Comuns

-   200 OK: Requisição processada com sucesso.
    
-   201 Created: Novo recurso criado com sucesso.
    
-   429 Too Many Requests: Limite de requisições excedido.
    
-   500 Internal Server Error: Ocorreu um erro inesperado no servidor.

## Model Controller Endpoints

### 1. Adicionar Nova Modelo

- **URL:** /models/add
- **Método:** POST

- **Descrição:** Adiciona uma nova modelo ao sistema. Apenas administradores e sub administradores têm permissão para essa ação.

- **Parâmetros de Corpo (Request Body):**
  - **JSON:**
  ```json
  {
      "name": "Jane Doe",
      "age": 25,
      "height": 175,
      "eyeColor": "Blue",
      "hairColor": "Blonde"
  }     
    
-   Permissões Necessárias:
    

-   Role: ADMINISTRADOR, SUB_ADMINISTRADOR
    

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
    {
    "message": "Usuário excluído com sucesso."
}
````````   
    

- **Resposta de erro:**
  - **Status:** `249 Too Many Requests`
  - **Body (JSON):**

```json
   {
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}
````````   

### 2. Buscar Modelo por ID

- **URL:** /models/getModels{id}
- **Método:** GET

- **Descrição:** Obtém os dados de uma modelo específica com base no ID.

- **Parâmetros da URL:**
  - **id:** O identificador único da modelo.  
    Exemplo: `/models/550e8400-e29b-41d4-a716-446655440000`

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

  ```json
  {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Jane Doe",
      "age": 25,
      "height": 175,
      "eyeColor": "Blue",
      "hairColor": "Blonde"
  }

- **Resposta de erro:**
  - **Status:** `404 NOT FOUND`
  - **Body (JSON):**

```json
   {
    "error": "Modelo não encontrado."
}
````````

----------

### 3. Obter Todas as Modelos

- **URL:** /models/getModels/findAll
- **Método:** GET

- **Descrição:** Retorna uma lista com todas as modelos registradas no sistema.

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

  ```json
  [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Jane Doe",
      "age": 25,
      "height": 175,
      "eyeColor": "Blue",
      "hairColor": "Blonde"
    },
    {
      "id": "650e8400-e29b-41d4-a716-446655440001",
      "name": "Emma Smith",
      "age": 28,
      "height": 168,
      "eyeColor": "Green",
      "hairColor": "Brown"
    }
  ]

- **Resposta de erro:**
  - **Status:** `249 Too Many Requests`
  - **Body (JSON):**

```json
   {
    "error": "Limite de requisições atingido. Tente novamente mais tarde."
}
```````` 

### 4. Buscar Modelo por Nome

- **URL:** /models/findByName
- **Método:** GET

- **Descrição:** Obtém os dados de uma modelo específica com base no nome.

- **Parâmetros da Query:**
  - `name`: O nome da modelo. Exemplo: `/models/findByName?name=Jane%20Doe`

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

  ```json
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Jane Doe",
    "age": 25,
    "height": 175,
    "eyeColor": "Blue",
    "hairColor": "Blonde"
  }     
    

- **Resposta de erro:**
  - **Status:** `404 NOT FOUND`
  - **Body (JSON):**

```json
   {
    "error": "Modelo não encontrado."
}
````````
### 5. Deletar Modelo por ID

- **URL:** /models/deleteModel{id}
- **Método:** DELETE

- **Descrição:** Remove uma modelo do sistema com base no ID. Apenas administradores e sub administradores têm permissão para essa ação.

- **Parâmetros da URL:**
  - `id`: O identificador único da modelo. Exemplo: `/models/550e8400-e29b-41d4-a716-446655440000`

- **Permissões Necessárias:**
  - **Role:** ADMINISTRADOR, SUB_ADMINISTRADOR

- **Resposta de sucesso:**
  - **Status:** `204 No Content`
  - **Body:** Nenhum conteúdo retornado.

- **Resposta de erro (404 Not Found):**
  - **Status:** `404 Not Found`
  - **Body (JSON):**

  ```json
  {
    "error": "Modelo não encontrado."
  }

### 6. Deletar Modelo por Nome

- **URL:** /models/deleteByName
- **Método:** DELETE

- **Descrição:** Remove uma modelo do sistema com base no nome. Apenas administradores e sub administradores têm permissão para essa ação.

- **Parâmetros da Query:**
  - `name`: O nome da modelo. Exemplo: `/models/deleteByName?name=Jane%20Doe`

- **Permissões Necessárias:**
  - **Role:** ADMINISTRADOR, SUB_ADMINISTRADOR

- **Resposta de sucesso:**
  - **Status:** `204 No Content`
  - **Body:** Nenhum conteúdo retornado.

- **Resposta de erro (404 Not Found):**
  - **Status:** `404 Not Found`
  - **Body (JSON):**

  ```json
  {
    "error": "Modelo não encontrada."
  }
----------

### Códigos de Resposta Comuns

-   200 OK: Requisição processada com sucesso.
    
-   204 No Content: Recurso deletado com sucesso.
    
-   404 Not Found: Recurso não encontrado.
    
-   429 Too Many Requests: Limite de requisições excedido.
    
-   500 Internal Server Error: Ocorreu um erro inesperado no servidor.
    

  

## Requests Controller Endpoints

### 1. Obter Todas as Requisições

- **URL:** /requests/getAllRequests
- **Método:** GET

- **Descrição:** Retorna uma lista com todas as requisições feitas pelas modelos.

- **Resposta de Sucesso (200 OK):**
  - **Status:** `200 OK`
  - **Body (JSON):**

  ```json
  [
      {
          "id": "550e8400-e29b-41d4-a716-446655440000",
          "modelId": "650e8400-e29b-41d4-a716-446655440001",
          "status": "PENDING",
          "submissionDate": "2024-11-17T10:00:00"
      },
      {
          "id": "650e8400-e29b-41d4-a716-446655440001",
          "modelId": "750e8400-e29b-41d4-a716-446655440002",
          "status": "APPROVED",
          "submissionDate": "2024-11-15T12:00:00"
      }
  ]

- **Resposta de erro:**
  - **Status:** `404 NOT FOUND`
  - **Body (JSON):**

```json
   {
    "error": "Modelo não encontrado."
}
````````
### 2. Obter Requisições por Filtro

- **URL:** /requests/getRequestsByFilter
- **Método:** GET

- **Descrição:** Retorna uma lista de requisições que correspondem aos filtros aplicados.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
    "model": {
        "name": "Jane Doe",
        "eyeColor": "Blue",
        "hairColor": "Blonde"
    },
    "startDate": "2024-11-01T00:00:00",
    "endDate": "2024-11-17T23:59:59",
    "status": "PENDING"
  }

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
   [
    {
        "id": "550e8400-e29b-41d4-a716-446655440000",
        "modelId": "650e8400-e29b-41d4-a716-446655440001",
        "status": "PENDING",
        "submissionDate": "2024-11-10T15:30:00"
    }
]
````````   
    

- **Resposta de erro:**
  - **Status:** `404 NOT FOUND`
  - **Body (JSON):**

```json
   {
    "error": "Modelo não encontrado."
}
````````

### 3. Buscar Requisição por ID

- **URL:** /requests/getRequestById
- **Método:** GET

- **Descrição:** Retorna os detalhes de uma requisição específica com base no ID.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
    "requestID": "550e8400-e29b-41d4-a716-446655440000"
  }

- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "modelId": "650e8400-e29b-41d4-a716-446655440001",
    "status": "PENDING",
    "submissionDate": "2024-11-10T15:30:00"
}
````````   
### 4. Obter Detalhes de Requisição com Fotos

- **URL:** /requests/getModelRequest
- **Método:** GET

- **Descrição:** Retorna um mapa contendo os detalhes de uma modelo e as URLs das fotos enviadas.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
    "request": {
        "id": "550e8400-e29b-41d4-a716-446655440000"
    }
  }
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
 
    {
        "modelId": "650e8400-e29b-41d4-a716-446655440001",
        "photos": [
            "https://example.com/photo1.jpg",
            "https://example.com/photo2.jpg"
        ]
    }
```````` 

### 5. Salvar Nova Requisição

- **URL:** /requests/saveRequest
- **Método:** POST

- **Descrição:** Adiciona uma nova requisição ao sistema.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
      "modelId": "650e8400-e29b-41d4-a716-446655440001",
      "status": "PENDING",
      "submissionDate": "2024-11-17T15:30:00"
  }

- **Resposta de sucesso:**
  - **Status:** `201 CREATED`
  - **Body (JSON):**

```json
 
    {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "modelId": "650e8400-e29b-41d4-a716-446655440001",
    "status": "PENDING",
    "submissionDate": "2024-11-17T15:30:00"
}
```````` 

### 6. Atualizar Requisição

- **URL:** /requests/updateRequest
- **Método:** PUT

- **Descrição:** Atualiza os dados de uma requisição existente.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
      "requestID": "550e8400-e29b-41d4-a716-446655440000",
      "request": {
          "status": "APPROVED"
      }
  }
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
 
   {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "modelId": "650e8400-e29b-41d4-a716-446655440001",
    "status": "APPROVED",
    "submissionDate": "2024-11-17T15:30:00"
}
```````` 

### 7. Deletar Requisição por ID

- **URL:** /requests/deleteRequest
- **Método:** DELETE

- **Descrição:** Remove uma requisição do sistema com base no ID.

- **Parâmetros do Corpo (Request Body):**

  ```json
  {
      "requestID": "550e8400-e29b-41d4-a716-446655440000"
  }

  
- **Resposta de sucesso:**
  - **Status:** `200 OK`
  - **Body (JSON):**

```json
 
  {
    "message": "Requisição deletada com sucesso."
}
```````` 
### 8. Deletar Todas as Requisições

- **URL:** /requests/deleteAllRequests
- **Método:** DELETE

- **Descrição:** Remove todas as requisições do sistema.

- **Resposta de Sucesso (200 OK):**
  - **Status:** `200 OK`
  - **Body (JSON):**

  ```json
  {
      "message": "Todas as requisições foram deletadas."
  }
  
  
  

----------

### Códigos de Resposta Comuns

-   200 OK: Requisição processada com sucesso.
    
-   201 Created: Recurso criado com sucesso.
    
-   404 Not Found: Recurso não encontrado.
    
-   500 Internal Server Error: Ocorreu um erro inesperado no servidor.

# Licença

Licença MIT
Copyright (c) 2024 [ModelsAPI]
