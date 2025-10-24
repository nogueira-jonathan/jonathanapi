package br.edu.infnet.jonathanapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.jonathanapi.exceptions.*;
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
        validarJogador(jogador);
        Jogador jogadorIncluido = jogadorService.incluir(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(jogadorIncluido);
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> obterLista() {
        List<Jogador> jogadores = jogadorService.obterLista();
        if (jogadores.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum jogador cadastrado no sistema");
        }
        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> obterPorId(@PathVariable Integer id) {
        validarId(id);
        return ResponseEntity.ok(buscarJogadorOuLancarExcecao(id));
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Jogador>> obterPorNivel(@PathVariable int nivel) {
        if (nivel < 1) {
            throw new InvalidDataException("nivel", "deve ser maior que 0");
        }
        if (nivel > 100) {
            throw new InvalidDataException("nivel", "não pode ser maior que 100");
        }
        List<Jogador> jogadores = jogadorService.obterPorNivel(nivel);
        if (jogadores.isEmpty()) {
            throw new ResourceNotFoundException("Jogador", "nível", nivel);
        }
        return ResponseEntity.ok(jogadores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> alterar(@PathVariable Integer id, @RequestBody Jogador jogador) {
        validarId(id);
        buscarJogadorOuLancarExcecao(id);
        validarJogador(jogador);
        Jogador jogadorAlterado = jogadorService.alterar(id, jogador);
        return ResponseEntity.ok(jogadorAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        validarId(id);
        buscarJogadorOuLancarExcecao(id);
        jogadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{jogadorId}/equipar-arma/{armaId}")
    public ResponseEntity<Jogador> equiparArma(@PathVariable Integer jogadorId, 
                                                @PathVariable Integer armaId) {
        validarId(jogadorId);
        validarId(armaId);
        
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Arma arma = buscarArmaOuLancarExcecao(armaId);
        
        if (jogador.getArma() != null) {
            throw new ItemAlreadyEquippedException("Arma", jogador.getArma().getNome());
        }
        
        jogador.equiparArma(arma);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-arma")
    public ResponseEntity<Jogador> desequiparArma(@PathVariable Integer jogadorId) {
        validarId(jogadorId);
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        
        if (jogador.getArma() == null) {
            throw new ItemNotEquippedException("Arma");
        }
        
        jogador.equiparArma(null);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/arma")
    public ResponseEntity<Arma> obterArmaEquipada(@PathVariable Integer jogadorId) {
        validarId(jogadorId);
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Arma arma = jogador.getArma();
        
        if (arma == null) {
            throw new ItemNotEquippedException("Arma");
        }
        
        return ResponseEntity.ok(arma);
    }

    @PostMapping("/{jogadorId}/equipar-armadura/{armaduraId}")
    public ResponseEntity<Jogador> equiparArmadura(@PathVariable Integer jogadorId, 
                                                    @PathVariable Integer armaduraId) {
        validarId(jogadorId);
        validarId(armaduraId);
        
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Armadura armadura = buscarArmaduraOuLancarExcecao(armaduraId);
        
        if (jogador.getArmadura() != null) {
            throw new ItemAlreadyEquippedException("Armadura", jogador.getArmadura().getNome());
        }
        
        jogador.equiparArmadura(armadura);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @DeleteMapping("/{jogadorId}/desequipar-armadura")
    public ResponseEntity<Jogador> desequiparArmadura(@PathVariable Integer jogadorId) {
        validarId(jogadorId);
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        
        if (jogador.getArmadura() == null) {
            throw new ItemNotEquippedException("Armadura");
        }
        
        jogador.equiparArmadura(null);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/{jogadorId}/armadura")
    public ResponseEntity<Armadura> obterArmaduraEquipada(@PathVariable Integer jogadorId) {
        validarId(jogadorId);
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        Armadura armadura = jogador.getArmadura();
        
        if (armadura == null) {
            throw new ItemNotEquippedException("Armadura");
        }
        
        return ResponseEntity.ok(armadura);
    }

    @PostMapping("/{jogadorId}/recuperar-mana")
    public ResponseEntity<Jogador> recuperarMana(@PathVariable Integer jogadorId, 
                                                  @RequestParam int quantidade) {
        validarId(jogadorId);
        
        if (quantidade <= 0) {
            throw new InvalidDataException("quantidade", "deve ser maior que 0");
        }
        if (quantidade > 10000) {
            throw new InvalidDataException("quantidade", "não pode ser maior que 10000");
        }
        
        Jogador jogador = buscarJogadorOuLancarExcecao(jogadorId);
        jogador.recuperarMana(quantidade);
        jogadorService.alterar(jogadorId, jogador);
        return ResponseEntity.ok(jogador);
    }

    @PostMapping("/{jogadorId}/regenerar-mana")
    public ResponseEntity<Jogador> regenerarManaPassivamente(@PathVariable Integer jogadorId) {
        validarId(jogadorId);
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

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id", "deve ser um número positivo");
        }
    }

    private void validarJogador(Jogador jogador) {
        if (jogador == null) {
            throw new InvalidDataException("Jogador não pode ser nulo");
        }
        if (jogador.getNome() == null || jogador.getNome().trim().isEmpty()) {
            throw new InvalidDataException("nome", "não pode ser vazio");
        }
        if (jogador.getNome().length() < 3) {
            throw new InvalidDataException("nome", "deve ter no mínimo 3 caracteres");
        }
        if (jogador.getNivel() < 1) {
            throw new InvalidDataException("nivel", "deve ser maior que 0");
        }
        if (jogador.getNivel() > 100) {
            throw new InvalidDataException("nivel", "não pode ser maior que 100");
        }
        if (jogador.getVida() < 0) {
            throw new InvalidDataException("vida", "não pode ser negativo");
        }
        if (jogador.getMana() < 0) {
            throw new InvalidDataException("mana", "não pode ser negativo");
        }
    }
}