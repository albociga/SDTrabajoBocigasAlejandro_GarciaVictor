package GUI;


import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.border.BevelBorder;

import java.awt.Rectangle;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;

public class Rosco extends JFrame {

	private JPanel contentPane;
	private JTextField txtRespuesta;
	private List<JLabel> lRosco;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JLabel lblE;
	private JLabel lblF;
	private JLabel lblG;
	private JLabel lblH;
	private JLabel lblI;
	private JLabel lblJ;
	private JLabel lblL;
	private JLabel lblM;
	private JLabel lblN;
	private JLabel lbl—;
	private JLabel lblO;
	private JLabel lblP;
	private JLabel lblQ;
	private JLabel lblR;
	private JLabel lblS;
	private JLabel lblT;
	private JLabel lblU;
	private JLabel lblV;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblZ;
	private JButton btnResponder;
	private JPanel panelRosco;
	private BufferedWriter buffer;
	private boolean pasapalabra=false;
	private JTextPane txtPregunta;
	private int aciertos=0;
	private int fallos=0;
	private JLabel lblContFallos;
	private JLabel lblContAciertos;
	private JLabel lblTiempoSeg;
	private int tiempoRestante;
	private JButton btnJugar;
	private Timer timer;
	private JButton btnPasapalabra;
	public Rosco(BufferedWriter bw,int tiempo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		buffer=bw;
		tiempoRestante=tiempo;
		txtRespuesta = new JTextField();
		txtRespuesta.setVisible(false);
		txtRespuesta.setColumns(10);
		
		panelRosco = new JPanel();
		panelRosco.setBounds(new Rectangle(0, 0, 360, 300));//He ido probando valores hasta que se ha colocado bien el rosco
		panelRosco.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRosco.setLayout(null);
		JPanel panelTiempo = new JPanel();
		
		JPanel panelAciertos = new JPanel();
		
		JPanel panelFallos = new JPanel();
		
		JLabel lblFallos = new JLabel("Fallos:");
		panelFallos.add(lblFallos);
		
		lblContFallos = new JLabel(String.valueOf(fallos));
		panelFallos.add(lblContFallos);
		
		btnPasapalabra = new JButton("Pasapalabra");
		btnPasapalabra.setVisible(false);
		//AcciÛn al pulsar el boton pasapalabra, envia al servidor palabra clave para decir que pasa de turno
		btnPasapalabra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pasapalabra=true;
					buffer.write("PASAPALABRA\r\n");
					buffer.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnResponder = new JButton("Responder");
		btnResponder.setVisible(false);
		//AcciÛn al pulsar el boton responder, coge lo que hay en el cuadro de respuesta, cambia los caracteres con signo por los de sin signo y lo envÌa al servidor. Las respuestas no llevan
		// caracteres con signo salvo la Ò
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pasapalabra=false;
					String conSigno="·‡‰ÈËÎÌÏÔÛÚˆ˙˘u¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹";
					String sinSigno="aaaeeeiiiooouuuAAAEEEIIIOOOUUU";
					String respuesta=txtRespuesta.getText();
					for (int i = 0; i < conSigno.length(); i++) {
						respuesta = respuesta.replace(conSigno.charAt(i), sinSigno.charAt(i));
					}
					buffer.write(respuesta+"\r\n");
					buffer.flush();
					txtRespuesta.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		txtPregunta = new JTextPane();
		txtPregunta.setVisible(false);
		txtPregunta.setDisabledTextColor(Color.BLACK);
		txtPregunta.setEditable(false);
		txtPregunta.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtPregunta.setEnabled(false);
		
		btnJugar = new JButton("Jugar");
		//Boton para empezar el juego o reanudar la partida
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanelRespuesta();
				btnJugar.setVisible(false);
				timer= new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					//Aqui inicializamos el contador con el valor del tiempoRestante, que es pasado por parametro y enviado por el servidor
		            public void run() {
		            	lblTiempoSeg.setText(String.valueOf(tiempoRestante));
		                tiempoRestante--;
		                if (tiempoRestante <0) {
		                	lblTiempoSeg.setText(String.valueOf(0));
		                	try {
		                		txtRespuesta.setEditable(false);
		                		btnResponder.setVisible(false);
		                		pasapalabra=true;
								buffer.write("PASAPALABRA\r\n");
								buffer.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                }
		            }}, 0, 1000);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPregunta, GroupLayout.PREFERRED_SIZE, 547, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelRosco, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(30)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(panelFallos, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
										.addComponent(panelTiempo, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
										.addComponent(panelAciertos, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnJugar)
									.addGap(43))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
							.addGap(45)
							.addComponent(btnResponder)
							.addGap(44)
							.addComponent(btnPasapalabra)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelRosco, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(124)
							.addComponent(panelAciertos, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelFallos, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelTiempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnJugar)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPregunta, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnResponder)
						.addComponent(btnPasapalabra))
					.addContainerGap())
		);
		
		JLabel lblAciertos = new JLabel("Aciertos:");
		panelAciertos.add(lblAciertos);
		
		lblContAciertos = new JLabel(String.valueOf(aciertos));
		panelAciertos.add(lblContAciertos);
		
		JLabel lblTiempoTxt = new JLabel("Tiempo:");
		panelTiempo.add(lblTiempoTxt);
		
		lblTiempoSeg = new JLabel("");
		panelTiempo.add(lblTiempoSeg);
		GroupLayout gl_panelRosco = new GroupLayout(panelRosco);
		gl_panelRosco.setHorizontalGroup(
			gl_panelRosco.createParallelGroup(Alignment.LEADING)
				.addGap(0, 379, Short.MAX_VALUE)
		);
		gl_panelRosco.setVerticalGroup(
			gl_panelRosco.createParallelGroup(Alignment.LEADING)
				.addGap(0, 305, Short.MAX_VALUE)
		);
		panelRosco.setLayout(gl_panelRosco);
		contentPane.setLayout(gl_contentPane);
		//Creo tantos labels como letras
		lRosco=new ArrayList<JLabel>();
		lblA=new JLabel();
		lblA.setText("A");
		lRosco.add(lblA);
		lblB=new JLabel();
		lblB.setText("B");
		lRosco.add(lblB);
		lblC=new JLabel();
		lblC.setText("C");
		lRosco.add(lblC);
		lblD=new JLabel();
		lblD.setText("D");
		lRosco.add(lblD);
		lblE=new JLabel();
		lblE.setText("E");
		lRosco.add(lblE);
		lblF=new JLabel();
		lblF.setText("F");
		lRosco.add(lblF);
		lblG=new JLabel();
		lblG.setText("G");
		lRosco.add(lblG);
		lblH=new JLabel();
		lblH.setText("H");
		lRosco.add(lblH);
		lblI=new JLabel();
		lblI.setText("I");
		lRosco.add(lblI);
		lblJ=new JLabel();
		lblJ.setText("J");
		lRosco.add(lblJ);
		lblL=new JLabel();
		lblL.setText("L");
		lRosco.add(lblL);
		lblM=new JLabel();
		lblM.setText("M");
		lRosco.add(lblM);
		lblN=new JLabel();
		lblN.setText("N");
		lRosco.add(lblN);
		lbl—=new JLabel();
		lbl—.setText("—");
		lRosco.add(lbl—);
		lblO=new JLabel();
		lblO.setText("O");
		lRosco.add(lblO);
		lblP=new JLabel();
		lblP.setText("P");
		lRosco.add(lblP);
		lblQ=new JLabel();
		lblQ.setText("Q");
		lRosco.add(lblQ);
		lblR=new JLabel();
		lblR.setText("R");
		lRosco.add(lblR);
		lblS=new JLabel();
		lblS.setText("S");
		lRosco.add(lblS);
		lblT=new JLabel();
		lblT.setText("T");
		lRosco.add(lblT);
		lblU=new JLabel();
		lblU.setText("U");
		lRosco.add(lblU);
		lblV=new JLabel();
		lblV.setText("V");
		lRosco.add(lblV);
		lblX=new JLabel();
		lblX.setText("X");
		lRosco.add(lblX);
		lblY=new JLabel();
		lblY.setText("Y");
		lRosco.add(lblY);
		lblZ=new JLabel();
		lblZ.setText("Z");
		lRosco.add(lblZ);
		//Sirve para asignar un icono correspondiente a cada letra y luego aÒadir el label al panel
		for(int i=0;i<lRosco.size();i++) {
			ImageIcon imagen = new ImageIcon(".\\LetrasPasapalabra\\Azul\\"+lRosco.get(i).getText()+".png");
			Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_DEFAULT));
			lRosco.get(i).setIcon(icono);
			lRosco.get(i).setSize(45,45);
			panelRosco.add(lRosco.get(i));
		}
		generarRosco();
	}
	//Esta funciÛn sirve para posicionar los iconos en forma circular
	
	public void generarRosco() {
		//Creamos un centro del plano
		double pMX = panelRosco.getSize().getWidth() / 2;
		double pMY = panelRosco.getSize().getHeight() / 2;
		double radio = 120 ;//Cogemos un radio
		for (int i = 0; i < lRosco.size(); i++) {
			double grados = (360 / 24.99) * i; //No dividimos entre 25 debido a que el icono de la letra z queda un poco mas abierto
			double radianes = (grados * Math.PI) / 180;//Pasamos de grados a radianes
			double x = (pMX + radio * Math.sin(radianes));//Damos valor a la x e y para mas tarde colocar el icono
			double y = (pMY - radio * Math.cos(radianes));
			lRosco.get(i).setBounds((int) (x - 45 / 2), (int) (y - 45 / 2), 45, 45); //Asignamos estos valores debido al tamaÒo que le hemos asignado al icono para que se posicione correctamente
			lRosco.get(i).setVisible(true);
		}
	}
	public JTextField getCampoRespuesta() {
		return this.txtRespuesta;
	}
	public JTextPane getCampoPregunta() {
		return this.txtPregunta;
	}
	public boolean getPasapalabra() {
		return this.pasapalabra;
	}
	public void actualizarRosco(String s,int i) {
		//Actualiza el rosco cada vez que se responde. Si se ha acertado, cambia el icono azul por el verde, sino lo cambia por el rojo
		if(s.equalsIgnoreCase("ACERTADA")) {
			ImageIcon imagen = new ImageIcon(".\\LetrasPasapalabra\\Verde\\"+lRosco.get(i).getText()+".png");
			Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_DEFAULT));
			lRosco.get(i).setIcon(icono);
			aciertos++;
			lblContAciertos.setText(String.valueOf(aciertos));
		}
		else {
			ImageIcon imagen = new ImageIcon(".\\LetrasPasapalabra\\Rojo\\"+lRosco.get(i).getText()+".png");
			Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(45,45, Image.SCALE_DEFAULT));
			lRosco.get(i).setIcon(icono);
			fallos++;
			lblContFallos.setText(String.valueOf(fallos));
		}
		generarRosco();
	}
	public int getTiempoRestante() {
		return this.tiempoRestante;
	}
	public void setTiempoRestante(int t) {
		this.tiempoRestante=t;
	}
	//He creado esta funcion para que una vez respondida no se acierte estando en modo multijugador, se oculte
	public void ocultarPanelRespuesta() {
		txtPregunta.setVisible(false);
		txtRespuesta.setVisible(false);
		btnPasapalabra.setVisible(false);
		btnResponder.setVisible(false);
	}
	//En caso de que se haya acertado esta pregunta siempre mostrara el panel
	public void mostrarPanelRespuesta(){
		txtPregunta.setVisible(true);
		txtRespuesta.setVisible(true);
		btnPasapalabra.setVisible(true);
		btnResponder.setVisible(true);
	}
	//Cada vez que se cambia de turno se para el timer
	public void pausarReloj() {
		timer.cancel();
		btnJugar.setVisible(true);
	}
}
