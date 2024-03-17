# Gestão de Reserva

## O que é este projeto?

Este é um projeto do Hackaton da Postech/FIAP de Arquitetura de Desenvolvimento Java.
Esse microsserviço é referente a Gestão de Reservas da solução para um Sistema de
Hospitalidade.

## O que eu devo configurar?

Este projeto implementa uma API Rest com Spring Boot, empacotado
em um container Docker.
Na máquina é preciso ter instalado:

* Docker
* Docker Compose

## Como executar este projeto?

Este projeto foi construído com docker e pode ser executado através do comando
`docker-compose up` no diretório que contenha o arquivo `docker-compose.yaml`
com o conteúdo abaixo:

```yaml
version: "3"
services:
  reserva:
    image: joaogabrielcampos/gestao-reserva:latest
    ports:
      - "8082:8082"
    networks:
      - hospitalidade
    depends_on:
      - customer
      - options
      - quarto

  customer:
    image: joaogabrielcampos/customer-management:latest
    ports:
      - "8083:8083"
    networks:
      - hospitalidade

  options:
    image: joaogabrielcampos/service-and-options-management:latest
    ports:
      - "8084:8084"
    networks:
      - hospitalidade

  quarto:
    image: joaogabrielcampos/gestao-quarto:latest
    ports:
      - "8081:8081"
    networks:
      - hospitalidade

networks:
  hospitalidade:
```

Esse arquivo inicializa os microsserviços de

- [Gestão de Reserva](https://github.com/jgcamposneto/ms-gestao-reservas)
- [Gestão de Quarto](https://github.com/jgcamposneto/ms-gestao-quartos)
- [Gestão de Cliente](https://github.com/iagoomes/customer-management)
- [Gestão de Serviços e Opcionais](https://github.com/iagoomes/service-and-options-management)

## Como testar o projeto?

### Primeiro devemos cadastrar o cliente.

_O ID é retornado no Header - location_

#### POST /v1/customers

```bash
curl -X POST --location "http://localhost:8083/v1/customers" \
    -H "Content-Type: application/json" \
    -d '{
          "fullName": "Jhon Doe",
          "countryOfBirth": "Brazil",
          "cpf": "724.564.650-32",
          "passport": "ABDE123456",
          "dateOfBirth": "2023-01-01T00:00:00",
          "addressInCountryOfBirth": "Rua ABC, 123",
          "phone": "1234567890",
          "email": "john.doe@example.com"
        }'
```

### Segundo passo devemos cadastrar o quarto

#### POST /quartos

```bash
curl -X POST --location "http://localhost:8081/quartos" \
    -H "Content-Type: application/json" \
    -d '{
          "idPredio": 1,
          "tipoQuartoEnumRequestModel": "STANDARD_SIMPLES"
        }'
```

### Consulta Ocupações

_pra quando já tem reservas cadastradas_

#### GET /quartos/{{id}}

```bash
curl -X GET --location "http://localhost:8081/quartos/{{id}}"
```

### Terceiro passo é incluir a reserva

```bash
curl -X POST --location "http://localhost:8082/reservas" \
    -H "Content-Type: application/json" \
    -d '{
          "idCliente": 1,
          "entrada": "2024-04-01",
          "saida": "2024-04-05",
          "totalPessoas": 2,
          "idQuartos": [1],
          "opcionais": [
            {
              "id": "3"
              "quantidade": 5
            }
          ]
        }'
```

### Quarto passo é confirmar reserva

_Processo realizado atráves de envio de email_

```bash
curl -X PATCH --location "http://localhost:8082/reservas/1" \
    -H "Content-Type: application/x-www-form-urlencoded"
```
