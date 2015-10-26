package Actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Display.Animation;
import Display.TextBox;
import Power.*;
import Segment.MenuSegment;
import Segment.Segment;
import Enemy.Citizen;
import Enemy.Enemy;

public class Player extends Actor {
	
	// Power Variables
	private int[] heldPowers;
	private int[] coolDown;
	private int currentPower;
	
	// Falling Variables
	private boolean falling = false;
	private boolean noGravity = false;
	private boolean onGround = true;
	private double gravity = .19;
	
	// Current Segment
	private Segment currentSegment;
	
	public Player( Segment currentSegment ) {
		x = 32;
		y = 180;
		hp = 100;
		wi = 32;
		hi = 32;
		moveSpeed = 1.35;
		solid = false;
		this.currentSegment = currentSegment;
		heldPowers = new int[3];
		coolDown = new int[heldPowers.length];
		currentPower = 0;
		heldPowers = MenuSegment.chosenPowers;
		baseDamage = 1;
		baseDefense = 10;
		// ********
		// Debug
		//heldPowers[0] = Power.ENERGY_BLAST;
		//heldPowers[1] = Power.FLIGHT;
		//heldPowers[2] = Power.ENERGY_BEAM;
		// ********
		
		//**********
		// Animation
		animation = new Animation();
		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/player.gif")
			);
			sprites = new BufferedImage[2];
			sprites[0] = spritesheet;
			sprites[1] = spritesheet;
//			for(int i = 0; i < sprites.length; i++) {
//				sprites[i] = spritesheet.getSubimage(i * wi, 0, wi, hi);
//			}
			animation.setFrames(sprites);
			animation.setDelay(1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//
	}
	public void update() {
		isFalling();
		movePlayer();
		checkLocation();
		animation.update();
		
		// Reduce cooldowns
		for ( int i = 0; i < coolDown.length; i++ )
			if (coolDown[i] > 0) coolDown[i]--;
	}
	
	private void movePlayer() {
		if (left) {
			dx = -moveSpeed;
		} else if (right) {
			dx = moveSpeed;
		} else dx = 0;
		
		if (falling) {
			up = false;
			dy += gravity;
			if ( dy > 5.0 )
				dy = 5.0;
			
		} else if (up) {
			
			// up == jumping
			up = false;
			dy = -moveSpeed * 5;
			falling = true;
			onGround = false;
			
		}
	}
	
	private void isFalling() {
		Rectangle rectangleBelowPlayer = new Rectangle( (int) x, (int) y + 1, wi, hi );
		ArrayList<Actor> objectsInArea;
		Actor object;
		Boolean areaClear = true;
		noGravity = false;
		objectsInArea = currentSegment.objectsInAreaForPlayer( rectangleBelowPlayer );
		
		for ( int i = 0; i < objectsInArea.size(); i++ ) 
		{
			object = objectsInArea.get( i );
			// Check if there are any powers that affect falling.
			if ( object instanceof PowerFlight )
				noGravity = true;
			if ( object.isSolid() ) 
			{
				// This is probably where to add the "hit floor" type stuff
				areaClear = false;
				break;
			} else if ( object instanceof Exit ) {
				// Go to different room
			}
		}
		
		if ( areaClear ) {
			falling = true;
		}
		if ( noGravity )
			falling = false;
	}
	private void checkLocation() {
		double nx = x + dx;
		double ny = y + dy;
		boolean areaClear = true;
		boolean messageDisplay = false;
		ArrayList<Actor> objectsInArea;
		// Get all objects that will be in that area. 
		TextBox.setMessageDisplaying( false );
		objectsInArea = currentSegment.objectsInAreaForPlayer( new Rectangle( (int) nx , (int) ny , wi , hi ));
		
		// Check if any of the mare solid.
		for ( int i = 0; i < objectsInArea.size(); i++ ) 
		{
			Actor object = objectsInArea.get( i );
			// In case we're talking to a Citizen.
			if ( object instanceof Citizen && !((Enemy) object).getAggro() ) {
				messageDisplay = true;
				TextBox.setMessagArray( ( (Citizen) object ).getMessages() );
			}
			
			if ( object instanceof Enemy && ((Enemy) object).getAggro() ) {
				// Collided with an enemy.
				this.hit( object.getDamage() );
			}
			if ( object instanceof Power && ((Power) object).fromEnemy() ) {
				// Collided with an enemy's power
				this.hit( object.getDamage());
				object.hit( this.getDamage() );
				System.out.println( hp );
			}
		    if ( object.isSolid() || (object instanceof Power && !(object instanceof PowerFlight))  ) 
			{
				// This is probably where to add the "hit floor" type stuff
				areaClear = false;
				break;
			}
		}
		if ( messageDisplay ){
			TextBox.setMessageDisplaying(  true );
		} else {
			TextBox.setCurrentMessage( 0 );
		}
		if ( areaClear ) {
			x = nx;
			y = ny;
		} else {
			falling = false;
			onGround = true;
			dy = 0;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.drawString( Integer.toString(hp), 40, 40);
		try {
			drawWithAnimation( g );
		} catch ( Exception e) {
			e.printStackTrace();
			g.setColor(Color.gray);
			g.fill(getRectangle());
		}
	}
	
	public int getCurrentPower() {
		return heldPowers[currentPower];
	}
	
	public void setCurrentPower( int i ) {
		if (heldPowers[ i ] != 0 ) currentPower = i;
	}
	
	public boolean isCoolingDown() {
		return coolDown[currentPower] != 0;
	}
	
	public void coolDown( int time ) {
		coolDown[currentPower] = time;
	}

}
