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

public class Cliente extends Thread{
	String host;
	int puerto;
	public Cliente(String h, int prt) {
		this.host=h;
		this.puerto=prt;
	}
	public void run() {
		try(Socket s = new Socket(this.host,this.puerto);
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))){
			List<Integer> lista=new ArrayList<Integer>();
			for(int i=0;i<25;i++) {
				lista.add(i);
			}
			int i=0;
			int t=Integer.parseInt(br.readLine());
			Rosco r=new Rosco(bw,t);
			r.setTitle("Jugador 2");
			while(!lista.isEmpty()&&r.getTiempoRestante()>0) {
				//Lectura necesaría para que hasta que el server no le de vía libre para jugar, no juegue
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
						r.pausarReloj();
						r.setVisible(false);
					}
				}
				else {
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
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
