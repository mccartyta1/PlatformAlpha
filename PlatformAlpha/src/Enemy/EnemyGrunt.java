package Enemy;

import java.awt.Color;
import java.awt.Graphics2D;


import Segment.Segment;
import Power.Power;
public class EnemyGrunt extends Enemy {
	
	// If they are to fall, use gravity from Player.
	
	public EnemyGrunt( double x, double y, int direction, Segment currentSegment ) {
		hp = 50;
		wi = 30;
		hi = 32;
		moveSpeed = maxMoveSpeed = 1.3;
		gravity = .32;
		reactionTime = reaction = 200;
		baseDamage = 2;
		baseDefense = 0;
		aggro = true;
		this.x = x;
		this.y = y;
		this.currentSegment = currentSegment;
		setDirection( direction, true );
	}

	public void think() {
		int chance;
		chance = random.nextInt(100);
		
		if ( chance < 10 ) {
			// 10% chance to...
			up = true;
		}
		if ( chance > 10 && chance < 85 ) {
			// 75% chance to change direction
			right = !right;
			left = !left;
		}
		
		if ( chance > 90 && ( aggro || effect == MIND_CONTROL ) ) {
			// Roughly 15% chance to...
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
		g.setColor( Color.RED );
		g.fill( getRectangle() );
		g.setColor( Color.RED );
		g.drawString( Integer.toString(hp), (int) x + 2, (int) y - 8); 
		g.drawString( Integer.toString( effect ), (int) x + 8, (int) y - 16);
		drawEffects( g );
	}
	
	

}
