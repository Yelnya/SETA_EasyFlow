package model;

import java.util.ArrayList;

public class RoadTileModel extends TileModel {
	
	private Integer mainDirection;
	private String tileType = "RoadTile";
	private VehicleModel vehicle=null;
	private ArrayList<TrafficSignValidityArea> trafficSignValidityArea=null;
	// not clear if really a list of areas is needed, if there are several 
	// traffic signs of different type and relevance of different area then yes
	public boolean isDrivable() {
		return true;
	}
	
	public RoadTileModel()
	{
		super();
	}
	
	public RoadTileModel(int posX, int posY, Integer direction)
	{
		super(posX, posY, direction);
		setTileType(tileType);
		this.mainDirection = direction;
		
	}

	
	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	public Integer getDirection() {
		return mainDirection;
	}
	
	
	public void addVehicle(VehicleModel vehicle ) { 
		this.vehicle=vehicle;
	}

	public void removeVehicle(VehicleModel vehicle ) { 
			this.vehicle=vehicle;
	}
	
	
}
