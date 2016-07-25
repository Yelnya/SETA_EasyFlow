package controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import util.Direction;
import util.LogUtility;
import util.RandomComponents;
import model.*;

public class BasicRules {

	private static Logger logger = Logger.getLogger(LogUtility
			.getName(BasicRules.class.getName()));
	RandomComponents randomComponents = new RandomComponents();

	private GridModel gridModel = null;
	@SuppressWarnings("unused")
	private TileModel[][] tilesArray = null;
	private ArrayList<VehicleModel> vehiclesArray = null;
	private ArrayList<TrafficLightModel> trafficLightsArray = null;
	@SuppressWarnings("unused")
	private Integer cycleCounter = null;
	@SuppressWarnings("unused")
	private ArrayList<TrafficsignModel> trafficSignArray = null;
	private DataModelSingleton model = null;
	private ArrayList<VehicleModel> movedVehicles = null;
	private ArrayList<VehicleCoordinates> createdVehicles = null;
	private ArrayList<VehicleModel> deletedVehicles = null;
	private ArrayList<TrafficLightModel> modifiedTrafficLights = null;
	private ArrayList<TrafficsignModel> createdTrafficSigns = null;
	private ArrayList<TrafficsignModel> deletedTrafficSigns = null;
//	private DijkstraRules dijkstraRules = null;

	@SuppressWarnings("unused")
	private VehicleCoordinates newVehiclePos;

	public BasicRules(GridModel gridModel, Integer cycleCounter) {
		this.gridModel = gridModel;
		this.model = DataModelSingleton.getInstance();
//		dijkstraRules = new DijkstraRules(gridModel);
		movedVehicles = new ArrayList<VehicleModel>();
		createdVehicles = new ArrayList<VehicleCoordinates>();
		deletedVehicles = new ArrayList<VehicleModel>();
		modifiedTrafficLights = new ArrayList<TrafficLightModel>();
		createdTrafficSigns = new ArrayList<TrafficsignModel>();
		deletedTrafficSigns = new ArrayList<TrafficsignModel>();
		logger.setLevel(LogUtility.getLevel(Level.INFO));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
	}

	/*******************************************************************/
	/**     exectue(VehicleCoordinates newVehiclePos)                 **/
	/**                                                               **/
	/**     executes rules for calculation of next simulation step    **/
	/**                                                               **/
	/*******************************************************************/

	public void execute(VehicleCoordinates newVehiclePos) {
		this.tilesArray = gridModel.getActualTilesArray();
		cycleCounter = 0;
		executeVehicleRule(newVehiclePos);
		executeTrafficLightRule();
		executeTrafficSignRule();
		gridModel.howManyVehiclesInList();
		gridModel.detectTrafficJam();
	}

	
	/*******************************************************************/
	/**     executeVehicleRule(VehicleCoordinates newVehiclePos)      **/
	/**                                                               **/
	/**     executes vehicle specific rules                           **/
	/**                                                               **/
	/*******************************************************************/

	public void executeVehicleRule(VehicleCoordinates newVehiclePos) {
		this.vehiclesArray = gridModel.getActualVehiclesArray();
		movedVehicles.clear();
		createdVehicles.clear();
		deletedVehicles.clear();
		// handle existing vehicles
		if (vehiclesArray.size()>0)
		for (VehicleModel vehicleModel : vehiclesArray) {
			// general handling
			scanAhead(vehicleModel);

			if (vehicleModel instanceof PassengerCarModel) {
				// specific handling
			}
			if (vehicleModel instanceof BusModel) {
				// specific handling
			}
			if (vehicleModel instanceof TruckModel) {
				// specific handling
			}
		}
		// handle new vehicles
		if (newVehiclePos != null)
		{
			createdVehicles.add(newVehiclePos);
		} 
	}
	/*******************************************************************/
	/**     executeTrafficLightRule()                                 **/
	/**                                                               **/
	/**     executes traffic light specific rules,                    **/ 
	/**     triggers the update of counters and switches lights      **/
	/**                                                               **/
	/*******************************************************************/

	public void executeTrafficLightRule() {
		this.trafficLightsArray = gridModel.getActualTrafficLightsArray();
		modifiedTrafficLights.clear();


		// if period reached, switch traffic lights and prepare list for
		// propagation

		// Note: for specifc turn around time per traffic light, counter is
		// maintained in traffic light model
		for (TrafficLightModel trafficLightModel : trafficLightsArray) {
			if (trafficLightModel.switchTrafficLightIntervall()) {
			modifiedTrafficLights.add(trafficLightModel);
			}
		}

	}

	/*******************************************************************/
	/**     executeTrafficSignRule()                                  **/
	/**                                                               **/
	/**     executes traffic sign specif ic rules,                    **/ 
	/**     for future use!                                           **/
	/*******************************************************************/

	@SuppressWarnings("unused")
	public void executeTrafficSignRule() {
		this.trafficSignArray = gridModel.getActualTrafficSignsArray();
		createdTrafficSigns = model.getControllerCreatedTrafficSigns();
		deletedTrafficSigns = model.getControllerDeletedTrafficSigns();

		for (TrafficLightModel trafficLightsModel : trafficLightsArray) {
			// general handling
		}

	}

	/*******************************************************************/
	/**  propagateChanges                  							  **/
	/**  triggers propagation of model changes,						  **/	
	/**  view will update as well, therefore call is performed using  **/
	/**  invokeAndWait method in a safe way                           **/
	/*******************************************************************/

	public void propagateChanges() {
		// Note this call need to be thread save since Model update triggers
		// implicitly View/Swing update
		
		try {
			SwingUtilities.invokeAndWait(new PropagateGridModelUpdate(
					movedVehicles, createdVehicles, deletedVehicles,
					createdTrafficSigns, deletedTrafficSigns,
					modifiedTrafficLights));

		} 
		
		catch (InterruptedException e) {
			logger.info("Controller thread received exit interrupt while propagation!");
		}
		
		catch (Exception e) {
			e.printStackTrace();
			logger.severe("Exception during invokeAndWait (modelUpdate)" + e);

		}

	}

	/*******************************************************************/
	/**                           SCAN AHEAD                          **/
	/**                                                               **/           
	/*******************************************************************/

	//scan of tile 1 step ahead - car moves one step straightAhead() if there are no obstacles (red traffic light or the like)
	public void scanAhead(VehicleModel vehicle)
	{
		Integer x = vehicle.getPosX();
		Integer y = vehicle.getPosY();
		Integer direction = vehicle.getDirection();
		

		logger.fine("Entering ScanAhead!");
		logger.fine("PosX=" + vehicle.getPosX());
		logger.fine("PosY=" + vehicle.getPosY());
		logger.fine("Direction=" + vehicle.getDirection());
		
		try {
			//if turning is in transaction, continue turning procedure (right or left, evading or take over)
			if ((vehicle.getCounterTurnRight().equals(0)==false) && vehicle.getExists().equals(true))
			{
				vehicle.turnRight();
				movedVehicles.add(vehicle);
			}
			else if ((vehicle.getCounterTurnLeft().equals(0)==false) && vehicle.getExists().equals(true))
			{
				vehicle.turnLeft();
				movedVehicles.add(vehicle);
			}
			else if ((vehicle.getCounterTurnStraight().equals(0)==false) && vehicle.getExists().equals(true))
			{
				vehicle.straightAheadRoundAbout();
				movedVehicles.add(vehicle);
			}
			else if ((vehicle.getCounterTurnStraightCrossing().equals(0)==false) && vehicle.getExists().equals(true))
			{
				vehicle.straightAheadIfCrossing();
				movedVehicles.add(vehicle);
			}
			else if ((vehicle.getCounterEvading().equals(0)==false) && vehicle.getExists().equals(true))
			{
				vehicle.evade();
				movedVehicles.add(vehicle);
			}
			
			
			//else look at environment
			
			/***************************** NORTH ************************************/
			else if (direction.equals(Direction.NORTH) && vehicle.getExists().equals(true))	//Norden
			{
				y--;
				
				//scan if tile in direction north, y-1, is an ExitRoad
				if (gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("ExitRoad"))
				{
					//logger.fine("Tile in north is exitroad tile");
					vehicle.setExists(false);
					deletedVehicles.add(vehicle);
				}
				//if there is a construction sign blocking the way
				else if (gridModel.scanForConstructionSign(x, y).equals(true))
				{
					//only if no obstacle behind construction sign or on other lane
					if (gridModel.findOneVehicleInModelsList(x-1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-1, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-1, y-2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y-2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-1, y+1).equals(false))
					{
						vehicle.evade();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				
				//if roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					vehicle.turnRight();
					movedVehicles.add(vehicle);
				}
				
				//scan if tile in direction north, y-1, is a crossing
				else if (x > 2 && y > 2 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
						gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("CrossingTile"))
				{
					//look at traffic light on this position - if it is red and facing the car's direction, stop()
					if (gridModel.watchTrafficLight(x, y, direction).equals("red"))
					{
						vehicle.stop();
					}
					else if (gridModel.watchTrafficLight(x, y, direction).equals("green") )
					{
						if (vehicle.getCounterReactionTime() == 2)
						{
							//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
							//@Thommy:
							
							Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
							switch (decision)
							{
								case 1 : 
										if (gridModel.getActualTilesArray()[y-1][x].getTileType().equals("RoadTile"))
										{
											vehicle.turnRight(); break;
										}
										else 
										{
											decision++;
										}
								case 2 : if (gridModel.getActualTilesArray()[y-1][x-3].getTileType().equals("RoadTile")) 
										{
											vehicle.turnLeft(); break; 
										}
										else
										{
											decision--;
										}
								case 3 : if (gridModel.getActualTilesArray()[y-3][x-1].getTileType().equals("RoadTile"))
										{
											vehicle.straightAheadIfCrossing(); break;
										}
										else
										{
											decision--;
										}
								default : break;
							}
							

							movedVehicles.add(vehicle);
							vehicle.setCounterReactionTime(0); 	//reset reactionCounter
						}

						vehicle.setCounterReactionTime(vehicle.getCounterReactionTime()+1); 	//raise reactionCounter by 1
					}
					//maybe there is no traffic light before the crossing
					else if (x > 2 && y > 2 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
							gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("CrossingTile") &&
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false)) 
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y-1][x].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y-1][x-3].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y-3][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.straightAheadIfCrossing(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}

						movedVehicles.add(vehicle);
					}
					
					//maybe there is no traffic light and no traffic sign on the crossing -> only at one way crossings
					else if (gridModel.findGiveWayTrafficSignInModelsList(x+1, y+1, vehicle.getDirection()).equals(false) && 
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false)) 	//no traffic Sign stands one cell right
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y-1][x].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y-1][x-3].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y-3][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.straightAhead(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}
						movedVehicles.add(vehicle);
						
					}
					else
					{
						vehicle.stop();
					}
				}
				
				//if roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					vehicle.turnRight();
					movedVehicles.add(vehicle);
				}

				//if there is no obstacle in way, continue
				else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
				{
					vehicle.stop(); 
				}
				else
				{
					vehicle.straightAhead();
					movedVehicles.add(vehicle);
				}
			}
			/************************************** NORTH EAST **********************************************/
			else if (direction.equals(Direction.NORTH_EAST) && vehicle.getExists().equals(true))	//north-east
			{
				//IF BUSSTOP
				if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("BusStopRoadTile"))
				{
					x++;
					y--;
					//if Tile in north is road, scan road 5 tiles back in west
					if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoadTile"))
					{
						//scan 5 steps back for cars and only leave busstop if road is free
						for (int i = 0; i < 5; i++)
						{
							if (gridModel.findVehicleInModelsList(x-i, y, vehicle.getDirection()).equals(true))
							{
								logger.fine("Road blocked - bus is not leaving bus stop");
								vehicle.stop();
								return;		//leave method
							}
						}
						//bus leaves bus stop
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
				//if scanning a roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
					//@Thommy:

					Integer decision = randomComponents.randomNumberBetweenBounds(1, 2);
					switch (decision)
					{
						case 1 : vehicle.turnRightRoundabout(); break;
						case 2 : vehicle.straightAheadRoundAbout(); break; 
						default : break;
					}
					
					
					movedVehicles.add(vehicle);
					

					
				}
				
				//IF CURVED ROAD -> road continuing north
				else if (x > 2 && y > 2 && gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y-2][x-1].getDirection().equals(1) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false &&
								gridModel.findOneVehicleInModelsList(x, y-2).equals(false))
				{
					if (gridModel.findOneVehicleInModelsList(x, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y-2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y-3).equals(false))
					{
						y--;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				//IF CURVED ROAD -> road continuing east
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y-1][x].getDirection().equals(3) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{
					
					if (gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+3, y).equals(false))
					{
						x++;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					y--;
					x++;
					if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
			}
			/****************************************** EAST *************************************/
			else if (direction.equals(Direction.EAST) && vehicle.getExists().equals(true))	//Osten
			{
				x++;
				
				//scan if tile in direction east, x+1, is an ExitRoad
				if (x > 0 && y > 0 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("ExitRoad"))
				{
					//logger.fine("Tile in east is exitroad tile");
					vehicle.setExists(false);
					deletedVehicles.add(vehicle);
				}
				//if there is a construction sign blocking the way
				else if (gridModel.scanForConstructionSign(x, y).equals(true))
				{
					//only if no obstacle behind construction sign or on other lane
					if (gridModel.findOneVehicleInModelsList(x, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-1, y-1).equals(false))
					{
						vehicle.evade();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				
				//if roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					vehicle.turnRight();
					movedVehicles.add(vehicle);
				}

				
				//scan if tile in direction east and the following as well, x+1 and x+2, is a crossing
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
						gridModel.getActualTilesArray()[y-1][x].getTileType().equals("CrossingTile"))
				{
					//look at traffic light on vehicle position - if it is red, stop()
					if (gridModel.watchTrafficLight(x, y, direction).equals("red"))
					{
						vehicle.stop();
					}
					else if (gridModel.watchTrafficLight(x, y, direction).equals("green") )
					{
						if (vehicle.getCounterReactionTime() == 2)
						{
							//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
							//@Thommy:
							
							Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
							switch (decision)
							{
								case 1 : 
										if (gridModel.getActualTilesArray()[y][x-1].getTileType().equals("RoadTile"))
										{
											vehicle.turnRight(); break;
										}
										else 
										{
											decision++;
										}
								case 2 : if (gridModel.getActualTilesArray()[y-3][x-1].getTileType().equals("RoadTile")) 
										{
											vehicle.turnLeft(); break; 
										}
										else
										{
											decision--;
										}
								case 3 : if (gridModel.getActualTilesArray()[y-1][x+1].getTileType().equals("RoadTile"))
										{
											vehicle.straightAheadIfCrossing(); break;
										}
										else
										{
											decision--;
										}
								default : break;
							}
							
							movedVehicles.add(vehicle);
							vehicle.setCounterReactionTime(0); 	//reset reactionCounter
							

						}

						vehicle.setCounterReactionTime(vehicle.getCounterReactionTime()+1); 	//raise reactionCounter by 1
					}
					//maybe there is no traffic light on the crossing
					else if (x > 1 && y >1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
							gridModel.getActualTilesArray()[y-1][x].getTileType().equals("CrossingTile") &&
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))	
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y-3][x-1].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y-1][x+1].getTileType().equals("RoadTile"))
									{
										vehicle.straightAheadIfCrossing(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}
						
						movedVehicles.add(vehicle);
						
					}
					
					//maybe there is no traffic light and no traffic sign on the crossing -> only at one way crossings
					else if (gridModel.findGiveWayTrafficSignInModelsList(x-1, y+1, vehicle.getDirection()).equals(false) && 
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))	
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
					{
						vehicle.stop();
					}
					else
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}

				//if there are no obstacles, move one step straightAhead()
				else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
				{
					vehicle.stop(); 
				}
				else
				{
					vehicle.straightAhead();
					movedVehicles.add(vehicle);
				}
			}
			/****************************************** SOUTH EAST ***************************************/
			else if (direction.equals(Direction.SOUTH_EAST) && vehicle.getExists().equals(true))	//south-east
			{
				//IF BUSSTOP
				if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("BusStopRoadTile"))
				{
					x++;
					y++;
					//if Tile in east is road, scan road 5 tiles back in north
					if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoadTile"))
					{
						//scan 5 steps back for cars and only leave busstop if road is free
						for (int i = 0; i < 5; i++)
						{
							if (gridModel.findVehicleInModelsList(x, y-i, vehicle.getDirection()).equals(true))
							{
								vehicle.stop();
								return;		//leave method
							}
						}
						//bus leaves bus stop
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
				//if scanning a roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
					//@Thommy:

					Integer decision = randomComponents.randomNumberBetweenBounds(1, 2);
					switch (decision)
					{
						case 1 : vehicle.turnRightRoundabout(); break;
						case 2 : vehicle.straightAheadRoundAbout(); break; 
						default : break;
					}
					
					
					movedVehicles.add(vehicle);
					
					
				}
				
				//IF CURVED ROAD -> road continuing east
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y-1][x].getDirection().equals(3) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{
					
					if (gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+3, y).equals(false))
					{
						x++;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
						
					}
					else
					{
						vehicle.stop();
					}
				}
				//IF CURVED ROAD -> road continuing south
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y][x-1].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y][x-1].getDirection().equals(5) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{
					
					if (gridModel.findOneVehicleInModelsList(x, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+3).equals(false))
					{
						y++;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}				
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					y++;
					x++;
					vehicle.straightAhead();
					movedVehicles.add(vehicle);
				}
			}
			
			/********************************** SOUTH *****************************************/
			else if (direction.equals(Direction.SOUTH) && vehicle.getExists().equals(true))	//Süden
			{
				//scan if tile in direction south, y+1, is a crossing
				y++;
				
				//scan if tile in direction south, y+1, is an ExitRoad
				if (gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("ExitRoad"))
				{
					vehicle.setExists(false);
					deletedVehicles.add(vehicle);
				}
				//if there is a construction sign blocking the way
				else if (gridModel.scanForConstructionSign(x, y).equals(true))
				{
					//only if no obstacle behind construction sign or on other lane
					if (gridModel.findOneVehicleInModelsList(x, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y-1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y+2).equals(false))
					{
						vehicle.evade();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				
				//if roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					vehicle.turnRight();
					movedVehicles.add(vehicle);
				}

				
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
						gridModel.getActualTilesArray()[y][x-1].getTileType().equals("CrossingTile"))
				{
					//look at traffic light on vehicle position - if it is red, stop()
					if (gridModel.watchTrafficLight(x, y, direction).equals("red"))
					{
						vehicle.stop();
					}
					else if (gridModel.watchTrafficLight(x, y, direction).equals("green") )
					{
						if (vehicle.getCounterReactionTime() == 2)
						{
							//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
							//@Thommy:
							
							Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
							switch (decision)
							{
								case 1 : 
										if (gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("RoadTile"))
										{
											vehicle.turnRight(); break;
										}
										else 
										{
											decision++;
										}
								case 2 : if (gridModel.getActualTilesArray()[y-1][x+1].getTileType().equals("RoadTile")) 
										{
											vehicle.turnLeft(); break; 
										}
										else
										{
											decision--;
										}
								case 3 : if (gridModel.getActualTilesArray()[y+1][x-1].getTileType().equals("RoadTile"))
										{
											vehicle.straightAheadIfCrossing(); break;
										}
										else
										{
											decision--;
										}
								default : break;
							}
							movedVehicles.add(vehicle);
							vehicle.setCounterReactionTime(0); 	//reset reactionCounter

						}

						vehicle.setCounterReactionTime(vehicle.getCounterReactionTime()+1); 	//raise reactionCounter by 1
					}
					//maybe there is no traffic light on the crossing
					else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
							gridModel.getActualTilesArray()[y][x-1].getTileType().equals("CrossingTile") &&
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y-1][x+1].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y+1][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.straightAheadIfCrossing(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}
						
						movedVehicles.add(vehicle);
					}
					
					//maybe there is no traffic light and no traffic sign on the crossing -> only at one way crossings
					else if (gridModel.findGiveWayTrafficSignInModelsList(x-1, y-1, vehicle.getDirection()).equals(false) && 
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y-1][x+1].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y+1][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.straightAhead(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}
						movedVehicles.add(vehicle);
					}
					else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
					{
						vehicle.stop();
					}
					else
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}

//				//if there are no obstacles, move one step straightAhead()
				else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
				{
					vehicle.stop(); 
				}
				else
				{
					vehicle.straightAhead();
					movedVehicles.add(vehicle);
				}
			}
			/*************************************** SOUTH WEST **************************************/
			else if (direction.equals(Direction.SOUTH_WEST ) && vehicle.getExists().equals(true))	//south-east
			{
				//IF BUSSTOP
				if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("BusStopRoadTile"))
				{
					x--;
					y++;
					//if Tile in north is road, scan road 5 tiles back in west
					if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoadTile"))
					{
						//scan 5 steps back for cars and only leave busstop if road is free
						for (int i = 0; i < 5; i++)
						{
							if (gridModel.findVehicleInModelsList(x+i, y, vehicle.getDirection()).equals(true))
							{
								vehicle.stop();
								return;		//leave method
							}
						}
						//bus leaves bus stop
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
				
				//if scanning a roundabout crossing, determine next destination
				else if (x > 2 && y > 2 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
					//@Thommy:

					Integer decision = randomComponents.randomNumberBetweenBounds(1, 2);
					switch (decision)
					{
						case 1 : vehicle.turnRightRoundabout(); break;
						case 2 : vehicle.straightAheadRoundAbout(); break; 
						default : break;
					}
					
					movedVehicles.add(vehicle);
					
				}
				
				//IF CURVED ROAD -> road continuing south
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y][x-1].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y][x-1].getDirection().equals(5) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{
					if (gridModel.findOneVehicleInModelsList(x, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+3).equals(false))
					{
						y++;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else 
					{
						vehicle.stop();
					}
				}
				//IF CURVED ROAD -> road continuing west
				else if (x > 2 && y > 2 && gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y-1][x-2].getDirection().equals(7) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{
					
					if (gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+3, y).equals(false))
					{
						x--;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else 
					{
						vehicle.stop();
					}
				}				
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					y++;
					x--;
					if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
					{
						vehicle.stop();
					}
					else 
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
			}
			/******************************************* WEST **********************************/
			else if (direction.equals(Direction.WEST) && vehicle.getExists().equals(true))	//Westen
			{
				//scan if tile in direction west, x-1, is a crossing
				x--;
				
				//scan if tile in direction west, x-1, is an ExitRoad
				if (x > 0 && y > 0 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("ExitRoad"))
				{
					vehicle.setExists(false);
					deletedVehicles.add(vehicle);
				}
				//if there is a construction sign blocking the way
				else if (gridModel.scanForConstructionSign(x, y).equals(true))
				{
					//only if no obstacle behind construction sign or on other lane
					if (gridModel.findOneVehicleInModelsList(x-1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+1, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-1, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x-2, y+1).equals(false))
					{
						vehicle.evade();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				
				//if roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					vehicle.turnRight();
					movedVehicles.add(vehicle);
				}

				
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
						gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("CrossingTile"))
				{
					//look at traffic light on this position - if it is red, stop()
					if (gridModel.watchTrafficLight(x, y, direction).equals("red"))
					{
						vehicle.stop();
					}
					else if (gridModel.watchTrafficLight(x, y, direction).equals("green") )
					{
						if (vehicle.getCounterReactionTime() == 2)
						{
							//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
							//@Thommy:
							
							Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
							switch (decision)
							{
								case 1 : 
										if (gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("RoadTile"))
										{
											vehicle.turnRight(); break;
										}
										else 
										{
											decision++;
										}
								case 2 : if (gridModel.getActualTilesArray()[y+1][x-1].getTileType().equals("RoadTile")) 
										{
											vehicle.turnLeft(); break; 
										}
										else
										{
											decision--;
										}
								case 3 : if (gridModel.getActualTilesArray()[y-1][x-3].getTileType().equals("RoadTile"))
										{
											vehicle.straightAheadIfCrossing(); break;
										}
										else
										{
											decision--;
										}
								default : break;
							}
							
							movedVehicles.add(vehicle);
							vehicle.setCounterReactionTime(0); 	//reset reactionCounter
							
	
							
						}
						vehicle.setCounterReactionTime(vehicle.getCounterReactionTime()+1); 	//raise reactionCounter by 1
					}
					//maybe there is no traffic light on the crossing
					else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("CrossingTile") &&
							gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("CrossingTile") &&
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
						//@Thommy:
						
						Integer decision = randomComponents.randomNumberBetweenBounds(1, 3);
						switch (decision)
						{
							case 1 : 
									if (gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("RoadTile"))
									{
										vehicle.turnRight(); break;
									}
									else 
									{
										decision++;
									}
							case 2 : if (gridModel.getActualTilesArray()[y+1][x-1].getTileType().equals("RoadTile")) 
									{
										vehicle.turnLeft(); break; 
									}
									else
									{
										decision--;
									}
							case 3 : if (gridModel.getActualTilesArray()[y-1][x-3].getTileType().equals("RoadTile"))
									{
										vehicle.straightAheadIfCrossing(); break;
									}
									else
									{
										decision--;
									}
							default : break;
						}
						
						movedVehicles.add(vehicle);
					}
					
					//maybe there is no traffic light and no traffic sign on the crossing -> only at one way crossings
					else if (gridModel.findGiveWayTrafficSignInModelsList(x+1, y+1, vehicle.getDirection()).equals(false) && 
							gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(false))
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
					{
						vehicle.stop();
					}
					else
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
				
				//if there are no obstacles, move one step straightAhead()
				else if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
				{
					vehicle.stop(); //if there are no obstacles, move one step straightAhead()
				}
				else
				{
					vehicle.straightAhead();
					movedVehicles.add(vehicle);
				}
			}
			/***************************************** NORTH WEST ***************************************/
			else if (direction.equals(Direction.NORTH_WEST) && vehicle.getExists().equals(true))	//north-west
			{
				//IF BUSSTOP
				if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("BusStopRoadTile"))
				{
					x--;
					y--;
					//if Tile in west is road, scan road 5 tiles back in south
					if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoadTile"))
					{
						//scan 5 steps back for cars and only leave busstop if road is free
						for (int i = 0; i < 5; i++)
						{
							if (gridModel.findVehicleInModelsList(x, y+1, vehicle.getDirection()).equals(true))
							{
								vehicle.stop();
								return;		//leave method
							}
						}
						//bus leaves bus stop
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}

				//if scanning a roundabout crossing, determine next destination
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing"))
				{
					//TODO: DIJKSTRA ZIELWAHL MUSS HIER ABGEFRAGT WERDEN, DANACH EINE RICHTUNG GEWÄHLT WERDEN!
					//@Thommy:

					Integer decision = randomComponents.randomNumberBetweenBounds(1, 2);
					switch (decision)
					{
						case 1 : vehicle.turnRightRoundabout(); break;
						case 2 : vehicle.straightAheadRoundAbout(); break; 
						default : break;
					}
					
					movedVehicles.add(vehicle);
					
				}
				
				//IF CURVED ROAD -> road continuing north
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-2][x-1].getTileType().equals("RoadTile") &&
							gridModel.getActualTilesArray()[y-2][x-1].getDirection().equals(1) &&
							gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{

					if (gridModel.findOneVehicleInModelsList(x, y+1).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+2).equals(false) &&
							gridModel.findOneVehicleInModelsList(x, y+3).equals(false))
					{
						y--;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}
				//IF CURVED ROAD -> road continuing west
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-2].getTileType().equals("RoadTile") &&
						gridModel.getActualTilesArray()[y-1][x-2].getDirection().equals(7) &&
						gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutCrossing")==false)
				{

					if (gridModel.findOneVehicleInModelsList(x+1, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+2, y).equals(false) &&
							gridModel.findOneVehicleInModelsList(x+3, y).equals(false))
					{
						x--;
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
					else
					{
						vehicle.stop();
					}
				}				
				else if (x > 1 && y > 1 && gridModel.getActualTilesArray()[y-1][x-1].getTileType().equals("RoundAboutSegmentTile"))
				{
					y--;
					x--;
					if (gridModel.findVehicleInModelsList(x, y, vehicle.getDirection()).equals(true))
					{
						vehicle.stop();
					}
					else
					{
						vehicle.straightAhead();
						movedVehicles.add(vehicle);
					}
				}
			}
			else
			{
				//do nothing
			}
		} catch (Exception e) {
			logger.severe("ENTERING EXCEPTION VEHICLEMODEL SCANAHEAD()");
			//System.out.println(vehicle.vehicleType + " color: " + vehicle.color + " direction: " + vehicle.getDirection() + " has thrown exception");
			e.printStackTrace();
		}
		

			
	} //end of scanAhead()

	/*******************************************************************/
	/**  addToDeletedVehicles(VehicleModel vehicle)					  **/
	/**  adds the specified vehicle to the list of the deleted ones   **/	
	/*******************************************************************/
	
	
	//adding to deleted
	public void addToDeletedVehicles(VehicleModel vehicle)
	{
		deletedVehicles.add(vehicle);
	}
	
	
	
	
} // end class BasicRules
