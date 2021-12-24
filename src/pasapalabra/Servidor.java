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


public class Servidor {
	public static void main(String[] args) {
		// El cliente ser� el que env�e al servidor la letra en la que se encuentra con
		// un INT
		// Si el Cliente pulsa Pasapalabra, enviar� "PASAPALABRA|INT"
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()));
		String linea = null;
		String palabra_actual = null;
		try (ServerSocket ss = new ServerSocket(8498);) {
			while (true) {
				try (Socket client = ss.accept();
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream())))

				{
					//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminar� el rosco
					while (!rosco.todas_preguntas_respondidas()) {

						// Lee el n�mero de la letra en la que se encuentra el jugador A=0,B=1....
						palabra_actual = br.readLine();
						//Busca el enunciado en la lista de preguntas del rosco y lo env�a el cliente
						bw.write(rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado());
						bw.flush();
						//Queda esperando la respuesta del cliente y la lee
						linea = br.readLine();
						//Si lo escrito por el cliente NO es "PASAPALABRA"
						if (!linea.equalsIgnoreCase("PASAPALABRA")) {
							//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y env�a el resultado
							if (rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
								bw.write("ACERTADA");
								bw.flush();
							} else {
								bw.write("FALLADA");
								bw.flush();
							}
							//Camb�a el respondido de la pregunta a TRUE
							rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
							//Despu�s volver� al while donde comprobar� que si se ha respondido a todas las preguntas, 
							//en caso negativo se quedar� esperando para seguir leyendo
							
							//De la misma forma si ha recibido un pasapalabra el cliente deber� enviarle la siguiente pregunta
							//en la que se encuentra
						}else {
							bw.write("PASA");
							bw.flush();
						}
					}
					bw.write("HA COMPLETADO EL ROSCO, VUELVA PRONTO");
					bw.flush();
					client.close();
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
