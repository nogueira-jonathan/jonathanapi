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
        Magia magia = magiaService.obterPorId(id);
        if (magia != null) {
            return ResponseEntity.ok(magia);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/rank/{rank}")
    public ResponseEntity<List<Magia>> obterPorRank(@PathVariable String rank) {
        List<Magia> magias = magiaService.obterPorRank(rank);
        return ResponseEntity.ok(magias);
    }

    @GetMapping("/custo")
    public ResponseEntity<List<Magia>> obterPorCustoMaximo(@RequestParam int custoMaximo) {
        List<Magia> magias = magiaService.obterPorCustoMaximo(custoMaximo);
        return ResponseEntity.ok(magias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magia> alterar(@PathVariable Integer id, @RequestBody Magia magia) {
        Magia magiaAlterada = magiaService.alterar(id, magia);
        if (magiaAlterada != null) {
            return ResponseEntity.ok(magiaAlterada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        magiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}