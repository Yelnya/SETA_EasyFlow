package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import util.Bitmap;
import util.LogUtility;
import util.RandomComponents;
import view.GridView;
import view.TrafficLightView;
import xml.XMLRead;

/*******************************************************************/
/**  Class GridModel                                              **/
/**                                                               **/ 
/**  the model class for the grid containing the tiles array      **/
/*******************************************************************/
public class GridModel extends BaseModel {

	private static Logger logger = Logger.getLogger(LogUtility
			.getName(GridModel.class.getName()));

	// private ArrayList<BaseModel> models; //for cars -> use
	// getActualVehiclesArray()
	Iterator<BaseModel> iter; // Iter for models
	
	public final static int TYPE_VEHICLE_PASSENGER_CAR=1;
	public final static int TYPE_VEHICLE_TRUCK=2;
	public final static int TYPE_VEHICLE_BUS=3;
	
	private TileModel[][] actualTilesArray; // reference to actual tiles array
	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/
	ArrayList<EntryRoadTileModel> entryRoadTilesArray = new ArrayList<EntryRoadTileModel>();
	ArrayList<ExitRoadTileModel> exitRoadTilesArray = new ArrayList<ExitRoadTileModel>();

	
	public ArrayList<ExitRoadTileModel> getExitRoadTilesArray() {
		return exitRoadTilesArray;
	}

	public void setExitRoadTilesArray(
			ArrayList<ExitRoadTileModel> exitRoadTilesArray) {
		this.exitRoadTilesArray = exitRoadTilesArray;
	}

	public ArrayList<EntryRoadTileModel> getEntryRoadTilesArray() {
		return entryRoadTilesArray;
	}

	public void setEntryRoadTilesArray(
			ArrayList<EntryRoadTileModel> entryRoadTilesArray) {
		this.entryRoadTilesArray = entryRoadTilesArray;
	}

	private volatile ArrayList<VehicleModel> pastVehiclesArray = new ArrayList<VehicleModel>(); // Vergangener

	private volatile ArrayList<VehicleModel> actualVehiclesArray = new ArrayList<VehicleModel>(); // Aktueller

	private volatile ArrayList<VehicleModel> futureVehiclesArray = new ArrayList<VehicleModel>(); // Nächster


	private volatile ArrayList<VehicleModel> newVehiclesArray = new ArrayList<VehicleModel>();
	private ArrayList<TrafficsignModel> arrayTrafficSigns = new ArrayList<TrafficsignModel>(); // selective																						// needed
	private ArrayList<TrafficLightModel> arrayTrafficLights = new ArrayList<TrafficLightModel>(); // selective


	private ArrayList<RoadTileModel> routeToNextNode = null;
	RandomComponents randomComponentes = new RandomComponents();

	public static PassengerCarModel newPassengerCar;
	public static BusModel newBus;
	public static TrafficLightModel newTrafficLight;
	public static TrafficsignNoEntryModel newNoEntry;
	public static TrafficsignGiveWayModel newGiveWay;
	public static TrafficsignOneWayModel newOneWay;
	public static TrafficsignConstructionSiteModel newConstructionSite;

	// GETTER and SETTER

	public ArrayList<VehicleModel> getPastVehiclesArray() {
		return pastVehiclesArray;
	}

	public void setPastVehiclesArray(ArrayList<VehicleModel> pastVehiclesArray) {
		this.pastVehiclesArray = pastVehiclesArray;
	}

	public synchronized ArrayList<VehicleModel> getActualVehiclesArray() {
		return actualVehiclesArray;
	}

	public void setActualVehiclesArray(
			ArrayList<VehicleModel> actualVehiclesArray) {
		this.actualVehiclesArray = actualVehiclesArray;
	}

	public ArrayList<VehicleModel> getFutureVehiclesArray() {
		return futureVehiclesArray;
	}

	public void setFutureVehiclesArray(
			ArrayList<VehicleModel> futureVehiclesArray) {
		this.futureVehiclesArray = futureVehiclesArray;
	}

	public ArrayList<VehicleModel> getArrayFutureVehicles() {
		return futureVehiclesArray;
	}

	public void setArrayFutureVehicles(
			ArrayList<VehicleModel> arrayFutureVehicles) {
		this.futureVehiclesArray = futureVehiclesArray;
	}

	public ArrayList<VehicleModel> getArrayPresentVehicles() {
		return actualVehiclesArray;
	}

	public void setArrayPresentVehicles(
			ArrayList<VehicleModel> actualVehiclesArray) {
		this.actualVehiclesArray = actualVehiclesArray;
	}

	public ArrayList<VehicleModel> getArrayPastVehicles() {
		return pastVehiclesArray;
	}

	public void setArrayPastVehicles(ArrayList<VehicleModel> pastVehiclesArray) {
		this.pastVehiclesArray = pastVehiclesArray;
	}

	public synchronized ArrayList<RoadTileModel> getRouteToNextNode() {
		return routeToNextNode;
	}

	public synchronized void setRouteToNextNode(
			ArrayList<RoadTileModel> routeToNextNode) {
		this.routeToNextNode = routeToNextNode;
	}

	public void setTilesArray(TileModel[][] tilesArray) {
		actualTilesArray = tilesArray;
	}

	@SuppressWarnings("unused")
	private static Bitmap backgroundPicture = null; // backgroundPicture used by
													// GridView for city map
	private static Integer xDim = null, yDim = null; // dimension of the Grid
														// w/o scale line in
														// view
	private static DataModelSingleton model = null;

	private RandomComponents randomComponents;
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/**  create the empty grid                                        **/  
	/*******************************************************************/
	// CONSTRUCTOR: create the empty grid
	// importDataFromXML
	@SuppressWarnings("static-access")
	public GridModel(int xDim, int yDim, Bitmap backgroundPicture) {
		super();
		// initialize logger for the class
		logger.setLevel(LogUtility.getLevel(Level.INFO));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		this.xDim = xDim;
		this.yDim = yDim;
		this.backgroundPicture = backgroundPicture;

		actualTilesArray = new TileModel[yDim][xDim];
		model = DataModelSingleton.getInstance();

		// just for test, obsolete with dijkstra!
		int x = 20;
		int y;
		for (y = 1; y <= 12; y++) {
			routeToNextNode = new ArrayList<RoadTileModel>();
			routeToNextNode.add((RoadTileModel) actualTilesArray[x][y]);
		}
		// just for test end

		randomComponents = new RandomComponents();

	} // end constructor GridModel
	/*******************************************************************/
	/**  importDataFromXml                                            **/
	/**                                                               **/ 
	/**  reads tiles, vehicles, traffic signs and lights from xml file**/ 
	/*******************************************************************/
	public void importDataFromXml() {
		// XMLRead.init: creates File handle, SAXBuilder -> returns root element
		Element root = XMLRead.init();

		TileModel[][] tilesArray = actualTilesArray;
		int i = 0;
		int j = 0;
		for (i = 0; i < yDim; i++) {
			logger.info("create line number " + i);
			for (j = 0; j < xDim; j++) {
				logger.fine("create element " + i + " " + j);

				// read xml file

				int tile_type = 0;
				try {
					tile_type = XMLRead.get_tile_from_xml(root, i, j);
				} catch (DataConversionException e) {
					e.printStackTrace();
				}

				// tile_type = XMLRead.get_tile_from_xml(i, j);
				// System.out.println("tile type code: " + tile_type);
				logger.fine("tile type code: " + tile_type);
				switch (tile_type) {

				case XMLRead.TYPE_ROAD_SOUTH: // type road - direction south
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 5); // 5 = south
					break;
				case XMLRead.TYPE_ROAD_NORTH: // type road - direction north
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 1); // 1 = north
					break;
				case XMLRead.TYPE_ROAD_EAST: // type road - direction east
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 3); // 3 = east
					break;
				case XMLRead.TYPE_ROAD_WEST: // type road - direction west
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 7); // 7 = west
					break;
				case XMLRead.TYPE_ROAD_NORTH_WEST:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 8); // 8 =
																	// north-west
					break;
				case XMLRead.TYPE_ROAD_WEST_NORTH:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 8); // 8 =
																	// west-north
																	// =
																	// north-west
					break;
				case XMLRead.TYPE_ROAD_EAST_SOUTH:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 4); // 4 =
																	// east-south
																	// =
																	// south-east
					break;
				case XMLRead.TYPE_ROAD_SOUTH_EAST:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 4); // 4 =
																	// south-east
					break;
				case XMLRead.TYPE_ROAD_WEST_SOUTH:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 6); // 6 =
																	// west-south
																	// =
																	// south-west
					break;
				case XMLRead.TYPE_ROAD_SOUTH_WEST:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 6); // 6 =
																	// south-west
					break;
				case XMLRead.TYPE_ROAD_NORTH_EAST:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 2); // 2 =
																	// north-east
					break;
				case XMLRead.TYPE_ROAD_EAST_NORTH:
					logger.fine("create road tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new RoadTileModel(i, j, 2); // 2 =
																	// east-north
																	// =
																	// north-east
					break;

				case XMLRead.TYPE_ENTRY_SOUTH: // type entry - direction south
					logger.fine("create entry tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new EntryRoadTileModel(i, j, 5); // 5 =
																		// south
					getEntryRoadTilesArray().add(
							(EntryRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_ENTRY_NORTH: // type entry - direction north
					logger.fine("create entry tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new EntryRoadTileModel(i, j, 1); // 1 =
																		// north
					getEntryRoadTilesArray().add(
							(EntryRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_ENTRY_EAST: // type entry - direction east
					logger.fine("create entry tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new EntryRoadTileModel(i, j, 3); // 3 =
																		// east
					getEntryRoadTilesArray().add(
							(EntryRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_ENTRY_WEST: // type entry - direction west
					logger.fine("create entry tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new EntryRoadTileModel(i, j, 7); // 7 =
																		// west
					getEntryRoadTilesArray().add(
							(EntryRoadTileModel) tilesArray[i][j]);
					break;

				case XMLRead.TYPE_EXIT_SOUTH: // type exit - direction south
					logger.fine("create exit tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new ExitRoadTileModel(i, j, 5); // 5 =
																		// south
					getExitRoadTilesArray().add(
							(ExitRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_EXIT_NORTH: // type exit - direction north
					logger.fine("create exit tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new ExitRoadTileModel(i, j, 1); // 1 =
																		// north
					getExitRoadTilesArray().add(
							(ExitRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_EXIT_EAST: // type exit - direction east
					logger.fine("create exit tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new ExitRoadTileModel(i, j, 3); // 3 =
																		// east
					getExitRoadTilesArray().add(
							(ExitRoadTileModel) tilesArray[i][j]);
					break;
				case XMLRead.TYPE_EXIT_WEST: // type exit - direction west
					logger.fine("create exit tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new ExitRoadTileModel(i, j, 7); // 7 =
																		// west
					getExitRoadTilesArray().add(
							(ExitRoadTileModel) tilesArray[i][j]);
					break;

				case XMLRead.TYPE_CROSSING_SOUTH: // type crossing - direction
													// south
					logger.fine("create crossing tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new CrossingTileModel(i, j, 5); // 5 =
																		// south
					break;
				case XMLRead.TYPE_CROSSING_NORTH: // type crossing - direction
													// north
					logger.fine("create crossing tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new CrossingTileModel(i, j, 1); // 1 =
																		// north
					break;
				case XMLRead.TYPE_CROSSING_EAST: // type crossing - direction
													// east
					logger.fine("create crossing tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new CrossingTileModel(i, j, 3); // 3 =
																		// east
					break;
				case XMLRead.TYPE_CROSSING_WEST: // type crossing - direction
													// west
					logger.fine("create crossing tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new CrossingTileModel(i, j, 7); // 7 =
																		// west
					break;
				/*
				 * to be done: set third parameter to correct value for case
				 * CROSS
				 */
				case XMLRead.TYPE_CROSSING_CROSS: // type crossing - direction
													// cross
					logger.fine("create crossing tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new CrossingTileModel(i, j, 0); // 7 =
																		// west
					break;

				case XMLRead.TYPE_BUSSTOP_SOUTH: // type busstop - direction
													// south
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 5); // 5 =
																			// south
					break;
				case XMLRead.TYPE_BUSSTOP_NORTH: // type busstop - direction
													// north
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 1); // 1 =
																			// north
					break;
				case XMLRead.TYPE_BUSSTOP_EAST: // type busstop - direction east
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 3); // 3 =
																			// east
					break;
				case XMLRead.TYPE_BUSSTOP_WEST: // type busstop - direction west
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 7); // 7 =
																			// west
					break;
				case XMLRead.TYPE_BUSSTOP_SOUTH_WEST: // type busstop -
														// direction south-west
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 6); // 6 =
																			// south-west
					break;
				case XMLRead.TYPE_BUSSTOP_SOUTH_EAST: // type busstop -
														// direction south-east
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 4); // 4 =
																			// south-east
					break;
				case XMLRead.TYPE_BUSSTOP_NORTH_EAST: // type busstop -
														// direction north-east
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 2); // 2 =
																			// north-east
					break;
				case XMLRead.TYPE_BUSSTOP_NORTH_WEST: // type busstop -
														// direction north-west
					logger.fine("create busstop tile :-) : [" + i + "] [" + j
							+ "]");
					tilesArray[i][j] = new BusStopRoadTileModel(i, j, 8); // 8 =
																			// north-west
					break;

				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_SOUTH: // type roundabout
															// segment -
															// direction south
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 5); // 5
																				// =
																				// south
					break;
				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_NORTH: // type roundabout
															// segment -
															// direction north
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 1); // 1
																				// =
																				// north
					break;
				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_EAST: // type roundabout
															// segment -
															// direction east
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 3); // 3
																				// =
																				// east
					break;
				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_WEST: // type roundabout
															// segment -
															// direction west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 7); // 7
																				// =
																				// west
					break;
				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_CIRCLE: // type roundabout
																// segment -
																// direction
																// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 0); // 7
																				// =
																				// west
					break;

				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_NORTH_EAST: // type
																	// roundabout
																	// segment -
																	// direction
																	// north-east
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 2); // 2
																				// =
																				// north-east
					break;

				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_SOUTH_EAST: // type
																	// roundabout
																	// segment -
																	// direction
																	// south
																	// east
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 4); // 4
																				// =
																				// south-east
					break;

				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_SOUTH_WEST: // type
																	// roundabout
																	// segment -
																	// direction
																	// south-west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 6); // 6
																				// =
																				// south-west
					break;

				case XMLRead.TYPE_ROUNDABOUT_SEGMENT_NORTH_WEST: // type
																	// roundabout
																	// segment -
																	// direction
																	// north-west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutSegmentTileModel(i, j, 8); // 8
																				// =
																				// north-west
					break;

				case XMLRead.TYPE_ROUNDABOUT_CROSSING_SOUTH: // type roundabout
																// crossing -
																// direction
																// south
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 5); // 5
																					// =
																					// south
					break;
				case XMLRead.TYPE_ROUNDABOUT_CROSSING_NORTH: // type roundabout
																// crossing -
																// direction
																// north
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 1); // 1
																					// =
																					// north
					break;
				case XMLRead.TYPE_ROUNDABOUT_CROSSING_EAST: // type roundabout
															// crossing -
															// direction east
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 3); // 3
																					// =
																					// east
					break;
				case XMLRead.TYPE_ROUNDABOUT_CROSSING_WEST: // type roundabout
															// crossing -
															// direction west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 7); // 7
																					// =
																					// west
					break;

				/*
				 * to be done: set third parameter to correct value for case
				 * CIRCLE
				 */
				case XMLRead.TYPE_ROUNDABOUT_CROSSING_CROSS: // type roundabout
																// crossing -
																// direction
																// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 0); // 00
					break;

				case XMLRead.TYPE_ROUNDABOUT_CROSSING_NORTH_EAST: // type
																	// roundabout
																	// crossing
																	// -
																	// direction
																	// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 2); // 2
																					// =
																					// north-east
					break;

				case XMLRead.TYPE_ROUNDABOUT_CROSSING_SOUTH_EAST: // type
																	// roundabout
																	// crossing
																	// -
																	// direction
																	// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 4); // 4
																					// =
																					// south-east
					break;

				case XMLRead.TYPE_ROUNDABOUT_CROSSING_SOUTH_WEST: // type
																	// roundabout
																	// crossing
																	// -
																	// direction
																	// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 6); // 6
																					// =
																					// south-west
					break;

				case XMLRead.TYPE_ROUNDABOUT_CROSSING_NORTH_WEST: // type
																	// roundabout
																	// crossing
																	// -
																	// direction
																	// west
					logger.fine("create roundabout tile :-) : [" + i + "] ["
							+ j + "]");
					tilesArray[i][j] = new RoundAboutCrossingTileModel(i, j, 8); // 8
																					// =
																					// north-west
					break;

				default:
					logger.fine("default: decor tile");
					logger.fine("create decor tile :-)");
					tilesArray[i][j] = new DecorTileModel();
				}

				/*
				 * if (road) { logger.info("create road tile :-) : [" + i +
				 * "] [" + j+"]"); tilesArray[i][j] = new RoadTileModel(i,j);
				 * logger.info("create passenger car :-)"); vehicle = new
				 * PassengerCarModel(tilesArray[i][j]);
				 * tilesArray[i][j].addVehicle(vehicle); } else {
				 * logger.info("create decor tile :-)"); tilesArray[i][j] = new
				 * DecorTileModel(); } // end if
				 */
			} // end for j
		} // end for i

		try {

			String name = "car_1";
			Integer number = 1;
			
			
			while ((newPassengerCar = XMLRead.readCars(root, name, randomComponentes.getRandomExitRoadTile(getExitRoadTilesArray()))) != null) {
				getActualVehiclesArray().add(newPassengerCar); // hinzufügen des
																// Autos in die
																// ArrayList
				number++;
				logger.info("Passenger Car: " + name + " created");
				name = "car_".concat(number.toString());
				// link vehicle to the road tile
//				newPassengerCar
//						.setParent((RoadTileModel) (tilesArray[newPassengerCar
//								.getPosY() - 1][newPassengerCar.getPosX() - 1]));
			}

			name = "bus_1";
			number = 1;
			while ((newBus = XMLRead.readBus(root, name, randomComponentes.getRandomExitRoadTile(getExitRoadTilesArray()))) != null) {
				getActualVehiclesArray().add(newBus); // hinzufügen des Autos in
														// die ArrayList
				number++;
				name = "bus_".concat(number.toString());
//				newBus.setParent((RoadTileModel) (tilesArray[newBus.getPosY() - 1][newBus
//						.getPosX() - 1]));
			}

		} catch (DataConversionException e) {
			e.printStackTrace();
		}

		try {
			Integer number1 = 1;
			Integer number2 = 1;
			String name = "traffic_light_".concat(number1.toString()).concat(
					number2.toString());
			while ((newTrafficLight = XMLRead.readTrafficLight(root, name)) != null) {
				getActualTrafficLightsArray().add(newTrafficLight);
				number2++;
				if (number2 > 4) {
					number1++;
					number2 = 1;
				}
				name = "traffic_light_".concat(number1.toString()).concat(
						number2.toString());
			}
			name = "NoEntry_1";
			Integer number = 1;
			while ((newNoEntry = XMLRead.readTrafficSignNoEntry(root, name)) != null) {
				getActualTrafficSignsArray().add(newNoEntry);
				number++;
				name = "NoEntry_".concat(number.toString());
			}
			name = "GiveWay_1";
			number = 1;
			while ((newGiveWay = XMLRead.readTrafficSignGiveWay(root, name)) != null) {
				getActualTrafficSignsArray().add(newGiveWay);
				number++;
				name = "GiveWay_".concat(number.toString());
			}
			name = "OneWay_1";
			number = 1;
			while ((newOneWay = XMLRead.readTrafficSignOneWay(root, name)) != null) {
				getActualTrafficSignsArray().add(newOneWay);
				number++;
				name = "OneWay_".concat(number.toString());
			}
			name = "ConstructionSite_1";
			number = 1;
			while ((newConstructionSite = XMLRead
					.readTrafficSignConstructionSite(root, name)) != null) {
				getActualTrafficSignsArray().add(newConstructionSite);
				number++;
				name = "ConstructionSite_".concat(number.toString());
			}
		} catch (DataConversionException e) {
			e.printStackTrace();
		}

		((GridView) this.getView())
				.updateCreateVehicles(getActualVehiclesArray());
		((GridView) this.getView())
				.updateCreateTrafficLights(getActualTrafficLightsArray());
		((GridView) this.getView())
				.updateCreateTrafficSigns(getActualTrafficSignsArray());

	} // end importDataFromXml

	public void copyTilesArrayTo(TileModel[][] tilesArraySource,
			TileModel[][] tilesArrayDest) {
		// copies all model instances to a destination tiles array

		tilesArrayDest = tilesArraySource.clone();

	}

	public TileModel[][] getActualTilesArray() {
		return actualTilesArray;
	}

	// METHOD: Delete object from models list to make it disappear from screen
	// (construction sign)
	public void deleteFromTrafficSignList(TrafficsignModel modelobject) {
		getActualTrafficSignsArray().remove(modelobject);
	}

	// METHOD: Add one new object to models list to make it appear in screen
	// (construction sign)
	public void addToTrafficSignList(TrafficsignModel modelobject) {
		getActualTrafficSignsArray().add(modelobject);
	}


	//METHOD: Look if BaseModel List contains vehicle on requested position
	public Boolean findVehicleInModelsList(Integer posX, Integer posY,
			Integer direction) {
		@SuppressWarnings("unused")
		Boolean vehicleFound = true;
		Integer xMod = 0;
		Integer yMod = 0;
		switch (direction) {
		case 1:
			yMod = -1;
			break;
		case 2:
			xMod = +1;
			yMod = -1;
			break;
		case 3:
			xMod = +1;
			break;
		case 4:
			xMod = +1;
			yMod = +1;
			break;
		case 5:
			yMod = +1;
			break;
		case 6:
			xMod = -1;
			yMod = +1;
			break;
		case 7:
			xMod = -1;
			break;
		case 8:
			xMod = -1;
			yMod = -1;
			break;
		default:
			break;
		}

		try {
			for (VehicleModel modelObject : getActualVehiclesArray()) {
				// list for only Vehicles
				if (modelObject instanceof VehicleModel) {
					if (((VehicleModel) modelObject).getPosX().equals(posX)
							&& ((VehicleModel) modelObject).getPosY().equals(
									posY)
							&& ((VehicleModel) modelObject).exists == true) {
						return vehicleFound = true;
					} else if (((VehicleModel) modelObject).getPosX().equals(
							posX + xMod)
							&& ((VehicleModel) modelObject).getPosY().equals(
									posY + yMod)
							&& ((VehicleModel) modelObject).exists == true) {
						if (modelObject instanceof BusModel || modelObject instanceof TruckModel)
						{
							return vehicleFound = true;
						}
						
					}
				} else {
					// do nothing
				}
			}
		} catch (Exception e) {
			//System.out.println("ENTERING EXCEPTION GRIDMODEL findVehicleInModelsList()");
			//e.printStackTrace();
		}
		return vehicleFound = false;
	} // end findVehicleInModelsList

	@SuppressWarnings("unused")
	public Boolean findOneVehicleInModelsList(Integer posX, Integer posY) {
		Boolean vehicleFound = true;

		try {
			for (VehicleModel modelObject : getActualVehiclesArray()) {
				// list for only Vehicles
				if (modelObject instanceof VehicleModel) {
					if (((VehicleModel) modelObject).getPosX().equals(posX)
							&& ((VehicleModel) modelObject).getPosY().equals(
									posY)
							&& ((VehicleModel) modelObject).exists == true) {
						return vehicleFound = true;
					}
				}
			}
		} catch (Exception e) {
			//System.out.println("ENTERING EXCEPTION GRIDMODEL findOneVehicleInModelsList()");
			//e.printStackTrace();
		}
		return vehicleFound = false;
	} // end findOneVehicleInModelsList

	// METHOD: Look if BaseModel List contains trafficSign on requested
	// position
	public Boolean findGiveWayTrafficSignInModelsList(int posX, int posY,
			Integer carDirection) {
		Boolean trafficsignGiveWayFound = false;
		try {
			for (TrafficsignModel modelObject : getActualTrafficSignsArray()) {
				// list for only TrafficSigns
				if (modelObject instanceof TrafficsignModel) {
					if (((TrafficsignModel) modelObject).getPosX().equals(posX)
							&& ((TrafficsignModel) modelObject).getPosY()
									.equals(posY)) {
						if (modelObject instanceof TrafficsignGiveWayModel) {
							if (carDirection.equals(0) == false) {
								return trafficsignGiveWayFound = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			//System.out.println("ENTERING EXCEPTION GRIDMODEL findTrafficSignInModelsList()");
			//e.printStackTrace();
		}
		return trafficsignGiveWayFound;
	} // end of findTrafficSignInModelsList()

	// METHOD: Scan for ConstructionSign in front of vehicle
	public synchronized Boolean scanForConstructionSign(int posX, int posY) {
		Boolean obstacleThere = false;

		try {
			for (TrafficsignModel modelObject : getActualTrafficSignsArray()) {
				// only if list entry is a trafficLight AND only if trafficLight
				// is facing the right direction
				if (modelObject instanceof TrafficsignModel) {
					// if traffic light is on the right position
					if (((TrafficsignModel) modelObject).getPosX().equals(posX)
							&& ((TrafficsignModel) modelObject).getPosY()
									.equals(posY)
							&& ((TrafficsignConstructionSiteModel) modelObject)
									.getExists().equals(true)) {
						obstacleThere = true;
					}
				}
			}
		} catch (Exception e) {
			//System.out.println("ENTERING EXCEPTION GRIDMODEL scanForConstructionSign()");
			//e.printStackTrace();
		}
		return obstacleThere;
	} // end of scanForConstructionSign()

	// METHOD: Scan TrafficLight in models list on given position if it is red
	// or green
	public synchronized String watchTrafficLight(int posX, int posY,
			int carDirection) {
		String color = "none";
		int trafficLightDirection = 0; // trafficLightDirection must be opposite
										// to carDirection
		// first determining the trafficLightDirection
		if (carDirection > 4) {
			trafficLightDirection = carDirection - 4;
		} else {
			trafficLightDirection = carDirection + 4;
		}

		try {
			for (TrafficLightModel modelObject : getActualTrafficLightsArray()) {
				// only if list entry is a trafficLight AND only if trafficLight
				// is facing the right direction
				if (modelObject instanceof TrafficLightModel
						&& ((TrafficLightModel) modelObject)
								.getTrafficLightDirection().equals(
										trafficLightDirection)) {
					// if traffic light is on the right position
					if (((TrafficLightModel) modelObject).getGridposX().equals(
							posX)
							&& ((TrafficLightModel) modelObject).getGridposY()
									.equals(posY)) {
						color = ((TrafficLightModel) modelObject)
								.getTrafficLightColor();
					}
				}
			}
		} catch (Exception e) {
			//System.out.println("ENTERING EXCEPTION GRIDMODEL watchTrafficLight()");
			//e.printStackTrace();
		}
		return color; // return value is none if trafficlight is not facing
						// right direction or no trafficlight at all
	} // end of watchTrafficLight()

	/*******************************************************************/
	/**  propagateChanges                                             **/
	/**                                                               **/ 
	/**  all changes calculated in controller are propagated          **/ 
	/*******************************************************************/
	public void propagateChanges(ArrayList<VehicleModel> movedVehicles,
			ArrayList<VehicleCoordinates> createdVehicles,
			ArrayList<VehicleModel> deletedVehicles,
			ArrayList<TrafficsignModel> createdTrafficSigns,
			ArrayList<TrafficsignModel> deletedTrafficSigns,
			ArrayList<TrafficLightModel> modifiedTrafficLights) {

		if (movedVehicles != null)
			logger.info("movedVehicles: " + "number of moved vehicles: "
					+ movedVehicles.size());
		if (createdVehicles != null)
			logger.info("createdVehicles: " + "number of created vehicles: "
					+ createdVehicles.size());
		if (deletedVehicles != null)
			logger.info("deletedVehicles: " + "number of deleted vehicles: "
					+ deletedVehicles.size());

		if (createdTrafficSigns != null)
			logger.info("createdTrafficSigns: "
					+ "number of created TrafficSign: "
					+ createdTrafficSigns.size());
		if (deletedTrafficSigns != null)
			logger.info("deletedTrafficSigns: "
					+ "number of deleted TrafficSign: "
					+ deletedTrafficSigns.size());
		if (modifiedTrafficLights != null)
			logger.info("modifiedTrafficLights: "
					+ "number of modified TrafficLights: "
					+ modifiedTrafficLights.size());

		GridView gridView = (GridView) this.getView();
		logger.info("propagateChanges called");

		gridView.updateMove(movedVehicles);

		createManuallyVehicles(createdVehicles);
		createAdditionalVehicles();
		gridView.updateCreateVehicles(getNewVehiclesArray());
		getNewVehiclesArray().clear();

		gridView.updateDeleteVehicle(deletedVehicles);

		deleteVehicles(deletedVehicles);

		gridView.updateTrafficLights(modifiedTrafficLights);

	}

	//METHOD: create vehicles manually
	@SuppressWarnings("unused")
	private void createManuallyVehicles(
			ArrayList<VehicleCoordinates> createdVehicles) {
		int posX, posY;
		if (createdVehicles != null && createdVehicles.size() != 0)
			for (VehicleCoordinates vehiclePos : createdVehicles) {
				posX = vehiclePos.getPosX();
				posY = vehiclePos.getPosY();
				createRandomVehicle(TYPE_VEHICLE_PASSENGER_CAR, vehiclePos);
			}

	}

	//METHOD: add vehicles automatically
	private void createAdditionalVehicles() {

		int actualNumberPassengerCars = getActualNumberOfVehicleInstancesPerType(TYPE_VEHICLE_PASSENGER_CAR);
		int actualNumberTrucks = getActualNumberOfVehicleInstancesPerType(TYPE_VEHICLE_TRUCK);
		int actualNumberBusses = getActualNumberOfVehicleInstancesPerType(TYPE_VEHICLE_BUS);

		int i, numberOfVehicles, delta;

		if (model.getControlPanelSettings().getNumberOfPassengerCars() > actualNumberPassengerCars+getNewVehiclesArray().size())
		{
			delta=model.getControlPanelSettings().getNumberOfPassengerCars()-actualNumberPassengerCars-getNewVehiclesArray().size();
			getNewVehiclesArray().size();
			numberOfVehicles = delta > 3 ? 3 : delta; // maximum 3 in one step
			logger.info("number of new cars "+ numberOfVehicles);
			for (i = 0; i < numberOfVehicles; i++) {
				createRandomVehicle(TYPE_VEHICLE_PASSENGER_CAR, null);
			}
		}
		
		
		if (model.getControlPanelSettings().getNumberOfTrucks() > actualNumberTrucks )
		{
			delta=model.getControlPanelSettings().getNumberOfTrucks()-actualNumberTrucks;
			numberOfVehicles = delta > 3 ? 3 : delta; // maximum 3 in one step
			logger.info("number of new trucks "+ numberOfVehicles);
			for (i = 0; i < numberOfVehicles; i++) {
				createRandomVehicle(TYPE_VEHICLE_TRUCK, null);
			}
		}
		
		if (model.getControlPanelSettings().getNumberOfBusses() > actualNumberBusses )
		{
			delta=model.getControlPanelSettings().getNumberOfBusses()-actualNumberBusses;
			numberOfVehicles = delta > 3 ? 3 : delta; // maximum 3 in one step
			logger.info("number of new busses "+ numberOfVehicles);
			for (i = 0; i < numberOfVehicles; i++) {
				createRandomVehicle(TYPE_VEHICLE_BUS, null);
			}
		}	
	
	}
	//METHOD: get number of vehicle instances for type	
	public int getActualNumberOfVehicleInstancesPerType(int vehicleType )
	{
		int carCount=0;
		for (VehicleModel vehicleModel : getActualVehiclesArray() )
		{

		switch (vehicleType) {
		case TYPE_VEHICLE_PASSENGER_CAR:
			if (vehicleModel instanceof PassengerCarModel) carCount++;
			break;
		case TYPE_VEHICLE_TRUCK:
			if (vehicleModel instanceof TruckModel) carCount++;
			break;
		case TYPE_VEHICLE_BUS:
			if (vehicleModel instanceof BusModel) carCount++;
			break;
			default:
				logger.info("getActualNumberOfVehicleTypeInstances: invalid vehicleType "+vehicleType);
				break;
		}	

			
		}
		
		return carCount;
	}

	
	//METHOD: create vehicle randomly
	private void createRandomVehicle(int vehicleType,
			VehicleCoordinates position) {

		int posX, posY;
		int layer = 3;
		int direction;
		String color;
		Bitmap bitmap;
		VehicleCoordinates randomEntryPosition;
		@SuppressWarnings("unused")
		VehicleCoordinates randomExitPosition;
				
		ExitRoadTileModel target = randomComponents.getRandomExitRoadTile(exitRoadTilesArray);
						
		if (position != null) {
			posX = position.getPosX();
			posY = position.getPosY();
			direction = getActualTilesArray()[posY - 1][posX - 1].getDirection();

		} else {
			randomEntryPosition = randomComponents
					.getRandomEntryPosition(entryRoadTilesArray);

			@SuppressWarnings("unused")
			boolean positionFree=false;int counter=0;
			do {
			
			posY = randomEntryPosition.getPosX(); // because wrong index in XML
												// import?
			posX = randomEntryPosition.getPosY(); // because wrong index in XML
												// import?
			direction = getActualTilesArray()[posY - 1][posX - 1].getDirection();	
			counter++;						
			}
			while (counter < 9 && !findVehicleInModelsList(posX, posY, direction));
		}
		direction = getActualTilesArray()[posY - 1][posX - 1].getDirection();
		switch (vehicleType) {
		case TYPE_VEHICLE_PASSENGER_CAR:
		// PassengerCar
			color = randomComponents.randomVehicleColor();
			bitmap = new Bitmap(new String(randomComponents.bitmapPathBuilder(
					color, direction)));
			PassengerCarModel car = new PassengerCarModel(posX, posY, layer,
					direction, 1, color, bitmap, target);	
			car.setParent((RoadTileModel) (getActualTilesArray()[posY - 1][posX - 1]));
			getNewVehiclesArray().add(car);
			getActualVehiclesArray().add(car);
			
			break;
		case TYPE_VEHICLE_TRUCK:
			// Truckr
				color = randomComponents.randomVehicleColor();
				bitmap = new Bitmap(new String(randomComponents.bitmapPathBuilderForTrucks(0, direction)));
				TruckModel truck = new TruckModel(posX, posY, layer,
					direction, 1, randomComponents.randomVehicleColor(), bitmap,  target);
				truck.setParent((RoadTileModel) (getActualTilesArray()[posY - 1][posX - 1]));
				getNewVehiclesArray().add(truck);
				getActualVehiclesArray().add(truck);

				break;
		case TYPE_VEHICLE_BUS:
			// Bus
				color = randomComponents.randomVehicleColor();
				bitmap = new Bitmap(new String(randomComponents.bitmapPathBuilderForBusses(0,direction)));
				BusModel bus = new BusModel(posX, posY, layer,
						direction, 1, color, bitmap, target);
				bus.setParent((RoadTileModel) (getActualTilesArray()[posY - 1][posX - 1]));
				getNewVehiclesArray().add(bus);
				getActualVehiclesArray().add(bus);

				break;
				default:
					logger.info("createRandomVehicle: invalid vheicleType "+vehicleType);
		} // end switch vehicle type
	} // end createRandomVehicle

	//METHOD: delete vehicles
	private void deleteVehicles(ArrayList<VehicleModel> deletedVehicles) {
		try {
			for (VehicleModel vehicle : deletedVehicles) {
				actualVehiclesArray.remove(vehicle);
			}

		} catch (Exception e) {
			logger.severe("entering GRIDMODEL.deleteVehicles(...)");
			e.printStackTrace();
		}
	}

	public ArrayList<TrafficLightModel> getActualTrafficLightsArray() {
		return arrayTrafficLights;
	}

	public void setActualTrafficLightsArray(
			ArrayList<TrafficLightModel> arrayTrafficLights) {
		this.arrayTrafficLights = arrayTrafficLights;
	}

	public ArrayList<TrafficsignModel> getActualTrafficSignsArray() {
		return arrayTrafficSigns;
	}

	public void setActualTrafficSignsArray(
			ArrayList<TrafficsignModel> arrayTrafficSigns) {
		this.arrayTrafficSigns = arrayTrafficSigns;
	}

	

    //METHOD: count vehicles in list
    public void howManyVehiclesInList()
    {
    	Integer numberSpecifiedVehicles = 0;
    	Integer vehicleType = 1;

    	for(vehicleType = 1; vehicleType <= 3; vehicleType++)
    	{
    		switch (vehicleType)
        	{
    		case 1 :
    			{
    		    	for (VehicleModel vehicel : getActualVehiclesArray())
    		    	{
    		    		if (vehicel instanceof PassengerCarModel)
    		    		{
    		    			numberSpecifiedVehicles++;
    		    		}
    		    	}
    		    	break;
    			}
    		case 2 :
    			{
    		    	for (VehicleModel vehicel : getActualVehiclesArray())
    		    	{
    		    		if (vehicel instanceof BusModel)
    		    		{
    		    			numberSpecifiedVehicles++;
    		    		}
    		    	}
    		    	break;
    			}
    		case 3 :
    			{
    		    	for (VehicleModel vehicel : getActualVehiclesArray())
    		    	{
    		    		if (vehicel instanceof TruckModel)
    		    		{
    		    			numberSpecifiedVehicles++;
    		    		}
    		    	}
    		    	break;
    			}
    			default : break; 
        	}
        	((GridView)this.getView()).getControlPanel().setVehicleCount(vehicleType, numberSpecifiedVehicles.toString());
        	numberSpecifiedVehicles = 0;
    	}
    }
	
    //METHOD: detect traffic jam
	public void detectTrafficJam()
	{
		Boolean jamDetected = false;
		
		for (TrafficLightModel trafficLight : getActualTrafficLightsArray())
		{	
			jamDetected = trafficLight.trafficJamDetection();
			if(trafficLight.getTrafficJamBitmap() != null)
			{
				((TrafficLightView)trafficLight.getView()).deleteLabelTrafficJam();
			}
			
			
			if (jamDetected.equals(true))
			{
				((TrafficLightView)trafficLight.getView()).drawtrafficJam(trafficLight);
				break;
			}
		}
		
		((GridView)this.getView()).getControlPanel().setTrafficJamLabel(jamDetected);
		
	}
    
	public ArrayList<VehicleModel> getNewVehiclesArray() {
		return newVehiclesArray;
	}

	public void setNewVehiclesArray(ArrayList<VehicleModel> newVehiclesArray) {
		this.newVehiclesArray = newVehiclesArray;
	}

} // end class
