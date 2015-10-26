package Actor;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Actor {

	public Wall(double x, double y) {
		this.x = x;
		this.y = y;
		wi = hi = 32;
		moveSpeed = 0;
		solid  = true;
	}
	
	public Wall( double x, double y, int wi, int hi ) {
		this.x = x;
		this.y = y;
		this.wi = wi;
		this.hi = hi;
		moveSpeed = 0;
		solid  = true;
	}
	public void update() {
		;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fill(getRectangle());
	}

}
