package br.com.fiap.postech.app.gestaoreserva.data.repositories;

import br.com.fiap.postech.app.gestaoreserva.data.datasources.QuartoDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.data.datasources.ReservaDataSourceLocal;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.QuartoRepository;

import java.math.BigDecimal;

public class QuartoRepositoryImpl implements QuartoRepository {

    final QuartoDataSourceLocal quartoDataSourceLocal;

    public QuartoRepositoryImpl(QuartoDataSourceLocal quartoDataSourceLocal) {
        this.quartoDataSourceLocal = quartoDataSourceLocal;
    }

    @Override
    public boolean existeQuarto(Long id) {
        return quartoDataSourceLocal.existeQuarto(id);
    }

    @Override
    public BigDecimal consultarValorDiaria(Long id) {
        return quartoDataSourceLocal.consultarValorDiaria(id);
    }
}
