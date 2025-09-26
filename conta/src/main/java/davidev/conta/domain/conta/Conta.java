package davidev.conta.domain.conta;

import davidev.conta.exceptions.SaldoInsuficienteException;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Entity
@Table(name = "contas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Conta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(name = "nome_titular", nullable = false, length = 155, unique = true, updatable = false)
    private String nomeTitular;

    @Column(nullable = false)
    private BigDecimal saldo;

    public void creditar(BigDecimal valor) {
        if (isNull(valor) || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor a ser creditado não pode ser negativo.");

        this.saldo = this.saldo.add(valor);
    }

    public void debitar(BigDecimal valor) {
        if (isNull(valor) || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor a ser debitado não pode ser negativo.");

        if (this.saldo.compareTo(valor) < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a operação.");

        this.saldo = this.saldo.subtract(valor);
    }
}