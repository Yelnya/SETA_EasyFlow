package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import util.LogUtility;
import view.BaseView;


/*******************************************************************/
/**  Class BaseModel                                       		  **/
/**  abstract class for MVC frame work                            **/ 
/*******************************************************************/

public abstract class BaseModel implements ModelMVC {
	private BaseView view = null;
	public BaseView getView() {
		return view;
	}
	public void setView(BaseView view) {
		this.view = view;
	}

	private static Logger logger = Logger.getLogger(LogUtility
			.getName(BaseModel.class.getName()));

	
	/*******************************************************************/
	/**  Constructor                                         		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public BaseModel()
	{
		logger.setLevel(LogUtility.getLevel(Level.SEVERE));
		logger.setUseParentHandlers(LogUtility.getLogEscalation(true));
	}

	/*******************************************************************/
	/**  register                                            		  **/
	/**  registers a View class at current model                      **/ 
	/*******************************************************************/

	@Override
	public void register(BaseView view) {
		// NOTE: currently only ONE view per model Supported!
		if (this.view == null)
		{
			this.view=view;	
			logger.info("View is registerd at model!");
		} 
		else
		{
			this.view=view;
			logger.warning("View is registerd twice, previous view discarded!");
		}
	}

	/*******************************************************************/
	/**  De-register                                         		  **/
	/**  De-register a View class from current model                  **/ 
	/*******************************************************************/

	@Override
	public void deRegister(BaseView view) {
		this.view=null;	
	}

	/*******************************************************************/
	/**  getData()                                           		  **/
	/**                                                               **/ 
	/*******************************************************************/
	@Override
	public BaseModel getData() {
		return this;
	}
	
	/*******************************************************************/
	/**  delete()                                           		  **/
	/**  De-Register a model class from parent                        **/ 
	/*******************************************************************/

	public void delete() {
	}
}
