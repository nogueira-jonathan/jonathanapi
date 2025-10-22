package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.jonathanapi.exceptions.ResourceNotFoundException;
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
        Magia magiaIncluida = magiaService.incluir(magia);
        return ResponseEntity.status(HttpStatus.CREATED).body(magiaIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Magia>> obterLista() {
        return ResponseEntity.ok(magiaService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magia> obterPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarMagiaOuLancarExcecao(id));
    }

    @GetMapping("/rank/{rank}")
    public ResponseEntity<List<Magia>> obterPorRank(@PathVariable String rank) {
        return ResponseEntity.ok(magiaService.obterPorRank(rank));
    }

    @GetMapping("/custo")
    public ResponseEntity<List<Magia>> obterPorCustoMaximo(@RequestParam int custoMaximo) {
        return ResponseEntity.ok(magiaService.obterPorCustoMaximo(custoMaximo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magia> alterar(@PathVariable Integer id, @RequestBody Magia magia) {
        buscarMagiaOuLancarExcecao(id);
        Magia magiaAlterada = magiaService.alterar(id, magia);
        return ResponseEntity.ok(magiaAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
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
}