package model;

/*******************************************************************/
/**  Class ExitRoadTileModel                                      **/
/**                                                               **/ 
/**  for road tiles which implement the exit of  vehicles         **/ 
/*******************************************************************/

public class ExitRoadTileModel extends RoadTileModel {

	private Integer mainDirection;
	private String tileType = "ExitRoad";

	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/

	public ExitRoadTileModel(int posX, int posY, Integer direction)
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
	
	//TODO: ExitRoadTiles sind eh als X und Y Koordinate definiert
	// -> immer abfragen, ob eines der Objekte (aus der Array List) auf dieser 
	//Position steht, und wenn ja -> destroy Object

}
