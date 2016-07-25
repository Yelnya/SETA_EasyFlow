package model;

import util.Bitmap;

public class TrafficsignOneWayModel extends TrafficsignModel
{
	//ATTRIBUTES

	
	//CONSTRUCTOR
	public TrafficsignOneWayModel(Integer posX, Integer posY, Integer layer, Integer direction)
	{
		super(posX, posY, layer, direction, choseBitmapDirection(direction));

	}

		
	//Method: chose the bitmap depending on direction
	public static Bitmap choseBitmapDirection(Integer direction)
	{
		Bitmap bitmap = null;
		if(direction.equals(1))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/oneWay_1.png")));
		}
		else if(direction.equals(3))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/oneWay_3.png")));	
		}
		else if(direction.equals(5))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/oneWay_5.png")));	
		}
		else if(direction.equals(7))
		{
			bitmap = (new Bitmap(new String("src/_resources/tiles/trafficSigns/oneWay_7.png")));	
		}
		return bitmap;
	}
	
	
} //end of class
