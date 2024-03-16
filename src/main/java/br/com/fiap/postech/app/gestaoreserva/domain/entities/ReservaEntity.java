package br.com.fiap.postech.app.gestaoreserva.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservaEntity {

    public static final String AGUARDANDO_CONFIRMACAO = "AGUARDANDO_CONFIRMACAO";
    public static final String CONFIRMADO = "CONFIRMADO";

    Long getId();
    Long getIdCliente();
    LocalDate getEntrada();
    LocalDate getSaida();
    int getTotalPessoas();
    List<Long> getIdQuartos();
    BigDecimal getValorTotalConta();
    String getStatus();
    void setId(Long id);

}
