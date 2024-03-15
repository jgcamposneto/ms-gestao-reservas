package br.com.fiap.postech.app.gestaoreserva.data.controllers;

import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaRequestModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Quarto Controller", description = "API para realização de Reservas")
public interface ReservaApi {

    @Operation(
            summary = "Registar uma nova reserva",
            description = "Registra uma nova reserva do cliente, para as datas de entrada e saída, para as pessoas e os quartos informados, calculando o valor total da reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição não pode ser completada")
    })
    ResponseEntity<?> incluir(@RequestBody ReservaRequestModel reservaRequestModel);
}
