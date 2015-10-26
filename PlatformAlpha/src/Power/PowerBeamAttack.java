package Power;

import java.awt.Color;
import java.awt.Graphics2D;

import Segment.Segment;
import Controller.GamePanel;

public class PowerBeamAttack extends Power {


	public PowerBeamAttack( double x, double y, int direction, Segment segment) {
		wi = 0;
		hi = 0;
		duration = maxDuration = 120;
		moveSpeed = 1;
		coolDown = 230;
		baseDamage = 1;
		this.x = x;
		this.y = y;
		this.segment = segment;
		setDirection( direction, true );
		name = "Beam Attack";
	}
	public void update() {
		move();
		/*
		 * Up: Beam goes from top of player to top of room. y is 0, hi is player's y.
		 * Down: Beam goes from bottom of player to bottom of room. y is player's y, hi is GP.HI - y
		 * Right: Beam goes from right of player to right of room. x is player's x, wi is gp.wi - x
		 * Left: Beam goes from left of player to left of room. x is 0, wi is player's x;
		 */
		
		if (up) {
			y = 0;
			hi = (int) segment.getPlayerPosition()[1];
		}
		if (down) {
			y = segment.getPlayerPosition()[1] + 32;
			hi = (int) (GamePanel.HI - y);
		}
		if (right) {
			x = segment.getPlayerPosition()[0] + 32;
			wi = (int) (GamePanel.WI - x);
		}
		if (left) {
			x = 0;
			wi = (int) segment.getPlayerPosition()[0] ;
		}
		
		if ( up || down ) {
			x = segment.getPlayerPosition()[0] + 7;
			wi += moveSpeed;
			if ( wi > 20 ) wi = 18;
		}
		if ( left || right ) {
			y = segment.getPlayerPosition()[1] + 7;
			hi += moveSpeed;
			if ( hi > 20 ) hi = 18;;
		}
		duration--;
		if ( duration < 1 ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.YELLOW );
		g.fill( getRectangle() );
	}

}
