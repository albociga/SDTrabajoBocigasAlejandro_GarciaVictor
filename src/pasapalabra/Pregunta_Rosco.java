package pasapalabra;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pregunta_Rosco {
	private String enunciado; //Enunciado de la pregunta
	private String respuesta; //Respuesta correcta a la pregunta
	private boolean respondida; //Si la pregunta ha sido respondida o no, al inicio todas ser·n falsas
	private String empieza_contiene; //Si la respuesta empieza o contiene la letra
	
	//Constructor que usaremos al iniciar un nuevo rosco
	/*public Pregunta_Rosco(String enunciado, String respuesta, String e_c) {
		this.enunciado = enunciado;
		this.respuesta = respuesta;
		this.respondida = false;
		this.empieza_contiene = e_c;
	}*/
	
	//Para pruebas
	public Pregunta_Rosco(String enunciado, String respuesta, String e_c, boolean respondida) {
		this.enunciado = enunciado;
		this.respuesta = respuesta;
		this.respondida = respondida;
		this.empieza_contiene = e_c;
	}
	
	public String getEnunciado() {
		return this.enunciado;
	}
	
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	public String getRespuesta() {
		return this.respuesta;
	}
	
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public boolean isRespondida() {
		return this.respondida;
	}
	
	public void setRespondida(boolean respondida) {
		this.respondida = respondida;
	}
	
	public String getEmpieza_contiene() {
		return this.empieza_contiene;
	}

	public void setEmpieza_contiene(String empieza_contiene) {
		this.empieza_contiene = empieza_contiene;
	}
	
	//CREA EL HASMAP CON TODAS LAS PREGUNTAS, DE MOMENTO CON ESTAS SOBRA PARA PRUEBAS, YA DECIDIREMOS 
	//COMO GESTIONAR Y GUARDAR LAS PREGUNTAS
	//SI SE A—ADEN NUEVAS PREGUNTAS, CUIDADO CON VARIABLE 'n' DE LA LINEA 366, HAY QUE CAMBIARLA
	public static Map<String,List<Pregunta_Rosco>> crea_hash_map_preguntas() {

				Map<String,List<Pregunta_Rosco>> l = new HashMap<String,List<Pregunta_Rosco>>();
				
				List<Pregunta_Rosco> lA = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lB = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lC = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lD = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lE = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lF = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lG = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lH = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lI = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lJ = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lL = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lM = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lN = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> l— = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lO = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lP = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lQ = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lR = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lS = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lT = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lU = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lV = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lX = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lY = new ArrayList<Pregunta_Rosco>();
				
				List<Pregunta_Rosco> lZ = new ArrayList<Pregunta_Rosco>();
				
				
				l.put("A", lA);
				l.put("B", lB);
				l.put("C", lC);
				l.put("D", lD);
				l.put("E", lE);
				l.put("F", lF);
				l.put("G", lG);
				l.put("H", lH);
				l.put("I", lI);
				l.put("J", lJ);
				l.put("L", lL);
				l.put("M", lM);
				l.put("N", lN);
				l.put("—", l—);
				l.put("O", lO);
				l.put("P", lP);
				l.put("Q", lQ);
				l.put("R", lR);
				l.put("S", lS);
				l.put("T", lT);
				l.put("U", lU);
				l.put("V", lV);
				l.put("X", lX);
				l.put("Y", lY);
				l.put("Z", lZ);
				
				for(String s:l.keySet()) {
					try(InputStream fos = new FileInputStream(".\\Preguntas\\"+s+".txt");
							Reader oos = new InputStreamReader(fos,"UTF-8");
							BufferedReader br = new BufferedReader(oos)){
						String linea=br.readLine();
						while(linea!=null) {
							String[] lineaTroceada=linea.split(":");
							if(lineaTroceada.length==3) {
								l.get(s).add(new Pregunta_Rosco(lineaTroceada[0], lineaTroceada[1],lineaTroceada[2], false));
							}
							linea=br.readLine();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return l;  
	}

	@Override
	public String toString() {
		return "Pregunta_Rosco [enunciado=" + enunciado + ", respuesta=" + respuesta + ", respondida=" + respondida
				+ ", empieza_contiene=" + empieza_contiene + "]";
	}

}
