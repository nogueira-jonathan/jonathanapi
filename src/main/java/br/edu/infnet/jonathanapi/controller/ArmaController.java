package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.jonathanapi.exceptions.ResourceNotFoundException;
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
        return ResponseEntity.ok(buscarArmaOuLancarExcecao(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arma> alterar(@PathVariable Integer id, @RequestBody Arma arma) {
        buscarArmaOuLancarExcecao(id);
        Arma armaAlterada = armaService.alterar(id, arma);
        return ResponseEntity.ok(armaAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        buscarArmaOuLancarExcecao(id);
        armaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Arma buscarArmaOuLancarExcecao(Integer id) {
        Arma arma = armaService.obterPorId(id);
        if (arma == null) {
            throw new ResourceNotFoundException("Arma", id);
        }
        return arma;
    }
}