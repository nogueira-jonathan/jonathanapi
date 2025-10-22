package br.edu.infnet.jonathanapi.model.domain;

public class Magia {
	
	private Integer id;
	private String nome;
    private int dano;
    private String rank;
    private int custoMana;
    
	public Magia(String nome, int dano, String rank, int custoMana) {
		this.nome = nome;
		this.dano = dano;
		this.rank = rank;
		this.custoMana = custoMana;
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

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	@Override
    public String toString() {
        return "Magia [id=" + id + ", nome=" + nome + ", dano=" + dano + 
               ", rank=" + rank + ", custoMana=" + custoMana + "]";
    }

    
}
