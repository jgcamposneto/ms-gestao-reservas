package br.com.fiap.postech.app.gestaoreserva.data.models;

import br.com.fiap.postech.app.gestaoreserva.domain.entities.ReservaEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservaQuartosOcupadosResponseModel {

    private LocalDate entrada;

    private LocalDate saida;

    private List<Long> idQuartos;

    public ReservaQuartosOcupadosResponseModel(ReservaEntity reservaEntity) {
        this.entrada = reservaEntity.getEntrada();
        this.saida = reservaEntity.getSaida();
        this.idQuartos = reservaEntity.getIdQuartos();
    }
}
