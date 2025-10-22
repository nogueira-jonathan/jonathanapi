package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.jonathanapi.model.domain.Arma;
import br.edu.infnet.jonathanapi.model.domain.Armadura;
import br.edu.infnet.jonathanapi.model.domain.Jogador;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaService;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaduraService;
import br.edu.infnet.jonathanapi.model.domain.service.JogadorService;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;
    private final ArmaService armaService;
    private final ArmaduraService armaduraService;

    public JogadorController(JogadorService jogadorService, ArmaService armaService, ArmaduraService armaduraService) {
        this.jogadorService = jogadorService;
        this.armaService = armaService;
        this.armaduraService = armaduraService;
    }

    @PostMapping
    public ResponseEntity<Jogador> incluir(@RequestBody Jogador jogador) {
        Jogador jogadorIncluido = jogadorService.incluir(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(jogadorIncluido);
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> obterLista() {
        return ResponseEntity.ok(jogadorService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> obterPorId(@PathVariable Integer id) {
        Jogador jogador = jogadorService.obterPorId(id);
        if (jogador != null) {
            return ResponseEntity.ok(jogador);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> alterar(@PathVariable Integer id, @RequestBody Jogador jogador) {
        Jogador jogadorAlterado = jogadorService.alterar(id, jogador);
        if (jogadorAlterado != null) {
            return ResponseEntity.ok(jogadorAlterado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        jogadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{jogadorId}/equipar-arma/{armaId}")
    public ResponseEntity<Jogador> equiparArma(@PathVariable Integer jogadorId, @PathVariable Integer armaId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);
        Arma arma = armaService.obterPorId(armaId);

        if (jogador == null || arma == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.equiparArma(arma);
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-arma")
    public ResponseEntity<Jogador> desequiparArma(@PathVariable Integer jogadorId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.equiparArma(null);
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/arma")
    public ResponseEntity<Arma> obterArmaEquipada(@PathVariable Integer jogadorId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        Arma arma = jogador.getArma();
        if (arma != null) {
            return ResponseEntity.ok(arma);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{jogadorId}/equipar-armadura/{armaduraId}")
    public ResponseEntity<Jogador> equiparArmadura(@PathVariable Integer jogadorId, @PathVariable Integer armaduraId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);
        Armadura armadura = armaduraService.obterPorId(armaduraId);

        if (jogador == null || armadura == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.equiparArmadura(armadura);
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-armadura")
    public ResponseEntity<Jogador> desequiparArmadura(@PathVariable Integer jogadorId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.equiparArmadura(null);
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/armadura")
    public ResponseEntity<Armadura> obterArmaduraEquipada(@PathVariable Integer jogadorId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        Armadura armadura = jogador.getArmadura();
        if (armadura != null) {
            return ResponseEntity.ok(armadura);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{jogadorId}/recuperar-mana")
    public ResponseEntity<Jogador> recuperarMana(@PathVariable Integer jogadorId, @RequestParam int quantidade) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.recuperarMana(quantidade);
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

    @PostMapping("/{jogadorId}/regenerar-mana")
    public ResponseEntity<Jogador> regenerarManaPassivamente(@PathVariable Integer jogadorId) {
        Jogador jogador = jogadorService.obterPorId(jogadorId);

        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }

        jogador.regenerarManaPassivamente();
        jogadorService.alterar(jogadorId, jogador);

        return ResponseEntity.ok(jogador);
    }

}