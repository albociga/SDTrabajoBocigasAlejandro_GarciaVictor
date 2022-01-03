package pasapalabra;

import java.util.ArrayList;
import java.util.List;

public class Pregunta_Pista_Musical {

	private String path_musica_pista_musical;
	private List<String> pistas;
	private String solucion;
	
	public Pregunta_Pista_Musical(String path_musica_pista_musical, List<String> pistas, String solucion) {
		this.path_musica_pista_musical = path_musica_pista_musical;
		this.pistas = pistas;
		this.solucion = solucion;
	}

	public String getPath_musica_pista_musical() {
		return path_musica_pista_musical;
	}

	public void setPath_musica_pista_musical(String path_musica_pista_musical) {
		this.path_musica_pista_musical = path_musica_pista_musical;
	}

	public List<String> getPistas() {
		return pistas;
	}

	public void setPistas(List<String> pistas) {
		this.pistas = pistas;
	}

	public String getSolucion() {
		return solucion;
	}

	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}

	@Override
	public String toString() {
		return "Pregunta_Pista_Musical [path_musica_pista_musical=" + this.path_musica_pista_musical + ", pistas=" + this.pistas
				+ ", solucion=" + this.solucion + "]";
	}
	
	public static List<Pregunta_Pista_Musical> todas_pistas_musicales_existentes(){
		List<String> pistas1 = new ArrayList<String>();
		pistas1.add("El cantante tiene apellido de �rbol frutal pero en plural y de nombre Jos� Luis");
		pistas1.add("Todo el mundo cree que va sobre un hombre que ha perdido a su mujer por culpa de otro");
		pistas1.add("En realidad esta dedicada por un padre a su hija que se ha enamorado y se va a ir de casa");
		pistas1.add("El estribillo esta lleno de preguntas");
		pistas1.add("El padre est� muy preocupdo y dice que se abrigue y se lleve el paraguas");
		List<String> pistas2 = new ArrayList<String>();
		pistas2.add("La cantan los cuatro muchachos m�s famosos de Liverpool");
		pistas2.add("Aparece en el mismo �lbum de Yesterday");
		pistas2.add("Tony Ronals cantaba una con el mismo nombre pero con otra letra");
		pistas2.add("En ingl�s solo tiene cuatro letras y lo dices cuando necesitas algo");
		pistas2.add("Es una llamada de socorro y de ayuda");
		List<String> pistas3 = new ArrayList<String>();
		pistas3.add("El cantante es un ex-triunfito y tiene unos rizos de oro");
		pistas3.add("No tiene nada que ver con los trenes aunque su nombre sea el del m�s r�pido");
		pistas3.add("Desde luego si la chica fuera suya conseguir�a todo del pobre chico");
		pistas3.add("Los romanos saludabasn como el comienzo del t�tulo de la canci�n");
		pistas3.add("Tiene nombre de oraci�n religiosa");
		List<String> pistas4 = new ArrayList<String>();
		pistas4.add("Es una canci�n t�pica de navidad");
		pistas4.add("Cada estrofa nos explica el nacimiento de Jes�s");
		pistas4.add("En la primera campana ver�s al ni�o en la cuna");
		pistas4.add("La conocemos con el nombre de 'Campana sobre campana'");
		pistas4.add("Un pastorcillo llevaba al portal reques�n, manteca y vino");
		List<String> pistas5 = new ArrayList<String>();
		pistas5.add("El padre del cantante era torero y la madre se pinta el pelo");
		pistas5.add("Aunque pasa desapercibido en la canci�n, est� dedicada a una chica llamada Roc�o");
		pistas5.add("El protagonista ten�a el corazon malherido");
		pistas5.add("El estribillo repite 'no dir�s que no'");
		pistas5.add("El grupo Az�car Moreno tambi�n cant� a uno pero no era amante");
		List<String> pistas6 = new ArrayList<String>();
		pistas6.add("La cantante es canadiense");
		pistas6.add("El v�deo de la canci�n ha tenido tanto �xito que hsta Justin Biever hizo uno casero con Selena G�mez");
		pistas6.add("La chica se empe�a en darle su n�mero de tel�fono al guaperas");
		pistas6.add("Por mucho que la chica lo intenta con el video sexy, no tienen nada que hacer con el chico");
		pistas6.add("Al final resulta que al chico no le gustan las chicas por mucho que le diga que la llame");
		//List<String> pistas7 = new ArrayList<String>();
		//List<String> pistas8 = new ArrayList<String>();
		//List<String> pistas9 = new ArrayList<String>();
		//List<String> pistas10 = new ArrayList<String>();
		Pregunta_Pista_Musical p1 = new Pregunta_Pista_Musical("1.snd", pistas1, "Y como es el");
		Pregunta_Pista_Musical p2 = new Pregunta_Pista_Musical("2.snd", pistas2, "Help");
		Pregunta_Pista_Musical p3 = new Pregunta_Pista_Musical("3.snd", pistas3, "Ave Maria");
		Pregunta_Pista_Musical p4 = new Pregunta_Pista_Musical("4.snd", pistas4, "Campanas de Belen");
		Pregunta_Pista_Musical p5 = new Pregunta_Pista_Musical("5.snd", pistas5, "Amante Bandido");
		Pregunta_Pista_Musical p6 = new Pregunta_Pista_Musical("6.snd", pistas6, "Call me maybe");
		//Pregunta_Pista_Musical p7 = new Pregunta_Pista_Musical("", pistas7, "");
		//Pregunta_Pista_Musical p8 = new Pregunta_Pista_Musical("", pistas8, "");
		//Pregunta_Pista_Musical p9 = new Pregunta_Pista_Musical("", pistas9, "");
		//Pregunta_Pista_Musical p10 = new Pregunta_Pista_Musical("", pistas10, "");
		List<Pregunta_Pista_Musical> todas_preguntas_pista_musical = new ArrayList<Pregunta_Pista_Musical>();
		todas_preguntas_pista_musical.add(p1);
		todas_preguntas_pista_musical.add(p2);
		todas_preguntas_pista_musical.add(p3);
		todas_preguntas_pista_musical.add(p4);
		todas_preguntas_pista_musical.add(p5);
		todas_preguntas_pista_musical.add(p6);
		//todas_preguntas_pista_musical.add(p7);
		//todas_preguntas_pista_musical.add(p8);
		//todas_preguntas_pista_musical.add(p9);
		//todas_preguntas_pista_musical.add(p10);
		return todas_preguntas_pista_musical;
		
	}
	
	
	
}
