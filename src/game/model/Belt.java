package game.model;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.GameObject;

public abstract class Belt implements GameObject{
	private static final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	protected BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	protected  int x ;
	protected  int y ;
	protected boolean visible;
	protected String name = "belt1";
	protected double force = 0.05;
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public Belt(int x , int y) {
		setX(x);
		setY(y);
		logger.debug("x , y of belt is "+x+","+y);
		this.visible = true ;
		logger.info("belt set visible in constractor");
		Resources res = Resources.getInstance() ;
		spriteImages = res.getImage(name) ;
		logger.info("get belt image from resources");
	}
	@Override
	public int getX() {
		
		return x ;
	}

	@Override
	public void setX(int x) {
		this.x = x ; 
		logger.debug("x was set "+x);
		
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		logger.debug("y was set "+y);
		this.y = y ; 
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return spriteImages[0].getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return spriteImages[0].getHeight();
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		// TODO Auto-generated method stub
		return spriteImages;
	}
	public boolean intersect(GameObject plate) {
		if (Math.abs(plate.getY() - this.y) > 5) {
			return false;
		}
		if (plate.getX() > this.x + this.getWidth() || plate.getX() < this.x ) {
			return false;
		}

		return true;
	}
	public double getForce(){
		return force;
	}
	public void setForce(double force){
		this.force = force;
	}

}
