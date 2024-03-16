package br.com.fiap.postech.app.gestaoreserva.data.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ReservaRequestModel {

    @NotNull(message = "O ID do cliente não pode ser nulo")
    private Long idCliente;

    @NotNull(message = "A data de entrada não pode ser nula")
    @Future(message = "A data de entrada deve ser no futuro")
    private LocalDate entrada;

    @NotNull(message = "A data de saída não pode ser nula")
    @Future(message = "A data de saída deve ser no futuro")
    private LocalDate saida;

    @Positive(message = "O número total de pessoas deve ser maior que zero")
    private int totalPessoas;

    @NotEmpty(message = "A lista de IDs de quartos não pode estar vazia")
    private List<Long> idQuartos;

    List<OpcionaisRequestModel> opcionais;

    public Map<Long, Integer> opcionaisToMap() {
        return opcionais.stream().collect(
                Collectors.toMap(OpcionaisRequestModel::getId, OpcionaisRequestModel::getQuantidade));
    }

}
