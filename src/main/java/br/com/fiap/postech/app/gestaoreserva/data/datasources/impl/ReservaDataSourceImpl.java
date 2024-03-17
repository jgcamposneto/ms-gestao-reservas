package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.JpaReservaRepository;
import br.com.fiap.postech.app.gestaoreserva.data.datasources.ReservaDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;
import br.com.fiap.postech.app.gestaoreserva.data.models.StatusEnumModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ReservaDataSourceImpl implements ReservaDataSourceLocal {

    final JpaReservaRepository jpaReservaRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ReservaDataSourceImpl(JpaReservaRepository jpaReservaRepository,
                                 RestTemplate restTemplate,
                                 ObjectMapper objectMapper) {
        this.jpaReservaRepository = jpaReservaRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public ReservaModel registrarReserva(ReservaModel reservaModel) {
        return jpaReservaRepository.save(reservaModel);
    }

    @Override
    public List<ReservaModel> buscarOcupacoesDosQuartos() {
        return jpaReservaRepository.findAll();
    }

    @Override
    public ReservaModel confirmarReserva(Long id) {
        Optional<ReservaModel> reservaModelOptional = jpaReservaRepository.findById(id);
        ReservaModel reservaModel =
                reservaModelOptional.orElseThrow(() -> new NoSuchElementException("Reserva não localizada"));
        reservaModel.setStatus(StatusEnumModel.CONFIRMADO);
        return jpaReservaRepository.save(reservaModel);
    }

    @Override
    public void enviarEmailConfirmacao(ReservaEntity reservaEntity) {

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://customer:8083/v1/customers/{id}",
                String.class,
                reservaEntity.getIdCliente()
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode produtoJsonNode = objectMapper.readTree(response.getBody());
                String email = produtoJsonNode.get("email").textValue();
                sendFakeEmail(reservaEntity.getIdCliente(), email);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendFakeEmail(Long id, String email) {
        System.out.println("Enviando email de confirmação de reserva para o cliente de id " + id +
                " no email " + email);
    }
}
