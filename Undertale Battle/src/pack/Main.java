package pack;

import java.awt.Graphics2D;

import engine.sprite.Image;
import engine.window.Window;

public class Main {
	
	static Image numbers[] = new Image[10];
	
	public static void drawHit(Graphics2D g, int x, int y, int number){
		if(number >= 0){
		String txt = number + "";
		
		int shift = 0;
		
		for(int i = 0; i < txt.length(); i ++){
			int digit = Integer.parseInt(txt.substring(i, i+1));
			numbers[digit].draw(g, x + shift, y);
			shift += numbers[digit].getWidth() + 5;
		}
		}
		
	}
	
	public static void main(String args[]){
		
		Window w = new Window("Morceguinho's Battle");
		w.addState(new Game());
		w.start();
		
		for(int i = 0; i < 10; i ++){
			numbers[i] = new Image("/numbers/fnt_" + i + ".png");
		}
		
	}

}
