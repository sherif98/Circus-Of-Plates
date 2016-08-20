package game.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class StackUpdaterCommand implements StackCommand, Observer{

	private Point clownPosition;
	private Map<MyStack, Point> stacksDeltaPositionTable;
	private ArrayList<MyStack> stacks;
	private final int MAX_WIDTH = 50;
	private final int DISTANCE_BET_STACKS = 30;
	
	public StackUpdaterCommand(Point clownCenterPoint) {
		stacksDeltaPositionTable = new HashMap<MyStack, Point>();
		stacks = new ArrayList<MyStack>();
		clownPosition = clownCenterPoint;
	}
	
	@Override
	public void update(Observable arg0, Object clownCenterPoint) {
		this.clownPosition = (Point) clownCenterPoint;
	}

	@Override
	public void addStack(MyStack stack) {
		if(!stacks.contains(stack)){
			stacks.add(stack);
		}
		calculateStacksPositions();
		
	}

	@Override
	public void setMyPosition(MyStack stack) {
	/*	System.out.println(clownPosition.x);
		System.out.println(stacksDeltaPositionTable.get(stack));
	*/	if(!stacks.contains(stack))addStack(stack);
		stack.setX(clownPosition.x + stacksDeltaPositionTable.get(stack).x);
		stack.setY(clownPosition.y);
	}
	
	private void calculateStacksPositions(){
		
		int totalWidth = stacks.size() * (DISTANCE_BET_STACKS + MAX_WIDTH);

		
		int startX =  -1 * totalWidth/2;
		
		stacksDeltaPositionTable = new HashMap<MyStack, Point>();
		for(MyStack stack : stacks){
			stacksDeltaPositionTable.put(stack, new Point(startX, clownPosition.y));
			startX += MAX_WIDTH + DISTANCE_BET_STACKS;
		}
	}
	

}
