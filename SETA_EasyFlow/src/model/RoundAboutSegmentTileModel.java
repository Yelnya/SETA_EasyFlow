package model;
/*******************************************************************/
/**  Class RoundAboutSegmentTileModel                            **/
/**                                                               **/ 
/**  road tile model for segments between crossings of roundabout **/ 
/*******************************************************************/
public class RoundAboutSegmentTileModel extends RoadTileModel 
{

	private String tileType = "RoundAboutSegmentTile";
	
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
public RoundAboutSegmentTileModel(int posX, int posY, Integer direction)
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
