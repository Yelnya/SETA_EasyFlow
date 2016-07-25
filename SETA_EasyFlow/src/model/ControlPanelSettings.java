package model;


/*******************************************************************/
/**  Class ControlPanelSettings                                   **/
/**                                                               **/ 
/**  storage for settings of Control Panel                        **/ 
/*******************************************************************/
public class ControlPanelSettings {
	protected boolean simulationActive;
	protected Integer timeModifier;
	private int numberOfPassengerCars;
	private int numberOfTrucks;
	private int numberOfBusses;
	
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/

	public ControlPanelSettings () {
		simulationActive=true;
		timeModifier=3;
		numberOfPassengerCars=5;
		numberOfTrucks=2;
		numberOfBusses=6;
	}
	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/

	public int getNumberOfBusses() {
		return numberOfBusses;
	}

	public void setNumberOfBusses(int numberOfBusses) {
		this.numberOfBusses = numberOfBusses;
	}

	public int getNumberOfTrucks() {
		return numberOfTrucks;
	}

	public void setNumberOfTrucks(int numberOfTrucks) {
		this.numberOfTrucks = numberOfTrucks;
	}

	public int getNumberOfPassengerCars() {
		return numberOfPassengerCars;
	}

	public void setNumberOfPassengerCars(int numberOfPassengerCars) {
		this.numberOfPassengerCars = numberOfPassengerCars;
	}

	public void setTimeModifier(int timeModifier) {
		this.timeModifier=timeModifier;
	}
	public void setSimulationActive(boolean simulationActive) {
		this.simulationActive=simulationActive;
	}
	public boolean isSimulationActive() {
		return simulationActive;
	}
	public Integer getTimeModifier() {
		return timeModifier;
	}


}
