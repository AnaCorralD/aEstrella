package negocio;
import javax.jws.soap.SOAPBinding;

import negocio.Coordenadas;

public class Nodo {

	private Nodo nodoPadre; //Sirve para saber el nodo que le precede
	private Nodo nodoDestino; //Indica el destino
	private Coordenadas coordenadas; //Coordenadas nodo actual
	private double costeTotal;
	private double costeG;
	
	public Nodo() {}
	
	public Nodo(Nodo p, Nodo d, Coordenadas cor, double cG) {
		this.nodoPadre = p;
		this.nodoDestino = d;
		this.coordenadas = cor;
		this.costeG = cG;
		if(nodoDestino != null) {
			this.costeTotal = this.costeG + calculoDistanciaEuclidea(); // g(x) + h(x)
		}
	}
	
	private double calculoDistanciaEuclidea() {
		return Math.sqrt(
				Math.pow((this.coordenadas.getX() - this.nodoDestino.coordenadas.getX()), 2) +
				Math.pow((this.coordenadas.getY() - this.nodoDestino.coordenadas.getY()), 2));
	}

	public Nodo getNodoPadre() {
		return nodoPadre;
	}

	public void setNodoPadre(Nodo nodoPadre) {
		this.nodoPadre = nodoPadre;
	}

	public Nodo getNodoFinal() {
		return nodoDestino;
	}

	public void setNodoFinal(Nodo nodoFinal) {
		this.nodoDestino = nodoFinal;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	public double getCosteTotal() {
		return costeTotal;
	}

	public void setCosteTotal(double costeTotal) {
		this.costeTotal = costeTotal;
	}

	public double getCosteG() {
		return costeG;
	}

	public void setCosteG(double costeG) {
		this.costeG = costeG;
	}
	
	@Override
	public boolean equals(Object n) {
		return this.coordenadas.esIgual(((Nodo) n).coordenadas);
	}
}
