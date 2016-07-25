package model;

import util.Bitmap;
/*******************************************************************/
/**  Class TrafficsignGiveWayModel                                **/
/**                                                               **/ 
/**  model for traffic sign: give way                             **/ 
/*******************************************************************/
public class TrafficsignGiveWayModel extends TrafficsignModel 
{
	//ATTRIBUTES

	
	//CONSTRUCTOR
	public TrafficsignGiveWayModel(Integer posX, Integer posY, Integer layer, Integer direction)
	{
		super(posX, posY, layer, direction, choseBitmapDirection(direction));

	}
	/*******************************************************************/
	/**  choseBitmapDirection                                		  **/
	/**                                                               **/ 
	/*******************************************************************/
	public static Bitmap choseBitmapDirection(Integer direction)
	{
		Bitmap bitmap = null;
		if(direction.equals(1))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/giveWaySign_1.png")));
		}
		else if(direction.equals(3))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/giveWaySign_3.png")));	
		}
		else if(direction.equals(5))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/giveWaySign_5.png")));	
		}
		else if(direction.equals(7))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/giveWaySign_7.png")));	
		}
		return bitmap;
	}
	
	
}
