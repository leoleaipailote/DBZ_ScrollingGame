public class RareAvoid extends Avoid{//A RareAvoid is like an Avoid but it 
	//boasts more health and does double the damage, killing a rare avoid
	//is also worth double the points
    
    //Location of image file to be drawn for a RareGet
    private static final String RAREAVOID_IMAGE_FILE = "assets/frieza.png";

    private int HP = 2;
    
    public RareAvoid(){
        this(0, 0);        
    }
    
    public RareAvoid(int x, int y){
        super(x, y, RAREAVOID_IMAGE_FILE);  
    }
    
    public int getPointsValue(){
       return 40;
    }
    
    public int getDamageValue(){
    	return -2;
    }
    
    public int getHP(){
    	return HP;
    }
    
    public void modifyHP(int x){
    	HP += x;
    }
    	
    

   
}
