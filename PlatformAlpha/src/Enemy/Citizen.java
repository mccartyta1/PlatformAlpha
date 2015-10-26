package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;

import Segment.Segment;
import Actor.Actor;
import Display.TextBox;
import Power.Power;

public class Citizen extends Enemy {
	
	private String[] messages;

	public Citizen( double x, double y, int direction, Segment currentSegment ) {
		hp = 20;
		wi = 30;
		hi = 32;
		moveSpeed = maxMoveSpeed = 0.6;
		gravity = .32;
		reactionTime = reaction = 200;
		baseDamage = 1;
		baseDefense = 1;
		this.x = x;
		this.y = y;
		this.currentSegment = currentSegment;
		setDirection( direction, true );
	}
	
	public void update() {
		checkEffects();
		isFalling();
		moveEnemy();
		if ( !TextBox.isMessageDisplaying() ) checkLocation();

		// Thinking
		reaction--;
		if ( reaction < 1 && !dead) {
			think();
			reaction = reactionTime;
		}
		
		if ( dead ) {
			hi -= 1;
			y -= 1;
			if ( hi < 1 )
				remove = true;
		}
	}

	public String[] getMessages() { return messages; }
	public void setMessages( String[] messages ) { this.messages = messages; }
	
	public void draw(Graphics2D g) {
		g.setColor( Color.WHITE );
		g.fill( getRectangle() );
		g.setColor( Color.RED );
		g.drawString( Integer.toString(hp), (int) x + 2, (int) y - 8); 
		g.drawString( Integer.toString( effect ), (int) x + 8, (int) y - 16);
		drawEffects( g );
	}

	@Override
	public void think() {
		int chance;
		chance = random.nextInt(100);
		
		if ( chance > 0 && chance < 96 ) {
			// Large chance to change direction
			right = !right;
			left = !left;
		}
		
		if ( chance > 98 && aggro ) {
			creatingPower = Power.RIFLE_SKILL;
		}
		
		if ( chance > 70 && effect == MIND_CONTROL ) {
			creatingPower = Power.RIFLE_SKILL;
		}
	}

}
