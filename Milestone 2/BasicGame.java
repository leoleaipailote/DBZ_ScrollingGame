import java.awt.*;
import java.awt.event.*;
import java.util.*;

//A Basic version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class BasicGame extends AbstractGame {
           
    //Dimensions of game window
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;  
    
    //Starting Player coordinates
    private static final int STARTING_PLAYER_X = 0;
    private static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    private static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    private static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    private static final int SPEED_CHANGE = 20;    
 
    private static final String INTRO_SPLASH_FILE = "assets/splash.gif";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    private static final int SPAWN_INTERVAL = 45;
    //Maximum Entities that can be spawned on a single call to spawnEntities
    private static final int MAX_SPAWNS = 3;

   
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();

    //Player's current score
    private int score;
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    private Player player;
    
    
    public BasicGame(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public BasicGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void preGame(){
        this.setBackgroundColor(Color.BLACK);
        this.setSplashImage(INTRO_SPLASH_FILE);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
    }
    
    //Called on each game tick
    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (ticksElapsed % SPAWN_INTERVAL == 0){
            scrollEntities();
        	for(int i = 0; i < rand.nextInt(4) + 1; i++){
        		spawnEntities();
        	}
        }
        Entity e = checkCollision(player);
        if(e instanceof Consumable){
        	Consumable c = (Consumable) e;
        	if(checkCollision(player) != null){
        		handleCollision(c);
        	}       
        }
        if(e instanceof Avoid){
        	Avoid a = (Avoid) e;
        	player.modifyHP(a.getDamageValue());
        }
        else if(e instanceof Get){
        	Get g = (Get) e;
        	score += g.getPointsValue();
        	if(e instanceof RareGet){
        		RareGet r = (RareGet) e;
        		player.modifyHP(r.getHealValue());
        	} 		
        }

           
        //Update the title text on the top of the window
        setTitleText("HP: "+ player.getHP() +", Score: " + score);
    }
    
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){
        for (int i = 0; i < displayList.size(); i++){
        	Entity e = displayList.get(i);
        	if(e instanceof Avoid){
        		Avoid a = (Avoid) e;
        		a.scroll();
        	}
        	else if(e instanceof Get){
        		Get g = (Get) e;
        		g.scroll();
        	}
        	else if(e instanceof RareGet){
        		RareGet r = (RareGet) e;
        		r.scroll();
        	}
            //How do you know what Entities to scroll?
            //finish me!
            
        }
    }
    
    //Spawn new Entities on the right edge of the game board
    protected void spawnEntities(){
        double prob;
        int index;
        int x = getWindowWidth();
        int y = rand.nextInt(getWindowHeight()-75);
        prob = rand.nextDouble();
        
        
		
		if(prob >= 0 && prob <0.6){
			Avoid a = new Avoid(x, y);
			while(checkCollision(a) != null){
				y = rand.nextInt(getWindowHeight()-a.getHeight());
			    a = new Avoid(x, y);
			}
			displayList.add(a);
		}
		else if(prob >= 0.6 && prob < 0.95){
			Get g = new Get(x, y);
			while(checkCollision(g) != null){
				y = rand.nextInt(getWindowHeight()-g.getHeight());
			    g = new Get(x, y);
			}
			displayList.add(g);
		}
		else if(prob >= 0.95 && prob < 1){
			RareGet r = new RareGet(x, y);
			while(checkCollision(r) != null){
				y = rand.nextInt(getWindowHeight()-r.getHeight());
			    r = new RareGet(x, y);
			}
			displayList.add(r);
        	
        }
	
        	
        
    }
    
    
    //Called whenever it has been determined that the Player collided with a consumable
    protected void handleCollision(Consumable collidedWith){
    	Entity e = (Entity) collidedWith;
    	e.setVisible(false);
    	displayList.remove(e);
        //throw new IllegalStateException("Hey 102 Student! You need to implement handleCollision in BasicGame!"); 
    }
    
    

    
    
    //Called once the game is over, performs any end-of-game operations
    protected void postGame(){
    	if(player.getHP() == 0){
    		super.setTitleText("GAME OVER - You Lose!");
    	}
    	else if(score == 300){
    		super.setTitleText("GAME OVER - You Win!");
    	}
    }
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
    	if(score == 300 || player.getHP() == 0){
    		return true;
    	}
        return false;
    }
     
    //Reacts to a single key press on the keyboard
    //Override's AbstractGame's handleKey
    protected void handleKey(int key){
        //first, call AbstractGame's handleKey to deal with any of the 
        //fundamental key press operations
        super.handleKey(key);
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key));
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);
            return;
        }
        if(key == KEY_PAUSE_GAME){
        	isPaused = !isPaused;  	
        }
        
        if(isPaused == false){
			if (key == UP_KEY){
				if(player.getY() >= 0){
					player.scrollUp();
				}
			}
			else if(key == DOWN_KEY){
				if(player.getY() <= getWindowHeight()-player.getHeight()){
					player.scrollDown();
				}
			}
			else if(key == RIGHT_KEY){
				if(player.getX() <= getWindowWidth()-player.getWidth())
				player.scrollRight();
			}
			else if(key == LEFT_KEY){
				if(player.getX() >= 0){
					player.scrollLeft();
				}
			}
			
			if(key == SPEED_UP_KEY){
				if(getGameSpeed() + SPEED_CHANGE <= MAX_GAME_SPEED){
					setGameSpeed(getGameSpeed() + SPEED_CHANGE);
				}
			}
			if(key == SPEED_DOWN_KEY){
				if(getGameSpeed() - SPEED_CHANGE > 0){
					setGameSpeed(getGameSpeed() - SPEED_CHANGE);
				}
			}
		}
    }    


    
    

    
    
}
