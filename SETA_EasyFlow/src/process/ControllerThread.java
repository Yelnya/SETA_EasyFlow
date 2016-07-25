package process;

import model.DataModelSingleton;
import controller.Controller;

public class ControllerThread implements Runnable {

	
	private Controller ctrl=null;
	private DataModelSingleton model=null;

	public ControllerThread() {
		super();
		this.model=model;
	}

	@Override
	public void run() {
		//logger.info("entry");
			ctrl = new Controller();
			model=DataModelSingleton.getInstance();
			model.setController(ctrl);
			ctrl.loop();		
	}


	
	public Controller getController()
	{
		return ctrl;
	}

}
