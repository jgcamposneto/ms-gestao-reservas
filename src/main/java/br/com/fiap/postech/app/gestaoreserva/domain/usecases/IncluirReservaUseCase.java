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
    final BuscarOcupacaoDosQuartosUseCase buscarOcupacaoDosQuartosUseCase;
    public IncluirReservaUseCase(ReservaRepository reservaRepository,
                                 ClienteRepository clienteRepository,
                                 QuartoRepository quartoRepository,
                                 BuscarOcupacaoDosQuartosUseCase buscarOcupacaoDosQuartosUseCase) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
        this.buscarOcupacaoDosQuartosUseCase = buscarOcupacaoDosQuartosUseCase;
    }

    public ReservaEntity call(Long idCliente, LocalDate entrada, LocalDate saida, int totalPessoas, List<Long> idQuartos) {
        validarDataEntradaMenorQueSaida(entrada, saida);
        validarCliente(idCliente);
        validarQuartos(idQuartos);
        verificarConflitoDeReservas(entrada, saida, idQuartos);
        BigDecimal valorTotalConta = calcularValorTotalConta(entrada, saida, idQuartos);
        ReservaEntity reservaEntity =
                new ReservaPadraoEntity(
                        idCliente,
                        entrada,
                        saida,
                        totalPessoas,
                        idQuartos,
                        valorTotalConta,
                        ReservaEntity.AGUARDANDO_CONFIRMACAO);
        return reservaRepository.registrarReserva(reservaEntity);
    }

    public BigDecimal calcularValorTotalConta(LocalDate entrada, LocalDate saida, List<Long> idQuartos) {
        long dias = ChronoUnit.DAYS.between(entrada, saida);
        BigDecimal valorTotalDiarias = idQuartos
                .stream()
                .map(quartoRepository::consultarValorDiaria)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return valorTotalDiarias.multiply(BigDecimal.valueOf(dias));
    }

    public void verificarConflitoDeReservas(LocalDate entrada, LocalDate saida, List<Long> idQuartos) {
        List<ReservaEntity> ocupacoesDosQuartos = buscarOcupacaoDosQuartosUseCase.call();
        for (ReservaEntity reserva : ocupacoesDosQuartos) {
            LocalDate reservaEntrada = reserva.getEntrada();
            LocalDate reservaSaida = reserva.getSaida();
            List<Long> reservaIdQuartos = reserva.getIdQuartos();
            boolean conflitoData = (entrada.isBefore(reservaEntrada) && saida.isAfter(reservaEntrada))
                    ||
                    (entrada.isBefore(reservaSaida) && saida.isAfter(reservaSaida))
                    ||
                    (entrada.isAfter(reservaEntrada) && saida.isBefore(reservaSaida));
            boolean conflitoQuarto = reservaIdQuartos.stream().anyMatch(idQuartos::contains);
            if (conflitoData && conflitoQuarto) {
                throw new IllegalStateException("Conflito de reservas detectado. Consulte a ocupação dos quartos");
            }
        }
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
