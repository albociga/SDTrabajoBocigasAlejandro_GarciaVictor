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

import GUI.PruebaMusica;
import GUI.Rosco;
import pasapalabra.SoundPlayer_FINAL;
//Clase igual a clientePrincipal solo que no aparece el panel de inicio y el booleano multijugador seria siempre true
public class Cliente extends Thread{
	private String host;
	private int puerto;
	public Cliente(String h, int prt) {
		this.host=h;
		this.puerto=prt;
	}
	public void run() {
		try(Socket s = new Socket(this.host,this.puerto);
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				DataInputStream di = new DataInputStream(s.getInputStream())){
			pruebaMusical(bw,s,di);
			roscoFinal(br,bw);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void pruebaMusical(BufferedWriter bw, Socket s,DataInputStream di) {
		String mandado_server = null;
		try{
			PruebaMusica pm=new PruebaMusica(bw);
			pm.setTitle("Jugador 2");
			//EL NUMERO DE PREGUNTAS QUE HAY EN LA LISTA SON 4
			for (int k = 0; k < 4; k++) {
				//CREO UN FICHERO AUXILIAR EN EL CLIENTE, QUE CUANDO EL JUEGO ACABE, SER? ELIMINADO
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

				//EL CLIENTE RECIBE EL TAMA?O DEL ARCHIVO DE MUSICA
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
				
				fos.close();
				
				int i = 0;
				boolean aux = false;
				boolean respuesta_correcta_segundo_cliente = false;
				
				int tam_pistas = di.readInt();
				while (i < tam_pistas && aux == false && respuesta_correcta_segundo_cliente == false) {
					mandado_server = di.readLine();
					SoundPlayer_FINAL simpleSoundPlayer = new SoundPlayer_FINAL(f.getName(),i); 
					pm.setPista(mandado_server);
					//SoundPlayer3_pruebas simpleSoundPlayer = new SoundPlayer3_pruebas("pista.snd",i); 

					try {
						Thread.sleep(simpleSoundPlayer.getMiliSegundosAudio());
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					simpleSoundPlayer.stop();
					//System.out.println("SI ES SU TURNO PODR? ESCRIBIR");
					//System.out.println();
					mandado_server = di.readLine();
					//System.out.println(mandado_server);
					if(mandado_server.equalsIgnoreCase("START")) {
						pm.activarRespuesta();
						pm.setVisible(true);
						mandado_server = di.readLine();
						if (mandado_server.equalsIgnoreCase("Correcto")) {
							aux = true;
						}
						i++;						
					}
					pm.setVisible(false);
					respuesta_correcta_segundo_cliente = di.readBoolean();					
				}
				pm.setVisible(true);
				mandado_server=di.readLine();
				pm.setPista(mandado_server);
				mandado_server=di.readLine();
				String[] trozos=mandado_server.split(" ");
				pm.setPuntuacionJug1(trozos[0]);
				pm.setPuntuacionJug2(trozos[1]);
				f.delete();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pm.setVisible(false);
			}
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
	public void roscoFinal(BufferedReader br,BufferedWriter bw) {
		List<Integer> lista=new ArrayList<Integer>();
		for(int i=0;i<25;i++) {
			lista.add(i);
		}
		int i=0;
		try {
			int t=Integer.parseInt(br.readLine());
			Rosco r=new Rosco(bw,t);
			r.setTitle("Jugador 2");
			while(!lista.isEmpty()&&r.getTiempoRestante()>0) {
				//Lectura necesar?a para que hasta que el server no le de v?a libre para jugar, no juegue
				//Si no hay dos jugadores no quitar primera lectura
				br.readLine();
				int t1=Integer.parseInt(br.readLine());
				r.setTiempoRestante(t1);
				r.setVisible(true);
				bw.write(lista.get(i)+"\r\n");
				bw.flush();
				r.getCampoPregunta().setText(br.readLine());
				String acierto_fallo=br.readLine();
				if(!r.getPasapalabra()) {
					r.actualizarRosco(acierto_fallo, lista.get(i));
					lista.remove(i);
					if(acierto_fallo.equals("FALLADA")) {
						r.ocultarPanelRespuesta();
						r.pausarReloj();
						r.setVisible(false);
					}
				}
				else {
					r.ocultarPanelRespuesta();
					r.pausarReloj();
					r.setVisible(false);
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
			bw.close();
		}catch(IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
