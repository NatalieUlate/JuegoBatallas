#  Videojuego de Batalla por Turnos en Java 

Proyecto final para el curso de Programación. Este es un videojuego de combate por turnos desarrollado en Java, aplicando conceptos de Programación Orientada a Objetos, persistencia con bases de datos (JDBC) y una arquitectura organizada en capas. 

## Tabla de Contenidos
1.  [Descripción General](#descripción-general)
2.  [Características Principales](#características-principales)
3.  [¿Cómo Jugar?](#cómo-jugar-guía-de-razas-y-habilidades)
4.  [Requisitos Técnicos](#requisitos-técnicos)
5.  [Instalación y Ejecución](#instalación-y-ejecución)
6.  [Estructura del Proyecto](#estructura-del-proyecto)
7.  [Equipo de Desarrollo](#equipo-de-desarrollo)

---

## Descripción General
Este es un videojuego de combate para dos jugadores, ejecutado en consola.  Cada jugador elige un nombre y luego selecciona un personaje de una de las cuatro razas disponibles para enfrentarse en una batalla a muerte.  El combate se desarrolla por turnos, y cada raza posee habilidades y armas únicas que le dan un estilo de juego distinto. 

El objetivo del proyecto es demostrar el manejo de herencia (todos los personajes derivan de una clase `Personaje`), el uso de estructuras de datos, y la persistencia de la información de jugadores, armas y resultados de partidas en una base de datos PostgreSQL. 

---

## Características Principales
* **Combate por Turnos:** El Jugador 1 ataca primero, seguido por el Jugador 2, y así sucesivamente hasta que uno de los dos se quede sin vida. 
* **Cuatro Razas Únicas:** Elige entre Humano, Elfo, Orco o Bestia, cada uno con sus propias fortalezas y debilidades. 
* **Sistema de Habilidades:** Cada personaje puede `Atacar` o `Sanar` en su turno, con efectos que varían según su raza y equipamiento. 
* **Persistencia de Datos:** Toda la información relevante (jugadores, estadísticas de victorias/derrotas, historial de partidas) se guarda en una base de datos PostgreSQL. 
* **Acceso a Datos Seguro:** Se utiliza `PreparedStatement` en la capa DAO para prevenir inyección SQL y `try-with-resources` para una gestión eficiente de las conexiones.
* **Código Organizado:** El proyecto está estructurado en paquetes (`controller`, `dao`, `model`, `util`) para una clara separación de responsabilidades.

---

## ¿Cómo Jugar? (Guía de Razas y Habilidades)
Al iniciar, el juego te pedirá el nombre de cada jugador. Luego, cada uno deberá seleccionar su raza y equipamiento desde un menú en la consola. 

Cada personaje inicia con 100 puntos de vida, con una excepción. 

### Humano 🔫
El Humano es un combatiente a distancia versátil.
* **Armas:** Solo puede usar armas de fuego como la Escopeta o el Rifle Francotirador. 
* **Ataque (Escopeta):** Causa un daño base aleatorio de 1-5, con un bono del 2%. 
* **Sanación ("Comer"):** Puede gastar un turno para comer, lo que le permite recuperar el 50% de la vida que ha perdido. 

### Elfo ✨
El Elfo es un maestro de la magia, con un poder que cambia según su afinidad elemental.
* **Armas:** Solo utiliza un Báculo para canalizar su magia. 
* **Afinidades:** Al inicio, debe elegir entre magia de Fuego, Tierra, Aire o Agua. 
* **Ataque:**
    * **Fuego:** Causa un daño base de 1-5, con un bono del 10%. 
    * **Tierra:** Causa un daño base de 1-5, con un bono del 2%. 
    * **Otras afinidades:** Daño base de 1-5. 
* **Sanación ("Hechizo de sanación"):** Recupera el 75% de la vida perdida. [cite: 34] Si su afinidad es **Agua**, la curación aumenta a un potente 90%. 
* **Habilidad Pasiva:** Un Elfo con magia de **Agua** es más resistente e inicia el combate con 115 puntos de vida en lugar de 100. 

### Orco 🪓
Un guerrero brutal que se especializa en el combate cuerpo a cuerpo.
* **Armas:** Puede elegir entre un Hacha o un Martillo. 
* **Ataque (Hacha):** Causa un daño de 1-5 y, además, provoca una herida sangrante en el oponente, que le causa 3 puntos de daño adicionales durante 2 turnos. 
* **Ataque (Furia):** Cuando un Orco se cura, se enfurece. Su siguiente ataque después de curarse recibe un bono de daño del 15%. 
* **Sanación ("Pócima de curación"):** Recupera el 25% de la vida perdida e inmediatamente se prepara para su ataque de furia. 

### Bestia 🐾
Un ser híbrido y salvaje que lucha con una fuerza descomunal. 
* **Armas:** Puede pelear usando sus Puños o una Espada. 
* **Ataque (Puños):** Es un ataque de alto riesgo. Causa un daño masivo y fijo de 25 puntos al oponente, pero la Bestia sufre 10 puntos de daño a cambio. 
* **Ataque (Espada):** Un ataque más seguro que causa un daño aleatorio entre 1 y 10. 
* **Sanación ("Dormir"):** La Bestia puede gastar un turno en dormir para recuperar el 45% de la vida que ha perdido. 

---

## Requisitos Técnicos
* Java Development Kit (JDK) 11 o superior.
* IDE NetBeans (el proyecto está configurado para usar Ant). 
* Servidor de base de datos PostgreSQL. 

---

## Instalación y Ejecución
1.  **Clonar el Repositorio:** 
    ```bash
    git clone [URL-DEL-REPOSITORIO]
    ```
2.  **Configurar la Base de Datos:**
    * Asegúrate de tener PostgreSQL instalado y corriendo.
    * Crea una base de datos llamada `juego`.
    * Ejecuta el script `database/script.sql` para crear todas las tablas necesarias. 
    * Restaura el archivo `database/juego.backup` para poblar las tablas con datos iniciales (razas, armas, etc.). 
3.  **Configurar la Conexión:**
    * Abre el archivo `src/util/ConexionBD.java`.
    * Verifica que la `URL`, `USER` y `PASSWORD` coincidan con tu configuración local de PostgreSQL.
4.  **Ejecutar el Proyecto:**
    * Abre el proyecto con NetBeans IDE. 
    * Limpia y construye el proyecto (Clean and Build).
    * Ejecuta el archivo principal: `src/controller/VideojuegoBatallas.java`. 
    * ¡Y a jugar! 

---

## Estructura del Proyecto
El código está organizado en los siguientes paquetes, siguiendo las mejores prácticas de diseño: 

* `controller`: Contiene la clase `MotorJuego`, que orquesta toda la lógica y el flujo de la aplicación.
* `dao`: Contiene las clases de Acceso a Datos (DAO) responsables de la comunicación con la base de datos (`JugadorDAO`, `ArmaDAO`, etc.).
* `model`: Contiene las clases que representan las entidades del juego (`Personaje`, `Humano`, `Orco`, `Arma`, etc.).
* `util`: Contiene clases de utilidad, como `ConexionBD` para gestionar la conexión a la base de datos.

---

## Equipo de Desarrollo
Este proyecto fue desarrollado por los siguientes integrantes: 

* **Natalie Ulate Rojas:** *Encargada del Backend y Base de Datos.* Responsable del diseño e implementación de la capa de acceso a datos (DAO), la configuración de la base de datos en PostgreSQL y la gestión de la persistencia de datos con JDBC.
* **
* **Tatiana Urbina Arroliga:** *Encargada del Backend y Base de Datos.* Responsable del parte de las clases de los modelos  e implementación de la capa de acceso a datos (DAO).
* **