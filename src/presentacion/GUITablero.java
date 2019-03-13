package presentacion;

import javax.swing.JFrame;

public abstract class GUITablero extends JFrame implements GUI{

	private static GUITablero instance;
	
	public static GUITablero getInstance() {
		if(instance == null) {
			instance = new GUITableroImp();
		}
		return instance;
	}

}
