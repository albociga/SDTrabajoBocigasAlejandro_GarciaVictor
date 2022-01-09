package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class AtenderPeticion implements Runnable {
	private Socket socket1;
	private Socket socket2;
	private boolean multijugador;
	private BufferedReader br;
	private BufferedWriter bw;
	public AtenderPeticion(Socket s1, BufferedReader r, BufferedWriter w) {
		this.socket1=s1;
		this.multijugador=false;
		this.br=r;
		this.bw=w;
	}
	public AtenderPeticion(Socket s1, Socket s2,BufferedReader r, BufferedWriter w) {
		this.socket1=s1;
		this.socket2=s2;
		this.multijugador=true;
		this.br=r;
		this.bw=w;
	}
	@Override
	public void run() {
		if(!this.multijugador) {
			roscoIndividual(this.bw,this.br);
		}else {
			try(BufferedReader br2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
					DataOutputStream dw = new DataOutputStream(socket1.getOutputStream());
					DataOutputStream dw2 = new DataOutputStream(socket2.getOutputStream());){
				int[]seg=pruebaMusica(socket1,socket2,br,br2,dw,dw2);
				roscoMultijugador(this.bw,this.br,br2,socket2,seg[0],seg[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static int[] pruebaMusica(Socket s1, Socket s2,BufferedReader br,BufferedReader br2,DataOutputStream dw,DataOutputStream dw2) {
		int [] resultados=new int[2];		
		Preguntas_pista_musical preg_musicales = Preguntas_pista_musical
				.crear_preguntas_musicales_aleatorio(Pregunta_Pista_Musical.todas_pistas_musicales_existentes());
		String respuesta = null;
		int aciertos_numseg_jug_1 = 0;
		int aciertos_numseg_jug_2 = 0;
		try{
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
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultados[0]=aciertos_numseg_jug_1;
		resultados[1]=aciertos_numseg_jug_2;
		return resultados;
	}
	public static void roscoIndividual(BufferedWriter bw, BufferedReader br) {
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20);
		String linea = null;
		String palabra_actual = null;
		List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
		try {
			bw.write(rosco.getTiempoRestante()+"\r\n");
			bw.flush();
			//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminará el rosco
			while (!rosco.todas_preguntas_respondidas()&&!rosco.tiempoTerminado()) {
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
						bw.write("ACERTADA\r\n");
						bw.flush();
						rosco.anadirAcierto();
					} else {
						bw.write("FALLADA\r\n");
						bw.flush();
						rosco.anadirFallo();
					}
					//Cambía el respondido de la pregunta a TRUE
					rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
					//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
					//en caso negativo se quedará esperando para seguir leyendo
					
					//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
					//en la que se encuentra
				}else {
					bw.write("PASA\r\n");
					bw.flush();
				}
				rosco.setTiempoRestante(Integer.parseInt(br.readLine()));
			}
			bw.write("Has obtenido "+rosco.getAciertos() +" aciertos y "+rosco.getFallos()+" fallos \r\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void roscoMultijugador(BufferedWriter bw, BufferedReader br,BufferedReader br2, Socket s,int segExt1,int segExt2) {
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20+segExt1);
		Rosco_Final rosco2 = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20+segExt2);
		boolean jug1_true_jug2_false = true;
		String linea = null;
		String palabra_actual = null;
		List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
		try(BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))){
			bw.write(rosco.getTiempoRestante()+"\r\n");
			bw.flush();
			bw2.write(rosco2.getTiempoRestante()+"\r\n");
			bw2.flush();
			while ((!rosco.todas_preguntas_respondidas() || !rosco2.todas_preguntas_respondidas()) && (!rosco.tiempoTerminado() || !rosco2.tiempoTerminado())) {
				if(!rosco.todas_preguntas_respondidas() && jug1_true_jug2_false &&!rosco.tiempoTerminado()) {
					//CONSOLA DEL SERVER, QUITAR PARA QUE NO APAREZCA(ES UN LOG DE JUGADAS)
					System.out.println("El JUGADOR 1 esta jugando");
					//Necesario para que los dos jugadores jueguen cada uno en su turno 
					//(para que cliente1 o cliente2 se quede parado mientras el otro liente juega) 
					
					bw.write("Para la espera \r\n");
					bw.flush();
					bw.write(rosco.getTiempoRestante()+"\r\n"); //LE ENVIA EL TIEMPO RESTANTE AL ROSCO
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
							bw.write("ACERTADA\r\n");
							bw.flush();
							rosco.anadirAcierto();
						} else {
							if(!rosco2.todas_preguntas_respondidas() && !rosco2.tiempoTerminado()) {
								jug1_true_jug2_false=false;
							}
							bw.write("FALLADA\r\n");
							bw.flush();
							rosco.anadirFallo();
						}
						//Cambía el respondido de la pregunta a TRUE
						rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
						//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
						//en caso negativo se quedará esperando para seguir leyendo
						//En caso de terminar este rosco y acertar la ultima pregunta utilizamos este if para cambiar al otro rosco si este otro no ha terminado
						if(rosco.todas_preguntas_respondidas() ||rosco.tiempoTerminado() ) {
							jug1_true_jug2_false=false;
						}
						//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
						//en la que se encuentra
					}else {
						if(!rosco2.todas_preguntas_respondidas() && !rosco2.tiempoTerminado()) {
							jug1_true_jug2_false=false;
						}
						bw.write("PASA\r\n");
						bw.flush();
					}
					rosco.setTiempoRestante(Integer.parseInt(br.readLine()));
				}else if (!rosco2.todas_preguntas_respondidas() && !jug1_true_jug2_false && !rosco2.tiempoTerminado()) {
					//CONSOLA DEL SERVER, QUITAR PARA QUE NO APAREZCA(ES UN LOG DE JUGADAS)
					System.out.println("El JUGADOR 2 esta jugando");
					//Necesario para que los dos jugadores jueguen cada uno en su turno 
					//(para que cliente1 o cliente2 se quede parado mientras el otro liente juega) 
					bw2.write("Para la espera \r\n");
					bw2.flush();
					bw2.write(rosco2.getTiempoRestante()+"\r\n"); //LE ENVIA EL TIEMPO RESTANTE AL ROSCO
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
							bw2.write("ACERTADA\r\n");
							bw2.flush();
							rosco2.anadirAcierto();
							
						} else {
							if(!rosco.todas_preguntas_respondidas()&&!rosco.tiempoTerminado()) {
								jug1_true_jug2_false=true;
							}
							bw2.write("FALLADA\r\n");
							bw2.flush();
							rosco2.anadirFallo();
						}
						//Cambía el respondido de la pregunta a TRUE
						rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
						if(rosco2.todas_preguntas_respondidas()||rosco2.tiempoTerminado()) {
							jug1_true_jug2_false=true;
						}
						//Después volverá al while donde comprobará que si se ha respondido a todas las preguntas, 
						//en caso negativo se quedará esperando para seguir leyendo
						
						//De la misma forma si ha recibido un pasapalabra el cliente deberá enviarle la siguiente pregunta
						//en la que se encuentra
					}else {
						if(!rosco.todas_preguntas_respondidas()&&!rosco.tiempoTerminado()) {
							jug1_true_jug2_false=true;
						}
						bw2.write("PASA\r\n");
						bw2.flush();
					}
					rosco2.setTiempoRestante(Integer.parseInt(br2.readLine()));
				}
			}
			if(rosco.getAciertos()>rosco2.getAciertos()) {
				bw.write("El ganador es: JUGADOR 1 \r\n");
				bw.flush();
				
			} else if(rosco.getAciertos()<rosco2.getAciertos()) {
				bw.write("El ganador es: JUGADOR 2 \r\n");
				bw.flush();
				
			} else if(rosco.getFallos()<rosco2.getFallos()) {
				bw.write("El ganador es: JUGADOR 1 \r\n");
				bw.flush();
				
			}else if(rosco.getFallos()>rosco2.getFallos()) {
				bw.write("El ganador es: JUGADOR 2 \r\n");
				bw.flush();
				
			}else {
				bw.write("EMPATE \r\n");
				bw.flush();
			}
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
