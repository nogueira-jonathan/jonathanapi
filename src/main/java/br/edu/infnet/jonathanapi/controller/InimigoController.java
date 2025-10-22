package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.jonathanapi.exceptions.ResourceNotFoundException;
import br.edu.infnet.jonathanapi.model.domain.Inimigo;
import br.edu.infnet.jonathanapi.model.domain.service.InimigoService;

@RestController
@RequestMapping("/api/inimigos")
public class InimigoController {

    private final InimigoService inimigoService;

    public InimigoController(InimigoService inimigoService) {
        this.inimigoService = inimigoService;
    }

    @PostMapping
    public ResponseEntity<Inimigo> incluir(@RequestBody Inimigo inimigo) {
        Inimigo inimigoIncluido = inimigoService.incluir(inimigo);
        return ResponseEntity.status(HttpStatus.CREATED).body(inimigoIncluido);
    }

    @GetMapping
    public ResponseEntity<List<Inimigo>> obterLista() {
        return ResponseEntity.ok(inimigoService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inimigo> obterPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarInimigoOuLancarExcecao(id));
    }

    @GetMapping("/aleatorio")
    public ResponseEntity<Inimigo> obterInimigoAleatorio() {
        Inimigo inimigo = inimigoService.obterInimigoAleatorio();
        if (inimigo == null) {
            throw new ResourceNotFoundException("Nenhum inimigo disponível no sistema");
        }
        return ResponseEntity.ok(inimigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inimigo> alterar(@PathVariable Integer id, @RequestBody Inimigo inimigo) {
        buscarInimigoOuLancarExcecao(id);
        Inimigo inimigoAlterado = inimigoService.alterar(id, inimigo);
        return ResponseEntity.ok(inimigoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        buscarInimigoOuLancarExcecao(id);
        inimigoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Inimigo buscarInimigoOuLancarExcecao(Integer id) {
        Inimigo inimigo = inimigoService.obterPorId(id);
        if (inimigo == null) {
            throw new ResourceNotFoundException("Inimigo", id);
        }
        return inimigo;
    }
}