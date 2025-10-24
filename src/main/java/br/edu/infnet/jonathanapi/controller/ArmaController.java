package br.edu.infnet.jonathanapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.jonathanapi.exceptions.*;
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
        validarArma(arma);
        Arma armaIncluida = armaService.incluir(arma);
        return ResponseEntity.status(HttpStatus.CREATED).body(armaIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Arma>> obterLista() {
        List<Arma> armas = armaService.obterLista();
        if (armas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma arma cadastrada no sistema");
        }
        return ResponseEntity.ok(armas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arma> obterPorId(@PathVariable Integer id) {
        validarId(id);
        return ResponseEntity.ok(buscarArmaOuLancarExcecao(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Arma> alterar(@PathVariable Integer id, @RequestBody Arma arma) {
        validarId(id);
        buscarArmaOuLancarExcecao(id);
        validarArma(arma);
        Arma armaAlterada = armaService.alterar(id, arma);
        return ResponseEntity.ok(armaAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        validarId(id);
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

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id", "deve ser um número positivo");
        }
    }

    private void validarArma(Arma arma) {
        if (arma == null) {
            throw new InvalidDataException("Arma não pode ser nula");
        }
        if (arma.getNome() == null || arma.getNome().trim().isEmpty()) {
            throw new InvalidDataException("nome", "não pode ser vazio");
        }
        if (arma.getNome().length() < 3) {
            throw new InvalidDataException("nome", "deve ter no mínimo 3 caracteres");
        }
        if (arma.getDano() < 0) {
            throw new InvalidDataException("dano", "não pode ser negativo");
        }
        if (arma.getDano() > 9999) {
            throw new InvalidDataException("dano", "não pode ser maior que 9999");
        }
    }
}