////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	SPlanController.java
//
// Project:	ROBOSIM, for learning how to control autonomous FTC robots
//
// Purpose:	Implements a simulation robot controller as described by a plan,
//		a.k.a. a series of goals.
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

public class SPlanController implements RController {
	SGoal m_plan[];
	int	  m_goal_id;
	@Override
	public RobotState init(){
		RobotState rs;
		rs = new RobotState();
		rs.x = 0.3;
		rs.y = 1;
		rs.h = 0;
		
		m_plan 	  = new SGoal[15];
		m_plan[0] = new SPointGoal(1,1);
		m_plan[1] = new SPointGoal(1,8.5);
		m_plan[2] = new SPointGoal(1.5,9);
		m_plan[3] = new SPointGoal(6,9);
		m_plan[4] = new SPointGoal(6.2,8);
		m_plan[5] = new SPointGoal(2,8);
		m_plan[6] = new SPointGoal(1.5,6);
		m_plan[7] = new SPointGoal(3,6);
		m_plan[8] = new SPointGoal(5.6,6);
		m_plan[9] = new SPointGoal(6,5);
		m_plan[10] = new SPointGoal(6,5);
		m_plan[11] = new SPointGoal(2.5,5);
		m_plan[12] = new SPointGoal(2.0,4);
		m_plan[13] = new SPointGoal(1.5,1);
		m_plan[14] = new SFinalGoal();
		
		return rs;
	}
	
	

	@Override
	public ControlInput run(RobotState rs) {
		ControlInput	ci;
		System.out.print("  Goal: " + m_goal_id);
		if ((m_goal_id < 0)||(m_goal_id >= m_plan.length))
			return null;
		ci = m_plan[m_goal_id].run(rs);
		if (m_plan[m_goal_id].done(rs))
				m_goal_id++;;
		return ci;
	}

}
