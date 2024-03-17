package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.QuartoDataSourceLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class QuartoDataSourceImpl implements QuartoDataSourceLocal {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public QuartoDataSourceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean existeQuarto(Long id) {

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://quarto:8081/quartos/{id}",
                String.class,
                id
        );

        return response.getStatusCode() == HttpStatus.OK;

    }

    @Override
    public BigDecimal consultarValorDiaria(Long id) {
        BigDecimal valorDiaria = BigDecimal.ZERO;

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://quarto:8081/quartos/{id}",
                String.class,
                id
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode produtoJsonNode = objectMapper.readTree(response.getBody());
                valorDiaria = produtoJsonNode.get("valorDiaria").decimalValue();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return valorDiaria;
    }
}
