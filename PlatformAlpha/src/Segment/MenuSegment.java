package Segment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Actor.Actor;
import Controller.*;
import Power.Power;

public class MenuSegment extends Segment {
	
	// Goal of MenuSegment is to provide a menu base. 
	
	// Options within the Menu
	private String[] menuOptions = {"New Game","Exit Game"};
	public static int[] chosenPowers = { 0, 0, 0};
	private int currentChoice, state;
	private int progress;
	
	public MenuSegment( Manager m ) {
		this.m = m;
		currentChoice = 0;
		state = 0;
	}

	@Override
	public void update() {
		if ( progress == 4) {
			m.setState( Manager.LEVEL_CREATOR );
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WI, GamePanel.HI);
		if (state == 0 ) {
				for ( int i = 0; i < menuOptions.length; i++ ) {
				g.setColor(Color.BLACK);
				g.drawString( menuOptions[i], 80 + i * 4, 60 + i * 30);
				
				if ( currentChoice == i ) {
					g.setColor(Color.GRAY);
					g.drawString( menuOptions[i], 82 + i * 4, 62 + i * 30);
				}
			}
		} else if ( state == 1) {
			g.setColor(Color.BLACK);
			g.drawString("Character Creation", 100, 100);
			for ( int i = 0; i < chosenPowers.length; i++ ) {
				g.setColor(Color.BLACK);
				g.drawString("Power " + (i + 1), 400, 150 + i * 100);
				Power power = Power.getPower(null, chosenPowers[i], this);
				if ( power == null ) {
					g.drawString( "No Power", 400, 190 + i * 100);
				} else {
					g.drawString( power.getName(), 400, 190 + i * 100);
				}
				
				if ( currentChoice == i) {
					g.setColor(Color.GRAY);
					if ( power == null ) {
						g.drawString( "No Power", 402, 192 + i * 100);
					} else {
						g.drawString( power.getName(), 402, 192 + i * 100);
					}
				}
			}
			
			g.setColor(Color.BLACK);
			g.drawString("Done", 450, 420);
			if ( currentChoice == chosenPowers.length) {
				g.setColor(Color.GRAY);
				g.drawString("Done", 452, 422);
			}
		}
	}

	public void keyPressed(int key) {
		;
	}

	@Override
	public void keyReleased(int key) {
		if (state == 0) {
			if ( key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				currentChoice++;
				if ( currentChoice >= menuOptions.length) {
					currentChoice = menuOptions.length - 1;
				}
				if ( progress < 4 && progress > 1) progress++;
				else progress = 0;
			}
			
			if ( key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				currentChoice--;
				if ( currentChoice < 0 ) {
					currentChoice = 0;
				}
				if ( progress < 2) progress++;
				else progress = 0;
			}
			
			if ( key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER ) {
				// Go to following thing
				if ( menuOptions[currentChoice].equalsIgnoreCase("New Game") ) {
					// Move on. 
					// Menu -> Character Creation -> Game
					state = 1;
				}
				if ( menuOptions[currentChoice].equalsIgnoreCase("Exit Game") ) {
					System.exit(0);
				}
			}
		} else if ( state == 1) {
			if ( key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				currentChoice++;
				if ( currentChoice >= chosenPowers.length + 1) {
					currentChoice = chosenPowers.length;
				}
			}
			
			if ( key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				currentChoice--;
				if ( currentChoice < 0 ) {
					currentChoice = 0;
				}
			}
			
			if ( key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				chosenPowers[currentChoice] = chosenPowers[currentChoice] + 1;
				if ( Power.getPower(null, chosenPowers[currentChoice], this) == null) {
					chosenPowers[currentChoice] = chosenPowers[currentChoice] - 1;
				}
			}
			
			if ( key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				chosenPowers[currentChoice] = chosenPowers[currentChoice] - 1;
				if ( chosenPowers[currentChoice] < 0 ) {
					chosenPowers[currentChoice] = 0;
				}
			}
			
			if ( key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER ) {
				// Go to following thing
				if ( currentChoice == chosenPowers.length ) {
					m.setState(Manager.LEVEL);
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
