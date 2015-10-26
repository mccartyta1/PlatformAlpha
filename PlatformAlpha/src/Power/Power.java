package Power;

import Segment.Segment;
import Actor.Actor;
import Actor.Player;

public abstract class Power extends Actor {
	
	// Constants
	public static final int NO_POWER = 0; // No powers are for 0.
	public static final int ENERGY_BLAST = 1; // Small, basic energy attack.
	public static final int FLIGHT = 2; // Self-Explanatory.
	public static final int BEAM_ATTACK = 3; // Yeah. Ka. Me. Ha. Me. Ha. Not stopped by walls.
	public static final int ICE_BLAST = 4; // Ice blast attack.
	public static final int ENERGY_BEAM = 5; // Beam attack, that is stopped by walls.
	public static final int RIFLE_SKILL = 6; // User uses a rifle, shooting a powerful bullet. 
	public static final int MIND_CONTROL = 7; // User takes control of an Enemy object, causing de-aggro and different actions.
	public static final int TIME_CONTROL = 8; // User causes time to slow by three times, not affecting them of course.





	
	// Statistic Variables
	protected int type, coolDown;
	protected double duration, maxDuration;
	protected String name;
	protected Segment segment;
	protected boolean stopsPlayer;
	protected boolean fromEnemy = false;
	protected boolean fromPlayer = false;

	public static Power getPower( Actor actor, int powerType, Segment segment ) {
		Power power = null;
		double px = 0;
		double py = 0;
		int pd = 1;
		if ( actor != null ) {
			px = actor.getX();
			py = actor.getY();
			pd = actor.getDirection();
		} 
		switch ( powerType ) {
		case NO_POWER: power = null; break;
		case ENERGY_BLAST: power = new PowerEnergyBlast( px, py + 10, pd, segment ); break;
		case FLIGHT: power = new PowerFlight( px, py + 10, pd, segment ); break;
		case BEAM_ATTACK: power = new PowerBeamAttack( px + 15, py + 15, pd, segment ); break;
		case ICE_BLAST: power = new PowerIceBlast( px, py + 10, pd, segment ); break;
		case ENERGY_BEAM: power = new PowerEnergyBeam( px + 14, py + 14, pd, segment ); break;
		case RIFLE_SKILL: power = new PowerRifleSkill( px + 14, py + 14, pd, segment ); break;
		case MIND_CONTROL: power = new PowerMindControl( px + 22, py + 14, pd, segment); break;
		case TIME_CONTROL: power = new PowerTimeControl( 1, 1, pd, segment); break;
		}
		return power;
	}
	public int getType() { return type; }
	public int getCoolDown() { return coolDown; }
	public double getSpeed() { return moveSpeed; }
	public double getDuration() { return duration; }
	public double getMaxDuration() { return maxDuration; }
	public double getDurationLeft() { return maxDuration - duration; }
	public String getName() { return name; }
	public boolean stopsPlayer() { return stopsPlayer; }
	public boolean fromEnemy() { return fromEnemy; }
	public void setFromEnemy( boolean b ) { fromEnemy = b; }
	public boolean fromPlayer() { return fromPlayer; }
	public void setFromPlayer( boolean b ) { fromPlayer = b; }


}
