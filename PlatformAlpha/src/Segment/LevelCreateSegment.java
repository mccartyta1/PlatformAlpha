package Segment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Actor.*;
import Enemy.*;
import Controller.Manager;
import Controller.GamePanel;

public class LevelCreateSegment extends Segment {
	
	public static ArrayList<Actor> actors;
	public static Actor selectorActor;
	public static double selectorX = 0;
	public static double selectorY = 0;
	public static int selectedUnit = 0;
	public static double[] playerStartingPosition = { 0, 0 };
	public static final int NORMAL_WALL = 0;
	public static final int EXIT = 1;
	public static final int GRUNT = 2;
	public static final int CITIZEN = 3;
	public static final int CMS = 32; // Cursor Movement Speed
	
	public LevelCreateSegment( Manager m ) {
		this.m = m;
		actors = new ArrayList<Actor>();
	}
	
	
	public void update() {
		
	}

	
	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, GamePanel.WI, GamePanel.HI);
		g.setColor( Color.GREEN);
		g.fillRect((int) playerStartingPosition[0], (int) playerStartingPosition[1], 32, 32);
		for ( int i = 0; i < actors.size(); i++ ) {
			Actor actor = actors.get( i );
			actor.draw( g ); 
		}
		g.setColor(Color.YELLOW);
		g.drawRect((int) selectorX, (int) selectorY, 16, 16);
		g.setColor(Color.ORANGE);
		g.drawString(currentActor().getClass().getName(), (int) selectorX + 2, (int) selectorY + 2);
	}

	public Actor currentActor() {
		switch (selectedUnit) {
		case 0: return new Wall( selectorX, selectorY);
		case 1: return new Exit( selectorX, selectorY, 0); 
		case 2: return new EnemyGrunt( selectorX, selectorY, 0, this); 
		case 3: return new Citizen( selectorX, selectorY, 0, this); 
		case 4: return new Player(this);
		default: return null;
		}
	}
	
	public Actor actorAtPos( double x, double y ) {
		Actor actor = null;
		for ( int i = 0; i < actors.size(); i++ ) {
			if ( actors.get( i ).getX() == x && actors.get( i ).getY() == y ) {
				actor = actors.get( i );
			}
		}
		return actor;
	}
	
	public void keyPressed(int key) {
		
	}

	
	public void keyReleased(int key) {
		Actor actor;
		if ( key == KeyEvent.VK_RIGHT) {
			selectorX += CMS;
		}
		if ( key == KeyEvent.VK_LEFT) {
			selectorX -= CMS;
		}
		if ( key == KeyEvent.VK_UP) {
			selectorY -= CMS;
		}
		if ( key == KeyEvent.VK_DOWN) {
			selectorY += CMS;
		}
		if ( key == KeyEvent.VK_SPACE) {
			actor = actorAtPos( selectorX, selectorY );
			if ( actor != null ) {
				actors.remove( actor );
			}
			switch (selectedUnit) {
			case 0: actors.add( new Wall( selectorX, selectorY)); break;
			case 1: actors.add( new Exit( selectorX, selectorY, 0)); break;
			case 2: actors.add( new EnemyGrunt( selectorX, selectorY, 0, this) ); break;
			case 3: actors.add( new Citizen( selectorX, selectorY, 0, this)); break;
			case 4: playerStartingPosition[0] = selectorX; playerStartingPosition[1] = selectorY; break;
			}
		}
		if ( key == KeyEvent.VK_ALT ) {
			actor = actorAtPos( selectorX, selectorY );
			if ( actor != null ) {
				actors.remove( actor );
			}
		}
		if ( key == KeyEvent.VK_0 ) {
			selectedUnit = 0;
		}
		if ( key == KeyEvent.VK_1 ) {
			selectedUnit = 1;
		}
		if ( key == KeyEvent.VK_2 ) {
			selectedUnit = 2;
		}
		if ( key == KeyEvent.VK_3 ) {
			selectedUnit = 3;
		}
		if ( key == KeyEvent.VK_4 ) {
			selectedUnit = 4;
		}
		if ( key == KeyEvent.VK_ENTER) {
			int x;
			int y;
			System.out.println("setPlayerPosition( " + playerStartingPosition[0] + ", " + playerStartingPosition[1] + " );");
			System.out.println("String[] messages; messages = new String[1]; messages[0] = \"...\";");
			for ( int i = 0; i < actors.size(); i++ ) {
				actor = actors.get( i );
				x = (int) actor.getX();
				y = (int) actor.getY();
				if ( actor instanceof Wall ) {
					System.out.println("createWall( " + x + "," + y + ");");
				}
				if ( actor instanceof Exit ) {
					System.out.println("createExit( " + x + "," + y + ", LEVEL_OUTPOST);" );
				}
				if ( actor instanceof EnemyGrunt ) {
					System.out.println("createEnemyGrunt( " + x + "," + y + ", RIGHT);" );
				}
				if ( actor instanceof Citizen ) {
					System.out.println("createCitizen( " + x + "," + y + ", RIGHT, messages );" );
				}
			}
		}
	}

	
	public ArrayList<Actor> objectsInArea(Rectangle area) {
		return null;
	}
	
	public ArrayList<Actor> objectsInAreaForPlayer(Rectangle area) {
		return null;
	}


}
