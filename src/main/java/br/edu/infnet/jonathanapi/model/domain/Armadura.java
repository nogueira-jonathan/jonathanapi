package br.edu.infnet.jonathanapi.model.domain;

public class Armadura {
	
	private Integer id;
	private String nome;
	private int defesa;
	
	public Armadura(String nome, int defesa) {
		super();
		this.nome = nome;
		this.defesa = defesa;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getDefesa() {
		return defesa;
	}
	public void setDefesa(int defesa) {
		this.defesa = defesa;
	}
	
	@Override
    public String toString() {
        return "Armadura [id=" + id + ", nome=" + nome + ", defesa=" + defesa + "]";
    }
	
}
