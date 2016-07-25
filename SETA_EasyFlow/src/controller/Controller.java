package controller;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.LogUtility;
import model.ControlPanelSettings;
import model.DataModelSingleton;
import model.VehicleCoordinates;

//Controller is Observer to ControlPanel  -> changes will be stored here 
public class Controller implements Observer {
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(Controller.class.getName()));
	// will be instantiated in DataModel

	// ATTRIBUTES
	// time interval
	private Integer timeIntervalStandard = null; 
	private Integer timeModifier = null; // control panel modified via slider
	private Integer timeIntervalCurrent = null; 
	private DataModelSingleton model = null;
	private Integer stepCounter;


	private Boolean simulationActive;

	private boolean firstCall;

	private BasicRules basicRules = null;

	private VehicleCoordinates manuallyCreatedVehiclePos = null;

	
	public Controller() {
		this.model = DataModelSingleton.getInstance();

		logger.setLevel(LogUtility.getLevel(Level.INFO));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));

		this.timeIntervalStandard = 900; // standard speed in ms
		this.timeModifier = 3; // slider modifier
		this.timeIntervalCurrent = timeIntervalStandard / timeModifier;

		this.simulationActive = true;
		this.firstCall = true;
		stepCounter = 0;

	}

	
	//GETTER AND SETTER
	public DataModelSingleton getModel() {
		return model;
	}
	public void setModel(DataModelSingleton model) {
		this.model = model;
	}

	public Integer getTimeIntervalStandard() {
		return timeIntervalStandard;
	}

	public void setTimeIntervalStandard(Integer timeIntervalStandard) {
		this.timeIntervalStandard = timeIntervalStandard;
	}

	public Integer getTimeModifier() {
		return timeModifier;
	}

	public Integer getTimeIntervalCurrent() {
		return timeIntervalCurrent;
	}

	public void setTimeIntervalCurrent(Integer timeIntervalCurrent) {
		this.timeIntervalCurrent = timeIntervalCurrent;
	}

	private void setManuallyCreatedVehiclePos(VehicleCoordinates vehicle) {
		manuallyCreatedVehiclePos = vehicle;
	}

	private VehicleCoordinates getManuallyCreatedVehiclePos() {
		return manuallyCreatedVehiclePos;
	}

	private void setSimulationActive(Boolean simulationActive) {
		this.simulationActive = simulationActive;
		return;
	}  // 
	
	
	/*******************************************************************/
	/**  setTimeModifier(Integer timeModifier) 						  **/
	/**  sets the timeModifier in order to change sleep time interval **/
	/**  of the controller thread                                     **/   
	/*******************************************************************/
	
	public void setTimeModifier(Integer timeModifier) {
		this.timeModifier = timeModifier;

		logger.info("timeModifier: " + timeModifier);
		// modification via slider in controlpanel

		setTimeIntervalCurrent(getTimeIntervalStandard() / timeModifier);

	}   // end method setTimeModifier()


	/*******************************************************************/
	/**  update(Observable o, Object arg)        					  **/
	/**  Observer framework method, receives update from ControlPanel **/
	/** (GUI)
	/*******************************************************************/

	public void update(Observable o, Object arg) {
		logger.info("Controller (Observer)   received update from View(Observable)!");
		if (arg instanceof ControlPanelSettings) {
			setTimeModifier(((ControlPanelSettings) arg).getTimeModifier()); 
			setSimulationActive(((ControlPanelSettings) arg)
					.isSimulationActive());

			//store current settings into model
			model.setControlPanelSettings((ControlPanelSettings) arg); 
			setManuallyCreatedVehiclePos(null); // indicates that the is noting created
		} else {
			if (arg instanceof VehicleCoordinates) {
				logger.info("ManuallyCreatedVehiclePos received!");
				
				// new vehicle create by menu!
				setManuallyCreatedVehiclePos((VehicleCoordinates) arg);
				logger.info("x: "+getManuallyCreatedVehiclePos().getPosX());
				logger.info("y: "+getManuallyCreatedVehiclePos().getPosY());
			} else {
				logger.info("Controller received invalid update(arg) !");
			}

		}
	}

	
	
	/*******************************************************************/
	/**  loop()                                 					  **/
	/**  this is the main loop of the controller thread               **/
	/*******************************************************************/

	public void loop() {

		boolean timeout = true;
		init();
		while (Thread.currentThread().isInterrupted() == false)
		// run in loop as long as there is no interrupt flag set from outside
		{
			logger.finest("Controller thread is running and running!");
			try {
				// Thread.sleep(model.getControllerSleepTime());
				Thread.sleep(timeIntervalCurrent);

			} catch (InterruptedException e) {
				logger.info("Controller thread interrupted while sleeping!");
				timeout = false;
			}
			if (timeout == true) {
				logger.finest("Controller thread back from sleeping !");
				if (firstCall) {
					firstStep();
					firstCall = false;
				} else {
					calculateNextStep();
				}

			} else {
				logger.finest("End of thread after interrupt reception before timeout expired");
				// stop this thread
				Thread.currentThread().interrupt(); // this is
													// required
													// because an
													// interrupt
													// exception
													// during sleep
													// would delete
													// the interrupt
													// flag,
				// and method isInterrupted() would not react accordingly!!!
			}
		} // end while
		logger.info("End of controller thread, because of exit-interrupt!");
	}  // end method loop()
	
	/*******************************************************************/
	/**  init()                                    					  **/
	/**  initialization routine                                       **/
	/*******************************************************************/

	private void init() {
		basicRules = new BasicRules(model.getGridModel(), 0);
	}  // end method init
	/*******************************************************************/
	/**  firstStep()                        				          **/
	/**  fist step initialization routine                             **/
	/*******************************************************************/

	private void firstStep() {
		if (model.getStartupStatus() >= 4) {
			basicRules.execute(manuallyCreatedVehiclePos);
			basicRules.propagateChanges();
			setManuallyCreatedVehiclePos(null); // reset the positions
		}

	} // end method firstStep
	
	
	/*******************************************************************/
	/**  calculateNextStep()                  				          **/
	/**  simulation step for calculation of next scenario             **/
	/*******************************************************************/

	private void calculateNextStep() {

		stepCounter++;
		logger.finest("Controller is calculating the next step!");
		if (simulationActive) {
			// logger indicates status only each n'th tick
			if (stepCounter % 20 == 0)
				logger.info("Simulation ongoing!");
			if (model.getStartupStatus() >= 4) {
				// only if the start up phase has been finished!
				basicRules.execute(manuallyCreatedVehiclePos);
				basicRules.propagateChanges();
				setManuallyCreatedVehiclePos(null); // reset the positions
			}
		}
	} // end method calculateNextStep

} // end class Controller
