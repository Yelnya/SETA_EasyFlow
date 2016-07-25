package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.LogUtility;
import util.RandomComponents;
import util.ValidateInput;
import model.ControlPanelSettings;
import model.DataModelSingleton;
import model.GridModel;
import model.VehicleCoordinates;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//is Observable for Controller - everything changing in ControlPanel will be reported to Controller
public class ControlPanel extends Observable
{
	@SuppressWarnings("unused")
	private RandomComponents randomComponents = new RandomComponents();

	
	Font font = new Font("Courier", Font.PLAIN,11);
	JLabel lblSpeed, lblSeparator, lblSimulationStartStop, lblNumberVehiclesInSimulation1, lblNumberVehiclesInSimulation2, lblNumberPassengerCars, lblNumberTrucks, lblPlaceCar, lblPlaceCar2;
	JLabel lblVehiclesInSimulation1, lblVehiclesInSimulation2, lblVehiclesInSimulationCars, lblVehiclesInSimulationCars2, lblVehiclesInSimulationBusses, lblVehiclesInSimulationBusses2, lblVehiclesInSimulationTrucks, lblVehiclesInSimulationTrucks2;
	JLabel lblTrafficJamDetection1, lblTrafficJamDetection2, lblTrafficJamDetected;
	JSlider sliderSpeed;
	GridBagConstraints gbc_lblSeparator;
	JButton btnSimulationStartStop, btnTestPlaceCarManually, btnExit;
	String textButtonStartStop = "Stop", defaultTextForNumberPassengerCarsInSimulation = "0 - 30", defaultTextForNumberTrucksInSimulation = "0 - 10";
    boolean done = false;														//for methods setDone() and waitUntilDone()
    ControlPanelSettings controlPanelSettings=null;
    private static JTextField txtNumberPassengerCarsInSimulation, txtNumberTrucksInSimulation;
    Integer numberOfPassengerCarsChosen = null, numberOfTrucksChosen = null;	//UserInput -> User wishes to alter the number of Vehicles in Simulation
    Integer xCoord = null, yCoord = null;	
	DataModelSingleton model = DataModelSingleton.getInstance();
	GridModel gridModel = DataModelSingleton.getInstance().getGridModel();
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(ControlPanel.class.getName()));    
    
	//Constructor
	@SuppressWarnings("static-access")
	public ControlPanel()
	{
	
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
		
		//Add new Observers -> Controller Instance
		this.addObserver(DataModelSingleton.getInstance().getController());		//Listener is instance of Controller Class - what is reported can be found in tell()
	
		controlPanelSettings = new ControlPanelSettings();
		
		//LABEL for SPEED
		lblSpeed = new JLabel("Speed Slider", SwingConstants.CENTER);
		lblSpeed.setForeground(Color.DARK_GRAY);
		lblSpeed.setFont(font);
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.gridwidth = 7;
		gbc_lblSpeed.gridheight = 2;
		gbc_lblSpeed.anchor = GridBagConstraints.WEST;
		gbc_lblSpeed.gridx = 53;
		gbc_lblSpeed.gridy = 4;
		gbc_lblSpeed.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSpeed, gbc_lblSpeed, 0);
		
		
		//SLIDER for SPEED
		sliderSpeed = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);					//(direction, min, max, current)
		sliderSpeed.setMinorTickSpacing(1);
		sliderSpeed.setPaintTicks(true);
		sliderSpeed.setSnapToTicks(true);
		sliderSpeed.setOpaque(false);
		GridBagConstraints gbc_sliderSpeed = new GridBagConstraints();
		gbc_sliderSpeed.gridwidth = 7;
		gbc_sliderSpeed.gridheight = 3;
		gbc_sliderSpeed.anchor = GridBagConstraints.WEST;
		gbc_sliderSpeed.gridx = 53;
		gbc_sliderSpeed.gridy = 5;
		gbc_sliderSpeed.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(sliderSpeed, gbc_sliderSpeed, 0);
		//CHANGESTATE LISTENER FOR SLIDER - if Slider gets moved
		sliderSpeed.addChangeListener(new ChangeListener() 
		{
		    public void stateChanged(ChangeEvent e) 
		    {
		        JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) 
		        {
		    		//tell() reports to the Observer when Slider position has changed
		        	controlPanelSettings.setTimeModifier(source.getValue());
		        	tell(controlPanelSettings);									//slider value is handed over to tell()
		        }
		    }
		});
		
		
		//1. SEPARATOR LINE
		lblSeparator = new JLabel("----------------");
		lblSeparator.setForeground(Color.GRAY);
		lblSeparator.setFont(font);
		gbc_lblSeparator = new GridBagConstraints();
		gbc_lblSeparator.gridwidth = 7;
		gbc_lblSeparator.gridheight = 1;
		gbc_lblSeparator.anchor = GridBagConstraints.WEST;
		gbc_lblSeparator.gridx = 53;
		gbc_lblSeparator.gridy = 7;
		gbc_lblSeparator.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSeparator, gbc_lblSeparator, 0);
		
		
		//LABEL SIMULATION START / STOPP
		lblSimulationStartStop = new JLabel("Simulation:", SwingConstants.CENTER);
		lblSimulationStartStop.setForeground(Color.DARK_GRAY);
		lblSimulationStartStop.setFont(font);
		GridBagConstraints gbc_lblSimulationStartStopp = new GridBagConstraints();
		gbc_lblSimulationStartStopp.gridwidth = 7;
		gbc_lblSimulationStartStopp.gridheight = 1;
		gbc_lblSimulationStartStopp.anchor = GridBagConstraints.WEST;
		gbc_lblSimulationStartStopp.gridx = 53;
		gbc_lblSimulationStartStopp.gridy = 8;
		gbc_lblSimulationStartStopp.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSimulationStartStop, gbc_lblSimulationStartStopp, 0);
		
		
		//BUTTON "SIMULATION START / STOP"
		btnSimulationStartStop = new JButton(textButtonStartStop);
		btnSimulationStartStop.setForeground(Color.DARK_GRAY);
		btnSimulationStartStop.setFont(font);
		GridBagConstraints gbc_btnSimulationStartStop = new GridBagConstraints();
		gbc_btnSimulationStartStop.gridwidth = 7;
		gbc_btnSimulationStartStop.gridheight = 3;
		gbc_btnSimulationStartStop.anchor = GridBagConstraints.WEST;
		gbc_btnSimulationStartStop.gridx = 53;
		gbc_btnSimulationStartStop.gridy = 9;
		gbc_btnSimulationStartStop.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(btnSimulationStartStop, gbc_btnSimulationStartStop, 0);
		//ACTION LISTENER FOR BUTTON "SIMULATION START/STOP"
		btnSimulationStartStop.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(btnSimulationStartStop.getText().equals("Stop"))
				{
					btnSimulationStartStop.setText("Continue");
					//DataModel.getControllerThread().interrupt();
					controlPanelSettings.setSimulationActive(false); 
				}
				else
				{
					btnSimulationStartStop.setText("Stop");
					controlPanelSettings.setSimulationActive(true);
				}
				tell(controlPanelSettings);
			}
		});
		
		//2. SEPARATOR LINE
		lblSeparator = new JLabel("----------------");
		lblSeparator.setForeground(Color.GRAY);
		lblSeparator.setFont(font);
		GridBagConstraints gbc_lblSeparator = new GridBagConstraints();
		gbc_lblSeparator.gridwidth = 7;
		gbc_lblSeparator.gridheight = 1;
		gbc_lblSeparator.anchor = GridBagConstraints.WEST;
		gbc_lblSeparator.gridx = 53;
		gbc_lblSeparator.gridy = 12;
		gbc_lblSeparator.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSeparator, gbc_lblSeparator, 0);
		
		//LABEL 1. LINE: NUMBER CARS IN SIMULATION
		lblNumberVehiclesInSimulation1 = new JLabel("Number of Cars", SwingConstants.CENTER);
		lblNumberVehiclesInSimulation1.setForeground(Color.DARK_GRAY);
		lblNumberVehiclesInSimulation1.setFont(font);
		GridBagConstraints gbc_lblNumberVehiclesInSimulation1 = new GridBagConstraints();
		gbc_lblNumberVehiclesInSimulation1.gridwidth = 7;
		gbc_lblNumberVehiclesInSimulation1.gridheight = 1;
		gbc_lblNumberVehiclesInSimulation1.anchor = GridBagConstraints.WEST;
		gbc_lblNumberVehiclesInSimulation1.gridx = 53;
		gbc_lblNumberVehiclesInSimulation1.gridy = 14;
		gbc_lblNumberVehiclesInSimulation1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblNumberVehiclesInSimulation1, gbc_lblNumberVehiclesInSimulation1, 0);
		
		//LABEL 2. LINE: NUMBER VEHICLES IN SIMULATION
		lblNumberVehiclesInSimulation2 = new JLabel("in Simulation:", SwingConstants.CENTER);
		lblNumberVehiclesInSimulation2.setForeground(Color.DARK_GRAY);
		lblNumberVehiclesInSimulation2.setFont(font);
		GridBagConstraints gbc_lblNumberVehiclesInSimulation2 = new GridBagConstraints();
		gbc_lblNumberVehiclesInSimulation2.gridwidth = 7;
		gbc_lblNumberVehiclesInSimulation2.gridheight = 1;
		gbc_lblNumberVehiclesInSimulation2.anchor = GridBagConstraints.WEST;
		gbc_lblNumberVehiclesInSimulation2.gridx = 53;
		gbc_lblNumberVehiclesInSimulation2.gridy = 15;
		gbc_lblNumberVehiclesInSimulation2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblNumberVehiclesInSimulation2, gbc_lblNumberVehiclesInSimulation2, 0);
		
		
		//TEXTFIELD: NUMBER PASSENGERCARS IN SIMULATION PLUS FOCUS LISTENER PLUS ACTIONLISTENER
		txtNumberPassengerCarsInSimulation = new JTextField("0 - 30", SwingConstants.CENTER);
		txtNumberPassengerCarsInSimulation.setForeground(Color.DARK_GRAY);
		txtNumberPassengerCarsInSimulation.setFont(font);
		GridBagConstraints gbc_lblNumberCarsInSimulation1 = new GridBagConstraints();
		gbc_lblNumberCarsInSimulation1.gridwidth = 3;
		gbc_lblNumberCarsInSimulation1.gridheight = 2;
		gbc_lblNumberCarsInSimulation1.anchor = GridBagConstraints.WEST;
		gbc_lblNumberCarsInSimulation1.gridx = 53;
		gbc_lblNumberCarsInSimulation1.gridy = 16;
		gbc_lblNumberCarsInSimulation1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(txtNumberPassengerCarsInSimulation, gbc_lblNumberCarsInSimulation1, 0);
		//Focus Listener: click -> clear TextField -> unclick -> setTextToOriginal
		txtNumberPassengerCarsInSimulation.addFocusListener(new FocusListener() 
		{
	        @Override
	        public void focusGained(FocusEvent e) 
	        {
	            txtNumberPassengerCarsInSimulation.setText("");
	            txtNumberPassengerCarsInSimulation.setForeground(Color.BLACK);
	        }
	        @Override
	        public void focusLost(FocusEvent e) 
	        {
	            txtNumberPassengerCarsInSimulation.setText(defaultTextForNumberPassengerCarsInSimulation);
	        }
	    });
		//Action Listener: compare inputNumber with valid range
		txtNumberPassengerCarsInSimulation.addActionListener(new ActionListener() 
				{
					String inputText = "";
					public void actionPerformed(ActionEvent e) 
					{
						inputText = txtNumberPassengerCarsInSimulation.getText();
						if (ValidateInput.validateMaxVehicleNumber(inputText) && Integer.parseInt(inputText) >= 0 && Integer.parseInt(inputText) <= 30)	//inputNumber must be in valid range
						{
							defaultTextForNumberPassengerCarsInSimulation = inputText;
							logger.info("number ok");
							txtNumberPassengerCarsInSimulation.setText(inputText);
							setNumberOfPassengerCarsChosen(Integer.parseInt(inputText)); 
				    		
							//tell() reports to the Observer when Slider position has changed
				        	controlPanelSettings.setNumberOfPassengerCars(getNumberOfPassengerCarsChosen());
				        	tell(controlPanelSettings);									//slider value is handed over to tell()
							
						
						}
						else
						{
							txtNumberPassengerCarsInSimulation.setText("0 - 30");
							txtNumberPassengerCarsInSimulation.setForeground(Color.RED);
						}
						
					}
				});
		
		//LABEL: NUMBER PASSENGERCARS
		lblNumberPassengerCars = new JLabel("->   Cars", SwingConstants.CENTER);
		lblNumberPassengerCars.setForeground(Color.DARK_GRAY);
		lblNumberPassengerCars.setFont(font);
		GridBagConstraints gbc_lblNumberPassengerCars = new GridBagConstraints();
		gbc_lblNumberPassengerCars.gridwidth = 4;
		gbc_lblNumberPassengerCars.gridheight = 2;
		gbc_lblNumberPassengerCars.anchor = GridBagConstraints.WEST;
		gbc_lblNumberPassengerCars.gridx = 56;
		gbc_lblNumberPassengerCars.gridy = 16;
		gbc_lblNumberPassengerCars.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblNumberPassengerCars, gbc_lblNumberPassengerCars, 0);
		
		//TEXTFIELD: NUMBER TRUCKS IN SIMULATION PLUS FOCUSLISTENER PLUS ACTIONLISTENER
		txtNumberTrucksInSimulation = new JTextField("0 - 10", SwingConstants.CENTER);
		txtNumberTrucksInSimulation.setForeground(Color.DARK_GRAY);
		txtNumberTrucksInSimulation.setFont(font);
		GridBagConstraints gbc_txtNumberTrucksInSimulation = new GridBagConstraints();
		gbc_txtNumberTrucksInSimulation.gridwidth = 3;
		gbc_txtNumberTrucksInSimulation.gridheight = 2;
		gbc_txtNumberTrucksInSimulation.anchor = GridBagConstraints.WEST;
		gbc_txtNumberTrucksInSimulation.gridx = 53;
		gbc_txtNumberTrucksInSimulation.gridy = 18;
		gbc_txtNumberTrucksInSimulation.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(txtNumberTrucksInSimulation, gbc_txtNumberTrucksInSimulation, 0);
		//Focus Listener: click -> clear TextField -> unclick -> setTextToOriginal
		txtNumberTrucksInSimulation.addFocusListener(new FocusListener() 
		{
			@Override
	        public void focusGained(FocusEvent e) 
	        {
	            txtNumberTrucksInSimulation.setText("");
	            txtNumberTrucksInSimulation.setForeground(Color.BLACK);
	        }
	        @Override
	        public void focusLost(FocusEvent e) 
	        {
	            txtNumberTrucksInSimulation.setText(defaultTextForNumberTrucksInSimulation);
	        }
	    });
		//Action Listener: compare inputNumber with valid range
		txtNumberTrucksInSimulation.addActionListener(new ActionListener() 
		{
			String inputText = "";
			public void actionPerformed(ActionEvent e) 
			{
				inputText = txtNumberTrucksInSimulation.getText();
				if (ValidateInput.validateMaxVehicleNumber(inputText) && Integer.parseInt(inputText) >= 0 && Integer.parseInt(inputText) <= 10)	//input number must be in valid range
				{
					defaultTextForNumberTrucksInSimulation = inputText;
					txtNumberTrucksInSimulation.setText(inputText);
					setNumberOfTrucksChosen(Integer.parseInt(inputText)); 
					//tell() reports to the Observer when Slider position has changed
		        	controlPanelSettings.setNumberOfTrucks(getNumberOfTrucksChosen());
		        	tell(controlPanelSettings);									//slider value is handed over to tell()
				
				}
				else
				{
					txtNumberTrucksInSimulation.setText("0 - 10");
					txtNumberTrucksInSimulation.setForeground(Color.RED);
				}
				
			}
		});
		
		//LABEL: NUMBER TRUCKS
		lblNumberTrucks = new JLabel("-> Trucks", SwingConstants.CENTER);
		lblNumberTrucks.setForeground(Color.DARK_GRAY);
		lblNumberTrucks.setFont(font);
		GridBagConstraints gbc_lblNumberTrucks = new GridBagConstraints();
		gbc_lblNumberTrucks.gridwidth = 4;
		gbc_lblNumberTrucks.gridheight = 2;
		gbc_lblNumberTrucks.anchor = GridBagConstraints.WEST;
		gbc_lblNumberTrucks.gridx = 56;
		gbc_lblNumberTrucks.gridy = 18;
		gbc_lblNumberTrucks.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblNumberTrucks, gbc_lblNumberTrucks, 0);
		
		//3. SEPARATOR LINE
		lblSeparator = new JLabel("----------------");
		lblSeparator.setForeground(Color.GRAY);
		lblSeparator.setFont(font);
		gbc_lblSeparator.gridwidth = 7;
		gbc_lblSeparator.gridheight = 1;
		gbc_lblSeparator.anchor = GridBagConstraints.WEST;
		gbc_lblSeparator.gridx = 53;
		gbc_lblSeparator.gridy = 20;
		gbc_lblSeparator.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSeparator, gbc_lblSeparator, 0);
		
		
		//LABEL 1: VEHICLES IN SIMULATION
		lblVehiclesInSimulation1 = new JLabel("VEHICLES IN", SwingConstants.CENTER);
		lblVehiclesInSimulation1.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulation1.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulation1 = new GridBagConstraints();
		gbc_lblVehiclesInSimulation1.gridwidth = 7;
		gbc_lblVehiclesInSimulation1.gridheight = 1;
		gbc_lblVehiclesInSimulation1.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulation1.gridx = 53;
		gbc_lblVehiclesInSimulation1.gridy = 22;
		gbc_lblVehiclesInSimulation1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulation1, gbc_lblVehiclesInSimulation1, 0);
		
		
		//LABEL 2: VEHICLES IN SIMULATION
		lblVehiclesInSimulation2 = new JLabel("SIMULATION:", SwingConstants.CENTER);
		lblVehiclesInSimulation2.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulation2.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulation2 = new GridBagConstraints();
		gbc_lblVehiclesInSimulation2.gridwidth = 7;
		gbc_lblVehiclesInSimulation2.gridheight = 1;
		gbc_lblVehiclesInSimulation2.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulation2.gridx = 53;
		gbc_lblVehiclesInSimulation2.gridy = 23;
		gbc_lblVehiclesInSimulation2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulation2, gbc_lblVehiclesInSimulation2, 0);
		
		
		//LABEL: VEHICLES IN SIMULATION - Cars
		lblVehiclesInSimulationCars = new JLabel("  Cars: ", SwingConstants.CENTER);
		lblVehiclesInSimulationCars.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationCars.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationCars = new GridBagConstraints();
		gbc_lblVehiclesInSimulationCars.gridwidth = 4;
		gbc_lblVehiclesInSimulationCars.gridheight = 1;
		gbc_lblVehiclesInSimulationCars.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationCars.gridx = 53;
		gbc_lblVehiclesInSimulationCars.gridy = 25;
		gbc_lblVehiclesInSimulationCars.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationCars, gbc_lblVehiclesInSimulationCars, 0);
		
		//LABEL 2: VEHICLES IN SIMULATION - Cars
		lblVehiclesInSimulationCars2 = new JLabel("5", SwingConstants.CENTER);
		lblVehiclesInSimulationCars2.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationCars2.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationCars2 = new GridBagConstraints();
		gbc_lblVehiclesInSimulationCars2.gridwidth = 3;
		gbc_lblVehiclesInSimulationCars2.gridheight = 1;
		gbc_lblVehiclesInSimulationCars2.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationCars2.gridx = 57;
		gbc_lblVehiclesInSimulationCars2.gridy = 25;
		gbc_lblVehiclesInSimulationCars2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationCars2, gbc_lblVehiclesInSimulationCars2, 0);
		
		
		//LABEL: VEHICLES IN SIMULATION - Busses
		lblVehiclesInSimulationBusses = new JLabel("Busses: ", SwingConstants.CENTER);
		lblVehiclesInSimulationBusses.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationBusses.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationBusses = new GridBagConstraints();
		gbc_lblVehiclesInSimulationBusses.gridwidth = 4;
		gbc_lblVehiclesInSimulationBusses.gridheight = 1;
		gbc_lblVehiclesInSimulationBusses.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationBusses.gridx = 53;
		gbc_lblVehiclesInSimulationBusses.gridy = 27;
		gbc_lblVehiclesInSimulationBusses.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationBusses, gbc_lblVehiclesInSimulationBusses, 0);
		
		//LABEL 2: VEHICLES IN SIMULATION - Busses
		lblVehiclesInSimulationBusses2 = new JLabel("6", SwingConstants.CENTER);
		lblVehiclesInSimulationBusses2.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationBusses2.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationBusses2 = new GridBagConstraints();
		gbc_lblVehiclesInSimulationBusses2.gridwidth = 3;
		gbc_lblVehiclesInSimulationBusses2.gridheight = 1;
		gbc_lblVehiclesInSimulationBusses2.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationBusses2.gridx = 57;
		gbc_lblVehiclesInSimulationBusses2.gridy = 27;
		gbc_lblVehiclesInSimulationBusses2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationBusses2, gbc_lblVehiclesInSimulationBusses2, 0);
		
		//LABEL: VEHICLES IN SIMULATION - Trucks
		lblVehiclesInSimulationTrucks = new JLabel("Trucks: ", SwingConstants.CENTER);
		lblVehiclesInSimulationTrucks.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationTrucks.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationTrucks = new GridBagConstraints();
		gbc_lblVehiclesInSimulationTrucks.gridwidth = 4;
		gbc_lblVehiclesInSimulationTrucks.gridheight = 1;
		gbc_lblVehiclesInSimulationTrucks.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationTrucks.gridx = 53;
		gbc_lblVehiclesInSimulationTrucks.gridy = 29;
		gbc_lblVehiclesInSimulationTrucks.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationTrucks, gbc_lblVehiclesInSimulationTrucks, 0);
		
		//LABEL 2: VEHICLES IN SIMULATION - Trucks
		lblVehiclesInSimulationTrucks2 = new JLabel("2", SwingConstants.CENTER);
		lblVehiclesInSimulationTrucks2.setForeground(Color.DARK_GRAY);
		lblVehiclesInSimulationTrucks2.setFont(font);
		GridBagConstraints gbc_lblVehiclesInSimulationTrucks2 = new GridBagConstraints();
		gbc_lblVehiclesInSimulationTrucks2.gridwidth = 3;
		gbc_lblVehiclesInSimulationTrucks2.gridheight = 1;
		gbc_lblVehiclesInSimulationTrucks2.anchor = GridBagConstraints.WEST;
		gbc_lblVehiclesInSimulationTrucks2.gridx = 57;
		gbc_lblVehiclesInSimulationTrucks2.gridy = 29;
		gbc_lblVehiclesInSimulationTrucks2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblVehiclesInSimulationTrucks2, gbc_lblVehiclesInSimulationTrucks2, 0);
		
		
		//4. SEPARATOR LINE
		lblSeparator = new JLabel("----------------");
		lblSeparator.setForeground(Color.GRAY);
		lblSeparator.setFont(font);
		gbc_lblSeparator.gridwidth = 7;
		gbc_lblSeparator.gridheight = 1;
		gbc_lblSeparator.anchor = GridBagConstraints.WEST;
		gbc_lblSeparator.gridx = 53;
		gbc_lblSeparator.gridy = 31;
		gbc_lblSeparator.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSeparator, gbc_lblSeparator, 0);
		
		
		//LABEL 1: TRAFFIC JAM DETECTION
		lblTrafficJamDetection1 = new JLabel("TRAFFIC JAM", SwingConstants.CENTER);
		lblTrafficJamDetection1.setForeground(Color.DARK_GRAY);
		lblTrafficJamDetection1.setFont(font);
		GridBagConstraints gbc_lblTrafficJamDetection1 = new GridBagConstraints();
		gbc_lblTrafficJamDetection1.gridwidth = 7;
		gbc_lblTrafficJamDetection1.gridheight = 1;
		gbc_lblTrafficJamDetection1.anchor = GridBagConstraints.WEST;
		gbc_lblTrafficJamDetection1.gridx = 53;
		gbc_lblTrafficJamDetection1.gridy = 33;
		gbc_lblTrafficJamDetection1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblTrafficJamDetection1, gbc_lblTrafficJamDetection1, 0);
		
		//LABEL 2: TRAFFIC JAM DETECTION
		lblTrafficJamDetection2 = new JLabel("DETECTED:", SwingConstants.CENTER);
		lblTrafficJamDetection2.setForeground(Color.DARK_GRAY);
		lblTrafficJamDetection2.setFont(font);
		GridBagConstraints gbc_lblTrafficJamDetection2 = new GridBagConstraints();
		gbc_lblTrafficJamDetection2.gridwidth = 7;
		gbc_lblTrafficJamDetection2.gridheight = 1;
		gbc_lblTrafficJamDetection2.anchor = GridBagConstraints.WEST;
		gbc_lblTrafficJamDetection2.gridx = 53;
		gbc_lblTrafficJamDetection2.gridy = 34;
		gbc_lblTrafficJamDetection2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblTrafficJamDetection2, gbc_lblTrafficJamDetection2, 0);
		
		
		//LABEL: TRAFFIC JAM DETECTED
		lblTrafficJamDetected = new JLabel("NO - Easy Flow", SwingConstants.CENTER);
		lblTrafficJamDetected.setForeground(Color.GREEN);
		lblTrafficJamDetected.setFont(font);
		GridBagConstraints gbc_lblTrafficJamDetected = new GridBagConstraints();
		gbc_lblTrafficJamDetected.gridwidth = 7;
		gbc_lblTrafficJamDetected.gridheight = 1;
		gbc_lblTrafficJamDetected.anchor = GridBagConstraints.WEST;
		gbc_lblTrafficJamDetected.gridx = 53;
		gbc_lblTrafficJamDetected.gridy = 36;
		gbc_lblTrafficJamDetected.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblTrafficJamDetected, gbc_lblTrafficJamDetected, 0);
		

		//LAST SEPARATOR LINE
		lblSeparator = new JLabel("----------------");
		lblSeparator.setForeground(Color.GRAY);
		lblSeparator.setFont(font);
		gbc_lblSeparator = new GridBagConstraints();
		gbc_lblSeparator.gridwidth = 7;
		gbc_lblSeparator.gridheight = 1;
		gbc_lblSeparator.anchor = GridBagConstraints.WEST;
		gbc_lblSeparator.gridx = 53;
		gbc_lblSeparator.gridy = 37;
		gbc_lblSeparator.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(lblSeparator, gbc_lblSeparator, 0);
		
		
		//BUTTON "SIMULATION START / STOP"
		btnExit = new JButton("EXIT");
		btnExit.setForeground(Color.DARK_GRAY);
		btnExit.setFont(font);
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.gridwidth = 7;
		gbc_btnExit.gridheight = 3;
		gbc_btnExit.anchor = GridBagConstraints.WEST;
		gbc_btnExit.gridx = 53;
		gbc_btnExit.gridy = 38;
		gbc_btnExit.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridView.getPanel().add(btnExit, gbc_btnExit, 0);
		//ACTION LISTENER FOR BUTTON "SIMULATION START/STOP"
		btnExit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Do you really want to exit the simulation?", "Quit application", dialogButton);
				
				if (dialogResult == JOptionPane.YES_OPTION) 
				{
					// kill controller thread by sending interrupt
					model.getControllerThread().interrupt();
					// commit suicide
					System.exit(0);
				}
				else
				{
				}
			}
		});
		model.setControlPanel(this);
		GridView.getPanel().revalidate(); GridView.getPanel().updateUI(); GridView.getPanel().validate();
		
	} //End of Constructor
	
	
	/****************************************************************/
	/*                           METHODS                            */
	/****************************************************************/
	

	//GETTERS AND SETTERS
	public Integer getNumberOfPassengerCarsChosen() {
		return numberOfPassengerCarsChosen;
	}
	public void setNumberOfPassengerCarsChosen(Integer numberOfPassengerCarsChosen) {
		this.numberOfPassengerCarsChosen = numberOfPassengerCarsChosen;
	}
	public Integer getNumberOfTrucksChosen() {
		return numberOfTrucksChosen;
	}
	public void setNumberOfTrucksChosen(Integer numberOfTrucksChosen) {
		this.numberOfTrucksChosen = numberOfTrucksChosen;
	}
	
	/******************** METHOD: setVehicleCount ********************/
	//determine how many vehicles of different types in list and display numbers in controlpanel
	public void setVehicleCount(Integer vehicleType, String labelText)
	{
		if (vehicleType.equals(1))
		{
			lblVehiclesInSimulationCars2.setText(labelText);
		}
		else if (vehicleType.equals(2))
		{
			lblVehiclesInSimulationBusses2.setText(labelText);
		}
		else if (vehicleType.equals(3))
		{
			lblVehiclesInSimulationTrucks2.setText(labelText);
		}
	}
	
	/******************** METHOD: setTrafficJamLabel ********************/
	//set Traffic Jam Label
	public void setTrafficJamLabel(Boolean isJam)
	{
		if (isJam.equals(true))
		{
			lblTrafficJamDetected.setText("YES - solving...");
			lblTrafficJamDetected.setForeground(Color.RED);
		}
		else
		{
			lblTrafficJamDetected.setText("NO - EasyFlow");
			lblTrafficJamDetected.setForeground(Color.GREEN);
		}

	}
	
	/******************** METHOD: sendToController ********************/
	//Method for Observer: tells all Observers (the Controller) - how slider position has changed
    public void sendToController(VehicleCoordinates pos)
    {
		logger.info("sendPosToController, entry");
			
        	tell(pos);
    }
	
    /************************* METHOD: tell ***************************/
	//Method for Observer: tells all Observers (the Controller) - how slider position has changed
    public void tell(ControlPanelSettings controlPanelSettings)
    {
    	logger.info("tell(controlPanelSettings) called");
    	if(countObservers()>0)
        {
    		logger.info("tell observers");
    		setChanged();
            notifyObservers(controlPanelSettings);
        }
    }
    
    public void tell(VehicleCoordinates pos)
    {
    	logger.info("tell(pos) called");
    	if(countObservers()>0)
        {
    		logger.info("tell observers");
    		setChanged();
            notifyObservers(pos);
        }
    }
    
} //End of Class 
