package br.edu.infnet.jonathanapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.jonathanapi.interfaces.CrudService;
import br.edu.infnet.jonathanapi.model.domain.Inimigo;

@Service
public class InimigoService implements CrudService<Inimigo, Integer> {

    private final Map<Integer, Inimigo> mapa = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Inimigo incluir(Inimigo inimigo) {
        inimigo.setId(nextId.getAndIncrement());
        mapa.put(inimigo.getId(), inimigo);
        return inimigo;
    }

    @Override
    public List<Inimigo> obterLista() {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public Inimigo alterar(Integer id, Inimigo inimigo) {
        if (mapa.containsKey(id)) {
            inimigo.setId(id);
            mapa.put(id, inimigo);
            return inimigo;
        }
        return null;
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

    public Inimigo obterPorId(Integer id) {
        return mapa.get(id);
    }

    public List<Inimigo> obterPorNivel(int nivelMinimo, int nivelMaximo) {
        return mapa.values().stream()
                .filter(i -> i.getVida() >= nivelMinimo * 10 && i.getVida() <= nivelMaximo * 10)
                .collect(Collectors.toList());
    }

    public Inimigo obterInimigoAleatorio() {
        List<Inimigo> inimigos = obterLista();
        if (inimigos.isEmpty()) {
            return null;
        }
        int indiceAleatorio = (int) (Math.random() * inimigos.size());
        return inimigos.get(indiceAleatorio);
    }
}