package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.Vector;
import engine.window.State;

public class Game extends State{

	Morceguinho morceguinho;
	
	GUI gui = new GUI();

	int x = 50, y = 320, w = 700, h = 150;
	int cx = 400, cy = 400, cw, ch;
 
	Color backGreen = new Color(34, 177, 76);
	

	public void init() {
		morceguinho = new Morceguinho(new Vector((800 - (89*3))/2, 50));
		
	}


	public void update(float delta) {
		morceguinho.update(delta);

		gui.update(delta);
		
		if(GUI.battle){
			w = 300;
			x = 250;
			h = 200;
		}
		else{
			x = 50;
			w = 700;
			h = 150;
		}
		
		if(cx < x) cx+=10;
		if(cx > x) cx-=10;
		
		if(cy < y) cy+=10;
		if(cy > y) cy-=10;
		
		if(cw < w) cw+=20;
		if(cw > w) cw-=20;
		
		if(ch < h) ch+=20;
		if(ch > h) ch-=20;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(backGreen);
		for(int i = 0; i < 6; i ++){
			for(int j = 0; j < 2; j ++){
				g.drawRect(40 + i*120, 50 + j*120, 120, 120);
			}
		}
		g.setColor(Color.white);
		morceguinho.draw(g);

		gui.draw(g);
		
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.white);
		g.drawRect(cx, cy, cw, ch);
		g.setStroke(new BasicStroke(1));
	}
	
	@Override
	public void keyPressed(KeyEvent k){
		gui.keyPressed(k);
	}
	
	public void keyReleased(KeyEvent k){
		gui.keyReleased(k);
	}

}
