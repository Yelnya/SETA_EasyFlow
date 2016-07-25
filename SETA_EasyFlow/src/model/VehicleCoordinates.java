package model;

/*******************************************************************/
/**  Class VehicleCoordinates                           		  **/
/**                                                               **/
/*******************************************************************/
public class VehicleCoordinates {
	/*******************************************************************/
	/** Constructor **/
	/**                                                               **/
	/*******************************************************************/
	public VehicleCoordinates(Integer posX, Integer posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

	Integer posX = null;
	Integer posY = null;

	/*******************************************************************/
	/** Getter, Setter **/
	/**                                                               **/
	/*******************************************************************/
	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

}
