package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ClienteDataSourceLocal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClienteDataSourceImpl implements ClienteDataSourceLocal {

    private final RestTemplate restTemplate;

    public ClienteDataSourceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
