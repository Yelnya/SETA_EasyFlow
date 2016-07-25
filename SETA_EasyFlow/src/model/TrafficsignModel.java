package model;

import util.Bitmap;
/*******************************************************************/
/**  Class TrafficsignModel                                       **/
/**                                                               **/ 
/**  base class for traffic sign model                            **/ 
/*******************************************************************/
public abstract class TrafficsignModel extends BaseModel
{
	//ATTRIBUTES
	private Integer posX = null;
	private Integer posY = null;
	private Integer layer = null;
	private Integer direction = null;
	private Bitmap bitmap = null;
	private static DataModelSingleton model=null;
	
	//CONSTRUCTOR
	public TrafficsignModel(Integer posX, Integer posY, Integer layer, Integer direction, Bitmap bitmap)
	{
		model=DataModelSingleton.getInstance();
		setPosX(posX);
		setPosY(posY);
		setLayer(layer);
		setDirection(direction);
		setBitmap(bitmap);
	}

	//GETTERS AND SETTERS
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

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
