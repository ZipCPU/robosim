////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	StudentController.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	This is my first attempt at building a controller for the velocity
//		vortex FTC competition.  (None of this code was used in the FTC
//		competition, but rather to learn of how a controller might be built.)
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

public class StudentController implements RController {
	int	m_state = 0;
	
	@Override
	public RobotState init() {
		RobotState	s = new RobotState();
		s.x = 7;
		s.y = 0;
		s.h = 90;
		m_state = 0;
		return s;
	}

	@Override
	public ControlInput run(RobotState rs) {
		ControlInput ci = new ControlInput();
		double	limitv;
		switch(m_state) {
		case 0: // Go forward 
				ci.lp = 1.0;
				ci.rp = 1.0;
				if (rs.y > 26.*SimRobot.ONE_INCH)
					m_state = 1;
				break;
		case 1: // Turn to the right
			ci.lp = 1.0;
			ci.rp = 0.25;
			if (rs.h <= 45)
				m_state++;
			break;
		case 2: // Proceed at a 45
			ci.lp = 1.0;
			ci.rp = 1.0;
			if (rs.y >= 3.8*SimRobot.ONE_FOOT)
				m_state++;
			break;
		case 3: // Turn to the right
			ci.lp = 1.0;
			ci.rp = 0.25;
			if (rs.h <= 0)
				m_state++;
			break;
		case 4: // Go press our first button
				limitv = SimRobot.GAME_DIMENSION-18*SimRobot.ONE_INCH;
				if (rs.x < limitv) {
					double d = limitv+SimRobot.ONE_INCH-rs.x;
					ci.lp = 1.0 * d;
					ci.rp = 1.0 * d;
				} else {
					ci.lp = -1;
					ci.rp = -1;
					m_state++;
				} break;
		case 5: // Turn back left -- partway
			ci.lp = -1;
			ci.rp = -0.25;
			if (rs.h >= 70.0)
				m_state++;
			break;
		case 6: // Continue turning back, but now while going forward
			ci.lp = 0.75;
			ci.rp = 1.00;
			if (rs.h >=105.0) // Turn a little past, to give us some more room
				m_state++;
			break;
		case 7: // Continue turning back, but now while going forward
			ci.lp = 1.00;
			ci.rp = 0.75;
			if (rs.h <= 90.0) // Turn a little past, to give us some more room
				m_state++;
			break;
		case 8:
			ci.lp = 1;
			ci.rp = 1;
			if (rs.y > SimRobot.ONE_FOOT*7.6)
				m_state++;
			break;
		case 9:
			ci.lp = 1.0;
			ci.rp = 0.25;
			if (rs.h <= 0)
				m_state++;
			break;
		case 10: // Push the second button
			limitv = SimRobot.GAME_DIMENSION-18*SimRobot.ONE_INCH;
			if (rs.x < limitv) {
				double d = limitv+SimRobot.ONE_INCH-rs.x;
				ci.lp = 1.0 * d;
				ci.rp = 1.0 * d;
			} else {
				ci.lp = -1;
				ci.rp = -1;
				m_state++;
			} break;
		case 11: // Pull back some
			ci.lp = -0.;
			ci.rp = -1.;
			if (rs.h <= -60)
				m_state++;
			break;
		case 12: // Rotate in place the rest
			ci.lp =  1.;
			ci.rp = -1.;
			if (rs.h <= -120)
				m_state++;
			break;
		case 13: // Rotate a touch to get us back on track
			ci.lp = 1.;
			ci.rp = 0.8;
			if ((rs.y < 6.8 * SimRobot.ONE_FOOT)||(rs.h <= -130))
				m_state++;
			break;
		case 14: // Go until we touch the cap-ball
			ci.lp = 1.;
			ci.rp = 1.;
			if (rs.y < 7 * SimRobot.ONE_FOOT)
				m_state++;
			break;
		case 15: // Rotate to knock off the cap ball
			ci.lp = -1.;
			ci.rp =  1.;
			if (rs.h > -65.0)
				m_state++;
			break;
		case 16: // Rotate back to our proper direction
			ci.lp = 1.;
			ci.rp = -1.;
			if (rs.h <= -120)
				m_state++;
			break;
		case 17: // Go until we are on the platform
			ci.lp =  1.;
			ci.rp =  1.;
			if (rs.y < 6.0 * SimRobot.ONE_FOOT)
				m_state++;
			break;
		default: // park
			ci.lp = ci.rp = 0.0;
			return null;
		}
		return ci;
	}

}
