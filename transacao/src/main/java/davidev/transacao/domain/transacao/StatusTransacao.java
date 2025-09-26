package davidev.transacao.domain.transacao;

public enum StatusTransacao {
    INICIADA,
    DEBITO_REALIZADO,
    FALHA_NO_DEBITO,
    CREDITO_REALIZADO,
    FALHA_NO_CREDITO,
    COMPENSACAO_REALIZADA,
    FALHA_NA_COMPENSACAO,
    CONCLUIDA
}
