////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	SPointGoal.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	The Point Goal instructs the robot to turn to and head to a given
//		point on the field.  Robots following this goal will not attempt to
//		follow lines from one point to the next, but rather focus solely on the
//	goal they wish to achieve.
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

public class SPointGoal implements SGoal {
	double m_x, m_y;
	public static final double TC = 0.5;
	
	
	public SPointGoal( double  x, double y) {
		m_x = x;
		m_y = y;
	}
	
	@Override
	public ControlInput run(RobotState rs) {
		double alpha, err;
		
		alpha = Math.atan2(m_y - rs.y, m_x - rs.x);
		alpha = alpha * 180. / Math.PI;
		err = rs.h - alpha;
		
		if(err > 180)	err -= 360;
		if(err < -180)	err += 360;
		
		ControlInput ctrls;
		ctrls = new ControlInput();
	
		System.out.print(String.format(" Err: %7.2f", err));
		if (err > 0) {
			 ctrls.lp = 1;
			 ctrls.rp = 1 - TC * err;
			 
			 if (ctrls.rp < - 0.25)
				 ctrls.rp = - 0.25;
			 
		 } else {
			 ctrls.lp = 1 + TC * err;
			 ctrls.rp = 1;
			 
			 if (ctrls.lp < - 0.25)
				 ctrls.lp = - 0.25;
		 }
		
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
