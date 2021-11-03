package com.github.lbovolini.app.estabelecimento.consulta;

import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/estabelecimentos")
class ConsultaEstabelecimentoController {

    private final EstabelecimentoRepository estabelecimentoRepository;

    ConsultaEstabelecimentoController(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @GetMapping("/{uuid}")
    ResponseEntity<ConsultaEstabelecimentoResponse> consultaPorUuid(@PathVariable UUID uuid) {
        return estabelecimentoRepository.findByUuid(uuid)
                .map(ConsultaEstabelecimentoResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
