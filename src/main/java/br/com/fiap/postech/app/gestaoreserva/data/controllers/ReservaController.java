package br.com.fiap.postech.app.gestaoreserva.data.controllers;


import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaQuartosOcupadosResponseModel;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaRequestModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.BuscarOcupacaoDosQuartosUseCase;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.ConfirmarReservaUseCase;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.IncluirReservaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reservas")
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

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(ReservaRequestModel reservaRequestModel) {
        try {
            ReservaEntity reservaEntityCriado =
                    incluirReservaUseCase.call(reservaRequestModel.getIdCliente(),
                            reservaRequestModel.getEntrada(),
                            reservaRequestModel.getSaida(),
                            reservaRequestModel.getTotalPessoas(),
                            reservaRequestModel.getIdQuartos());
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaEntityCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> buscar() {
        try {
            List<ReservaEntity> ocupacoesDosQuartos = buscarOcupacaoDosQuartosUseCase.call();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ocupacoesDosQuartos
                            .stream()
                            .map(ReservaQuartosOcupadosResponseModel::new)
                            .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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
