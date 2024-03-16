package br.com.fiap.postech.app.gestaoreserva.domain.repositories;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;

import java.util.List;

public interface ReservaRepository {
    ReservaEntity registrarReserva(ReservaEntity reservaEntity);

    List<ReservaEntity> buscarOcupacoesDosQuartos();
}
