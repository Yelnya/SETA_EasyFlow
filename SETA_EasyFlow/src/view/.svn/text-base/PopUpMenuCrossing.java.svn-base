package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.DataModelSingleton;
import model.TrafficLightModel;

@SuppressWarnings("serial")
class PopUpMenuCrossing extends JPopupMenu
{
    JMenuItem menuItem0;
	JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
	private static DataModelSingleton model=null;
    
    public PopUpMenuCrossing(final Integer posX, final Integer posY, String tileType)
    {
		model=DataModelSingleton.getInstance();
    	menuItem0 = new JMenuItem("Possible action at  " + tileType + ", " + posX + ", " + posY + ":");
    	menuItem0.setFont(new Font("Tahoma", Font.BOLD, 10));
    	add(menuItem0);
    	
    	//changes label trafficlight from green to red and other way round
    	menuItem1 = new JMenuItem("Switch TrafficLight");
    	menuItem1.setFont(new Font("Tahoma", Font.PLAIN, 10));
    	menuItem1.addActionListener(new ActionListener()
    	{
			TrafficLightModel trafficLightModel = null;
    		public void actionPerformed(ActionEvent arg0) 
			{
				//find out which trafficlight has been clicked -> parameter for method switchTrafficLights
    			for (int i = 0; i < model.getGridModel().getActualTrafficLightsArray().size(); i++)
    	    	{
    				if (model.getGridModel().getActualTrafficLightsArray().get(i).getGridposX().equals(posX) &&
    	    				model.getGridModel().getActualTrafficLightsArray().get(i).getGridposY().equals(posY))
    				{
    					trafficLightModel = model.getGridModel().getActualTrafficLightsArray().get(i);
    				}
    	    	}
    			//TrafficLights will be switched			
    			trafficLightModel.switchTrafficLights(trafficLightModel);
			}
		});
    	add(menuItem1);
    }
}