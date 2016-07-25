package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import model.BaseModel;
import model.GridModel;
import model.TrafficLightModel;
import model.TrafficsignModel;
import model.VehicleModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;








//import threads.BusThread;
//import threads.PassengerCarThread;
//import threads.TestAutoThread;
//import threads.TestAutoThread2;
//import threads.TruckThread;
import util.MouseComponent;

public class GridView extends BaseView {
	// ATTRIBUTES
	private JFrame frame = null;
	private static JPanel panel = null;
	private ControlPanel controlPanel = null;
	Component mouseClick = new MouseComponent();

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	
/***************************************************************************/
/**					APPLICATION LAUNCHED, FRAME CREATED                    */
/***************************************************************************/

	public GridView(GridModel gridModel) {
		super(null, gridModel);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.setContentPane(panel);

		GridBagLayout gbl_panel = new GridBagLayout();

		gbl_panel.columnWidths = new int[] { 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16 }; // width 64 (48 + 16)
		gbl_panel.rowHeights = new int[] { 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
				16, 16, 16, 16, 16 }; // height 45 units
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE }; // weights 64 (48 + 16)
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE }; // weights 45 
		panel.setLayout(gbl_panel);

		GridAxis.drawAxisNames(); 		// draw axis names
		Draw.drawBackgroundPicture(); 	// draw background picture
		panel.addMouseListener((MouseListener) mouseClick); // for Class
															// MouseComponent
															// rightClick Event
	}

	
	/**********************************************************************/
	/**                            METHODS                                */
	/**********************************************************************/
	// method init
	public void init() { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					frame.setTitle("EasyFlow");
					frame.setSize(1030, 764); // position of window
					frame.setResizable(false);
					frame.setLocation(
							dim.width / 2 - frame.getSize().width / 2,
							dim.height / 2 - frame.getSize().height / 2); // frame
																			// position
																			// centered
					controlPanel = new ControlPanel();
					frame.setVisible(true);
					

				} catch (Exception e) {
					System.out.println("ENTERING EXCEPTION GRIDVIEW INIT()");
					e.printStackTrace();
				}
			}
		});

	} // end of method init

	
	// MVC Pattern Method
	public void updateCreate(ArrayList<BaseModel> models)													// !!!
	{
		try {
			for (BaseModel modelObject : models) {
				if (modelObject instanceof VehicleModel) {
					@SuppressWarnings("unused")
					VehicleView vehicleView = new VehicleView(this,
							(VehicleModel) modelObject);
				} else if (modelObject instanceof TrafficsignModel) {
					@SuppressWarnings("unused")
					TrafficSignView trafficsignView = new TrafficSignView(this,
							(TrafficsignModel) modelObject);
				} else if (modelObject instanceof TrafficLightModel) {
					@SuppressWarnings("unused")
					TrafficLightView trafficLightView = new TrafficLightView(
							this, (TrafficLightModel) modelObject);
				}
				System.out.println(modelObject);

			}
		} catch (Exception e) {
			System.out.println("GRIDVIEW.updateCreate()");
			e.printStackTrace();
		}

	} // end of updateCreate

	//METHOD: create views for vehicles from handed arrayList
	public void updateCreateVehicles(ArrayList<VehicleModel> getVehicleModels) // MVC Pattern Method
	{
		try {
			for (VehicleModel vehicle : getVehicleModels) {
				@SuppressWarnings("unused")
				VehicleView vehicleView = new VehicleView(this,(VehicleModel) vehicle);

			}
		} catch (Exception e) {
			System.out.println("GRIDVIEW.updateCreateVehicles()");
			e.printStackTrace();
		}

	} // updateCreate
	
	//METHOD: Create views for trafficsigns handed over by arraylist
	@SuppressWarnings("unused")
	public void updateCreateTrafficSigns(ArrayList<TrafficsignModel> getTrafficSignModels) // MVC Pattern Method
	{
		try {
			for (TrafficsignModel trafficSign : getTrafficSignModels) {
				TrafficSignView vehicleView = new TrafficSignView(this,(TrafficsignModel) trafficSign);

			}
		} catch (Exception e) {
			System.out.println("GRIDVIEW.updateCreateTrafficSigns()");
			e.printStackTrace();
		}

	} // updateCreate
	
	//METHOD: create views for trafficlights from handed arraylist
	@SuppressWarnings("unused")
	public void updateCreateTrafficLights(ArrayList<TrafficLightModel> getTrafficLightModels) // MVC Pattern Method
	{
		try {
			for (TrafficLightModel trafficLight : getTrafficLightModels) {
				TrafficLightView vehicleView = new TrafficLightView(this,(TrafficLightModel) trafficLight);

			}
		} catch (Exception e) {
			System.out.println("GRIDVIEW.updateCreateTrafficLights()");
			e.printStackTrace();
		}

	} // updateCreate

	//create one single traffic sign and draw view - e.g. when manually placing a constructino sign
	@SuppressWarnings("unused")
	public void updateCreateOneTrafficSign(BaseModel modelObject) 
	{
		if (modelObject instanceof TrafficsignModel) 
		{
			TrafficSignView trafficsignView = new TrafficSignView(this,
					(TrafficsignModel) modelObject);
		}
	}

	//GETTERS AND SETTERS
	public static JPanel getPanel() {
		return panel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	void update(ArrayList<BaseModel> models) {
	}


	//find one specific vehicle in arraylist and delete object and view
	public void updateDeleteVehicle(ArrayList<VehicleModel> models) {

		try {
			for (VehicleModel modelObject : models) {
				VehicleView view;
				view = (VehicleView) modelObject.getView();
				view.delete();
			}

			getPanel().revalidate();
			getPanel().updateUI();
			getPanel().validate();
		} catch (Exception e) {
			System.out
					.println("entering GRIDVIEW.updateDeleteVehicle(ArrayList<BaseModel> models)");
			e.printStackTrace();
		}
	} // end updateDelete()

	
	//redraw positions of vehicles from arraylist
	public void updateMove(ArrayList<VehicleModel> vehiclemodels) {
		try {
			Iterator<VehicleModel> iter = vehiclemodels.iterator();
			while (iter.hasNext()) // as long as there are items in the list
			{
				VehicleModel modelObject = iter.next(); // sets pointer to next
														// element
				if (modelObject instanceof VehicleModel) {
					((VehicleView) modelObject.getView()).updateMove();
				} else {
				}
				getPanel().revalidate();
				getPanel().updateUI();
				getPanel().validate();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

	}

	//redraw stance of traffic lights from arraylist
	public void updateTrafficLights(ArrayList<TrafficLightModel> trafficLights) {

		try {
			Iterator<TrafficLightModel> iter = trafficLights.iterator();
			while (iter.hasNext()) // as long as there are items in the list
			{
				BaseModel modelObject = iter.next(); // sets pointer to next
														// element

				if (modelObject instanceof TrafficLightModel) {
					((TrafficLightView) modelObject.getView()).updateChange();
				}
				getPanel().revalidate();
				getPanel().updateUI();
				getPanel().validate();
			}
		} catch (Exception e) {
//			System.out.println("ENTERING EXCEPTION GRIDVIEW UPDATETRAFFICLIGHTS()");
//			e.printStackTrace();
		}
	}

	//redraw trafficsigns from arraylists
	public void updateMoveObjects(ArrayList<BaseModel> models) {

		try {
			Iterator<BaseModel> iter = models.iterator();
			while (iter.hasNext()) // as long as there are items in the list
			{
				BaseModel modelObject = iter.next(); // sets pointer to next
														// element
				if (modelObject instanceof TrafficsignModel) {
					((TrafficSignView) modelObject.getView()).updateChange();
				}
				else if (modelObject instanceof TrafficLightModel) {
					((TrafficLightView) modelObject.getView()).updateChange();
				} else {
					//do nothing
				}
				getPanel().revalidate();
				getPanel().updateUI();
				getPanel().validate();
			}
		} catch (Exception e) {
			// System.out.println("ENTERING EXCEPTION GRIDVIEW UPDATEMOVE TRAFFIC SIGNS & TRAFFICLIGHTS()");
			// e.printStackTrace();
		}

	}

}
