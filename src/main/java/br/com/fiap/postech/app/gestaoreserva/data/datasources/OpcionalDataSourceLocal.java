package br.com.fiap.postech.app.gestaoreserva.data.datasources;

import java.math.BigDecimal;

public interface OpcionalDataSourceLocal {
    boolean existeOpcional(Long id);

    BigDecimal consultarValor(Long id);
}
