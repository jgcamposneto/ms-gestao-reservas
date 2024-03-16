package br.com.fiap.postech.app.gestaoreserva.domain.repositories;

import java.math.BigDecimal;

public interface OpcionalRepository {

    boolean existeOpcional(Long id);

    BigDecimal consultarValor(Long longIntegerEntry);
}
