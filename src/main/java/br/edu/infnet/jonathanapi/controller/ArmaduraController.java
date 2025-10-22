package br.edu.infnet.jonathanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.jonathanapi.exceptions.ResourceNotFoundException;
import br.edu.infnet.jonathanapi.model.domain.Armadura;
import br.edu.infnet.jonathanapi.model.domain.service.ArmaduraService;

@RestController
@RequestMapping("/api/armaduras")
public class ArmaduraController {

    private final ArmaduraService armaduraService;

    public ArmaduraController(ArmaduraService armaduraService) {
        this.armaduraService = armaduraService;
    }

    @PostMapping
    public ResponseEntity<Armadura> incluir(@RequestBody Armadura armadura) {
        Armadura armaduraIncluida = armaduraService.incluir(armadura);
        return ResponseEntity.status(HttpStatus.CREATED).body(armaduraIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Armadura>> obterLista() {
        return ResponseEntity.ok(armaduraService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armadura> obterPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarArmaduraOuLancarExcecao(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armadura> alterar(@PathVariable Integer id, @RequestBody Armadura armadura) {
        buscarArmaduraOuLancarExcecao(id);
        Armadura armaduraAlterada = armaduraService.alterar(id, armadura);
        return ResponseEntity.ok(armaduraAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        buscarArmaduraOuLancarExcecao(id);
        armaduraService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Armadura buscarArmaduraOuLancarExcecao(Integer id) {
        Armadura armadura = armaduraService.obterPorId(id);
        if (armadura == null) {
            throw new ResourceNotFoundException("Armadura", id);
        }
        return armadura;
    }
}