package Power;

import java.awt.Color;
import java.awt.Graphics2D;
import Enemy.Enemy;

import Segment.Segment;

public class PowerIceBlast extends Power {


	public PowerIceBlast( double x, double y, int direction, Segment segment) {
		wi = 17;
		hi = 13;
		duration = maxDuration = 120;
		moveSpeed = 2.7;
		coolDown = 95;
		baseDamage = Enemy.FROZEN;
		this.x = x;
		this.y = y;
		setDirection( direction, true );
		name = "Ice Blast";
	}
	public void update() {
		move();
		x += dx;
		y += dy;
		duration--;
		if ( duration < 1 || dead ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.CYAN );
		g.fill( getRectangle() );
	}

}
