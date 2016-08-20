package game.model;

import java.awt.image.BufferedImage;

import eg.edu.alexu.csd.oop.game.GameObject;

public class Platte implements GameObject {
	private Shape spriteShape;
	private double y, x;
	private boolean visible;
	private static int BUFFER_SIZE = 1;
	private int direction = 0;
	private double horizontalSpeed = 0;
	private double verticalSpeed = 0;
	private int mass = 1;
	
	public Platte(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
		// create a bunch of buffered images and place into an array, to be displayed sequentially
		Resources resource = Resources.getInstance();
		spriteShape = resource.getShape();
	}

	@Override
	public int getX() {
		return (int) x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return (int)y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	public void addToY(double y){
		this.y += y;
	}

	public void addToX(double x){
		this.x += x * direction;
	}
	
	public void setDirection(int d){
		direction = d;
	}
	public double getVerticalSpeed(){
		return verticalSpeed;
	}
	public void setVerticalSpeed(double speed){
		this.verticalSpeed = speed;
	}
	public double getHorizontalSpeed(){
		return horizontalSpeed;
	}
	public void setHorizontalSpeed(double speed){
		this.horizontalSpeed = speed;
	}
	public int getMass(){
		return mass;
	}
	public void setMass(int mass){
		this.mass = mass;
	}

	@Override
	public int getWidth() {
		if (spriteShape == null) {
			throw new RuntimeException("platte has no shape");
		}
		return spriteShape.getImage().getWidth();
	}

	@Override
	public int getHeight() {
		if (spriteShape == null) {
			throw new RuntimeException("Platte has no shape");
		}
		return spriteShape.getImage().getHeight();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		if (spriteShape == null) {
			throw new RuntimeException("Platte Has No Shape");
		}
		BufferedImage buffer[] = new BufferedImage[BUFFER_SIZE];
		buffer[0] = spriteShape.getImage();
		return buffer;
	}
	
	public Shape getShape(){
		return spriteShape;
	}

}
