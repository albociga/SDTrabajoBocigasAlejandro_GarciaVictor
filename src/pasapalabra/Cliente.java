package pasapalabra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import GUI.Rosco;

public class Cliente {
	public static void main(String[]args) {
		try(Socket s = new Socket("localhost",8498);
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))){
			
			List<Integer> lista=new ArrayList<Integer>();
			for(int i=0;i<25;i++) {
				lista.add(i);
			}
			int i=0;
			Rosco r=new Rosco(bw);
			while(!lista.isEmpty()) {
				//Lectura necesaría para que hasta que el server no le de vía libre para jugar, no juegue
				//Si no hay dos jugadores no quitar primera lectura
				br.readLine();
				r.setVisible(true);
				bw.write(lista.get(i)+"\r\n");
				bw.flush();
				r.getCampoPregunta().setText(br.readLine());
				String acierto_fallo=br.readLine();
				if(!r.getPasapalabra()) {
					r.actualizarRosco(acierto_fallo, lista.get(i));
					lista.remove(i);
					if(acierto_fallo.equals("FALLADA")) {
						r.setVisible(false);
					}
				}
				else {
					r.setVisible(false);
					i++;
				}
				if(i>=lista.size()) {
					i=0;
				}
			}
			r.setVisible(false);
			r.dispose();
			System.out.println(br.readLine());
			bw.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
