package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.JpaReservaRepository;
import br.com.fiap.postech.app.gestaoreserva.data.datasources.ReservaDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;

public class ReservaDataSourceImpl implements ReservaDataSourceLocal {

    final JpaReservaRepository jpaReservaRepository;

    public ReservaDataSourceImpl(JpaReservaRepository jpaReservaRepository) {
        this.jpaReservaRepository = jpaReservaRepository;
    }

    @Override
    public ReservaModel registrarReserva(ReservaModel reservaModel) {
        return jpaReservaRepository.save(reservaModel);
    }
}
