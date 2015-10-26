package Segment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Actor.*;
import Controller.GamePanel;
import Controller.Manager;
import Display.HUD;
import Display.TextBox;
import Power.*;
import Enemy.*;

public class SBaseLevel extends Segment {
	
	/**
	 * TODO: Look into graphics.
	 * TODO: Possibly make this class abstract. The RC handles what we though the segment would though.
	 * TODO: 
	 * TODO:
	 */
	private ArrayList<Actor> walls;
	private ArrayList<Actor> powers;
	private ArrayList<Actor> enemies;
	private HUD hud;
	private RoomConstructor rc;
	
	// Area Effect
	private int slowTime = 0; // Slow time starts at slowTimeStart, giving the player three updates per other updates.
	private static int slowTimeStart = 5; // The value slowTime starts at.

	public SBaseLevel( Manager m ) {
		powers = new ArrayList<Actor>();
		enemies = new ArrayList<Actor>();
		// Debug Start
		rc = new RoomConstructor( this );
		rc.constructLevel( RoomConstructor.WORLD_ENTRY );
		walls = rc.getWalls();
		enemies = rc.getEnemies();
		player = new Player( this );
		player.setX( rc.getPlayerStartingPosition()[0] );
		player.setY( rc.getPlayerStartingPosition()[1] );
		hud = new HUD( player );
		// ****
		this.m = m;
	}
	public void update() {
		// If you want to do specific functions for a set of actors,
		// add instanceof check.
		// updateActors( enemies );
		
		// Updates
		player.update();
		if ( slowTime == 0 ) {
			updateActors( powers );
			updateActors( enemies );
			
			// Check for Collisions
			checkCollisionsForActors( enemies, powers );
			checkCollisionsForActors( walls, powers );
		} else slowTime--;
		
		// Custom Player Collisons, mainly handle in Player class.
		
	}

	public ArrayList<Actor> objectsInArea(Rectangle area) {
		ArrayList<Actor> objectsAtPoint = new ArrayList<Actor>();
		Actor actor;
		for ( int i = 0; i < enemies.size(); i++ ) {
			actor = enemies.get ( i );
			if ( actor.getRectangle().intersects( area ) ) {
				if ( actor instanceof Citizen )
					objectsAtPoint.add( actor );
			}
		}
		for ( int i = 0; i < walls.size(); i++ ) {
			actor = walls.get( i );
			if ( actor.getRectangle().intersects( area )) {
				if ( actor instanceof Exit) {
					// Next room
//					rc.constructLevel( ( (Exit) actor).getLevel() );
//					walls = rc.getWalls();
//					enemies = rc.getEnemies();
//					player.setX( rc.getPlayerStartingPosition()[0] );
//					player.setY( rc.getPlayerStartingPosition()[1] );
				} else
					objectsAtPoint.add( actor );
			}
		}
		
		for ( int i = 0; i < powers.size(); i++ ) {
			actor = powers.get( i );
			// Flight. Add it to the objects so the player sees it.
			if ( ((Power) actor).stopsPlayer() ) objectsAtPoint.add( actor );
			if ( actor instanceof PowerFlight ) objectsAtPoint.add( actor );
		}
		return objectsAtPoint;
	}
	
	public ArrayList<Actor> objectsInAreaForPlayer(Rectangle area) {
		ArrayList<Actor> objectsAtPoint = new ArrayList<Actor>();
		Actor actor;
		for ( int i = 0; i < enemies.size(); i++ ) {
			actor = enemies.get ( i );
			if ( actor.getRectangle().intersects( area ) ) {
				if ( actor instanceof Citizen )
					objectsAtPoint.add( actor );
			}
		}
		for ( int i = 0; i < walls.size(); i++ ) {
			actor = walls.get( i );
			if ( actor.getRectangle().intersects( area )) {
				if ( actor instanceof Exit) {
					// Next room
					rc.constructLevel( ( (Exit) actor).getLevel() );
					walls = rc.getWalls();
					enemies = rc.getEnemies();
					player.setX( rc.getPlayerStartingPosition()[0] );
					player.setY( rc.getPlayerStartingPosition()[1] );
				} else
					objectsAtPoint.add( actor );
			}
		}
		
		for ( int i = 0; i < powers.size(); i++ ) {
			actor = powers.get( i );
			// Flight. Add it to the objects so the player sees it.
			if ( ((Power) actor).stopsPlayer() ) objectsAtPoint.add( actor );
			if ( actor instanceof PowerFlight ) objectsAtPoint.add( actor );
			if ( actor.getRectangle().intersects( area )) objectsAtPoint.add( actor );
		}
		return objectsAtPoint;
	}
	
	public void draw(Graphics2D g) {
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WI, GamePanel.HI);
		
		// Actor Drawing
		drawActors( walls, g );
		drawActors( enemies, g );
		
		// Player Drawing
		player.draw( g );
		
		// Power Drawing ( Needs to be over the player )
		drawActors( powers, g );
		
		// HUD
		hud.draw( g );
		TextBox.draw( g );
	}
	
	private void drawActors( ArrayList<Actor> actors, Graphics2D g ) {
		Actor actor;
		for ( int i = 0; i < actors.size(); i++ ) {
			actor = actors.get( i );
			actor.draw( g );
		}
	}
	private void updateActors( ArrayList<Actor> actors ) {
		Actor actor;
		for ( int i = 0; i < actors.size(); i++ ) {
			actor = actors.get( i );
			actor.update();
			if ( actor instanceof Enemy && ((Enemy) actor).isCreatingPower() != 0 ) {
				Power power = Power.getPower( actor, ((Enemy) actor).isCreatingPower(), this );
				if ( ((Enemy) actor).getAggro() ) power.setFromEnemy( true ); // If aggro, set as fromEnemy. Else, no. 
				powers.add( power );
				((Enemy) actor).setCreatingPower( 0 );
			}
			if ( actor instanceof PowerTimeControl ) {
				slowTime = slowTimeStart;
			}
			if ( actor.shouldRemove() ) {
				actors.remove( actor );
				i--;
			}
		}
	}
	
	private void checkCollisionsForActors( ArrayList<Actor> actors1, ArrayList<Actor> actors2 ) {
		Actor actor1;
		Actor actor2;
		boolean hit = false;
		
		for ( int i = 0; i < actors1.size(); i++ )
		{
			actor1 = actors1.get( i );
			for ( int j = 0; j < actors2.size(); j++ )
			{
				actor2 = actors2.get( j );
				if ( (actor1 instanceof Power || actor1 instanceof Enemy) && (actor2 instanceof Power || actor2 instanceof Enemy) ) {
					// If one is a power and the other is an enemy, we need to see if it:s a good power or not.
					// I could just make it so power is always first actors and enemies are always second, but these checks should help more
					// in the future.
					
					// Power from Player
					if ( ( actor1 instanceof Power && ((Power) actor1).fromPlayer() ) || ( actor2 instanceof Power && ((Power) actor2).fromPlayer()) ) {
						hit = true;
					}
					// Power from angry Enemy
					else if ( ( actor1 instanceof Power && ((Power) actor1).fromEnemy() ) || ( actor2 instanceof Power && ((Power) actor2).fromEnemy()) ) {
						if ( ( actor1 instanceof Enemy && !((Enemy) actor1).getAggro() ) || ( actor2 instanceof Enemy && !((Enemy) actor2).getAggro()) ) {
							// No Aggro
							hit = true;
						}
					}
					// Power from non-angry Enemy and the other enemy is aggro
					else if ( ( actor1 instanceof Enemy && ((Enemy) actor1).getAggro() ) || ( actor2 instanceof Enemy && ((Enemy) actor2).getAggro()) ) {
						hit = true;
					}
				}
				else {
					hit = true;
				}
				
				if ( hit && ( actor1.getRectangle().intersects( actor2.getRectangle() ) ) ) {
					actor1.hit( actor2.getDamage() );
					actor2.hit( actor1.getDamage() );
				}
				
			}
		}
	}
	


	public void keyPressed(int key) {
		if (key == KeyEvent.VK_1) {
			player.setCurrentPower( 0 );
		}
		if (key == KeyEvent.VK_2) {
			player.setCurrentPower( 1 );
		}
		if (key == KeyEvent.VK_3) {
			player.setCurrentPower( 2 );
		}
		if (key == KeyEvent.VK_UP) {
			player.setDirection(player.UP, true);
		}
		if (key == KeyEvent.VK_DOWN) {
			player.setDirection(player.DOWN, true);
		}
		if (key == KeyEvent.VK_RIGHT) {
			player.setDirection(player.RIGHT, true);
		}
		if (key == KeyEvent.VK_LEFT) {
			player.setDirection(player.LEFT, true);
		}
		
	}

	public void keyReleased(int key) {
		if (key == KeyEvent.VK_1) {
			player.setCurrentPower( 0 );
		}
		if (key == KeyEvent.VK_2) {
			player.setCurrentPower( 1 );
		}
		if (key == KeyEvent.VK_3) {
			player.setCurrentPower( 2 );
		}
		if (key == KeyEvent.VK_DOWN) {
			player.setDirection(player.DOWN, false);
		}
		if (key == KeyEvent.VK_RIGHT) {
			player.setDirection(player.RIGHT, false);
		}
		if (key == KeyEvent.VK_LEFT) {
			player.setDirection(player.LEFT, false);
		}
		if (key == KeyEvent.VK_UP) {
			player.setDirection(player.UP, false);
		}
		if (key == KeyEvent.VK_V) {
			TextBox.nextMessage();
		}
		if (key == KeyEvent.VK_SPACE && !player.isCoolingDown() && !TextBox.isMessageDisplaying()) {
			if ( !TextBox.isMessageDisplaying() ) {
				// Create power thing
				Power power = Power.getPower( player, player.getCurrentPower(), this );
				if ( power != null ) {
					power.setFromPlayer( true );
					powers.add( power );
					player.coolDown( power.getCoolDown() );
				}
			} else {
				TextBox.nextMessage();
			}
		}
	}

}
