package com.github.lbovolini.app.estabelecimento.atualiza;

import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/estabelecimentos")
class AtualizaEstabelecimentoController {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final TransactionTemplate transaction;

    AtualizaEstabelecimentoController(EstabelecimentoRepository estabelecimentoRepository, TransactionTemplate transaction) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.transaction = transaction;
    }

    @PutMapping("/{uuid}")
    @CacheEvict(cacheNames = "estabelecimento", key = "#uuid", condition = "#result.getStatusCode().is2xxSuccessful()")
    public ResponseEntity<Void> atualiza(@PathVariable UUID uuid, @Valid @RequestBody AtualizaEstabelecimentoRequest request) {

        boolean cnpjCadastradoOutroEstabelecimento = verificaCnpjCadastradoOutroEstabelecimento(uuid, request.getCnpj());

        if (cnpjCadastradoOutroEstabelecimento) {
            return ResponseEntity.unprocessableEntity().build();
        }

        boolean foiAtualizado = atualizaDados(uuid, request);

        if (foiAtualizado) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    private boolean verificaCnpjCadastradoOutroEstabelecimento(UUID uuid, String cnpj) {
        return estabelecimentoRepository.findByCnpj(cnpj)
                .filter(estabelecimento -> !estabelecimento.getUuid().equals(uuid))
                .isPresent();
    }

    private boolean atualizaDados(UUID uuid, AtualizaEstabelecimentoRequest request) {
        return transaction.execute(status -> estabelecimentoRepository.findByUuid(uuid)
                .map(estabelecimento -> {
                    estabelecimento.setNome(request.getNome());
                    estabelecimento.setCnpj(request.getCnpj());

                    return estabelecimento;
                })
                .isPresent()
        );
    }

}
