package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import GUI.ElegirNumJugadores;
import GUI.PruebaMusica;
import GUI.Reproduciendo;
import GUI.Resultado;
import GUI.Rosco;
import pasapalabra.SoundPlayer_FINAL;

public class ClientePrincipal extends Thread{
	private  boolean multijugador;
	private String host;
	private int puerto;
	public ClientePrincipal(String h,int port) {
		this.host=h;
		this.puerto=port;
	}
	public void run() {
		try(Socket s = new Socket(host,puerto);
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				DataInputStream di = new DataInputStream(s.getInputStream())){
			inicio(br,bw);
			if(multijugador) {//Si es multijufador crea un cliente y lo ejecuta
				Cliente cli=new Cliente(host,puerto);
				cli.start();
				pruebaMusical(bw,s,di);
			}
			roscoFinal(br,bw);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inicio(BufferedReader br, BufferedWriter bw) {
		//Se crea y muestra el panel para elegir el modo de juego
		ElegirNumJugadores enj=new ElegirNumJugadores(bw);
		enj.setVisible(true);
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Una vez le responde el servidor que ha recibido el modo de juego continua, solo responde cuando se haya pulsado el boton aceptar
		enj.setVisible(false);
		multijugador=enj.multijugador();
		enj.dispose();
	}
	
	public void pruebaMusical(BufferedWriter bw, Socket s,DataInputStream di) {
		String mandado_server = null;
		try{
			PruebaMusica pm=new PruebaMusica(bw);
			pm.setTitle("Jugador 1");
			Reproduciendo rep=new Reproduciendo();
			//EL NUMERO DE PREGUNTAS QUE HAY EN LA LISTA SON 4
			for (int k = 0; k < 4; k++) {
				//CREO UN FICHERO AUXILIAR EN EL CLIENTE, QUE CUANDO EL JUEGO ACABE, SERÁ ELIMINADO
				int numFich=Integer.parseInt(di.readLine());
				File f = new File("pista"+numFich+".snd");
				if (!f.exists()) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
				
				FileOutputStream fos = new FileOutputStream(f);

				//EL CLIENTE RECIBE EL TAMAÑO DEL ARCHIVO DE MUSICA
				long tam_leer_musica =  di.readLong();
				//Lee el fichero de musica
				byte[] buff = new byte[1024];
				int num_iteraciones_leerfichero = (int)(tam_leer_musica/1024);
				if(tam_leer_musica%1024!=0) {
					num_iteraciones_leerfichero++;
				}
				int leidos = 0;
				
				int p = 0;
				for (p=0; p < num_iteraciones_leerfichero-1; p ++) {			
					leidos = di.read(buff);
					fos.write(buff, 0, leidos);
					fos.flush();
				}
				int tam_leido= p*1024;
				byte[] buff2 = new byte[(int)(tam_leer_musica - tam_leido)];
				
				leidos = di.read(buff2);
				fos.write(buff2, 0, leidos);
				
				fos.close(); //Cierra el fichero una vez leido
				
				int i = 0;
				boolean aux = false;
				boolean respuesta_correcta_segundo_cliente = false;
				
				int tam_pistas = di.readInt();
				while (i < tam_pistas && aux == false && respuesta_correcta_segundo_cliente == false) {
					//Envia el servidor un fragmento de la pista musical
					mandado_server = di.readLine();
					SoundPlayer_FINAL simpleSoundPlayer = new SoundPlayer_FINAL(f.getName(),i); 
					pm.setPista(mandado_server);
					rep.setVisible(true);//Muestra el mensaje de reproduciendo
					try {//Espera a que suene toda la pista musical
						Thread.sleep(simpleSoundPlayer.getMiliSegundosAudio());
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					simpleSoundPlayer.stop();
					rep.setVisible(false);
					//System.out.println("SI ES SU TURNO PODRÁ ESCRIBIR");
					//System.out.println();
					mandado_server = di.readLine();
					//System.out.println(mandado_server);
					if(mandado_server.equalsIgnoreCase("START")) {
						pm.activarRespuesta();//Si es tu turno se hace visible y se activa la respuesta
						pm.setVisible(true);
						mandado_server = di.readLine();
						if (mandado_server.equalsIgnoreCase("Correcto")) {//Si es correcto pasan de cancion
							aux = true;
						}
						i++;						
					}
					pm.setVisible(false);
					respuesta_correcta_segundo_cliente = di.readBoolean();					
				}
				mandado_server=di.readLine();
				pm.setPista(mandado_server);//Muestra si se ha acertado o si se ha agotado el numero de intentos y actualiza el marcador
				pm.setVisible(true);
				mandado_server=di.readLine();
				String[] trozos=mandado_server.split(" ");
				pm.setPuntuacionJug1(trozos[0]);
				pm.setPuntuacionJug2(trozos[1]);
				f.delete();
				try {//Espera 4 segundos a que el cuadro sea leido
					Thread.sleep(4000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pm.setVisible(false);
			}
			rep.dispose();
			pm.dispose();	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void roscoFinal(BufferedReader br, BufferedWriter bw) {
		//Se crea una lista de enteros, que harán referencia a la posicion del rosco en el que están
		List<Integer> lista=new ArrayList<Integer>();
		for(int i=0;i<25;i++) {
			lista.add(i);
		}
		int i=0;
		try {
			int t=Integer.parseInt(br.readLine());//El servidor pasa el tiempo inicial del rosco
			Rosco r=new Rosco(bw,t);//Se lo pasa a rosco para que se descuente el tiempo mientras se juega
			r.setTitle("Jugador 1");
			while(!lista.isEmpty()&&r.getTiempoRestante()>0) {//Se mantiene en el bucle si todavía quedan elementos por responder y tiempo restante
				//Lectura necesaría para que hasta que el server no le de vía libre para jugar, no juegue
				//Si no hay dos jugadores, quitar primera lectura
				if(multijugador) {
					br.readLine();
					int t1=Integer.parseInt(br.readLine());//El cliente lee el tiempo restante que le envia el servidor
					r.setTiempoRestante(t1);//Se lo pasa al rosco para que se lo asigne al tiempo restante
				}
				r.setVisible(true);
				bw.write(lista.get(i)+"\r\n");//Envia al servidor en que letra se situa el cliente
				bw.flush();
				r.getCampoPregunta().setText(br.readLine());//Se muestra en la interfaz el enunciado de la pregunta
				String acierto_fallo=br.readLine();//Una vez respondida a través de la interfaz, lee si ha acertado o fallado la pregunta
				if(!r.getPasapalabra()) {//Entra si en el rosco no ha pulsado pasapalabra, es decir, si la ha respondido
					r.actualizarRosco(acierto_fallo, lista.get(i));//Actualiza el rosco, pasando si la ha acertado o la ha fallado y la posición en la que estamos
					lista.remove(i);//Borra el elemento en el que estamos en la lista
					if(acierto_fallo.equals("FALLADA")&&multijugador) {
						r.ocultarPanelRespuesta();
						r.pausarReloj();//Si falla pausa el reloj y pasa de turno
						r.setVisible(false);
					}
				}
				else {
					if(multijugador) {
						r.ocultarPanelRespuesta();
						r.pausarReloj();//Si pasa para el reloj
						r.setVisible(false);
					}
					i++;
				}
				if(i>=lista.size()) {
					i=0;//Si el indicice es mayor o igual que el tamaño de la lista vuelve al principio
				}
				bw.write(r.getTiempoRestante()+"\r\n");
				bw.flush();
			}
			r.pausarReloj();
			r.setVisible(false);
			r.dispose();
			String mensajeFinal=br.readLine();
			Resultado result=new Resultado(mensajeFinal);
			result.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
