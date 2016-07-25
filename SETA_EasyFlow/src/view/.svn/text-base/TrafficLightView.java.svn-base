package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import util.Bitmap;
import model.TrafficLightModel;


public class TrafficLightView extends BaseView
{
	//ATTRIBUTES
	GridBagConstraints gbc_lblname;

	//CONSTRUCTOR
	public TrafficLightView(GridView gridView, TrafficLightModel trafficlightModel)
	{
		super(gridView, trafficlightModel);
		gbc_lblname = new GridBagConstraints();
		drawOneTrafficLight(gbc_lblname, trafficlightModel);
	} //End of Constructor
	
	
	//GETTERS AND SETTERS
	public GridBagConstraints getGbc_lblname() {
		return gbc_lblname;
	}
	public void setGbc_lblname(GridBagConstraints gbc_lblname) {
		this.gbc_lblname = gbc_lblname;
	}
	
	
	//METHOD: one trafficlight draw
	public synchronized void drawOneTrafficLight(GridBagConstraints gbc_lblname, TrafficLightModel trafficlightModel)
	{
		gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			
		gbc_lblname.gridwidth = 1;									//width
		gbc_lblname.gridheight = 1;									//height
		gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
		gbc_lblname.gridx = trafficlightModel.getGridposX();		//placing x coord
		gbc_lblname.gridy = trafficlightModel.getGridposY();		//placing y coord			
		GridView.getPanel().add(trafficlightModel.getTrafficLightBitmap().getBitmapLabel(), gbc_lblname, trafficlightModel.getLayer());		//layer 2
		GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();
	}
	
	//METHOD: traffic jam draw optic alarm
	public synchronized void drawtrafficJam(TrafficLightModel trafficLight)
	{
		GridBagConstraints gbc_lblname = new GridBagConstraints();
		
		//if direction is N	
		if (trafficLight.getTrafficLightDirection().equals(1))
		{
			trafficLight.setTrafficJamBitmap(new Bitmap(new String("src/_resources/tiles/trafficJam/trafficJamSN.png")));
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 1;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 5;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			gbc_lblname.gridx = trafficLight.getGridposX();				//where to place the bitmap X
			gbc_lblname.gridy = trafficLight.getGridposY()-5;			//where to place the bitmap	Y
			GridView.getPanel().add(trafficLight.getTrafficJamBitmap().getBitmapLabel(), gbc_lblname, 2);		//placed in layer 4 (under vehicles)
		}
		//if direction is E	
		else if (trafficLight.getTrafficLightDirection().equals(3))
		{
			trafficLight.setTrafficJamBitmap(new Bitmap(new String("src/_resources/tiles/trafficJam/trafficJamWE.png")));
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 5;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 1;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			gbc_lblname.gridx = trafficLight.getGridposX()+1;			//where to place the bitmap X
			gbc_lblname.gridy = trafficLight.getGridposY();				//where to place the bitmap	Y
			GridView.getPanel().add(trafficLight.getTrafficJamBitmap().getBitmapLabel(), gbc_lblname, 2);		//placed in layer 4 (under vehicles)
		}
		//if direction is S	
		else if (trafficLight.getTrafficLightDirection().equals(5))
		{
			trafficLight.setTrafficJamBitmap(new Bitmap(new String("src/_resources/tiles/trafficJam/trafficJamSN.png")));
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 1;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 5;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			gbc_lblname.gridx = trafficLight.getGridposX();				//where to place the bitmap X
			gbc_lblname.gridy = trafficLight.getGridposY()+1;			//where to place the bitmap	Y
			GridView.getPanel().add(trafficLight.getTrafficJamBitmap().getBitmapLabel(), gbc_lblname, 2);		//placed in layer 4 (under vehicles)
		}
		//if direction is W	
		else if (trafficLight.getTrafficLightDirection().equals(7))
		{
			trafficLight.setTrafficJamBitmap(new Bitmap(new String("src/_resources/tiles/trafficJam/trafficJamWE.png")));
			gbc_lblname.anchor = GridBagConstraints.NORTHWEST;			//anchor of label
			gbc_lblname.gridwidth = 5;									//how much space the bitmap needs (grid cells) - width
			gbc_lblname.gridheight = 1;									//how much space the bitmap needs (grid cells) - height
			gbc_lblname.insets = new Insets(0, 0, 0, 0); 				//padding
			gbc_lblname.gridx = trafficLight.getGridposX()-5;			//where to place the bitmap X
			gbc_lblname.gridy = trafficLight.getGridposY();				//where to place the bitmap	Y
			GridView.getPanel().add(trafficLight.getTrafficJamBitmap().getBitmapLabel(), gbc_lblname, 2);		//placed in layer 4 (under vehicles)
		}
	}
	
	//METHOD: delete Bitmap before change
	public void delete() 
	{
		JLabel bitmapLabel=((TrafficLightModel)getModelInstance()).getTrafficLightBitmap().getBitmapLabel();
		// trafficlight view is deleted, trafficlight disappears from GUI
		super.delete(); // framework function: deregister from model and delete from parent view
		// delete trafficlight view from gui
		GridView.getPanel().remove(bitmapLabel);		
		GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();
	}
	
	//METHOD: delete only label from trafficlight
	public void deleteLabel() 
	{
		JLabel bitmapLabel=((TrafficLightModel)getModelInstance()).getTrafficLightBitmap().getBitmapLabel();
		GridView.getPanel().remove(bitmapLabel);
	}
	
	//METHOD: delete only label from trafficjam
	public void deleteLabelTrafficJam() 
	{
		JLabel bitmapLabel=((TrafficLightModel)getModelInstance()).getTrafficJamBitmap().getBitmapLabel();
		GridView.getPanel().remove(bitmapLabel);
	}
	
	//METHOD: delete only label from trafficlight manual change
	public void updateChange() {
		// update of the trafficlight view due to manually or interval change		
		GridView.getPanel().remove(((TrafficLightModel)getModelInstance()).getTrafficLightBitmap().getBitmapLabel());
		
		drawOneTrafficLight(gbc_lblname, (TrafficLightModel)getModelInstance());
	}
	

}
