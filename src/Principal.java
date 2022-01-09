
import pasapalabra.ClientePrincipal;

public class Principal {
	public static void main(String [] args) {
		ClientePrincipal cp=new ClientePrincipal("localhost",8498);
		cp.start();
	}
}
