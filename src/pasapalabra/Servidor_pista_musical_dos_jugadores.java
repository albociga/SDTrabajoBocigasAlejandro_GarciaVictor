//EL SERVER SE LO INDIQUE Y QUE PUEDA RESPONDER SI EL OTRO FALLA
package pasapalabra;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_pista_musical_dos_jugadores {
	public static void main(String[] args) {
		// El cliente1 comenzará jugando en la canción 1 y 3, el clinete2 comenzará
		// jugando en las canciones 2 y 4 (NO IMPLEMENTADO)
		// Creación aleatoria de las preguntas musicales
		Preguntas_pista_musical preg_musicales = Preguntas_pista_musical
				.crear_preguntas_musicales_aleatorio(Pregunta_Pista_Musical.todas_pistas_musicales_existentes());
		String respuesta = null;
		int aciertos_numseg_jug_1 = 0;
		int aciertos_numseg_jug_2 = 0;
		try (ServerSocket ss = new ServerSocket(8498, 2);) {
			while (true) {
				try (Socket client = ss.accept();
						// BufferedWriter bw = new BufferedWriter(new
						// OutputStreamWriter(client.getOutputStream()));
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						DataOutputStream dw = new DataOutputStream(client.getOutputStream());
						Socket client2 = ss.accept();
						BufferedReader br2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
						DataOutputStream dw2 = new DataOutputStream(client2.getOutputStream());)

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
						dw2.writeLong(tam_archivo_musica);
						dw2.flush();

						// Creo el Buffer de lectura
						byte[] buff = new byte[1024 * 32];
						// if(i % 2 == 0) { IMPLEMENTACIÓN POSTERIOR 2 JUGADORES
						// Leo y envío el fichero al cliente
						int leidos = cancion.read(buff);
						while (leidos != -1) {
							dw.write(buff, 0, leidos);
							dw.flush();
							dw2.write(buff, 0, leidos);
							dw2.flush();
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
						dw2.writeInt(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
						dw2.flush();
						
						// HAGO UN BUCLE CON LIMITE EN EL NUMERO TOTAL DE PISTAS Y CON UN BOOLEANO QUE
						// CAMBIA CUANDO LA RESPONDAN BIEN
						while (j < preg_musicales.getPreguntas_musica().get(i).getPistas().size() 
								&& respondida_correcta == false) {
							// ENVIO LA PISTA AL CLIENTE
							dw.writeBytes(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
							dw.flush();
							dw2.writeBytes(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
							dw2.flush();
							
							System.out.println("i = " + i);
							System.out.println("i%2 = " + (i % 2));
							
							if (i % 2 == 0) {
								
								// MANDO UN START
								dw.writeBytes("START\n");
								dw.flush();
								
								// ESPERO SU RESPUESTA
								respuesta = br.readLine();
								System.out.println(respuesta);
								
								// COMPRUEBO SI LA RESPUESTA ES CORRECTA O NO
								if (respuesta
										.equalsIgnoreCase(preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
									
									// SI ES CORRECTA, CAMBIA EL BOOLEANO PARA SALIR DEL BUCLE
									respondida_correcta = true;
									
									// ENVIO AL CLIENTE PARA QUE SEPA SI HA RESPONDIDO BIEN O NO
									dw.writeBytes("Correcto\n");
									dw.flush();
									
									dw2.writeBytes("NO JUEGAS\n");
									dw2.flush();
									
									dw2.writeBoolean(true);
									dw2.flush();
									
									aciertos_numseg_jug_1 = aciertos_numseg_jug_1 + (5 - j);
								} else {
									dw.writeBytes("Incorrecto\n");
									dw.flush();
									
									dw2.writeBytes("START\n");
									dw2.flush();
									
									respuesta = br2.readLine();
									
									if (respuesta.equalsIgnoreCase(
											preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
										
										// SI ES CORRECTA, CAMBIA EL BOOLEANO PARA SALIR DEL BUCLE
										respondida_correcta = true;
										
										// ENVIO AL CLIENTE PARA QUE SEPA SI HA RESPONDIDO BIEN O NO
										dw2.writeBytes("Correcto\n");
										dw2.flush();
										
										dw.writeBoolean(true);
										dw.flush();
										
										aciertos_numseg_jug_2 = aciertos_numseg_jug_2 + (5 - j);
									} else {
										// SI HAN FALLADO LOS 2 AUNMENTO J PARA QUE CONTINUE CON LA SIGUIENTE PISTA
										dw2.writeBytes("Incorrecto\n");
										dw2.flush();
										
										dw.writeBoolean(false);
										dw.flush();
										
										dw2.writeBoolean(false);
										dw2.flush();
										
										j++;
										System.out.println(j);
									}

								}
								
							}
							if (i % 2 == 1) {
								// MANDO UN START
								dw2.writeBytes("START\n");
								dw2.flush();
								
								// ESPERO SU RESPUESTA
								respuesta = br2.readLine();
								System.out.println(respuesta);
								
								// COMPRUEBO SI LA RESPUESTA ES CORRECTA O NO
								if (respuesta
										.equalsIgnoreCase(preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
									
									// SI ES CORRECTA, CAMBIA EL BOOLEANO PARA SALIR DEL BUCLE
									respondida_correcta = true;
									
									// ENVIO AL CLIENTE PARA QUE SEPA SI HA RESPONDIDO BIEN O NO
									dw2.writeBytes("Correcto\n");
									dw2.flush();
									
									dw.writeBytes("NO JUEGAS\n");
									dw.flush();
									
									dw.writeBoolean(true);
									dw.flush();
									
									aciertos_numseg_jug_2 = aciertos_numseg_jug_2 + (5 - j);
								} else {
									dw2.writeBytes("Incorrecto\n");
									dw2.flush();
									
									dw.writeBytes("START\n");
									dw.flush();
									
									respuesta = br.readLine();
									
									if (respuesta.equalsIgnoreCase(
											preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
										
										// SI ES CORRECTA, CAMBIA EL BOOLEANO PARA SALIR DEL BUCLE
										respondida_correcta = true;
										
										// ENVIO AL CLIENTE PARA QUE SEPA SI HA RESPONDIDO BIEN O NO
										dw.writeBytes("Correcto\n");
										dw.flush();
										
										dw2.writeBoolean(true);
										dw2.flush();
										
										aciertos_numseg_jug_1 = aciertos_numseg_jug_1 + (5 - j);
									} else {
										// SI HAN FALLADO LOS 2 AUNMENTO J PARA QUE CONTINUE CON LA SIGUIENTE PISTA
										dw.writeBytes("Incorrecto\n");
										dw.flush();
										
										dw2.writeBoolean(false);
										dw2.flush();
										
										dw.writeBoolean(false);
										dw.flush();
										
										j++;
										System.out.println(j);
									}

								}
								
							}
						}

						// respuesta = br.readLine();

						// CUANDO SE ACABAN LAS PISTAS, ENVIA UN MENSAJE PARA QUE EL CLIENTE SEPA QUE HA
						// PASADO
						if (j == preg_musicales.getPreguntas_musica().get(i).getPistas().size()) {
							dw.writeBytes("INTENTOS AGOTADOS, LA SOLUCIÓN ERA "
									+ preg_musicales.getPreguntas_musica().get(i).getSolucion() + "\n");
							dw.flush();
							dw2.writeBytes("INTENTOS AGOTADOS, LA SOLUCIÓN ERA "
									+ preg_musicales.getPreguntas_musica().get(i).getSolucion() + "\n");
							dw2.flush();
							dw.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\r\n");
							dw.flush();
							dw2.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\r\n");
							dw2.flush();
						} else {
							dw.writeBytes("CANCIÓN ACERTADA\r\n");
							dw.flush();
							dw2.writeBytes("CANCIÓN ACERTADA\r\n");
							dw2.flush();
							dw.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\r\n");
							dw.flush();
							dw2.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\r\n");
							dw2.flush();
						}
						//respuesta = br.readLine();

					}

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
