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

public class Servidor_pista_musical {
	public static void main(String[] args) {
		// El cliente1 comenzará jugando en la canción 1 y 3, el clinete2 comenzará jugando en las canciones 2 y 4
		Preguntas_pista_musical preg_musicales = Preguntas_pista_musical.crear_preguntas_musicales_aleatorio(Pregunta_Pista_Musical.todas_pistas_musicales_existentes());
		String respuesta = null;
		int aciertos_jug_1 = 0;
		try (ServerSocket ss = new ServerSocket(8498);) {
			while (true) {
				try (Socket client = ss.accept();
						//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
						BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
						DataOutputStream dw = new DataOutputStream(client.getOutputStream()))
						//Socket client2 = ss.accept();
						//PrintStream p = new PrintStream(client2.getOutputStream());
						//BufferedReader br2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
						//BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(client2.getOutputStream())))

				{
					FileInputStream cancion = null;
					//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminará el rosco
					for (int i = 0; i < 4; i++) {
						File faux = new File(preg_musicales.getPreguntas_musica().get(i).getPath_musica_pista_musical());
						cancion= new FileInputStream(faux);
						long tam_archivo_musica = faux.length();
						dw.writeLong(tam_archivo_musica);
						dw.flush();
						
						byte[] buff=new byte[1024*32];
						//if(i % 2 == 0) {
							int leidos = cancion.read(buff);
							while(leidos!=-1)
							{
								dw.write(buff, 0, leidos);
								dw.flush();
								leidos = cancion.read(buff);
							}
							cancion.close();
							
							int j=0;
							boolean respondida_correcta = false;
							dw.writeInt(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
							//System.out.println(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
							dw.flush();
							while(j<preg_musicales.getPreguntas_musica().get(i).getPistas().size() && respondida_correcta == false) {
								//System.out.println(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j).toString());
								dw.writeChars(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
								dw.flush();
								respuesta = br.readLine();
								//System.out.println();
								if(respuesta.equalsIgnoreCase(preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
									respondida_correcta = true;
									dw.writeChar('C');
									dw.flush();
									aciertos_jug_1 ++;
								}else{
									dw.writeChar('F');
									dw.flush();
									j++;
								}
							}
							if(j == preg_musicales.getPreguntas_musica().get(i).getPistas().size()) {
								dw.writeChars("HA FALLADO TODOS LOS INTENTOS PARA ESTA CANCION, LA SOLUCIÓN ERA " + preg_musicales.getPreguntas_musica().get(i).getSolucion() + "\n");
								dw.flush();
							}else {
								dw.writeChars("CANCIÓN ACERTADA\n");
								dw.flush();
							}
							
							
						//}
					}
					dw.writeChars("El numero de aciertos ha sido " + aciertos_jug_1 + " Felicidades\n");
						/*
						//El servidor lee el fichero de musica
						
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
			}*/
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
	}}
