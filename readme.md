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

### Cadastar uma nova modelo

- **URL:** /models/add
- **Método:** POST
- **Body (JSON):**

``` json
{
    "name": "Alice",
    "age": 25,
    "height": 1.75,
    "eyesColor": "Blue",
    "weight": 60.5,
    "hairColor": "Blonde",
    "waistline": 65.0,
    "description": "Professional model",
    "hip": 90.0,
    "bust": 85.0
}
```

- **Resposta de sucesso:**
  - **Status:** ```200 OK```
  - **Body (JSON):** 

``` json
{
    "id": 1,
    "name": "Alice",
    "age": 25,
    "height": 1.75,
    "eyesColor": "Blue",
    "weight": 60.5,
    "hairColor": "Blonde",
    "waistline": 65.0,
    "description": "Professional model",
    "hip": 90.0,
    "bust": 85.0
}
```

### Busca modelo por ID

- **URL:**/models/{id}
- **Método:** GET
- **Parâmetros:**
  - ```id```: O ID da modelo (por exemplo: ```/models/1```)
- **Resposta de sucesso:**
  - **Status:** ```200 OK```

``` json
{
    "id": 1,
    "name": "Alice",
    "age": 25,
    "height": 1.75,
    "eyesColor": "Blue",
    "weight": 60.5,
    "hairColor": "Blonde",
    "waistline": 65.0,
    "description": "Professional model",
    "hip": 90.0,
    "bust": 85.0
}
```

- **Erro de modelo não encontrada:**
  - **Status:** ```404 Not Found```

### Listar todas as modelos

- **URL:** ```/models/findAll```
- **Método:** ```GET```
- **Resposta de sucesso:**
  - **Status:** ```200 OK```
  - **Body: (JSON):**
  
``` json
[
  {
      "id": 1,
      "name": "Alice",
      "age": 25,
      "height": 1.75,
      "eyesColor": "Blue",
      "weight": 60.5,
      "hairColor": "Blonde",
      "waistline": 65.0,
      "description": "Professional model",
      "hip": 90.0,
      "bust": 85.0
  },
  {
      "id": 2,
      "name": "Sophia",
      "age": 23,
      "height": 1.70,
      "eyesColor": "Green",
      "weight": 55.0,
      "hairColor": "Brown",
      "waistline": 60.0,
      "description": "Fashion model",
      "hip": 88.0,
      "bust": 83.0
  }
]
```

