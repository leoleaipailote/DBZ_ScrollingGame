//A RareGet is a special kind of Get that spawns more infrequently than the regular Get
//When consumed, RareGets restores the Player's HP but does not award points
//Rarer than a regular Get
public class RareGet extends Get{
    
    //Location of image file to be drawn for a RareGet
    private static final String RAREGET_IMAGE_FILE = "assets/senzu.png";

    public RareGet(){
        this(0, 0);        
    }
    
    public RareGet(int x, int y){
        super(x, y, RAREGET_IMAGE_FILE);  
    }
    
    public int getHealValue(){
    	return 1;
    }
    
    //I'm missing something here...
    //What's special about RareGets?
   
}
