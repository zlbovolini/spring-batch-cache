package com.github.lbovolini.app.estabelecimento.registra;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
        // Cria estabelecimento com o cliente a seguir, somente para simplificar a implementação
        estabelecimento.adicionaCliente(new Cliente("Lucas", estabelecimento));
        transaction.execute(status -> estabelecimentoRepository.save(estabelecimento));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(estabelecimento.getUuid())
                .toUri();

        RegistraEstabelecimentoResponse response = new RegistraEstabelecimentoResponse(estabelecimento);

        return ResponseEntity.created(location).body(response);
    }
}
