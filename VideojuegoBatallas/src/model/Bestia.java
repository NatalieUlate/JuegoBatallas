/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Natalie Ulate Rojas
 */
public class Bestia extends Personaje {

    public Bestia(String nombre, Raza raza, int fuerza, int energia, int vida, Arma arma) {
        super(nombre, raza, fuerza, energia, vida, arma);
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danoFinal = 0;

        if (this.armaEquipada.getNombre().equalsIgnoreCase("Puños")) {
            danoFinal = 25; 
            System.out.println(this.nombre + " ataca con sus puños salvajes!");
            objetivo.recibirDano(danoFinal);
            
            System.out.println("¡El esfuerzo del ataque hiere a la Bestia!");
            this.recibirDano(10);

        } else if (this.armaEquipada.getNombre().equalsIgnoreCase("Espada")) {
            danoFinal = random.nextInt(10) + 1; 
            System.out.println(this.nombre + " ataca con su " + this.armaEquipada.getNombre() + "!");
            objetivo.recibirDano(danoFinal);
        }
    }

    @Override
    public void sanar() {
        int danoRecibido = this.vidaMaxima - this.vidaActual;
        int curacion = (int) (danoRecibido * 0.45);
        this.vidaActual += curacion;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
        System.out.println(this.nombre + " se echa a dormir un momento y recupera " + curacion + " puntos de vida.");
    }
}
