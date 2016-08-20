package game.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.GameObject;

public class MyStack implements GameObject, VisitedElement{ //observer of the clown
	
	private final int MAX_WIDTH = 50;
	private final int MIN_HEIGHT = 30;
	private BufferedImage plateHolder;;
	private ArrayList <GameObject> plattes;
	private boolean visible = true;
	private int height = MIN_HEIGHT;
	private int width = MAX_WIDTH;
	private int score = 0;
	private int x;
	private int y;
	private State currentState;
	private BufferedImage[] spriteImages;
	private StackCommand command;
	private Logger logger = Logger.getLogger(MyStack.class);
	
	public MyStack(StackCommand command) {
		plattes = new ArrayList<GameObject>();
		this.command = command;
		command.setMyPosition(this);
		currentState = new DirtyState();
		logger.debug("current State of stack is"+currentState.toString());
		BufferedImage tmpImg = new BufferedImage(MAX_WIDTH, MIN_HEIGHT+5, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmpImg.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(8));
		g.drawLine(MAX_WIDTH/2, MIN_HEIGHT, MAX_WIDTH/2, 0);
		g.drawLine(0, 2, MAX_WIDTH, 2);
		plateHolder = tmpImg;
		logger.debug("image was set for stack");
		g.dispose();
		currentState.setSpriteImages();
	}
	
	public void refresh(){
		command.setMyPosition(this);
		//System.out.println(plattes.size());
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y - height;
	}

	@Override
	public int getWidth() {
		
		for(GameObject plate : plattes){
			width = Math.max(width, plate.getWidth());
		}
		return width;
	}

	@Override
	public int getHeight() {
		
		height = plattes.size() * 2 + MIN_HEIGHT;
		for(GameObject plate : plattes){
			height += plate.getHeight();
		}
		return height;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		
		currentState.setSpriteImages();
		return spriteImages;
	}
	
	public boolean addPlate(GameObject plate){
		/*
		 * controller must test for intersection with the stack
		 * and if it is , call me and remove that plate from the
		 * moving list
		 */
		currentState = new DirtyState();
		if(plattes.size() < 2){			
			plattes.add(plate);
			return true;
		}
		
		Platte platte = (Platte) plate;
		Platte platte1 = (Platte) plattes.get(plattes.size() - 1);
		Platte platte2 = (Platte) plattes.get(plattes.size() - 2);
		
		if(platte1.getShape().getColor().equals(platte.getShape().getColor())
				&& platte2.getShape().getColor().equals(platte.getShape().getColor())){
			
			score++;
			plattes.remove(plattes.size() - 1);
			plattes.remove(plattes.size() - 1);
		}else{
			plattes.add(plate);
		}
	//	System.out.println(plattes.size());
		return true;
	}
	
	public int getScore(){
		return score;
	}
	
	private class DirtyState implements State{
		
		private State nextState = new CleanState(this);

		@Override
		public State getNextState() {
			return nextState;
		}
		
		@Override
		public void setSpriteImages(){
			
			getWidth();
			getHeight();
			
			BufferedImage[] img = new BufferedImage[1];
			int y = height;
			
			img[0] = new BufferedImage(width + 1, height, BufferedImage.TYPE_INT_ARGB);
			
			Graphics2D g = img[0].createGraphics();
			y -= MIN_HEIGHT + 2;
			g.drawImage(plateHolder, 0, y, null);
			
			for(GameObject plate : plattes){
				BufferedImage[] tmp = plate.getSpriteImages();
				y -= plate.getHeight() + 2;
				g.drawImage(tmp[0], 0, y, null);
			}
			g.dispose();
			spriteImages = img;
			currentState = nextState;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Dirty state";
		}
	}

	private class CleanState implements State{
		
		private State nextState;
		
		private CleanState(State nextState) {
			this.nextState = nextState;
		}
		
		@Override
		public State getNextState() {
			return nextState;
		}
		
		@Override
		public void setSpriteImages(){
			
			return;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Clean State";
		}
		
	}

	@Override
	public void acceptVisitor(StackVisitor visitor) {
		visitor.visit(this);
		
	}

}