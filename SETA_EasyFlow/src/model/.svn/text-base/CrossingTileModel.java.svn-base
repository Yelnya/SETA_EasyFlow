package model;

/*******************************************************************/
/**  Class CrossingTileModel                                      **/
/**                                                               **/ 
/**  road tile model for crossings                                **/ 
/*******************************************************************/	
public class CrossingTileModel extends RoadTileModel {
	
	private Integer mainDirection;		// i.e. straight ahead
	private Integer rightDirection;
	private Integer leftDirection;
	private String tileType = "CrossingTile";
	
	
	
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public CrossingTileModel(int posX, int posY, Integer direction)
	{
		super(posX, posY, direction);
		this.mainDirection = direction;
		setTileType(tileType);
	}
	
	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	public boolean hasLeftTurn() {
		return false;
	}
	
	public boolean hasRightTurn() {
		return false;
	}
	
	public Integer getDirection() {
		return mainDirection;
	}
	
	public Integer getLeftDirection() {
		return leftDirection;
	}
	
	public Integer getRightDirection() {
		return rightDirection;
	}
}
