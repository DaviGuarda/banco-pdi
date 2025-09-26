package davidev.transacao.domain.transacao;

import davidev.transacao.domain.transacao.saga.TransacaoSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoStatusService transacaoStatusService;
    private final TransacaoSagaOrchestrator sagaOrchestrator;

    @Override
    public void iniciarTransacao(TransacaoInput input, String idempotencyKey) {
        if (transacaoRepository.findByIdempotencyKey(idempotencyKey).isPresent()) return;

        Transacao transacao = transacaoStatusService.criarTransacaoInicial(input, idempotencyKey);
        sagaOrchestrator.executarSaga(transacao);
    }
}
