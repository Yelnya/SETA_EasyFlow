package util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtility {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(LogUtility.getName(LogUtility.class.getName()));

	private static volatile Level actualStatus;

	static String appName = "SETA_EasyFlow";
			
			public static final String getName(String className)
	{
		return (appName+"."+className);
	}
			public static final String getAppName()
			{
				return (appName);
			}
			public static Level getLevel(Level specificLevel) {
				actualStatus = 
				
 
				// specificLevel; // sets the level as specified in specific class
				// Level.INFO;   // sets  the level to "INFO" for all classes
				Level.SEVERE; // sets  the level to "SEVERE" for all classes 
				
				// Level.OFF; // sets  the level to "OFF" for all classes 
				 
				 return actualStatus;

			}
			public static boolean getLogEscalation(boolean specificStatus) {
				// return specificStatus; // for selection on class level
				return true;	// escalate the log entries to superiour log handler
//				return false;	// do NOT escalate the log entries
			}
	
}
