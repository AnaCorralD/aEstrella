package presentacion;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import negocio.Casillas;
import negocio.Coordenadas;


public class GUIMatriz extends JPanel{

	private Celda[][] panel;
	private int altura;
	private int anchura;
	private ImageIcon libre, inicio, fin, bloqueado, camino, pirata, penalizacion, point;
	private boolean tieneInicio;
	private boolean tieneMeta;
	private double penalizacionMaxima;
	private double penalizacionMinima;
	private Coordenadas corInicio;
	private Coordenadas corFinal;
	private ArrayList<Coordenadas> savepoint;
	private double altCasillas;
	
	public GUIMatriz(int altura, int anchura){
		this.tieneInicio = false;
		this.tieneMeta = false;
		this.anchura = anchura;
		this.altura = altura;
		this.libre = new ImageIcon(getClass().getResource("/imagenes/agua.gif"));
		this.inicio = new ImageIcon(getClass().getResource("/imagenes/barco.gif"));
		Image m = this.inicio.getImage();
		this.inicio = new ImageIcon(m.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		this.fin = new ImageIcon(getClass().getResource("/imagenes/tesoro.gif"));
		Image f = this.fin.getImage();
		this.fin = new ImageIcon(f.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		this.bloqueado = new ImageIcon(getClass().getResource("/imagenes/piedra.png"));
		Image b = bloqueado.getImage();
		this.bloqueado = new ImageIcon(b.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		this.camino = new ImageIcon(getClass().getResource("/imagenes/arena.jpg"));
		this.pirata = new ImageIcon(getClass().getResource("/imagenes/camino.gif"));
		Image c = pirata.getImage();
		this.pirata = new ImageIcon(c.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		this.penalizacion = new ImageIcon(getClass().getResource("/imagenes/penalizacion.gif"));
		Image p = penalizacion.getImage();
		this.penalizacion = new ImageIcon(p.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		this.penalizacionMaxima = (Math.sqrt(Math.pow(altura, 2) + Math.pow(anchura, 2)))*0.80;
		this.penalizacionMinima = this.penalizacionMaxima / 2;
		
		this.point = new ImageIcon(getClass().getResource("/imagenes/mapa.png"));
		Image po = this.point.getImage();
		this.point = new ImageIcon(po.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		this.savepoint = new ArrayList();
		this.corInicio = null;
		this.corFinal = null;
		inicializarPanel();
	}

	private void inicializarPanel() {
		this.setName("Tablero");
		panel= new Celda[altura][anchura];
		this.setLayout(new GridLayout(altura, anchura)); //ordenacion de celdas alto x ancho
		//INICIALIZACION DE LA TABLA.
		for( int x=0; x< altura;x++){
			for (int y=0; y< anchura;y++){
				this.panel[x][y] = new Celda();
				this.add(panel[x][y]);
				this.panel[x][y].setFoto(this.libre);
				this.panel[x][y].setTipo(Casillas.LIBRE);
				this.panel[x][y].setToolTipText(Integer.toString(x + 1) + " , " + Integer.toString(y + 1));
				this.panel[x][y].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
			}
		}
	}
	
	//Inicializa los escuchadores de los botones de la matriz
	public void inicializarEscuchadores(MouseListener e){
		for(int x=0;x<altura;x++){
			for(int y=0; y<anchura;y++){
				this.panel[x][y].addMouseListener(e);;
				this.panel[x][y].setName("Map");
				this.panel[x][y].setFila(x);
				this.panel[x][y].setColumna(y);
			}
		}
	}
	
	public void pintarCeldaCamino(int x, int y){		
		if(this.panel[x][y].getTipo() == Casillas.LIBRE) {
			this.panel[x][y].addFoto(camino, pirata);
		}
		else if(this.panel[x][y].getTipo() == Casillas.INICIO) {
			this.panel[x][y].addFoto(camino, inicio);
		}
		else if(this.panel[x][y].getTipo() == Casillas.FINAL) {
			this.panel[x][y].addFoto(camino, fin);
		}
		else if(this.panel[x][y].getTipo() == Casillas.PENALIZACION) {
			this.panel[x][y].addFoto(camino, penalizacion);
		}
		else if(this.panel[x][y].getTipo() == Casillas.SAVEPOINT) {
			this.panel[x][y].addFoto(camino, point);
		}
		
	}
	
	public ArrayList<Coordenadas> getSavepoint() {
		return savepoint;
	}

	public void setSavepoint(ArrayList<Coordenadas> savepoint) {
		this.savepoint = savepoint;
	}

	public void pintarNormal(int x, int y){		
		this.panel[x][y].setFoto(this.libre);
		this.panel[x][y].setTipo(Casillas.LIBRE);
	}
	
	public void pintarObstaculo(int x, int y){		
		this.panel[x][y].addFoto(this.libre, this.bloqueado);
		this.panel[x][y].setTipo(Casillas.BLOQUEADO);
	}
	
	public void pintarPenalizacion(int x, int y){		
		this.panel[x][y].addFoto(this.libre, this.penalizacion);
		this.panel[x][y].setTipo(Casillas.PENALIZACION);
		this.panel[x][y].crearPenalizacion(penalizacionMinima, penalizacionMaxima);
	}

	public void pintarFinal(int xFinal, int yFinal) {
		this.panel[xFinal][yFinal].addFoto(this.libre, this.fin);
		this.panel[xFinal][yFinal].setTipo(Casillas.FINAL);
		this.tieneMeta = true;
		this.corFinal = new Coordenadas(xFinal, yFinal);
	}
	
	public Coordenadas getCorInicio() {
		return corInicio;
	}

	public void setCorInicio(Coordenadas corInicio) {
		this.corInicio = corInicio;
	}

	public Coordenadas getCorFinal() {
		return corFinal;
	}

	public void setCorFinal(Coordenadas corFinal) {
		this.corFinal = corFinal;
	}

	public void pintarInicio(int x, int y) {
		this.panel[x][y].addFoto(this.libre, this.inicio);
		this.panel[x][y].setTipo(Casillas.INICIO);
		this.tieneInicio = true;
		this.corInicio = new Coordenadas(x, y);
	}

	/**
	 * @return the altura
	 */
	public int getAltura() {
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * @return the anchura
	 */
	public int getAnchura() {
		return anchura;
	}

	/**
	 * @param anchura the anchura to set
	 */
	public void setAnchura(int anchura) {
		this.anchura = anchura;
	}
	
	
	public Celda getCelda(int x, int y) {
		return panel[x][y];
	}
	
	public ImageIcon getLibre() {
		return this.libre;
	}

	public boolean getTieneInicio() {
		return this.tieneInicio;
	}

	public boolean getTieneMeta() {
		return this.tieneMeta;
	}

	public void setTieneInicio(boolean b) {
		this.tieneInicio = b;
	}

	public void setTieneMeta(boolean b) {
		this.tieneMeta = b;
	}

	public ImageIcon getInicio() {
		return this.inicio;
	}
	
	public ImageIcon getBloqueo() {
		return this.bloqueado;
	}
	
	public ImageIcon getFinal() {
		return this.fin;
	}
	
	public void limpiarTablero() {
		for( int x=0; x< altura;x++){
			for (int y=0; y< anchura;y++){
				this.panel[x][y].setFoto(this.libre);
				this.panel[x][y].setTipo(Casillas.LIBRE);
				this.panel[x][y].setToolTipText(Integer.toString(x + 1) + " , " + Integer.toString(y + 1));
				this.panel[x][y].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
			}
		}
		tieneInicio = false;
		tieneMeta = false;
		this.savepoint = new ArrayList <>();
	}
	
	public Celda[][] getMatriz() {
		return this.panel;
	}

	public void ponerSavePoint(int fila, int columna) {
		this.panel[fila][columna].setTipo(Casillas.SAVEPOINT);
		savepoint.add(new Coordenadas(fila, columna));
		this.panel[fila][columna].addFoto(this.libre, this.point);
		
		
	}

	public double getAltCasillas() {
		return altCasillas;
	}

	public void setAltCasillas(double altCasillas) {
		this.altCasillas = altCasillas;
	}

	public void ponerAlturas(double altu) {
		this.altCasillas = altu;
		for (int x = 0; x < altura; x++) {
			for (int y = 0; y < anchura; y++) {
				this.panel[x][y].crearAltura(altCasillas);
			}
		}
	}
	
}
