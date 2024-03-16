package br.com.fiap.postech.app.gestaoreserva.data.models;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaPadraoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "reserva")
@Table(name = "reserva")
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;

    private LocalDate entrada;

    private LocalDate saida;

    private int totalPessoas;

    @ElementCollection
    @CollectionTable(name = "ids_quarto", joinColumns = @JoinColumn(name = "reserva_id"))
    @Column(name = "id_quarto")
    private List<Long> idQuartos;

    private BigDecimal valorTotalConta;

    @Enumerated(EnumType.STRING)
    private StatusEnumModel status;

    public static ReservaModel toReservaModel(ReservaEntity reservaEntity) {
        ReservaModel reservaModel = new ReservaModel();
        reservaModel.setIdCliente(reservaEntity.getIdCliente());
        reservaModel.setEntrada(reservaEntity.getEntrada());
        reservaModel.setSaida(reservaEntity.getSaida());
        reservaModel.setTotalPessoas(reservaEntity.getTotalPessoas());
        reservaModel.setIdQuartos(reservaEntity.getIdQuartos());
        reservaModel.setValorTotalConta(reservaEntity.getValorTotalConta());
        reservaModel.setStatus(StatusEnumModel.valueOf(reservaEntity.getStatus()));
        return reservaModel;
    }

    public static ReservaEntity toReservaEntity(ReservaModel reservaModel) {
        return new ReservaPadraoEntity(
                reservaModel.getIdCliente(),
                reservaModel.getEntrada(),
                reservaModel.getSaida(),
                reservaModel.getTotalPessoas(),
                reservaModel.getIdQuartos(),
                reservaModel.getValorTotalConta(),
                reservaModel.getStatus().toString()
        );
    }
}
