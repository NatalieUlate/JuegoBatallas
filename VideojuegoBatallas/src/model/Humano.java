/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Natalie Ulate Rojas
 */
public class Humano extends Personaje {

    public Humano(String nombre, Raza raza, int fuerza, int energia, int vida, Arma arma) {
        super(nombre, raza, fuerza, energia, vida, arma);
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danoBase = random.nextInt(5) + 1;
        double danoFinal = danoBase;

        if (this.armaEquipada.getNombre().equalsIgnoreCase("Escopeta")) {
            danoFinal *= 1.02;
        }
        
        System.out.println(this.nombre + " ataca con su " + this.armaEquipada.getNombre() + "!");
        objetivo.recibirDano((int) danoFinal);
    }

    @Override
    public void sanar() {
        int danoRecibido = this.vidaMaxima - this.vidaActual;
        int curacion = (int) (danoRecibido * 0.50);
        this.vidaActual += curacion;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
        System.out.println(this.nombre + " gasta un turno en comer y recupera " + curacion + " puntos de vida.");
    }
}