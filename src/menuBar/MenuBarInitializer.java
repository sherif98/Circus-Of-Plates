package menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import eg.edu.alexu.csd.oop.game.*;
import game.controller.Controller;
import game.model.Belt;

public class MenuBarInitializer extends JMenuBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private World controller;
	private GameState currentGameState = new PauseState();
	private final double EASY_FORCE = 0.05;
	private final double MEDIUM_FORCE = 0.06;
	private final double HARD_FORCE = 0.07;
	private Logger logger = Logger.getLogger(MenuBarInitializer.class);
	
	public MenuBarInitializer(World controller){
		this.controller = controller;
		
		JMenu file = new JMenu("File");
		add(file);
		
		JMenuItem tmpMenuItem;
		
		//new game menu
		tmpMenuItem = new JMenuItem("New Game");
		tmpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		file.add(tmpMenuItem);
		
		//new game menu
		tmpMenuItem = new JMenuItem("Exit");
		tmpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		file.add(tmpMenuItem);
		
		//pause resume menu
		JMenuItem pauseItem = new JMenuItem("Pause");
		pauseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentGameState.applyState(pauseItem);
			}
		});
		file.add(pauseItem);
		
		//difficulty
		JMenu difficulty = new JMenu("Difficulty");
		file.add(difficulty);
		
		//easy
		tmpMenuItem = new JMenuItem("Easy");
		tmpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				easy();
			}
		});
		difficulty.add(tmpMenuItem);
		
		//medium
		tmpMenuItem = new JMenuItem("Medium");
		tmpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				medium();
			}
		});
		difficulty.add(tmpMenuItem);
		
		//hard
		tmpMenuItem = new JMenuItem("Hard");
		tmpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hard();
			}
		});
		difficulty.add(tmpMenuItem);
		
	}
	
	private void newGame(){
		
		String s;
		int nStacks, nPlates;
		try{
			((Controller) controller).pause();
			s = JOptionPane.showInputDialog(null
					, "Please enter the number of stacks that clown holds", "Stacks", JOptionPane.PLAIN_MESSAGE);
			nStacks = Integer.valueOf(s);
			
			s = JOptionPane.showInputDialog(null
					, "Please enter MAX number of plates that can be shown on the screen at a time", "Plates", JOptionPane.PLAIN_MESSAGE);
			nPlates = Integer.valueOf(s);
			((Controller) controller).startNewGame(nPlates, nStacks);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Bad input", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		finally{
			((Controller) controller).resume();
		}
		
	}
	
	private void easy(){
		setDifficulty(EASY_FORCE, 0);
	}
	private void medium(){
		setDifficulty(MEDIUM_FORCE, 2);
	}
	private void hard(){
		setDifficulty(HARD_FORCE, 5);
	}
	
	private void setDifficulty(double force, int speed){
		if(currentGameState instanceof ResumeState)return;
		
		((Controller) controller).pause();
		((Controller) controller).setSpeed(speed);
		
		for(GameObject gameObj : controller.getConstantObjects()){
			if(gameObj instanceof Belt){
				((Belt) gameObj).setForce(force);
			}
		}
		((Controller) controller).resume();
		logger.info("Difficulty was set with force "+force+" and spedd "+speed);
	}
	
	private class PauseState implements GameState{

		private GameState nextState = new ResumeState(this);
		private final String NEXT_CAPTION = "Resume";
		
		@Override
		public GameState getNextState() {
			return nextState;
		}
		@Override
		public void applyState(JMenuItem item) {
			item.setText(NEXT_CAPTION);
			((Controller) controller).pause();
			currentGameState = nextState;
			logger.info("current state is "+NEXT_CAPTION);
		}
	
		
	}
	
	private class ResumeState implements GameState{
		
		private GameState nextState;
		private final String NEXT_CAPTION = "Pause";
		
		
		private ResumeState(GameState nextState){
			
			this.nextState = nextState;
		}
		@Override
		public GameState getNextState() {
			return nextState;
		}
		@Override
		public void applyState(JMenuItem item) {
			item.setText(NEXT_CAPTION);
			((Controller) controller).resume();
			currentGameState = nextState;
			logger.info("current state is "+NEXT_CAPTION);
		}
		
	}

}

