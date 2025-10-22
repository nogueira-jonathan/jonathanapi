package br.edu.infnet.jonathanapi.model.domain;

public class Personagem {
	
	private Integer id;
	
	private String nome; 
	private int vida;
	private int forca;
	private int constituicao;
	private int agilidade;
	private int destreza;
	private Arma arma;
	private Armadura armadura;
	
	public String toString() {
        return "Personagem [id=" + id + 
               ", nome=" + nome + 
               ", vida=" + vida + 
               ", forca=" + forca + 
               ", constituicao=" + constituicao + 
               ", agilidade=" + agilidade + 
               ", destreza=" + destreza + "]";
    }

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getForca() {
		return forca;
	}

	public void setForca(int forca) {
		this.forca = forca;
	}

	public int getConstituicao() {
		return constituicao;
	}

	public void setConstituicao(int constituicao) {
		this.constituicao = constituicao;
	}

	public int getAgilidade() {
		return agilidade;
	}

	public void setAgilidade(int agilidade) {
		this.agilidade = agilidade;
	}


	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}
	
	public Arma getArma() {
	    return arma;
	}

	public void equiparArma(Arma arma) {
	    this.arma = arma;
	}

	public Armadura getArmadura() {
	    return armadura;
	}

	public void equiparArmadura(Armadura armadura) {
	    this.armadura = armadura;
	}

}
	 	