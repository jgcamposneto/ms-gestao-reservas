package br.com.fiap.postech.app.gestaoreserva.data.datasources;

import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;

public interface ReservaDataSourceLocal {

    ReservaModel registrarReserva(ReservaModel reservaModel);

}
