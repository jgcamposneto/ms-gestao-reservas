package br.com.fiap.postech.app.gestaoreserva.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservaEntity {

    Long getId();
    Long getIdCliente();
    LocalDate getEntrada();
    LocalDate getSaida();
    int getTotalPessoas();
    List<Long> getIdQuartos();
    BigDecimal getValorTotalConta();
    void setId(Long id);
}
