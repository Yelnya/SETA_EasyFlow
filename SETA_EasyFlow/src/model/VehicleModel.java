package model;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Bitmap;
import util.LogUtility;
import util.RandomComponents;
import view.VehicleView;
/*******************************************************************/
/**  Class VehicleModel                                    		  **/
/**                                                               **/ 
/*******************************************************************/
public abstract class VehicleModel extends BaseModel {
	//ATTRIBUTES
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(VehicleModel.class.getName()));

	
	GridModel gridModel = DataModelSingleton.getInstance().getGridModel();

	RandomComponents randomComponents = new RandomComponents();

	String vehicleType = null;

	private Integer size = null;
	private Integer speed = null;			//0 = 0km/h, 1 = 20km/h, 2=40km/h, 3=60km/h, 4=80km/h, 5=100km/h
	private Integer maxSpeed = null;
	private Integer direction = null;		//8 directions
	private Integer counterTurnRight = 0;
	private Integer counterTurnLeft = 0;
	private Integer counterTurnStraight = 0;
	private Integer counterTurnStraightCrossing = 0;
	private Integer counterEvading = 0;
	private Integer counterReactionTime = 0;

	Integer posX = null;
	Integer posY = null;
	Integer layer = null;
	
	RoadTileModel currentPosition = null;	//für Zielfindung
	RoadTileModel startPosition = null;		//für Zielfindung
	RoadTileModel endPosition = null;		//für Zielfindung
	
	ArrayList <RoadTileModel>	routingInfo=null;	// this is the route to the next note (list of roadTiles) 
	ExitRoadTileModel	target=null;	// exit target road tile 

	Bitmap bitmap = null;			//übergibt Bitmap
	String color = null;

	Integer si = null; // was ist das?
	Boolean exists = true; // for exitRoad PJ
	Integer speedCounter = 0; // for speedCalculation

	private boolean toBeDeleted = false;
	
	//GETTERS AND SETTERS
	public Integer getCounterTurnStraightCrossing() {
		return counterTurnStraightCrossing;
	}

	public void setCounterTurnStraightCrossing(Integer counterTurnStraightCrossing) {
		this.counterTurnStraightCrossing = counterTurnStraightCrossing;
	}

	public Integer getCounterTurnStraight() {
		return counterTurnStraight;
	}

	public void setCounterTurnStraight(Integer counterTurnStraight) {
		this.counterTurnStraight = counterTurnStraight;
	}

	public Integer getCounterEvading() {
		return counterEvading;
	}

	public void setCounterEvading(Integer counterEvading) {
		this.counterEvading = counterEvading;
	}

	public Integer getCounterReactionTime() {
		return counterReactionTime;
	}

	public void setCounterReactionTime(Integer counterReactionTime) {
		this.counterReactionTime = counterReactionTime;
	}


	
	public ExitRoadTileModel getTarget() {
		return target;
	}

	public void setTarget(ExitRoadTileModel target) {
		this.target = target;
	}

	public Boolean getExists() {
		return exists;
	}

	public void setExists(Boolean exists) {
		this.exists = exists;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public synchronized Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public TileModel getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(RoadTileModel currentPosition) {
		this.currentPosition = currentPosition;
	}

	public TileModel getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(RoadTileModel startPosition) {
		this.startPosition = startPosition;
	}

	public TileModel getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(RoadTileModel endPosition) {
		this.endPosition = endPosition;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getLayer() {
		return layer;
	}

	public Integer getCounterTurnRight() {
		return counterTurnRight;
	}
	public void setCounterTurnRight(Integer counterTurnRight) {
		this.counterTurnRight = counterTurnRight;
	}
	public Integer getCounterTurnLeft() {
		return counterTurnLeft;
	}
	public void setCounterTurnLeft(Integer counterTurnLeft) {
		this.counterTurnLeft = counterTurnLeft;
	}


	public void setLayer(Integer layer) {
		this.layer = layer;
	}


	public synchronized boolean isToBeDeleted() {
		return toBeDeleted;
	}

	public synchronized void setToBeDeleted(boolean toBeDeleted) {
		// indicates model to be deleted
		this.toBeDeleted = toBeDeleted;
	}


	
	//CONSTRUCTOR

	public VehicleModel(String vehicleType, Integer posX, Integer posY,
			Integer layer, Integer direction, Integer speed, String color,
			Bitmap bitmap, ExitRoadTileModel target) {
		setVehicleType(vehicleType);
		setBitmap(bitmap);
		setPosX(posX);
		setPosY(posY);
		setLayer(layer);
		setDirection(direction);
		setSpeed(speed);
		setColor(color);
		setTarget(target);

		logger.setLevel(LogUtility.getLevel(Level.INFO));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		
	}
	//Method: set RoadTile as parent object
	public void setParent(TileModel gridPosition) {
		if (gridPosition instanceof RoadTileModel) {
			this.currentPosition = (RoadTileModel)gridPosition;	
		} else
			logger.severe("Vehicle on invlalid tile type !!!!");	
	}
	
	
	/**************************************************************************/
	/**                LOOK LEFT, RIGHT, STRAIGHT ON CROSSING                **/
	/**************************************************************************/

	public Boolean lookAheadFree(Integer lookWhere)
	{
		Boolean wayIsFree = false;
		Integer x1 = 0;
		Integer y1 = 0;
		switch(this.getDirection())
		{
			case 1 : x1 = this.getPosX(); y1 = this.getPosY()-lookWhere; break;
			case 3 : x1 = this.getPosX()+lookWhere; y1 = this.getPosY(); break;
			case 5 : x1 = this.getPosX(); y1 = this.getPosY()+lookWhere; break;
			case 7 : x1 = this.getPosX()-lookWhere; y1 = this.getPosY(); break;
			default : break;
		}
		if (gridModel.findOneVehicleInModelsList(x1, y1).equals(false))
		{
			return wayIsFree = true;
		}
		return wayIsFree = false;
	}
	
	
	public Boolean lookLeftFree(Integer lookWhere)
	{
		Boolean wayIsFree = false;
		Integer x1 = 0;
		Integer y1 = 0;
		switch(this.getDirection())
		{
			case 1 : x1 = this.getPosX()-lookWhere; y1 = this.getPosY()-2; break;
			case 3 : x1 = this.getPosX()+2; y1 = this.getPosY()-lookWhere; break;
			case 5 : x1 = this.getPosX()+lookWhere; y1 = this.getPosY()+2; break;
			case 7 : x1 = this.getPosX()-2; y1 = this.getPosY()+lookWhere; break;
			default : break;
		}
		if (gridModel.findOneVehicleInModelsList(x1, y1).equals(false))
		{
			return wayIsFree = true;
		}
		return wayIsFree = false;
	}
	
	
	public Boolean lookRightFree(Integer lookWhere)
	{
		Boolean wayIsFree = false;
		Integer x1 = 0;
		Integer y1 = 0;
		switch(this.getDirection())
		{
			case 1 : x1 = this.getPosX()+lookWhere; y1 = this.getPosY()-1; break;
			case 3 : x1 = this.getPosX()+1; y1 = this.getPosY()+lookWhere; break;
			case 5 : x1 = this.getPosX()-lookWhere; y1 = this.getPosY()+1; break;
			case 7 : x1 = this.getPosX()-1; y1 = this.getPosY()-lookWhere; break;
			default : break;
		}
		if (gridModel.findOneVehicleInModelsList(x1, y1).equals(false))
		{
			return wayIsFree = true;
		}
		return wayIsFree = false;
	}
	
	
	public Boolean lookLeftSameLaneFree(Integer lookWhere)
	{
		Boolean wayIsFree = false;
		Integer x1 = 0;
		Integer y1 = 0;
		
		switch(this.getDirection())
		{
			case 8 : x1 = this.getPosX()-lookWhere; y1 = this.getPosY(); break;
			case 2 : x1 = this.getPosX(); y1 = this.getPosY()-lookWhere; break;
			case 4 : x1 = this.getPosX()+lookWhere; y1 = this.getPosY(); break;
			case 6 : x1 = this.getPosX(); y1 = this.getPosY()+lookWhere; break;
			default : break;
		}
		
		if (gridModel.findOneVehicleInModelsList(x1, y1).equals(false))
		{
			return wayIsFree = true;
		}
		return wayIsFree = false;
	}
	
	
	/*******************************************************************/
	/**                      STRAIGHT AHEAD                           **/
	/*******************************************************************/
	
	public void straightAhead()
	{
		try {
			//continue one step in direction without changing speed
			if (getDirection().equals(1))	//north
			{
				Integer oldDirection = 1;
				this.setPosY(this.getPosY() -1);	//new position one step north
				//but if direction changes, then change bitmap too
				if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
						!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
				{
					((VehicleView)this.getView()).deleteLabel();
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
					if (this instanceof BusModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
					}
					else if (this instanceof PassengerCarModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
					}
					else if (this instanceof TruckModel)
					{
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
				//if direction does not change the truck sprite has to be set straight
				else
				{
					if (this instanceof TruckModel)
					{
						((VehicleView)this.getView()).deleteLabel();
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
			}
			else if (getDirection().equals(2))	//north-east
			{
				Integer oldDirection = 2;
				//IF BUSSTOP
				if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("BusStopRoadTile") || 
						gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					//for busstop and roundabout - NOT for curved roads
					this.setPosX(this.getPosX() +1);
					this.setPosY(this.getPosY() -1);
					//but if direction changes, then change bitmap too
					if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
							!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
					{
						((VehicleView)this.getView()).deleteLabel();
						this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
						if (this instanceof BusModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));	
						}
						else if (this instanceof PassengerCarModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
						}
						else if (this instanceof TruckModel)
						{
							((TruckModel)this).setOldDirection(oldDirection);
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
						}
					}
				}
				
				//IF CURVED ROAD
				else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoadTile"))
				{
					// -> road continuing north
					if (gridModel.getActualTilesArray()[getPosY()-2][getPosX()-1].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-2][getPosX()-1].getDirection().equals(1) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-2).equals(false))
					{
						this.setPosY(this.getPosY() -1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					// -> road continuing east
					else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-1][getPosX()].getDirection().equals(3) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()+2, this.getPosY()).equals(false))
					{
						this.setPosX(this.getPosX() +1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}				
					else
					{
						this.stop();
					}
				}

			}
			else if (getDirection().equals(3))	//east
			{
				Integer oldDirection = 3;
				this.setPosX(this.getPosX() +1); //new position one step east
				//but if direction changes, then change bitmap too
				if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
						!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
				{
					((VehicleView)this.getView()).deleteLabel();
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
					if (this instanceof BusModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
					}
					else if (this instanceof PassengerCarModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
					}
					else if (this instanceof TruckModel)
					{
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
				//if direction does not change the truck sprite has to be set straight
				else
				{
					if (this instanceof TruckModel)
					{
						((VehicleView)this.getView()).deleteLabel();
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
			}
			else if (getDirection().equals(4))	//south-east
			{
				Integer oldDirection = 4;
				//IF BUSSTOP
				if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("BusStopRoadTile") || 
						gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					//for busstop and roundabout - NOT for curved roads
					this.setPosX(this.getPosX() +1);
					this.setPosY(this.getPosY() +1);
					//but if direction changes, then change bitmap too
					if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
							!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
					{
						((VehicleView)this.getView()).deleteLabel();
						this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
						if (this instanceof BusModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
						}
						else if (this instanceof PassengerCarModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
						}
						else if (this instanceof TruckModel)
						{
							((TruckModel)this).setOldDirection(oldDirection);
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
						}
					}
				}
				//IF CURVED ROAD
				else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoadTile"))
				{
					// -> road continuing east
					if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-1][getPosX()].getDirection().equals(3) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()+2, this.getPosY()).equals(false))
					{
						this.setPosX(this.getPosX() +1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					// -> road continuing south
					else if (gridModel.getActualTilesArray()[getPosY()][getPosX()-1].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()][getPosX()-1].getDirection().equals(5) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+2).equals(false))
					{
						this.setPosY(this.getPosY() +1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					else
					{
						this.stop();
					}
				}
			}
			else if (getDirection().equals(5))	//south
			{
				Integer oldDirection = 5;
				this.setPosY(this.getPosY() +1);	 //new position one step south
				//but if direction changes, then change bitmap too
				if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
						!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
				{
					((VehicleView)this.getView()).deleteLabel();
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
					if (this instanceof BusModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
					}
					else if (this instanceof PassengerCarModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
					}
					else if (this instanceof TruckModel)
					{
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
				//if direction does not change the truck sprite has to be set straight
				else
				{
					if (this instanceof TruckModel)
					{
						((VehicleView)this.getView()).deleteLabel();
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
			}
			else if (getDirection().equals(6))	//south-west
			{
				Integer oldDirection = 6;
				//IF BUSSTOP
				if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("BusStopRoadTile") || 
						gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					//for busstop and roundabout - NOT for curved roads
					this.setPosX(this.getPosX() -1);
					this.setPosY(this.getPosY() +1);
					//but if direction changes, then change bitmap too
					if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
							!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
					{
						((VehicleView)this.getView()).deleteLabel();
						this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
						if (this instanceof BusModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
						}
						else if (this instanceof PassengerCarModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
						}
						else if (this instanceof TruckModel)
						{
							((TruckModel)this).setOldDirection(oldDirection);
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
						}
					}
				}
				
				//IF CURVED ROAD
				else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoadTile"))
				{
					// -> road continuing south
					if (gridModel.getActualTilesArray()[getPosY()][getPosX()-1].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()][getPosX()-1].getDirection().equals(5) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+2).equals(false))
					{
						this.setPosY(this.getPosY() +1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					// -> road continuing west
					else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-2].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-1][getPosX()-2].getDirection().equals(7) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()-2, this.getPosY()).equals(false))
					{
						this.setPosX(this.getPosX() -1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					else
					{
						this.stop();
					}
				}
			}
			
			else if (getDirection().equals(7))	//west
			{
				Integer oldDirection = 7;
				this.setPosX(this.getPosX() -1);	 //new position one step west
				//but if direction changes, then change bitmap too
				if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
						!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
				{
					((VehicleView)this.getView()).deleteLabel();
					//GridView.getPanel().remove(this.getBitmap().getBitmapLabel());
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
					if (this instanceof BusModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
					}
					else if (this instanceof PassengerCarModel)
					{
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
					}
					else if (this instanceof TruckModel)
					{
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
				//if direction does not change the truck sprite has to be set straight
				else
				{
					if (this instanceof TruckModel)
					{
						((VehicleView)this.getView()).deleteLabel();
						((TruckModel)this).setOldDirection(oldDirection);
						this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
					}
				}
			}
			else if (getDirection().equals(8))	//north-west
			{
				Integer oldDirection = 8;
				
				//IF BUSSTOP
				if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("BusStopRoadTile") || 
						gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					//for busstop and roundabout - NOT for curved roads
					this.setPosX(this.getPosX() -1);
					this.setPosY(this.getPosY() -1);
					//but if direction changes, then change bitmap too
					if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
							!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
					{
						((VehicleView)this.getView()).deleteLabel();
						this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
						if (this instanceof BusModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
						}
						else if (this instanceof PassengerCarModel)
						{
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
						}
						else if (this instanceof TruckModel)
						{
							((TruckModel)this).setOldDirection(oldDirection);
							this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
						}
					}
				}
				
				//IF CURVED ROAD
				else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-1].getTileType().equals("RoadTile"))
				{
					// -> road continuing north
					if (gridModel.getActualTilesArray()[getPosY()-2][getPosX()-1].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-2][getPosX()-1].getDirection().equals(1) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-2).equals(false))
					{
						this.setPosY(this.getPosY() -1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					// -> road continuing west
					else if (gridModel.getActualTilesArray()[getPosY()-1][getPosX()-2].getTileType().equals("RoadTile") && 
							gridModel.getActualTilesArray()[getPosY()-1][getPosX()-2].getDirection().equals(7) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false) &&
							gridModel.findOneVehicleInModelsList(this.getPosX()-2, this.getPosY()).equals(false))
					{
						this.setPosX(this.getPosX() -1);
						//but if direction changes, then change bitmap too
						if (!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(0) &&
								!gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection().equals(this.getDirection()))
						{
							((VehicleView)this.getView()).deleteLabel();
							this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection()); 	//change direction for vehicle
							if (this instanceof BusModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
							}
							else if (this instanceof PassengerCarModel)
							{
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
							}
							else if (this instanceof TruckModel)
							{
								((TruckModel)this).setOldDirection(oldDirection);
								this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
							}
						}
					}
					else
					{
						this.stop();
					}
				}
			}
		} catch (Exception e) {
			logger.severe("ENTERING EXCEPTION VEHICLEMODEL STRAIGHTAHEAD()");
			//e.printStackTrace();
		}
	} //end of straightAhead()

	public void straightAheadIfCrossing()
	{
		Integer oldDirection = 0;
		
		if (this.getCounterTurnStraightCrossing().equals(0) && this.exists.equals(true))
		{
			this.setCounterTurnStraightCrossing(1);
		}

		/********************************* NORTH *****************************************/
		
		if(this.getDirection().equals(1) && (this.getCounterTurnStraightCrossing().equals(1)) && this.exists.equals(true) &&
				gridModel.getActualTilesArray()[this.getPosY()-4][this.getPosX()-1].getTileType().equals("RoadTile"))	//FIRST step for NORTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(4).equals(true) && this.lookRightFree(1))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(1) && (this.getCounterTurnStraightCrossing().equals(2)) && this.exists.equals(true))	//SECOND step for NORTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(1) && (this.getCounterTurnStraightCrossing().equals(3)) && this.exists.equals(true))	//THIRD step for NORTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(0);
			}
			else
			{
				this.stop();
			}
		}
		
		/********************************* EAST *****************************************/
		
		if(this.getDirection().equals(3) && (this.getCounterTurnStraightCrossing().equals(1)) && this.exists.equals(true) &&
				gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()+2].getTileType().equals("RoadTile"))	//FIRST step for EAST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(4).equals(true) && this.lookRightFree(1))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(3) && (this.getCounterTurnStraightCrossing().equals(2)) && this.exists.equals(true))	//SECOND step for EAST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(3) && (this.getCounterTurnStraightCrossing().equals(3)) && this.exists.equals(true))	//THIRD step for EAST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(0);
			}
			else
			{
				this.stop();
			}
		}
		
		/********************************* SOUTH *****************************************/
		
		if(this.getDirection().equals(5) && (this.getCounterTurnStraightCrossing().equals(1)) && this.exists.equals(true) &&
				gridModel.getActualTilesArray()[this.getPosY()+2][this.getPosX()-1].getTileType().equals("RoadTile"))	//FIRST step for SOUTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(4).equals(true) && this.lookRightFree(1))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(5) && (this.getCounterTurnStraightCrossing().equals(2)) && this.exists.equals(true))	//SECOND step for SOUTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(5) && (this.getCounterTurnStraightCrossing().equals(3)) && this.exists.equals(true))	//THIRD step for SOUTH
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(0);
			}
			else
			{
				this.stop();
			}
		}
		
		/********************************* WEST *****************************************/
		
		if(this.getDirection().equals(7) && (this.getCounterTurnStraightCrossing().equals(1)) && this.exists.equals(true) &&
				gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-4].getTileType().equals("RoadTile"))	//FIRST step for WEST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(4).equals(true) && this.lookRightFree(1))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(7) && (this.getCounterTurnStraightCrossing().equals(2)) && this.exists.equals(true))	//SECOND step for WEST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(this.getCounterTurnStraightCrossing()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		else if(this.getDirection().equals(7) && (this.getCounterTurnStraightCrossing().equals(3)) && this.exists.equals(true))	//THIRD step for WEST
		{
			//look ahead if free
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1);
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnStraightCrossing(0);
			}
			else
			{
				this.stop();
			}
		}
		else
		{
			this.stop();
		}
	}
	
	

	public void straightAheadRoundAbout()
	{
		Integer oldDirection = 0;
		
		if (this.counterTurnStraight.equals(0) && this.exists.equals(true))
		{
			this.counterTurnStraight = 1;
		}

		
		/********************************* NORTH EAST *****************************************/
		//if direction is northeast and first counterTurnStraight is active
		if(this.getDirection().equals(2) && (this.counterTurnStraight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false))	//FIRST step for NORTH EAST
		{
			oldDirection = 2;
			//setting next tile
			this.setPosY(this.getPosY() -1); 	//new position one step north
			//direction changes -> change of bitmap
			this.setDirection(8);	
			((VehicleView)this.getView()).deleteLabel();
						
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnStraight(2);
		}

		//if direction is northwest and second counterTurnStraight is active
		else if(this.getDirection().equals(8) && (this.counterTurnStraight.equals(2)))	//SECOND step for NORTHEAST
		{
			
			oldDirection = 8;
			//setting next tile
			this.setPosX(this.getPosX() -1); 	//new position one step northwest
			this.setPosY(this.getPosY() -1); 
			((VehicleView)this.getView()).deleteLabel();
			//no direction change 
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnStraight(3);
		}
		
		/********************************* SOUTH EAST *****************************************/
		//if direction is southeast and first counterTurnStraight is active
		if(this.getDirection().equals(4) && (this.counterTurnStraight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false))	//FIRST step for SOUTH EAST
		{
			oldDirection = 4;
			//setting next tile
			this.setPosX(this.getPosX() +1); 	//new position one step east
			//direction changes -> change of bitmap
			this.setDirection(2);	
			((VehicleView)this.getView()).deleteLabel();
						
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnStraight(2);
		}

		//if direction is northwest and second counterTurnStraight is active
		else if(this.getDirection().equals(2) && (this.counterTurnStraight.equals(2)))	//SECOND step for SOUTH EAST
		{
			oldDirection = 2;
			//setting next tile
			this.setPosX(this.getPosX() +1); 	//new position one step northeast
			this.setPosY(this.getPosY() -1); 
			((VehicleView)this.getView()).deleteLabel();
			//no direction change 
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnStraight(3);
		}
		
		/********************************* SOUTH WEST *****************************************/
		//if direction is southwest and first counterTurnStraight is active
		if(this.getDirection().equals(6) && (this.counterTurnStraight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false))	//FIRST step for SOUTH WEST
		{
			oldDirection = 6;
			//setting next tile
			this.setPosY(this.getPosY() +1); 
			//direction changes -> change of bitmap
			this.setDirection(4);	
			((VehicleView)this.getView()).deleteLabel();
						
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnStraight(2);
		}

		//if direction is southwest  and second counterTurnStraight is active
		else if(this.getDirection().equals(4) && (this.counterTurnStraight.equals(2)))	//SECOND step for SOUTH WEST
		{
			oldDirection = 4;
			//setting next tile
			this.setPosX(this.getPosX() +1); 	//new position one step southeast
			this.setPosY(this.getPosY() +1); 
			((VehicleView)this.getView()).deleteLabel();
			//no direction change 
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnStraight(3);
		}		
		
		
		/********************************* NORTH WEST *****************************************/
		//if direction is northwest and first counterTurnStraight is active
		if(this.getDirection().equals(8) && (this.counterTurnStraight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false))	//FIRST step for NORTH WEST
		{
			oldDirection = 8;
			//setting next tile
			this.setPosX(this.getPosX() -1); 	//new position one step west
			//direction changes -> change of bitmap
			this.setDirection(6);	
			((VehicleView)this.getView()).deleteLabel();
						
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnStraight(2);
		}

		//if direction is northwest  and second counterTurnStraight is active
		else if(this.getDirection().equals(6) && (this.counterTurnStraight.equals(2)))	//SECOND step for NORTH WEST
		{
			oldDirection = 6;
			//setting next tile
			this.setPosX(this.getPosX() -1); 	//new position one step southwest
			this.setPosY(this.getPosY() +1); 
			((VehicleView)this.getView()).deleteLabel();
			//no direction change 
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnStraight(3);
		}		
		
		if (this.getCounterTurnStraight().equals(3))
		{
			this.counterTurnStraight = 0;
		}
		
		else
		{
			this.stop();
		}
		
	} //end of straightAheadRoundAbout()
	
	

	/*******************************************************************/
	/** STOP **/
	/*******************************************************************/

	public synchronized void stop() {
		// anhalten und warten, zB bis grün wird - keine Positionsveränderung
		logger.fine("vehicle stopped()");
	} // end of stop()

	/*******************************************************************/
	/**                         TURN RIGHT                            **/
	/*******************************************************************/
	
	public void turnRight() 
	{
		Integer oldDirection = 0;
				
		if (this.counterTurnRight.equals(0) && this.exists.equals(true))
		{
			this.counterTurnRight = 1;
		}
		
		/********************************* NORTH *****************************************/
		//if direction is north and first counterTurnRight is active
		if(this.getDirection().equals(1) && (this.counterTurnRight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false))	//FIRST step for NORTH
		{
			//look left and right
			if (this.lookAheadFree(1).equals(true) && this.lookRightFree(1).equals(true) && this.lookRightFree(2).equals(true))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1); 	//new position one step north
				//direction changes -> change of bitmap
				if (gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
				}
				else
				{
					this.setDirection(2);	
				}

				((VehicleView)this.getView()).deleteLabel();
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnRight(this.getCounterTurnRight()+1); 	//raise counter
			}
			else
			{
				this.stop();
			}
		}

		//if direction is northeast and second counterTurnRight is active
		else if(this.getDirection().equals(2) && (this.getCounterTurnRight().equals(2)))	//SECOND step for NORTH
		{
			oldDirection = 2;
			//setting next tile
			if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
			{
				this.setPosX(this.getPosX() +1); 
				this.setPosY(this.getPosY() -1); 
			}
			else
			{
				this.setPosX(this.getPosX() +1); 	//new position one step east
			}
			((VehicleView)this.getView()).deleteLabel();
			//direction changes -> change of bitmap
			this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
						
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnRight(0); 	//reset counter
		}
		
		
		/****************************************** EAST ***************************************/
		//if direction is east and first counterTurnRight is active
		else if(this.getDirection().equals(3) && (this.counterTurnRight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false))	//FIRST step for EAST
		{
			//look left and right
			if(this.lookAheadFree(1).equals(true) && this.lookRightFree(1).equals(true) && this.lookRightFree(2).equals(true))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1); 	//new position one step east
				//direction changes -> change of bitmap
				if (gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
				}
				else
				{
					this.setDirection(4);	
				}
				((VehicleView)this.getView()).deleteLabel();
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnRight(this.getCounterTurnRight()+1); 	//raise counter
			}
			else
			{
				this.stop();
			}

		}

		//if direction is east and second counterTurnRight is active
		else if(this.getDirection().equals(4) && (this.counterTurnRight.equals(2)) && this.exists.equals(true))	//SECOND step for EAST
		{
			//scan if tile standing on is an ExitRoad
			if (this.getPosX() > 0 && this.getPosY() > 0 && gridModel.getActualTilesArray()[this.getPosY()][this.getPosX()-1].getTileType().equals("ExitRoad"))
			{	
				this.counterTurnRight = 0;
				this.setExists(false);
				((VehicleView)this.getView()).delete();
				return;
			}
			
			else
			{
				oldDirection = 4;
				//setting next tile
				if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
				{
					this.setPosX(this.getPosX() +1); 
					this.setPosY(this.getPosY() +1); 
				}
				else
				{
					this.setPosY(this.getPosY() +1); 	//new position one step south
				}
				((VehicleView)this.getView()).deleteLabel();
				//direction changes -> change of bitmap
				this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
			}
			this.setCounterTurnRight(0); 	//reset counter
		}
		
		
		/******************************************** SOUTH **************************************/
		//if direction is south and first counterTurnRight is active
		else if(this.getDirection().equals(5) && (this.counterTurnRight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false))	//FIRST step for SOUTH
		{
			if (this.lookAheadFree(1).equals(true) && this.lookRightFree(1).equals(true) && this.lookRightFree(2).equals(true))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1); 	//new position one step south
				//direction changes -> change of bitmap
				if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
				}
				else
				{
					this.setDirection(6);	
				}
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnRight(this.getCounterTurnRight()+1); 	//raise counter
			}
			else
			{
				this.stop();
			}
		}

		//if direction is southwest and second counterTurnRight is active
		else if(this.getDirection().equals(6) && (this.counterTurnRight.equals(2)))	//SECOND step for SOUTH
		{
			oldDirection = 6;
			//setting next tile
			if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
			{
				this.setPosX(this.getPosX() -1); 
				this.setPosY(this.getPosY() +1); 
			}
			else
			{
				this.setPosX(this.getPosX() -1); 	//new position one step west
			}
			
			((VehicleView)this.getView()).deleteLabel();
			//direction changes -> change of bitmap
			this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnRight(0); 	//reset counter
		}
		
		/********************************************** WEST **************************************/
		//if direction is west and first counterTurnRight is active
		else if(this.getDirection().equals(7) && (this.counterTurnRight.equals(1)) && this.exists.equals(true) &&
				gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false))	//FIRST step for WEST
		{
			if (this.lookAheadFree(1).equals(true) && this.lookRightFree(1).equals(true) && this.lookRightFree(2).equals(true))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1); 	//new position one step west
				//direction changes -> change of bitmap
				if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
				}
				else
				{
					this.setDirection(8);	
				}
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
				}
				this.setCounterTurnRight(this.getCounterTurnRight()+1); 	//raise counter
			}
			else
			{
				this.stop();
			}
		}

		//if direction is west and first counterTurnRight is active
		else if(this.getDirection().equals(8) && (this.counterTurnRight.equals(2)) && this.exists.equals(true))	//SECOND step for WEST
		{
			oldDirection = 8;
			//setting next tile
			if(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getTileType().equals("RoundAboutCrossing"))
			{
				this.setPosX(this.getPosX() -1); 
				this.setPosY(this.getPosY() -1); 
			}
			else
			{
				this.setPosY(this.getPosY() -1);  	//new position one step north
			}
			
			((VehicleView)this.getView()).deleteLabel();
			//direction changes -> change of bitmap
			this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));
			}
			this.setCounterTurnRight(0); 	//reset counter
		}
		else
		{
			this.stop();
		}
	} //end of turnRight()
	

	public void turnRightRoundabout()
	{
		Integer oldDirection = 0;
		
		/********************************* NORTH EAST *****************************************/

		if(this.getDirection().equals(2) && this.exists.equals(true))	
		{
			if(gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false))
			{
				oldDirection = 2;
				//setting next tile
				this.setPosX(this.getPosX() +1);
				((VehicleView)this.getView()).deleteLabel();
				//direction changes -> change of bitmap
				this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
			}
			else
			{
				this.stop();
			}
			
		}
		
		/********************************* SOUTH EAST *****************************************/

		else if(this.getDirection().equals(4) && this.exists.equals(true))
		{
			if(gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false))
			{
				oldDirection = 4;
				//setting next tile
				this.setPosY(this.getPosY() +1);
				((VehicleView)this.getView()).deleteLabel();
				//direction changes -> change of bitmap
				this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
			}
			else
			{
				this.stop();
			}
		}
		
		/********************************* SOUTH WEST *****************************************/

		else if(this.getDirection().equals(6) && this.exists.equals(true))
		{
			if(gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false))
			{
				oldDirection = 6;
				//setting next tile
				this.setPosX(this.getPosX() -1);
				((VehicleView)this.getView()).deleteLabel();
				//direction changes -> change of bitmap
				this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
			}
			else
			{
				this.stop();
			}
		}

		/********************************* NORTH WEST *****************************************/

		else if(this.getDirection().equals(8) && this.exists.equals(true))
		{
			if(gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false))
			{
				oldDirection = 8;
				//setting next tile
				this.setPosY(this.getPosY() -1);
				((VehicleView)this.getView()).deleteLabel();
				//direction changes -> change of bitmap
				this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());	//direction to tile
							
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
			}
			else
			{
				this.stop();
			}
		}

	} //end of turnRightRoundabout()
	
	
	/*******************************************************************/
	/**                          TURN LEFT                            **/
	/*******************************************************************/
	
	public void turnLeft() 
	{
		Integer oldDirection = 0;
		
		if (this.counterTurnLeft.equals(0))
		{
			this.counterTurnLeft = 1;
		}	

		//if direction is north and first counterTurnLeft is active
		if(this.getDirection().equals(1) && (this.counterTurnLeft.equals(1)))	//FIRST step for NORTH
		{
			//look left and right
			if (this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true) && this.lookLeftFree(1).equals(true) && this.lookLeftFree(2).equals(true) && this.lookLeftFree(3).equals(true))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1); 	//new position one step north
				((VehicleView)this.getView()).deleteLabel();
				//direction stays the same
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}	
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}

		//if direction is north and second counterTurnLeft is active
		else if(this.getDirection().equals(1) && (this.counterTurnLeft.equals(2)))	//SECOND step for NORTH
		{
			oldDirection = 1;
			//setting next tile
			this.setPosY(this.getPosY() -1); 	//new position one step north
			//direction changes -> change of bitmap
			this.setDirection(8);				//direction to northwest
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is northwest and third counterTurnLeft is active
		else if(this.getDirection().equals(8) && (this.counterTurnLeft.equals(3)))	//THIRD step for NORTH
		{
			//look left and right
			if (this.lookLeftSameLaneFree(1).equals(true) && this.lookLeftSameLaneFree(2).equals(true))
			{
				oldDirection = 8;
				//setting next tile
				this.setPosX(this.getPosX() -1); 	//new position one step west
				//direction changes -> change of bitmap
				this.setDirection(7);				//direction to west
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}

		//if direction is west and fourth counterTurnLeft is active
		else if(this.getDirection().equals(7) && (this.counterTurnLeft.equals(4)))	//FOURTH step for NORTH
		{
			oldDirection = 7;
			//setting next tile
			this.setPosX(this.getPosX() -1); 	//new position one step west
			//no change of direction
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}

		//if direction is west and fifth counterTurnLeft is active
		else if(this.getDirection().equals(7) && (this.counterTurnLeft.equals(5)))	//FIFTH step for NORTH
		{
			if (gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false) &&
					this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1); 	//new position one step west
				//no change of direction
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(0);
			}
			else
			{
				this.stop();
			}
			
		}

		//if direction is east and first counterTurnLeft is active
		else if (this.getDirection().equals(3) && (this.counterTurnLeft.equals(1)))		//FIRST step for EAST
		{
			if(this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true)  && this.lookLeftFree(1).equals(true) && this.lookLeftFree(2).equals(true) && this.lookLeftFree(3).equals(true))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1);  	//new position one step east
				((VehicleView)this.getView()).deleteLabel();
				//direction stays the same
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is east and second counterTurnLeft is active
		else if(this.getDirection().equals(3) && (this.counterTurnLeft.equals(2)))	//SECOND step for EAST
		{
			oldDirection = 3;
			//setting next tile
			this.setPosX(this.getPosX() +1); 	//new position one step east
			//direction changes -> change of bitmap
			this.setDirection(2);				//direction to northwest
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is northeast and third counterTurnLeft is active
		else if(this.getDirection().equals(2) && (this.counterTurnLeft.equals(3)))	//THIRD step for EAST
		{
			//look left and right
			if (this.lookLeftSameLaneFree(1).equals(true) && this.lookLeftSameLaneFree(2).equals(true))
			{
				oldDirection = 2;
				//setting next tile
				this.setPosY(this.getPosY() -1); 	//new position one step north
				//direction changes -> change of bitmap
				this.setDirection(1);				//direction to north
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is north and fourth counterTurnLeft is active
		else if(this.getDirection().equals(1) && (this.counterTurnLeft.equals(4)))	//FOURTH step for EAST
		{
			oldDirection = 1;
			//setting next tile
			this.setPosY(this.getPosY() -1); 	//new position one step north
			//no change of direction
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is north and fifth counterTurnLeft is active
		else if(this.getDirection().equals(1) && (this.counterTurnLeft.equals(5)))	//FIFTH step for EAST
		{
			if (gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false) &&
					this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 1;
				//setting next tile
				this.setPosY(this.getPosY() -1); 	//new position one step north
				//no change of direction
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(0);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is south and first counterTurnLeft is active
		else if (this.getDirection().equals(5) && (this.counterTurnLeft.equals(1)))		//FIRST step for SOUTH
		{
			if(this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true) && this.lookLeftFree(1).equals(true) && this.lookLeftFree(2).equals(true) && this.lookLeftFree(3).equals(true))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1);  	//new position one step south
				((VehicleView)this.getView()).deleteLabel();
				//direction stays the same
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is south and second counterTurnLeft is active
		else if(this.getDirection().equals(5) && (this.counterTurnLeft.equals(2)))	//SECOND step for SOUTH
		{
			oldDirection = 5;
			//setting next tile
			this.setPosY(this.getPosY() +1); 	//new position one step south
			//direction changes -> change of bitmap
			this.setDirection(4);				//direction to southeast
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is southeast and third counterTurnLeft is active
		else if(this.getDirection().equals(4) && (this.counterTurnLeft.equals(3)))	//THIRD step for SOUTH
		{
			//look left and right
			if (this.lookLeftSameLaneFree(1).equals(true) && this.lookLeftSameLaneFree(2).equals(true))
			{
				oldDirection = 4;
				//setting next tile
				this.setPosX(this.getPosX() +1); 	//new position one step east
				//direction changes -> change of bitmap
				this.setDirection(3);				//direction to east
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is east and fourth counterTurnLeft is active
		else if(this.getDirection().equals(3) && (this.counterTurnLeft.equals(4)))	//FOURTH step for SOUTH
		{
			oldDirection = 3;
			//setting next tile
			this.setPosX(this.getPosX() +1); 	//new position one step east
			//no change of direction
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is east and fifth counterTurnLeft is active
		else if(this.getDirection().equals(3) && (this.counterTurnLeft.equals(5)))	//FIFTH step for SOUTH
		{
			if (gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false) &&
					this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 3;
				//setting next tile
				this.setPosX(this.getPosX() +1); 	//new position one step east
				//no change of direction
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(0);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is west and first counterTurnLeft is active
		else if (this.getDirection().equals(7) && (this.counterTurnLeft.equals(1)))		//FIRST step for WEST
		{
			if(this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true) && this.lookLeftFree(1).equals(true) && this.lookLeftFree(2).equals(true) && this.lookLeftFree(3).equals(true))
			{
				oldDirection = 7;
				//setting next tile
				this.setPosX(this.getPosX() -1);  	//new position one step west
				((VehicleView)this.getView()).deleteLabel();
				//direction stays the same
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is west and second counterTurnLeft is active
		else if(this.getDirection().equals(7) && (this.counterTurnLeft.equals(2)))	//SECOND step for WEST
		{
			oldDirection = 7;
			//setting next tile
			this.setPosX(this.getPosX() -1); 	//new position one step west
			//direction changes -> change of bitmap
			this.setDirection(6);				//direction to southwest
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is southwest and third counterTurnLeft is active
		else if(this.getDirection().equals(6) && (this.counterTurnLeft.equals(3)))	//THIRD step for WEST
		{
			//look left and right
			if (this.lookLeftSameLaneFree(1).equals(true) && this.lookLeftSameLaneFree(2).equals(true))
			{
				oldDirection = 6;
				//setting next tile
				this.setPosY(this.getPosY() +1); 	//new position one step south
				//direction changes -> change of bitmap
				this.setDirection(5);				//direction to south
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
			}
			else
			{
				this.stop();
			}
		}
		
		//if direction is south and fourth counterTurnLeft is active
		else if(this.getDirection().equals(5) && (this.counterTurnLeft.equals(4)))	//FOURTH step for WEST
		{
			oldDirection = 5;
			//setting next tile
			this.setPosY(this.getPosY() +1); 	//new position one step south
			//no change of direction
			((VehicleView)this.getView()).deleteLabel();
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
			}
			else if (this instanceof TruckModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
			}
			this.setCounterTurnLeft(this.getCounterTurnLeft()+1);
		}
		
		//if direction is south and fifth counterTurnLeft is active
		else if(this.getDirection().equals(5) && (this.counterTurnLeft.equals(5)))	//FIFTH step for WEST
		{
			if (gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false) &&
					this.lookAheadFree(1).equals(true) && this.lookAheadFree(2).equals(true))
			{
				oldDirection = 5;
				//setting next tile
				this.setPosY(this.getPosY() +1); 	//new position one step south
				//no change of direction
				((VehicleView)this.getView()).deleteLabel();
				
				if (this instanceof BusModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));
				}
				else if (this instanceof TruckModel)
				{
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(oldDirection, this.getDirection())));
				}
				this.setCounterTurnLeft(0);
			}
			else
			{
				this.stop();
			}
		}

	} //end of turnLeft()
	
	
		/*******************************************************************/
	/**                            EVADE                              **/
	/*******************************************************************/
	

	public synchronized void evade() 
	{
		Integer oldDirection = 0;
		
		if (this.counterEvading.equals(0))
		{
			this.counterEvading = 1;
		}
		else if (this.counterEvading.equals(1))
		{
			this.counterEvading = 2;
		}
		else if (this.counterEvading.equals(2))
		{
			this.counterEvading = 3;
		}
		else if (this.counterEvading.equals(3))
		{
			this.counterEvading = 4;
		}

		
		//NORTH
		if (this.getDirection().equals(1) && (this.counterEvading.equals(1)))	//first step for north
		{
			oldDirection = 1;
			//setting next tile
			this.setPosX(this.getPosX() -1);
			this.setPosY(this.getPosY() -1);	//new position one step northwest
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(8); 	//change direction for vehicle to northwest
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		
		else if (this.getDirection().equals(8) && (this.counterEvading.equals(2)))	//second step for north
		{
			oldDirection = 8;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(1); 	//change direction for vehicle to north
			
			if (this instanceof BusModel)
			{
				this.setPosY(this.getPosY() -1);	//new position one step north
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosY(this.getPosY() -1);	//new position one step north
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(1, this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(1) && (this.counterEvading.equals(3)))	//third step for north
		{
			oldDirection = 1;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(2); 	//change direction for vehicle to northeast
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step northeast
				this.setPosY(this.getPosY() -1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step northeast
				this.setPosY(this.getPosY() -1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step northeast
				this.setPosY(this.getPosY() -1);
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(2) && (this.counterEvading.equals(4)))	//fourth step for north
		{
			oldDirection = 2;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			
			if (gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-1).equals(false) &&
					gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()-2).equals(false))
			{
				if (this instanceof BusModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setPosY(this.getPosY() -1);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(1, this.getDirection())));				//also change bitmap
				}
				this.setCounterEvading(5);
			}
			else
			{
				this.stop();
			}
			
		}
		
		//EAST
		if (this.getDirection().equals(3) && (this.counterEvading.equals(1)))	//first step for east
		{
			oldDirection = 3;
			//setting next tile
			this.setPosX(this.getPosX() +1);
			this.setPosY(this.getPosY() -1);	//new position one step northeast
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(2); 	//change direction for vehicle to northeast
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		
		else if (this.getDirection().equals(2) && (this.counterEvading.equals(2)))	//second step for east
		{
			oldDirection = 2;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(3); 	//change direction for vehicle to east
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step east
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step east
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(3, this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(3) && (this.counterEvading.equals(3)))	//third step for east
		{
			oldDirection = 3;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(4); 	//change direction for vehicle to southeast
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step southeast
				this.setPosY(this.getPosY() +1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step southeast
				this.setPosY(this.getPosY() +1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() +1);	//new position one step southeast
				this.setPosY(this.getPosY() +1);
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(4) && (this.counterEvading.equals(4)))	//fourth step for east
		{
			oldDirection = 4;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			
			if (gridModel.findOneVehicleInModelsList(this.getPosX()+1, this.getPosY()).equals(false) &&
					gridModel.findOneVehicleInModelsList(this.getPosX()+2, this.getPosY()).equals(false))
			{
				if (this instanceof BusModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setPosX(this.getPosX() +1);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
				}
				else if (this instanceof TruckModel)
				{
					((TruckModel)this).setOldDirection(oldDirection);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(3, this.getDirection())));				//also change bitmap
				}
				this.setCounterEvading(5);
			}
			else
			{
				this.stop();
			}
		}
		
		//SOUTH
		if (this.getDirection().equals(5) && (this.counterEvading.equals(1)))	//first step for south
		{
			oldDirection = 5;
			//setting next tile - next tile = same tile
			this.setPosX(this.getPosX() +1);
			this.setPosY(this.getPosY() +1);	//new position one step southeast
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(4); 	//change direction for vehicle to southeast
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}		
		else if (this.getDirection().equals(4) && (this.counterEvading.equals(2)))	//second step for south
		{
			oldDirection = 4;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(5); 	//change direction for vehicle to south
			
			if (this instanceof BusModel)
			{
				this.setPosY(this.getPosY()+1); 	//new position one step south
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosY(this.getPosY()+1); 	//new position one step south
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(5, this.getDirection())));					//also change bitmap
			}
		}
		else if (this.getDirection().equals(5) && (this.counterEvading.equals(3)))	//third step for south
		{
			oldDirection = 5;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(6); 	//change direction for vehicle to southwest
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step southwest
				this.setPosY(this.getPosY() +1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step southwest
				this.setPosY(this.getPosY() +1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step southwest
				this.setPosY(this.getPosY() +1);
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(6) && (this.counterEvading.equals(4)))	//fourth step for south
		{
			oldDirection = 6;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			
			if (gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+1).equals(false) &&
					gridModel.findOneVehicleInModelsList(this.getPosX(), this.getPosY()+2).equals(false))
			{
				if (this instanceof BusModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setPosY(this.getPosY() +1);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
				}
				else if (this instanceof TruckModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(5, this.getDirection())));				//also change bitmap
				}
				this.setCounterEvading(5);
			}
			else
			{
				this.stop();
			}
		}
		
		//WEST
		if (this.getDirection().equals(7) && (this.counterEvading.equals(1)))	//first step for west
		{
			oldDirection = 7;
			//setting next tile - next tile = same tile
			this.setPosX(this.getPosX() -1);
			this.setPosY(this.getPosY() +1);	//new position one step southwest
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(6); 	//change direction for vehicle to southwest
			
			if (this instanceof BusModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(6) && (this.counterEvading.equals(2)))	//second step for west
		{
			oldDirection = 6;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(7); 	//change direction for vehicle to west
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step west
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step west
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(7, this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(7) && (this.counterEvading.equals(3)))	//third step for west
		{
			oldDirection = 7;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			//set direction
			this.setDirection(8); 	//change direction for vehicle to northwest
			
			if (this instanceof BusModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step northwest
				this.setPosY(this.getPosY() -1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
			}
			else if (this instanceof PassengerCarModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step northwest
				this.setPosY(this.getPosY() -1);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
			}
			else if (this instanceof TruckModel)
			{
				this.setPosX(this.getPosX() -1);	//new position one step northwest
				this.setPosY(this.getPosY() -1);
				((TruckModel)this).setOldDirection(oldDirection);
				this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(((TruckModel)this).getOldDirection(), this.getDirection())));				//also change bitmap
			}
		}
		else if (this.getDirection().equals(8) && (this.counterEvading.equals(4)))	//fourth step for west
		{
			oldDirection = 8;
			//direction changes-> change of bitmap
			((VehicleView)this.getView()).deleteLabel();
			
			if (gridModel.findOneVehicleInModelsList(this.getPosX()-1, this.getPosY()).equals(false) &&
					gridModel.findOneVehicleInModelsList(this.getPosX()-2, this.getPosY()).equals(false))
			{
				if (this instanceof BusModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForBusses(oldDirection, this.getDirection())));			//also change bitmap
				}
				else if (this instanceof PassengerCarModel)
				{
					this.setPosX(this.getPosX() -1);
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilder(this.getColor(), this.getDirection())));				//also change bitmap
				}
				else if (this instanceof TruckModel)
				{
					this.setDirection(gridModel.getActualTilesArray()[this.getPosY()-1][this.getPosX()-1].getDirection());
					((TruckModel)this).setOldDirection(oldDirection);
					this.setBitmap(new Bitmap(randomComponents.bitmapPathBuilderForTrucks(7, this.getDirection())));				//also change bitmap
				}
				this.setCounterEvading(5);
			}
			else
			{
				this.stop();
			}
		}

		if (this.counterEvading.equals(5))
		{
			this.counterEvading = 0;
		}
	} //end of evade()

	
	
	//Method: change lane :for further use
	void changeLane() {
	}
	
	//Method: veer :for further use
	void veer() {
	} // Don't veer for a deer!
	
	
	//NAGEL-SCHRECKENBERG
	//Method: accelerate :for further use
	void accelerate() {
	}
	//Method: brake :for further use

	void brake() {
	}
	
	//Method: drive :for further use

	void drive() {
	}
	//Method: dillyDally :for further use
	void dillyDally() {
	} // trödeln :-) 
	
	//Method: checkSpeedLimit :for further use
	abstract void checkSpeedLimit();

	//Method: checkWeightLimit :for further use
	abstract void checkWeightLimit();

	//Method: nodeReached : crossing or roundAbout reached, 
	public synchronized boolean nodeReached() {
		
		int x,y;
		y = currentPosition.getPosY();
		x = currentPosition.getPosX();
		
		return nodeReached(y,x);
		
	}
	//Method: nodeReached : crossing or roundAbout reached
	public boolean nodeReached(int x, int y) {

		RoadTileModel position = (RoadTileModel) gridModel
				.getActualTilesArray()[y][x];

		if (position instanceof CrossingTileModel
				|| position instanceof RoundAboutCrossingTileModel)
		// this condition should be replaced by a Dijkstra Node linked to the
		// Tiles

		{
			return true;
		} else {
			return false;
		}
	}
	//Method: exitReached
	public boolean exitReached(int x, int y) {

		RoadTileModel position = (RoadTileModel) gridModel
				.getActualTilesArray()[y][x];

		if (position instanceof ExitRoadTileModel)
		{
			return true;
		} else {
			return false;
		}
	}

	//Method: exitReached
	public synchronized boolean exitReached() {

		int x,y;
		
		y = currentPosition.getPosY();
		x = currentPosition.getPosX();
		
		return exitReached(y,x);
	}
	//Method: busStopReached	
	public boolean busStopReached(int x, int y) {

		RoadTileModel position = (RoadTileModel) gridModel
				.getActualTilesArray()[y][x];

		if (position instanceof BusStopRoadTileModel)
		{
			return true;
		} else {
			return false;
		}
	}
	//Method: busStopReached
	public synchronized boolean busStopReached() {

		int x,y;
		
		y = currentPosition.getPosY();
		x = currentPosition.getPosX();
		
		return busStopReached(y,x);
	}

	
}