package Power;

import java.awt.Color;
import java.awt.Graphics2D;

import Segment.Segment;

public class PowerFlight extends Power {


	public PowerFlight( double x, double y, int direction, Segment segment) {
		wi = 32;
		hi = 6;
		duration = maxDuration = 80;
		moveSpeed = -.4;
		coolDown = 235;
		baseDamage = 0;
		this.x = x;
		this.y = y;
		setDirection( direction, true );
		solid = false;
		name = "Flight";
	}
	public void update() {
		move();
		//x += dx;
		y += dy;
		duration--;
		if ( duration < 1 ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.BLUE );
		g.fill( getRectangle() );
	}

}
