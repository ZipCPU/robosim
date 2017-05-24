////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	SMWPointGoal.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	A control loop designed to send a robot moving towards a point
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

public class SMWPointGoal implements SMWGoal {
	double m_x, m_y;
	public static final double TC = 0.5;
	
	
	public SMWPointGoal(double  x, double y) {
		m_x = x;
		m_y = y;
	}


	@Override
	public MWControlInput run(RobotState rs) {
		double alpha, err;
		
		alpha = Math.atan2(m_y - rs.y, m_x - rs.x);
		alpha = alpha * 180. / Math.PI;
		err = rs.h - alpha;
		
		if(err > 180)	err -= 360;
		if(err < -180)	err += 360;
		
		MWControlInput ctrls;
		ctrls = new MWControlInput();
	
		System.out.print(String.format(" Err: %7.2f", err));
		if (err > 0) {
			 ctrls.flp = 1;
			 ctrls.frp = 1 - TC * err;
			 
			 if (ctrls.frp < - 0.25)
				 ctrls.frp = - 0.25;
			 
		 } else {
			 ctrls.flp = 1 + TC * err;
			 ctrls.frp = 1;
			 
			 if (ctrls.flp < - 0.25)
				 ctrls.flp = - 0.25;
		 }
		
		ctrls.blp = ctrls.flp;
		ctrls.brp = ctrls.frp;
		return ctrls;
	}

	@Override
	public boolean done(RobotState rs) {
		double alpha, err;
		
		alpha = Math.atan2(m_y - rs.y, m_x - rs.x);
		alpha = alpha * 180. / Math.PI;
		err = rs.h - alpha;
		
		if(err > 180)	err -= 360;
		if(err < -180)	err += 360;
		
		
		return (Math.abs(err) > 90);
	}
}
