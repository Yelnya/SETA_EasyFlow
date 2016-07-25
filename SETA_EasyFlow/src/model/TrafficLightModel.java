package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import util.Bitmap;
import util.LogUtility;
import view.GridView;
import view.TrafficLightView;
import view.TrafficSignView;
/*******************************************************************/
/**  Class TrafficLightModel                                      **/
/**                                                               **/ 
/**  model for traffic lights                                     **/ 
/*******************************************************************/
public class TrafficLightModel extends BaseModel
{
	//ATTRIBUTES
	private Integer gridposX = null;
	private Integer gridposY = null;
	private Integer layer = null;
	private volatile Integer trafficLightDirection = 0; // N=1, O=3, S=5, W=7
	private volatile String trafficLightColor = ""; // red or green
	private volatile Bitmap trafficLightBitmap = null; // will be determined
												// automatically in method
												// createTrafficLightBitmap()
	private Bitmap trafficJamBitmap = null;
	private static DataModelSingleton model = null;
	private volatile Integer counter = 0; // for trafficLight Interval
	private Integer redPhase = null; // length of red phase in controller cycles
	private Integer greenPhase = null; // length of green phase in controller
										// cycles
	
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(TrafficLightModel.class.getName()));
	
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public TrafficLightModel(Integer posX, Integer posY, Integer layer,
	Integer direction, String color) {
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		
		model = DataModelSingleton.getInstance();
		setGridposX(posX);
		setGridposY(posY);
		setLayer(layer);
		setTrafficLightDirection(direction);
		setTrafficLightColor(color);
		setTrafficLightBitmap(createTrafficLightBitmap(direction, color));
		setRedPhase(model.getDefaultRedPhase());
		setGreenPhase(model.getDefaultGreenPhase());
		
	} // end of constructor

	// GETTERS AND SETTERS
	public Integer getGridposX() {
		return gridposX;
	}
	public void setGridposX(Integer gridposX) {
		this.gridposX = gridposX;
	}

	public Integer getGridposY() {
		return gridposY;
	}
	public void setGridposY(Integer gridposY) {
		this.gridposY = gridposY;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Bitmap getTrafficLightBitmap() {
		return trafficLightBitmap;
	}
	public void setTrafficLightBitmap(Bitmap trafficLightBitmap) {
		this.trafficLightBitmap = trafficLightBitmap;
	}

	public Integer getTrafficLightDirection() {
		return trafficLightDirection;
	}
	public void setTrafficLightDirection(Integer trafficLightDirection) {
		this.trafficLightDirection = trafficLightDirection;
	}

	public String getTrafficLightColor() {
		return trafficLightColor;
	}
	public void setTrafficLightColor(String trafficLightColor) {
		this.trafficLightColor = trafficLightColor;
	}

	public Integer getRedPhase() {
		return redPhase;
	}

	public void setRedPhase(Integer redPhase) {
		this.redPhase = redPhase;
	}

	public Integer getGreenPhase() {
		return greenPhase;
	}

	public void setGreenPhase(Integer greenPhase) {
		this.greenPhase = greenPhase;
	}
	
	
	
	//METHODS
	
	public Integer getCounter() {
		return counter;
	}


	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public Bitmap getTrafficJamBitmap() {
		return trafficJamBitmap;
	}

	public void setTrafficJamBitmap(Bitmap trafficJamBitmap) {
		this.trafficJamBitmap = trafficJamBitmap;
	}

	/*****************************************************************************/
	/** CREATE BITMAPS AUTOMATICALLY */
	/*****************************************************************************/

	// creates bitmap for current trafficLight automatically, needs information
	// about direction and color
	public static Bitmap createTrafficLightBitmap(Integer direction,
			String color) {
		Bitmap currentBitmap = null;
		
		if (direction.equals(1) && color.equals("red"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_n_red.png"));
		}
		else if (direction.equals(1) && color.equals("green"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_n_green.png"));
		}
		else if (direction.equals(3) && color.equals("red"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_o_red.png"));
		}
		else if (direction.equals(3) && color.equals("green"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_o_green.png"));
		}
		else if (direction.equals(5) && color.equals("red"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_s_red.png"));
		}
		else if (direction.equals(5) && color.equals("green"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_s_green.png"));
		}
		else if (direction.equals(7) && color.equals("red"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_w_red.png"));
		}
		else if (direction.equals(7) && color.equals("green"))
		{
			currentBitmap = new Bitmap(new String("src/_resources/tiles/trafficLights/trafficLight_w_green.png"));
		}
		return currentBitmap;
	} // end of method createTrafficLightBitmap()

	/*****************************************************************************/
	/** SWITCH TRAFFIC LIGHTS MANUALLY */
	/*****************************************************************************/

	// method to automatically switch all other connected trafficlights on the
	// crossing, if one is switched
	@SuppressWarnings("static-access")
	public void switchTrafficLights(TrafficLightModel trafficLightModel) {
		TrafficLightModel trafficLightModelNorth = null;
		TrafficLightModel trafficLightModelEast = null;
		TrafficLightModel trafficLightModelSouth = null;
		TrafficLightModel trafficLightModelWest = null;
		
		//1. DIRECTION = NORTH
		//if trafficlight faces direction north - search the other connected trafficlights, then change color and Bitmap of object
		if(trafficLightModel.getTrafficLightDirection().equals(1))
		{
			//defining the connected trafficLightModels from model.getGridModel().getActualTrafficLightsArray list
			for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
	    	{
	    		//traffic light east
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()+1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()))
	    		{
	    			trafficLightModelEast = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light south
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()+1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()+1))
	    		{
	    			trafficLightModelSouth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light west
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()+1))
	    		{
	    			trafficLightModelWest = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		else
	    		{
	    			//do nothing
	    		}
	    		
	    	}
			
			GridView.getPanel().remove(trafficLightModel.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelEast.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelSouth.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelWest.getTrafficLightBitmap().getBitmapLabel());
			
			if (trafficLightModel.getTrafficLightColor().equals("green"))
			{
				trafficLightModel.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(1, "red"));
				logger.info("North switched to red");
				//TODO: method drawOneTrafficLight() in TrafficLightView has to be called
				
				//now, switch the connected trafficlight in east, south, west as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//EAST
				trafficLightModelEast.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "green"));
				logger.info("East switched to green");
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "red"));
				logger.info("South switched to red");
				//WEST
				trafficLightModelWest.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "green"));
				logger.info("West switched to green");
			}
			else
			{
				trafficLightModel.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(1, "green"));
				logger.info("North switched to green");
				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in east, south, west as well (get models from model.getGridModel().getActualTrafficLightsArray)
				
				//EAST
				trafficLightModelEast.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "red"));
				logger.info("East switched to red");
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "green"));
				logger.info("South switched to green");
				//WEST
				trafficLightModelWest.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "red"));
				logger.info("West switched to red");
			}
			//reset counter for all connected trafficLights
			trafficLightModel.setCounter(0);
			trafficLightModelEast.setCounter(0);
			trafficLightModelSouth.setCounter(0);
			trafficLightModelWest.setCounter(0);
			logger.info("Counter of these 4 trafficLights has been resetted to 0 !");
		}
		
		//2. DIRECTION = EAST
		//if trafficlight faces direction east - search the other connected trafficlights, then change color and Bitmap of object
		else if(trafficLightModel.getTrafficLightDirection().equals(3))
		{
			//defining the connected trafficLightModels from model.getGridModel().getActualTrafficLightsArray list
			for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
	    	{
	    		//traffic light south
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()+1))
	    		{
	    			trafficLightModelSouth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light west
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()-1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()+1))
	    		{
	    			trafficLightModelWest = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light north
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()-1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()))
	    		{
	    			trafficLightModelNorth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		else
	    		{
	    			//do nothing
	    		}
	    		
	    	}

			GridView.getPanel().remove(trafficLightModel.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelNorth.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelSouth.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelWest.getTrafficLightBitmap().getBitmapLabel());
			
			if (trafficLightModel.getTrafficLightColor().equals("red"))
			{
				trafficLightModel.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(3, "green"));
				logger.info("East switched to green");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				
				//now, switch the connected trafficlight in south, west, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "red"));
				logger.info("South switched to red");
				//WEST
				trafficLightModelWest.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "green"));
				logger.info("West switched to green");
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "red"));
				logger.info("North switched to red");
			}
			else
			{
				trafficLightModel.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(3, "red"));
				logger.info("East switched to red");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in south, west, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "green"));
				logger.info("South switched to green");
				//WEST
				trafficLightModelWest.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "red"));
				logger.info("West switched to red");
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "green"));
				logger.info("North switched to green");
			}
			//reset counter for all connected trafficLights
			trafficLightModel.setCounter(0);
			trafficLightModelNorth.setCounter(0);
			trafficLightModelSouth.setCounter(0);
			trafficLightModelWest.setCounter(0);
			logger.info("Counter of these 4 trafficLights has been resetted to 0 !");
		}
		
		//3. DIRECTION = SOUTH
		//if trafficlight faces direction south - search the other connected trafficlights, then change color and Bitmap of object
		else if(trafficLightModel.getTrafficLightDirection().equals(5))
		{
			//defining the connected trafficLightModels from model.getGridModel().getActualTrafficLightsArray list
			for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
	    	{
	    		//traffic light west
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()-1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()))
	    		{
	    			trafficLightModelWest = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light north
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()-1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()-1))
	    		{
	    			trafficLightModelNorth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light east
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()-1))
	    		{
	    			trafficLightModelEast = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		else
	    		{
	    			//do nothing
	    		}
	    		
	    	}
			
			GridView.getPanel().remove(trafficLightModel.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelNorth.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelEast.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelWest.getTrafficLightBitmap().getBitmapLabel());
				
			if (trafficLightModel.getTrafficLightColor().equals("red"))
			{
				trafficLightModel.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(5, "green"));
				logger.info("South switched to green");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in east, west, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//WEST
				trafficLightModelWest.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "red"));
				logger.info("West switched to red");
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "green"));
				logger.info("North switched to green");
				//EAST
				trafficLightModelEast.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "red"));
				logger.info("East switched to red");
			}
			else
			{
				trafficLightModel.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(5, "red"));
				logger.info("South switched to red");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in east, west, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//WEST
				trafficLightModelWest.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelWest.getView()).delete(); //delete existing trafficLightView
				trafficLightModelWest.setTrafficLightBitmap(createTrafficLightBitmap(7, "green"));
				logger.info("West switched to green");
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "red"));
				logger.info("North switched to red");
				//EAST
				trafficLightModelEast.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "green"));
				logger.info("East switched to green");
			}
			//reset counter for all connected trafficLights
			trafficLightModel.setCounter(0);
			trafficLightModelNorth.setCounter(0);
			trafficLightModelEast.setCounter(0);
			trafficLightModelWest.setCounter(0);
			logger.info("Counter of these 4 trafficLights has been resetted to 0 !");
		}
		
		//4. DIRECTION = WEST
		//if trafficlight faces direction west - search the other connected trafficlights, then change color and Bitmap of object
		else if(trafficLightModel.getTrafficLightDirection().equals(7))
		{
			//defining the connected trafficLightModels from model.getGridModel().getActualTrafficLightsArray list
			for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
	    	{
	    		//traffic light north
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()-1))
	    		{
	    			trafficLightModelNorth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light east
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()+1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()-1))
	    		{
	    			trafficLightModelEast = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		//traffic light south
	    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(trafficLightModel.getGridposX()+1) &&
	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(trafficLightModel.getGridposY()))
	    		{
	    			trafficLightModelSouth = model.getGridModel().getActualTrafficLightsArray().get(i);
	    		}
	    		else
	    		{
	    			//do nothing
	    		}		
	    	}
			
			GridView.getPanel().remove(trafficLightModel.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelNorth.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelEast.getTrafficLightBitmap().getBitmapLabel());
			GridView.getPanel().remove(trafficLightModelSouth.getTrafficLightBitmap().getBitmapLabel());
			
			if (trafficLightModel.getTrafficLightColor().equals("red"))
			{				
				trafficLightModel.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(7, "green"));
				logger.info("West switched to green");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in south, east, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "red"));
				logger.info("North switched to red");
				//EAST
				trafficLightModelEast.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "green"));
				logger.info("East switched to green");
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "red"));
				logger.info("South switched to red");
			}
			else
			{
				trafficLightModel.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModel.getView()).delete(); //delete existing trafficLightView
				trafficLightModel.setTrafficLightBitmap(createTrafficLightBitmap(7, "red"));
				logger.info("West switched to red");

				//method drawOneTrafficLight() in TrafficLightView has to be called
				//now, switch the connected trafficlight in south, east, north as well (get models from model.getGridModel().getActualTrafficLightsArray)
				//NORTH
				trafficLightModelNorth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelNorth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelNorth.setTrafficLightBitmap(createTrafficLightBitmap(1, "green"));
				logger.info("North switched to green");
				//EAST
				trafficLightModelEast.setTrafficLightColor("red");
				//((TrafficLightView) trafficLightModelEast.getView()).delete(); //delete existing trafficLightView
				trafficLightModelEast.setTrafficLightBitmap(createTrafficLightBitmap(3, "red"));
				logger.info("East switched to red");
				//SOUTH
				trafficLightModelSouth.setTrafficLightColor("green");
				//((TrafficLightView) trafficLightModelSouth.getView()).delete(); //delete existing trafficLightView
				trafficLightModelSouth.setTrafficLightBitmap(createTrafficLightBitmap(5, "green"));
				logger.info("South switched to green");
			}
			//reset counter for all connected trafficLights
			trafficLightModel.setCounter(0);
			trafficLightModelNorth.setCounter(0);
			trafficLightModelEast.setCounter(0);
			trafficLightModelSouth.setCounter(0);
			logger.info("Counter of these 4 trafficLights has been resetted to 0 !");
		}
		else
		{
			//do nothing
		}
		if (trafficLightModel != null) {

			((TrafficLightView) trafficLightModel.getView()).updateChange();
		}
		if (trafficLightModelNorth != null) {
			((TrafficLightView) trafficLightModelNorth.getView())
					.updateChange();
		}
		if (trafficLightModelEast != null) {
			((TrafficLightView) trafficLightModelEast.getView()).updateChange();
		}
		if (trafficLightModelSouth != null) {
			((TrafficLightView) trafficLightModelSouth.getView())
					.updateChange();
		}
		if (trafficLightModelWest != null) {
			((TrafficLightView) trafficLightModelWest.getView()).updateChange();
		}
	} // end of method switchTrafficLights()

	/*****************************************************************************/
	/** SWITCH TRAFFIC LIGHTS AUTOMATICALLY */
	/*****************************************************************************/

	// TODO: change Light green->red and red->green after interval end --->
	// change all other lights depending on change

	public void switchTrafficLightsIntervalAutomatically() // obsolete: will be
															// replaced by
															// switchTrafficLight
	{

		if (this.getCounter() < 10) {
			this.setCounter(this.getCounter() + 1); // increase counter by one
		} else {
			this.setCounter(0); // reset counter to null again
			// and switch trafficLights
			if (this.getTrafficLightColor().equals("red")) {
				this.setTrafficLightColor("green");
				
				((TrafficLightView)this.getView()).deleteLabel();
				this.setTrafficLightBitmap(createTrafficLightBitmap(this.getTrafficLightDirection(), this.getTrafficLightColor()));
			}
			else
			{
				this.setTrafficLightColor("red");

				((TrafficLightView)this.getView()).deleteLabel();
				this.setTrafficLightBitmap(createTrafficLightBitmap(this.getTrafficLightDirection(), this.getTrafficLightColor()));
			}
		}
	} // end of switchTrafficLightsIntervalAutomatically()

	public synchronized Boolean switchTrafficLightIntervall() {
		// switch trafficLights, called by controller basic rules
		Boolean modified=false;
		int counter = getCounter();

		if (getTrafficLightColor().equals("red") && counter == getRedPhase()) {
			this.setTrafficLightColor("green");
			((TrafficLightView)this.getView()).deleteLabel();
			setTrafficLightBitmap(createTrafficLightBitmap(this.getTrafficLightDirection(), this.getTrafficLightColor()));
			setCounter(0); // reset counter to null again
			modified=true;
			logger.info("set to green !");
		} else {

			if (getTrafficLightColor().equals("green")
					&& counter == getGreenPhase()) {
				this.setTrafficLightColor("red");
				((TrafficLightView)this.getView()).deleteLabel();
				setTrafficLightBitmap(createTrafficLightBitmap(this.getTrafficLightDirection(), this.getTrafficLightColor()));
				setCounter(0); // reset counter to null again
				modified=true;
				logger.info("set to red !");
				
			} else {
				setCounter(counter+1); // increase counter by one
				
			}

		}
		// and switch trafficLights
		return modified;
	} // end of switchTrafficLights()

	public void switchTrafficLight()
	{
			//switch trafficLights, called by controller basic rules
			if (this.getTrafficLightColor().equals("red"))
			{
				this.setTrafficLightColor("green");
			}
			else
			{
				this.setTrafficLightColor("red");
			}
		
	} //end of switchTrafficLights()

	//TRAFFIC JAM DETECTION
	public Boolean trafficJamDetection()
	{
		Boolean isJam = false;
		Integer x = this.getGridposX();
		Integer y = this.getGridposY();
		
		Integer jamCounter = 0;
		
		for (int i=1; i <= 6; i++)
		{
			if(this.getTrafficLightDirection().equals(1))	//NORTH
			{
				if (model.getGridModel().findOneVehicleInModelsList(x, y-i).equals(true))
				{
					jamCounter++;
					if(jamCounter.equals(3))
					{
						if (this.getTrafficLightColor().equals("red"))
						{
							this.switchTrafficLights(this);
						}				
						return isJam = true;
					}
				}
			}
			else if(this.getTrafficLightDirection().equals(3))	//EAST
			{
				if (model.getGridModel().findOneVehicleInModelsList(x+i, y).equals(true))
				{
					jamCounter++;
					if(jamCounter.equals(3))
					{
						if (this.getTrafficLightColor().equals("red"))
						{
							this.switchTrafficLights(this);
						}	
						return isJam = true;
					}
				}
			}
			else if(this.getTrafficLightDirection().equals(5))	//SOUTH
			{
				if (model.getGridModel().findOneVehicleInModelsList(x, y+i).equals(true))
				{
					jamCounter++;
					if(jamCounter.equals(3))
					{
						if (this.getTrafficLightColor().equals("red"))
						{
							this.switchTrafficLights(this);
						}	
						return isJam = true;
					}
				}
			}
			else if(this.getTrafficLightDirection().equals(7))	//WEST
			{
				if (model.getGridModel().findOneVehicleInModelsList(x-i, y).equals(true))
				{
					jamCounter++;
					if(jamCounter.equals(3))
					{
						if (this.getTrafficLightColor().equals("red"))
						{
							this.switchTrafficLights(this);
						}	
						return isJam = true;
					}
				}
			}
		}
		return isJam;
	}

	
	// this constructor is not yet supported by view
	public TrafficLightModel(Integer posX, Integer posY, Integer layer,
			Integer direction, String color, Integer redPhase,
			Integer greenPhase) {
		model = DataModelSingleton.getInstance();
		setGridposX(posX);
		setGridposY(posY);
		setLayer(layer);
		setTrafficLightDirection(direction);
		setTrafficLightColor(color);
		setTrafficLightBitmap(createTrafficLightBitmap(direction, color));
		setRedPhase(redPhase);
		setGreenPhase(greenPhase);

	} // end of constructor
	  // @ the end because of SVN resolve conflict problem...



} // end of class
