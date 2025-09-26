package davidev.notificacao.controller;

import davidev.notificacao.domain.transacaodocument.TransacaoDocument;
import davidev.notificacao.domain.transacaodocument.TransacaoSearchService;
import davidev.notificacao.domain.transacaodocument.output.StatsGastosPorContaDestinoOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes-search")
@RequiredArgsConstructor
public class TransacaoSearchController {

    private final TransacaoSearchService searchService;

    @GetMapping
    public ResponseEntity<Page<TransacaoDocument>> buscaFiltrada(
            @RequestParam(required = false) UUID contaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(searchService.buscarTransacoes(contaId, dataInicio, dataFim, pageable));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<TransacaoDocument>> buscaLivre(@RequestParam String termo, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(searchService.buscaLivre(termo, pageable));
    }

    @GetMapping("/stats/gastos-por-conta-destino")
    public ResponseEntity<StatsGastosPorContaDestinoOutput> gastosPorContaDestino(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(defaultValue = "5") int topN) {
        return ResponseEntity.ok(searchService.gastosPorContaDestino(dataInicio, dataFim, topN));
    }
}