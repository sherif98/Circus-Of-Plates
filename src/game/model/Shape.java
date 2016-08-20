package game.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

public abstract class Shape implements Cloneable{

	public abstract BufferedImage getImage();
	public abstract Color getColor();
	public abstract void setFillColor(Color fillColor);
	public abstract void Draw(Graphics2D canvas);
	
	
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (Exception e) {
			Logger.getLogger(Shape.class).fatal("Fail to clone shape");
			e.printStackTrace();
		}
		return clone;
	}
}
