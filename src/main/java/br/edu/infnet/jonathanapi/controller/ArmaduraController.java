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
        Armadura armadura = armaduraService.obterPorId(id);
        if (armadura != null) {
            return ResponseEntity.ok(armadura);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armadura> alterar(@PathVariable Integer id, @RequestBody Armadura armadura) {
        Armadura armaduraAlterada = armaduraService.alterar(id, armadura);
        if (armaduraAlterada != null) {
            return ResponseEntity.ok(armaduraAlterada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        armaduraService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}