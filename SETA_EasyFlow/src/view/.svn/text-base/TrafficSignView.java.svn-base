package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import model.TrafficsignModel;

public class TrafficSignView extends BaseView
{
	//ATTRIBUTES
	GridBagConstraints gbc_lblname;

	//CONSTRUCTOR
	public TrafficSignView(GridView gridView, TrafficsignModel trafficsignModel)
	{
		super(gridView, trafficsignModel);
		gbc_lblname = new GridBagConstraints();
		drawOneTrafficSign(gbc_lblname, trafficsignModel);
	} //End of Constructor
	
	
	//GETTERS AND SETTERS
	public GridBagConstraints getGbc_lblname() {
		return gbc_lblname;
	}
	public void setGbc_lblname(GridBagConstraints gbc_lblname) {
		this.gbc_lblname = gbc_lblname;
	}
	
	
	//METHOD: one vehicle draw
	public synchronized void drawOneTrafficSign(GridBagConstraints gbc_lblname, TrafficsignModel trafficsignModel)
	{
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			
			gbc_lblname.gridwidth = 1;									
			gbc_lblname.gridheight = 1;									
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				
			gbc_lblname.gridx = trafficsignModel.getPosX();				
			gbc_lblname.gridy = trafficsignModel.getPosY();				
			GridView.getPanel().add(trafficsignModel.getBitmap().getBitmapLabel(), gbc_lblname, trafficsignModel.getLayer());		
			GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();	
	}

	
	//METHOD: delete Bitmap before change
	public void delete() 
	{
		JLabel bitmapLabel= ((TrafficsignModel)getModelInstance()).getBitmap().getBitmapLabel();
		// trafficsign view is deleted, trafficsign disappears from GUI
		super.delete(); // framework function: deregister from model and delete from parent view
		// delete trafficsign view from gui
		GridView.getPanel().remove(bitmapLabel);		
		GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();
	}

	
	//METHOD: update of the trafficlight view due to manually or interval change	
	public void updateChange() 
	{	
		GridView.getPanel().remove(((TrafficsignModel)getModelInstance()).getBitmap().getBitmapLabel());
		drawOneTrafficSign(gbc_lblname, (TrafficsignModel)getModelInstance());
	}
}
