package br.com.fiap.postech.app.gestaoreserva.data.datasources;

import java.math.BigDecimal;

public interface QuartoDataSourceLocal {

    boolean existeQuarto(Long id);
    BigDecimal consultarValorDiaria(Long id);
}
