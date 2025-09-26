package davidev.transacao.messageria;

import davidev.banco.dtos.transacao.TransacaoConcluidaOutput;
import davidev.transacao.config.KafkaTopicoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void enviarEventoTransacaoConcluida(TransacaoConcluidaOutput output) {
        kafkaTemplate.send(KafkaTopicoConfig.TOPICO_TRANSACOES_CONCLUIDA, output.transacaoId().toString(), output);
    }
}