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
        Inimigo inimigo = inimigoService.obterPorId(id);
        if (inimigo != null) {
            return ResponseEntity.ok(inimigo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/aleatorio")
    public ResponseEntity<Inimigo> obterInimigoAleatorio() {
        Inimigo inimigo = inimigoService.obterInimigoAleatorio();
        if (inimigo != null) {
            return ResponseEntity.ok(inimigo);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inimigo> alterar(@PathVariable Integer id, @RequestBody Inimigo inimigo) {
        Inimigo inimigoAlterado = inimigoService.alterar(id, inimigo);
        if (inimigoAlterado != null) {
            return ResponseEntity.ok(inimigoAlterado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        inimigoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}