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
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.jonathanapi.model.domain.Arma;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaService;

@RestController
@RequestMapping("/api/armas")
public class ArmaController {

    private final ArmaService armaService;

    public ArmaController(ArmaService armaService) {
        this.armaService = armaService;
    }

    @PostMapping
    public ResponseEntity<Arma> incluir(@RequestBody Arma arma) {
        Arma armaIncluida = armaService.incluir(arma);
        return ResponseEntity.status(HttpStatus.CREATED).body(armaIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Arma>> obterLista() {
        return ResponseEntity.ok(armaService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arma> obterPorId(@PathVariable Integer id) {
        Arma arma = armaService.obterPorId(id);
        if (arma != null) {
            return ResponseEntity.ok(arma);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arma> alterar(@PathVariable Integer id, @RequestBody Arma arma) {
        Arma armaAlterada = armaService.alterar(id, arma);
        if (armaAlterada != null) {
            return ResponseEntity.ok(armaAlterada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        armaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}