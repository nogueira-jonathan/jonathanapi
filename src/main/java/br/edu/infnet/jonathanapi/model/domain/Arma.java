package br.edu.infnet.jonathanapi.model.domain;

public class Arma {
	private Integer id;
	private String nome;
	private int dano;
	private String categoria;
	
	public Arma(String nome, int dano, String categoria) {
		this.nome = nome;
		this.dano = dano;
		this.categoria = categoria;
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

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
    public String toString() {
        return "Arma [id=" + id + ", nome=" + nome + ", dano=" + dano + 
               ", categoria=" + categoria + "]";
    }
}
