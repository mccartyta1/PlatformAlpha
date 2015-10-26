package Segment;

import java.awt.Rectangle;
import java.util.ArrayList;

import Actor.Actor;
import Actor.Player;
import Controller.Manager;

public abstract class Segment {
	
	protected Manager m;
	protected Player player;
	
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract ArrayList<Actor> objectsInArea(Rectangle area);
	public abstract ArrayList<Actor> objectsInAreaForPlayer(Rectangle area);

	
	public double[] getPlayerPosition() {
		double[] returnVal = { player.getX(), player.getY() };
		return returnVal;
	}
	
}
