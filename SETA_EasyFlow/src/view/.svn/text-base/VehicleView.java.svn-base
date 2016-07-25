package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import util.LogUtility;
import model.BusModel;
import model.PassengerCarModel;
import model.TruckModel;
import model.VehicleModel;


public class VehicleView extends BaseView
{
	//ATTRIBUTES
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(VehicleView.class.getName()));
	
	//CONSTRUCTOR
	public VehicleView(GridView gridView, VehicleModel vehicleModel)
	{
		super(gridView, vehicleModel);
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		drawOneVehicle(vehicleModel);
		
		
	} //End of Constructor
	
	
	//METHODS


	/****************************** DRAW SPRITES  *******************************/

	//METHOD: draw one vehicle determined by its type
	public synchronized void drawOneVehicle(VehicleModel vehicleModel)
	{
		GridBagConstraints gbc_lblname = new GridBagConstraints();
		
		//if it is a car	
		if (vehicleModel instanceof PassengerCarModel)
		{
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 1;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 1;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			gbc_lblname.gridx = vehicleModel.getPosX();					//where to place the bitmap X
			gbc_lblname.gridy = vehicleModel.getPosY();					//where to place the bitmap	Y
			GridView.getPanel().add(vehicleModel.getBitmap().getBitmapLabel(), gbc_lblname, vehicleModel.getLayer());		//car is placed in layer 3 (layer 0 is top)
			GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();	//NECESSARY! -> validate and repaint of GUI
		}
		//if it is a bus	
		if (vehicleModel instanceof BusModel)
		{
			//depending on direction the bitmap is placed individually
			if (vehicleModel.getDirection().equals(1))
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(2))
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(3))
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(4))
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(5))
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap	Y
			}
			else if (vehicleModel.getDirection().equals(6))
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(7))
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			else if (vehicleModel.getDirection().equals(8))
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 2;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 2;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			GridView.getPanel().add(vehicleModel.getBitmap().getBitmapLabel(), gbc_lblname, vehicleModel.getLayer());		//bus is placed in layer 3 (layer 0 is top)
			GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();	//NECESSARY! -> validate and repaint of GUI
		}
		//if it is a truck	
		if (vehicleModel instanceof TruckModel)
		{
			//depending on direction the bitmap is placed individually
			//only entry tiles
			if ((vehicleModel.getDirection().equals(1) && ((TruckModel)vehicleModel).getOldDirection().equals(0)) ||
					(vehicleModel.getDirection().equals(3) && ((TruckModel)vehicleModel).getOldDirection().equals(0)) ||
					(vehicleModel.getDirection().equals(5) && ((TruckModel)vehicleModel).getOldDirection().equals(0)) ||
					(vehicleModel.getDirection().equals(7) && ((TruckModel)vehicleModel).getOldDirection().equals(0)))
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			//all other tiles
			else if ((vehicleModel.getDirection().equals(1) && ((TruckModel)vehicleModel).getOldDirection().equals(1)) ||
					(vehicleModel.getDirection().equals(1) && ((TruckModel)vehicleModel).getOldDirection().equals(2)) ||
					(vehicleModel.getDirection().equals(2) && ((TruckModel)vehicleModel).getOldDirection().equals(2)) ||
					(vehicleModel.getDirection().equals(2) && ((TruckModel)vehicleModel).getOldDirection().equals(4)) ||
					(vehicleModel.getDirection().equals(2) && ((TruckModel)vehicleModel).getOldDirection().equals(8)) ||
					(vehicleModel.getDirection().equals(3) && ((TruckModel)vehicleModel).getOldDirection().equals(2)) ||
					(vehicleModel.getDirection().equals(4) && ((TruckModel)vehicleModel).getOldDirection().equals(3)) ||
					(vehicleModel.getDirection().equals(8) && ((TruckModel)vehicleModel).getOldDirection().equals(1)) )
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			else if ((vehicleModel.getDirection().equals(1) && ((TruckModel)vehicleModel).getOldDirection().equals(8)) ||
						(vehicleModel.getDirection().equals(2) && ((TruckModel)vehicleModel).getOldDirection().equals(1)) ||
						(vehicleModel.getDirection().equals(6) && ((TruckModel)vehicleModel).getOldDirection().equals(7)) ||
						(vehicleModel.getDirection().equals(7) && ((TruckModel)vehicleModel).getOldDirection().equals(7)) ||
						(vehicleModel.getDirection().equals(7) && ((TruckModel)vehicleModel).getOldDirection().equals(8)) ||
						(vehicleModel.getDirection().equals(8) && ((TruckModel)vehicleModel).getOldDirection().equals(2)) ||
						(vehicleModel.getDirection().equals(8) && ((TruckModel)vehicleModel).getOldDirection().equals(6)) ||
						(vehicleModel.getDirection().equals(8) && ((TruckModel)vehicleModel).getOldDirection().equals(8)) )
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY();				//where to place the bitmap Y
			}
			else if ((vehicleModel.getDirection().equals(2) && ((TruckModel)vehicleModel).getOldDirection().equals(3)) ||
						(vehicleModel.getDirection().equals(3) && ((TruckModel)vehicleModel).getOldDirection().equals(3)) ||
						(vehicleModel.getDirection().equals(3) && ((TruckModel)vehicleModel).getOldDirection().equals(4)) ||
						(vehicleModel.getDirection().equals(4) && ((TruckModel)vehicleModel).getOldDirection().equals(2)) ||
						(vehicleModel.getDirection().equals(4) && ((TruckModel)vehicleModel).getOldDirection().equals(4)) ||
						(vehicleModel.getDirection().equals(4) && ((TruckModel)vehicleModel).getOldDirection().equals(6)) ||
						(vehicleModel.getDirection().equals(5) && ((TruckModel)vehicleModel).getOldDirection().equals(4)) ||
						(vehicleModel.getDirection().equals(6) && ((TruckModel)vehicleModel).getOldDirection().equals(5)) )
			{
				gbc_lblname.gridx = vehicleModel.getPosX()-1;			//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap Y
			}
			else if ((vehicleModel.getDirection().equals(4) && ((TruckModel)vehicleModel).getOldDirection().equals(5)) ||
					(vehicleModel.getDirection().equals(5) && ((TruckModel)vehicleModel).getOldDirection().equals(5)) ||
					(vehicleModel.getDirection().equals(5) && ((TruckModel)vehicleModel).getOldDirection().equals(6)) ||
					(vehicleModel.getDirection().equals(6) && ((TruckModel)vehicleModel).getOldDirection().equals(4)) ||
					(vehicleModel.getDirection().equals(6) && ((TruckModel)vehicleModel).getOldDirection().equals(6)) ||
					(vehicleModel.getDirection().equals(6) && ((TruckModel)vehicleModel).getOldDirection().equals(8)) ||
					(vehicleModel.getDirection().equals(7) && ((TruckModel)vehicleModel).getOldDirection().equals(6)) ||
					(vehicleModel.getDirection().equals(8) && ((TruckModel)vehicleModel).getOldDirection().equals(7))  )
			{
				gbc_lblname.gridx = vehicleModel.getPosX();				//where to place the bitmap X
				gbc_lblname.gridy = vehicleModel.getPosY()-1;			//where to place the bitmap Y
			}
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 2;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 2;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			GridView.getPanel().add(vehicleModel.getBitmap().getBitmapLabel(), gbc_lblname, vehicleModel.getLayer());		//truck is placed in layer 3 (layer 0 is top)
			GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();				//NECESSARY! -> validate and repaint of GUI
		}
	}

	//METHOD: delete view of vehicle
	public void delete() 
	{
		logger.info("vehicleview delete");
		JLabel bitmapLabel=((VehicleModel)getModelInstance()).getBitmap().getBitmapLabel();
		// vehicle view is deleted, vehicle disappears from GUI
		super.delete(); // framework function: deregister from model and delete from parent view
		GridView.getPanel().remove(bitmapLabel);
	}
	
	//METHOD: delete Label of object
	public void deleteLabel() 
	{
		JLabel bitmapLabel=((VehicleModel)getModelInstance()).getBitmap().getBitmapLabel();
		GridView.getPanel().remove(bitmapLabel);
	}

	
	//METHOD: update of the vehicle view due to movement
	public void updateMove() 
	{		
		drawOneVehicle((VehicleModel)getModelInstance());
	}

}


