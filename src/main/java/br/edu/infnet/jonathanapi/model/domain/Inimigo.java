package br.edu.infnet.jonathanapi.model.domain;

public class Inimigo extends Personagem {
    
    private String tipo; // Pode ser: "comum", "elite", "boss"
    private int recompensaExperiencia;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getRecompensaExperiencia() {
        return recompensaExperiencia;
    }

    public void setRecompensaExperiencia(int recompensaExperiencia) {
        this.recompensaExperiencia = recompensaExperiencia;
    }

    @Override
    public String toString() {
        return "Inimigo [id=" + getId() +
               ", nome=" + getNome() +
               ", vida=" + getVida() +
               ", forca=" + getForca() +
               ", constituicao=" + getConstituicao() +
               ", agilidade=" + getAgilidade() +
               ", destreza=" + getDestreza() +
               ", tipo=" + tipo +
               ", recompensaExperiencia=" + recompensaExperiencia + "]";
    }
}