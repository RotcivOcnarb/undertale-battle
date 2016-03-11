package pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;

import engine.Vector;
import engine.sprite.Animation;
import engine.sprite.Image;

public class GUI {
	
	Image act1, act2, fight1, fight2, item1, item2, mercy1, mercy2, heart, fight_menu;
	
	String actItems[][] = {{"* Check", "* Tell a joke"}, {"* Show some loli pics", "* Insult"}};
	String itemItems[][] = {{"* Loli juice", "* Loli juice"}, {"* Loli juice", "* Loli juice"}};
	String mercyItems[][] = {{"* Spare"}, {}};
	
	int health = 20;
	boolean invenc = false;
	int invencTimer = 0;
	
	int furtherSelectionX = 0;
	int furtherSelectionY = 0;
	
	int fakeHealth = 3000;
	
	int downSelected = 1;
	
	boolean fighting = false, addfight = false;
	int fightbar = 0, colorchange = 0, fighttimer = 0;
	
	Font undertale;
	Font hud;
	
	int hx = 400, hy = 400;
	
	int wichMenu = 0;
	final int TALK = 0;
	final int FIGHT = 1;
	final int ACT = 2;
	final int ITEM = 3;
	final int MERCY = 4;
	
	boolean canDrawSpeak = true;
	static public boolean battle = false;
	static public int atckTyp = 0;
	static public int numshots = 0;
	int wait = 0;
	boolean selectedEnemy = false;
	
	Animation hit;
	
	ArrayList<Wave> waves = new ArrayList<Wave>();
	
	String fala = "*This little bat looks happier than usual...";
	String firstMet = "*This little bat looks happier than usual...";
	String checkSpeak = "* MORCEGUINHO 10 ATK 5 DEF\n* Just hide your lolis,\ntrust me...";
	String tellJoke = "* You tell him a joke\n* Morceguinho is now laughing with a\nfunny voice";
	String showPics = "* You show morceguinho some loli\npics you found on the internet\n* Morceguinho DEF fell!";
	String insultSpeak = "* You called morceguinho fat\n* Somehow he's still happy...";
	int chars = 0;
	int timer = 0;
	
	public GUI(){
		act1 = new Image("/act1.png");
		act2 = new Image("/act2.png");
		fight1 = new Image("/fight1.png");
		fight2 = new Image("/fight2.png");
		item1 = new Image("/item1.png");
		item2 = new Image("/item2.png");
		mercy1 = new Image("/mercy1.png");
		mercy2 = new Image("/mercy2.png");
		heart = new Image("/heart.png");
		fight_menu = new Image("/fight_menu.png");
		
		try{
			InputStream is = getClass().getResourceAsStream("/undertale.otf");
			undertale = Font.createFont(Font.TRUETYPE_FONT, is);
			
			is = getClass().getResourceAsStream("/undertalehud.ttf");
			hud = Font.createFont(Font.TRUETYPE_FONT, is);
			}
			catch(Exception e){
				
			}
		
		String hitPaths[] = {"/hit1.png", "/hit2.png", "/hit3.png", "/hit4.png", "/hit5.png"};
		
		hit = new Animation(hitPaths, 100);
		hit.repeat = false;
	}
	
	boolean baux;
	int waveTimer = 0;
	int numWaves = 0;
	public void update(double delta){
		if(battle && atckTyp == 1){
			waveTimer += delta;
			
			for(int i = waves.size() - 1; i >= 0; i --){
				Wave w = waves.get(i);

				Vector hV = new Vector(hx + heart.getWidth()*3/2, hy + heart.getHeight()*3/2);
				Vector bV = new Vector(w.x, w.y);
				
				double dist = hV.subtract(bV).length();
				
				if(dist > w.radius-5 && dist < w.radius +5){
					if(w.type == 0){
						if(!left && !right && !up && !down){
							if(!invenc){
								health -= 5;
								invenc = true;
								invencTimer = 2000;
								}
						}
					}
					else{
						if(left || right || up || down){
							if(!invenc){
								health -= 5;
								invenc = true;
								invencTimer = 2000;
								}
						}
					}
				}
				
			}
			
			if(Morceguinho.health > 0){
			if(waveTimer > 500){
				waveTimer = 0;
				numWaves++;
				waves.add(new Wave(400, 200, (int)(Math.random()*2)));
				
				if(numWaves == 10){
					waves.clear();
					battle = false;
					waveTimer = 0;
					numWaves = 0;
					downSelected = 1;
					fala = firstMet;
					canDrawSpeak = true;
					selectedEnemy = false;
					wichMenu = TALK;
					timer = 0;
					chars = 0;
				}
			}
			}
		}
		
		for(int i = waves.size() - 1; i >= 0; i --){
			Wave w = waves.get(i);
			w.update(delta);
		}
		
		timer += delta;
		invencTimer -= delta;
	
		
		if(invencTimer < 0){
			invencTimer = 0;
			invenc = false;
		}
		
		if(numshots == 6){
			wait += delta;
			if(wait > 3500){
				if(!baux){
					battle = false;
					downSelected = 1;
					fala = firstMet;
					canDrawSpeak = true;
					selectedEnemy = false;
					wichMenu = TALK;
					timer = 0;
					chars = 0;
					baux = true;
				}
				
			}
		}
		else{
			baux = false;
		}
		
		if(up) hy-=2;
		if(down) hy+=2;
		if(left) hx-=2;
		if(right) hx+=2;
		
		if(hx < 255) hx = 255;
		if(hx > 530) hx = 530;
		if(hy < 325) hy = 325;
		if(hy > 500) hy = 500;
		
		if(timer > 30){
			timer = 0;
			if(chars < fala.length()){
			chars ++;
			}
		}
		
		if(fighting && addfight){
			fightbar += delta;
		}
		if(fighting && !addfight){
			fighttimer += delta;
			colorchange +=delta;
			hit.update((float)delta);
		}
		
	}
	
	public Color invertColor(Color c){
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
	
	boolean generateHit = false;
	int numHit = 0;
	public void draw(Graphics2D g){
		if(!battle){
		if(downSelected == 1){
			fight2.draw(g, 50, 520, 0, 1.5f, 1.5f);
		}
		else{
			fight1.draw(g, 50, 520, 0, 1.5f, 1.5f);
		}
		
		if(downSelected == 2){
			act2.draw(g, 230, 520, 0, 1.5f, 1.5f);
		}
		else{
			act1.draw(g, 230, 520, 0, 1.5f, 1.5f);
		}
		
		if(downSelected == 3){
			item2.draw(g, 410, 520, 0, 1.5f, 1.5f);
		}
		else{
			item1.draw(g, 410, 520, 0, 1.5f, 1.5f);
		}

		if(downSelected == 4){
			mercy2.draw(g, 590, 520, 0, 1.5f, 1.5f);
		}
		else{
			mercy1.draw(g, 590, 520, 0, 1.5f, 1.5f);
		}
		
		}
		
		for(int i = Morceguinho.bullets.size() -1; i >= 0 ; i --){
			Bullet b = Morceguinho.bullets.get(i);
			if(heartCollision(b.img, b.x, b.y)){
				if(!invenc){
				health -= 5;
				invenc = true;
				invencTimer = 2000;
				}
			}
		}
		
		if(!GUI.battle){
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.black);
		g.fillRect(50, 320, 700, 150);
		}
		
		if(fakeHealth > Morceguinho.health){
			fakeHealth -=10;
		}
		if(fightbar > 1400){
			fighttimer = 1001;
		}
		
		if(fighting){
			fight_menu.draw(g, 60, 317, 0, 1.2f, 1.2f);
			
			if(!addfight){
				hit.draw(g, 350, 100);
				
				if(!generateHit){
					
					int aaa = (int)((782 - Math.abs(fightbar - 782))/4.0);
					
					numHit = (int)(Math.random()*200 + aaa);
					generateHit = true;
					Morceguinho.health -= numHit;
				}
				Morceguinho.hitShift = (fighttimer/100)%2*20;
				Main.drawHit(g, 400, 150, numHit);
				
				g.setColor(Color.darkGray);
				g.fillRect(300, 200, 200, 20);
				g.setColor(Color.green);
				g.fillRect(302, 202, (int)((fakeHealth / 3000.0) * 198), 18);
				//TODO:
				
				
				
			}
			
			Color c = Color.white;
			if(fighttimer > 1000){
				
				if(Morceguinho.health >= 0){
					
					atckTyp = (int)(Math.random()*2);
					GUI.numshots = 0;
					hx = 400;
					hy = 400;
					hit.setCurrentFrame(0);
					generateHit = false;
				}
				
				battle = true;
				//TODO: ENTER BATTLE
				fighttimer = 0;
				fightbar = 0;
				addfight = false;
				fighting = false;
				downSelected = wichMenu;
				fala = firstMet;
				canDrawSpeak = true;
				selectedEnemy = false;
				wichMenu = TALK;
				timer = 0;
				chars = 0;
				
				if(Morceguinho.health < 0){
					fala = "";
				}
			}
			if(colorchange > 100){
				colorchange = 0;
				c = invertColor(c);
			}
			if(fightbar > 0){
			g.setColor(c);
			g.fillRect(fightbar/2, 300, 15, 200);
			g.setColor(invertColor(c));
			g.drawRect(fightbar/2, 300, 15, 200);
			}
			
		}
		
		if(!battle){
		g.setColor(Color.white);
		g.setFont(hud.deriveFont(Font.PLAIN, 24));
		g.drawString("LEOCA   LV 1", 60, 500);
		g.setFont(hud.deriveFont(Font.PLAIN, 20));
		g.drawString("HP", 300, 500);
		g.setFont(hud.deriveFont(Font.PLAIN, 24));
		g.drawString(health + "/20", 385, 500);
		
		g.setColor(Color.red);
		g.fillRect(335, 482, 40, 25);
		g.setColor(Color.YELLOW);
		g.fillRect(335, 482, health*2, 25);
		}
		else{
			g.setColor(Color.white);
			g.setFont(hud.deriveFont(Font.PLAIN, 20));
			g.drawString("HP", 300, 580);
			g.setFont(hud.deriveFont(Font.PLAIN, 24));
			g.drawString(health + "/20", 385, 580);
			
			g.setColor(Color.red);
			g.fillRect(335, 562, 40, 25);
			g.setColor(Color.YELLOW);
			g.fillRect(335, 562, health*2, 25);
			
			if(invencTimer/100 % 2 == 0)
			heart.draw(g, hx, hy);
		}
		
		
		g.setColor(Color.white);
		
		if(!battle){
		g.setFont(undertale.deriveFont(Font.PLAIN, 24));
		if(canDrawSpeak){
		drawString(g, fala.substring(0, chars), 80, 330);
		}
		}
		if(wichMenu == TALK){
			selectedEnemy = false;
		}
		else{
			if(!selectedEnemy){
				heart.draw(g, 85, 355);
				if(Morceguinho.spared) g.setColor(Color.yellow);
				g.drawString("*Morceguinho", 115, 370);
			}
		}
		g.setColor(Color.white);
		g.setFont(undertale.deriveFont(Font.PLAIN, 28));
		if(selectedEnemy && !canDrawSpeak){
			switch(wichMenu){
			case ACT:
				for(int i = 0; i < actItems.length; i ++){
					for(int j = 0; j < actItems[0].length; j ++){
						drawString(g, actItems[i][j], 85 + i*300, 345 + j*60);
					}
				}
				heart.draw(g, 65+ furtherSelectionX*300, 355 + furtherSelectionY*60);
				break;
			case ITEM:
				for(int i = 0; i < itemItems.length; i ++){
					for(int j = 0; j < itemItems[0].length; j ++){
						drawString(g, itemItems[i][j], 85 + i*300, 345 + j*60);
					}
				}
				heart.draw(g, 65+ furtherSelectionX*300, 355 + furtherSelectionY*60);
				break;
			case MERCY:
				furtherSelectionX = 0;
				furtherSelectionY = 0;
				for(int i = 0; i < mercyItems.length; i ++){
					for(int j = 0; j < mercyItems[0].length; j ++){
						if(Morceguinho.spared) g.setColor(Color.yellow);
						try{drawString(g, mercyItems[i][j], 85 + i*300, 345 + j*60);}catch(Exception e){}
					}
				}
				heart.draw(g, 65+ furtherSelectionX*300, 355 + furtherSelectionY*60);
				break;
			}
		}
		
		for(int i = waves.size() - 1; i >= 0; i --){
			Wave w = waves.get(i);
			w.draw(g);
		}
		
		g.setStroke(new BasicStroke(1));

	}
	
	void drawString(Graphics2D g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public boolean heartCollision(Image bullet, double x, double y){
		Vector hV = new Vector(hx + heart.getWidth()/2, hy + heart.getHeight()/2);
		Vector bV = new Vector(x + bullet.getWidth(), y + bullet.getHeight());
		
		double dist = hV.subtract(bV).length();
		
		if(dist < heart.getWidth()*3/2)
			return true;
		else
			return false;
	}
	
	public void keyPressed(KeyEvent k){
		if(downSelected != 0){
			if(k.getKeyCode() == KeyEvent.VK_LEFT){
				downSelected --;
				if(downSelected == 0){
					downSelected = 4;
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_RIGHT){
				downSelected++;
				if(downSelected == 5){
					downSelected = 1;
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_Z){
				if(!battle){
				canDrawSpeak = false;
				wichMenu = downSelected;
				downSelected = 0;
				return;
				}
			}
		}
		else{
			if(k.getKeyCode() == KeyEvent.VK_X){
				if(chars == fala.length() && !fighting){
				downSelected = wichMenu;
				fala = firstMet;
				canDrawSpeak = true;
				selectedEnemy = false;
				wichMenu = TALK;
				timer = 0;
				chars = 0;
				}
			}
			if(!selectedEnemy){
				if(k.getKeyCode() == KeyEvent.VK_Z){
					selectedEnemy = true;
					if(wichMenu == FIGHT){
						fighting = true;
						addfight = true;
					}
					return;
				}
			}
		}
		
		if(k.getKeyCode() == KeyEvent.VK_UP){
			up = battle;
		}
		if(k.getKeyCode() == KeyEvent.VK_DOWN){
			down = battle;
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){
			left = battle;
		}
		if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			right = battle;
		}
		
		if(selectedEnemy){
			if(k.getKeyCode() == KeyEvent.VK_Z){
				if(wichMenu == ACT){
					if(canDrawSpeak && Morceguinho.health >= 0){
						downSelected = wichMenu;
						fala = firstMet;
						canDrawSpeak = true;
						selectedEnemy = false;
						wichMenu = TALK;
						timer = 0;
						chars = 0;
						//TODO: ENTER BATTLE
						battle = true;
						hx = 400;
						hy = 400;
						atckTyp = (int)(Math.random()*2);
						GUI.numshots = 0;
						return;
					}
					if(furtherSelectionX == 0 && furtherSelectionY == 0){
						Morceguinho.speed = 1;
						fala = checkSpeak;
					}
					if(furtherSelectionX == 0 && furtherSelectionY == 1){
						Morceguinho.speed = 1;
						fala = tellJoke;
					}
					if(furtherSelectionX == 1 && furtherSelectionY == 0){
						fala = showPics;
						Morceguinho.speed = 2;
						Morceguinho.spared = true;
					}
					if(furtherSelectionX == 1 && furtherSelectionY == 1){
						Morceguinho.speed = 1;
						fala = insultSpeak;
					}
					timer = 0;
					chars = 0;
					canDrawSpeak = true;
				}
				if(wichMenu == FIGHT){
					if(addfight){
						addfight = false;
					}
				}
				if(wichMenu == MERCY){
					downSelected = wichMenu;
					fala = firstMet;
					canDrawSpeak = true;
					selectedEnemy = false;
					wichMenu = TALK;
					timer = 0;
					chars = 0;
					if(Morceguinho.spared){
						Morceguinho.speed = 0.2;
					}
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_UP){
				if(wichMenu != MERCY){
					furtherSelectionY --;
					if(furtherSelectionY == -1){
						furtherSelectionY = 0;
					}
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_DOWN){
				if(wichMenu != MERCY){
					furtherSelectionY ++;
					if(furtherSelectionY == 2){
						furtherSelectionY = 1;
					}
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_LEFT){
				if(wichMenu != MERCY){
					furtherSelectionX --;
					if(furtherSelectionX == -1){
						furtherSelectionX = 0;
					}
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_RIGHT){
				if(wichMenu != MERCY){
					furtherSelectionX ++;
					if(furtherSelectionX == 2){
						furtherSelectionX = 1;
					}
				}
			}
		}
	}
	public void keyReleased(KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_UP){
			up = false;
		}
		if(k.getKeyCode() == KeyEvent.VK_DOWN){
			down = false;
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT){
			left = false;
		}
		if(k.getKeyCode() == KeyEvent.VK_RIGHT){
			right = false;
		}
	}

	boolean up, down, left, right;
	
}
