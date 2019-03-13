/**
 * 
 */
package dispatcher;

import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUITablero;
public class DispatcherImp extends Dispatcher {

	@Override
	public void generarVista(Contexto contexto) {
		int evento = contexto.getEvento();
		switch(evento) {
		case(Events.GUI_MAIN):
			GUITablero.getInstance().actualizar(contexto);
		break;
		case(Events.BUSCAR_CAMINO):
			GUITablero.getInstance().actualizar(contexto);
		break;
		case(Events.BUSCAR_KO):
			GUITablero.getInstance().actualizar(contexto);
		break;
		case(Events.BUSCAR_OK):
			GUITablero.getInstance().actualizar(contexto);
		break;
		case(Events.GUI_TABLERO):
			GUITablero.getInstance().actualizar(contexto);
		break;
		}
	}
}