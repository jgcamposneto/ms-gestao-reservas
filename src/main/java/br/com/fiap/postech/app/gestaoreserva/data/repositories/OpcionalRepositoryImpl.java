package br.com.fiap.postech.app.gestaoreserva.data.repositories;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ClienteDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.datasources.OpcionalDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.OpcionalRepository;

import java.math.BigDecimal;

public class OpcionalRepositoryImpl implements OpcionalRepository {

    final OpcionalDataSourceLocal opcionalDataSourceLocal;

    public OpcionalRepositoryImpl(OpcionalDataSourceLocal opcionalDataSourceLocal) {
        this.opcionalDataSourceLocal = opcionalDataSourceLocal;
    }

    @Override
    public boolean existeOpcional(Long id) {
        return opcionalDataSourceLocal.existeOpcional(id);
    }

    @Override
    public BigDecimal consultarValor(Long id) {
        return opcionalDataSourceLocal.consultarValor(id);
    }
}
