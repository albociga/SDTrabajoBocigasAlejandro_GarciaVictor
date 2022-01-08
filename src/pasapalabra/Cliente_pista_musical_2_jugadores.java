package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Cliente_pista_musical_2_jugadores {
	public static void main(String[] args) {
		
		String mandado_server = null;
		String leido_teclado = null;
		try (Socket s = new Socket("localhost", 8498);
				DataInputStream di = new DataInputStream(s.getInputStream());
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				//BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {

			//EL NUMERO DE PREGUNTAS QUE HAY EN LA LISTA SON 4
			for (int k = 0; k < 4; k++) {
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
				
				fos.close();
				
				int i = 0;
				boolean aux = false;
				boolean respuesta_correcta_segundo_cliente = false;
				
				int tam_pistas = di.readInt();
				
				while (i < tam_pistas && aux == false && respuesta_correcta_segundo_cliente == false) {
					mandado_server = di.readLine();
					
					System.out.println("Pista:" + mandado_server);
					System.out.println();
					System.out.println();
					System.out.println("REPRODUCIENDO SONIDO");	
					SoundPlayer2 simpleSoundPlayer = new SoundPlayer2("pista.snd"); 
					//SoundPlayer3_pruebas simpleSoundPlayer = new SoundPlayer3_pruebas("pista.snd",i); 
					try {
						Thread.sleep(simpleSoundPlayer.getMiliSegundosAudio());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println();
					System.out.println();
					//System.out.println("SI ES SU TURNO PODRÁ ESCRIBIR");
					//System.out.println();
					mandado_server = di.readLine();
					//System.out.println(mandado_server);
					if(mandado_server.equalsIgnoreCase("START")) {
						System.out.println("********SU TURNO********");
						leido_teclado = teclado.readLine();
						bw.write(leido_teclado);
						bw.newLine();
						bw.flush();
						mandado_server = di.readLine();
						if (mandado_server.equalsIgnoreCase("Correcto")) {
							aux = true;
						}
						i++;						
					}
					respuesta_correcta_segundo_cliente = di.readBoolean();					
				}
				mandado_server=di.readLine();
				System.out.println(mandado_server);
				f.delete();
			}
			System.out.println();	
			mandado_server=di.readLine();
			System.out.println(mandado_server);	
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
