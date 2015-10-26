package Power;

import java.awt.Color;
import java.awt.Graphics2D;

import Segment.Segment;

public class PowerEnergyBlast extends Power {


	public PowerEnergyBlast( double x, double y, int direction, Segment segment) {
		wi = 18;
		hi = 14;
		duration = maxDuration = 70;
		moveSpeed = 3.1;
		coolDown = 75;
		baseDamage = 40;
		this.x = x;
		this.y = y;
		setDirection( direction, true );
		name = "Energy Blast";
	}
	public void update() {
		move();
		x += dx;
		y += dy;
		duration--;
		if ( duration < 1 || dead ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.YELLOW );
		g.fill( getRectangle() );
	}

}
