////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	STurnToGoal.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	A goal to have the robot turn to a given heading.  Once it gets
//	within the threshold of the heading, the goal has been accomplished.
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

public class STurnToGoal implements SGoal {
	double m_desired_heading, m_slow_wheel;
	public static final double THRESHHOLD = 5.;
	
	public STurnToGoal( double  desired_heading, double radius) {
		m_desired_heading = desired_heading;
		m_slow_wheel = (2. * radius - SimRobot.WHEEL_BASE) / (2. * radius + SimRobot.WHEEL_BASE);
	}
	@Override
	public ControlInput run(RobotState rs) {
		double err = rs.h - m_desired_heading;
		
		if(err > 180)	err -= 360;
		if(err < -180)	err += 360;
		
		ControlInput ctrls;
		ctrls = new ControlInput();
		
		if (err > 0) {
			 ctrls.lp = 1;
			 ctrls.rp = m_slow_wheel;
		 } else {
			 ctrls.lp = m_slow_wheel;
			 ctrls.rp = 1;
		 }
		
		return ctrls;
	
	}

	@Override
	public boolean done(RobotState rs) {
		double err = rs.h - m_desired_heading;
		
		if(err > 180)	err -= 360;
		if(err < -180)	err += 360;
		
		return (Math.abs(err) < THRESHHOLD);
	}

}
