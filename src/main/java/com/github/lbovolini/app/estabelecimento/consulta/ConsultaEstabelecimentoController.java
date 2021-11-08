package com.github.lbovolini.app.estabelecimento.consulta;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/estabelecimentos")
class ConsultaEstabelecimentoController {

    private final ConsultaEstabelecimentoCache consultaEstabelecimentoCache;

    ConsultaEstabelecimentoController(ConsultaEstabelecimentoCache consultaEstabelecimentoCache) {
        this.consultaEstabelecimentoCache = consultaEstabelecimentoCache;
    }

    @GetMapping("/{uuid}")
    ResponseEntity<ConsultaEstabelecimentoResponse> consultaPorUuid(@PathVariable UUID uuid) {
        return consultaEstabelecimentoCache.consultaPorUuid(uuid)
                .map(ConsultaEstabelecimentoResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
