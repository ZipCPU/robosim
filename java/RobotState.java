////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	RobotState.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	To capture the "state" of the robot on the field.  This includes
//		the robots position in x and y, as well as its heading, h.
//
//	x	is measured in feet from the left side of the field.
//	y	is measured in feet from the bottom of the field
//	h	is measured in degrees from 0=going up through 90 = going right,
//		180 = going down, etc--like the degrees of a compass.
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

public class RobotState {
	// X and Y are measured in units of feet, between zero and 12, measured from the bottom
	// left of the field.
	double	x, y,
			// H is the "heading" of the robot measured in degrees.  "Up" is a heading of
			// zero.  Heading rotates clockwise from there.  Hence a 90 degree heading
			// would move to the right.
			h;
	
	public RobotState() {
		x = y = h = 0.0;
	}
	
	static public	double normalize(double angle) {
		if (angle > 180.0) {
			do {
				angle -= 360.0;
			} while(angle > 180.0);
		} else if (angle < -180.0) {
			do {
				angle += 360.0;
			} while(angle < -180.0);
		} return angle;
	}
	
	public void set_heading(double nh) {
		h = normalize(nh);
	}
	
	public RobotState(RobotState rs) {
		x = rs.x;
		y = rs.y;
		h = rs.h;
	}
}
