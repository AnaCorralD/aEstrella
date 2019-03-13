package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import controlador.Controlador;
import negocio.Casillas;
import negocio.Coordenadas;
import negocio.TAlgoritmo;
import javax.swing.JOptionPane;

public class GUITableroImp extends GUITablero implements MouseListener, GUI{
	
	private GUIMatriz guiMatriz;
	private int anchura;
	private int altura;
	private boolean save;
	private boolean modoAltu;
		
	public GUITableroImp() {
		if(this.guiMatriz != null) this.remove(guiMatriz);
		this.modoAltu = false;
		this.setTitle("Algoritmo A*");
		this.altura = 5;
		this.anchura = 5;
		this.save = false;
		this.guiMatriz = new GUIMatriz(this.altura, this.anchura);
		this.setLayout(new BorderLayout());
		this.add(guiMatriz, BorderLayout.CENTER);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.guiMatriz.inicializarEscuchadores(this);
		Border bordejpanel = new SoftBevelBorder(BevelBorder.LOWERED);
		JPanel panel = new JPanel(new GridLayout(2, 3));
		this.setMinimumSize(new Dimension(800,800));
		JButton buscar = new JButton("Empezar!");
		buscar.setBorder(bordejpanel);
		buscar.setFont(new Font("Magneto", Font.BOLD, 30));
		buscar.setBackground(Color.GREEN);
		buscar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				modoAltu = false;
				if(guiMatriz.getTieneInicio() && guiMatriz.getTieneMeta()){
					guiMatriz.getSavepoint().add(guiMatriz.getCorFinal());
					TAlgoritmo tAlgoritmo = new TAlgoritmo(guiMatriz.getCorInicio(), guiMatriz.getCorFinal(), guiMatriz.getMatriz(), guiMatriz.getSavepoint(), modoAltu);
					Controlador.getInstance().accion(new Contexto(Events.BUSCAR_CAMINO, tAlgoritmo));
				}
				else {
					JLabel label = new JLabel("Introduce en el tablero el inicio y el fin");
					label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label, "Cuidado!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		JButton limpiar = new JButton("Reiniciar");
		limpiar.setBorder(bordejpanel);
		limpiar.setFont(new Font("Magneto", Font.BOLD, 30));
		limpiar.setBackground(Color.ORANGE);
		limpiar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				guiMatriz.limpiarTablero();
			}
		});
		JPanel ptam = new JPanel(new GridLayout(1, 2));
		ptam.setBorder(bordejpanel);
		JPanel p1 = new JPanel(new GridLayout(2, 2));
		p1.setBackground(Color.YELLOW);
		
		JLabel alturaT =  new JLabel("Altura");
		alturaT.setFont(new Font("Magneto", Font.BOLD, 33));
		JTextField textoAltura = new JTextField();
		textoAltura.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isDigit(c)) e.consume();
			}
		});
		textoAltura.setFont(new Font("Magneto", Font.BOLD, 25));
		textoAltura.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextField textoAnchura = new JTextField();
		textoAnchura.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isDigit(c)) e.consume();
			}
		});
		textoAnchura.setFont(new Font("Magneto", Font.BOLD, 25));
		textoAnchura.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel anchuraT = new JLabel("Anchura");
		anchuraT.setFont(new Font("Magneto", Font.BOLD, 30));
		
		p1.add(alturaT);
		p1.add(textoAltura);
		p1.add(anchuraT);
		p1.add(textoAnchura);
		ptam.add(p1);
		JButton tamano = new JButton("Cambiar");
		tamano.setFont(new Font("Magneto", Font.BOLD, 30));
		tamano.setBackground(Color.YELLOW);
		tamano.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int altu , anchu;
				altu = Integer.parseInt(textoAltura.getText());
				anchu = Integer.parseInt(textoAnchura.getText());
				if (altu > 0 && anchu > 0) {
					altura = altu;
					anchura = anchu;
					if(guiMatriz != null) remove(guiMatriz);
					guiMatriz = new GUIMatriz(altura, anchura);
					add(guiMatriz, BorderLayout.CENTER);
					guiMatriz.inicializarEscuchadores(getThis());
					guiMatriz.updateUI();
				}
				else {
					JLabel label = new JLabel("La altura y anchura tiene que ser mayor que cero");
					label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label, "Cuidado!", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		ptam.add(tamano);
		JButton salir = new JButton("Salir");
		salir.setBorder(bordejpanel);
		salir.setFont(new Font("Magneto", Font.BOLD, 30));
		salir.setBackground(Color.ORANGE);
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JPanel savePointyAltura = new JPanel(new GridLayout(1, 2));
		
		JButton savepoint = new JButton("Poner Savepoint");
		savepoint.setBorder(bordejpanel);
		savepoint.setFont(new Font("Magneto", Font.BOLD, 30));
		savepoint.setBackground(Color.ORANGE);
		savepoint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!save) {
					if(!guiMatriz.getSavepoint().isEmpty()) guiMatriz.setSavepoint(new ArrayList<>());
					JLabel label = new JLabel("<html><body>El orden que ponga los savepoint, sera en el que se recorra el camino.<br>"
							+ "-No se pueden borrar- <body></html>");
					label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label, "Cuidado!", JOptionPane.INFORMATION_MESSAGE);
					save = true;
					JLabel label1 = new JLabel("Se ha activado el modo savepoint");
					label1.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label1, "Información", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else {
					save = false;
					JLabel label1 = new JLabel("Se ha desactivado el modo savepoint");
					label1.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label1, "Información", JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				guiMatriz.updateUI();
				
			}
		});
		
		JButton info = new JButton("Informacion");
		info.setBorder(bordejpanel);
		info.setFont(new Font("Magneto", Font.BOLD, 30));
		info.setBackground(Color.ORANGE);
		info.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("<html><body>Cambiar: boton para cambiar la altura y anchura <br>"
													+ "Empezar!: se calcula el camino más optimo <br>"
													+ "Poner Savepoint: se agregan savepoint a nuestro algoritmo <br>"
													+ "Poner Alturas: se ponen diferentes alturas a las casillas del tablero <br>"
													+ "Reinciar: limpia la pantalla y para poder ejecutar otro camino <br>"
													+ "Salir: cerrar del programa<body></html>");
				label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
				JOptionPane.showMessageDialog(null, label, "CUIDADO!", JOptionPane.INFORMATION_MESSAGE);
				guiMatriz.updateUI();
				
			}
		});
		savePointyAltura.add(info);
		savePointyAltura.add(savepoint);
		
		
		
		JPanel alturas = new JPanel(new GridLayout(1, 2));
		alturas.setBorder(bordejpanel);
		JPanel p2 = new JPanel(new GridLayout(2, 2));
		p2.setBackground(Color.YELLOW);
		JLabel modoAltura =  new JLabel("Indicar Altura Maxima");
		modoAltura.setFont(new Font("Magneto", Font.BOLD, 24));
		
		JTextField texto = new JTextField();
		texto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isDigit(c)) e.consume();
			}
		});
		texto.setHorizontalAlignment(SwingConstants.CENTER);
		texto.setFont(new Font("Magneto", Font.BOLD, 25));
		
		p2.add(modoAltura);
		p2.add(texto);
		
		JButton botonAlturas = new JButton("Modo Alturas");
		botonAlturas.setFont(new Font("Magneto", Font.BOLD, 30));
		botonAlturas.setBackground(Color.YELLOW);
		botonAlturas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double altu;
				altu = Double.parseDouble(texto.getText());
				if (altu > 0) {
					modoAltu = true;
					guiMatriz.ponerAlturas (altu);
					if(guiMatriz.getTieneInicio() && guiMatriz.getTieneMeta()){
						guiMatriz.getSavepoint().add(guiMatriz.getCorFinal());
						TAlgoritmo tAlgoritmo = new TAlgoritmo(guiMatriz.getCorInicio(), guiMatriz.getCorFinal(), guiMatriz.getMatriz(), guiMatriz.getSavepoint(), modoAltu,altu);
						Controlador.getInstance().accion(new Contexto(Events.BUSCAR_CAMINO, tAlgoritmo));
					}
					else {
						JLabel label = new JLabel("Introduce en el tablero el inicio y el fin");
						label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
						JOptionPane.showMessageDialog(null, label, "Cuidado!", JOptionPane.INFORMATION_MESSAGE);
					}
					guiMatriz.updateUI();
				}
				else {
					JLabel label = new JLabel("Introduce una altura mayor que 0");
					label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
					JOptionPane.showMessageDialog(null, label, "Cuidado!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		alturas.add(p2);
		alturas.add(botonAlturas);
		
		panel.add(ptam);
		panel.add(buscar);
		panel.add(limpiar);
		panel.add(savePointyAltura);
		panel.add(alturas);
		panel.add(salir);
		panel.setPreferredSize(new Dimension(100,100));
		this.add(panel, BorderLayout.NORTH);
		this.setVisible(true);	}

	@Override
	public void actualizar(Contexto contexto) {
		switch(contexto.getEvento()) {
		case(Events.GUI_TABLERO):
			this.setVisible(false);
			this.actualizar(contexto);
		break;
		case(Events.GUI_MAIN):
			this.setVisible(true);
		break;
		case(Events.BUSCAR_KO):
			JLabel label = new JLabel("No hay camino posible");
			label.setFont(new Font("Magneto", Font.BOLD, 30));
			JOptionPane.showMessageDialog(null, label, "ALERTA!", JOptionPane.WARNING_MESSAGE);
			
		break;
		case(Events.BUSCAR_OK):
			this.pintarCamino(contexto);
		break;
		}
	}
	
	public void pintarCamino(Contexto contexto) {
		ArrayList<Coordenadas> caminoMinimo = (ArrayList<Coordenadas>) contexto.getDato();
		for(int i = 0; i < caminoMinimo.size(); ++i) {
			this.guiMatriz.pintarCeldaCamino(caminoMinimo.get(i).getX(), caminoMinimo.get(i).getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Celda celda = (Celda) e.getSource();
		if (save) {
			if (celda.getTipo() == Casillas.LIBRE) {
				this.guiMatriz.ponerSavePoint(celda.getFila(), celda.getColumna());
			}
			else {
				JLabel label = new JLabel("Ya hay un savepoint en esa casilla");
				label.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
				JOptionPane.showMessageDialog(null, label, "CUIDADO!", JOptionPane.INFORMATION_MESSAGE);
			}
				
		}
		
		else if(celda.getTipo() == Casillas.LIBRE) {
			if(!this.guiMatriz.getTieneInicio()) {
				this.guiMatriz.pintarInicio(celda.getFila(), celda.getColumna());
			}
			else if(!this.guiMatriz.getTieneMeta()) {
				this.guiMatriz.pintarFinal(celda.getFila(), celda.getColumna());
			}
			else this.guiMatriz.pintarObstaculo(celda.getFila(), celda.getColumna());
		}
		else if(celda.getTipo() == Casillas.INICIO) {
			this.guiMatriz.setTieneInicio(false);
			if(!this.guiMatriz.getTieneMeta()) {
				this.guiMatriz.pintarFinal(celda.getFila(), celda.getColumna());
			}
			else this.guiMatriz.pintarObstaculo(celda.getFila(), celda.getColumna());
		}
		else if(celda.getTipo() == Casillas.FINAL) {
			this.guiMatriz.setTieneMeta(false);
			this.guiMatriz.pintarObstaculo(celda.getFila(), celda.getColumna());
		}
		else if(celda.getTipo() == Casillas.BLOQUEADO) {
			this.guiMatriz.pintarPenalizacion(celda.getFila(), celda.getColumna());
		}
		else if(celda.getTipo() == Casillas.PENALIZACION) {
			this.guiMatriz.pintarNormal(celda.getFila(), celda.getColumna());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public GUITableroImp getThis() {
		return this;
	}
}
