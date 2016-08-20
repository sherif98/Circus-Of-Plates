package menuBar;

public interface GameState {
	
	public GameState getNextState();
	public void applyState(javax.swing.JMenuItem item);
}
