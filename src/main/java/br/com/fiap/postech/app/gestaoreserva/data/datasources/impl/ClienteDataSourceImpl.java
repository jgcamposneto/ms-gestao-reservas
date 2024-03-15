package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ClienteDataSourceLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClienteDataSourceImpl implements ClienteDataSourceLocal {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ClienteDataSourceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean existeCliente(Long id) {

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:8083/v1/customers/{id}",
                String.class,
                id
        );

        return response.getStatusCode() == HttpStatus.OK;

    }
}
