package process;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.LogUtility;
import view.GridView;
import model.DataModelSingleton;
import model.GridModel;

public class EasyFlow 
{

	private static Logger superLogger = Logger.getLogger(LogUtility.getAppName());
	private static Logger logger = Logger.getLogger(LogUtility.getName(EasyFlow.class.getName()));

	static Handler fh = null;
	static Handler ch = new ConsoleHandler();

	@SuppressWarnings("static-access")
	public static void main(String[] args) 
	{
		try {
			ch.setLevel(Level.ALL);

			String path = System.getProperty("user.home");		// retrieve users home directory from evironment

			path += "\\EasyFlowLogDir";
			File logDir = new File(path);
			if (logDir.exists() && logDir.isDirectory())
			{
				// log dir exists
			}
			else
			{
				// log dir is not there!
				logDir.mkdir();
			}
			path +="\\EasyFlow.log";
			try {
				fh = new FileHandler(path);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fh.setLevel(Level.ALL);
			superLogger.addHandler(fh);
			superLogger.setLevel(Level.ALL);
			logger.setLevel(LogUtility.getLevel(Level.INFO));
			logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
			logger.info("entry");
			logger.fine("create DataModel");
			DataModelSingleton model = DataModelSingleton.getInstance();
			model.init();
			model.setStartupStatus(1);
			
			logger.fine("create controllerThread");
			Thread controllerThread = new Thread (new ControllerThread());
			controllerThread.setDaemon(true);
			controllerThread.start();
			model.setControllerThread(controllerThread);
			model.getControllerThread().setName("Controller Thread");
			model.setStartupStatus(2);			
			
			GridView gridView = new GridView(model.getGridModel());		// creation of GridView
			gridView.init();
			model.setStartupStatus(3);			
			GridModel gridModel=model.getGridModel();
			gridModel.importDataFromXml();		// creation of GridModel content from XML file
			// gridModel.copyTilesArrayTo(gridModel.getActualTilesArray(), gridModel.getFutureTilesArray());		// creation of GridModel content from XML file
			model.setStartupStatus(4);			

			
			
			logger.info("*** EasyFlow ***");
		} catch (SecurityException e) {
			System.out.println("EXCEPTION IN MAIN()");
			e.printStackTrace();
		}
	} //end of constructor
} //end of class
