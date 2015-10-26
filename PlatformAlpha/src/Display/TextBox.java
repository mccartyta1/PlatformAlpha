package Display;

import java.awt.Color;
import java.awt.Graphics2D;

import Controller.GamePanel;

public class TextBox {
	
	public static final int TEXTBOX_X = 96;
	public static final int TEXTBOX_Y = GamePanel.HI - 96;
	public static final int TEXTBOX_WI = GamePanel.WI - ( 2 * TEXTBOX_X );
	public static final int TEXTBOX_HI = 96;
	private static String[] messageArray;
	private static int currentMessage;
	private static boolean messageDisplaying;
	
	public static void setMessagArray( String[] messages ) {
		messageArray = messages;
	}
	
	public static void setCurrentMessage( int current ) {
		currentMessage = current;
	}
	
	public static void nextMessage() {
		if ( currentMessage != messageArray.length - 1) {
			currentMessage++;
		}
	}
	
	public static boolean isMessageDisplaying() { return messageDisplaying; }
	public static void setMessageDisplaying( boolean b) { messageDisplaying = b; }
	
	public static void draw( Graphics2D g ) {
		if ( messageDisplaying ) {
			g.setColor( Color.BLUE );
			g.fillRect( TEXTBOX_X, TEXTBOX_Y, TEXTBOX_WI, TEXTBOX_HI);
			g.setColor( Color.WHITE );
			g.drawString( messageArray[currentMessage], TEXTBOX_X + 16, TEXTBOX_Y + 16);
		}
		// Draw message and rectangle
	}

}
