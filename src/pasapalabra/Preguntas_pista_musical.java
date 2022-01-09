package pasapalabra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Preguntas_pista_musical {
	private List<Pregunta_Pista_Musical> preguntas_musica;

	public Preguntas_pista_musical(List<Pregunta_Pista_Musical> preguntas_musica) {
		this.preguntas_musica = preguntas_musica;
	}

	public List<Pregunta_Pista_Musical> getPreguntas_musica() {
		return preguntas_musica;
	}

	public void setPreguntas_musica(List<Pregunta_Pista_Musical> preguntas_musica) {
		this.preguntas_musica = preguntas_musica;
	}
	
	public static Preguntas_pista_musical crear_preguntas_musicales_aleatorio(List<Pregunta_Pista_Musical> todas_preguntas) {
		List<Pregunta_Pista_Musical> preguntas_musicales_final = new ArrayList<Pregunta_Pista_Musical>();
		Random aleatorio = new Random();
		int numaleatorio = 0;
		for(int i = 0;i<4; i++) {	
			numaleatorio = aleatorio.nextInt(todas_preguntas.size());
			preguntas_musicales_final.add(todas_preguntas.get(numaleatorio));
			todas_preguntas.remove(numaleatorio);
		}
		//System.out.println(rosco_final);
		return (new Preguntas_pista_musical(preguntas_musicales_final));
		
	}
		
}
