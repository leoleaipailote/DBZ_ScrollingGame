public class Projectile extends Entity implements Scrollable{//Projectiles
	//are fired by the player and are used to kill Avoids which give the
	//player points.
	
	private static final String PROJECTILE_IMAGE_FILE = "assets/ki_blast.png";
	private static final int GET_WIDTH = 30;
	private static final int GET_HEIGHT = 30;
	private static final int GET_SCROLL_SPEED = 10;
	private static final int GET_DAMAGE_VALUE = 1;
	
	
	public Projectile(){
        this(0, 0);        
    }
    
    public Projectile(int x, int y){
        super(x, y, GET_WIDTH, GET_HEIGHT, PROJECTILE_IMAGE_FILE);  
    }
    
    public Projectile(int x, int y, String imageFileName){
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
    }
    
    public int getScrollSpeed(){
        return GET_SCROLL_SPEED;
    }
    
    public void scroll(){
        setX(getX() + GET_SCROLL_SPEED);
    }
    
    public int getPointsValue(){
       return 0;
    }
    
    public int getDamageValue(){
        return -1;
    }
}