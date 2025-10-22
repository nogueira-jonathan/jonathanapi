package br.edu.infnet.jonathanapi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.jonathanapi.interfaces.CrudService;
import br.edu.infnet.jonathanapi.model.domain.Armadura;

@Service
public class ArmaduraService implements CrudService<Armadura, Integer> {

    private final Map<Integer, Armadura> mapa = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Armadura incluir(Armadura armadura) {
        armadura.setId(nextId.getAndIncrement());
        mapa.put(armadura.getId(), armadura);
        return armadura;
    }

    @Override
    public List<Armadura> obterLista() {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public Armadura alterar(Integer id, Armadura armadura) {
        if (mapa.containsKey(id)) {
            armadura.setId(id);
            mapa.put(id, armadura);
            return armadura;
        }
        return null;
    }

    @Override
    public void excluir(Integer id) {
        mapa.remove(id);
    }

    public Armadura obterPorId(Integer id) {
        return mapa.get(id);
    }
}