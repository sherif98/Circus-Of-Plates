package game.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ImageFactory {
	ArrayList<MyImage> images = new ArrayList<>();
	Logger logger = Logger.getLogger(ImageFactory.class);
	public void setImages(ArrayList<MyImage> images) {
		this.images = images;
	}
	
	public BufferedImage[] getImage(String name) {
		for (MyImage image : images) {
			if (image.match(name)) {
				logger.debug("image matches name '"+name+"'and returned ");
				return image.getImage();
			}
		}
		return null;
	}

}
