package davidev.conta.controller;

import davidev.banco.dtos.conta.OperacaoInput;
import davidev.conta.domain.conta.ContaInput;
import davidev.conta.domain.conta.ContaOutput;
import davidev.conta.domain.conta.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaOutput> cadastrarConta(@RequestBody @Valid ContaInput contaInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(contaInput));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaOutput> buscarContaPorId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(contaService.buscarContaPorId(id));
    }

    @PostMapping("/{id}/debitar")
    public ResponseEntity<Void> debitar(
            @PathVariable("id") UUID idConta,
            @RequestBody @Valid OperacaoInput input
    ) {
        contaService.debitar(idConta, input.valor());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/creditar")
    public ResponseEntity<Void> creditar(@PathVariable("id") UUID idConta,
                                         @RequestBody @Valid OperacaoInput input
    ) {
        contaService.creditar(idConta, input.valor());
        return ResponseEntity.noContent().build();
    }
}
