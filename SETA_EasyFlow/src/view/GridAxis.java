package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GridAxis 
{
	
	/************************************************************************/
	/*                          DRAW AXIS NAMES                             */
	/************************************************************************/
	
	public static void drawAxisNames()
	{
		JLabel lblx0y0 = new JLabel("x/y");
		lblx0y0.setHorizontalAlignment(SwingConstants.CENTER);
		lblx0y0.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblx0y0.setToolTipText("Beschriftung");
		lblx0y0.setBorder(Draw.getBorderDeco());
		GridBagConstraints gbc_lblx0y0 = new GridBagConstraints();
		gbc_lblx0y0.fill = GridBagConstraints.BOTH;
		gbc_lblx0y0.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblx0y0.insets = new Insets(0, 0, 0, 0); 	//Padding
		gbc_lblx0y0.gridx = 0;							//where to place image
		gbc_lblx0y0.gridy = 0;
		GridView.getPanel().add(lblx0y0, gbc_lblx0y0, 0);
		
		/*********************** NAMES OF X-AXIS *****************************/
		JLabel lblx1y0 = new JLabel("1");
		GridBagConstraints gbc_lblx1y0 = new GridBagConstraints();
		drawTileXYaxis(lblx1y0, gbc_lblx1y0, 1, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx2y0 = new JLabel("2");
		GridBagConstraints gbc_lblx2y0 = new GridBagConstraints();
		drawTileXYaxis(lblx2y0, gbc_lblx2y0, 2, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx3y0 = new JLabel("3");
		GridBagConstraints gbc_lblx3y0 = new GridBagConstraints();
		drawTileXYaxis(lblx3y0, gbc_lblx3y0, 3, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx4y0 = new JLabel("4");
		GridBagConstraints gbc_lblx4y0 = new GridBagConstraints();
		drawTileXYaxis(lblx4y0, gbc_lblx4y0, 4, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx5y0 = new JLabel("5");
		GridBagConstraints gbc_lblx5y0 = new GridBagConstraints();
		drawTileXYaxis(lblx5y0, gbc_lblx5y0, 5, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx6y0 = new JLabel("6");
		GridBagConstraints gbc_lblx6y0 = new GridBagConstraints();
		drawTileXYaxis(lblx6y0, gbc_lblx6y0, 6, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx7y0 = new JLabel("7");
		GridBagConstraints gbc_lblx7y0 = new GridBagConstraints();
		drawTileXYaxis(lblx7y0, gbc_lblx7y0, 7, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx8y0 = new JLabel("8");
		GridBagConstraints gbc_lblx8y0 = new GridBagConstraints();
		drawTileXYaxis(lblx8y0, gbc_lblx8y0, 8, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx9y0 = new JLabel("9");
		GridBagConstraints gbc_lblx9y0 = new GridBagConstraints();
		drawTileXYaxis(lblx9y0, gbc_lblx9y0, 9, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx10y0 = new JLabel("10");
		GridBagConstraints gbc_lblx10y0 = new GridBagConstraints();
		drawTileXYaxis(lblx10y0, gbc_lblx10y0, 10, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx11y0 = new JLabel("11");
		GridBagConstraints gbc_lblx11y0 = new GridBagConstraints();
		drawTileXYaxis(lblx11y0, gbc_lblx11y0, 11, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx12y0 = new JLabel("12");
		GridBagConstraints gbc_lblx12y0 = new GridBagConstraints();
		drawTileXYaxis(lblx12y0, gbc_lblx12y0, 12, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx13y0 = new JLabel("13");
		GridBagConstraints gbc_lblx13y0 = new GridBagConstraints();
		drawTileXYaxis(lblx13y0, gbc_lblx13y0, 13, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx14y0 = new JLabel("14");
		GridBagConstraints gbc_lblx14y0 = new GridBagConstraints();
		drawTileXYaxis(lblx14y0, gbc_lblx14y0, 14, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx15y0 = new JLabel("15");
		GridBagConstraints gbc_lblx15y0 = new GridBagConstraints();
		drawTileXYaxis(lblx15y0, gbc_lblx15y0, 15, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx16y0 = new JLabel("16");
		GridBagConstraints gbc_lblx16y0 = new GridBagConstraints();
		drawTileXYaxis(lblx16y0, gbc_lblx16y0, 16, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx17y0 = new JLabel("17");
		GridBagConstraints gbc_lblx17y0 = new GridBagConstraints();
		drawTileXYaxis(lblx17y0, gbc_lblx17y0, 17, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx18y0 = new JLabel("18");
		GridBagConstraints gbc_lblx18y0 = new GridBagConstraints();
		drawTileXYaxis(lblx18y0, gbc_lblx18y0, 18, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx19y0 = new JLabel("19");
		GridBagConstraints gbc_lblx19y0 = new GridBagConstraints();
		drawTileXYaxis(lblx19y0, gbc_lblx19y0, 19, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx20y0 = new JLabel("20");
		GridBagConstraints gbc_lblx20y0 = new GridBagConstraints();
		drawTileXYaxis(lblx20y0, gbc_lblx20y0, 20, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx21y0 = new JLabel("21");
		GridBagConstraints gbc_lblx21y0 = new GridBagConstraints();
		drawTileXYaxis(lblx21y0, gbc_lblx21y0, 21, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx22y0 = new JLabel("22");
		GridBagConstraints gbc_lblx22y0 = new GridBagConstraints();
		drawTileXYaxis(lblx22y0, gbc_lblx22y0, 22, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx23y0 = new JLabel("23");
		GridBagConstraints gbc_lblx23y0 = new GridBagConstraints();
		drawTileXYaxis(lblx23y0, gbc_lblx23y0, 23, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx24y0 = new JLabel("24");
		GridBagConstraints gbc_lblx24y0 = new GridBagConstraints();
		drawTileXYaxis(lblx24y0, gbc_lblx24y0, 24, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx25y0 = new JLabel("25");
		GridBagConstraints gbc_lblx25y0 = new GridBagConstraints();
		drawTileXYaxis(lblx25y0, gbc_lblx25y0, 25, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx26y0 = new JLabel("26");
		GridBagConstraints gbc_lblx26y0 = new GridBagConstraints();
		drawTileXYaxis(lblx26y0, gbc_lblx26y0, 26, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx27y0 = new JLabel("27");
		GridBagConstraints gbc_lblx27y0 = new GridBagConstraints();
		drawTileXYaxis(lblx27y0, gbc_lblx27y0, 27, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx28y0 = new JLabel("28");
		GridBagConstraints gbc_lblx28y0 = new GridBagConstraints();
		drawTileXYaxis(lblx28y0, gbc_lblx28y0, 28, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx29y0 = new JLabel("29");
		GridBagConstraints gbc_lblx29y0 = new GridBagConstraints();
		drawTileXYaxis(lblx29y0, gbc_lblx29y0, 29, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx30y0 = new JLabel("30");
		GridBagConstraints gbc_lblx30y0 = new GridBagConstraints();
		drawTileXYaxis(lblx30y0, gbc_lblx30y0, 30, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx31y0 = new JLabel("31");
		GridBagConstraints gbc_lblx31y0 = new GridBagConstraints();
		drawTileXYaxis(lblx31y0, gbc_lblx31y0, 31, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx32y0 = new JLabel("32");
		GridBagConstraints gbc_lblx32y0 = new GridBagConstraints();
		drawTileXYaxis(lblx32y0, gbc_lblx32y0, 32, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx33y0 = new JLabel("33");
		GridBagConstraints gbc_lblx33y0 = new GridBagConstraints();
		drawTileXYaxis(lblx33y0, gbc_lblx33y0, 33, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx34y0 = new JLabel("34");
		GridBagConstraints gbc_lblx34y0 = new GridBagConstraints();
		drawTileXYaxis(lblx34y0, gbc_lblx34y0, 34, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx35y0 = new JLabel("35");
		GridBagConstraints gbc_lblx35y0 = new GridBagConstraints();
		drawTileXYaxis(lblx35y0, gbc_lblx35y0, 35, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx36y0 = new JLabel("36");
		GridBagConstraints gbc_lblx36y0 = new GridBagConstraints();
		drawTileXYaxis(lblx36y0, gbc_lblx36y0, 36, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx37y0 = new JLabel("37");
		GridBagConstraints gbc_lblx37y0 = new GridBagConstraints();
		drawTileXYaxis(lblx37y0, gbc_lblx37y0, 37, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx38y0 = new JLabel("38");
		GridBagConstraints gbc_lblx38y0 = new GridBagConstraints();
		drawTileXYaxis(lblx38y0, gbc_lblx38y0, 38, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx39y0 = new JLabel("39");
		GridBagConstraints gbc_lblx39y0 = new GridBagConstraints();
		drawTileXYaxis(lblx39y0, gbc_lblx39y0, 39, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx40y0 = new JLabel("40");
		GridBagConstraints gbc_lblx40y0 = new GridBagConstraints();
		drawTileXYaxis(lblx40y0, gbc_lblx40y0, 40, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx41y0 = new JLabel("41");
		GridBagConstraints gbc_lblx41y0 = new GridBagConstraints();
		drawTileXYaxis(lblx41y0, gbc_lblx41y0, 41, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx42y0 = new JLabel("42");
		GridBagConstraints gbc_lblx42y0 = new GridBagConstraints();
		drawTileXYaxis(lblx42y0, gbc_lblx42y0, 42, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over	
		
		JLabel lblx43y0 = new JLabel("43");
		GridBagConstraints gbc_lblx43y0 = new GridBagConstraints();
		drawTileXYaxis(lblx43y0, gbc_lblx43y0, 43, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx44y0 = new JLabel("44");
		GridBagConstraints gbc_lblx44y0 = new GridBagConstraints();
		drawTileXYaxis(lblx44y0, gbc_lblx44y0, 44, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx45y0 = new JLabel("45");
		GridBagConstraints gbc_lblx45y0 = new GridBagConstraints();
		drawTileXYaxis(lblx45y0, gbc_lblx45y0, 45, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx46y0 = new JLabel("46");
		GridBagConstraints gbc_lblx46y0 = new GridBagConstraints();
		drawTileXYaxis(lblx46y0, gbc_lblx46y0, 46, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx47y0 = new JLabel("47");
		GridBagConstraints gbc_lblx47y0 = new GridBagConstraints();
		drawTileXYaxis(lblx47y0, gbc_lblx47y0, 47, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
	
		JLabel lblx48y0 = new JLabel("48");
		GridBagConstraints gbc_lblx48y0 = new GridBagConstraints();
		drawTileXYaxis(lblx48y0, gbc_lblx48y0, 48, 0);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		
		/*********************** NAMES OF Y-AXIS *****************************/
		JLabel lblx0y1 = new JLabel("1");
		GridBagConstraints gbc_lblx0y1 = new GridBagConstraints();
		drawTileXYaxis(lblx0y1, gbc_lblx0y1, 0, 1);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y2 = new JLabel("2");
		GridBagConstraints gbc_lblx0y2 = new GridBagConstraints();
		drawTileXYaxis(lblx0y2, gbc_lblx0y2, 0, 2);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y3 = new JLabel("3");
		GridBagConstraints gbc_lblx0y3 = new GridBagConstraints();
		drawTileXYaxis(lblx0y3, gbc_lblx0y3, 0, 3);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y4 = new JLabel("4");
		GridBagConstraints gbc_lblx0y4 = new GridBagConstraints();
		drawTileXYaxis(lblx0y4, gbc_lblx0y4, 0, 4);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y5 = new JLabel("5");
		GridBagConstraints gbc_lblx0y5 = new GridBagConstraints();
		drawTileXYaxis(lblx0y5, gbc_lblx0y5, 0, 5);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y6 = new JLabel("6");
		GridBagConstraints gbc_lblx0y6 = new GridBagConstraints();
		drawTileXYaxis(lblx0y6, gbc_lblx0y6, 0, 6);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y7 = new JLabel("7");
		GridBagConstraints gbc_lblx0y7 = new GridBagConstraints();
		drawTileXYaxis(lblx0y7, gbc_lblx0y7, 0, 7);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y8 = new JLabel("8");
		GridBagConstraints gbc_lblx0y8 = new GridBagConstraints();
		drawTileXYaxis(lblx0y8, gbc_lblx0y8, 0, 8);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y9 = new JLabel("9");
		GridBagConstraints gbc_lblx0y9 = new GridBagConstraints();
		drawTileXYaxis(lblx0y9, gbc_lblx0y9, 0, 9);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y10 = new JLabel("10");
		GridBagConstraints gbc_lblx0y10 = new GridBagConstraints();
		drawTileXYaxis(lblx0y10, gbc_lblx0y10, 0, 10);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y11 = new JLabel("11");
		GridBagConstraints gbc_lblx0y11 = new GridBagConstraints();
		drawTileXYaxis(lblx0y11, gbc_lblx0y11, 0, 11);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y12 = new JLabel("12");
		GridBagConstraints gbc_lblx0y12 = new GridBagConstraints();
		drawTileXYaxis(lblx0y12, gbc_lblx0y12, 0, 12);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y13 = new JLabel("13");
		GridBagConstraints gbc_lblx0y13 = new GridBagConstraints();
		drawTileXYaxis(lblx0y13, gbc_lblx0y13, 0, 13);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y14 = new JLabel("14");
		GridBagConstraints gbc_lblx0y14 = new GridBagConstraints();
		drawTileXYaxis(lblx0y14, gbc_lblx0y14, 0, 14);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y15 = new JLabel("15");
		GridBagConstraints gbc_lblx0y15 = new GridBagConstraints();
		drawTileXYaxis(lblx0y15, gbc_lblx0y15, 0, 15);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y16 = new JLabel("16");
		GridBagConstraints gbc_lblx0y16 = new GridBagConstraints();
		drawTileXYaxis(lblx0y16, gbc_lblx0y16, 0, 16);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y17 = new JLabel("17");
		GridBagConstraints gbc_lblx0y17 = new GridBagConstraints();
		drawTileXYaxis(lblx0y17, gbc_lblx0y17, 0, 17);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y18 = new JLabel("18");
		GridBagConstraints gbc_lblx0y18 = new GridBagConstraints();
		drawTileXYaxis(lblx0y18, gbc_lblx0y18, 0, 18);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y19 = new JLabel("19");
		GridBagConstraints gbc_lblx0y19 = new GridBagConstraints();
		drawTileXYaxis(lblx0y19, gbc_lblx0y19, 0, 19);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y20 = new JLabel("20");
		GridBagConstraints gbc_lblx0y20 = new GridBagConstraints();
		drawTileXYaxis(lblx0y20, gbc_lblx0y20, 0, 20);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y21 = new JLabel("21");
		GridBagConstraints gbc_lblx0y21 = new GridBagConstraints();
		drawTileXYaxis(lblx0y21, gbc_lblx0y21, 0, 21);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y22 = new JLabel("22");
		GridBagConstraints gbc_lblx0y22 = new GridBagConstraints();
		drawTileXYaxis(lblx0y22, gbc_lblx0y22, 0, 22);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y23 = new JLabel("23");
		GridBagConstraints gbc_lblx0y23 = new GridBagConstraints();
		drawTileXYaxis(lblx0y23, gbc_lblx0y23, 0, 23);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y24 = new JLabel("24");
		GridBagConstraints gbc_lblx0y24 = new GridBagConstraints();
		drawTileXYaxis(lblx0y24, gbc_lblx0y24, 0, 24);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y25 = new JLabel("25");
		GridBagConstraints gbc_lblx0y25 = new GridBagConstraints();
		drawTileXYaxis(lblx0y25, gbc_lblx0y25, 0, 25);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y26 = new JLabel("26");
		GridBagConstraints gbc_lblx0y26 = new GridBagConstraints();
		drawTileXYaxis(lblx0y26, gbc_lblx0y26, 0, 26);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y27 = new JLabel("27");
		GridBagConstraints gbc_lblx0y27 = new GridBagConstraints();
		drawTileXYaxis(lblx0y27, gbc_lblx0y27, 0, 27);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y28 = new JLabel("28");
		GridBagConstraints gbc_lblx0y28 = new GridBagConstraints();
		drawTileXYaxis(lblx0y28, gbc_lblx0y28, 0, 28);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y29 = new JLabel("29");
		GridBagConstraints gbc_lblx0y29 = new GridBagConstraints();
		drawTileXYaxis(lblx0y29, gbc_lblx0y29, 0, 29);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y30 = new JLabel("30");
		GridBagConstraints gbc_lblx0y30 = new GridBagConstraints();
		drawTileXYaxis(lblx0y30, gbc_lblx0y30, 0, 30);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y31 = new JLabel("31");
		GridBagConstraints gbc_lblx0y31 = new GridBagConstraints();
		drawTileXYaxis(lblx0y31, gbc_lblx0y31, 0, 31);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y32 = new JLabel("32");
		GridBagConstraints gbc_lblx0y32 = new GridBagConstraints();
		drawTileXYaxis(lblx0y32, gbc_lblx0y32, 0, 32);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y33 = new JLabel("33");
		GridBagConstraints gbc_lblx0y33 = new GridBagConstraints();
		drawTileXYaxis(lblx0y33, gbc_lblx0y33, 0, 33);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y34 = new JLabel("34");
		GridBagConstraints gbc_lblx0y34 = new GridBagConstraints();
		drawTileXYaxis(lblx0y34, gbc_lblx0y34, 0, 34);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y35 = new JLabel("35");
		GridBagConstraints gbc_lblx0y35 = new GridBagConstraints();
		drawTileXYaxis(lblx0y35, gbc_lblx0y35, 0, 35);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y36 = new JLabel("36");
		GridBagConstraints gbc_lblx0y36 = new GridBagConstraints();
		drawTileXYaxis(lblx0y36, gbc_lblx0y36, 0, 36);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y37 = new JLabel("37");
		GridBagConstraints gbc_lblx0y37 = new GridBagConstraints();
		drawTileXYaxis(lblx0y37, gbc_lblx0y37, 0, 37);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y38 = new JLabel("38");
		GridBagConstraints gbc_lblx0y38 = new GridBagConstraints();
		drawTileXYaxis(lblx0y38, gbc_lblx0y38, 0, 38);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y39 = new JLabel("39");
		GridBagConstraints gbc_lblx0y39 = new GridBagConstraints();
		drawTileXYaxis(lblx0y39, gbc_lblx0y39, 0, 39);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y40 = new JLabel("40");
		GridBagConstraints gbc_lblx0y40 = new GridBagConstraints();
		drawTileXYaxis(lblx0y40, gbc_lblx0y40, 0, 40);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y41 = new JLabel("41");
		GridBagConstraints gbc_lblx0y41 = new GridBagConstraints();
		drawTileXYaxis(lblx0y41, gbc_lblx0y41, 0, 41);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y42 = new JLabel("42");
		GridBagConstraints gbc_lblx0y42 = new GridBagConstraints();
		drawTileXYaxis(lblx0y42, gbc_lblx0y42, 0, 42);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y43 = new JLabel("43");
		GridBagConstraints gbc_lblx0y43 = new GridBagConstraints();
		drawTileXYaxis(lblx0y43, gbc_lblx0y43, 0, 43);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y44 = new JLabel("44");
		GridBagConstraints gbc_lblx0y44 = new GridBagConstraints();
		drawTileXYaxis(lblx0y44, gbc_lblx0y44, 0, 44);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		JLabel lblx0y45 = new JLabel("45");
		GridBagConstraints gbc_lblx0y45 = new GridBagConstraints();
		drawTileXYaxis(lblx0y45, gbc_lblx0y45, 0, 45);		//labelname, gridbagname, x-Coordinate, y-Coordinate handed over
		
		//LAST DECO ELEMENT ABOVE CONTROL PANEL
		JLabel xxx = new JLabel("    oOo.oOo.oOo.oOo.oOo.oOo.oOo.oOo   ");
		GridBagConstraints gbc_xxx = new GridBagConstraints();
		gbc_xxx.gridwidth = 15;
		gbc_xxx.gridheight = 1;
		gbc_xxx.anchor = GridBagConstraints.WEST;
		gbc_xxx.gridx = 49;							//where image is placed
		gbc_xxx.gridy = 0;
		GridView.getPanel().add(xxx, gbc_xxx, 1);	
	}
	
	
	//methods draws axis tiles -> no Action Listener
	private static void drawTileXYaxis(JLabel lblName, GridBagConstraints gbc_lblname, int gridX, int gridY)
	{
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBorder(Draw.getBorderDeco());
		lblName.setOpaque(true);							//set lowest level opaque
		gbc_lblname.fill = GridBagConstraints.BOTH;
		gbc_lblname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblname.insets = new Insets(0, 0, 0, 0); 		//padding
		gbc_lblname.gridx = gridX;							//where to place image
		gbc_lblname.gridy = gridY;
		GridView.getPanel().add(lblName, gbc_lblname, 1);	//layer 1
	}
	
}
