/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Natalie Ulate Rojas
 */
public class Arma {
    private int id;
    private String nombre;
    private String tipo;
    private int danoMinimo;
    private int danoMaximo;
    private String modificadores; 


    public Arma(String nombre, String tipo, int danoMinimo, int danoMaximo, String modificadores) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.danoMinimo = danoMinimo;
        this.danoMaximo = danoMaximo;
        this.modificadores = modificadores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDanoMinimo() {
        return danoMinimo;
    }

    public void setDanoMinimo(int danoMinimo) {
        this.danoMinimo = danoMinimo;
    }

    public int getDanoMaximo() {
        return danoMaximo;
    }

    public void setDanoMaximo(int danoMaximo) {
        this.danoMaximo = danoMaximo;
    }

    public String getModificadores() {
        return modificadores;
    }

    public void setModificadores(String modificadores) {
        this.modificadores = modificadores;
    }
    
    
}