package Actor;

import java.awt.Color;
import java.awt.Graphics2D;

public class Exit extends Wall {
	
	private int level;

	public void update() {
		;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor( new Color(255, 255, 255, 10) );
		g.fill( this.getRectangle() );
		;
		// Invisible yo
	}
	
	public Exit( double x, double y, int level ) {
		super( x, y );
		solid = false;
		this.level = level;
	}
	
	public Exit( double x, double y, int wi, int hi, int level ) {
		super( x, y, wi, hi );
		solid = false;
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

}
