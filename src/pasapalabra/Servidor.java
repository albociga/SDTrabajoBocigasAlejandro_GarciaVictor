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
		// El cliente será el que envíe al servidor la letra en la que se encuentra con
		// un INT
		// Si el Cliente pulsa Pasapalabra, enviará "PASAPALABRA|INT"
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),200);
		String linea = null;
		String palabra_actual = null;
		try (ServerSocket ss = new ServerSocket(8498);) {
			while (true) {
				try (Socket client = ss.accept();
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream())))

				{
					List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
					//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminará el rosco
					while (!rosco.todas_preguntas_respondidas()) {

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
								bw.write("FALLADA \r\n");
								bw.flush();
							}
							//Cambía el respondido de la pregunta a TRUE
							rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
							//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
							//en caso negativo se quedará esperando para seguir leyendo
							
							//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
							//en la que se encuentra
						}else {
							bw.write("PASA \r\n");
							bw.flush();
						}
					}
					bw.write("HA COMPLETADO EL ROSCO, VUELVA PRONTO \r\n");
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
