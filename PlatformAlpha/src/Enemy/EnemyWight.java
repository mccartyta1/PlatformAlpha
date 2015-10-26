package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;


import Segment.Segment;
import Power.Power;
public class EnemyWight extends Enemy {
	
	// If they are to fall, use gravity from Player.
	
	public EnemyWight( double x, double y, int direction, Segment currentSegment ) {
		hp = 120;
		wi = 28;
		hi = 31;
		moveSpeed = maxMoveSpeed = 1.8;
		gravity = .29;
		reactionTime = reaction = 140;
		baseDamage = 3;
		baseDefense = 5;
		aggro = true;
		this.x = x;
		this.y = y;
		this.currentSegment = currentSegment;
		setDirection( direction, true );
	}

	public void think() {
		int chance;
		chance = random.nextInt(100);
		
		if ( chance < 16 ) {
			// 10% chance to...
			up = true;
		}
		if ( chance > 10 && chance < 50 ) {
			// 75% chance to change direction
			right = !right;
			left = !left;
		}
		
		if ( chance > 50 && chance < 60 ) {
			// Roughly 10% chance
			creatingPower = Power.ENERGY_BLAST;
		}
		
		if ( chance > 80 && ( aggro || effect == MIND_CONTROL ) ) {
			// Roughly 20% chance to...
			creatingPower = Power.RIFLE_SKILL;
		}
		
	}

	public void update() {
		checkEffects();
		isFalling();
		moveEnemy();
		checkLocation();

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

	public void draw(Graphics2D g) {
		g.setColor( Color.PINK );
		g.fill( getRectangle() );
		g.setColor( Color.RED );
		g.drawString( Integer.toString(hp), (int) x + 2, (int) y - 8); 
		g.drawString( Integer.toString( effect ), (int) x + 8, (int) y - 16);
		drawEffects( g );
	}
	
	

}
