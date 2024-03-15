package br.com.fiap.postech.app.gestaoreserva.data.datasources.impl;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ClienteDataSourceLocal;

public class ClienteDataSourceImpl implements ClienteDataSourceLocal {
    @Override
    public boolean existeCliente(Long id) {
        return true;
    }
}
