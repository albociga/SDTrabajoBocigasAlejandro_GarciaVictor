package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Servidor2 {
	public static void main(String[] args) {
		// El cliente será el que envíe al servidor la letra en la que se encuentra con
		// un INT
		// Si el Cliente pulsa Pasapalabra, enviará "PASAPALABRA|INT"
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()));
		Rosco_Final rosco2 = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()));
		String linea = null;
		String palabra_actual = null;
		//Cuando juegue el jugador 1 booleano= true, si juega el jugador 2, booleano = false
		boolean jug1_true_jug2_false = true;
		try (ServerSocket ss = new ServerSocket(8498,2);) {
			while (true) {
				try (Socket client = ss.accept();
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
						Socket client2 = ss.accept();
						BufferedReader br2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
						BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(client2.getOutputStream())))

				{
					List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
					//Empieza jugando el jugador 1 (Se podría implementar que empezasen aleatoriamente, pero creo que es insignificante)
					//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminará el juego
					while (!rosco.todas_preguntas_respondidas() || !rosco2.todas_preguntas_respondidas()) {
						if(!rosco.todas_preguntas_respondidas() && jug1_true_jug2_false) {
							//CONSOLA DEL SERVER, QUITAR PARA QUE NO APAREZCA(ES UN LOG DE JUGADAS)
							System.out.println("El JUGADOR 1 esta jugando");
							//Necesario para que los dos jugadores jueguen cada uno en su turno 
							//(para que cliente1 o cliente2 se quede parado mientras el otro liente juega) 
							bw.write("Para la espera \r\n");
							bw.flush();
							// Lee el número de la letra en la que se encuentra el jugador A=0,B=1....
							palabra_actual = br.readLine();
							//Busca el enunciado en la lista de preguntas del rosco y lo envía el cliente
							String pregunta=rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEmpieza_contiene() 
									+" con "+abecedario.get(Integer.parseInt(palabra_actual))+" "
									+rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado()+"\r\n";
							bw.write(pregunta);
							bw.flush();
							//Queda esperando la respuesta del cliente y la lee
							linea = br.readLine();
							//Si lo escrito por el cliente NO es "PASAPALABRA"
							if (!linea.equalsIgnoreCase("PASAPALABRA")) {
								//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y envía el resultado
								if (rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
									bw.write("ACERTADA \r\n");
									bw.flush();
								} else {
									if(!rosco2.todas_preguntas_respondidas()) {
										jug1_true_jug2_false=false;
									}
									bw.write("FALLADA \r\n");
									bw.flush();
								}
								//Cambía el respondido de la pregunta a TRUE
								rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
								
								//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
								//en caso negativo se quedará esperando para seguir leyendo
								//En caso de que se termine el roscon acertando una pregunta no cambiaria de turno
								if(rosco.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=false;
								}
								//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
								//en la que se encuentra
							}else {
								if(!rosco2.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=false;
								}
								bw.write("PASA \r\n");
								bw.flush();
							}
						}else if (!rosco2.todas_preguntas_respondidas() && !jug1_true_jug2_false) {
							//CONSOLA DEL SERVER, QUITAR PARA QUE NO APAREZCA(ES UN LOG DE JUGADAS)
							System.out.println("El JUGADOR 2 esta jugando");
							//Necesario para que los dos jugadores jueguen cada uno en su turno 
							//(para que cliente1 o cliente2 se quede parado mientras el otro liente juega) 
							bw2.write("Para la espera \r\n");
							bw2.flush();
							// Lee el número de la letra en la que se encuentra el jugador A=0,B=1....
							palabra_actual = br2.readLine();
							//Busca el enunciado en la lista de preguntas del rosco y lo envía el cliente
							String pregunta=rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getEmpieza_contiene() 
									+" con "+ abecedario.get(Integer.parseInt(palabra_actual))+": "
									+rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado()+"\r\n";
							bw2.write(pregunta);
							bw2.flush();
							//Queda esperando la respuesta del cliente y la lee
							linea = br2.readLine();
							//Si lo escrito por el cliente NO es "PASAPALABRA"
							if (!linea.equalsIgnoreCase("PASAPALABRA")) {
								//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y envía el resultado
								if (rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
									bw2.write("ACERTADA \r\n");
									bw2.flush();
								} else {
									if(!rosco.todas_preguntas_respondidas()) {
										jug1_true_jug2_false=true;
									}
									bw2.write("FALLADA \r\n");
									bw2.flush();
								}
								//Cambía el respondido de la pregunta a TRUE
								rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
								//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
								//en caso negativo se quedará esperando para seguir leyendo
								//En caso de que se termine el roscon acertando una pregunta no cambiaria de turno
								if(rosco2.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=true;
								}
								//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
								//en la que se encuentra
							}else {
								if(!rosco.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=true;
								}
								bw2.write("PASA \r\n");
								bw2.flush();
							}
						}
					}
					bw.write("LOS DOS JUGADORES HAN COMPLETADO EL ROSCO \r\n");
					bw.flush();
					bw2.write("LOS DOS JUGADORES HAN COMPLETADO EL ROSCO \r\n");
					bw2.flush();
					bw.write("EL ganador es: ...FALTA IMPLEMENTAR........ \r\n");
					bw.flush();
					bw.write("EL ganador es: ...FALTA IMPLEMENTAR........ \r\n");
					bw2.flush();
					client.close();
					client2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
