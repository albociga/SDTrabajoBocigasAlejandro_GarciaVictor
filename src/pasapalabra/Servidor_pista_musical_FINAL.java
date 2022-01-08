package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_pista_musical_FINAL {
	public static void main(String[] args) {
		// El cliente1 comenzará jugando en la canción 1 y 3, el clinete2 comenzará
		// jugando en las canciones 2 y 4 (NO IMPLEMENTADO)
		// Creación aleatoria de las preguntas musicales
		Preguntas_pista_musical preg_musicales = Preguntas_pista_musical
				.crear_preguntas_musicales_aleatorio(Pregunta_Pista_Musical.todas_pistas_musicales_existentes());
		String respuesta = null;
		int aciertos_jug_1 = 0;
		try (ServerSocket ss = new ServerSocket(8498);) {
			while (true) {
				try (Socket client = ss.accept();
						// BufferedWriter bw = new BufferedWriter(new
						// OutputStreamWriter(client.getOutputStream()));
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						DataOutputStream dw = new DataOutputStream(client.getOutputStream()))
				// Socket client2 = ss.accept();
				// PrintStream p = new PrintStream(client2.getOutputStream());
				// BufferedReader br2 = new BufferedReader(new
				// InputStreamReader(client2.getInputStream()));
				// BufferedWriter bw2 = new BufferedWriter(new
				// OutputStreamWriter(client2.getOutputStream())))

				{
					FileInputStream cancion = null;
					// El numero total de Preguntas Musicales en la lista es 4 (Preguntas con
					// diferentes canciones)
					for (int i = 0; i < 4; i++) {
						// Fichero de la canción
						File faux = new File(
								preg_musicales.getPreguntas_musica().get(i).getPath_musica_pista_musical());
						cancion = new FileInputStream(faux);

						// Envio al cliente el tamaño del archivo de musica
						long tam_archivo_musica = faux.length();
						dw.writeLong(tam_archivo_musica);
						dw.flush();

						// Creo el Buffer de lectura
						byte[] buff = new byte[1024 * 32];
						// if(i % 2 == 0) { IMPLEMENTACIÓN POSTERIOR 2 JUGADORES
						// Leo y envío el fichero al cliente
						int leidos = cancion.read(buff);
						while (leidos != -1) {
							dw.write(buff, 0, leidos);
							dw.flush();
							leidos = cancion.read(buff);
						}
						cancion.close();
						
						for(Pregunta_Pista_Musical p : preg_musicales.getPreguntas_musica()) {
							System.out.println(p.getSolucion());
						}

						int j = 0;
						boolean respondida_correcta = false;
						// Envío al cliente el numero de pistas totales para cada pregunta

						
						dw.writeInt(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
						dw.flush();
						
						// HAGO UN BUCLE CON LIMITE EN EL NUMERO TOTAL DE PISTAS Y CON UN BOOLEANO QUE
						// CAMBIA CUANDO LA RESPONDAN BIEN
						while (j < (preg_musicales.getPreguntas_musica().get(i).getPistas().size())
								&& respondida_correcta == false) {
							// ENVIO LA PISTA AL CLIENTE
							dw.writeBytes(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
							dw.flush();
							// ESPERO SU RESPUESTA
							respuesta = br.readLine();
							// COMPRUEBO SI LA RESPUESTA ES CORRECTA O NO
							if (respuesta.equalsIgnoreCase(preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
								System.out.println("JUGADOR HA ACERTADO");
								// SI ES CORRECTA, CAMBIA EL BOOLEANO PARA SALIR DEL BUCLE
								respondida_correcta = true;
								// ENVIO AL CLIENTE PARA QUE SEPA SI HA RESPONDIDO BIEN O NO
								dw.writeBytes("Correcto\n");
								dw.flush();
								aciertos_jug_1++;
							} else {
								dw.writeBytes("Fallado\n");
								dw.flush();
								// SI HA FALLADO AUNMENTO J PARA QUE CONTINUE CON LA SIGUIENTE PISTA
								j++;
							}
						}
						// CUANDO SE ACABAN LAS PISTAS, ENVIA UN MENSAJE PARA QUE EL CLIENTE SEPA QUE HA
						// PASADO
						System.out.println("El numero de pistas mostradas:" + j);
						if (j == preg_musicales.getPreguntas_musica().get(i).getPistas().size()) {
							dw.writeBytes("HA FALLADO TODOS LOS INTENTOS PARA ESTA CANCION, LA SOLUCIÓN ERA "
									+ preg_musicales.getPreguntas_musica().get(i).getSolucion() + "\n");
							dw.flush();
						} else {
							dw.writeBytes("CANCIÓN ACERTADA\n");
							dw.flush();
						}

						// }
					}
					dw.writeBytes("El numero de aciertos ha sido " + aciertos_jug_1 + " Felicidades\n");
					dw.flush();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
