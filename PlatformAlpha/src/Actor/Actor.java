package Actor;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Display.Animation;

// Our basic object. But I didn't call it Object.
public abstract class Actor {
	
	protected double x, y, dx, dy, moveSpeed;
	protected int hp, type, wi, hi, direction, baseDamage, baseDefense;
	protected boolean left, right, up, down, solid, remove;
	protected BufferedImage[] sprites;
	protected Animation animation;
	public final int LEFT = 0;
	public final int RIGHT = 1;
	public final int UP = 2;
	public final int DOWN = 3;
	protected boolean dead = false;
	
	
	public abstract void update();
	
	public abstract void draw(java.awt.Graphics2D g);
	
	public double getX() { return x; }
	public double getY() { return y; }
	public int getHP() { return hp; }
	public int getDirection() { return direction; }
	public int getDamage() { return baseDamage; }
	public boolean shouldRemove() { return remove; }
	public void setHP(int nhp) { hp = nhp; }
	public void setX(double nx) { x = nx; }
	public void setY(double ny) { y = ny; }
	public void setWidth(int nwi) { wi = nwi; }
	public void setHeight (int nhi) { hi = nhi; }
	protected void move() {
		// Deciding dx and dy values based on key presses.
		if (left) {
			dx = -moveSpeed;
		} else if (right) {
			dx = moveSpeed;
		} else dx = 0;
		if (up) {
			dy = -moveSpeed;
		} else if (down) {
			dy = moveSpeed;
		} else dy = 0;
	}
	
	public void setDirection(int i, boolean b) {
		direction = i;
		if (i == LEFT) {
			left = b;
		}
		if (i == RIGHT) {
			right = b;
		}
		if (i == UP) {
			up = b;
		}
		if (i == DOWN) {
			down = b;
		}
	}
	
	public void hit(int damage) {
		int actualDamage = (damage * 100) / ( 100 - baseDefense );
		if ( actualDamage > 0 ) hp -= actualDamage;
		
		if (hp < 1) {
			hp = 0;
			dead = true;
		}
	}
	
	
	
	public boolean isSolid() { return solid; }
	
	public Rectangle getRectangle() { 
		int width = Math.abs( wi );
		int height = Math.abs( hi );
		return new Rectangle((int)x, (int)y, width, height); 
	}
	
	protected void drawWithAnimation( java.awt.Graphics2D g ) {
		if (right) g.drawImage( animation.getImage(), (int)(x), 
				(int)(y), null);
		else if (left) g.drawImage( animation.getImage(), (int)(x) + wi, 
				(int)(y), -wi, hi, null);
		else g.drawImage( animation.getImage(), (int)(x), 
				(int)(y), null);
	}

}
