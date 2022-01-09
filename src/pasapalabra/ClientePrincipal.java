package pasapalabra;

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
import java.util.concurrent.CyclicBarrier;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import GUI.ElegirNumJugadores;
import GUI.PruebaMusica;
import GUI.Resultado;
import GUI.Rosco;

public class ClientePrincipal extends Thread{
	public static boolean multijugador;
	private String host;
	private int puerto;
	public ClientePrincipal(String h,int port) {
		this.host=h;
		this.puerto=port;
	}
	public void run() {
		try(Socket s = new Socket(host,puerto);
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))){
			inicio(br,bw);
			if(multijugador) {
				Cliente cli=new Cliente(host,puerto);
				cli.start();
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
	
	public static void inicio(BufferedReader br, BufferedWriter bw) {
		ElegirNumJugadores enj=new ElegirNumJugadores(bw);
		enj.setVisible(true);
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		enj.setVisible(false);
		multijugador=enj.multijugador();
		enj.dispose();
	}
	public static void pruebaMusical(BufferedWriter bw, Socket s) {
		String mandado_server = null;
		try(DataInputStream di = new DataInputStream(s.getInputStream())){
			PruebaMusica pm=new PruebaMusica(bw);
			//EL NUMERO DE PREGUNTAS QUE HAY EN LA LISTA SON 4
			for (int k = 0; k < 4; k++) {
				pm.setVisible(true);
				final CyclicBarrier barrera = new CyclicBarrier(2);
				//CREO UN FICHERO AUXILIAR EN EL CLIENTE, QUE CUANDO EL JUEGO ACABE, SERÁ ELIMINADO
				File f = new File("pista.snd");
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
	
				byte[] buff = new byte[1024];
				int num_iteraciones_leerfichero = (int)(tam_leer_musica/1024);//REVSAR
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
				
				
				
				int i = 0;
				boolean aux = false;
				boolean respuesta_correcta_segundo_cliente = false;
				
				int tam_pistas = di.readInt();
				while (i < tam_pistas && aux == false && respuesta_correcta_segundo_cliente == false) {
					mandado_server = di.readLine();
					pm.setPista(mandado_server);
					SoundPlayer2 simpleSoundPlayer = new SoundPlayer2("pista.snd"); 
					//SoundPlayer3_pruebas simpleSoundPlayer = new SoundPlayer3_pruebas("pista.snd",i); 
					try {
						Thread.sleep(simpleSoundPlayer.getMiliSegundosAudio());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println("SI ES SU TURNO PODRÁ ESCRIBIR");
					//System.out.println();
					mandado_server = di.readLine();
					//System.out.println(mandado_server);
					if(mandado_server.equalsIgnoreCase("START")) {
						pm.activarRespuesta();
						mandado_server = di.readLine();
						pm.desactivarRespuesta();
						if (mandado_server.equalsIgnoreCase("Correcto")) {
							aux = true;
						}
						i++;						
					}
					respuesta_correcta_segundo_cliente = di.readBoolean();					
				}
				pm.setVisible(false);
				mandado_server=di.readLine();
				System.out.println(mandado_server);
				f.delete();
			}
			pm.dispose();
			System.out.println();	
			mandado_server=di.readLine();
			System.out.println(mandado_server);	
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
	public static void roscoFinal(BufferedReader br, BufferedWriter bw) {
		List<Integer> lista=new ArrayList<Integer>();
		for(int i=0;i<25;i++) {
			lista.add(i);
		}
		int i=0;
		try {
			int t=Integer.parseInt(br.readLine());
			Rosco r=new Rosco(bw,t);
			r.setTitle("Jugador 1");
			while(!lista.isEmpty()&&r.getTiempoRestante()>0) {
				//Lectura necesaría para que hasta que el server no le de vía libre para jugar, no juegue
				//Si no hay dos jugadores no quitar primera lectura
				if(multijugador) {
					br.readLine();
					int t1=Integer.parseInt(br.readLine());
					r.setTiempoRestante(t1);
				}
				r.setVisible(true);
				bw.write(lista.get(i)+"\r\n");
				bw.flush();
				r.getCampoPregunta().setText(br.readLine());
				String acierto_fallo=br.readLine();
				if(!r.getPasapalabra()) {
					r.actualizarRosco(acierto_fallo, lista.get(i));
					lista.remove(i);
					if(acierto_fallo.equals("FALLADA")&&multijugador) {
						r.pausarReloj();
						r.setVisible(false);
					}
				}
				else {
					if(multijugador) {
						r.pausarReloj();
						r.setVisible(false);
					}
					i++;
				}
				if(i>=lista.size()) {
					i=0;
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
