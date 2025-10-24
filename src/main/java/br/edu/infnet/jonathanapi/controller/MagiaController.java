package br.edu.infnet.jonathanapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.jonathanapi.exceptions.*;
import br.edu.infnet.jonathanapi.model.domain.Magia;
import br.edu.infnet.jonathanapi.model.domain.service.MagiaService;

@RestController
@RequestMapping("/api/magias")
public class MagiaController {

    private final MagiaService magiaService;

    public MagiaController(MagiaService magiaService) {
        this.magiaService = magiaService;
    }

    @PostMapping
    public ResponseEntity<Magia> incluir(@RequestBody Magia magia) {
        validarMagia(magia);
        Magia magiaIncluida = magiaService.incluir(magia);
        return ResponseEntity.status(HttpStatus.CREATED).body(magiaIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Magia>> obterLista() {
        List<Magia> magias = magiaService.obterLista();
        if (magias.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma magia cadastrada no sistema");
        }
        return ResponseEntity.ok(magias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magia> obterPorId(@PathVariable Integer id) {
        validarId(id);
        return ResponseEntity.ok(buscarMagiaOuLancarExcecao(id));
    }

    @GetMapping("/rank/{rank}")
    public ResponseEntity<List<Magia>> obterPorRank(@PathVariable String rank) {
        if (rank == null || rank.trim().isEmpty()) {
            throw new InvalidDataException("rank", "não pode ser vazio");
        }
        
        List<String> ranksValidos = List.of("S", "A", "B", "C", "D", "E", "F");
        if (!ranksValidos.contains(rank.toUpperCase())) {
            throw new InvalidDataException("rank", 
                "deve ser um dos seguintes valores: S, A, B, C, D, E, F");
        }
        
        List<Magia> magias = magiaService.obterPorRank(rank);
        if (magias.isEmpty()) {
            throw new ResourceNotFoundException("Magia", "rank", rank);
        }
        return ResponseEntity.ok(magias);
    }

    @GetMapping("/custo")
    public ResponseEntity<List<Magia>> obterPorCustoMaximo(@RequestParam int custoMaximo) {
        if (custoMaximo < 0) {
            throw new InvalidDataException("custoMaximo", "não pode ser negativo");
        }
        if (custoMaximo > 10000) {
            throw new InvalidDataException("custoMaximo", "não pode ser maior que 10000");
        }
        
        List<Magia> magias = magiaService.obterPorCustoMaximo(custoMaximo);
        if (magias.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Nenhuma magia com custo máximo de %d encontrada", custoMaximo));
        }
        return ResponseEntity.ok(magias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magia> alterar(@PathVariable Integer id, @RequestBody Magia magia) {
        validarId(id);
        buscarMagiaOuLancarExcecao(id);
        validarMagia(magia);
        Magia magiaAlterada = magiaService.alterar(id, magia);
        return ResponseEntity.ok(magiaAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        validarId(id);
        buscarMagiaOuLancarExcecao(id);
        magiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Magia buscarMagiaOuLancarExcecao(Integer id) {
        Magia magia = magiaService.obterPorId(id);
        if (magia == null) {
            throw new ResourceNotFoundException("Magia", id);
        }
        return magia;
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id", "deve ser um número positivo");
        }
    }

    private void validarMagia(Magia magia) {
        if (magia == null) {
            throw new InvalidDataException("Magia não pode ser nula");
        }
        if (magia.getNome() == null || magia.getNome().trim().isEmpty()) {
            throw new InvalidDataException("nome", "não pode ser vazio");
        }
        if (magia.getNome().length() < 3) {
            throw new InvalidDataException("nome", "deve ter no mínimo 3 caracteres");
        }
        if (magia.getCustoMana() < 0) {
            throw new InvalidDataException("custoMana", "não pode ser negativo");
        }
        if (magia.getCustoMana() > 10000) {
            throw new InvalidDataException("custoMana", "não pode ser maior que 10000");
        }
        if (magia.getRank() == null || magia.getRank().trim().isEmpty()) {
            throw new InvalidDataException("rank", "não pode ser vazio");
        }
        
        List<String> ranksValidos = List.of("S", "A", "B", "C", "D", "E", "F");
        if (!ranksValidos.contains(magia.getRank().toUpperCase())) {
            throw new InvalidDataException("rank", 
                "deve ser um dos seguintes valores: S, A, B, C, D, E, F");
        }
    }
}