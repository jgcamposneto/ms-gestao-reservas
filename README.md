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

As urls dos endpoints disponíveis para acesso são as abaixo: