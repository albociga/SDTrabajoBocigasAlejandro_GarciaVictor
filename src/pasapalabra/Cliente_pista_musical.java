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

public class Cliente_pista_musical {
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
				//System.out.println(1);

				//AQUI CREA EL BUFFER Y RECIBE EL FICHERO
				byte[] buff = new byte[1024 * 32];
				int leidos = di.read(buff);
				//System.out.println(2);
				for (int i = leidos; i < tam_leer_musica; i += leidos) {
					fos.write(buff, 0, leidos);
					fos.flush();
					leidos = di.read(buff);
				}
				fos.close();
				
				int i = 0;
				boolean aux = false;
				
				//***********************************AQUI ES DONDE ME APARECE EL PROBLEMA**********************************
				//A VECES RECIBE 5 (CORRECTO) , A VECES NUMERO ALEATORIO Y OTRAS SE QUEDA PILLADO
				int tam_pistas = di.readInt();
				System.out.println("ESTE VALOR BIEN(5) O MAL: "+ tam_pistas);
				//***********************************AQUI ES DONDE ME APARECE EL PROBLEMA**********************************
				
				while (i < tam_pistas && aux == false) {
					mandado_server = di.readLine();
					
					System.out.println("Pista:" + mandado_server);
					System.out.println("REPRODUCIENDO SONIDO");
					System.out.println("-------------AQUI SONARA LA MUSICA |||FALTA IMPLEMENTAR|||-------------");
					System.out.println("AHORA PUEDE ESCRIBIR");
					leido_teclado = teclado.readLine();
					bw.write(leido_teclado);
					bw.newLine();
					bw.flush();
					//mandado_server = di.readLine().trim();
					char resp = di.readChar();
					if (resp=='C') {
						aux = true;
					}
				}
				mandado_server=di.readLine();
			}

			// bw.write("hola");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
