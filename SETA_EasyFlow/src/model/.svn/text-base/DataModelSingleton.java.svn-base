package model;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

//import methods.Methods;
import controller.Controller;
import util.Bitmap;
import util.LogUtility;
import view.ControlPanel;
// import view.GridView;


// DataModel is implemented according to Singleton pattern, to be translated...
/*******************************************************************/
/**  Class DataModelSingleton                                     **/
/**                                                               **/ 
/**  Data Model according singleton pattern                       **/ 
/*******************************************************************/
public class DataModelSingleton 
{	
 // Eine (versteckte) Klassenvariable vom Typ der eigene Klasse
  private static DataModelSingleton instance;
  // Verhindere die Erzeugung des Objektes über andere Methoden
  private DataModelSingleton () {}
  // Eine Zugriffsmethode auf Klassenebene, welches dir '''einmal''' ein konkretes 
  // Objekt erzeugt und dieses zurückliefert.
  // Durch 'synchronized' wird sichergestellt dass diese Methode nur von einem Thread 
  // zu einer Zeit durchlaufen wird. Der nächste Thread erhält immer eine komplett 
  // initialisierte Instanz.
 
/*******************************************************************/
/**  getInstance                                         		  **/
/**                                                               **/ 
/**  access method to the singleton                               **/
/*******************************************************************/
  public static synchronized DataModelSingleton getInstance () {
    if (DataModelSingleton.instance == null) {
    	DataModelSingleton.instance = new DataModelSingleton ();
    }
    return DataModelSingleton.instance;
  }

	private GridModel gridModel = null;

	private static Thread controllerThread=null;
	private static Controller controller=null;
	
	private volatile Integer controllerSleepTime = 500; //time in ms for controller thread sleep period
	private volatile Integer controllerCyclePeriod=100; // specifies the number of cycles for traffic light switch
	private volatile Integer controllerCycleCounter=100; // specifies the number of cycles for traffic light switch
	

  
	// logger reference
	private static Logger logger = Logger.getLogger(LogUtility
			.getName(DataModelSingleton.class.getName()));
    static int standardGridSizeX=48;        // GridModel tilesArray: number of columns
    static int standardGridSizeY=45;        // GridModel tilesArray: number of lines

	static Bitmap backgroundBitmap=new Bitmap("Hintergrundbild.png") ;
	volatile private ControlPanelSettings  controlPanelSettings= null;	
							
	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public ControlPanelSettings getControlPanelSettings() {
		return controlPanelSettings;
	}

	public GridModel getGridModel() {
		return gridModel;
	}

	public void setGridModel(GridModel gridModel) {
		this.gridModel = gridModel;
	}

	// static GridView gridView = null;	
	ControlPanel controlPanel = null;	
	// public static Controller controller = null;
	
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	public  void setControlPanel(ControlPanel controlPanel2) {
		controlPanel = controlPanel2;
	}

	private volatile Integer startupStatus=0;
	
	
	public Integer getControllerSleepTime()
	{
		return controllerSleepTime ;
	}
	public void setControllerThread(Thread controllerThread) {
		// TODO Auto-generated method stub
		this.controllerThread = controllerThread;
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}
	public static Thread getControllerThread() {
		// TODO Auto-generated method stub
		return controllerThread;
	}

	public static Controller getController() {
		// TODO Auto-generated method stub
		return controller;
	}

	public Integer getControllerCycleCounter() {
		// TODO Auto-generated method stub
		return controllerCycleCounter;
	}
	public Integer getControllerCyclePeriod() {
		return controllerCyclePeriod;
	}
	public void setControllerCyclePeriod(Integer controllerCyclePeriod) {
		this.controllerCyclePeriod = controllerCyclePeriod;
	}
	public ArrayList<TrafficsignModel> getControllerCreatedTrafficSigns() {
		return null;
	}
	public ArrayList<TrafficsignModel> getControllerDeletedTrafficSigns() {
		return null;
	}
	public Integer getDefaultRedPhase() {
		return 10;
	}
	public Integer getDefaultGreenPhase() {
		return 10;
	}
	public Integer getStartupStatus() {
		return startupStatus;
	}
	public void setStartupStatus(Integer startupStatus) {
		this.startupStatus = startupStatus;
	}


	
	synchronized public  void setControlPanelSettings (ControlPanelSettings controlPanelSettings) {
		// method is called by Controller therefore need to be threadsafe
		this.controlPanelSettings = controlPanelSettings;
		logger.info("controlPanelSettings.SimulationActive: " + controlPanelSettings.isSimulationActive());
		logger.info("controlPanelSettings.timeModifier: " + controlPanelSettings.getTimeModifier());
		
	}
	/*******************************************************************/
	/**  init()                                             		  **/
	/**                                                               **/
	/**  initilizes the datamodel                                     **/ 
	/*******************************************************************/
	
	public void init() 
	{
	// initialize logger for the class
	logger.setLevel(LogUtility.getLevel(Level.INFO));
	logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
	logger.info("Create city grid :-)");

	controlPanelSettings = new ControlPanelSettings ();
	// setMethods(new Methods());
	gridModel = new GridModel(standardGridSizeX,standardGridSizeY, backgroundBitmap);
	// futureGridModel = new GridModel();
	}

}

