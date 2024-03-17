package br.com.fiap.postech.app.gestaoreserva.data.datasources;

import br.com.fiap.postech.app.gestaoreserva.data.models.ReservaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReservaRepository extends JpaRepository<ReservaModel, Long> {
}
