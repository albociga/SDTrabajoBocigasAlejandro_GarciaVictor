package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorPrincipal {
	public static void main(String[] args) {
		//Se crea un pool de hilos para que se pueda acceder a la vez todas las veces que se desee
		ExecutorService pool= Executors.newCachedThreadPool();
		try (ServerSocket ss = new ServerSocket(8498))
		{
			while(true) {
					Socket s1 = ss.accept();
					BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()));
					//System.out.println("Jugador 1 conectado");
					String linea=br.readLine();
					bw.write("Modo recibido\r\n");
					bw.flush();
					AtenderPeticion a;
					if(linea.equals("1")) {
						a=new AtenderPeticion(s1,br,bw);
					}else {
						//System.out.println("Esperado al segundo jugador (conecte otro clente si no lo has hecho)");
						Socket s2 = ss.accept();
						//System.out.println("Jugador 2 conectado");
						a=new AtenderPeticion(s1,s2,br,bw);
						
					}
					pool.execute(a);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			pool.shutdown();
		}

	}
}
