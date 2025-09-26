package davidev.transacao.domain.transacao;

public interface TransacaoService {

    void iniciarTransacao(TransacaoInput input, String idempotencyKey);
}
