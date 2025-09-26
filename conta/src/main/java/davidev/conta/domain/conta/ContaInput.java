package davidev.conta.domain.conta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ContaInput(
        @JsonProperty("nome_titular")
        @NotBlank(message = "Nome do titular deve ser preenchido.")
        @Size(min = 4, max = 155, message = "Nome do titular deve ter no minímo 4 digitos.")
        String nomeTitular,
        @JsonProperty("valor_inicial")
        @PositiveOrZero(message = "O valor inicial não pode ser negativo.")
        BigDecimal valorInicial
) {
}
