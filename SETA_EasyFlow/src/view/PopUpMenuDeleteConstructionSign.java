package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import model.DataModelSingleton;
import model.GridModel;
import model.TrafficsignConstructionSiteModel;
import model.TrafficsignModel;

@SuppressWarnings("serial")
class PopUpMenuDeleteConstructionSign extends JPopupMenu
{
	JMenuItem menuItem0;
	JMenuItem menuItem1;
	GridModel gridModel = DataModelSingleton.getInstance().getGridModel();
	private static DataModelSingleton model=null;
	TrafficsignModel constructionSignModel = null;
    
    public PopUpMenuDeleteConstructionSign(final Integer posX, final Integer posY, String tileType)
    {
    	model=DataModelSingleton.getInstance();
    	menuItem0 = new JMenuItem("Action possible for " + tileType + " at " + posX + ", " + posY + ":");
        menuItem0.setFont(new Font("Tahoma", Font.BOLD, 10));
        add(menuItem0);
    	
    	menuItem1 = new JMenuItem("Delete Construction Site");
        menuItem1.setFont(new Font("Tahoma", Font.PLAIN, 10));
        menuItem1.addActionListener(new ActionListener() 
        {
			public void actionPerformed(ActionEvent arg0) 
			{
				//delete existing construction sign at mouseclick
				for (int i = 0; i < model.getGridModel().getActualTrafficSignsArray().size(); i++)
				{
					if (model.getGridModel().getActualTrafficSignsArray().get(i).getPosX().equals(posX) && model.getGridModel().getActualTrafficSignsArray().get(i).getPosY().equals(posY))
					{
						constructionSignModel = model.getGridModel().getActualTrafficSignsArray().get(i);
						((TrafficSignView) constructionSignModel.getView()).delete();
						//object will be deleted from ArrayList<BaseModel> models
						gridModel.deleteFromTrafficSignList(constructionSignModel);
						((TrafficsignConstructionSiteModel)constructionSignModel).setExists(false);
					}
				}
			}
		});
        add(menuItem1);
    }
}