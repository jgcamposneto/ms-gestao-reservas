package br.com.fiap.postech.app.gestaoreserva.domain.usecases;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaPadraoEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ClienteRepository;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.QuartoRepository;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ReservaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

public class IncluirReservaUseCase {

    final ReservaRepository reservaRepository;
    final ClienteRepository clienteRepository;
    final QuartoRepository quartoRepository;

    public IncluirReservaUseCase(ReservaRepository reservaRepository, ClienteRepository clienteRepository, QuartoRepository quartoRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
    }

    public ReservaEntity call(Long idCliente, LocalDate entrada, LocalDate saida, int totalPessoas, List<Long> idQuartos) {
        validarDataEntradaMenorQueSaida(entrada, saida);
        validarCliente(idCliente);
        validarQuartos(idQuartos);
        BigDecimal valorTotalConta = calcularValorTotalConta(entrada, saida, idQuartos);
        ReservaEntity reservaEntity =
                new ReservaPadraoEntity(idCliente, entrada, saida, totalPessoas, idQuartos, valorTotalConta);
        return reservaRepository.registrarReserva(reservaEntity);
    }

    private BigDecimal calcularValorTotalConta(LocalDate entrada, LocalDate saida, List<Long> idQuartos) {
        long dias = ChronoUnit.DAYS.between(entrada, saida);
        BigDecimal valorTotalDiarias = idQuartos
                .stream()
                .map(quartoRepository::consultarValorDiaria)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorTotalDiarias.multiply(BigDecimal.valueOf(dias));
    }

    private void validarQuartos(List<Long> idQuartos) {
        idQuartos.forEach(id -> {
            if(!quartoRepository.existeQuarto(id)) {
                throw new NoSuchElementException("Um ou mais quartos não existe");
            }
        });
    }

    private void validarCliente(Long idCliente) {
        if(!clienteRepository.existeCliente(idCliente)) {
            throw new NoSuchElementException("Cliente não existe");
        }
    }

    private void validarDataEntradaMenorQueSaida(LocalDate entrada, LocalDate saida) {
        if(saida.isBefore(entrada)) {
            throw new IllegalArgumentException("Data de entrada deve ser inferior a data de saída");
        }
    }


}
