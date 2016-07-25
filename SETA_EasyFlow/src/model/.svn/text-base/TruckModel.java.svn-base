package model;

import util.Bitmap;

/*******************************************************************/
/**  Class TruckModel                                     		  **/
/**                                                               **/ 
/*******************************************************************/
public class TruckModel extends VehicleModel 
{

	//ATTRIBUTES
	static final String vehicleType = "Truck";
	private Integer oldDirection = 0;			//needed for rear direction an choice of right sprite
	

	
	public TruckModel(Integer posX, Integer posY, Integer layer, Integer direction, Integer speed, String color, Bitmap bitmap, ExitRoadTileModel target)
	{
		super(vehicleType, posX, posY, layer, direction, speed, color, bitmap, target);
		setOldDirection(direction);
	}
	


	//GETTERS AND SETTERS
	public Integer getOldDirection() {
		return oldDirection;
	}
	public void setOldDirection(Integer oldDirection) {
		this.oldDirection = oldDirection;
	}

	void checkSpeedLimit() {
		// TODO Auto-generated method stub
		
	}

	void checkWeightLimit() {
		// TODO Auto-generated method stub
		
	}





}
