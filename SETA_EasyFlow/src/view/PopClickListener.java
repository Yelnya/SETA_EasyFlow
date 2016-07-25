package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopClickListener extends MouseAdapter 
{
	//Attribute
	
	//default constructor
    public PopClickListener()	
    {
    }

    //if mouse is right-clicked...
    
	/**************************** METHODS *********************************/

    //call popup menu for street
    public void doPopStreet(MouseEvent e, String tileType, Integer posX, Integer posY)
    {
        PopUpMenuStreet menu = new PopUpMenuStreet(posX, posY, tileType);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    //call popup menu for crossing
    public void doPopCrossing(MouseEvent e, String tileType, Integer posX, Integer posY)
    {
        PopUpMenuCrossing menu = new PopUpMenuCrossing(posX, posY, tileType);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    //call popup menu for deleteConstructionSign
    public void doPopDeleteConstructionSign(MouseEvent e, String tileType, Integer posX, Integer posY)
    {
        PopUpMenuDeleteConstructionSign menu = new PopUpMenuDeleteConstructionSign(posX, posY, tileType);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}