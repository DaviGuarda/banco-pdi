package davidev.conta.domain.conta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaOutput(
        UUID id,
        @JsonProperty("nome_titular")
        String nomeTitular,
        @JsonProperty("saldo_conta")
        BigDecimal saldoConta
) {
}
