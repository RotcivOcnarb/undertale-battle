package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Wave {
	
	int radius = 0;
	int type;
	int x, y;
	public Wave(int x, int y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void draw(Graphics2D g){
		if(type == 0){
			g.setColor(Color.orange);
		}
		else{
			g.setColor(Color.cyan);
		}
		
		g.setStroke(new BasicStroke(5));
		
		g.drawOval(x - radius, y - radius, radius*2, radius*2);
	}
	
	public void update(double delta){
		radius += 3;
	}

}
