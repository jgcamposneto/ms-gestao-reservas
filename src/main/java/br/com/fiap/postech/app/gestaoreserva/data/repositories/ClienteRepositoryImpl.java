package br.com.fiap.postech.app.gestaoreserva.data.repositories;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.ClienteDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ClienteRepository;

public class ClienteRepositoryImpl implements ClienteRepository {

    final ClienteDataSourceLocal clienteDataSourceLocal;

    public ClienteRepositoryImpl(ClienteDataSourceLocal clienteDataSourceLocal) {
        this.clienteDataSourceLocal = clienteDataSourceLocal;
    }

    @Override
    public boolean existeCliente(Long id) {
        return clienteDataSourceLocal.existeCliente(id);
    }
}
