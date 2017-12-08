import java.util.Random;

public class Rain {
	EZImage drop;
	int x, y;
	int speed;
	
	public Rain() {
		Random rg = new Random();

		x = rg.ints(530, 900).findFirst().getAsInt();
		y = 100;
		drop = EZ.addImage("resources/images/raindrop.png", x,y);
		
		speed = rg.nextInt(10)+1;
	}
	
	public boolean floatUp() {
		y += speed;
		drop.translateTo(x, y);
		
		if (y > 260) {
			remove();
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isPointInElementTo(int x, int y) {
		return drop.isPointInElement(x, y);
	}
	
	public void remove() {
		EZ.removeEZElement(drop);
	}
}
