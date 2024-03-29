package negocio;

import java.util.ArrayList;

import presentacion.Celda;

public class TAlgoritmo {
	private Coordenadas inicio;
	private Coordenadas meta;
	private Celda[][] matriz;
	private ArrayList<Coordenadas> metas;
	private boolean modo;
	private double altura;
	
	public ArrayList<Coordenadas> getMetas() {
		return metas;
	}

	public void setMetas(ArrayList<Coordenadas> metas) {
		this.metas = metas;
	}

	//modo sin altura
	public TAlgoritmo(Coordenadas ini, Coordenadas fin, Celda[][] m, ArrayList<Coordenadas> arrayList, boolean modo) {
		this.inicio = ini;
		this.meta = fin;
		this.matriz = m;
		this.metas = arrayList;
		this.modo = modo;
	}
	
	//modo sin altura
	public TAlgoritmo(Coordenadas ini, Coordenadas fin, Celda[][] m, ArrayList<Coordenadas> arrayList, boolean modo, double altura) {
		this.inicio = ini;
		this.meta = fin;
		this.matriz = m;
		this.metas = arrayList;
		this.modo = modo;
		this.altura = altura;
	}

	public boolean getModo() {
		return modo;
	}

	public void setModo(boolean modo) {
		this.modo = modo;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public Coordenadas getCorInicio() {
		return this.inicio;
	}

	public Coordenadas getCorFinal() {
		return this.meta;
	}

	public Celda[][] getMatriz() {
		return this.matriz;
	}
}
