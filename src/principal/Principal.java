package principal;

public class Principal {
	public static void main(String [] args) {
		ClientePrincipal cp=new ClientePrincipal("localhost",8498);
		cp.start();
		try {
			Thread.sleep(1000); //Ponemos un sleep para asegurarnos que el primero que entra al servidor es el cliente principal
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cliente cli=new Cliente("localhost",8498);
		cli.start();
	}
}
