package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import util.LogUtility;
import util.RandomComponents;
import model.BaseModel;
import model.DataModelSingleton;
import model.GridModel;
import model.TrafficsignConstructionSiteModel;
import model.TrafficsignModel;
import model.VehicleCoordinates;
import model.VehicleModel;

@SuppressWarnings("serial")
class PopUpMenuStreet extends JPopupMenu
{
	JMenuItem menuItem0, menuItem1, menuItem2;
	private static DataModelSingleton model=null;
	GridModel gridModel = DataModelSingleton.getInstance().getGridModel();
	TrafficsignConstructionSiteModel constructionSignModel = null;
	VehicleModel carModel = null;
	RandomComponents randomComponents = new RandomComponents();
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(PopUpMenuStreet.class.getName()));
	
    public PopUpMenuStreet(final Integer posX, final Integer posY, String tileType)
    {
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
    	
    	model=DataModelSingleton.getInstance();
    	menuItem0 = new JMenuItem("Action possible for " + tileType + " at " + posX + ", " + posY + ":");
        menuItem0.setFont(new Font("Tahoma", Font.BOLD, 10));
        add(menuItem0);
    	
    	menuItem1 = new JMenuItem("Create Construction Site");
        menuItem1.setFont(new Font("Tahoma", Font.PLAIN, 10));
        menuItem1.addActionListener(new ActionListener() 
        {
			public void actionPerformed(ActionEvent arg0) 
			{
				//create new construction sign at mouseclick
				constructionSignModel = new TrafficsignConstructionSiteModel(posX, posY, 2, 1);
				logger.info("New construction sign added at: " + constructionSignModel.getPosX() + ", " + constructionSignModel.getPosY());
				
				gridModel.addToTrafficSignList(((TrafficsignModel)constructionSignModel));	//add newly created object to GridModel List
				((GridView)gridModel.getView()).updateCreateOneTrafficSign(((BaseModel)constructionSignModel));
				
				model.getGridModel().getActualTrafficSignsArray().add(constructionSignModel);
			
			}
		});
        add(menuItem1);
        
        menuItem2 = new JMenuItem("Create Passenger Car");
        menuItem2.setFont(new Font("Tahoma", Font.PLAIN, 10));
        menuItem2.addActionListener(new ActionListener() 
        {
			public void actionPerformed(ActionEvent arg0) 
			{
				
				// just store position of vehicle
	    		logger.info("Action Performed: new Vehicle");
				VehicleCoordinates pos = new VehicleCoordinates(posX, posY);
				model.getControlPanel().sendToController(pos);
	
			}
        
		});
        add(menuItem2);
    }
}






		