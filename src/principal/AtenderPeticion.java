package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import pasapalabra.Pregunta_Pista_Musical;
import pasapalabra.Pregunta_Rosco;
import pasapalabra.Preguntas_pista_musical;
import pasapalabra.Rosco_Final;

public class AtenderPeticion implements Runnable {
	private Socket socket1;
	private Socket socket2;
	private boolean multijugador;
	private int numeroDeJuegos;
	public AtenderPeticion(Socket s1,Socket s2, int numJuegos) {
		this.socket1=s1;
		this.socket2=s2;
		this.multijugador=false;
		this.numeroDeJuegos=numJuegos;
	}
	@Override
	public void run() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));) {
			inicio(br,bw);
			if(!this.multijugador) {//Si no es multijugador, solo se pasa el rosco individual, sino, el servidor pasa a ambos clientes tanto la prueba musical como el rosco multijugador
				roscoIndividual(br,bw);
			}else {
				try(BufferedReader br2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
						BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()));
						DataOutputStream dw = new DataOutputStream(socket1.getOutputStream());
						DataOutputStream dw2 = new DataOutputStream(socket2.getOutputStream());){
					int[]seg=pruebaMusica(br,bw,br2,dw,dw2);
					roscoMultijugador(br,bw,br2,bw2,seg[0],seg[1]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void inicio(BufferedReader br, BufferedWriter bw) {
		try {
			int modo=Integer.parseInt(br.readLine());
			if(modo==2) {
				multijugador=true;
			}
			bw.write("MODDO recibido\r\n");
			bw.flush();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//esta funcion va a devolver las puntuaciones que se han conseguido en este prueba para añadirselos al rosco final
	public int[] pruebaMusica(BufferedReader br, BufferedWriter bw,BufferedReader br2,DataOutputStream dw,DataOutputStream dw2) {
		int [] resultados=new int[2];		
		//Creación de las preguntas musicales aleatoriamente
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
				//Envio del numero de juegos que se estan ejecutando simultaneamente
				dw.writeBytes(numeroDeJuegos+"\r\n");
				dw.flush();
				dw2.writeBytes(numeroDeJuegos+"\r\n");
				dw2.flush();
				// Fichero de la canción, las canciones las contiene el servidor
				File faux = new File(
						preg_musicales.getPreguntas_musica().get(i).getPath_musica_pista_musical());
				cancion = new FileInputStream(faux);

				// Envio a los clientes el tamaño del archivo de musica, para que sepa el tamaño del archivo que recibirá
				long tam_archivo_musica = faux.length();
				dw.writeLong(tam_archivo_musica);
				dw.flush();
				dw2.writeLong(tam_archivo_musica);
				dw2.flush();

				// Creo el Buffer de lectura
				byte[] buff = new byte[1024 * 32];				
				// Leo y envío el fichero a los clientes
				int leidos = cancion.read(buff);
				while (leidos != -1) {
					dw.write(buff, 0, leidos);
					dw.flush();
					dw2.write(buff, 0, leidos);
					dw2.flush();
					leidos = cancion.read(buff);
				}
				cancion.close();
				
				int j = 0;
				boolean respondida_correcta = false;
				
				// Envío a los clientes el número de pistas totales para cada pregunta
				dw.writeInt(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
				dw.flush();
				dw2.writeInt(preg_musicales.getPreguntas_musica().get(i).getPistas().size());
				dw2.flush();
				
				// Bucle con el número de pistas de cada pregunta y un booleano por si uno de los dos clientes responde bien.
				while (j < preg_musicales.getPreguntas_musica().get(i).getPistas().size() 
						&& respondida_correcta == false) {
					// Envío de pistas a los clientes
					dw.writeBytes(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
					dw.flush();
					dw2.writeBytes(preg_musicales.getPreguntas_musica().get(i).getPistas().get(j) + "\n");
					dw2.flush();
					
					//Comienza jugando el Jugador 1
					if (i % 2 == 0) {
						
						// Le envío una señal de Start para que comience a jugar
						dw.writeBytes("START\n");
						dw.flush();
						
						// Servidor espera la respuesta del cliente
						respuesta = br.readLine();
						
						// Comprobación respuesta es correcta
						if (respuesta
								.equalsIgnoreCase(preg_musicales.getPreguntas_musica().get(i).getSolucion())) {
							
							// Si respuesta correcta booleano cambia a true
							respondida_correcta = true;
							
							// Aviso al jugador que ha acertado y si ya no juega
							dw.writeBytes("Correcto\n");
							dw.flush();
							
							dw2.writeBytes("NO JUEGAS\n");
							dw2.flush();
							
							//Envio un booleano que sacará del propio bucle al cliente
							dw2.writeBoolean(true);
							dw2.flush();
							
							//Le sumo los segundos que ha ganado
							aciertos_numseg_jug_1 = aciertos_numseg_jug_1 + (5 - j);
						} else {
							//Como va por turnos, el primero habrá fallado, por tanto se lo indico al primero jugador
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
								//System.out.println(j);
							}

						}
						
					}
					//Para aquellas preguntas impares, el jugador2 comienza a jugar
					if (i % 2 == 1) {
						// MANDO UN START
						dw2.writeBytes("START\n");
						dw2.flush();
						
						// ESPERO SU RESPUESTA
						respuesta = br2.readLine();
						
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
					dw.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\n");
					dw.flush();
					dw2.writeBytes(aciertos_numseg_jug_1 +" "+aciertos_numseg_jug_2+ "\n");
					dw2.flush();
				} else {
					dw.writeBytes(" CANCIÓN ACERTADA\r\n");
					dw.flush();
					dw2.writeBytes(" CANCIÓN ACERTADA\r\n");
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
		//Se asigna los resultados y devuelve el resultado
		resultados[0]=aciertos_numseg_jug_1;
		resultados[1]=aciertos_numseg_jug_2;
		return resultados;
	}
	public void roscoIndividual(BufferedReader br, BufferedWriter bw) {
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
	
	public void roscoMultijugador(BufferedReader br, BufferedWriter bw,BufferedReader br2, BufferedWriter bw2,int segExt1,int segExt2) {
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20+segExt1);
		Rosco_Final rosco2 = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20+segExt2);
		boolean jug1_true_jug2_false = true;
		String linea = null;
		String palabra_actual = null;
		List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
		try{
			bw.write(rosco.getTiempoRestante()+"\r\n");
			bw.flush();
			bw2.write(rosco2.getTiempoRestante()+"\r\n");
			bw2.flush();
			while ((!rosco.todas_preguntas_respondidas() || !rosco2.todas_preguntas_respondidas()) && (!rosco.tiempoTerminado() || !rosco2.tiempoTerminado())) {
				if(!rosco.todas_preguntas_respondidas() && jug1_true_jug2_false &&!rosco.tiempoTerminado()) {
					//CONSOLA DEL SERVER, QUITAR PARA QUE NO APAREZCA(ES UN LOG DE JUGADAS)
					//System.out.println("El JUGADOR 1 esta jugando");
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
					//System.out.println("El JUGADOR 2 esta jugando");
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
