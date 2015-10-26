package Power;

import java.awt.Color;
import java.awt.Graphics2D;

import Controller.GamePanel;
import Segment.Segment;

public class PowerTimeControl extends Power {


	public PowerTimeControl( double x, double y, int direction, Segment segment) {
		wi = 1;
		hi = 1;
		duration = maxDuration = 120;
		moveSpeed = 0;
		coolDown = 900;
		baseDamage = 0;
		this.x = x;
		this.y = y;
		setDirection( direction, true );
		solid = false;
		name = "Time Control";
	}
	public void update() {
		move();
		//x += dx;
		//y += dy;
		duration--;
		if ( duration < 1 ) remove = true;
	}

	public void draw(Graphics2D g) {
		g.setColor( new Color( 0, 255, 255, 40) );
		g.fillRect( 0, 0, GamePanel.WI, GamePanel.HI );
		//g.setColor( new Color( 255, 255, 0, 40) );
		//g.fillRect( GamePanel.WI / 2 - 20, GamePanel.HI /2 - 20, GamePanel.WI / 2, GamePanel.HI / 2);
	}

}
