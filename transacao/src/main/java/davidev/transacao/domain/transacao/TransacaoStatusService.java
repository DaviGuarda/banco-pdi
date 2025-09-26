package davidev.transacao.domain.transacao;

import davidev.transacao.messageria.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TransacaoStatusService {

    private final TransacaoRepository transacaoRepository;
    private final KafkaMessageService kafkaMessageService;

    @Transactional
    public Transacao criarTransacaoInicial(TransacaoInput input, String idempotencyKey) {
        Transacao transacao = TransacaoMapper.toEntity(input, idempotencyKey);
        return transacaoRepository.save(transacao);
    }

    @Transactional
    public void atualizarStatus(Transacao transacao, StatusTransacao status, String motivoFalha) {
        transacao.setStatus(status);
        if (nonNull(motivoFalha))
            transacao.setMotivoFalha(motivoFalha.length() > 250
                    ? motivoFalha.substring(0, 250) + "..."
                    : motivoFalha);

        transacaoRepository.save(transacao);
    }

    @Transactional
    public void finalizarSagaComSucesso(Transacao transacao) {
        transacao.setStatus(StatusTransacao.CONCLUIDA);
        transacaoRepository.save(transacao);
        enviarEventoTransacao(transacao);
    }

    public void enviarEventoTransacao(Transacao transacao) {
        try {
            kafkaMessageService.enviarEventoTransacaoConcluida(
                    TransacaoMapper.toTransacaoConcluida(transacao));
        } catch (Exception e) {
            // Falha Envio mensagem
        }
    }
}