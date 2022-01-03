package pasapalabra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Rosco_Final {
	//Preguntas que contienen el Rosco
	private List<Pregunta_Rosco> preguntas = new ArrayList<Pregunta_Rosco>();
	private int aciertos;
	private int fallos;
	private int tiempoRestante;
	//Constructor de un Rosco nuevo, habitualmente se iniciara con aleatoriamente
	public Rosco_Final(List<Pregunta_Rosco> preguntas, int tiempo) {
		this.preguntas = preguntas;
		this.aciertos=0;
		this.fallos=0;
		this.tiempoRestante=tiempo;
	}
	
	public List<Pregunta_Rosco> getPreguntas() {
		return this.preguntas;
	}

	public void setPreguntas(List<Pregunta_Rosco> preguntas) {
		this.preguntas = preguntas;
	}
	
	//Devuelve true si todas las preguntas del rosco han sido respondidas y se puede terminar la ejecución
	public boolean todas_preguntas_respondidas() {
		for (Pregunta_Rosco pregunta : this.preguntas) {
			if(!pregunta.isRespondida()) {
				return false; 
			}
		}
		return true;
		
	}
	
	//Incluir una pregunta específica en una posición especifica
	//(DE MOMENTO NADA)Será usado para pruebas, a lo mejor para juego final poner trampas al contrincante o algo así
	public void incluir_pregunta_posicion_determinada(ArrayList<Pregunta_Rosco> preguntas,Pregunta_Rosco paux,char letra){
		int letra_mayus = Character.toUpperCase(letra);
		if((letra_mayus<=90)&&(letra_mayus>=65)) {
			preguntas.set(letra_mayus-65,paux);
		}
		if(letra_mayus==209) {
			preguntas.set(14,paux);
		}
	}
	
	//Crear un rosco completamente aleatorio entre todas las preguntas existentes
	public static List<Pregunta_Rosco> crear_rosco_aleatorio(Map<String,List<Pregunta_Rosco>> l){
		List<Pregunta_Rosco> rosco_final = new ArrayList<Pregunta_Rosco>();
		List<String> abecedario = Arrays.asList("A","B","C","D","E","F","G","H","I","J","L","M","N","Ñ","O","P","Q","R","S","T","U","V","X","Y","Z");
		Random aleatorio = new Random();
		for(String letra : abecedario) {
			//System.out.println(letra);
			if(l.containsKey(letra)) {
				//System.out.println("Sí");
				//System.out.println(l.get(letra));
				rosco_final.add(l.get(letra).get(aleatorio.nextInt(l.get(letra).size())));
			}
		}
		//System.out.println(rosco_final);
		return rosco_final;
	}
	public int getAciertos() {
		return this.aciertos;
	}
	public void anadirAcierto() {
		this.aciertos++;
	}
	public int getFallos() {
		return this.fallos;
	}
	public void anadirFallo() {
		this.fallos++;
	}
	public int getTiempoRestante() {
		return this.tiempoRestante;
	}
	public void setTiempoRestante(int t) {
		this.tiempoRestante=t;
	}
	public boolean tiempoTerminado() {
		return this.tiempoRestante<=0;
	}
}
