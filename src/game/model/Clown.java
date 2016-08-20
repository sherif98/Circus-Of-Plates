package game.model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;


import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;


public class Clown extends Observable implements GameObject {
	
	/*
	 * each clown should have one object of command (StackUpdaterCommand) for the created stacks..
	 * 
	 * each stack is created as a moving game object and is added to the command through 
	 * the method addStack..
	 * 
	 * the command object is an observer of the clown and must be notified whenever
	 * its position changes through the method update ofcourse with parameter "clownCenterPoint"
	 * representing the center of the clown
	 */
	private static final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	private int x;
	private int y;
	private boolean visible;
	private static String name = "clown";
	private StackUpdaterCommand stackCommand ;
	private boolean stopClown = false;
	private Logger logger = Logger.getLogger(Clown.class);
	public Clown(int leftX, int bottomY , World world, int stackNumber) {
		this.visible = true;
		// create a bunch of buffered images and place into an array, to be displayed sequentially
		Resources resource = Resources.getInstance();
		spriteImages = resource.getImage(name);
		logger.info("get Clown image from resources");
		if (spriteImages == null) {
			logger.fatal("Clown image equals null");
			throw new RuntimeException("Clown image equal null");
		}
		this.x = leftX - spriteImages[0].getWidth() / 2;
		this.y = bottomY - spriteImages[0].getHeight() - 5;
		logger.debug("set x , y for clown "+x+","+y);
		stackCommand = new StackUpdaterCommand(new Point(this.x + getWidth() / 2, this.y)) ; 
		int numberOfStacks = stackNumber ;
		ArrayList<GameObject>  moving = (ArrayList<GameObject>) world.getMovableObjects() ;
		for(int i = 0 ; i < numberOfStacks ; i++){
			MyStack stack = new MyStack(stackCommand) ;
			stackCommand.addStack(stack);
			moving.add(stack) ;
		}
		addObserver(stackCommand);
		logger.info("create "+numberOfStacks+" stacks and put them in MovableObjects ArrayList");
		
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		if(stopClown)return;
		
		this.x = x;
		setChanged();
		notifyObservers(new Point(x + getWidth() / 2, y));
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		return;
	}

	@Override
	public int getWidth() {
		return spriteImages[0].getWidth();
	}

	@Override
	public int getHeight() {
		return spriteImages[0].getHeight();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		return spriteImages;
	}
	public void stopClown(boolean stop){
		stopClown = stop;
		logger.info("Clown is stopped");
	}
}
