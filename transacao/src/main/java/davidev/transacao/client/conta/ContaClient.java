package davidev.transacao.client.conta;

import davidev.banco.dtos.conta.OperacaoInput;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "conta", url = "${feign.client.url.conta}")
public interface ContaClient {

    @PostMapping("/contas/{id}/debitar")
    @CircuitBreaker(name = "contaService")
    void debitar(@PathVariable("id") UUID id, OperacaoInput input);

    @PostMapping("/contas/{id}/creditar")
    @CircuitBreaker(name = "contaService")
    void creditar(@PathVariable("id") UUID id, OperacaoInput input);
}