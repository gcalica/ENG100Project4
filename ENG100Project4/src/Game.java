import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * ENG 100 - Project 4(Remixing Project 2)
 * Professor John Gagnon
 * @author Gian Calica
 */
public class Game {
	static final int SCREEN_WIDTH = 1024;
	static final int SCREEN_HEIGHT = 768;

	EZImage computer;
	Color g = Color.green;
	Color b = Color.black;
	int opt = 40;
	String cont = "Continue";
	String back = "Back";
	int contx, conty;	
	int backx, backy;
	int top;
	int topfs = 65;

	//Sounds
	EZSound click, shutter, typing, shuffle, message, calculator, construction, underwater, waterClick, lasergun, waterDrop, art;

	//Initial Phase
	EZText initialPhaseTitle, initialPhaseLine1, initialPhaseLine2, initialPhaseContinue;
	EZRectangle initialPhaseContinueButton;

	//First Phase
	EZText firstPhaseCont, firstPhaseBack, firstPhaseLine1, firstPhaseLine2;
	EZImage firstPhaseImg1, firstPhaseImg2, firstPhaseImg3;

	//Second Phase
	EZText secondPhaseCont, secondPhaseBack, secondPhaseLine1, secondPhaseLine2, secondPhaseLine3, secondPhaseLine4, secondPhaseLine5;
	EZImage secondPhaseImg1, secondPhaseImg2, secondPhaseImg3, secondPhaseImg4;

	//Third Phase
	EZText thirdPhaseCont, thirdPhaseBack, thirdPhaseLine1, thirdPhaseLine2, thirdPhaseLine3;
	EZImage thirdPhaseImg1, thirdPhaseImg2, thirdPhaseImg3;

	//Fourth Phase
	EZText fourthPhaseCont, fourthPhaseBack, fourthPhaseLine1, fourthPhaseLine2, fourthPhaseLine3;
	Trace pen; //Tree Fractal Pen
	public static ArrayList<EZLine> bronchi = new ArrayList<EZLine>(); //Bronchi Fractal

	//Fifth Phase
	EZText fifthPhaseCont, fifthPhaseBack, fifthPhaseLine1, fifthPhaseLine2, fifthPhaseLine3;
	EZRectangle room, roomOutline, waterRect;
	EZCircle ball;
	EZImage[] jitter;
	int jitterX, jitterY;
	ArrayList<Rain> dropList = new ArrayList<Rain>();
	Rain aDrop;

	//Sixth Phase
	EZText sixthPhaseCont, sixthPhaseBack, sixthPhaseLine1;
	EZImage sixthPhaseImg1, sixthPhaseImg2, sixthPhaseImg3, sixthPhaseImg4, sixthPhaseImg5;
	EZGroup bodyGrp, headGrp, leftArmGrp, rightArmGrp, leftLegGrp, rightLegGrp;
	EZImage head, leftArm, rightArm, leftLeg, rightLeg, body;

	//Seventh Phase
	EZText seventhPhaseBack, seventhPhaseLine1;
	EZImage shutdown;

	/*
	 * Initial Phase: Menu
	 * First Phase: Computers are everywhere
	 * Second Phase: Used in everyday life
	 * Third Phase: Used in many careers
	 * Fourth Phase: Used to create visuals (Fractals: forests, Bronchi)
	 * Fifth Phase: Used to create visuals part. 2 (Physics, Disorder)
	 * Sixth Phase: Used to solve real life problems
	 * Seventh Phase: End Credit
	 */

	/*
	 * Set to false when the phase has already been run once.
	 * This is to keep track of whether we should draw or just show the phase based on if it not already has been run or it did already run, respectively.
	 */
	boolean initialrun = true;
	boolean firstrun = true;
	boolean secondrun = true;
	boolean thirdrun = true;
	boolean fourthrun = true;
	boolean fifthrun = true;
	boolean sixthrun = true;
	boolean seventhrun = true;

	boolean firstPhase = false;
	boolean secondPhase = false;
	boolean thirdPhase = false;
	boolean fourthPhase = false;
	boolean fifthPhase = false;
	boolean sixthPhase = false;
	boolean seventhPhase = false;

	public static void main(String[] args) {
		Game game = new Game();
		game.createWindow();
		game.loadSoundFiles();
		game.drawInitialPhase();
	}

	private void createWindow() {
		EZ.initialize(SCREEN_WIDTH, SCREEN_HEIGHT);
		EZ.setBackgroundColor(Color.blue);
		computer = EZ.addImage("resources/images/computer.png", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		computer.scaleTo(0.95f);
		contx = computer.getXCenter() + 300;
		conty = computer.getYCenter() + 185;
		backx = computer.getXCenter() - 300;
		backy = computer.getYCenter() + 185;
	}

	private void loadSoundFiles() {
		click = EZ.addSound("resources/sounds/doteat.wav");
		shutter = EZ.addSound("resources/sounds/shutter.wav");
		typing = EZ.addSound("resources/sounds/typing.wav");
		shuffle = EZ.addSound("resources/sounds/cardShuffle.wav");
		message = EZ.addSound("resources/sounds/message.wav");
		calculator = EZ.addSound("resources/sounds/calculator.wav");
		construction = EZ.addSound("resources/sounds/construction.wav");
		underwater = EZ.addSound("resources/sounds/underwater.wav");
		waterClick = EZ.addSound("resources/sounds/waterClick.wav");
		lasergun = EZ.addSound("resources/sounds/laser7.wav");
		waterDrop = EZ.addSound("resources/sounds/waterDrop.wav");
		art = EZ.addSound("resources/sounds/art.wav");
	}

	private void playSound(EZSound sound) {
		if(sound == click) {
			stopSounds();
			click.play();
		} else if (sound == shutter) {
			stopSounds();
			shutter.play();
		} else if(sound == typing) {
			stopSounds();
			typing.play();
		} else if(sound == shuffle) {
			stopSounds();
			shuffle.play();
		} else if(sound == message) {
			stopSounds();
			message.play();
		} else if(sound == calculator) {
			stopSounds();
			calculator.play();
		} else if(sound == construction) {
			stopSounds();
			construction.play();
		} else if(sound == underwater) {
			stopSounds();
			underwater.play();
		} else if(sound == waterClick) {
			stopSounds();
			waterClick.play();
		} else if(sound == lasergun) {
			stopSounds();
			lasergun.play();
		} else if(sound == waterDrop) {
			stopSounds();
			waterDrop.play();
		} else if(sound == art) {
			stopSounds();
			art.play();
		}
	}

	private void stopSounds() {
		click.stop();
		shutter.stop();
		typing.stop();
		shuffle.stop();
		message.stop();
		calculator.stop();
		construction.stop();
		underwater.stop();
		waterClick.stop();
		lasergun.stop();
	}

	/*
	 * When pressing continue, hide previous phase, then draw the next phase. 
	 * When pressing back, hide current phase, then show the previous phase. DO NOT REDRAW.
	 */
	private void drawInitialPhase() {
		top = computer.getYCenter() - 300;

		initialPhaseTitle = EZ.addText(computer.getXCenter(), top, "ENG 100: Project 4", b, 100);
		initialPhaseTitle.setFont("resources/fonts/COMPUTERrobot.ttf");

		initialPhaseLine1 = EZ.addText(initialPhaseTitle.getXCenter(), initialPhaseTitle.getYCenter() + 100, "Interactive Remix of Project 2", b, 50);
		initialPhaseLine1.setFont("resources/fonts/COMPUTERrobot.ttf");

		initialPhaseLine2 = EZ.addText(initialPhaseLine1.getXCenter(), initialPhaseLine1.getYCenter() + 100, "by Gian Calica", Color.blue, 70);
		initialPhaseLine2.setFont("resources/fonts/COMPUTERrobot.ttf");

		initialPhaseContinueButton = EZ.addRectangle(computer.getXCenter(), computer.getYCenter() + 50, 550, 100, b, true);
		initialPhaseContinue = EZ.addText(initialPhaseContinueButton.getXCenter(), initialPhaseContinueButton.getYCenter(), "Continue", g, 95);
		initialPhaseContinue.setFont("resources/fonts/COMPUTERrobot.ttf");
		initialPhaseInteract();
	}

	private void initialPhaseInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(initialPhaseContinueButton.isPointInElement(x, y)) {
					playSound(click);
					hideInitialPhase();
					firstPhase = true;
					if(initialrun) {
						initialrun = false;
						drawFirstPhase();
					} else {
						showFirstPhase();
						firstPhaseInteract();
					}
					break;
				}
			}
		}
	}

	private void showInitialPhase() {
		initialPhaseTitle.show();
		initialPhaseLine1.show();
		initialPhaseLine2.show();
		initialPhaseContinueButton.show();
		initialPhaseContinue.show();
	}

	private void hideInitialPhase() {
		initialPhaseTitle.hide();
		initialPhaseLine1.hide();
		initialPhaseLine2.hide();
		initialPhaseContinueButton.hide();
		initialPhaseContinue.hide();
	}

	private void drawFirstPhase() {
		if(firstPhase) {
			firstPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			firstPhaseBack = EZ.addText(backx, backy, back, g, opt);

			firstPhaseLine1 = EZ.addText(computer.getXCenter(), top, "Computers are everywhere", b, topfs);

			firstPhaseImg1 = EZ.addImage("resources/images/smartphone.png", computer.getXCenter() - 250, computer.getYCenter() - 120); //Smartphone
			firstPhaseImg1.scaleTo(0.5f);
			firstPhaseLine2 = EZ.addText(firstPhaseImg1.getXCenter() + 10, firstPhaseImg1.getYCenter() + 60, "Click me!", b, 45);
			firstPhaseLine2.rotateTo(330);

			firstPhaseImg2 = EZ.addImage("resources/images/pc.png", computer.getXCenter() + 250, computer.getYCenter() - 120); //PC
			firstPhaseImg2.scaleTo(0.6f);

			firstPhaseImg3 = EZ.addImage("resources/images/laptop.png", computer.getXCenter(), computer.getYCenter() + 10); //Laptop
			firstPhaseImg3.scaleTo(0.4f);

			firstPhaseInteract();
		}
	}

	private void firstPhaseInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(firstPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideFirstPhase();
					firstPhase = false;
					if(firstrun) {
						firstrun = false;
						secondPhase = true;
						drawSecondPhase();
					} else {
						showSecondPhase();
						secondPhaseInteract();
					}
					break;
				} else if(firstPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideFirstPhase();
					firstPhase = false;
					showInitialPhase();
					initialPhaseInteract();
					break;
				} else if(firstPhaseImg1.isPointInElement(x, y)) {
					playSound(shutter);
					int alpha = 0;
					int maxAlpha = 200;

					EZCircle flashScreen = EZ.addCircle(firstPhaseImg1.getXCenter() + 5, firstPhaseImg1.getYCenter() - 70, 100, 100, new Color(255, 255, 255, alpha), true);

					try {
						//Fading into White Flash
						for(alpha = 0; alpha <= maxAlpha; alpha++) {
							Thread.sleep(1);
							flashScreen.setColor(new Color(255, 255, 255, alpha));
						}
						//Fading out of White Flash
						for(alpha = maxAlpha; alpha >= 0; alpha--) {
							Thread.sleep(1);
							flashScreen.setColor(new Color(255, 255, 255, alpha));
						}

						EZ.removeEZElement(flashScreen);
					} catch(InterruptedException e) {
						System.out.println("Error: Flash Screen");
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void showFirstPhase() {
		firstPhaseCont.show();
		firstPhaseBack.show();

		firstPhaseLine1.show();
		firstPhaseLine2.show();

		firstPhaseImg1.show();
		firstPhaseImg2.show();
		firstPhaseImg3.show();
	}

	private void hideFirstPhase() {
		firstPhaseCont.hide();
		firstPhaseBack.hide();

		firstPhaseLine1.hide();
		firstPhaseLine2.hide();

		firstPhaseImg1.hide();
		firstPhaseImg2.hide();
		firstPhaseImg3.hide();
	}

	private void drawSecondPhase() {
		if(secondPhase) {
			secondPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			secondPhaseBack = EZ.addText(backx, backy, back, g, opt);

			secondPhaseLine1 = EZ.addText(computer.getXCenter(), top, "...Used in everyday life", b, topfs);

			int fs = 40;

			secondPhaseImg1 = EZ.addImage("resources/images/secondPhaseImg1.png", computer.getXCenter() - 150, computer.getYCenter() - 150); //Word Doc
			secondPhaseLine2 = EZ.addText(secondPhaseImg1.getXCenter(), secondPhaseImg1.getYCenter() + 70, "Writing Data", b, fs);

			secondPhaseImg2 = EZ.addImage("resources/images/secondPhaseImg2.png", computer.getXCenter() + 150, computer.getYCenter() - 150); //Message Bubbles
			secondPhaseLine3 = EZ.addText(secondPhaseImg2.getXCenter(), secondPhaseImg2.getYCenter() + 70, "Communication", b, fs);

			secondPhaseImg3 = EZ.addImage("resources/images/secondPhaseImg3.png", computer.getXCenter() - 150, computer.getYCenter() + 70); //Data Folders
			secondPhaseLine4 = EZ.addText(secondPhaseImg3.getXCenter(), secondPhaseImg3.getYCenter() + 70, "Storing Data", b, fs);

			secondPhaseImg4 = EZ.addImage("resources/images/secondPhaseImg4.png", computer.getXCenter() + 150, computer.getYCenter() + 70); //Calculator
			secondPhaseLine5 = EZ.addText(secondPhaseImg4.getXCenter(), secondPhaseImg4.getYCenter() + 70, "Computing Data", b, fs);

			secondPhaseInteract();
		}
	}

	private void secondPhaseInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(secondPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideSecondPhase();
					secondPhase = false;
					if(secondrun) {
						secondrun = false;
						thirdPhase = true;
						drawThirdPhase();
					} else {
						showThirdPhase();
						thirdPhaseInteract();
					}
					break;
				} else if(secondPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideSecondPhase();
					secondPhase = false;
					firstPhase = true;
					showFirstPhase();
					firstPhaseInteract();
					break;
				} else if(secondPhaseImg1.isPointInElement(x, y)) {
					playSound(typing);
				} else if(secondPhaseImg2.isPointInElement(x, y)) {
					playSound(message);
				} else if(secondPhaseImg3.isPointInElement(x, y)) {
					playSound(shuffle);
				} else if(secondPhaseImg4.isPointInElement(x, y)) {
					playSound(calculator);
				}
			}
		}
	}

	private void showSecondPhase() {
		secondPhaseCont.show();
		secondPhaseBack.show();

		secondPhaseLine1.show();
		secondPhaseLine2.show();
		secondPhaseLine3.show();
		secondPhaseLine4.show();
		secondPhaseLine5.show();

		secondPhaseImg1.show();
		secondPhaseImg2.show();
		secondPhaseImg3.show();
		secondPhaseImg4.show();
	}

	private void hideSecondPhase() {
		secondPhaseCont.hide();
		secondPhaseBack.hide();

		secondPhaseLine1.hide();
		secondPhaseLine2.hide();
		secondPhaseLine3.hide();
		secondPhaseLine4.hide();
		secondPhaseLine5.hide();

		secondPhaseImg1.hide();
		secondPhaseImg2.hide();
		secondPhaseImg3.hide();
		secondPhaseImg4.hide();
	}

	private void drawThirdPhase() {
		if(thirdPhase) {
			thirdPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			thirdPhaseBack = EZ.addText(backx, backy, back, g, opt);

			thirdPhaseLine1 = EZ.addText(computer.getXCenter(), top, "...Used in many careers", b, topfs);

			thirdPhaseImg1 = EZ.addImage("resources/images/thirdPhaseImg1.png", computer.getXCenter() - 250, computer.getYCenter() - 50); //Engineer
			thirdPhaseImg2 = EZ.addImage("resources/images/thirdPhaseImg2.png", computer.getXCenter(), computer.getYCenter() - 50); //Biology
			thirdPhaseImg3 = EZ.addImage("resources/images/thirdPhaseImg3.png", computer.getXCenter() + 250, computer.getYCenter() - 50); //Art

			thirdPhaseInteract();
		}
	}

	private void thirdPhaseInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(thirdPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideThirdPhase();
					thirdPhase = false;
					if(thirdrun) {
						thirdrun = false;
						fourthPhase = true;
						drawFourthPhase();
					} else {
						showFourthPhase();
						fourthPhaseInteract();
					}
					break;
				} else if(thirdPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideThirdPhase();
					thirdPhase = false;
					secondPhase = true;
					showSecondPhase();
					secondPhaseInteract();
					break;
				} else if(thirdPhaseImg1.isPointInElement(x, y)) {
					playSound(construction);
				} else if(thirdPhaseImg2.isPointInElement(x, y)) {
					playSound(underwater);
				} else if(thirdPhaseImg3.isPointInElement(x, y)) {
					playSound(art);
				}
			}
		}
	}

	private void showThirdPhase() {
		thirdPhaseCont.show();
		thirdPhaseBack.show();

		thirdPhaseLine1.show();

		thirdPhaseImg1.show();
		thirdPhaseImg2.show();
		thirdPhaseImg3.show();
	}

	private void hideThirdPhase() {
		thirdPhaseCont.hide();
		thirdPhaseBack.hide();

		thirdPhaseLine1.hide();

		thirdPhaseImg1.hide();
		thirdPhaseImg2.hide();
		thirdPhaseImg3.hide();
	}

	private void drawFourthPhase() {
		if(fourthPhase) {
			fourthPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			fourthPhaseBack = EZ.addText(backx, backy, back, g, opt);

			fourthPhaseLine1 = EZ.addText(computer.getXCenter(), top - 35, "Create visuals and simulations", b, topfs);

			//Tree Fractal
			pen = new Trace();
			EZ.setFrameRate(1001);
			pen.setPenSize(1);
			pen.penup();
			pen.moveTo(EZ.getWindowWidth() / 2 - 150,  EZ.getWindowHeight() / 2 + 150);
			pen.left(90);
			pen.pendown();
			drawTree(300, pen);
			fourthPhaseLine2 = EZ.addText(computer.getXCenter() - 300, computer.getYCenter() - 200, "Forest/Tree branching", Color.white, 30);
			fourthPhaseLine2.rotateBy(-45);
			fourthPhaseLine2.setColor(b);

			//Bronchi Fractal
			EZ.setFrameRate(60);
			drawBronchi(computer.getXCenter() + 150, computer.getYCenter() - 270, 90, 8);
			fourthPhaseLine3 = EZ.addText(computer.getXCenter() + 260, computer.getYCenter() - 180, "Bronchi/Bronchioles branching", Color.white, 30);
			fourthPhaseLine3.rotateBy(45);
			fourthPhaseLine3.setColor(b);


			fourthPhaseInteract();
		}
	}

	public static void drawTree(int r, Trace theTurtle){
		if (r<5){
			theTurtle.forward(r);
			theTurtle.backward( r);
		} else {
			theTurtle.forward( r/3);
			theTurtle.left(30);
			drawTree(r*2/3, theTurtle);
			theTurtle.right(30);
			theTurtle.backward( r/3);

			theTurtle.forward(r/2);
			theTurtle.right(25);
			drawTree(r/2,theTurtle);
			theTurtle.left(25);
			theTurtle.backward(r/2);

			theTurtle.forward( r*5/6);
			theTurtle.right(25);
			drawTree(r/2, theTurtle);
			theTurtle.left(25);
			theTurtle.backward(r*5/6);

		}
	}

	/** Credited to: https://gist.github.com/AnimeshShaw/7087470 */
	private void drawBronchi( int x1, int y1, double angle, int depth) {
		if (depth == 0)
			return;
		int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 12.0);
		int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 12.0);
		EZLine branch;
		bronchi.add(branch = EZ.addLine(x1, y1, x2, y2, new Color(255, 140, 95)));
		branch.setThickness(3);
		drawBronchi(x2, y2, angle - 20, depth - 1);
		drawBronchi(x2, y2, angle + 20, depth - 1);
	}

	private void fourthPhaseInteract() {
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(fourthPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideFourthPhase();
					fourthPhase = false;
					if(fourthrun) {
						fourthrun = false;
						fifthPhase = true;
						drawFifthPhase();
					} else {
						showFifthPhase();
						fifthPhaseInteract();
					}
					break;
				} else if(fourthPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideFourthPhase();
					fourthPhase = false;
					thirdPhase = true;
					showThirdPhase();
					thirdPhaseInteract();
					break;
				}
			}
		}
	}

	private void showFourthPhase() {
		fourthPhaseCont.show();
		fourthPhaseBack.show();

		fourthPhaseLine1.show();

		pen.show();
		for(int i = 0; i < Trace.ezline.size() ; i++) {
			EZLine turtle;
			turtle = Trace.ezline.get(i);
			turtle.show();
		}
		fourthPhaseLine2.show();

		for(int i = 0; i < bronchi.size() ; i++) {
			EZLine branch;
			branch = bronchi.get(i);
			branch.show();
		}
		fourthPhaseLine3.show();
	}

	private void hideFourthPhase() {
		fourthPhaseCont.hide();
		fourthPhaseBack.hide();

		fourthPhaseLine1.hide();

		pen.hide();
		for(int i = 0; i < Trace.ezline.size() ; i++) {
			EZLine turtle;
			turtle = Trace.ezline.get(i);
			turtle.hide();
		}
		fourthPhaseLine2.hide();

		for(int i = 0; i < bronchi.size() ; i++) {
			EZLine branch;
			branch = bronchi.get(i);
			branch.hide();
		}
		fourthPhaseLine3.hide();

	}

	private void drawFifthPhase() {
		if(fifthPhase) {
			fifthPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			fifthPhaseBack = EZ.addText(backx, backy, back, g, opt);

			fifthPhaseLine1 = EZ.addText(computer.getXCenter(), top - 35, "Create visuals and simulations", b, topfs);
			room = EZ.addRectangle(computer.getXCenter() - 200, computer.getYCenter() - 50, 370, 400, Color.white, true);
			roomOutline = EZ.addRectangle(computer.getXCenter() - 200, computer.getYCenter() - 50, 370, 400, Color.black, false);
			fifthPhaseLine2 = EZ.addText(room.getXCenter(), room.getYCenter() - 70, "Physics", b, 40);
			ball = EZ.addCircle(room.getXCenter(), room.getYCenter(), 20, 20, Color.blue, true);

			//Turtle Jitters
			waterRect = EZ.addRectangle(fifthPhaseCont.getXCenter() - 105, fifthPhaseCont.getYCenter()- 155, 415, 260, new Color(0, 240, 255, 150), true);
			fifthPhaseLine3 = EZ.addText(waterRect.getXCenter(), waterRect.getYCenter() - 100, "Disorder/Randomness", b, 40);

			jitter = new EZImage[6];
			Random rand = new Random();

			for (int i=0; i < jitter.length; i++){
				jitterX  = rand.ints(computer.getXCenter() + 20, fifthPhaseCont.getXCenter() + 50).findFirst().getAsInt();
				jitterY = rand.ints(computer.getYCenter() - 40, fifthPhaseCont.getYCenter() - 20).findFirst().getAsInt();
				jitter[i]= EZ.addImage("resources/images/turtle.png", jitterX ,jitterY);
			}
			fifthPhaseInteract();
		}
	}

	private void fifthPhaseInteract() {
		//Physics Ball
		int posX = room.getXCenter();
		int posY = room.getYCenter();
		Random rand = new Random();
		int directionX = rand.nextInt(20);
		int directionY = rand.nextInt(20);
		boolean active = false;

		Random randj = new Random(); //Turtle Jitters random gen

		while(true) {
			EZ.refreshScreen();

			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			//Physics Ball
			ball.translateTo(posX, posY);
			posX += directionX;
			posY += directionY;

			if (posX <= room.getXCenter() - room.getWidth() / 2 + 7) {
				if(active) {
					playSound(lasergun);
				}
				randomBallColor();
				directionX = -directionX;
			}
			if (posX >= room.getXCenter() + room.getWidth() / 2 - 7) {
				if(active) {
					playSound(lasergun);
				}
				randomBallColor();
				directionX = -directionX;
			}
			if (posY <= room.getYCenter() - room.getHeight() / 2 + 10) {
				if(active) {
					playSound(lasergun);
				}
				randomBallColor();
				directionY = -directionY;
			}
			if (posY >= room.getYCenter() + room.getHeight() / 2 - 10) {
				if(active) {
					playSound(lasergun);
				}
				randomBallColor();
				directionY = -directionY;
			}

			//Turtle Jitters
			for (int i=0; i < jitter.length; i++){
				jitterX = jitter[i].getXCenter();
				jitterY = jitter[i].getYCenter();

				jitterX += -2+randj.nextInt(6);
				jitterY += -2+randj.nextInt(6);

				if(jitterX > fifthPhaseCont.getXCenter() || jitterX < computer.getXCenter()) {
					jitterX = randj.ints(computer.getXCenter() + 20, fifthPhaseCont.getXCenter() + 50).findFirst().getAsInt();
				}
				if(jitterY > fifthPhaseCont.getYCenter() - 70 || jitterY < computer.getYCenter() - 200) {
					jitterY = randj.ints(computer.getYCenter() - 40, fifthPhaseCont.getYCenter() - 40).findFirst().getAsInt();
				}
				jitter[i].translateTo(jitterX, jitterY);
			}

			//Rain Drops
			aDrop = new Rain();
			dropList.add(aDrop);
			for(int i =0; i < dropList.size(); i++){
				Rain eachDrop;
				eachDrop = dropList.get(i);

				if (eachDrop.floatUp() == false) {
					dropList.remove(eachDrop);
				}
				
			}

			//Mouse Interaction
			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(fifthPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideFifthPhase();
					fifthPhase = false;
					if(fifthrun) {
						fifthrun = false;
						sixthPhase = true;
						drawSixthPhase();
					} else {
						showSixthPhase();
						sixthPhaseInteract();
					}
					break;
				} else if(fifthPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideFifthPhase();
					fifthPhase = false;
					fourthPhase = true;
					showFourthPhase();
					fourthPhaseInteract();
					break;
				} else if(waterRect.isPointInElement(x, y)) {
					playSound(waterClick);
				} else if(room.isPointInElement(x, y)) {
					if(!active) {
						active = true;
						room.setColor(new Color(0, 0, 0, 100));
					} else if(active) {
						active = false;
						room.setColor(new Color(255, 255, 255, 0));
					}
				} else {
					for(int i =0; i < dropList.size(); i++){
						Rain eachDrop;
						eachDrop = dropList.get(i);
						if(eachDrop.isPointInElementTo(x, y)) {
							playSound(waterDrop);
							dropList.remove(eachDrop);
							eachDrop.remove();
						}
					}
				}
			}
			EZ.refreshScreen();
		}
	}

	private void randomBallColor() {
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);

		Color c = new Color(r, g, b);
		ball.setColor(c);
	}

	private void showFifthPhase() {
		fifthPhaseCont.show();
		fifthPhaseBack.show();

		fifthPhaseLine1.show();
		fifthPhaseLine2.show();
		fifthPhaseLine3.show();

		room.show();
		roomOutline.show();
		ball.show();

		for (int i=0; i < jitter.length; i++){
			jitter[i].show();
		}
		waterRect.show();
	}

	private void hideFifthPhase() {
		fifthPhaseCont.hide();
		fifthPhaseBack.hide();

		fifthPhaseLine1.hide();
		fifthPhaseLine2.hide();
		fifthPhaseLine3.hide();

		room.hide();
		roomOutline.hide();
		ball.hide();

		for (int i = 0; i < jitter.length; i++){
			jitter[i].hide();
		}
		waterRect.hide();

		for(int i = 0; i < dropList.size(); i++) {
			Rain eachDrop;
			eachDrop = dropList.get(i);
			eachDrop.remove();
		}

		dropList.clear();	
	}

	private void drawSixthPhase() {
		if(sixthPhase) {
			sixthPhaseCont = EZ.addText(contx, conty, cont, g, opt);
			sixthPhaseBack = EZ.addText(backx, backy, back, g, opt);

			sixthPhaseLine1 = EZ.addText(computer.getXCenter(), top, "...Used for practical applications", b, topfs);
			
			sixthPhaseImg1 = EZ.addImage("resources/images/stocks.png", computer.getXCenter() - 300, computer.getYCenter() - 50);
			sixthPhaseImg2 = EZ.addImage("resources/images/plane.png", computer.getXCenter() - 100, computer.getYCenter() - 50);
			sixthPhaseImg3 = EZ.addImage("resources/images/shopping.png", computer.getXCenter() + 100, computer.getYCenter() - 50);
			sixthPhaseImg4 = EZ.addImage("resources/images/maps.png", computer.getXCenter() + 300, computer.getYCenter() - 50);
			sixthPhaseImg5 = EZ.addImage("resources/images/editing.png", computer.getXCenter(), computer.getYCenter() + 90);

			bodyGrp = EZ.addGroup();
			headGrp = EZ.addGroup();
			leftArmGrp = EZ.addGroup();
			rightArmGrp = EZ.addGroup();
			leftLegGrp = EZ.addGroup();
			rightLegGrp = EZ.addGroup();

			bodyGrp.addElement(headGrp);
			bodyGrp.addElement(leftArmGrp);
			bodyGrp.addElement(rightArmGrp);
			leftArmGrp.addElement(leftLegGrp);
			rightArmGrp.addElement(rightLegGrp);

			int headHeight = 100;
			head = EZ.addImage("resources/images/robothead.png",0, -headHeight/2);
			headGrp.addElement(head);

			int armHeight = 150;
			leftArm = EZ.addImage("resources/images/robotarm.png", 0, armHeight/2);
			leftArmGrp.addElement(leftArm);

			rightArm = EZ.addImage("resources/images/robotarm.png", 0, armHeight/2);
			rightArmGrp.addElement(rightArm);

			leftLeg = EZ.addImage("resources/images/robotarm.png", 0, armHeight/2);
			leftLegGrp.addElement(leftLeg);

			rightLeg = EZ.addImage("resources/images/robotarm.png", 0, armHeight/2);
			rightLegGrp.addElement(rightLeg);

			int bodyWidth = 150;
			int bodyHeight = 137;
			body = EZ.addImage("resources/images/robotshortbody.png",0, bodyHeight/2);
			bodyGrp.addElement(body);

			leftArmGrp.translateTo(-bodyWidth/2,0);
			rightArmGrp.translateTo(bodyWidth/2,0);
			leftLegGrp.translateTo(0,bodyHeight);
			rightLegGrp.translateTo(0,bodyHeight);

			bodyGrp.translateTo(computer.getXCenter(), computer.getYCenter() - 220);
			bodyGrp.scaleTo(0.4f);
			
			sixthPhaseInteract();
		}
	}

	private void sixthPhaseInteract() {
		double angle=0;
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			headGrp.rotateTo((int) (Math.sin(Math.toRadians(angle))*30));

			leftArmGrp.rotateTo((int) (Math.cos(Math.toRadians(angle))*30)+45);
			rightArmGrp.rotateTo((int) (-Math.cos(Math.toRadians(angle))*30)-45);

			leftLegGrp.rotateTo((int) (Math.cos(Math.toRadians(angle))*30)+45);
			rightLegGrp.rotateTo((int) (-Math.cos(Math.toRadians(angle))*30)-45);

			angle += 2;

			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(sixthPhaseCont.isPointInElement(x, y)) {
					playSound(click);
					hideSixthPhase();
					sixthPhase = false;
					if(sixthrun) {
						sixthrun = false;
						seventhPhase = true;
						drawSeventhPhase();
					} else {
						showSeventhPhase();
						seventhPhaseInteract();
					}
					break;
				} else if(sixthPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideSixthPhase();
					sixthPhase = false;
					fifthPhase = true;
					showFifthPhase();
					fifthPhaseInteract();
					break;
				} else if(headGrp.isPointInElement(x, y) 
						|| leftArmGrp.isPointInElement(x, y) || rightArmGrp.isPointInElement(x, y) 
						|| leftLegGrp.isPointInElement(x, y) || rightLegGrp.isPointInElement(x, y) 
						|| body.isPointInElement(x, y)) {
				}
			}
		}
	}

	private void showSixthPhase() {
		sixthPhaseCont.show();
		sixthPhaseBack.show();

		sixthPhaseLine1.show();
		
		sixthPhaseImg1.show();
		sixthPhaseImg2.show();
		sixthPhaseImg3.show();
		sixthPhaseImg4.show();
		sixthPhaseImg5.show();

		headGrp.show();
		leftArmGrp.show();
		rightArmGrp.show();
		leftLegGrp.show();
		rightLegGrp.show();
		bodyGrp.show();

	}

	private void hideSixthPhase() {
		sixthPhaseCont.hide();
		sixthPhaseBack.hide();

		sixthPhaseLine1.hide();

		sixthPhaseImg1.hide();
		sixthPhaseImg2.hide();
		sixthPhaseImg3.hide();
		sixthPhaseImg4.hide();
		sixthPhaseImg5.hide();
		
		headGrp.hide();
		leftArmGrp.hide();
		rightArmGrp.hide();
		leftLegGrp.hide();
		rightLegGrp.hide();
		bodyGrp.hide();
	}

	private void drawSeventhPhase() {
		if(seventhPhase) {
			seventhPhaseBack = EZ.addText(backx, backy, back, g, opt);

			seventhPhaseLine1 = EZ.addText(computer.getXCenter(), top + 200, "Shutting down...", b, 100);
			shutdown = EZ.addImage("resources/images/ajax-loader.gif", computer.getXCenter(), computer.getYCenter());
			seventhPhaseInteract();
		}
	}

	private void seventhPhaseInteract() {
		Random rand = new Random();
		while(true) {
			EZ.refreshScreen();
			int x = EZInteraction.getXMouse();
			int y = EZInteraction.getYMouse();

			try {
				Thread.sleep(20);
				int r = rand.nextInt(256);
				int g = rand.nextInt(256);
				int b = rand.nextInt(256);
				Color c = new Color(r, g, b);
				seventhPhaseLine1.setColor(c);
			} catch(Exception e) {
				e.getStackTrace();
				System.out.println("Seventh Phase Change Color");
			}

			if(EZInteraction.wasMouseLeftButtonPressed()) {
				if(seventhPhaseBack.isPointInElement(x, y)) {
					playSound(click);
					hideSeventhPhase();
					seventhPhase = false;
					sixthPhase = true;
					showSixthPhase();
					sixthPhaseInteract();
					break;
				}
			}
		}
	}

	private void showSeventhPhase() {
		seventhPhaseBack.show();
		seventhPhaseLine1.show();
		shutdown.show();
	}

	private void hideSeventhPhase() {
		seventhPhaseBack.hide();
		seventhPhaseLine1.hide();
		shutdown.hide();
	}

}
