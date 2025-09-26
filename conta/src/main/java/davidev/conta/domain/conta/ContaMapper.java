package davidev.conta.domain.conta;

import java.math.BigDecimal;
import java.util.Optional;

class ContaMapper {

    public static Conta toEntity(ContaInput contaInput) {
        return Conta.builder()
                .nomeTitular(contaInput.nomeTitular())
                .saldo(
                        Optional.ofNullable(contaInput.valorInicial())
                                .orElse(BigDecimal.ZERO)
                )
                .build();
    }

    public static ContaOutput toDto(Conta contaOutput) {
        return new ContaOutput(
                contaOutput.getId(),
                contaOutput.getNomeTitular(),
                contaOutput.getSaldo()
        );
    }
}
