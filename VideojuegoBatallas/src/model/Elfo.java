/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Natalie Ulate Rojas
 */
public class Elfo extends Personaje {
    
    private String tipoMagia;

    public Elfo(String nombre, Raza raza, int fuerza, int energia, int vida, Arma arma, String tipoMagia) {
        super(nombre, raza, fuerza, energia, vida, arma);
        this.tipoMagia = tipoMagia;

        if (this.tipoMagia.equalsIgnoreCase("Agua")) {
            this.vidaMaxima = 115;
            this.vidaActual = 115;
        }
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danoBase = random.nextInt(5) + 1; 
        double danoFinal = danoBase;

        switch (tipoMagia.toLowerCase()) {
            case "fuego":
                danoFinal *= 1.10;
                break;
            case "tierra":
                danoFinal *= 1.02; 
                break;
        }
        
        System.out.println(this.nombre + " lanza un hechizo de " + this.tipoMagia + "!");
        objetivo.recibirDano((int) danoFinal);
    }

    @Override
    public void sanar() {
        int danoRecibido = this.vidaMaxima - this.vidaActual;
        double porcentajeCuracion = this.tipoMagia.equalsIgnoreCase("Agua") ? 0.90 : 0.75;
        int curacion = (int) (danoRecibido * porcentajeCuracion);
        
        this.vidaActual += curacion;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
        System.out.println(this.nombre + " usa un hechizo de sanación y recupera " + curacion + " puntos de vida.");
    }
}
