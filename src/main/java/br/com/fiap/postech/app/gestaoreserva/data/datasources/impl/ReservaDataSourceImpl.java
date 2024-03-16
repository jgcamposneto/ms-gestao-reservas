package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.JpaReservaRepository;
import br.com.fiap.postech.app.gestaoreserva.data.datasources.ReservaDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;
import br.com.fiap.postech.app.gestaoreserva.data.models.StatusEnumModel;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ReservaDataSourceImpl implements ReservaDataSourceLocal {

    final JpaReservaRepository jpaReservaRepository;

    public ReservaDataSourceImpl(JpaReservaRepository jpaReservaRepository) {
        this.jpaReservaRepository = jpaReservaRepository;
    }

    @Override
    public ReservaModel registrarReserva(ReservaModel reservaModel) {
        return jpaReservaRepository.save(reservaModel);
    }

    @Override
    public List<ReservaModel> buscarOcupacoesDosQuartos() {
        return jpaReservaRepository.findAll();
    }

    @Override
    public void confirmarReserva(Long id) {
        Optional<ReservaModel> reservaModelOptional = jpaReservaRepository.findById(id);
        ReservaModel reservaModel =
                reservaModelOptional.orElseThrow(() -> new NoSuchElementException("Reserva não localizada"));
        reservaModel.setStatus(StatusEnumModel.CONFIRMADO);
        jpaReservaRepository.save(reservaModel);
    }
}
