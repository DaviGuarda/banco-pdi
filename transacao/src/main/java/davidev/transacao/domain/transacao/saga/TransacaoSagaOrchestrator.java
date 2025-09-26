package davidev.transacao.domain.transacao.saga;

import davidev.transacao.domain.transacao.Transacao;
import davidev.transacao.domain.transacao.TransacaoStatusService;
import davidev.transacao.exceptions.FalhaEtapaSagaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoSagaOrchestrator {

    private final DebitoStep debitoStep;
    private final CreditoStep creditoStep;
    private final TransacaoStatusService statusService;

    public void executarSaga(Transacao transacao) {
        List<SagaStep> passosExecutados = new ArrayList<>();
        try {
            debitoStep.executar(transacao);
            passosExecutados.add(debitoStep);

            creditoStep.executar(transacao);
            passosExecutados.add(creditoStep);

            statusService.finalizarSagaComSucesso(transacao);
        } catch (FalhaEtapaSagaException e) {
            compensarSaga(passosExecutados, transacao);
        }
    }

    private void compensarSaga(List<SagaStep> passos, Transacao transacao) {
        Collections.reverse(passos);
        for (SagaStep step : passos) {
            try {
                step.compensar(transacao);
            } catch (Exception e) {
                // Falha na compensação, não necessário PDI
            }
        }
    }
}
