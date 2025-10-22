package br.edu.infnet.jonathanapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.infnet.jonathanapi.interfaces.CrudService;
import br.edu.infnet.jonathanapi.model.domain.Magia;

@Service
public class MagiaService implements CrudService<Magia, Integer> {

    private final Map<Integer, Magia> mapa = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Magia incluir(Magia magia) {
        magia.setId(nextId.getAndIncrement());
        mapa.put(magia.getId(), magia);
        return magia;
    }

    @Override
    public List<Magia> obterLista() {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public Magia alterar(Integer id, Magia magia) {
        if (mapa.containsKey(id)) {
            magia.setId(id);
            mapa.put(id, magia);
            return magia;
        }
        return null;
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

    public Magia obterPorId(Integer id) {
        return mapa.get(id);
    }

    public List<Magia> obterPorRank(String rank) {
        return mapa.values().stream()
                .filter(m -> m.getRank().equalsIgnoreCase(rank))
                .collect(Collectors.toList());
    }

    public List<Magia> obterPorCustoMaximo(int custoMaximo) {
        return mapa.values().stream()
                .filter(m -> m.getCustoMana() <= custoMaximo)
                .collect(Collectors.toList());
    }
}