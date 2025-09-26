package davidev.notificacao.domain.transacaodocument;

import davidev.banco.dtos.transacao.TransacaoConcluidaOutput;
import davidev.notificacao.config.KafkaTopicoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoDocumentConsumer {

    private final TransacaoDocumentRepository transacaoDocumentRepository;

    @KafkaListener(topics = KafkaTopicoConfig.TOPICO_TRANSACOES_CONCLUIDA, groupId = "notificacao-group")
    public void concluiuTransacao(TransacaoConcluidaOutput transacaoConcluidaOutput) {
        var transacaoDocumento = TransacaoDocumentMapper.toTransacaoDocument(transacaoConcluidaOutput);
        transacaoDocumento.setDescricao(String.format("Transferência de %s para %s no valor de R$%.2f", transacaoConcluidaOutput.contaOrigemId(), transacaoConcluidaOutput.contaDestinoId(), transacaoConcluidaOutput.valor()));
        transacaoDocumentRepository.save(transacaoDocumento);
    }
}
