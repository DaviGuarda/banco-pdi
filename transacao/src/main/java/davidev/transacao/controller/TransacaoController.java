package davidev.transacao.controller;

import davidev.transacao.domain.transacao.TransacaoInput;
import davidev.transacao.domain.transacao.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Void> iniciarTransacao(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody @Valid TransacaoInput input
    ) {
        transacaoService.iniciarTransacao(input, idempotencyKey);
        return ResponseEntity.accepted().build();
    }
}