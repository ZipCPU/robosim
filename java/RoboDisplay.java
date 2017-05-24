////////////////////////////////////////////////////////////////////////////////
//
// Filename:	RoboDisplay.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	To draw the robot as it moves around on a simulated field.
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
// import java.net.*;
import java.io.*;
import javax.imageio.*;

public class RoboDisplay extends Canvas {
	PastPoint	m_pplist[];
	int			m_npastpoints, m_nppbuf;
	RobotState	m_current;
	private	Image	m_base, // Offscreen bitmap containing nothing but the field background
					m_tail, // Offscreen bitmap, with field plus travel tail on it
					m_onscreen; // Currently displayed bitmap = tail with robot displayed on it
	private static final long serialVersionUID = 1;
	public	static final int DIMENSION = 512; // 1069;
	
	public	RoboDisplay() {
		// setBackground(Color.white);
		buildImages();
	}
	
	public	void	buildImages() {
		if (m_base == null)
			// m_base = createImage(DIMENSION, DIMENSION);
			try {
				// m_base = ImageIO.read(new File("/home/dan/field1069h.png"));
				// m_base = ImageIO.read(new File("/home/dan/field384h.png"));
				m_base = ImageIO.read(new File("/home/dan/201617-fieldhr-512.png"));
			} catch(IOException e) {
				m_base = createImage(DIMENSION, DIMENSION);
			}
		if (m_tail == null)
			m_tail = createImage(DIMENSION, DIMENSION);
		if (m_onscreen == null)
			m_onscreen = createImage(DIMENSION, DIMENSION);

		if (m_base != null) {
			if (m_tail != null) {
				m_tail.getGraphics().drawImage(m_base,  0,  0,  null);
				if (m_onscreen != null)
					m_onscreen.getGraphics().drawImage(m_tail,  0,  0,  null);
			}
		}
	}

	public	void	advance(RobotState rs) {
		if ((m_base == null)||(m_tail == null)||(m_onscreen == null))
			buildImages();
		else {
			Graphics	g;
			
			drawpp(rs.x,rs.y);
			g = m_onscreen.getGraphics();
			drawcurrent(g,rs);
		}
		m_current = rs;
		repaint(mapx(rs.x-1.5*SimRobot.ONE_FOOT), mapy(rs.y+1.5*SimRobot.ONE_FOOT),
				mapx(3.0*SimRobot.ONE_FOOT), mapx(3.0*SimRobot.ONE_FOOT));
		
		ImgLogger.log(m_onscreen);
	}

	public	int	mapx(double xv) {
		return (int)(xv * (double)DIMENSION / SimRobot.GAME_DIMENSION);
	}
	
	public	int	mapy(double yv) {
		return (int)(DIMENSION - yv * (double)DIMENSION / SimRobot.GAME_DIMENSION);
	}

	/*
	 * Draw past points onto the m_tail image, and then onto the onscreen image.
	 * This particular routine places a new point onto the points trail.
	 */
	public	void	drawpp(double x, double y) {
		Graphics g;
		int	sx, sy;

		g = m_tail.getGraphics();
		sx = mapx(x);
		sy = mapy(y);
		g.clipRect(sx-4,sy-4, 8,8);
		g.setColor(Color.lightGray);
		g.drawArc(sx-1,sy-1,3,3,0,360);
		g = m_onscreen.getGraphics();
		g.clipRect(sx-4,sy-4, 8,8);
		g.drawImage(m_tail,  0,  0,  null);
	}

	public	double	d2r(double degrees) {
		return degrees * Math.PI / 180.0;
	}
	
	public	int		r2sx(RobotState rs, double x, double y) {
		return mapx(rs.x+x*Math.sin(d2r(rs.h)) + y*Math.cos(d2r(rs.h)));
	}
	public	int		r2sy(RobotState rs, double x, double y) {
		return mapy(rs.y-x*Math.cos(d2r(rs.h)) + y*Math.sin(d2r(rs.h)));
	}
	
	public void	drawwheel(Graphics g, RobotState rs, double wcx, double wcy) {
		double	hr;
		int		polyx[], polyy[];

		polyx = new int[64];
		polyy = new int[64];

		hr = rs.h * Math.PI / 180.0;
		for(int i=0; i<64; i++) {
			double a = i*Math.PI/32.;
			double	xo =     SimRobot.ONE_INCH*Math.cos(a);
			double	yo = 4.0*SimRobot.ONE_INCH*Math.sin(a);
			polyx[i] = r2sx(rs, xo+wcx, yo+wcy);
			polyy[i] = r2sy(rs, xo+wcx, yo+wcy);
		} g.drawPolygon(polyx, polyy, 64);
	}

	
	public	void	drawtwowheel(Graphics g, RobotState rs) {
		g.clipRect(mapx(rs.x-1.5*SimRobot.ONE_FOOT), mapy(rs.y+1.5*SimRobot.ONE_FOOT),
				mapx(3.0*SimRobot.ONE_FOOT), mapx(3.0*SimRobot.ONE_FOOT));
		g.drawImage(m_tail,  0, 0,  null);
		// Draw the pivot point of the bot
		g.drawArc(mapx(rs.x)-3,mapy(rs.y)-3,7,7,0,360);
		// Draw the base line (wheel-wheel) of the bot
		g.drawLine(r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sx(rs,(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sy(rs,(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0));
	
		int	polyx[], polyy[];
		polyx = new int[64];
		polyy = new int[64];

		// Draw a box shape to outline the overall shape of the robot
		polyx[0] = mapx(rs.x);
		polyy[0] = mapy(rs.y);
		polyx[1] = r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0);
		polyy[1] = r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0);
		polyx[2] = r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),SimRobot.ONE_INCH*16);
		polyy[2] = r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),SimRobot.ONE_INCH*16);
		polyx[3] = r2sx(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),SimRobot.ONE_INCH*16);
		polyy[3] = r2sy(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),SimRobot.ONE_INCH*16);
		polyx[4] = r2sx(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0);
		polyy[4] = r2sy(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0);
		g.drawPolygon(polyx, polyy, 5);
		
		// Left wheel
		drawwheel(g, rs, -SimRobot.WHEEL_BASE/2,0);
		// Right wheel
		drawwheel(g, rs, +SimRobot.WHEEL_BASE/2,0);
	}

	public void	drawmwheel(Graphics g, RobotState rs, double wcx, double wcy) {
		double	hr;
		int		polyx[], polyy[];

		polyx = new int[64];
		polyy = new int[64];

		hr = rs.h * Math.PI / 180.0;
		for(int i=0; i<64; i++) {
			double a = i*Math.PI/32.;
			double	xo =     SimRobot.ONE_INCH*Math.cos(a);
			double	yo = 4.0*SimRobot.ONE_INCH*Math.sin(a);
			polyx[i] = r2sx(rs, xo+wcx, yo+wcy);
			polyy[i] = r2sy(rs, xo+wcx, yo+wcy);
		} g.drawPolygon(polyx, polyy, 64);
	}
	
	
	public	void	drawmwdrive(Graphics g, RobotState rs) {
		g.clipRect(mapx(rs.x-1.5*SimRobot.ONE_FOOT), mapy(rs.y+1.5*SimRobot.ONE_FOOT),
				mapx(3.0*SimRobot.ONE_FOOT), mapx(3.0*SimRobot.ONE_FOOT));
		g.drawImage(m_tail,  0, 0,  null);
		// Draw the pivot point of the bot
		g.drawArc(mapx(rs.x)-3,mapy(rs.y)-3,7,7,0,360);
		// Draw the base line (wheel-wheel) of the bot
		g.drawLine(r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sx(rs,(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0),
				r2sy(rs,(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),0));
	
		int	polyx[], polyy[];
		polyx = new int[64];
		polyy = new int[64];
		
		// Draw a box shape to outline the overall shape of the robot
		polyx[0] = r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		polyy[0] = r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		polyx[1] = r2sx(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH), SimRobot.ONE_INCH*8);
		polyy[1] = r2sy(rs,-(SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH), SimRobot.ONE_INCH*8);
		polyx[2] = r2sx(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH), SimRobot.ONE_INCH*8);
		polyy[2] = r2sy(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH), SimRobot.ONE_INCH*8);
		polyx[3] = r2sx(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		polyy[3] = r2sy(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		polyx[4] = r2sx(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		polyy[4] = r2sy(rs, (SimRobot.WHEEL_BASE/2.-SimRobot.ONE_INCH),-SimRobot.ONE_INCH*8);
		g.drawPolygon(polyx, polyy, 5);
		
		drawmwheel(g, rs, -SimRobot.WHEEL_BASE/2,  SimRobot.WHEEL_BASE/2);
		drawmwheel(g, rs, -SimRobot.WHEEL_BASE/2, -SimRobot.WHEEL_BASE/2);
		drawmwheel(g, rs,  SimRobot.WHEEL_BASE/2,  SimRobot.WHEEL_BASE/2);
		drawmwheel(g, rs,  SimRobot.WHEEL_BASE/2, -SimRobot.WHEEL_BASE/2);
	}

	public	void	drawcurrent(Graphics g, RobotState rs) {
		drawmwdrive(g,rs);
	}
	
	public	void	repaint(Graphics g) {
		// g.drawImage(m_onscreen, 0, 0, null);
		paint(g);
		// g.drawImage(m_onscreen, 0, 0, null);
	}
	
	@Override
	public	void	paint(Graphics g) {
		super.paint(g);
		g.drawImage(m_onscreen, 0, 0, null);
	}
}
