////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	RController.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:	This defines an interface that any student robot controller can
//		inherit from.  The controller is allowed to specify where the robot
//	will be placed on the field initially.  Then, during the active portion
//	of the simulation, the controller is given the robot state and queried for
//	a control input.  A null control input ends the simulation.
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

public interface RController {
	RobotState init();
	ControlInput run(RobotState rs);
}
