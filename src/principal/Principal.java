package principal;

public class Principal {
	public static void main(String [] args) {
		ClientePrincipal cp=new ClientePrincipal("localhost",8498);
		cp.start();
		Cliente cli=new Cliente("localhost",8498);
		cli.start();
	}
}
