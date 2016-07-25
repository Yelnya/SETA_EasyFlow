package model;

/*******************************************************************/
/**  Class RoundAboutCrossingTileModel                            **/
/**                                                               **/ 
/**  road tile model for crossings part of roundabout             **/ 
/*******************************************************************/
public class RoundAboutCrossingTileModel extends CrossingTileModel 
{
	private String tileType = "RoundAboutCrossing";

	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public RoundAboutCrossingTileModel(int posX, int posY, Integer direction)
	{
		super(posX, posY, direction);
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

}
