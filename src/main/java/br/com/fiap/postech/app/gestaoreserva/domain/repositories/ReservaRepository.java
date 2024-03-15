package br.com.fiap.postech.app.gestaoreserva.domain.repositories;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;

public interface ReservaRepository {
    ReservaEntity registrarReserva(ReservaEntity reservaEntity);
}
