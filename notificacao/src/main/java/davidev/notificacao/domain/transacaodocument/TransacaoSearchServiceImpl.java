package davidev.notificacao.domain.transacaodocument;

import davidev.notificacao.domain.transacaodocument.output.StatsGastosPorContaDestinoOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TransacaoSearchServiceImpl implements TransacaoSearchService {

    private final TransacaoDocumentRepository transacaoRepository;
    private final TransacaoSearchRepository transacaoSearchRepository;


    @Override
    public Page<TransacaoDocument> buscarTransacoes(UUID contaId, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable) {
        boolean hasConta = nonNull(contaId);
        boolean hasDatas = nonNull(dataInicio) && nonNull(dataFim);

        if (hasConta && hasDatas) {
            String dataInicioStr = dataInicio.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String dataFimStr = dataFim.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return transacaoRepository.findByContaAndDataBetween(contaId, dataInicioStr, dataFimStr, pageable);
        } else if (hasConta) {
            return transacaoRepository.findByConta(contaId, pageable);
        } else {
            return transacaoRepository.findAll(pageable);
        }
    }

    @Override
    public Page<TransacaoDocument> buscaLivre(String termo, Pageable pageable) {
        if (isNull(termo) || termo.isBlank()) {
            return Page.empty(pageable);
        }
        return transacaoRepository.findByDescricaoContaining(termo, pageable);
    }

    @Override
    public StatsGastosPorContaDestinoOutput gastosPorContaDestino(LocalDateTime dataInicio, LocalDateTime dataFim, int topN) {
        return transacaoSearchRepository.calcularGastosPorContaDestino(dataInicio, dataFim, topN);
    }
}
