package model;

/*******************************************************************/
/**  Class DecorTileModel                                         **/
/**                                                               **/ 
/**  for grid tiles which are not driveable like properties       **/ 
/*******************************************************************/
public class DecorTileModel extends TileModel 
{
	private String tileType = "DecorTile";

	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public DecorTileModel() {
		setTileType(tileType);
	}
	/*******************************************************************/
	/**  isDrivable                                          		  **/
	/**                                                               **/ 
	/*******************************************************************/
	
	public boolean isDrivable() {
		// TODO Auto-generated method stub
		return false;
		
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
