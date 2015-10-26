package Power;

import java.awt.Color;
import java.awt.Graphics2D;

import Segment.Segment;

public class PowerRifleSkill extends Power {
	
	// The rifle skill allows the person to use a gun. 

	double originX;
	double originY;
	int rifleHi, rifleWi;
	
	public PowerRifleSkill( double x, double y, int direction, Segment segment) {
		wi = 6;
		hi = 6;
		rifleHi = 7;
		rifleWi = 16;
		duration = maxDuration = 140;
		moveSpeed = 3.8;
		coolDown = 180;
		baseDamage = 60;
		this.x = originX = x;
		this.y = originY = y;
		setDirection( direction, true );
		name = "Rifle Skill";
	}
	public void update() {
		move();
		x += dx;
		y += dy;
		duration--;
		if ( duration < 1 || dead ) remove = true;
	}

	public void draw(Graphics2D g) {
		// Rifle
		if ( duration > 100 ) {
			g.setColor( Color.GRAY );
			g.fillRect((int) originX, (int) originY, rifleWi, rifleHi);
		}
		// Bullet
		g.setColor( Color.RED );
		g.fill( getRectangle() );
	}

}
