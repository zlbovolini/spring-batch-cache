package com.github.lbovolini.vendas.estabelecimento.cria;

import com.github.lbovolini.vendas.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.vendas.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/estabelecimentos")
class CriaEstabelecimentoController {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final TransactionTemplate transaction;

    CriaEstabelecimentoController(EstabelecimentoRepository estabelecimentoRepository,
                                  TransactionTemplate transaction) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.transaction = transaction;
    }

    @PostMapping
    ResponseEntity<CriaEstabelecimentoResponse> cria(@Valid @RequestBody CriaEstabelecimentoRequest request) {
        Estabelecimento estabelecimento = request.toEstabelecimento();

        transaction.execute(status -> estabelecimentoRepository.save(estabelecimento));
        CriaEstabelecimentoResponse response = new CriaEstabelecimentoResponse(estabelecimento);

        return ResponseEntity.ok(response);
    }
}