package util;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bitmap 
{
	String bitmapName=null;
	Icon bitmapIcon=null;
	JLabel bitmapLabel=null;

	
	public Bitmap ()	{
	}

	public Bitmap(String bitmapName) {
		super();
		setBitmapName(bitmapName);
		setBitmapIcon(new ImageIcon(getBitmapName()));
		setBitmapLabel(new JLabel(getBitmapIcon()));
	}

	public JLabel getBitmapLabel() {
		return bitmapLabel;
	}
	public void setBitmapLabel(JLabel bitmapLabel) {
		this.bitmapLabel = bitmapLabel;
	}

	public Icon getBitmapIcon() {
		return bitmapIcon;
	}

	public void setBitmapIcon(Icon bitmapIcon) {
		this.bitmapIcon = bitmapIcon;
	}

	public String getBitmapName() {
		return bitmapName;
	}
	public void setBitmapName(String bitmapName) {
		this.bitmapName = bitmapName;
	}

}
