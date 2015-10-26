package Power;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Actor.Actor;
import Segment.Segment;

public class PowerEnergyBeam extends Power {

	Rectangle collisionRectangle;

	public PowerEnergyBeam( double x, double y, int direction, Segment segment) {
		duration = maxDuration = 70;
		moveSpeed = 3;
		coolDown = 95;
		baseDamage = 2;
		setDirection( direction, true );
		this.x = x; //(right ? x + 32 : x );
		this.y = y; //( up ? y - 14 : y );
		wi = ( left || right ? 5 : 14);
		hi = ( up || down ? 5 : 14);
		name = "Energy Beam";
		this.segment = segment;
		stopsPlayer = true;
	}
	public void update() {
		// Get all objects that will be in that area. 
		boolean areaClear = true;
		// Depending on the direction, we check where it will be going.
		if ( left )
			areaClear = isAreaClear( new Rectangle( (int) (x - moveSpeed), (int) y, wi, hi));
		if ( right )
			areaClear = isAreaClear( new Rectangle( (int) (x + moveSpeed), (int) y, wi, hi));
		if ( up )
			areaClear = isAreaClear( new Rectangle( (int) x, (int) (y - moveSpeed), wi, hi));
		if ( down )
			areaClear = isAreaClear( new Rectangle( (int) x, (int) (y + moveSpeed), wi, hi));
		
		if ( areaClear ) {
			if ( left ) {
				x -= moveSpeed;
				wi += (int) moveSpeed;
			}
			if ( right ) {
				wi += (int) moveSpeed;
			}
			if ( up ) {
				y -= moveSpeed;
				hi += (int) moveSpeed;
			}
			if ( down ) {
				hi += (int) moveSpeed;
			}
		} else if ( collisionRectangle == null ) {
			if ( left )
				collisionRectangle = new Rectangle( (int) (x - moveSpeed), (int) (y - moveSpeed * 2) , 15, 24);
			if ( right )
				collisionRectangle = new Rectangle( (int) x + wi, (int) (y - moveSpeed * 2) , 15, 24);
			if ( up )
				collisionRectangle = new Rectangle( (int) (x - moveSpeed * 2), (int) (y - moveSpeed) , 24, 15);
			if ( down )
				collisionRectangle = new Rectangle( (int) (x - moveSpeed * 2), (int) y + hi , 24, 15);
		}
		duration--;
		if ( duration < 1 ) remove = true;
	}
	
	private boolean isAreaClear( Rectangle rectangle ) {
		boolean areaClear = true;
		ArrayList<Actor> objectsInArea = new ArrayList<Actor>();
		objectsInArea = segment.objectsInArea( rectangle );
		
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
		
		return areaClear;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.YELLOW );
		g.fill( getRectangle() );
		if ( collisionRectangle != null ) g.fill( collisionRectangle );
	}

}
