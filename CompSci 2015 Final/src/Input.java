import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener, MouseMotionListener{
	private Map<Integer, Boolean> key = new HashMap<Integer, Boolean>();
	
	public int mx = 0, my = 0;
	
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

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
}
