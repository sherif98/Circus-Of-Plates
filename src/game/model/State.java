package game.model;

public interface State {
	
	/**
	 * sets stack as an image to be painted
	 */
	public void setSpriteImages();
	
	/**
	 * changes the changed state when the state of of the object is changed
	 * @return
	 * returns next state
	 */
	public State getNextState();
}
