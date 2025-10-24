package br.edu.infnet.jonathanapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.jonathanapi.exceptions.*;
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
        validarArmadura(armadura);
        Armadura armaduraIncluida = armaduraService.incluir(armadura);
        return ResponseEntity.status(HttpStatus.CREATED).body(armaduraIncluida);
    }

    @GetMapping
    public ResponseEntity<List<Armadura>> obterLista() {
        List<Armadura> armaduras = armaduraService.obterLista();
        if (armaduras.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma armadura cadastrada no sistema");
        }
        return ResponseEntity.ok(armaduras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armadura> obterPorId(@PathVariable Integer id) {
        validarId(id);
        return ResponseEntity.ok(buscarArmaduraOuLancarExcecao(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armadura> alterar(@PathVariable Integer id, @RequestBody Armadura armadura) {
        validarId(id);
        buscarArmaduraOuLancarExcecao(id);
        validarArmadura(armadura);
        Armadura armaduraAlterada = armaduraService.alterar(id, armadura);
        return ResponseEntity.ok(armaduraAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        validarId(id);
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

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id", "deve ser um número positivo");
        }
    }

    private void validarArmadura(Armadura armadura) {
        if (armadura == null) {
            throw new InvalidDataException("Armadura não pode ser nula");
        }
        if (armadura.getNome() == null || armadura.getNome().trim().isEmpty()) {
            throw new InvalidDataException("nome", "não pode ser vazio");
        }
        if (armadura.getNome().length() < 3) {
            throw new InvalidDataException("nome", "deve ter no mínimo 3 caracteres");
        }
        if (armadura.getDefesa() < 0) {
            throw new InvalidDataException("defesa", "não pode ser negativo");
        }
        if (armadura.getDefesa() > 9999) {
            throw new InvalidDataException("defesa", "não pode ser maior que 9999");
        }
    }
}