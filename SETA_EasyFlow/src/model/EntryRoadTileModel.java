package model;

/*******************************************************************/
/**  Class EntryRoadTileModel                                     **/
/**                                                               **/ 
/**  for road tiles which implement the entry of new vehicles     **/ 
/*******************************************************************/

public class EntryRoadTileModel extends RoadTileModel {

	private Integer mainDirection;
	private String tileType = "EntryRoadTile";

	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/

	public EntryRoadTileModel(int posX, int posY, Integer direction)
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

}
