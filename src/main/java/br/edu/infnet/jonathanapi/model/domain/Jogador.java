package br.edu.infnet.jonathanapi.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador extends Personagem { 
    private int nivel;
    private int poçõesRestantes = 3;
    private int mana = 100;
    private int manaMaxima;
    private int vidaMaxima;


    public int getMana() {
        return mana;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setMana(int mana) {
        this.mana = Math.min(mana, manaMaxima);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void recuperarMana(int quantidade) {
        this.mana = Math.min(this.mana + quantidade, manaMaxima);
        exibirMensagemRecuperacaoMana(quantidade);
    }

    private void exibirMensagemRecuperacaoMana(int quantidade) {
        System.out.printf("\nVocê recuperou %d de mana. Mana atual: %d%n", quantidade, this.mana);                                                                                                  
    }

    public void regenerarManaPassivamente() {
        int regeneracao = 5;
        this.mana = Math.min(this.mana + regeneracao, manaMaxima);
        System.out.printf("\nVocê regenerou %d pontos de mana. Mana atual: %d%n", regeneracao, this.mana); 
    }

    private void mostrarManaAtual() {
        System.out.printf("\nMana atual: %d%n", this.mana);
    }

} 

