////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	SimRobot.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	Provides the physics model, mapping motor power to robot motion
//		and hence pdated position.
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

public class SimRobot {
	RobotState	m_state;
	MotorResponse	m_mr;
	public	static	final	double	ONE_INCH = 1.0 / 12.0;
	public	static	final	double	WHEEL_BASE = 16.0 * ONE_INCH;
	public	static	final	double	ONE_FOOT = 1.0;
	public	static	final	double	GAME_DIMENSION = 12.0 * ONE_FOOT;
	final	double	MAX_DISTANCE_PER_ITERATION = 0.4 * ONE_INCH;
	final double	m_w = WHEEL_BASE;
	
	public SimRobot() {
		m_state = new RobotState();
		m_mr = new MotorResponse();
	}
	
	public void	init(RobotState rs) {
		m_state = rs;
	}
	
	public	RobotState	apply(ControlInput ci) {
		ci = m_mr.map(ci);
		// Clip the control inputs to be within bounds, -1 to 1
		if (Math.abs(ci.lp) > 1.0)
			ci.lp = (ci.lp>0.0) ? 1.0 : -1.0;
		if (Math.abs(ci.rp) > 1.0)
			ci.rp = (ci.rp>0.0) ? 1.0 : -1.0;
		
		double	dl = ci.lp * MAX_DISTANCE_PER_ITERATION;
		double	dr = ci.rp * MAX_DISTANCE_PER_ITERATION;
		
		if ((dl == dr)||(Math.abs((dl+dr)*m_w/(dl-dr))>1000.0*GAME_DIMENSION)) {
			double d = (dl+dr)/2;
			m_state.x += d*Math.cos(m_state.h*Math.PI/180.0);
			m_state.y += d*Math.sin(m_state.h*Math.PI/180.0);
		} else {
			double r = (dl+dr)*m_w/(dl-dr)/2.;
			double	rpx, rpy;
			
			// dl = theta * (r - w/2)
			// dr = theta * (r + w/2)
			// r = (dl + dr) / 2 / theta
			// (dr - dl) = theta * w
			// theta_in_radians = (dr - dl)/w
			
			// Find our point of rotation
			rpx = m_state.x - r*Math.cos((m_state.h+90)*Math.PI/180.0);
			rpy = m_state.y - r*Math.sin((m_state.h+90)*Math.PI/180.0);

			// Find our new heading
			double	nh;
			nh = m_state.h + (dr-dl)/m_w*180/Math.PI;
			// System.out.println("nh="+nh);

			// Our new position
			m_state.x = rpx + r*Math.cos((nh+90)*Math.PI/180.0);
			m_state.y = rpy + r*Math.sin((nh+90)*Math.PI/180.0);
			
			while (nh > 180)
				nh -= 360.;
			while(nh < -180.)
				nh += 360.;
			m_state.h = nh;
		}
		
		return new RobotState(m_state);
	}
	
	public boolean error() {
		if (m_state.x < 0)
			return true;
		if (m_state.y < 0)
			return true;
		if (m_state.x > GAME_DIMENSION)
			return true;
		if (m_state.y > GAME_DIMENSION)
			return true;
		return false;
	}
}
