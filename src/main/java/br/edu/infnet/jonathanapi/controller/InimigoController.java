package br.edu.infnet.jonathanapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.infnet.jonathanapi.exceptions.*;
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
        validarInimigo(inimigo);
        Inimigo inimigoIncluido = inimigoService.incluir(inimigo);
        return ResponseEntity.status(HttpStatus.CREATED).body(inimigoIncluido);
    }

    @GetMapping
    public ResponseEntity<List<Inimigo>> obterLista() {
        List<Inimigo> inimigos = inimigoService.obterLista();
        if (inimigos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum inimigo cadastrado no sistema");
        }
        return ResponseEntity.ok(inimigos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inimigo> obterPorId(@PathVariable Integer id) {
        validarId(id);
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
        validarId(id);
        buscarInimigoOuLancarExcecao(id);
        validarInimigo(inimigo);
        Inimigo inimigoAlterado = inimigoService.alterar(id, inimigo);
        return ResponseEntity.ok(inimigoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        validarId(id);
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

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidDataException("id", "deve ser um número positivo");
        }
    }

    private void validarInimigo(Inimigo inimigo) {
        if (inimigo == null) {
            throw new InvalidDataException("Inimigo não pode ser nulo");
        }
        if (inimigo.getNome() == null || inimigo.getNome().trim().isEmpty()) {
            throw new InvalidDataException("nome", "não pode ser vazio");
        }
        if (inimigo.getNome().length() < 3) {
            throw new InvalidDataException("nome", "deve ter no mínimo 3 caracteres");
        }
        if (inimigo.getVida() <= 0) {
            throw new InvalidDataException("vida", "deve ser maior que zero");
        }
        if (inimigo.getVida() > 999999) {
            throw new InvalidDataException("vida", "não pode ser maior que 999999");
        }
    }
}