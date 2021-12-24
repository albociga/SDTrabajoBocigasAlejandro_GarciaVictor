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
			while(!lista.isEmpty()) {
				bw.write(lista.get(i)+"\r\n");
				bw.flush();
				System.out.println(br.readLine());
				String respuesta=teclado.readLine();
				bw.write(respuesta+"\r\n");
				bw.flush();
				if(!respuesta.equalsIgnoreCase("PASAPALABRA")) {
					lista.remove(i);
				}
				else i++;
				if(i>=lista.size()) {
					i=0;
				}
				System.out.println(br.readLine());
			}
			System.out.println(br.readLine());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
