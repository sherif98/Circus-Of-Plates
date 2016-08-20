package game.model;

import java.util.Random;

import eg.edu.alexu.csd.oop.game.GameObject;

public class MovingBelt extends Belt implements GameObject{
	Random rand = new Random() ; 
	public MovingBelt(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getForce() {
		
		return 0;
	}

}
