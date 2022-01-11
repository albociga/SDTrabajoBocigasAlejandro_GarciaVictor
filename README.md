# PASAPALABRA
## Sistemas Distribuidos Trabajo Alejandro Bocigas y Víctor García
Juego de Pasapalabra en el que se ha implementado el "Juego de Pista Musical" y el "Rosco" característico de Pasapalabra. 
### Pasos para la ejecución:
- Iniciar clase "ServidorPrincipal.java"
- Iniciar clase "Principal.java"
- Seleccionar si se desea jugar Individualmente (Rosco) o Multijugador (Juego Pista Musical + Rosco)

### Otras indicaciones:

- El Juego Pista Musical consta de 4 canciones y 5 intentos en cada una. Si aciertas la canción el primer intento obtendrás 5 segundos más en el rosco, en el segundo intento 4, en el tercero 3, en el cuarto 2 y en el quinto 1.
- En el Rosco pulse Pasapalabra para pasar de turno y Responder para responder (no hemos implementado el pulsar intro para responder ya que pdría dar fallos). No es necesario poner tildes, ni mayúsculas en las respuestas.
- El juego del rosco lo hemos implementado para que dure 200 segundos (mas tiempo si consigue tiempo en el modo pista musical). Si desea cambiar el valor de este tiempo, se debe ir a AtenderPeticion.java y en las acciones roscoIndividual o roscoMultijugador, cambia el valor del segundo paramentro de los constructores del rosco, que son los segundos que va a tenner como tiempo de duración.

> Si al abrir proyecto apareciese un error relacionado con librerias, probar a realizar un "Clean project" o añadir una librería y despues borrarla. (Solución: https://stackoverflow.com/questions/3632632/the-project-cannot-be-built-until-the-build-path-errors-are-resolved)