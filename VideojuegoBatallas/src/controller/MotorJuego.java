/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.ArmaDAO;
import dao.HistorialPartidasDAO;
import dao.JugadorDAO;
import dao.PersonajeDAO;
import dao.RazaDAO;
import java.util.HashMap;
import model.*;
import util.ConexionBD;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author Natalie Ulate Rojas
 */
/**
 * Clase controladora principal que orquesta todo el flujo del videojuego.
 * Se encarga de los menús, la creación de partidas, el bucle de combate y
 * la comunicación con la capa de persistencia (DAOs).
 */
public class MotorJuego {

    private final Scanner scanner;
    private final JugadorDAO jugadorDAO;
    private final RazaDAO razaDAO;
    private final ArmaDAO armaDAO;
    private final PersonajeDAO personajeDAO;
    private final HistorialPartidasDAO historialDAO;

    public MotorJuego() {
        this.scanner = new Scanner(System.in);
        this.jugadorDAO = new JugadorDAO();
        this.razaDAO = new RazaDAO();
        this.armaDAO = new ArmaDAO();
        this.personajeDAO = new PersonajeDAO();
        this.historialDAO = new HistorialPartidasDAO();
    }


    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    jugarNuevaPartida();
                    break;
                case 2:
                    mostrarEstadisticas();
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por jugar. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        }
        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- VIDEOJUEGO DE COMBATE RPG ---");
        System.out.println("1. Jugar Nueva Partida");
        System.out.println("2. Ver Estadísticas de Jugadores");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    private void jugarNuevaPartida() {
        System.out.println("\n--- CONFIGURACIÓN DE LA PARTIDA ---");


        System.out.print("Nombre del Jugador 1: ");
        String nombreJ1 = scanner.nextLine();
        Jugador jugador1 = jugadorDAO.obtenerOCrearJugador(nombreJ1);
        
 
        Map<String, Object> plantillaP1 = new HashMap<>();
        Personaje personaje1 = seleccionarPersonaje(jugador1, plantillaP1);
        if (personaje1 == null) return; 


        System.out.print("\nNombre del Jugador 2: ");
        String nombreJ2 = scanner.nextLine();
        Jugador jugador2 = jugadorDAO.obtenerOCrearJugador(nombreJ2);
        

        Map<String, Object> plantillaP2 = new HashMap<>();
        Personaje personaje2 = seleccionarPersonaje(jugador2, plantillaP2);
        if (personaje2 == null) return;


        iniciarCombate(jugador1, personaje1, plantillaP1, jugador2, personaje2, plantillaP2);
    }

    private Personaje seleccionarPersonaje(Jugador jugador, Map<String, Object> plantillaSeleccionada) {

        System.out.println("\n" + jugador.getNombre() + ", elige tu raza:");
        List<Raza> razas = razaDAO.obtenerTodasLasRazas();
        for (int i = 0; i < razas.size(); i++) {
            System.out.println((i + 1) + ". " + razas.get(i).getNombre());
        }
        System.out.print("Elige una opción: ");
        int opcionRaza = leerOpcion() - 1;
        Raza razaElegida = razas.get(opcionRaza);


        Map<String, Object> datosPlantilla = personajeDAO.obtenerPlantillaPorRaza(razaElegida.getNombre());
        plantillaSeleccionada.putAll(datosPlantilla); 
        
        Arma armaElegida = null;
        String tipoMagia = null;

        if (razaElegida.getNombre().equalsIgnoreCase("Elfo")) {
            System.out.println("Elige tu afinidad mágica:");
            System.out.println("1. Fuego\n2. Tierra\n3. Aire\n4. Agua");
            System.out.print("Elige una opción: ");
            int opcionMagia = leerOpcion();
            switch (opcionMagia) {
                case 1: tipoMagia = "Fuego"; break;
                case 2: tipoMagia = "Tierra"; break;
                case 3: tipoMagia = "Aire"; break;
                case 4: tipoMagia = "Agua"; break;
                default: tipoMagia = "Aire"; 
            }

            int idArmaElfo = (int) datosPlantilla.get("id_arma_inicial");
            armaElegida = armaDAO.obtenerArmaPorId(idArmaElfo);

        } else {
            List<Arma> armas = armaDAO.obtenerArmasPorRaza(razaElegida.getNombre());
            System.out.println("Elige tu arma:");
            for (int i = 0; i < armas.size(); i++) {
                System.out.println((i + 1) + ". " + armas.get(i).getNombre());
            }
            System.out.print("Elige una opción: ");
            int opcionArma = leerOpcion() - 1;
            armaElegida = armas.get(opcionArma);
        }

        return construirPersonaje(jugador, datosPlantilla, armaElegida, tipoMagia);
    }

    private Personaje construirPersonaje(Jugador jugador, Map<String, Object> datos, Arma arma, String tipoMagia) {
        String nombreJugador = jugador.getNombre();
        Raza raza = (Raza) datos.get("raza_obj");
        int fuerza = (int) datos.get("fuerza");
        int energia = (int) datos.get("energia");
        int vida = (int) datos.get("vida_base");
        
        switch (raza.getNombre().toLowerCase()) {
            case "humano":
                return new Humano(nombreJugador, raza, fuerza, energia, vida, arma);
            case "elfo":
                return new Elfo(nombreJugador, raza, fuerza, energia, vida, arma, tipoMagia);
            case "orco":
                return new Orco(nombreJugador, raza, fuerza, energia, vida, arma);
            case "bestia":
                return new Bestia(nombreJugador, raza, fuerza, energia, vida, arma);
            default:
                System.err.println("Error: Raza desconocida al construir personaje.");
                return null;
        }
    }

    private void iniciarCombate(Jugador j1, Personaje p1, Map<String, Object> plantillaP1, Jugador j2, Personaje p2, Map<String, Object> plantillaP2) {
        System.out.println("\n--- ¡QUE COMIENCE EL COMBATE! ---");
        System.out.println(p1.getNombre() + " vs " + p2.getNombre());

        Personaje atacante = p1;
        Personaje defensor = p2;
        
        while (p1.estaVivo() && p2.estaVivo()) {
            mostrarEstadoCombate(p1, p2);
            realizarTurno(atacante, defensor);

            if (!defensor.estaVivo()) {
                break;
            }

            Personaje temp = atacante;
            atacante = defensor;
            defensor = temp;
        }

        Jugador ganador = p1.estaVivo() ? j1 : j2;
        Jugador perdedor = p1.estaVivo() ? j2 : j1;

        System.out.println("\n--- COMBATE FINALIZADO ---");
        System.out.println("¡El ganador es " + ganador.getNombre() + "!");

        finalizarPartida(ganador, perdedor, plantillaP1, plantillaP2);
    }
    
    private void realizarTurno(Personaje atacante, Personaje defensor) {
        System.out.println("\n--- Turno de: " + atacante.getNombre() + " ---");
        atacante.aplicarEfectosDeTurno(); 
        if (!atacante.estaVivo()) return; 
        
        System.out.println("Acciones: [1] Atacar, [2] Sanar");
        System.out.print("Elige tu acción: ");
        int opcion = leerOpcion();
        
        if (opcion == 1) {
            atacante.atacar(defensor);
        } else if (opcion == 2) {
            atacante.sanar();
        } else {
            System.out.println("Opción no válida. Pierdes el turno.");
        }
    }
    
    private void finalizarPartida(Jugador ganador, Jugador perdedor, Map<String, Object> plantillaGanador, Map<String, Object> plantillaPerdedor) {

        ganador.setPartidasGanadas(ganador.getPartidasGanadas() + 1);
        perdedor.setPartidasPerdidas(perdedor.getPartidasPerdidas() + 1);
        jugadorDAO.actualizarEstadisticas(ganador);
        jugadorDAO.actualizarEstadisticas(perdedor);


        int idPlantillaGanador = (int) plantillaGanador.get("id_personaje_plantilla");
        int idPlantillaPerdedor = (int) plantillaPerdedor.get("id_personaje_plantilla");
        
        HistorialPartida partida = new HistorialPartida(ganador.getId(), perdedor.getId(), idPlantillaGanador, idPlantillaPerdedor, ganador.getId());
        historialDAO.registrarPartida(partida);
    }

    private void mostrarEstadisticas() {
        System.out.println("\n--- RANKING DE JUGADORES ---");
        List<Jugador> jugadores = jugadorDAO.obtenerTodosLosJugadores();
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados todavía.");
            return;
        }
        
        System.out.printf("%-5s | %-20s | %-7s | %s%n", "ID", "NOMBRE", "GANADAS", "PERDIDAS");
        System.out.println("---------------------------------------------------");
        for (Jugador j : jugadores) {
            System.out.printf("%-5d | %-20s | %-7d | %d%n", 
                j.getId(), j.getNombre(), j.getPartidasGanadas(), j.getPartidasPerdidas());
        }
    }
    
    private void mostrarEstadoCombate(Personaje p1, Personaje p2) {
        System.out.println("------------------------------------");
        System.out.printf("%s: %d/%d HP%n", p1.getNombre(), p1.getVidaActual(), p1.getVidaMaxima());
        System.out.printf("%s: %d/%d HP%n", p2.getNombre(), p2.getVidaActual(), p2.getVidaMaxima());
        System.out.println("------------------------------------");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; 
        }
    }
}
