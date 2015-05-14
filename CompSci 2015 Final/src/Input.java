import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener{
	Map<Integer, Boolean> key = new HashMap<Integer, Boolean>();
	
	public void keyPressed(KeyEvent e) {
		key.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		key.put(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {

	}
	
	public boolean getKey(int keyCode){
		if(key.get(keyCode) != null)
			return key.get(keyCode);
		else
			return false;
	}
}
