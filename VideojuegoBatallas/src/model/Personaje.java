/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Natalie Ulate Rojas
 */
public abstract class Personaje {

    protected int id;
    protected String nombre; 
    protected Raza raza;
    protected int fuerza;
    protected int energia;
    protected int vidaMaxima;
    protected int vidaActual;
    protected Arma armaEquipada;
    protected Map<String, Integer> efectosDeEstado;
    protected Random random = new Random();

    public Personaje(String nombre, Raza raza, int fuerza, int energia, int vida, Arma arma) {
        this.nombre = nombre;
        this.raza = raza;
        this.fuerza = fuerza;
        this.energia = energia;
        this.vidaMaxima = vida;
        this.vidaActual = vida;
        this.armaEquipada = arma;
        this.efectosDeEstado = new HashMap<>();
    }


    public abstract void atacar(Personaje objetivo);

    public abstract void sanar();

    public void recibirDano(int dano) {
        this.vidaActual -= dano;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
        System.out.println("¡" + this.nombre + " recibe " + dano + " puntos de daño!");
    }

    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    public void aplicarEfectosDeTurno() {
        if (efectosDeEstado.containsKey("sangrado")) {
            int danoSangrado = 3; 
            recibirDano(danoSangrado);
            System.out.println(this.nombre + " sufre " + danoSangrado + " de daño por sangrado.");

            int turnosRestantes = efectosDeEstado.get("sangrado") - 1;
            if (turnosRestantes <= 0) {
                efectosDeEstado.remove("sangrado");
                System.out.println("El sangrado de " + this.nombre + " ha parado.");
            } else {
                efectosDeEstado.put("sangrado", turnosRestantes);
            }
        }
    }


    public String getNombre() {
        return nombre;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }
}
