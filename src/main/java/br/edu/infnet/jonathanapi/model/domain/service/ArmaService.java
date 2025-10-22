package br.edu.infnet.jonathanapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.jonathanapi.interfaces.CrudService;
import br.edu.infnet.jonathanapi.model.domain.Arma;

@Service
public class ArmaService implements CrudService<Arma, Integer> {

    private final Map<Integer, Arma> mapa = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Arma incluir(Arma arma) {
        arma.setId(nextId.getAndIncrement());
        mapa.put(arma.getId(), arma);
        return arma;
    }

    @Override
    public List<Arma> obterLista() {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public Arma alterar(Integer id, Arma arma) {
        if (mapa.containsKey(id)) {
            arma.setId(id);
            mapa.put(id, arma);
            return arma;
        }
        return null;
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

    public Arma obterPorId(Integer id) {
        return mapa.get(id);
    }
}