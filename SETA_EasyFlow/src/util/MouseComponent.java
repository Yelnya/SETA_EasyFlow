package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import view.PopClickListener;
import model.DataModelSingleton;

@SuppressWarnings("serial")
public class MouseComponent extends JComponent implements MouseListener
{
	private PopClickListener popclick = new PopClickListener();  //instantiate PopClickListener
	private Integer rightClickedX;
	private Integer rightClickedY;
	private String tileType; 
	private String text;
	private static DataModelSingleton model=null;
	
	public MouseComponent() 
	{
		model=DataModelSingleton.getInstance();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	//METHOD: what happens if mouse button is clicked
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		//if right mouse button is clicked
		try {
			if(SwingUtilities.isRightMouseButton(e))
			{
				setRightClickedX(e.getX()/16 -1);
			    setRightClickedY(e.getY()/16 -1);
			    setTileType(DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()][getRightClickedX()].getTileType());
			    
			    System.out.println("x: " + (getRightClickedX()+1) + ", y: " + (getRightClickedY()+1) + " clicked! -> TileType: " + getTileType() +
			    		", Direction: " + DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()][getRightClickedX()].getDirection());
			    System.out.println((getRightClickedX()+1) +","+(getRightClickedY()+1));//these co-ords are relative to the component
			    
			    //IF ROAD TILE
			    if(getTileType().equals("RoadTile"))	//only allowed on road tiles
			    {
			    	//TRY OPENING POPUPMENU FOR CONSTRUCTIONSIGN
			    	//if there is is no ConstructionSign, CrossingTile, RoundAboutCrossing, ExitRoad, EntryRoadTile in x+-2 and y+-2
			    	if (checkTileTypesOfRange(getRightClickedX()+1, getRightClickedY()+1).equals(true))
			    	{
			    		//System.out.println("Range is clear");
			    		popclick.doPopStreet(e, getTileType(), getRightClickedX()+1, getRightClickedY()+1);
			    	}
			    	//TRY OPENING POPUPMENU TO DELETE CONSTRUCTION SIGN IF THERE ALREADY IS A CONSTRUCTIONSIGN
			    	else 
			    		if (checkIfTileIsConstructionSign(getRightClickedX()+1, getRightClickedY()+1).equals(true))
			    	{
			    		popclick.doPopDeleteConstructionSign(e, getTileType(), getRightClickedX()+1, getRightClickedY()+1);
			    		
			    	}
			    	
			    	else
			    	{
			    		//do nothing
			    		//System.out.println("Range blocked.");
			    	}	    	
			    }
			    //IF CROSSING TILE
			    else if (getTileType().equals("CrossingTile"))
			    {
			    	System.out.println("Crossing clicked.");
			    	//OPEN POPUP AT TRAFFICLIGHT
			    	//if there is a TrafficLight, open PopupMenu
			    	for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
			    	{
			    		//if there is a valid TrafficLight in List arrayTrafficLights (or later: Grid): open PopupMenu for TrafficLights
			    		if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(getRightClickedX()+1) && model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(getRightClickedY()+1))
			    		{
			    			System.out.println(
				    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX() + ", " +
		    						model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY() + ", " +
		    						model.getGridModel().getActualTrafficLightsArray().get(i).getTrafficLightDirection() + ", " +
		    						model.getGridModel().getActualTrafficLightsArray().get(i).getTrafficLightColor()
				    				);
				    		popclick.doPopCrossing(e, getTileType(), getRightClickedX()+1, getRightClickedY()+1);
			    		}
			    		else
			    		{
			    			//do nothing
			    		}
			    		
			    	}
			    	
			    }
			    else
			    {
			    }		    
			}
		} 
		catch (Exception e1) 
		{
			//System.out.println("ENTERING EXCEPTION MOUSECOMPONENT");
			//e1.printStackTrace();
		}
		
		
	}

	//GETTERS AND SETTERS
	public Integer getRightClickedX() {
		return rightClickedX;
	}

	public void setRightClickedX(Integer rightClickedX) {
		this.rightClickedX = rightClickedX;
	}

	public Integer getRightClickedY() {
		return rightClickedY;
	}

	public void setRightClickedY(Integer rightClickedY) {
		this.rightClickedY = rightClickedY;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	
	
	/*********************************************************************/
	/**                  CHECK TILE TYPES IN RANGE                      **/
	/*********************************************************************/
	//Check range around current Tile
	@SuppressWarnings({ "unchecked" })
	public Boolean checkTileTypesOfRange(Integer posX, Integer posY)
	{
		//obstacles: CrossingTile, RoundAboutCrossing, ExitRoad, BusStopRoadTile, RoundAboutSegmentTile EntryRoadTile in x+-2 and y+-2
		Boolean obstacleClear = false;	//if range is clear
		@SuppressWarnings("rawtypes")
		List obstacleList = new ArrayList<String>();
		
		
		obstacleList.clear();
		
		if ((2 < posX && posX < 45) && (2 < posY && posY < 43))
		{
			for (int j = -2; j <= 2 ; j++)
			{
				for (int i = -2; i <= 2; i++)
				{
					//add all tileType names of periphery
					obstacleList.add(DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()+j][getRightClickedX()+i].getTileType());
					try {
						//where road curve is in periphery, no construction sign can be set
						if (DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()+j][getRightClickedX()+i].getDirection().equals(2) ||
							DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()+j][getRightClickedX()+i].getDirection().equals(4) ||
							DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()+j][getRightClickedX()+i].getDirection().equals(6) ||
							DataModelSingleton.getInstance().getGridModel().getActualTilesArray()[getRightClickedY()+j][getRightClickedX()+i].getDirection().equals(8))
						{
							obstacleList.add("CurvedRoad");
						}
					} catch (Exception e) {
						//System.out.println("ENTERING EXCEPTION MOUSECOMPONENT CHECKTILETYPESOFRANGE()");
						//do nothing
					}
				}
			}
			
			//where construction sign is in periphery, no second construction sign can be added
			for (int x = 0; x < model.getGridModel().getActualTrafficSignsArray().size(); x++)
			{
				for (int j = -2; j <= 2; j++)
				{
					for (int i = -2; i <= 2; i++)
					{
						if (model.getGridModel().getActualTrafficSignsArray().get(x).getPosX().equals(posX+i) &&
								model.getGridModel().getActualTrafficSignsArray().get(x).getPosY().equals(posY+j))
						{
							//System.out.println("There already is a construction sign in periphery !");
							obstacleList.add("ConstructionSign");
						}
					}
				}
			}
			
			//ask obstacleList if there is an obstacle in periphery
			for (int i = 0; i < obstacleList.size(); i++)
			{		
				if (obstacleList.get(i).toString().equals("CrossingTile") || 
						obstacleList.get(i).toString().equals("RoundAboutCrossing") ||
						obstacleList.get(i).toString().equals("ExitRoad") ||
						obstacleList.get(i).toString().equals("EntryRoadTile") ||
						obstacleList.get(i).toString().equals("BusStopRoadTile") ||
						obstacleList.get(i).toString().equals("RoundAboutSegmentTile") ||
						obstacleList.get(i).toString().equals("ConstructionSign") ||
						obstacleList.get(i).toString().equals("Vehicle") ||
						obstacleList.get(i).toString().equals("CurvedRoad"))
				{
					//System.out.println("OBSTACLE: " + obstacleList.get(i).toString() + " too near ! No Construction Sign can be added here !");
					obstacleClear = false;
					return obstacleClear;
				}
			}
			
			//if one of the entries in the list is an obstacle, return clear false - else return clear true
			obstacleClear = true;

		}
			//System.out.println("ObstacleClear Value: " + obstacleClear.toString());
			return obstacleClear;
		
	}
	
	
	/*********************************************************************/
	/**         CHECK IF TILE IS CONSTRUCTION SIGN                      **/
	/*********************************************************************/
	
	public Boolean checkIfTileIsConstructionSign(Integer posX, Integer posY)
	{
		Boolean constructionSignCheck = false;
		//ask if there is construction sign on tile - if yes return true
		for (int i = 0; i < model.getGridModel().getActualTrafficSignsArray().size(); i++)
		{
			if (model.getGridModel().getActualTrafficSignsArray().get(i).getPosX().equals(posX) &&
					model.getGridModel().getActualTrafficSignsArray().get(i).getPosY().equals(posY))
			{
				System.out.println("There already is a construction sign on this tile!");
				return constructionSignCheck = true;
			}
			else
			{
				//do nothing - left to false
			}
		}
		
		return constructionSignCheck;
	}
	
}
