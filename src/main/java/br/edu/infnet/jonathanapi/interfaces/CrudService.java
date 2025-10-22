package br.edu.infnet.jonathanapi.interfaces;

import java.util.List;

public interface CrudService<T, ID> {
	T incluir(T entidade);
	List<T> obterLista();
	T alterar(ID id, T entidade);
	void excluir(ID id);

}
