package model;

/*******************************************************************/
/**  Class TileModel                                              **/
/**                                                               **/ 
/**  abstract base class for tile model                           **/ 
/*******************************************************************/
abstract public class TileModel {
	
	
	private String tileType = null;
	
	private int posX;
	private int posY;
	private Integer direction;

	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public TileModel()
	{
	}
	
	public TileModel(int posX, int posY, Integer direction)
	{
		setPosX(posX);
		setPosY(posY);
		setDirection(direction);
	}
	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}


	public abstract String getTileType();

	public abstract void setTileType(String tileType);

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	/*******************************************************************/
	/**  isDrivable                                          		  **/
	/**                                                               **/
	/** true: a vehicle is able to drive on this tile (e.g. road, crossing, round about...) */
	/** false: a vehicle is not allowed and/or not able on this tile (e.g. private property) */
	/*******************************************************************/
	public abstract boolean isDrivable();	
	
}
