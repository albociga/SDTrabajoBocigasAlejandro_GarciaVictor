package pasapalabra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pregunta_Rosco {
	private String enunciado; //Enunciado de la pregunta
	private String respuesta; //Respuesta correcta a la pregunta
	private boolean respondida; //Si la pregunta ha sido respondida o no, al inicio todas ser�n falsas
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
	//SI SE A�ADEN NUEVAS PREGUNTAS, CUIDADO CON VARIABLE 'n' DE LA LINEA 366, HAY QUE CAMBIARLA
	public static Map<String,List<Pregunta_Rosco>> crea_hash_map_preguntas() {
		// A
				Pregunta_Rosco pr1A = new Pregunta_Rosco("Objeto peque�o que se lleva encima, al que se atribuye la virtud de alejar el mal o propiciar el bien", "Amuleto", "Empieza", false);
				Pregunta_Rosco pr2A = new Pregunta_Rosco("Persona que colecciona o negocia con antig�edades", "Anticuario", "Empieza", false);
				Pregunta_Rosco pr3A = new Pregunta_Rosco("L�quido graso de color verde amarillento que se obtiene prensando las aceitunas", "Aceite", "Empieza", false);
//				Pregunta_Rosco pr4A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9A = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10A = new Pregunta_Rosco("", "", "Empieza", false);

				// B
				Pregunta_Rosco pr1B = new Pregunta_Rosco("Juego que se practica sobre una mesa rectangular forrada de pa�o y que consiste en impulsar bolas con un taco", "Billar", "Empieza", false);
				Pregunta_Rosco pr2B = new Pregunta_Rosco("Utensilio para la lactancia artificial que consiste en una botella peque�a con una tetina de goma", "Biber�n", "Empieza", false);
				Pregunta_Rosco pr3B = new Pregunta_Rosco("Abertura por donde se echan las cartas por el correo", "Buz�n", "Empieza", false);
//				Pregunta_Rosco pr4B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9B = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10B = new Pregunta_Rosco("", "", "Empieza", false);

				// C
				Pregunta_Rosco pr1C = new Pregunta_Rosco("Persona que manda un barco", "Capitan", "Empieza", false);
				Pregunta_Rosco pr2C = new Pregunta_Rosco("Jefe de la mafia, especialmente de narcotraficantes", "Capo", "Empieza", false);
				Pregunta_Rosco pr3C = new Pregunta_Rosco("Ropa deportiva que consta de un pantal�n y una chaqueta o jersey amplios", "Ch�ndal", "Empieza", false);
//				Pregunta_Rosco pr4C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9C = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10C = new Pregunta_Rosco("", "", "Empieza", false);

				// D
				Pregunta_Rosco pr1D = new Pregunta_Rosco("Rama de la pol�tica que se ocupa del estudio de las relaciones internacionales", "Diplomacia", "Empieza", false);
				Pregunta_Rosco pr2D = new Pregunta_Rosco("Producto que se utiliza para suprimir el olor corporal o de alg�n recinto", "Desodorante", "Empieza", false);
				Pregunta_Rosco pr3D = new Pregunta_Rosco("Dedicado con fervor a obras de piedad y religi�n", "Devoto", "Empieza", false);
//				Pregunta_Rosco pr4D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9D = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10D = new Pregunta_Rosco("", "", "Empieza", false);

				// E
				Pregunta_Rosco pr1E = new Pregunta_Rosco("T�tulo del �lbum publicado en 2009 por el m�sico Javier Malosetti, en el cual se incluye la canci�n 'Ginger Tea'", "Electrohope", "Empieza", false);
				Pregunta_Rosco pr2E = new Pregunta_Rosco("Pasajero, de corta duraci�n", "Ef�mero", "Empieza", false);
				Pregunta_Rosco pr3E = new Pregunta_Rosco("Se dice de la leche que conserva toda la grasa y sustancias nutritivas", "Entera", "Empieza", false);
//				Pregunta_Rosco pr4E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9E = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr1OE = new Pregunta_Rosco("", "", "Empieza", false);

				// F
				Pregunta_Rosco pr1F = new Pregunta_Rosco("Breve relato ficticio con intenci�n did�ctica o cr�tica frecuentemente manifestada en una moraleja final", "F�bula", "Empieza", false);
				Pregunta_Rosco pr2F = new Pregunta_Rosco("Prenda interior el�stica que ci�e la cintura, o la cintura y las caderas", "Faja", "Empieza", false);
				Pregunta_Rosco pr3F = new Pregunta_Rosco("Perteneciente o relativo a los bosques y a los aprovechamientos de le�as o pastos", "Forestal", "Empieza", false);
//				Pregunta_Rosco pr4F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9F = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10F = new Pregunta_Rosco("", "", "Empieza", false);

				// G
				Pregunta_Rosco pr1G = new Pregunta_Rosco("Queso blando y de sabor intenso, de origen italiano, elaborado con leche de vaca", "Gorgonzola", "Empieza", false);
				Pregunta_Rosco pr2G = new Pregunta_Rosco("Golosina blanca masticable, generalmente recubierta de az�car", "Gominola", "Empieza", false);
				Pregunta_Rosco pr3G = new Pregunta_Rosco("Coloquialmente canto f�nebre con el que se acompa�an los entierros", "Gorigori", "Empieza", false);
//				Pregunta_Rosco pr4G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9G = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10G = new Pregunta_Rosco("", "", "Empieza", false);

				// H
				Pregunta_Rosco pr1H = new Pregunta_Rosco("Perteneciente o relativo a la higiene", "Higienico", "Empieza", false);
				Pregunta_Rosco pr2H = new Pregunta_Rosco("Conjunto de todos los hechos ocurridos en tiempos pasados ", "Historia", "Empieza", false);
				Pregunta_Rosco pr3H = new Pregunta_Rosco("Adorno especial de los vestidos en la parte correspondiente a los hombros", "Hombrera", "Empieza", false);
				Pregunta_Rosco pr4H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9H = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10H = new Pregunta_Rosco("", "", "Empieza", false);

				// I
				Pregunta_Rosco pr1I = new Pregunta_Rosco("Principio que reconoce la equiparaci�n de todos los ciudadanos en derechos y obligaciones", "Igualdad", "Empieza", false);
				Pregunta_Rosco pr2I = new Pregunta_Rosco("Apellido del pol�tico que fue presidente de Estonia entre los a�os 2006 y 2016", "Ilves", "Empieza", false);
				Pregunta_Rosco pr3I = new Pregunta_Rosco("Percibir �ntima e instant�neamente una idea o verdad tal como si se la tuviera a la vista", "Intuir", "Empieza", false);
//				Pregunta_Rosco pr4I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9I = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10I = new Pregunta_Rosco("", "", "Empieza", false);

				// J
				Pregunta_Rosco pr1J = new Pregunta_Rosco("Pa�s asi�tico con capital en Tokio", "Jap�n", "Empieza", false);
				Pregunta_Rosco pr2J = new Pregunta_Rosco("Variedad de jud�a de vainas anchas y semilla grande", "Judi�n", "Empieza", false);
				Pregunta_Rosco pr3J = new Pregunta_Rosco("Tiempo de duraci�n del trabajo diario", "Jornada", "Empieza", false);
//				Pregunta_Rosco pr4J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9J = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10J = new Pregunta_Rosco("", "", "Empieza", false);

				// L
				Pregunta_Rosco pr1L = new Pregunta_Rosco("Apellido del poeta ruso autor de la obra La muerte del poeta de 1837", "L�rmontov", "Empieza", false);
				Pregunta_Rosco pr2L = new Pregunta_Rosco("Ascendencia o descendencia de una familia, especialmente noble", "Linaje", "Empieza", false);
				Pregunta_Rosco pr3L = new Pregunta_Rosco("Se dice de la persona de aspecto saludable", "Lozano", "Empieza", false);
//				Pregunta_Rosco pr4L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9L = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10L = new Pregunta_Rosco("", "", "Empieza", false);

				// M
				Pregunta_Rosco pr1M = new Pregunta_Rosco("Se dice de una persona que no ha alcanzado la mayor�a de edad", "Menor", "Empieza", false);
				Pregunta_Rosco pr2M = new Pregunta_Rosco("Cueva en la que habitan ciertos animales, especialmente los conejos", "Madriguera", "Empieza", false);
				Pregunta_Rosco pr3M = new Pregunta_Rosco("Barniz que usan los alfareros", "Mogate", "Empieza", false);
//				Pregunta_Rosco pr4M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9M = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10M = new Pregunta_Rosco("", "", "Empieza", false);

				// N
				Pregunta_Rosco pr1N = new Pregunta_Rosco("Cada una de las fabulosas deidades de las aguas, bosques o selva", "Ninfa", "Empieza", false);
				Pregunta_Rosco pr2N = new Pregunta_Rosco("Espinazo de los vertebrados", "Navato", "Empieza", false);
				Pregunta_Rosco pr3N = new Pregunta_Rosco("Decir que no a lo que se pretende o se pide, o no concederlo", "Negar", "Empieza", false);
//				Pregunta_Rosco pr4N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9N = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10N = new Pregunta_Rosco("", "", "Empieza", false);

				// �
				Pregunta_Rosco pr1� = new Pregunta_Rosco("Acci�n, espect�culo u obra que exagera ciertos rasgos que se consideran espa�oles", "Espa�olada", "Contiene", false);
				Pregunta_Rosco pr2� = new Pregunta_Rosco("Hacer o fabricar moneda", "Acu�ar", "Contiene", false);
				Pregunta_Rosco pr3� = new Pregunta_Rosco("Mentira o noticia fabulosa de pura invenci�n", "Patra�a.", "Contiene", false);
//				Pregunta_Rosco pr4� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9� = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10� = new Pregunta_Rosco("", "", "Empieza", false);

				// O
				Pregunta_Rosco pr1O = new Pregunta_Rosco("Idea fija y persistente que ocupa el pensamiento", "Obsesi�n", "Empieza", false);
				Pregunta_Rosco pr2O = new Pregunta_Rosco("Perteneciente o relativo a los sue�os", "On�rico", "Empieza", false);
				Pregunta_Rosco pr3O = new Pregunta_Rosco("Empleado que en ciertas oficinas desempe�a funciones subalternas", "Ordenanza", "Empieza", false);
//				Pregunta_Rosco pr4O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9O = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10O = new Pregunta_Rosco("", "", "Empieza", false);

				// P
				Pregunta_Rosco pr1P = new Pregunta_Rosco("Nombre del personaje de animaci�n que es un perro de color anaranjado que suele acompa�ar a Mickey Mouse", "Pluto", "Empieza", false);
				Pregunta_Rosco pr2P = new Pregunta_Rosco("Instrumento con forma de pico que utilizan los alpinistas para asegurar sus movimientos sobre la nieve o el hielo", "Piolet", "Empieza", false);
				Pregunta_Rosco pr3P = new Pregunta_Rosco("Anterioridad de algo respecto de otra cosa en tiempo u orden", "Prioridad", "Empieza", false);
//				Pregunta_Rosco pr4P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9P = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10P = new Pregunta_Rosco("", "", "Empieza", false);

				// Q
				Pregunta_Rosco pr1Q = new Pregunta_Rosco("�rgano respiratorio de los peces formado por l�minas o filamentos", "Branquia", "Contiene", false);
				Pregunta_Rosco pr2Q = new Pregunta_Rosco("Tri�ngulo que tiene todos sus lados iguales", "Equil�tero", "Contiene", false);
				Pregunta_Rosco pr3Q = new Pregunta_Rosco("Anilla que se abre y cierra mediante un muelle", "Mosquet�n", "Contiene", false);
//				Pregunta_Rosco pr4Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9Q = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr1OQ = new Pregunta_Rosco("", "", "Empieza", false);

				// R
				Pregunta_Rosco pr1R = new Pregunta_Rosco("Apellido del ingeniero franc�s que, junto a Arthur C. Krebs, construy� el dirigible militar La France en 1884", "Renard", "Empieza", false);
				Pregunta_Rosco pr2R = new Pregunta_Rosco("Departamento de Colombia con capital en la ciudad de Pereira", "Risaralda", "Empieza", false);
				Pregunta_Rosco pr3R = new Pregunta_Rosco("Carnero, macho de la oveja, con cuernos divergentes y arrollados en espiral", "Ramiro", "Empieza", false);
//				Pregunta_Rosco pr4R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9R = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10R = new Pregunta_Rosco("", "", "Empieza", false);

				// S
				Pregunta_Rosco pr1S = new Pregunta_Rosco("Danza popular brasile�a de influencia africana cantada de comp�s binario", "Samba", "Empieza", false);
				Pregunta_Rosco pr2S = new Pregunta_Rosco("Poner o colocar a alguien en una silla o banco, de manera que quede apoyado y descansando sobre las nalgas", "Sentar", "Empieza", false);
				Pregunta_Rosco pr3S = new Pregunta_Rosco("Librar de un riesgo o peligro, poner en seguro", "Salvar", "Empieza", false);
//				Pregunta_Rosco pr4S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9S = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10S = new Pregunta_Rosco("", "", "Empieza", false);

				// T
				Pregunta_Rosco pr1T = new Pregunta_Rosco("Prenda de ba�o que por delante cubre la zona genital y por detr�s deja las nalgas al aire", "Tanga", "Empieza", false);
				Pregunta_Rosco pr2T = new Pregunta_Rosco("Tienda de piel de forma c�nica que utilizaban como vivienda los indios de las praderas de Am�rica del Norte", "Tipi", "Empieza", false);
				Pregunta_Rosco pr3T = new Pregunta_Rosco("M�quina de juegos de azar que funciona introduciendo monedas", "Tragaperras", "Empieza", false);
//				Pregunta_Rosco pr4T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9T = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr1OT = new Pregunta_Rosco("", "", "Empieza", false);

				// U
				Pregunta_Rosco pr1U = new Pregunta_Rosco("Art�culo indeterminado en masculino singular", "Un", "Empieza", false);
				Pregunta_Rosco pr2U = new Pregunta_Rosco("Que se refiere o se circunscribe solamente a una parte o a un aspecto de algo", "Unilateral", "Empieza", false);
				Pregunta_Rosco pr3U = new Pregunta_Rosco("Atribuirse y usar un t�tulo o cargo ajeno como si fuera propio", "Usurpar", "Empieza", false);
//				Pregunta_Rosco pr4U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9U = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10U = new Pregunta_Rosco("", "", "Empieza", false);

				// V
				Pregunta_Rosco pr1V = new Pregunta_Rosco("Apellido del ciclista que gan� el Gran Premio de la Monta�a del Tour de Francia en 1982", "Vallet", "Empieza", false);
				Pregunta_Rosco pr2V = new Pregunta_Rosco("Flor del cardo", "Vilano", "Empieza", false);
				Pregunta_Rosco pr3V = new Pregunta_Rosco("Lugar cerrado o cubierto construido para ser habitado por personas", "Vivienda", "Empieza", false);
//				Pregunta_Rosco pr4V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9V = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10V = new Pregunta_Rosco("", "", "Empieza", false);

				// X
				Pregunta_Rosco pr1X = new Pregunta_Rosco("Ciudad inglesa cuya universidad compite cada a�o en una popular regata contra la universidad de Cambridge", "Oxford", "Contiene", false);
				Pregunta_Rosco pr2X = new Pregunta_Rosco("En las rep�blicas de Venecia o G�nova, pr�ncipe o magistrado supremo", "Dux", "Contiene", false);
				Pregunta_Rosco pr3X = new Pregunta_Rosco("Conjunto de las palabras de una lengua, de una regi�n o de una materia", "L�xico", "Contiene", false);
//				Pregunta_Rosco pr4X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9X = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10X = new Pregunta_Rosco("", "", "Empieza", false);

				// Y
				Pregunta_Rosco pr1Y = new Pregunta_Rosco("Persona que aplica el derecho sin rigor y desenfadadamente", "Leguleyo", "Contiene", false);
				Pregunta_Rosco pr2Y = new Pregunta_Rosco("Preparar el montaje y ejecuci�n de un espect�culo antes de ofrecerlo al p�blico", "Ensayar", "Contiene", false);
				Pregunta_Rosco pr3Y = new Pregunta_Rosco("Fruto del papayo, de carne anaranjada y con muchas pepitas negras en el centro", "Papaya", "Contiene", false);
//				Pregunta_Rosco pr4Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9Y = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10Y = new Pregunta_Rosco("", "", "Empieza", false);

				// Z
				Pregunta_Rosco pr1Z = new Pregunta_Rosco("Movimiento repetido y violento de un lado a otro", "Zarandeo", "Empieza", false);
				Pregunta_Rosco pr2Z = new Pregunta_Rosco("Participio del verbo zumbar", "Zumbado", "Empieza", false);
				Pregunta_Rosco pr3Z = new Pregunta_Rosco("Plato consistente en varias clases de pescados y marisco condimentado con una salsa", "Zarzuela", "Empieza", false);
//				Pregunta_Rosco pr4Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr5Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr6Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr7Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr8Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr9Z = new Pregunta_Rosco("", "", "Empieza", false);
//				Pregunta_Rosco pr10Z = new Pregunta_Rosco("", "", "Empieza", false);
				
				int n= 3;
				
				Map<String,List<Pregunta_Rosco>> l = new HashMap<String,List<Pregunta_Rosco>>();
				
				List<Pregunta_Rosco> lA = new ArrayList<Pregunta_Rosco>();
				lA.add(pr1A);
				lA.add(pr2A);
				lA.add(pr3A);
				List<Pregunta_Rosco> lB = new ArrayList<Pregunta_Rosco>();
				lB.add(pr1B);
				lB.add(pr2B);
				lB.add(pr3B);
				List<Pregunta_Rosco> lC = new ArrayList<Pregunta_Rosco>();
				lC.add(pr1C);
				lC.add(pr2C);
				lC.add(pr3C);
				List<Pregunta_Rosco> lD = new ArrayList<Pregunta_Rosco>();
				lD.add(pr1D);
				lD.add(pr2D);
				lD.add(pr3D);
				List<Pregunta_Rosco> lE = new ArrayList<Pregunta_Rosco>();
				lE.add(pr1E);
				lE.add(pr2E);
				lE.add(pr3E);
				List<Pregunta_Rosco> lF = new ArrayList<Pregunta_Rosco>();
				lF.add(pr1F);
				lF.add(pr2F);
				lF.add(pr3F);
				List<Pregunta_Rosco> lG = new ArrayList<Pregunta_Rosco>();
				lG.add(pr1G);
				lG.add(pr2G);
				lG.add(pr3G);
				List<Pregunta_Rosco> lH = new ArrayList<Pregunta_Rosco>();
				lH.add(pr1H);
				lH.add(pr2H);
				lH.add(pr3H);
				List<Pregunta_Rosco> lI = new ArrayList<Pregunta_Rosco>();
				lI.add(pr1I);
				lI.add(pr2I);
				lI.add(pr3I);
				List<Pregunta_Rosco> lJ = new ArrayList<Pregunta_Rosco>();
				lJ.add(pr1J);
				lJ.add(pr2J);
				lJ.add(pr3J);
				List<Pregunta_Rosco> lL = new ArrayList<Pregunta_Rosco>();
				lL.add(pr1L);
				lL.add(pr2L);
				lL.add(pr3L);
				List<Pregunta_Rosco> lM = new ArrayList<Pregunta_Rosco>();
				lM.add(pr1M);
				lM.add(pr2M);
				lM.add(pr3M);
				List<Pregunta_Rosco> lN = new ArrayList<Pregunta_Rosco>();
				lN.add(pr1N);
				lN.add(pr2N);
				lN.add(pr3N);
				List<Pregunta_Rosco> l� = new ArrayList<Pregunta_Rosco>();
				l�.add(pr1�);
				l�.add(pr2�);
				l�.add(pr3�);
				List<Pregunta_Rosco> lO = new ArrayList<Pregunta_Rosco>();
				lO.add(pr1O);
				lO.add(pr2O);
				lO.add(pr3O);
				List<Pregunta_Rosco> lP = new ArrayList<Pregunta_Rosco>();
				lP.add(pr1P);
				lP.add(pr2P);
				lP.add(pr3P);
				List<Pregunta_Rosco> lQ = new ArrayList<Pregunta_Rosco>();
				lQ.add(pr1Q);
				lQ.add(pr2Q);
				lQ.add(pr3Q);
				List<Pregunta_Rosco> lR = new ArrayList<Pregunta_Rosco>();
				lR.add(pr1R);
				lR.add(pr2R);
				lR.add(pr3R);
				List<Pregunta_Rosco> lS = new ArrayList<Pregunta_Rosco>();
				lS.add(pr1S);
				lS.add(pr2S);
				lS.add(pr3S);
				List<Pregunta_Rosco> lT = new ArrayList<Pregunta_Rosco>();
				lT.add(pr1T);
				lT.add(pr2T);
				lT.add(pr3T);
				List<Pregunta_Rosco> lU = new ArrayList<Pregunta_Rosco>();
				lU.add(pr1U);
				lU.add(pr2U);
				lU.add(pr3U);
				List<Pregunta_Rosco> lV = new ArrayList<Pregunta_Rosco>();
				lV.add(pr1V);
				lV.add(pr2V);
				lV.add(pr3V);
				List<Pregunta_Rosco> lX = new ArrayList<Pregunta_Rosco>();
				lX.add(pr1X);
				lX.add(pr2X);
				lX.add(pr3X);
				List<Pregunta_Rosco> lY = new ArrayList<Pregunta_Rosco>();
				lY.add(pr1Y);
				lY.add(pr2Y);
				lY.add(pr3Y);
				List<Pregunta_Rosco> lZ = new ArrayList<Pregunta_Rosco>();
				lZ.add(pr1Z);
				lZ.add(pr2Z);
				lZ.add(pr3Z);
				
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
				l.put("�", l�);
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
				return l;  //A->LIST, B->LIST .....
	}

	@Override
	public String toString() {
		return "Pregunta_Rosco [enunciado=" + enunciado + ", respuesta=" + respuesta + ", respondida=" + respondida
				+ ", empieza_contiene=" + empieza_contiene + "]";
	}

}
