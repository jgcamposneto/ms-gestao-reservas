package br.com.fiap.postech.app.gestaoreserva.domain.usecases;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ReservaRepository;

import java.util.List;

public class BuscarOcupacaoDosQuartosUseCase {

    final ReservaRepository reservaRepository;

    public BuscarOcupacaoDosQuartosUseCase(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<ReservaEntity> call() {
        return reservaRepository.buscarOcupacoesDosQuartos();
    }
}
