package davidev.transacao.domain.transacao.saga;

import davidev.transacao.domain.transacao.Transacao;

public interface SagaStep {

    void executar(Transacao transacao);

    void compensar(Transacao transacao);
}
