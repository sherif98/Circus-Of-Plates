package game.model;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.GameObject;

public class BackGround implements GameObject{
	private static final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	private static final int X = 0;
	private static final int Y = 0;
	private boolean visible;
	private static String name = "backGround";
	private Logger logger = Logger.getLogger(BackGround.class);
	
	public BackGround(){
		
		this.visible = true;
		logger.debug("BackGround set"+visible);
		// create a bunch of buffered images and place into an array, to be displayed sequentially
		Resources resource = Resources.getInstance();
		spriteImages = resource.getImage(name);
		logger.info("get back ground image from resources and put it");
	}
	

	@Override
	public int getX() {
		return X;
	}

	@Override
	public void setX(int x) {
		return;
	}

	@Override
	public int getY() {
		return Y;
	}

	@Override
	public void setY(int y) {
		return;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
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
}
