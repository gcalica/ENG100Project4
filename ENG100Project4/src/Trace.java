import java.awt.Color;
import java.util.ArrayList;

public class Trace {

	private EZCircle circle;
	public static ArrayList<EZLine> ezline = new ArrayList<EZLine>();
	private int oldx, oldy, x, y;
	private Color lineColor;
	private boolean penDown;
	private int penSize;

	public Trace(){
		circle = EZ.addCircle( EZ.getWindowWidth() / 2 - 500, EZ.getWindowHeight() / 2 + 100, 10, 10, Color.black, false);
		lineColor = new Color (0,255,200);
		penDown = true;
		penSize = 5;
	}


	public void setPenSize(int size){
		penSize = size;
	}

	public void pendown(){
		penDown = true;
	}
	public void penup(){
		penDown = false;
	}
	
	public void move(int distance){	

		oldx = circle.getXCenter();
		oldy = circle.getYCenter();

		circle.moveForward(distance);

		x = circle.getXCenter();
		y = circle.getYCenter();

		if (penDown){
			ezline.add(EZ.addLine(oldx,oldy,x,y,lineColor,penSize));
		}
		
		EZ.refreshScreen();

	}
	
	public void forward(int distance){
		move(distance);
	}
	
	public void moveTo(int x, int y){
		oldx = circle.getXCenter();
		oldy = circle.getYCenter();

		circle.translateTo(x,y);

		x = circle.getXCenter();
		y = circle.getYCenter();

		if (penDown){
			ezline.add(EZ.addLine(oldx,oldy,x,y,lineColor,penSize));
		}
		EZ.refreshScreen();

	}

	public void backward(int distance){
		move(-distance);
	}

	public void left(int angle){
		circle.turnLeft(angle);
		EZ.refreshScreen();
	}
	public void right(int angle){
		circle.turnRight(angle);
		EZ.refreshScreen();
	}
	
	public void show() {
		circle.show();
	}
	
	public void hide() {
		circle.hide();
	}
}
