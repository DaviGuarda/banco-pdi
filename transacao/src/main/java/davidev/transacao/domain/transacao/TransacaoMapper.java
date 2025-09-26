package davidev.transacao.domain.transacao;

import davidev.banco.dtos.transacao.TransacaoConcluidaOutput;

import java.time.LocalDateTime;

public class TransacaoMapper {

    public static Transacao toEntity(TransacaoInput input, String idempotencyKey) {
        return Transacao.builder()
                .contaOrigemId(input.contaOrigemId())
                .contaDestinoId(input.contaDestinoId())
                .valor(input.valor())
                .idempotencyKey(idempotencyKey)
                .status(StatusTransacao.INICIADA)
                .dataHora(LocalDateTime.now())
                .build();
    }

    public static TransacaoConcluidaOutput toTransacaoConcluida(Transacao transacao) {
        return new TransacaoConcluidaOutput(
                transacao.getId(),
                transacao.getContaOrigemId(),
                transacao.getContaDestinoId(),
                transacao.getValor(),
                transacao.getDataHora()
        );
    }

}
