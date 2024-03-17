package br.com.fiap.postech.app.gestaoreserva.data.controllers;


import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaQuartosOcupadosResponseModel;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaRequestModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.BuscarOcupacaoDosQuartosUseCase;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.ConfirmarReservaUseCase;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.IncluirReservaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reservas", produces = "application/json")
public class ReservaController implements ReservaApi {

    final IncluirReservaUseCase incluirReservaUseCase;
    final BuscarOcupacaoDosQuartosUseCase buscarOcupacaoDosQuartosUseCase;

    final ConfirmarReservaUseCase confirmarReservaUseCase;

    public ReservaController(IncluirReservaUseCase incluirReservaUseCase,
                             BuscarOcupacaoDosQuartosUseCase buscarOcupacaoDosQuartosUseCase,
                             ConfirmarReservaUseCase confirmarReservaUseCase) {
        this.incluirReservaUseCase = incluirReservaUseCase;
        this.buscarOcupacaoDosQuartosUseCase = buscarOcupacaoDosQuartosUseCase;
        this.confirmarReservaUseCase = confirmarReservaUseCase;
    }

    @Operation(summary = "Realiza a reserva", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição contem tributo invalido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(ReservaRequestModel reservaRequestModel) {
        try {

            ReservaEntity reservaEntityCriado =
                    incluirReservaUseCase.call(reservaRequestModel.getIdCliente(),
                            reservaRequestModel.getEntrada(),
                            reservaRequestModel.getSaida(),
                            reservaRequestModel.getTotalPessoas(),
                            reservaRequestModel.getIdQuartos(),
                            reservaRequestModel.opcionaisToMap());
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaEntityCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza a busca da reserva", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A busca da reserva foi realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @GetMapping
    public ResponseEntity<?> buscar() {
        try {
            List<ReservaEntity> ocupacoesDosQuartos = buscarOcupacaoDosQuartosUseCase.call();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ocupacoesDosQuartos
                            .stream()
                            .map(ReservaQuartosOcupadosResponseModel::new)
                            .toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza a confirmação da reserva", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não foi processada corretamente"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @PatchMapping("{id}")
    public ResponseEntity<?> confirmar(@PathVariable("id") Long id) {
        try {
            confirmarReservaUseCase.call(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
