package Display;

import java.awt.Color;
import java.awt.Graphics2D;

import Actor.Player;
import Power.Power;

public class HUD {
	
	private Player player;
	private Power currentPower;
	
	public HUD( Player player ) {
		this.player = player;
	}
	
	
	public void draw( Graphics2D g ) {
		currentPower = Power.getPower( player, player.getCurrentPower(), null );
		g.setColor( Color.RED );
		g.drawString( Integer.toString(player.getHP()) , 15, 15 );
		if ( !player.isCoolingDown() ) g.setColor( Color.CYAN );
		if ( currentPower != null ) g.drawString( currentPower.getName() , 60, 15 );

	}
}
