package xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 * import org.jdom2.Content;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

 */

import java.util.logging.Level;
import java.util.logging.Logger;

import model.BusModel;
import model.DataModelSingleton;
import model.ExitRoadTileModel;
import model.PassengerCarModel;
import model.TrafficLightModel;
import model.TrafficsignConstructionSiteModel;
import model.TrafficsignGiveWayModel;
import model.TrafficsignNoEntryModel;
import model.TrafficsignOneWayModel;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import util.Bitmap;
import util.LogUtility;
import util.RandomComponents;

/*******************************************************************/
/**  Class XMLRead                                                **/
/**                                                               **/ 
/**  class for reading data from the xml file                     **/
/*******************************************************************/

public class XMLRead {
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(XMLRead.class.getName()));

	public final static int TYPE_ROAD_SOUTH=11;	
	public final static int TYPE_ROAD_SOUTH_EAST=113;	
	public final static int TYPE_ROAD_SOUTH_WEST=114;	
	public final static int TYPE_ROAD_NORTH=12;		
	public final static int TYPE_ROAD_NORTH_WEST=124;
	public final static int TYPE_ROAD_NORTH_EAST=123;
	public final static int TYPE_ROAD_EAST=13;
	public final static int TYPE_ROAD_EAST_NORTH=132;
	public final static int TYPE_ROAD_EAST_SOUTH=131;
	public final static int TYPE_ROAD_WEST=14;
	public final static int TYPE_ROAD_WEST_SOUTH=141;
	public final static int TYPE_ROAD_WEST_NORTH=142;

	public final static int TYPE_ENTRY_SOUTH=21;
	public final static int TYPE_ENTRY_NORTH=22;
	public final static int TYPE_ENTRY_EAST=23;
	public final static int TYPE_ENTRY_WEST=24;

	public final static int TYPE_EXIT_SOUTH=31;
	public final static int TYPE_EXIT_NORTH=32;
	public final static int TYPE_EXIT_EAST=33;
	public final static int TYPE_EXIT_WEST=34;

	public final static int TYPE_CROSSING_SOUTH=41;
	public final static int TYPE_CROSSING_NORTH=42;
	public final static int TYPE_CROSSING_EAST=43;
	public final static int TYPE_CROSSING_WEST=44;
	public final static int TYPE_CROSSING_CROSS=45;

	public final static int TYPE_BUSSTOP_SOUTH=51;
	public final static int TYPE_BUSSTOP_NORTH=52;
	public final static int TYPE_BUSSTOP_EAST=53;
	public final static int TYPE_BUSSTOP_WEST=54;
	public final static int TYPE_BUSSTOP_SOUTH_WEST=55;
	public final static int TYPE_BUSSTOP_SOUTH_EAST=56;
	public final static int TYPE_BUSSTOP_NORTH_WEST=57;
	public final static int TYPE_BUSSTOP_NORTH_EAST=58;

	public final static int TYPE_ROUNDABOUT_SEGMENT_SOUTH=61;
	public final static int TYPE_ROUNDABOUT_SEGMENT_NORTH=62;
	public final static int TYPE_ROUNDABOUT_SEGMENT_EAST=63;
	public final static int TYPE_ROUNDABOUT_SEGMENT_WEST=64;
	public final static int TYPE_ROUNDABOUT_SEGMENT_CIRCLE=65;
	public final static int TYPE_ROUNDABOUT_SEGMENT_NORTH_EAST=66;
	public final static int TYPE_ROUNDABOUT_SEGMENT_SOUTH_EAST=67;
	public final static int TYPE_ROUNDABOUT_SEGMENT_SOUTH_WEST=68;
	public final static int TYPE_ROUNDABOUT_SEGMENT_NORTH_WEST=69;

	public final static int TYPE_ROUNDABOUT_CROSSING_SOUTH=71;
	public final static int TYPE_ROUNDABOUT_CROSSING_NORTH=72;
	public final static int TYPE_ROUNDABOUT_CROSSING_EAST=73;
	public final static int TYPE_ROUNDABOUT_CROSSING_WEST=74;
	public final static int TYPE_ROUNDABOUT_CROSSING_CROSS=75;
	public final static int TYPE_ROUNDABOUT_CROSSING_NORTH_EAST=76;
	public final static int TYPE_ROUNDABOUT_CROSSING_SOUTH_EAST=77;
	public final static int TYPE_ROUNDABOUT_CROSSING_SOUTH_WEST=78;
	public final static int TYPE_ROUNDABOUT_CROSSING_NORTH_WEST=79;


	public final static int TYPE_UNKNOWN=-1;
	
	@SuppressWarnings("unused")
	private static DataModelSingleton model=null;
	public static PassengerCarModel newPassengerCar;
	public static BusModel newBus;
	public static TrafficLightModel newTrafficLight;
	public static TrafficsignNoEntryModel newNoEntry;
	public static TrafficsignGiveWayModel newGiveWay;
	public static TrafficsignOneWayModel newOneWay;
	public static TrafficsignConstructionSiteModel newConstructionSite;
	
	/*******************************************************************/
	/**  init              							                  **/
	/**  open of the xml file and initialization for reading the file **/
	/*******************************************************************/

	static public Element init() {
		
		Element root = null;
	
		// initialize logger for the class
		logger.setLevel(LogUtility.getLevel(Level.INFO));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		
		File f = new File("src/xml/city.xml");
		logger.info("XML filename: " +f.getAbsolutePath());
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(f);
			root = document.getRootElement();
		} catch (JDOMException e) {
			System.out.println("ENTERING EXCEPTION XMLREAD JDOME");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ENTERING EXCEPTION XMLREAD");
			e.printStackTrace();
		}
		return(root);
	}

	/*******************************************************************/
	/**  get_tile_from_xml              							  **/
	/**  looks in xml file if a tile is specified for grid_y and 	  **/
	/**  grid_x. If a tile is specified then the tile data is read    **/   
	/*******************************************************************/

	static public int get_tile_from_xml (Element root, Integer grid_y, Integer grid_x) throws DataConversionException {
		
		String name = null;
		
		// creates search string

		name = "grid_".concat(grid_y.toString()).concat("_").concat(grid_x.toString());
		logger.fine("Suchstring: " + name);
		List<Element> rrs = root.getChildren(name);
		logger.fine("Anzahl:" + name +"::" + rrs.size());
		
		if (rrs.size() > 0) {
			logger.fine(name +" -> GEFUNDEN!");
			Element rr = (Element) rrs.get(0);
			String type = rr.getAttribute("type").getValue();
			String dir = rr.getAttribute("direction").getValue();
			logger.fine("type = " + type);
			logger.fine("direction = " + dir);
			
    		switch(type){
    		case "road":
    			switch(dir){
    			case "south":
    				logger.fine("return type: road/south");
    				return TYPE_ROAD_SOUTH;
    			case "north":
    				logger.fine("return type: road/north");
    				return TYPE_ROAD_NORTH;
				case "east":
    				logger.fine("return type: road/east");
    				return TYPE_ROAD_EAST;
				case "west":
    				logger.fine("return type: road/west");
    				return TYPE_ROAD_WEST;
				case "north-west":
    				logger.fine("return type: road/north_west");
    				return TYPE_ROAD_NORTH_WEST;
				case "east-south":
    				logger.fine("return type: road/east_south");
    				return TYPE_ROAD_EAST_SOUTH;
				case "south-east":
    				logger.fine("return type: road/south_east");
    				return TYPE_ROAD_SOUTH_EAST;
				case "west-south":
    				logger.fine("return type: road/west_south");
    				return TYPE_ROAD_WEST_SOUTH;
				case "south-west":
    				logger.fine("return type: road/south_west");
    				return TYPE_ROAD_SOUTH_WEST;
				case "north-east":
    				logger.fine("return type: road/north_east");
    				return TYPE_ROAD_NORTH_EAST;
				case "east-north":
    				logger.fine("return type: road/east_north");
    				return TYPE_ROAD_EAST_NORTH;
				case "west-north":
    				logger.fine("return type: road/west_north");
    				return TYPE_ROAD_WEST_NORTH;
				default:
	    			System.out.println("ROAD DEFAULT");
    			}
	   		case "entry":
	   			switch(dir){
	    		case "south":
	    			logger.fine("return type: entry/south");
	    			return TYPE_ENTRY_SOUTH;
	   			case "north":
	   				logger.fine("return type: entry/north");
	   				return TYPE_ENTRY_NORTH;
				case "east":
	   				logger.fine("return type: entry/east");
	   				return TYPE_ENTRY_EAST;
				case "west":
	    			logger.fine("return type: entry/west");
	   				return TYPE_ENTRY_WEST;
				default:
	    			System.out.println("ENTRY DEFAULT");
	   			}
	   		case "exit":
	   			switch(dir){
	    		case "south":
	    			logger.fine("return type: exit/south");
	    			return TYPE_EXIT_SOUTH;
	   			case "north":
	   				logger.fine("return type: exit/north");
	   				return TYPE_EXIT_NORTH;
				case "east":
		  				logger.fine("return type: exit/east");
	   				return TYPE_EXIT_EAST;
				case "west":
	    			logger.fine("return type: exit/west");
	   				return TYPE_EXIT_WEST;
				default:
	    			System.out.println("EXIT DEFAULT");
	   			}
	   		case "crossing":
	   			switch(dir){
	    		case "south":
	    			logger.fine("return type: crossing/south");
	    			return TYPE_CROSSING_SOUTH;
	   			case "north":
	   				logger.fine("return type: crossing/north");
	   				return TYPE_CROSSING_NORTH;
				case "east":
	   				logger.fine("return type: crossing/east");
	   				return TYPE_CROSSING_EAST;
				case "west":
	    			logger.fine("return type: crossing/west");
	   				return TYPE_CROSSING_WEST;
				case "cross":
	    			logger.fine("return type: crossing/cross");
	   				return TYPE_CROSSING_CROSS; // for temporary use
				default:
	    			System.out.println("CROSS DEFAULT");
	   			}
		 	case "busstop":
		  		switch(dir){
		  		case "south":
		   			logger.fine("return type: busstop/south");
		   			return TYPE_BUSSTOP_SOUTH;
		   		case "north":
		   			logger.fine("return type: busstop/north");
		  			return TYPE_BUSSTOP_NORTH;
				case "east":
		  			logger.fine("return type: busstop/east");
		   			return TYPE_BUSSTOP_EAST;
				case "west":
		   			logger.fine("return type: busstop/west");
		   			return TYPE_BUSSTOP_WEST;
				case "south-west":
		   			logger.fine("return type: busstop/south-west");
		   			return TYPE_BUSSTOP_SOUTH_WEST;	
				case "south-east":
		   			logger.fine("return type: busstop/south-east");
		   			return TYPE_BUSSTOP_SOUTH_EAST;	
				case "north-west":
		   			logger.fine("return type: busstop/north-west");
		   			return TYPE_BUSSTOP_NORTH_WEST;	
				case "north-east":
		   			logger.fine("return type: busstop/north-east");
		   			return TYPE_BUSSTOP_NORTH_EAST;	
				default:
	    			System.out.println("BUSSTOP DEFAULT");
		  		}
			 case "round_s":
				switch(dir){
				case "south":
					logger.fine("return type: roundabout segment/south");
				 	return TYPE_ROUNDABOUT_SEGMENT_SOUTH;
				case "north":
					logger.fine("return type: roundabout segment/north");
					return TYPE_ROUNDABOUT_SEGMENT_NORTH;
				case "east":
			  		logger.fine("return type: roundabout segment/east");
			   		return TYPE_ROUNDABOUT_SEGMENT_EAST;
				case "west":
			   		logger.fine("return type: roundabout segment/west");
			   		return TYPE_ROUNDABOUT_SEGMENT_WEST;
				case "circle":
			   		logger.fine("return type: roundabout segment/circle");
			   		return TYPE_ROUNDABOUT_SEGMENT_CIRCLE;
				case "north-east":
			   		logger.fine("return type: roundabout segment/north-east");
			   		return TYPE_ROUNDABOUT_SEGMENT_NORTH_EAST;
				case "south-east":
			   		logger.fine("return type: roundabout segment/south-east");
			   		return TYPE_ROUNDABOUT_SEGMENT_SOUTH_EAST;
				case "south-west":
			   		logger.fine("return type: roundabout segment/south-west");
			   		return TYPE_ROUNDABOUT_SEGMENT_SOUTH_WEST;
				case "north-west":
			   		logger.fine("return type: roundabout segment/north-west");
			   		return TYPE_ROUNDABOUT_SEGMENT_NORTH_WEST;
			 	default:
	    			System.out.println("ROUND_S DEFAULT");
			 	}
			 case "round_c":
				switch(dir){
			  	case "south":
			 		logger.fine("return type: roundabout crossing/south");
			  		return TYPE_ROUNDABOUT_CROSSING_SOUTH;
			 	case "north":
			   		logger.fine("return type: roundabout crossing/north");
			  		return TYPE_ROUNDABOUT_CROSSING_NORTH;
				case "east":
			  		logger.fine("return type: roundabout crossing/east");
			   		return TYPE_ROUNDABOUT_CROSSING_EAST;
				case "west":
			   		logger.fine("return type: roundabout crossing/west");
			   		return TYPE_ROUNDABOUT_CROSSING_WEST;
				case "cross":
			   		logger.fine("return type: roundabout crossing/cross");
			   		return TYPE_ROUNDABOUT_CROSSING_CROSS;
				case "north-east":
			   		logger.fine("return type: roundabout crossing/north-east");
			   		return TYPE_ROUNDABOUT_CROSSING_NORTH_EAST;
				case "south-east":
			   		logger.fine("return type: roundabout crossing/south-east");
			   		return TYPE_ROUNDABOUT_CROSSING_SOUTH_EAST;
				case "south-west":
			   		logger.fine("return type: roundabout crossing/south-west");
			   		return TYPE_ROUNDABOUT_CROSSING_SOUTH_WEST;
				case "north-west":
			   		logger.fine("return type: roundabout crossing/north-west");
			   		return TYPE_ROUNDABOUT_CROSSING_NORTH_WEST;
				default:
	    			System.out.println("ROUND_C DEFAULT");
				}
			 default:
	    			System.out.println("TYPE DEFAULT");
		   	}
		}
		else {
			logger.fine(name +" -> nicht gefunden!");
		}
		return TYPE_UNKNOWN;
	}
	
	/*******************************************************************/
	/**  readCars              							              **/
	/**  reading of predefined cars in the xml file                   **/
	/**  and creation of the objects                                  **/
	/*******************************************************************/
	
	static public PassengerCarModel readCars (Element root, String name, ExitRoadTileModel target) throws DataConversionException {

		
		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> Car found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			String dir = rr.getAttribute("direction").getValue();
			Integer speed = rr.getAttribute("speed").getIntValue();
			String color = rr.getAttribute("color").getValue();

			logger.fine("Car - grid_x: " +grid_x);
			logger.fine("Car - grid_y: " +grid_y);
			logger.fine("Car - direction: " +dir);
			logger.fine("Car - speed: " +speed);
			logger.fine("Car - color: " +color);
			
			logger.info("Create a new Car: " +grid_x +grid_y +dir +speed +color);
			// create a new passenger car with params X,Y,layer,direction,speed,color,bitmap - X and Y are the start positions
			
			RandomComponents randomComponents = new RandomComponents();
			int DirValue = DirectionText_to_DirectionValue(dir);
			String BitmapPath = randomComponents.bitmapPathBuilder(color, DirValue);

			newPassengerCar = new PassengerCarModel(grid_x, grid_y, 3, DirValue, speed, color, 
					new Bitmap(new String(BitmapPath)),target);
			return(newPassengerCar);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readBus              							              **/
	/**  reading of predefined buses in the xml file                  **/
	/**  and creation of the objects                                  **/
	/*******************************************************************/

	static public BusModel readBus (Element root, String name, ExitRoadTileModel target) throws DataConversionException {

		
		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> Bus found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			String dir = rr.getAttribute("direction").getValue();
			Integer speed = rr.getAttribute("speed").getIntValue();
			String color = rr.getAttribute("color").getValue();

			logger.fine("Bus - grid_x: " +grid_x);
			logger.fine("Bus - grid_y: " +grid_y);
			logger.fine("Bus - direction: " +dir);
			logger.fine("Bus - speed: " +speed);
			logger.fine("Bus - color: " +color);
			
			logger.info("Create a new Bus: " +grid_x +grid_y +dir +speed +color);
			// create a new bus with params X,Y,layer,direction,speed,color,bitmap - X and Y are the start positions
			
			RandomComponents randomComponents = new RandomComponents();
			int DirValue = DirectionText_to_DirectionValue(dir);
			String BitmapPath = randomComponents.bitmapPathBuilder(color, DirValue);

			newBus = new BusModel(grid_x, grid_y, 3, DirValue, speed, color, 
					new Bitmap(new String(BitmapPath)),target);
			return(newBus);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readTrafficSignNoEntry         				              **/
	/**  reading of the no entry traffic signs and                    **/
	/**  creation of the objects									  **/
	/*******************************************************************/

	static public TrafficsignNoEntryModel readTrafficSignNoEntry(Element root, String name) throws DataConversionException {

		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> No Entry Traffic Sign found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			Integer layer = rr.getAttribute("layer").getIntValue();
			String dir = rr.getAttribute("direction").getValue();

			logger.fine("NoEntry - grid_x: " +grid_x);
			logger.fine("NoEntry - grid_y: " +grid_y);
			logger.fine("NoEntry - layer: " +layer);
			logger.fine("NoEntry - direction: " +dir);
			
			logger.info("Create a new No Entry traffic sign: " +grid_x +grid_y +layer +dir);
			// create a new no entry traffic sign
			
			int DirValue = DirectionText_to_DirectionValue(dir);

			newNoEntry = new TrafficsignNoEntryModel(grid_x, grid_y, layer, DirValue);
			return(newNoEntry);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readTrafficSignGiveWay         				              **/
	/**  reading of the "give way" traffic signs and                  **/
	/**  creation of the objects									  **/
	/*******************************************************************/

	static public TrafficsignGiveWayModel readTrafficSignGiveWay(Element root, String name) throws DataConversionException {
		
		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> Give Way Traffic Sign found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			Integer layer = rr.getAttribute("layer").getIntValue();
			String dir = rr.getAttribute("direction").getValue();

			logger.fine("GiveWay - grid_x: " +grid_x);
			logger.fine("GiveWay - grid_y: " +grid_y);
			logger.fine("GiveWay - layer: " +layer);
			logger.fine("GiveWay - direction: " +dir);
			
			logger.info("Create a new Give Way traffic sign: " +grid_x +grid_y +layer +dir);
			// create a new Give Way traffic sign
			
			int DirValue = DirectionText_to_DirectionValue(dir);

			newGiveWay = new TrafficsignGiveWayModel(grid_x, grid_y, layer, DirValue);
			return(newGiveWay);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readTrafficSignOneWay         	     			              **/
	/**  reading of the "One Way" traffic signs and                   **/
	/**  creation of the objects									  **/
	/*******************************************************************/

	static public TrafficsignOneWayModel readTrafficSignOneWay(Element root, String name) throws DataConversionException {
		
		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> One Way Traffic Sign found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			Integer layer = rr.getAttribute("layer").getIntValue();
			String dir = rr.getAttribute("direction").getValue();

			logger.fine("OneWay - grid_x: " +grid_x);
			logger.fine("OneWay - grid_y: " +grid_y);
			logger.fine("OneWay - layer: " +layer);
			logger.fine("OneWay - direction: " +dir);
			
			logger.info("Create a new One Way traffic sign: " +grid_x +grid_y +layer +dir);
			// create a new One Way traffic sign
			
			int DirValue = DirectionText_to_DirectionValue(dir);

			newOneWay = new TrafficsignOneWayModel(grid_x, grid_y, layer, DirValue);
			return(newOneWay);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readTrafficSignConstructionSite     			              **/
	/**  reading of the "Construction site" traffic signs and         **/
	/**  creation of the objects									  **/
	/*******************************************************************/

	static public TrafficsignConstructionSiteModel readTrafficSignConstructionSite(Element root, String name) throws DataConversionException {
		
		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> Construction Site Traffic Sign found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			Integer layer = rr.getAttribute("layer").getIntValue();
			String dir = rr.getAttribute("direction").getValue();

			logger.fine("ConstructionSite - grid_x: " +grid_x);
			logger.fine("ConstructionSite - grid_y: " +grid_y);
			logger.fine("ConstructionSite - layer: " +layer);
			logger.fine("ConstructionSite - direction: " +dir);
			
			logger.info("Create a new Construction Site traffic sign: " +grid_x +grid_y +layer +dir);
			// create a new Construction Site traffic sign
			
			int DirValue = DirectionText_to_DirectionValue(dir);

			newConstructionSite = new TrafficsignConstructionSiteModel(grid_x, grid_y, layer, DirValue);
			return(newConstructionSite);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  readTrafficLight     			                              **/
	/**  reading of the traffic lights and                            **/
	/**  creation of the objects									  **/
	/*******************************************************************/

	static public TrafficLightModel readTrafficLight (Element root, String name) throws DataConversionException {

		List<Element> rrs = root.getChildren(name);

		if (rrs.size() > 0) {
			logger.fine(name +" -> Traffic Light found in xml!");
			Element rr = (Element) rrs.get(0);
			Integer grid_x = rr.getAttribute("grid_x").getIntValue();
			Integer grid_y = rr.getAttribute("grid_y").getIntValue();
			Integer layer = rr.getAttribute("layer").getIntValue();
			String dir = rr.getAttribute("direction").getValue();
			String color = rr.getAttribute("color").getValue();

			logger.info("Create a new Traffic Light: " +grid_x +grid_y +layer +dir +color);

			int DirValue = DirectionText_to_DirectionValue(dir);

			// create a new traffic light with params X,Y,layer,direction,color
			newTrafficLight = new TrafficLightModel(grid_x, grid_y, layer, DirValue, color);
		
			return (newTrafficLight);
		}
		else {
//			System.out.println("not existing in XML: " + name);
			return null;
		}
	}

	/*******************************************************************/
	/**  DirectionText_to_DirectionValue                              **/
	/**  converting text direction to a number                        **/
	/*******************************************************************/

	public static int DirectionText_to_DirectionValue(String DirectionText) {
		
		switch(DirectionText){		
		case "north":
			return 1;
		case "north-east":
			return 2;
		case "east":
			return 3;
		case "south-east":
			return 4;
		case "south":
			return 5;
		case "south-west":
			return 6;
		case "west":
			return 7;
		case "north-west":
			return 8;
		default:
			System.out.println("invalid direction");
			return (-1);
		}
	}
		
	
}
