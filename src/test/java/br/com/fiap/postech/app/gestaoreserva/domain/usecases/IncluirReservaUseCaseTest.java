package br.com.fiap.postech.app.gestaoreserva.domain.usecases;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaPadraoEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ClienteRepository;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.QuartoRepository;
import br.com.fiap.postech.app.gestaoreserva.domain.repositories.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IncluirReservaUseCaseTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private QuartoRepository quartoRepository;

    @InjectMocks
    private IncluirReservaUseCase incluirReservaUseCase;

    @Mock
    private BuscarOcupacaoDosQuartosUseCase buscarOcupacaoDosQuartosUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeveIncluirReserva_QuandoValidarParametros_RetornaReservaEntity() {
        // Mocking
        Long idCliente = 1L;
        LocalDate entrada = LocalDate.now();
        LocalDate saida = entrada.plusDays(1);
        List<Long> idQuartos = Collections.singletonList(1L);
        ReservaEntity reservaMock = mock(ReservaEntity.class);

        // Configuring mocks
        when(quartoRepository.consultarValorDiaria(any(Long.class))).thenReturn(BigDecimal.TEN);
        when(reservaRepository.registrarReserva(any(ReservaEntity.class))).thenReturn(reservaMock);
        when(clienteRepository.existeCliente(idCliente)).thenReturn(true);
        when(quartoRepository.existeQuarto(any(Long.class))).thenReturn(true);

        // Execution
        ReservaEntity result = incluirReservaUseCase.call(idCliente, entrada, saida, 1, idQuartos);

        // Verification
        assertNotNull(result);
        assertEquals(reservaMock, result);
        verify(quartoRepository, times(1)).consultarValorDiaria(any(Long.class));
        verify(reservaRepository, times(1)).registrarReserva(any(ReservaEntity.class));
        verify(clienteRepository, times(1)).existeCliente(idCliente);
        verify(quartoRepository, times(1)).existeQuarto(any(Long.class));
    }

    @Test
    void testDeveLancarNoSuchElementException_QuandoClienteForInvalido() {
        // Mocking
        Long idCliente = 1L;
        LocalDate entrada = LocalDate.now();
        LocalDate saida = entrada.plusDays(1);
        List<Long> idQuartos = Collections.singletonList(1L);

        // Configuring mocks
        when(clienteRepository.existeCliente(idCliente)).thenReturn(false);

        // Execution and Verification
        assertThrows(NoSuchElementException.class, () -> incluirReservaUseCase.call(idCliente, entrada, saida, 1, idQuartos));
        verify(quartoRepository, never()).consultarValorDiaria(any(Long.class));
        verify(reservaRepository, never()).registrarReserva(any(ReservaEntity.class));
        verify(quartoRepository, never()).existeQuarto(any(Long.class));
    }

    @Test
    void testDeveLancarNoSuchElementException_QuandoQuartoForInvalido() {
        // Mocking
        Long idCliente = 1L;
        LocalDate entrada = LocalDate.now();
        LocalDate saida = entrada.plusDays(1);
        List<Long> idQuartos = Collections.singletonList(1L);

        // Configuring mocks
        when(clienteRepository.existeCliente(idCliente)).thenReturn(true);
        when(quartoRepository.existeQuarto(any(Long.class))).thenReturn(false);

        // Execution and Verification
        assertThrows(NoSuchElementException.class, () -> incluirReservaUseCase.call(idCliente, entrada, saida, 1, idQuartos));
        verify(quartoRepository, never()).consultarValorDiaria(any(Long.class));
        verify(reservaRepository, never()).registrarReserva(any(ReservaEntity.class));
        verify(quartoRepository, times(1)).existeQuarto(any(Long.class));
    }

    @Test
    void testDeveLancarIllegalArgumentException_QuandoDataDeEntradaForMaiorQueDataDeSaida() {
        // Mocking
        Long idCliente = 1L;
        LocalDate entrada = LocalDate.now();
        LocalDate saida = entrada.minusDays(1); // Reverse order

        // Execution and Verification
        assertThrows(IllegalArgumentException.class, () -> incluirReservaUseCase.call(idCliente, entrada, saida, 1, Collections.singletonList(1L)));
        verify(clienteRepository, never()).existeCliente(any(Long.class));
        verify(quartoRepository, never()).consultarValorDiaria(any(Long.class));
        verify(reservaRepository, never()).registrarReserva(any(ReservaEntity.class));
        verify(quartoRepository, never()).existeQuarto(any(Long.class));
    }

    @Test
    void testCalcularValorTotalConta_QuandoParametrosForemValidos() {
        // Mocking
        LocalDate entrada = LocalDate.now();
        LocalDate saida = entrada.plusDays(2);
        List<Long> idQuartos = Collections.singletonList(1L);
        BigDecimal valorDiaria = BigDecimal.valueOf(100);
        BigDecimal expectedValorTotalConta = BigDecimal.valueOf(200); // 2 days * 100 per day

        // Configuring mocks
        when(quartoRepository.consultarValorDiaria(any(Long.class))).thenReturn(valorDiaria);

        // Execution
        BigDecimal result = incluirReservaUseCase.calcularValorTotalConta(entrada, saida, idQuartos);

        // Verification
        assertNotNull(result);
        assertEquals(expectedValorTotalConta, result);
        verify(quartoRepository, times(1)).consultarValorDiaria(any(Long.class));
    }

    @Test
    void testVerificarConflitoDeReservas_DeveLancarExcecao_QuandoHouverConflito() {
        // Mocking
        LocalDate entrada = LocalDate.of(2024, 3, 10);
        LocalDate saida = LocalDate.of(2024, 3, 15);
        List<Long> idQuartos = Collections.singletonList(1L);
        ReservaEntity reservaEntity = new ReservaPadraoEntity(1L, LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 14), 2, idQuartos, null);
        List<ReservaEntity> ocupacoesDosQuartos = Collections.singletonList(reservaEntity);

        // Configuring mocks
        when(buscarOcupacaoDosQuartosUseCase.call()).thenReturn(ocupacoesDosQuartos);

        // Execution and Verification
        assertThrows(IllegalStateException.class, () -> incluirReservaUseCase.verificarConflitoDeReservas(entrada, saida, idQuartos));
        verify(buscarOcupacaoDosQuartosUseCase, times(1)).call();
    }

    @Test
    void testVerificarConflitoDeReservas_NaoDeveLancarExcecao_QuandoNaoHouverConflito() {
        // Mocking
        LocalDate entrada = LocalDate.of(2024, 3, 5);
        LocalDate saida = LocalDate.of(2024, 3, 9);
        List<Long> idQuartos = Collections.singletonList(1L);
        List<ReservaEntity> ocupacoesDosQuartos = Collections.emptyList();

        // Configuring mocks
        when(buscarOcupacaoDosQuartosUseCase.call()).thenReturn(ocupacoesDosQuartos);

        // Execution and Verification
        incluirReservaUseCase.verificarConflitoDeReservas(entrada, saida, idQuartos);
        verify(buscarOcupacaoDosQuartosUseCase, times(1)).call();
    }

}