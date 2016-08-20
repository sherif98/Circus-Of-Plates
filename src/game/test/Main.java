package game.test;

import menuBar.MenuBarInitializer;
import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.World;
import game.controller.Controller;

public class Main {
	public static void main(String[] args) {
		
		World controller = new Controller(1300, 650, 0, 20);
		GameEngine.start("Circus Of Plates" , controller , new MenuBarInitializer(controller));
	}
}
