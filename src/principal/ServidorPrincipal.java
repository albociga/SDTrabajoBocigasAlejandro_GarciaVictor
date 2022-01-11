package principal;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorPrincipal {
	public static void main(String[] args) {
		//Se crea un pool de hilos para que se pueda acceder a la vez todas las veces que se desee
		ExecutorService pool= Executors.newCachedThreadPool();
		int numJuegos=0;
		try (ServerSocket ss = new ServerSocket(8498))
		{
			while(true) {
					Socket s1 = ss.accept();
					numJuegos++;
					Socket s2 = ss.accept();
					AtenderPeticion a=new AtenderPeticion(s1,s2,numJuegos);
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
