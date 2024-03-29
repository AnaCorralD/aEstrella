package command;

import java.util.ArrayList;

import negocio.Coordenadas;
import negocio.FactoriaNegocio;
import negocio.TAlgoritmo;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarCamino implements Command {

	@Override
	public Contexto execute(Object dato) {
		TAlgoritmo tAlgoritmo = (TAlgoritmo) dato;
		ArrayList<Coordenadas> res = FactoriaNegocio.getInstance().crearBusqueda().buscarCamino(tAlgoritmo);
		if(res == null) return new Contexto(Events.BUSCAR_KO, null);
		else return new Contexto(Events.BUSCAR_OK, res);
	}



}
