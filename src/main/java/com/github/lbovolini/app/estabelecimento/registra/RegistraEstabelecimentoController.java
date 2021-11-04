package com.github.lbovolini.app.estabelecimento.registra;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
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
class RegistraEstabelecimentoController {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final TransactionTemplate transaction;

    RegistraEstabelecimentoController(EstabelecimentoRepository estabelecimentoRepository,
                                      TransactionTemplate transaction) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.transaction = transaction;
    }

    @PostMapping
    ResponseEntity<RegistraEstabelecimentoResponse> registra(@Valid @RequestBody RegistraEstabelecimentoRequest request) {

        boolean jaCadastrado = estabelecimentoRepository.findByCnpj(request.getCnpj()).isPresent();
        
        if (jaCadastrado) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Estabelecimento estabelecimento = request.toEstabelecimento();

        transaction.execute(status -> estabelecimentoRepository.save(estabelecimento));
        RegistraEstabelecimentoResponse response = new RegistraEstabelecimentoResponse(estabelecimento);

        return ResponseEntity.ok(response);
    }
}
