package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.OpcionalDataSourceLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class OpcionalDataSourceImpl implements OpcionalDataSourceLocal {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpcionalDataSourceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean existeOpcional(Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://options:8084/v1/services_option/{id}",
                String.class,
                id
        );

        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public BigDecimal consultarValor(Long id) {
        BigDecimal valor = BigDecimal.ZERO;

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://options:8084/v1/services_option/{id}",
                String.class,
                id
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode produtoJsonNode = objectMapper.readTree(response.getBody());
                valor = produtoJsonNode.get("pricePerPerson").decimalValue();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return valor;
    }
}
