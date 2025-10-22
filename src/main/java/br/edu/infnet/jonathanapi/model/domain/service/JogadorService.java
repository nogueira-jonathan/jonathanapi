package br.edu.infnet.jonathanapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.jonathanapi.interfaces.CrudService;
import br.edu.infnet.jonathanapi.model.domain.Jogador;

@Service
public class JogadorService implements CrudService<Jogador, Integer> {

    private final Map<Integer, Jogador> mapa = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Jogador incluir(Jogador jogador) {
        jogador.setId(nextId.getAndIncrement());
        mapa.put(jogador.getId(), jogador);
        return jogador;
    }

    @Override
    public List<Jogador> obterLista() {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public Jogador alterar(Integer id, Jogador jogador) {
        if (mapa.containsKey(id)) {
            jogador.setId(id);
            mapa.put(id, jogador);
            return jogador;
        }
        return null;
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

    public Jogador obterPorId(Integer id) {
        return mapa.get(id);
    }

    public List<Jogador> obterPorNivel(int nivel) {
        return mapa.values().stream()
                .filter(j -> j.getNivel() == nivel)
                .collect(Collectors.toList());
    }

    public List<Jogador> obterJogadoresVivos() {
        return mapa.values().stream()
                .filter(j -> j.getVida() > 0)
                .collect(Collectors.toList());
    }

    public Jogador obterPorNome(String nome) {
        return mapa.values().stream()
                .filter(j -> j.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
}