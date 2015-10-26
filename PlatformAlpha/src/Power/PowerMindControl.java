package Power;

import java.awt.Color;
import java.awt.Graphics2D;
import Enemy.Enemy;

import Segment.Segment;

public class PowerMindControl extends Power {


	public PowerMindControl( double x, double y, int direction, Segment segment) {
		wi = 21;
		hi = 9;
		duration = maxDuration = 100;
		moveSpeed = 1.6;
		coolDown = 200;
		baseDamage = Enemy.MIND_CONTROL;
		this.x = x;
		this.y = y;
		setDirection( direction, true );
		name = "Mind Control";
	}
	public void update() {
		move();
		x += dx;
		y += dy;
		duration--;
		if ( duration < 1 || dead ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( Color.MAGENTA );
		g.fill( getRectangle() );
	}

}
