package model;

import util.Bitmap;
/*******************************************************************/
/**  Class TrafficsignConstructionSiteModel                       **/
/**                                                               **/ 
/**  model for constructions site traffic sign model              **/ 
/*******************************************************************/
public class TrafficsignConstructionSiteModel extends TrafficsignModel 
{
	//ATTRIBUTES
	Boolean exists = true;			//for delete ConstructionSign PJ
	
	/*******************************************************************/
	/**  Constructor                                        		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public TrafficsignConstructionSiteModel(Integer posX, Integer posY, Integer layer, Integer direction)
	{
		super(posX, posY, layer, direction, new Bitmap(new String(
				"src/_resources/tiles/trafficSigns/constructionSite.png")));
	}

	/*******************************************************************/
	/**  Getter, Setter                                       		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public Boolean getExists() {
		return exists;
	}
	public void setExists(Boolean exists) {
		this.exists = exists;
	}


	
	
	
}
