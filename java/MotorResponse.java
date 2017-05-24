////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	MotorResponse.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	Few motors have a truly linear response.  The robot motors are no
//		different.  The response is often an S-curve.  Here we capture that
//		non-linearity.
//
//	Rather than implementing a proper "S" shaped curve, we approximate that
//	curve as a set of lines.
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

public class MotorResponse {
	public	double motormap(double v) {
		if (v > 0.0) {
			v = (v - 0.2) * 1.0 / 0.6;
			if (v < 0.0)
				v = 0.0;
			else if (v > 1.0)
				v = 1.0;
		} else {
			v = (-v + 0.2) * 1.0 / 0.6;
			if (v > 0.0)
				v = 0.0;
			else if (v < -1.0)
				v = -1.0;
		} return v;
	}
	
	public ControlInput map(ControlInput ci) {
		ci.lp = motormap(ci.lp);
		ci.rp = motormap(ci.rp);
		
		return ci;
	}
	public MWControlInput map(MWControlInput ci) {
		ci.flp = motormap(ci.flp);
		ci.frp = motormap(ci.frp);
		ci.blp = motormap(ci.blp);
		ci.brp = motormap(ci.brp);
		
		return ci;
	}
}
