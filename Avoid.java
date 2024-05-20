//Avoids are entities the player needs to avoid colliding with.
//If a player collides with an avoid, it reduces the players Hit Points (HP).
//A player must destroy Avoids in order to gain points. to win the game.
public class Avoid extends Entity implements Consumable, Scrollable {
     
    //Location of image file to be drawn for an Avoid
    private static final String AVOID_IMAGE_FILE = "assets/saibamen.png";
    //Dimensions of the Avoid    
    private static final int AVOID_WIDTH = 75;
    private static final int AVOID_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    private static final int AVOID_SCROLL_SPEED = 5;
    
    private int HP = 1;
   
    public Avoid(){
        this(0, 0);        
    }
    
    public Avoid(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);  
    }
     public Avoid(int x, int y, String imageFileName){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, imageFileName);
    }
    
    public int getScrollSpeed(){
        return AVOID_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - AVOID_SCROLL_SPEED);
    }
    
    //Colliding with an Avoid does not affect the player's score
    public int getPointsValue(){
       return 20;
    }
    
    //Colliding with an Avoid Reduces players HP by 1
    public int getDamageValue(){
        return -1;
    }
    
    public int getHP(){
    	return HP;
    }
    
    public void modifyHP(int x){
    	HP += x;
    }
    	
    	
   
}
