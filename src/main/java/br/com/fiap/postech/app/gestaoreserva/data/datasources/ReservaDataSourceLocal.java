package br.com.fiap.postech.app.gestaoreserva.data.datasources;

import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;

import java.util.List;

public interface ReservaDataSourceLocal {

    ReservaModel registrarReserva(ReservaModel reservaModel);

    List<ReservaModel> buscarOcupacoesDosQuartos();

    ReservaModel confirmarReserva(Long id);

    void enviarEmailConfirmacao(ReservaEntity reservaEntity);
}
