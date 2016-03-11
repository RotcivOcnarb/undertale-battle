package pack;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.Vector;
import engine.sprite.Image;

public class Morceguinho {
	
	Vector position;
	Image asa1, asa2, cabeca, antena, pernas, blushed;
	
	double shift = 0;
	double woshift = 0;
	
	public static int hitShift = 0;
	
	public static int health = 3000;
	
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public static boolean spared = false;
	public static double speed = 1;
	
	public Morceguinho(Vector position){
		this.position = position;
		asa1 = new Image("/asa1.png");
		asa1.setAnchorRotationPoint(new Vector(asa1.getWidth()*3/2, asa1.getHeight()*3/2));
		asa2 = new Image("/asa2.png");
		asa2.setAnchorRotationPoint(new Vector(asa2.getWidth()*3/2, asa2.getHeight()*3/2));
		cabeca = new Image("/cabeca.png");
		blushed = new Image("/cabecablushed.png");
		antena = new Image("/antena.png");
		antena.setAnchorRotationPoint(new Vector(antena.getWidth()*3/2, 85));
		pernas = new Image("/pes.png");
	}
	
	public void update(double delta){
		
		for(int i = bullets.size() -1; i >= 0 ; i --){
			Bullet b = bullets.get(i);
			b.update(delta);
		}
		woshift += delta;
		shift = woshift*speed;
		
		if(health <= 0){
			speed = 0;
		}
		
		try{
		if(speed == 0.2){
			asa1.setTransparency(100);
			asa2.setTransparency(100);
			cabeca.setTransparency(100);
			blushed.setTransparency(100);
			antena.setTransparency(100);
			pernas.setTransparency(100);
		}
		else{
			asa1.setTransparency(255);
			asa2.setTransparency(255);
			cabeca.setTransparency(255);
			blushed.setTransparency(255);
			antena.setTransparency(255);
			pernas.setTransparency(255);	
		}
		}
		catch(Exception e){
			
		}
	}
		
	double angle;
	boolean shot;
	public void draw(Graphics2D g){
		if(health > 0){
		if(GUI.battle){
			angle += Math.sin(Math.toRadians(angle - 30))*30+30 + 1;
		}
		else{
			if(bullets.size() > 0){
				bullets.clear();
			}
		}

		if(GUI.atckTyp == 0){
		if(GUI.numshots != 6){
		if(GUI.battle && (angle%360) > 30 && (angle%360) < 150){
			if(!shot){
				for(int i = 0; i < 5; i ++)
					bullets.add(new Bullet(400, 200, Math.random()*120 + 30, 2, "/bullet.png"));
			shot = true;
			GUI.numshots ++;
			}
		}
		else{
			shot = false;
		}
		}
		}
		}
		
		asa1.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), Math.sin(shift/150)*10, 3, 3);
		asa2.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), 360 - Math.sin(shift/150)*10, 3, 3);
		if(speed == 2)
		blushed.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), 0, 3, 3);
		else
		cabeca.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), 0, 3, 3);
		if(!(GUI.battle && GUI.atckTyp == 0) || GUI.numshots == 6 || health <= 0)
		antena.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), Math.sin(shift/150 + 20)*5, 3, 3);
		else
		antena.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100)*5), angle, 3, 3);

		pernas.draw(g, (int)position.x + hitShift, (int)(position.y + Math.sin(shift/100 + 20)*5) - 20, 0, 3, 3);
		
		for(int i = bullets.size() -1; i >= 0 ; i --){
			Bullet b = bullets.get(i);
			b.draw(g);
		}
	}

}
