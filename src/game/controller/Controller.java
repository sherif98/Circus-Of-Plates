package game.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import game.model.BackGround;
import game.model.Belt;
import game.model.Clown;
import game.model.LeftBelt;
import game.model.MyStack;
import game.model.Platte;
import game.model.Resources;
import game.model.RightBelt;
import game.model.StackVisitor;

public class Controller implements World, StackVisitor{

	/*
	 * please read written the comment on top of the clown class first..
	 * 
	 * as it was said before stacks are added to list of moving game objects
	 * normally like all other game objects..
	 * 
	 * the intersection method which should be implemented here is to check for
	 * the intersection of any plate with any of the stacks if so ,, that a
	 * method call to the method "addPlate" should be done ,, where the only
	 * parameter is the plate which made the intersection ,, also that plate
	 * must be totally removed from the moving list or any other list..
	 * 
	 * stacks are moved through calling the method "refresh" where each one
	 * updates its position by itself ,, so the method called "refresh" here
	 * must make a call to the method "refresh" of every stack..
	 */
	private final double GRAVITY = 0.0058;
	private final int LEFT_X = 0;
	private final int RIGHT_X = 1100;
	private final int MAX_MASS = 4;
	private final int DEFAULT_PLATE_NUMBER = 10;
	private int maxBeltWidth = 100;
	private ArrayList<GameObject> constant = new ArrayList<>();
	private ArrayList<GameObject> movable = new ArrayList<>();
	private ArrayList<GameObject> control = new ArrayList<>();
	private double speed;
	private int controlledSpeed; // should be taken from the level
	private int width, height;
	private int score = 0;
	private boolean isPaused = false;
	private boolean isGameOver = false;
	Logger logger = Logger.getLogger(Controller.class);

	// private int freq ;
	public Controller(int screenWidth, int screenHight, double speed, int controlledSpeed) {
		width = screenWidth;
		height = screenHight;
		this.speed = speed;
		// should be done using builder
		Resources resource = Resources.getInstance();
		resource.loadAllThemes();
		resource.loadAllImages();
		resource.loadAllShapes();
		////////////////////////////////
		this.controlledSpeed = controlledSpeed;
		logger.debug("controller speed equals " +controlledSpeed);
		BackGround backGround = new BackGround();
		logger.info("Back Ground is created");
		constant.add(backGround);
		initLeftBelt(LEFT_X , 50) ;
		initLeftBelt(LEFT_X, 350) ; 
		initLeftBelt(LEFT_X, 200) ; 
		initRightBelt(RIGHT_X , 400) ; 
		initRightBelt(RIGHT_X, 50) ; 
		initRightBelt(RIGHT_X, 200) ;
		logger.info("Belts are postioned");
		Random rand = new Random();
		// maybe better to take it as a parameter
		for (int i = 0; i < DEFAULT_PLATE_NUMBER; i++) {
			int tmpRnd = rand.nextInt(maxBeltWidth) + rand.nextInt(2) * RIGHT_X;
			Platte p = new Platte(tmpRnd, 0);
			((Platte) p).setVerticalSpeed(speed);
			movable.add(p);
		}
		
		Clown clown1 = new Clown(screenWidth / 2, screenHight, this, 3);
		control.add(clown1);
		logger.info("Clown added to control array list");

	}
	
	private void initLeftBelt(int x, int y){
		Belt belt = new LeftBelt(x, y);
		constant.add(belt);
		maxBeltWidth = Math.max(maxBeltWidth, belt.getWidth());
		logger.debug("iniatial Left Belt at "+x+","+y );

	}
	private void initRightBelt(int x, int y){
		Belt belt = new RightBelt(x, y);
		constant.add(belt);
		maxBeltWidth = Math.max(maxBeltWidth, belt.getWidth());
		logger.debug("iniatial right Belt at "+x+","+y );
	}
	
	public void startNewGame(int numberOfPlates, int nStacks){
		logger.info("new game is started with numberOfPlates "+numberOfPlates+"and number of stacks"+nStacks);
		if(isGameOver)return;
		movable = new ArrayList<>();
		control = new ArrayList<>();
		
		Random rand = new Random();
		// maybe better to take it as a parameter
		for (int i = 0; i < numberOfPlates; i++) {
			int tmpRnd = rand.nextInt(maxBeltWidth) + rand.nextInt(2) * RIGHT_X;
			Platte p = new Platte(tmpRnd, 0);
			movable.add(p);
		}
		Clown clown = new Clown(width / 2, height, this, nStacks);
		logger.info("Clown is created");
		control.add(clown);
		score = 0;
	}

	@Override
	public List<GameObject> getConstantObjects() {
		return constant;
	}

	@Override
	public List<GameObject> getMovableObjects() {
		return movable;
	}

	@Override
	public List<GameObject> getControlableObjects() {
		return control;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean refresh() {
		// if you return false from refresh it displays game over on the screen
		if(isPaused)return true;
		Random rand = new Random();
		for(GameObject plate : movable){
			if(plate instanceof Platte == false){
				if(plate instanceof MyStack)((MyStack) plate).refresh();
				continue;
			}
			
			boolean mustPass = false;
			
			for (GameObject stack : movable) {
				if (stack instanceof MyStack) {
					if(stack.getY() <= 0){
						isGameOver = true;
						return false;
					}
					if (intersect(stack, plate)) {
						((MyStack) stack).addPlate(plate);
						plate.setY(0);
						int tmpRnd = rand.nextInt(maxBeltWidth) + rand.nextInt(2) * RIGHT_X;
						plate.setX(tmpRnd);
						mustPass = true;
						
						Random rnd = new Random();
						((Platte) plate).setMass(rnd.nextInt(1 << MAX_MASS));
						((Platte) plate).setVerticalSpeed(speed);
						((Platte) plate).setHorizontalSpeed(0);
						((Platte) plate).setDirection(0);
						break;
					}
				}
			}
			
			if(mustPass)continue;

			boolean isOnBelt = false;
			for(GameObject movingObj : constant){
				if(movingObj instanceof Belt){
					if( ((Belt)movingObj).intersect(plate)){
						double force = ((Belt)movingObj).getForce();
						double acceleration = force / ((Platte) plate).getMass();
						double plateHorizontalSpeed = ((Platte) plate).getHorizontalSpeed();
						((Platte) plate).addToX(plateHorizontalSpeed);
						((Platte) plate).setHorizontalSpeed(plateHorizontalSpeed + acceleration);
						
						if(movingObj instanceof LeftBelt)((Platte) plate).setDirection(1);
						else ((Platte) plate).setDirection(-1);
						((Platte) plate).setVerticalSpeed(0);
						isOnBelt = true;
						break;
					}
				}
			}
			if(!isOnBelt){
				double plateHorizontalSpeed = ((Platte) plate).getHorizontalSpeed();
				((Platte) plate).addToX(plateHorizontalSpeed);
				double plateVerticalSpeed = ((Platte) plate).getVerticalSpeed();
				if(plateVerticalSpeed == 0){
					plateVerticalSpeed = speed;
					((Platte) plate).setVerticalSpeed(speed);
				}
				((Platte) plate).addToY(plateVerticalSpeed);
				((Platte) plate).setVerticalSpeed(plateVerticalSpeed + GRAVITY);
				if (plate.getY() > this.height) {
					Random rnd = new Random();
					((Platte) plate).setMass(rnd.nextInt(1 << MAX_MASS));
					plate.setY(0);
					((Platte) plate).setVerticalSpeed(speed);
					((Platte) plate).setHorizontalSpeed(0);
					((Platte) plate).setDirection(0);
					int tmpRnd = rand.nextInt(maxBeltWidth) + rand.nextInt(2) * RIGHT_X;
					plate.setX(tmpRnd);
				}
			}
		}

		return true;
	}



	private boolean intersect(GameObject controller, GameObject moving) {
		MyStack stack = (MyStack) controller ;
		Platte plate = (Platte) moving ;
		
		boolean tmpIntersectY1 = liesBetween(plate.getY() + plate.getHeight() + plate.getVerticalSpeed()
				, stack.getY(), plate.getY() + plate.getHeight());
		boolean tmpIntersectY2 = liesBetween(plate.getY() + plate.getHeight()
				, stack.getY(), plate.getY() + plate.getHeight() -plate. getVerticalSpeed());
		
		boolean tmpIntersectX1 = liesBetween(plate.getX() + plate.getWidth() + plate.getHorizontalSpeed()
				, stack.getX(), plate.getX() - stack.getWidth());
		
		if((tmpIntersectY1 || tmpIntersectY2) && tmpIntersectX1){
			return true ; 
		}
		return false;
	}

	@Override
	public String getStatus() {
		score = 0;
		for(GameObject gameObject : movable){
			if(gameObject instanceof MyStack){
				((MyStack) gameObject).acceptVisitor(this);
			}
		}
		return "score : " + score;
	}

	@Override
	public int getSpeed() {
		return (int) speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
		logger.info("speed changed to "+speed);
	}
	
	private boolean liesBetween(double greaterNum, double toTest , double smallerNum){
		return toTest <= greaterNum && toTest >= smallerNum;
	}

	@Override
	public int getControlSpeed() {
		return controlledSpeed;
	}

	@Override
	public void visit(MyStack stack) {
		
		score += stack.getScore();
		
	}
	public void pause(){
		isPaused = true;
		logger.info("game is paused");
		for(GameObject gameObject : control){
			if(gameObject instanceof Clown){
				((Clown) gameObject).stopClown(true);
			}
		}
	}
	public void resume(){
		isPaused = false;
		logger.info("game is resume");
		for(GameObject gameObject : control){
			if(gameObject instanceof Clown){
				((Clown) gameObject).stopClown(false);
			}
		}
	}

}
