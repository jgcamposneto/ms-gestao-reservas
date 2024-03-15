package br.com.fiap.postech.app.gestaoreserva.domain.repositories;

import java.math.BigDecimal;

public interface QuartoRepository {

    boolean existeQuarto(Long id);

    BigDecimal consultarValorDiaria(Long id);
}
