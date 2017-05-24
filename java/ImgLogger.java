////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	ImgLogger.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	If you want to display after the fact what the robot did during the
//		simulation, you'll need to record those images to then make an
//		animation.
//
// Creator:	Dan Gisselquist, Ph.D.
//		Gisselquist Technology, LLC
//
////////////////////////////////////////////////////////////////////////////////
//
// Copyright (C) 2017, Gisselquist Technology, LLC
//
// This program is free software (firmware): you can redistribute it and/or
// modify it under the terms of  the GNU General Public License as published
// by the Free Software Foundation, either version 3 of the License, or (at
// your option) any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTIBILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
// for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program.  (It's in the $(ROOT)/doc directory.  Run make with no
// target there if the PDF file isn't present.)  If not, see
// <http://www.gnu.org/licenses/> for a copy.
//
// License:	GPL, v3, as defined and found on www.gnu.org,
//		http://www.gnu.org/licenses/gpl.html
//
//
////////////////////////////////////////////////////////////////////////////////
//
//
package robosim;
import java.awt.*;
// import java.awt.Image.BufferedImage;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ImgLogger {
	public	static	int	m_count = 0;
	public static final boolean WRITE_IMAGES = false; // true;
	public static void	log(Image img) {
		if (WRITE_IMAGES) {
			if (img == null)
				return;
			if ((m_count & 0x07)==0) {
				int frame = m_count>>3;
				try {
					BufferedImage	bi = new BufferedImage(RoboDisplay.DIMENSION, RoboDisplay.DIMENSION, 1);
					bi.getGraphics().drawImage(img,  0,  0,  null);
					File	outputFile = new File(String.format("%s%03d.png", "rsim", frame));
					ImageIO.write(bi, "png", outputFile);
				} catch(IOException ioe) {
					System.err.println("IO Exception creating img "+m_count);
				}
			}
		}
		m_count++;
	}
}