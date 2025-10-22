package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.jonathanapi.exceptions.ResourceNotFoundException;
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

    public JogadorController(JogadorService jogadorService, ArmaService armaService, 
                            ArmaduraService armaduraService) {
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
        return ResponseEntity.ok(buscarJogadorOuLancarExcecao(id));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Jogador>> obterPorNivel(@PathVariable int nivel) {
        return ResponseEntity.ok(jogadorService.obterPorNivel(nivel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> alterar(@PathVariable Integer id, @RequestBody Jogador jogador) {
        buscarJogadorOuLancarExcecao(id);
        Jogador jogadorAlterado = jogadorService.alterar(id, jogador);
        return ResponseEntity.ok(jogadorAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        buscarJogadorOuLancarExcecao(id);
        jogadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{jogadorId}/equipar-arma/{armaId}")
    public ResponseEntity<Jogador> equiparArma(@PathVariable Integer jogadorId, 
                                                @PathVariable Integer armaId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Arma arma = buscarArmaOuLancarExcecao(armaId);

        jogador.equiparArma(arma);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-arma")
    public ResponseEntity<Jogador> desequiparArma(@PathVariable Integer jogadorId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        jogador.equiparArma(null);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/arma")
    public ResponseEntity<Arma> obterArmaEquipada(@PathVariable Integer jogadorId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Arma arma = jogador.getArma();
        if (arma == null) {
            throw new ResourceNotFoundException("Arma equipada do Jogador " + jogadorId + " não encontrada");
        }
        return ResponseEntity.ok(arma);
    }

    @PostMapping("/{jogadorId}/equipar-armadura/{armaduraId}")
    public ResponseEntity<Jogador> equiparArmadura(@PathVariable Integer jogadorId, 
                                                    @PathVariable Integer armaduraId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Armadura armadura = buscarArmaduraOuLancarExcecao(armaduraId);

        jogador.equiparArmadura(armadura);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-armadura")
    public ResponseEntity<Jogador> desequiparArmadura(@PathVariable Integer jogadorId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        jogador.equiparArmadura(null);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/armadura")
    public ResponseEntity<Armadura> obterArmaduraEquipada(@PathVariable Integer jogadorId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Armadura armadura = jogador.getArmadura();
        if (armadura == null) {
            throw new ResourceNotFoundException("Armadura equipada do Jogador " + jogadorId + " não encontrada");
        }
        return ResponseEntity.ok(armadura);
    }

    @PostMapping("/{jogadorId}/recuperar-mana")
    public ResponseEntity<Jogador> recuperarMana(@PathVariable Integer jogadorId, 
                                                  @RequestParam int quantidade) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        jogador.recuperarMana(quantidade);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @PostMapping("/{jogadorId}/regenerar-mana")
    public ResponseEntity<Jogador> regenerarManaPassivamente(@PathVariable Integer jogadorId) {
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        jogador.regenerarManaPassivamente();
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    private Jogador buscarJogadorOuLancarExcecao(Integer id) {
        Jogador jogador = jogadorService.obterPorId(id);
        if (jogador == null) {
            throw new ResourceNotFoundException("Jogador", id);
        }
        return jogador;
    }

    private Arma buscarArmaOuLancarExcecao(Integer id) {
        Arma arma = armaService.obterPorId(id);
        if (arma == null) {
            throw new ResourceNotFoundException("Arma", id);
        }
        return arma;
    }

    private Armadura buscarArmaduraOuLancarExcecao(Integer id) {
        Armadura armadura = armaduraService.obterPorId(id);
        if (armadura == null) {
            throw new ResourceNotFoundException("Armadura", id);
        }
        return armadura;
    }
}