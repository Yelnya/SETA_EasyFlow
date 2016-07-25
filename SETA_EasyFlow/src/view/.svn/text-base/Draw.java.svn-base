package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Draw 
{
	//ATTRIBUTES
	private static Border borderSign = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

	//BACKGROUND PICTURE
	static Icon imgBackground = new ImageIcon ("src/_resources/tiles/Hintergrundbild.png");
	//Label for Background Picture
	private static JLabel lblBackground;
	

	public static Border getBorderDeco() {
		return borderSign;
	}
	public static void setBorderDeco(Border borderLines) {
		Draw.borderSign = borderLines;
	}


	/************************************************************************/
	/*                       BACKGROUNDPICTURE  48x46                       */
	/************************************************************************/
	//draws the background picture
	public static void drawBackgroundPicture()
	{
		lblBackground = new JLabel(imgBackground);
		GridBagConstraints gbc_lblBackground = new GridBagConstraints();
		gbc_lblBackground.gridwidth = 63;						
		gbc_lblBackground.gridheight = 46;
		gbc_lblBackground.insets = new Insets(0, 0, 0, 0); 		//Padding
		gbc_lblBackground.gridx = 1;							//placing of the image
		gbc_lblBackground.gridy = 1;
		GridView.getPanel().add(lblBackground, gbc_lblBackground);
	} //End of method drawBackgroundPicture

} //End of class
