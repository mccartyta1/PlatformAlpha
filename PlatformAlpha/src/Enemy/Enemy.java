package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import Segment.Segment;
import Actor.Actor;

public abstract class Enemy extends Actor {
	
	protected double reactionTime, reaction, gravity, maxMoveSpeed;
	protected boolean falling, onGround, aggro;
	protected int effect, effectDuration, creatingPower;
	protected Segment currentSegment;
	protected Random random = new Random();
	public static final int NORMAL = 0;
	public static final int FROZEN = -1;
	public static final int MIND_CONTROL = -2;

	
	protected void moveEnemy() {
		if (left) {
			dx = -moveSpeed;
		} else if (right) {
			dx = moveSpeed;
		} else dx = 0;
		
		if (falling) {
			up = false;
			dy += gravity;
			if ( dy > 5.0 )
				dy = 5.0;
		} else if (up) {
			// up == jumping
			up = false;
			dy = -moveSpeed * 5;
			falling = true;
			onGround = false;
			
		}
	}
	
	protected void isFalling() {
		Rectangle rectangleBelowPlayer = new Rectangle( (int) x, (int) y + 1, wi, hi );
		ArrayList<Actor> objectsInArea;
		Boolean areaClear = true;
		objectsInArea = currentSegment.objectsInArea( rectangleBelowPlayer );
		
		for ( int i = 0; i < objectsInArea.size(); i++ ) 
		{
			if ( objectsInArea.get( i ).isSolid() ) 
			{
				// This is probably where to add the "hit floor" type stuff
				areaClear = false;
				break;
			}
		}
		
		if ( areaClear ) {
			falling = true;
		}
	}
	
	protected void checkLocation() {
		double nx = (dead ? x : x + dx );
		double ny = y + dy;
		boolean areaClear = true;
		ArrayList<Actor> objectsInArea;
		// Get all objects that will be in that area. 
		objectsInArea = currentSegment.objectsInArea( new Rectangle( (int) nx , (int) ny , wi , hi ));
		
		// Check if any of the mare solid.
		for ( int i = 0; i < objectsInArea.size(); i++ ) 
		{
			if ( objectsInArea.get( i ).isSolid() ) 
			{
				// This is probably where to add the "hit floor" type stuff
				areaClear = false;
				break;
			}
		}
		
		if ( areaClear ) {
			x = nx;
			y = ny;
		} else {
			falling = false;
			onGround = true;
			dy = 0;
			left = !left;
			right = !right;
		}
	}
	
	public void checkEffects() {
		if ( effect != 0 && effectDuration > 0 ) {
			
			if ( effect == FROZEN && effectDuration == 1000 ) {
				moveSpeed = moveSpeed / 2;
			}
			
			if ( effect == MIND_CONTROL ) {
				aggro = false;
			}
			
			effectDuration--;

		} else {
			
			// Effects end
			
			if ( effect == FROZEN ) {
				moveSpeed = maxMoveSpeed;
			}
			if ( effect == MIND_CONTROL ) {
				// We could set their aggro back to true, but eh.
			}
			effect = NORMAL;
		}
	}
	
	public void hit( int damage ) {
		aggro = true;
		if ( damage == FROZEN ) {
			// ice effect
			effect = FROZEN;
			effectDuration = 1000;
			damage = 10;
		}
		
		if ( damage == MIND_CONTROL ) {
			// mind control leffect
			effect = MIND_CONTROL;
			effectDuration = 1000;
			damage = 15;
		}
		super.hit( damage );
	}
	
	public void setAggro( boolean b ) {
		aggro = b;
	}
	
	public boolean getAggro() {
		return aggro;
	}
	
	public int isCreatingPower() {
		return creatingPower;
	}
	
	public void setCreatingPower( int i ) {
		creatingPower = i;
	}
	
	public void drawEffects( Graphics2D g ) {
		if ( effect == FROZEN ) {
			g.setColor( Color.CYAN );
			g.fillRect((int) x - 2, (int) y + hi / 2, wi + 4, hi / 2);
		}
		
		if ( effect == MIND_CONTROL ) {
			g.setColor( Color.MAGENTA );
			g.drawRect((int) x - 1, (int) y - 1, wi + 2, hi + 2);
		}
	}
	
	public abstract void think();

}
