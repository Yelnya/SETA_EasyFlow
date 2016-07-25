package util;

import java.util.ArrayList;
import java.util.Random;

import model.EntryRoadTileModel;
import model.ExitRoadTileModel;
import model.VehicleCoordinates;

public class RandomComponents

{
	//ATTRIBUTES
	
	public enum VehicleColorEnumValues 
	{
	    white,
	    green,
	    blue,
	    orange
	}
	
	

	
	//CONSTRUCTOR
	public RandomComponents() 
	{
	}
	
	
	//METHODS
	
	/********************************************************/
	/**         RANDOM NUMBER BETWEEN low AND high         **/
	/********************************************************/
	//RANGE INCLUDING HIGH AND LOW NUMBER
	public int randomNumberBetweenBounds(int low, int high)
	{
		int randomNumber = low +  (int)(Math.random()*(high-low+1));
		return randomNumber;
	}
	
	
	/********************************************************/
	/**            RANDOM COLOR PICKED FROM ENUM           **/
	/********************************************************/
	//PICKING RANDOM COLOR FROM ENUM - return "white", "green" a.s.o.
	public String randomVehicleColor() 
	{
	    int pick = new Random().nextInt(VehicleColorEnumValues.values().length);
	    String color = VehicleColorEnumValues.values()[pick].toString();
	    return color;
	}
	
	
	/********************************************************/
	/**            STRINGBUILDER FOR BITMAP PATH           **/
	/********************************************************/
	//CONSTRUCTING VALID BITMAP PATH FROM ATTRIBUTES
	//e.g. output should be "src/_resources/tiles/vehicles/white/8.png"
	public String bitmapPathBuilder(String color, int direction) 
	{
	    String path = new StringBuilder().append("src/_resources/tiles/vehicles/").append(color).append("/").append(direction).append(".png").toString();
	    return path;
	}
	
	//CONSTRUCTING VALID BITMAP PATH FROM ATTRIBUTES FOR BUSSES
	//e.g. output should be "src/_resources/tiles/vehicles/_bus/8.png"
	public String bitmapPathBuilderForBusses(Integer oldDirection, Integer newDirection) 
	{
	    if ((oldDirection.equals(1) || oldDirection.equals(5)) &&	//north or south
	    		(newDirection.equals(2) || newDirection.equals(4) || newDirection.equals(6) || newDirection.equals(8)))	
	    {
			String path = new StringBuilder().append("src/_resources/tiles/vehicles/_bus/").append(newDirection).append("_1.png").toString();
		    return path;
	    }
	    else if ((oldDirection.equals(3) || oldDirection.equals(7)) &&	//west or east
	    		(newDirection.equals(2) || newDirection.equals(4) || newDirection.equals(6) || newDirection.equals(8)))	
	    {
			String path = new StringBuilder().append("src/_resources/tiles/vehicles/_bus/").append(newDirection).append("_2.png").toString();
		    return path;
	    }
	    else if (newDirection.equals(2) || newDirection.equals(4) || newDirection.equals(6) || newDirection.equals(8))
	    {
			String path = new StringBuilder().append("src/_resources/tiles/vehicles/_bus/").append(newDirection).append("_1.png").toString();
		    return path;
	    }
	    else
	    {
			String path = new StringBuilder().append("src/_resources/tiles/vehicles/_bus/").append(newDirection).append(".png").toString();
		    return path;
	    }
	}
	//CONSTRUCTING VALID BITMAP PATH FROM ATTRIBUTES FOR TRUCKS
	//e.g. output should be "src/_resources/tiles/vehicles/_truck/1_1.png"
	public String bitmapPathBuilderForTrucks(Integer oldDirection, Integer newDirection) 
	{
		String path = null;
		//only for EntryTiles!
		if (oldDirection == 0)
		{
			path = new StringBuilder().append("src/_resources/tiles/vehicles/_truck/").append(newDirection).append(".png").toString();
		}
		//for all other Tiles
		path = new StringBuilder().append("src/_resources/tiles/vehicles/_truck/").append(newDirection).append("_").append(oldDirection).append(".png").toString();
	    return path;
	}

	public VehicleCoordinates getRandomEntryPosition(ArrayList<EntryRoadTileModel> entries) {
	int numberOfEntryTiles = entries.size();
	int randomEntry = randomNumberBetweenBounds(0, numberOfEntryTiles-1);
	VehicleCoordinates position = 
			new VehicleCoordinates (
					entries.get(randomEntry).getPosX()+1,	// +1 because of index confusion
					entries.get(randomEntry).getPosY()+1	// +1 because of index confusion
					);
	
	return position;
		
	} // getRandomEntryPosition
	
	public VehicleCoordinates getRandomExitPosition(ArrayList<ExitRoadTileModel> exits) {
		int numebrOfExitsTiles = exits.size();
		int randomIndex = randomNumberBetweenBounds(0, numebrOfExitsTiles-1);
		VehicleCoordinates position = 
				new VehicleCoordinates (
						exits.get(randomIndex).getPosX()+1,	// +1 because of index confusion
						exits.get(randomIndex).getPosY()+1	// +1 because of index confusion
						);
		
		return position;
			
		} // getRandomExitPosition
		
	public ExitRoadTileModel getRandomExitRoadTile (ArrayList<ExitRoadTileModel> exits) {
		int numebrOfExitsTiles = exits.size();
		int randomIndex = randomNumberBetweenBounds(0, numebrOfExitsTiles-1);
				return exits.get(randomIndex);
			
		} // getRandomExitPosition
	
} //end of class
