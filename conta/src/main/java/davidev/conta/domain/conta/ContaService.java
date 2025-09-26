package davidev.conta.domain.conta;

import java.math.BigDecimal;
import java.util.UUID;

public interface ContaService {

    ContaOutput criarConta(ContaInput contaInput);

    ContaOutput buscarContaPorId(UUID idConta);

    void creditar(UUID idConta, BigDecimal valor);

    void debitar(UUID idConta, BigDecimal valor);
}

