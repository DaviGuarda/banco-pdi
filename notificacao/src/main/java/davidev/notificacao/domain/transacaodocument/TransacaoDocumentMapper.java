package davidev.notificacao.domain.transacaodocument;

import davidev.banco.dtos.transacao.TransacaoConcluidaOutput;

public class TransacaoDocumentMapper {

    public static TransacaoDocument toTransacaoDocument(TransacaoConcluidaOutput output) {
        return TransacaoDocument.builder()
                .id(output.transacaoId())
                .contaOrigemId(output.contaOrigemId())
                .contaDestinoId(output.contaDestinoId())
                .valor(output.valor())
                .dataHora(output.dataHora())
                .build();
    }

}
