/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


/**
 *
 * @author Natalie Ulate Rojas
 */
public class Orco extends Personaje {

    public Orco(String nombre, Raza raza, int fuerza, int energia, int vida, Arma arma) {
        super(nombre, raza, fuerza, energia, vida, arma);
    }

    @Override
    public void atacar(Personaje objetivo) {
        int danoBase = random.nextInt(5) + 1; 
        double danoFinal = danoBase;
        
        if (this.efectosDeEstado.containsKey("bono_ataque")) {
            danoFinal *= 1.15;
            this.efectosDeEstado.remove("bono_ataque"); 
            System.out.println("¡El Orco ataca con furia potenciada!");
        }

        System.out.println(this.nombre + " ataca brutalmente con su " + this.armaEquipada.getNombre() + "!");
        objetivo.recibirDano((int) danoFinal);


        if (this.armaEquipada.getNombre().equalsIgnoreCase("Hacha")) {
            System.out.println("¡El golpe provoca una herida sangrante!");
            objetivo.efectosDeEstado.put("sangrado", 2); 
        }
    }

    @Override
    public void sanar() {
        int danoRecibido = this.vidaMaxima - this.vidaActual;
        int curacion = (int) (danoRecibido * 0.25);
        this.vidaActual += curacion;
        if (this.vidaActual > this.vidaMaxima) {
            this.vidaActual = this.vidaMaxima;
        }
        
        System.out.println(this.nombre + " bebe una pócima de curación, recuperando " + curacion + " de vida.");
        System.out.println("¡Se enfurece y su siguiente ataque será más fuerte!");
        this.efectosDeEstado.put("bono_ataque", 1); 
    }
}
