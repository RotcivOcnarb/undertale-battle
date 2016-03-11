package pack;

import java.awt.Graphics2D;

import engine.Vector;
import engine.sprite.Image;

public class Bullet {
	
	public Image img;
	
	double x, y, angle, speed;
	double dx, dy;
	int imgAn, aux;
	
	public Bullet(int x, int y, double angle, int speed, String img){
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
		this.img = new Image(img);
		
		this.img.setAnchorRotationPoint(new Vector(this.img.getWidth(), this.img.getHeight()));
		
		aux = (int)(Math.random() * 20 - 10);
		
		dy = Math.sin(Math.toRadians(angle))*speed;
		dx = Math.cos(Math.toRadians(angle))*speed;
	}
	
	public void draw(Graphics2D g){
		img.draw(g, (int)x, (int)y, imgAn, 2, 2);
	}
	
	public void update(double delta){
		imgAn += aux;
		x += dx;
		y += dy;
	}

}
