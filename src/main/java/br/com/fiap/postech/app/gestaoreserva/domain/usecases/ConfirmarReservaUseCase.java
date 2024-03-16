package br.com.fiap.postech.app.gestaoreserva.domain.usecases;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ReservaRepository;

public class ConfirmarReservaUseCase {

    final ReservaRepository reservaRepository;

    public ConfirmarReservaUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void call(Long id) {
        ReservaEntity reservaEntityConfirmada = reservaRepository.confirmar(id);
        reservaRepository.enviarEmailConfirmacao(reservaEntityConfirmada);
    }



}
