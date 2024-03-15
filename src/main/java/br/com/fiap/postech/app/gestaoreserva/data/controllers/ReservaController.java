package br.com.fiap.postech.app.gestaoreserva.data.controllers;


import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaRequestModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaPadraoEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.usecases.IncluirReservaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservas")
public class ReservaController implements ReservaApi {

    final IncluirReservaUseCase incluirReservaUseCase;

    public ReservaController(IncluirReservaUseCase incluirReservaUseCase) {
        this.incluirReservaUseCase = incluirReservaUseCase;
    }


    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(ReservaRequestModel reservaRequestModel) {
        try {
            ReservaEntity quartoEntityCriado =
                    incluirReservaUseCase.call(reservaRequestModel.getIdCliente(),
                            reservaRequestModel.getEntrada(),
                            reservaRequestModel.getSaida(),
                            reservaRequestModel.getTotalPessoas(),
                            reservaRequestModel.getIdQuartos());
            return ResponseEntity.status(HttpStatus.CREATED).body(quartoEntityCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
