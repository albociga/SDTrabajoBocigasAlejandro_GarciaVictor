package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		Rosco_Final rosco = new Rosco_Final(
				Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20);
		String linea = null;
		String palabra_actual = null;
		List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","�","O","P","Q","R","S","T","U","V","X","Y","Z");
		if(!this.multijugador) {
			try {
				bw.write(rosco.getTiempoRestante()+"\r\n");
				bw.flush();
				//El servidor comprueba que todas las palabras hayan sido respondidas en cuyo caso terminar� el rosco
				while (!rosco.todas_preguntas_respondidas()&&!rosco.tiempoTerminado()) {
					// Lee el n�mero de la letra en la que se encuentra el jugador A=0,B=1....
					palabra_actual = br.readLine();
					//Busca el enunciado en la lista de preguntas del rosco y lo env�a el cliente
					String pregunta=rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEmpieza_contiene() 
							+" con "+abecedario.get(Integer.parseInt(palabra_actual))+" "
							+rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado()+"\r\n";
					bw.write(pregunta);
					bw.flush();
					//Queda esperando la respuesta del cliente y la lee
					linea = br.readLine();
					//Si lo escrito por el cliente NO es "PASAPALABRA"
					if (!linea.equalsIgnoreCase("PASAPALABRA")) {
						//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y env�a el resultado
						if (rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
							bw.write("ACERTADA\r\n");
							bw.flush();
							rosco.anadirAcierto();
						} else {
							bw.write("FALLADA\r\n");
							bw.flush();
							rosco.anadirFallo();
						}
						//Camb�a el respondido de la pregunta a TRUE
						rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
						//Despu�s volver� al while donde comprobar� que si se ha respondido a todas las preguntas, 
						//en caso negativo se quedar� esperando para seguir leyendo
						
						//De la misma forma si ha recibido un pasapalabra el cliente deber� enviarle la siguiente pregunta
						//en la que se encuentra
					}else {
						bw.write("PASA\r\n");
						bw.flush();
					}
					rosco.setTiempoRestante(Integer.parseInt(br.readLine()));
				}
				bw.write("HA COMPLETADO EL ROSCO \r\n");
				bw.flush();
				bw.write("Has obtenido "+rosco.getAciertos() +" aciertos y "+rosco.getFallos()+" fallos \r\n");
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Rosco_Final rosco2 = new Rosco_Final(
					Rosco_Final.crear_rosco_aleatorio(Pregunta_Rosco.crea_hash_map_preguntas()),20);
			boolean jug1_true_jug2_false = true;
			try(BufferedReader br2 = new BufferedReader(new InputStreamReader(this.socket2.getInputStream()));
						BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(this.socket2.getOutputStream()))){
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
						// Lee el n�mero de la letra en la que se encuentra el jugador A=0,B=1....
						palabra_actual = br.readLine();
						//Busca el enunciado en la lista de preguntas del rosco y lo env�a el cliente
						String pregunta=rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEmpieza_contiene() 
								+" con "+abecedario.get(Integer.parseInt(palabra_actual))+" "
								+rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado()+"\r\n";
						bw.write(pregunta);
						bw.flush();
						//Queda esperando la respuesta del cliente y la lee
						linea = br.readLine();
						//Si lo escrito por el cliente NO es "PASAPALABRA"
						if (!linea.equalsIgnoreCase("PASAPALABRA")) {
							//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y env�a el resultado
							if (rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
								bw.write("ACERTADA\r\n");
								bw.flush();
								rosco.anadirAcierto();
							} else {
								if(!rosco2.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=false;
								}
								bw.write("FALLADA\r\n");
								bw.flush();
								rosco.anadirFallo();
							}
							//Camb�a el respondido de la pregunta a TRUE
							rosco.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
							//Despu�s volver� al while donde comprobar� que si se ha respondido a todas las preguntas, 
							//en caso negativo se quedar� esperando para seguir leyendo
							//En caso de terminar este rosco y acertar la ultima pregunta utilizamos este if para cambiar al otro rosco si este otro no ha terminado
							if(rosco.todas_preguntas_respondidas()) {
								jug1_true_jug2_false=false;
							}
							//De la misma forma si ha recibido un pasapalabra el cliente deber� enviarle la siguiente pregunta
							//en la que se encuentra
						}else {
							if(!rosco2.todas_preguntas_respondidas()) {
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
						// Lee el n�mero de la letra en la que se encuentra el jugador A=0,B=1....
						palabra_actual = br2.readLine();
						//Busca el enunciado en la lista de preguntas del rosco y lo env�a el cliente
						String pregunta=rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getEmpieza_contiene() 
								+" con "+ abecedario.get(Integer.parseInt(palabra_actual))+": "
								+rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getEnunciado()+"\r\n";
						bw2.write(pregunta);
						bw2.flush();
						//Queda esperando la respuesta del cliente y la lee
						linea = br2.readLine();
						//Si lo escrito por el cliente NO es "PASAPALABRA"
						if (!linea.equalsIgnoreCase("PASAPALABRA")) {
							//Comprueba que lo leido del cliente y la respuesta a la pregunta sea correcta o falsa, y env�a el resultado
							if (rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).getRespuesta().equalsIgnoreCase(linea.toUpperCase())) {
								bw2.write("ACERTADA\r\n");
								bw2.flush();
								rosco2.anadirAcierto();
								
							} else {
								if(!rosco.todas_preguntas_respondidas()) {
									jug1_true_jug2_false=true;
								}
								bw2.write("FALLADA\r\n");
								bw2.flush();
								rosco2.anadirFallo();
							}
							//Camb�a el respondido de la pregunta a TRUE
							rosco2.getPreguntas().get(Integer.parseInt(palabra_actual)).setRespondida(true);
							if(rosco2.todas_preguntas_respondidas()) {
								jug1_true_jug2_false=true;
							}
							//Despu�s volver� al while donde comprobar� que si se ha respondido a todas las preguntas, 
							//en caso negativo se quedar� esperando para seguir leyendo
							
							//De la misma forma si ha recibido un pasapalabra el cliente deber� enviarle la siguiente pregunta
							//en la que se encuentra
						}else {
							if(!rosco.todas_preguntas_respondidas()) {
								jug1_true_jug2_false=true;
							}
							bw2.write("PASA\r\n");
							bw2.flush();
						}
						rosco2.setTiempoRestante(Integer.parseInt(br2.readLine()));
					}
				}
				bw.write("LOS DOS JUGADORES HAN COMPLETADO EL ROSCO \r\n");
				bw.flush();
				bw2.write("LOS DOS JUGADORES HAN COMPLETADO EL ROSCO \r\n");
				bw2.flush();
				if(rosco.getAciertos()>rosco2.getAciertos()) {
					bw.write("El ganador es: JUGADOR 1 \r\n");
					bw.flush();
					bw2.write("El ganador es: JUGADOR 1 \r\n");
					bw2.flush();
				} else if(rosco.getAciertos()<rosco2.getAciertos()) {
					bw.write("El ganador es: JUGADOR 2 \r\n");
					bw.flush();
					bw2.write("El ganador es: JUGADOR 2 \r\n");
					bw2.flush();
				} else if(rosco.getFallos()<rosco2.getFallos()) {
					bw.write("El ganador es: JUGADOR 1 \r\n");
					bw.flush();
					bw2.write("El ganador es: JUGADOR 1 \r\n");
					bw2.flush();
				}else if(rosco.getFallos()>rosco2.getFallos()) {
					bw.write("El ganador es: JUGADOR 2 \r\n");
					bw.flush();
					bw2.write("El ganador es: JUGADOR 2 \r\n");
					bw2.flush();
				}else {
					bw.write("EMPATE \r\n");
					bw.flush();
					bw2.write("EMPATE \r\n");
					bw2.flush();
				}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		
		
	}
}
