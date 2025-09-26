package davidev.transacao.domain.transacao.saga;

import davidev.banco.dtos.conta.OperacaoInput;
import davidev.transacao.client.conta.ContaClient;
import davidev.transacao.domain.transacao.StatusTransacao;
import davidev.transacao.domain.transacao.Transacao;
import davidev.transacao.domain.transacao.TransacaoStatusService;
import davidev.transacao.exceptions.FalhaEtapaSagaException;
import davidev.transacao.utils.ExtracaoErroFeignClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitoStep implements SagaStep {

    private final ContaClient contaClient;
    private final TransacaoStatusService statusService;

    @Override
    public void executar(Transacao transacao) {
        try {
            contaClient.debitar(transacao.getContaOrigemId(), new OperacaoInput(transacao.getValor()));
            statusService.atualizarStatus(transacao, StatusTransacao.DEBITO_REALIZADO, null);
        } catch (Exception e) {
            statusService.atualizarStatus(
                    transacao, StatusTransacao.FALHA_NO_DEBITO, ExtracaoErroFeignClientUtils.extrairMensagemDeErro(e));
            throw new FalhaEtapaSagaException("Falha no passo de débito", e);
        }
    }

    @Override
    public void compensar(Transacao transacao) {
        contaClient.creditar(transacao.getContaOrigemId(), new OperacaoInput(transacao.getValor()));
        statusService.atualizarStatus(transacao, StatusTransacao.COMPENSACAO_REALIZADA, null);
    }
}