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

public class ClientePrincipal {
	public static void main(String[]args) {
		try(Socket s = new Socket("localhost",8498);
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))){
			System.out.println(br.readLine());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			String opcion="";
			do {
				opcion=teclado.readLine();
			}while(!opcion.equals("1")&&!opcion.equals("2"));
			bw.write(opcion+"\r\n");
			bw.flush();
			List<Integer> lista=new ArrayList<Integer>();
			for(int i=0;i<25;i++) {
				lista.add(i);
			}
			int i=0;
			Rosco r=new Rosco(bw);
			r.setVisible(true);
			while(!lista.isEmpty()) {
				//Lectura necesar�a para que hasta que el server no le de v�a libre para jugar, no juegue
				//Si no hay dos jugadores no quitar primera lectura
				if(opcion.equals("2")) {
					br.readLine();
				}
				bw.write(lista.get(i)+"\r\n");
				bw.flush();
				r.getCampoPregunta().setText(br.readLine());
				String acierto_fallo=br.readLine();
				if(!r.getPasapalabra()) {
					r.actualizarRosco(acierto_fallo, lista.get(i));
					lista.remove(i);
				}
				else i++;
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
