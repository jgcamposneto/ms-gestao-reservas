package br.com.fiap.postech.app.gestaoreserva.data.controllers;

import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface ReservaApi {

    ResponseEntity<?> incluir(@RequestBody ReservaRequestModel reservaRequestModel);
}
