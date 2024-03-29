package br.com.fiap.postech.app.gestaoreserva.data.repositories;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ReservaDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ReservaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ReservaRepositoryImpl implements ReservaRepository {

    final ReservaDataSourceLocal reservaDataSourceLocal;

    public ReservaRepositoryImpl(ReservaDataSourceLocal reservaDataSourceLocal) {
        this.reservaDataSourceLocal = reservaDataSourceLocal;
    }

    @Override
    public ReservaEntity registrarReserva(ReservaEntity reservaEntity) {
        ReservaModel reservaModel = ReservaModel.toReservaModel(reservaEntity);
        ReservaModel reservaModelCriada = reservaDataSourceLocal.registrarReserva(reservaModel);
        reservaEntity.setId(reservaModelCriada.getId());
        return reservaEntity;
    }

    @Override
    public List<ReservaEntity> buscarOcupacoesDosQuartos() {
        return reservaDataSourceLocal.buscarOcupacoesDosQuartos()
                .stream()
                .map(ReservaModel::toReservaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaEntity confirmar(Long id) {
        return ReservaModel.toReservaEntity(reservaDataSourceLocal.confirmarReserva(id));
    }

    @Override
    public void enviarEmailConfirmacao(ReservaEntity reservaEntityConfirmada) {
        reservaDataSourceLocal.enviarEmailConfirmacao(reservaEntityConfirmada);
    }
}
