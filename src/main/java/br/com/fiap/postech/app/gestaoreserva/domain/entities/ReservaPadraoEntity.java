package br.com.fiap.postech.app.gestaoreserva.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class ReservaPadraoEntity implements ReservaEntity {

    private Long id;
    private Long idCliente;
    private LocalDate entrada;
    private LocalDate saida;
    private int totalPessoas;
    private List<Long> idQuartos;
    private BigDecimal valorTotalConta;
    private String status;

    public ReservaPadraoEntity(Long idCliente, LocalDate entrada, LocalDate saida, int totalPessoas, List<Long> idQuartos, BigDecimal valorTotalConta, String status) {
        this.idCliente = idCliente;
        this.entrada = entrada;
        this.saida = saida;
        this.totalPessoas = totalPessoas;
        this.idQuartos = idQuartos;
        this.valorTotalConta = valorTotalConta;
        this.status = status;
    }

    public ReservaPadraoEntity() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public LocalDate getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDate entrada) {
        this.entrada = entrada;
    }

    @Override
    public LocalDate getSaida() {
        return saida;
    }

    public void setSaida(LocalDate saida) {
        this.saida = saida;
    }

    @Override
    public int getTotalPessoas() {
        return totalPessoas;
    }

    public void setTotalPessoas(int totalPessoas) {
        this.totalPessoas = totalPessoas;
    }

    @Override
    public List<Long> getIdQuartos() {
        return idQuartos;
    }

    public void setIdQuartos(List<Long> idQuartos) {
        this.idQuartos = idQuartos;
    }

    @Override
    public BigDecimal getValorTotalConta() {
        return valorTotalConta;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setValorTotalConta(BigDecimal valorTotalConta) {
        this.valorTotalConta = valorTotalConta;
    }
}
