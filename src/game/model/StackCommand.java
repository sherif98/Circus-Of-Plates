package game.model;

public interface StackCommand {
	/**
	 * adds that stack to the list of stacks that the command has.
	 * actually every clown should have only one of that command which takes control
	 * of all of the stacks carried by the clown
	 * @param stack
	 * reference of a stack to be added
	 */
	public void addStack(MyStack stack);
	
	/**
	 * takes  reference to a stack that is already in the list that the command has
	 * and sets its position according to the position of the clown
	 * @param stack
	 * reference to a stack to adjust its position
	 */
	public void setMyPosition(MyStack stack);
	
}
