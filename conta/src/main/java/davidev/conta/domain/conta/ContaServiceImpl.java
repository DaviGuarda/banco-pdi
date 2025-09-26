package davidev.conta.domain.conta;

import davidev.conta.exceptions.RegistroNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    @Override
    public ContaOutput criarConta(ContaInput contaInput) {
        var contaCriada = contaRepository.save(ContaMapper.toEntity(contaInput));
        return ContaMapper.toDto(contaCriada);
    }

    @Override
    public ContaOutput buscarContaPorId(UUID idConta) {
        return ContaMapper.toDto(contaRepository.findById(idConta)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Conta não encontrada com o ID:" + idConta)));
    }

    @Transactional
    @Override
    public void creditar(UUID idConta, BigDecimal valor) {
        var conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Conta não encontrada com o ID: " + idConta));

        conta.creditar(valor);
        contaRepository.save(conta);
    }

    @Transactional
    @Override
    public void debitar(UUID idConta, BigDecimal valor) {
        var conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Conta não encontrada com o ID: " + idConta));

        conta.debitar(valor);
        contaRepository.save(conta);
    }
}
