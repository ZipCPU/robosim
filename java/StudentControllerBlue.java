////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	StudentControllerBlue.java
//
// Project:	ROBOSIM, for learning how to control autonomous FTC robots
//
// Purpose:	StudentControllerBlue is a student built (not mentor built)
//		controller.
//
// Creator:	(A student)
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

public class StudentControllerBlue implements RController {
	public static final double TC = 0.04;
	int m_state = 0;
	
	public RobotState init(){
		RobotState rs;
		rs = new RobotState();
		rs.x = 0.3;
		rs.y = 6;
		rs.h = 0;
		
		return rs;
	}
	private ControlInput maintain_heading(RobotState rs, double  desired_heading, double tracking_const){
		ControlInput ctrls;
		ctrls = new ControlInput();
		
		if (rs.h > desired_heading) {
			 ctrls.lp = 1;
			 ctrls.rp = 1 - tracking_const * (rs.h - desired_heading);
		 } else {
			 ctrls.lp = 1 - tracking_const * (desired_heading - rs.h);
			 ctrls.rp = 1;
		 }
		
		
		return ctrls;
	}
	public ControlInput run(RobotState rs) {
		ControlInput ctrls;
		ctrls = new ControlInput();
		switch (m_state) {
		case 0:
			ctrls.lp = 1;
			ctrls.rp = 1;
			
			if (rs.x > 2.5)
				m_state ++;
			break;
		case 1:
			ctrls.lp = -0.5;
			ctrls.rp = 1;
			
			if ( rs.h > 90)
				m_state ++;
			break;
		 case  2:
			ctrls = maintain_heading (rs, 90, TC);
			 
			if ( rs.y > 9)
				m_state ++;
			break;
		 case 3:
			ctrls.lp = 1;
			ctrls.rp = -1;
			
			if ( rs.h < 5)
				m_state ++;
			break;
		 case 4:
			ctrls = maintain_heading (rs, 0, TC);
			
			if ( rs.x > 4.5)
				m_state ++;
			break;
		 case 5:
			ctrls.lp = -1;
			ctrls.rp = 1;
			
			if ( rs.h > 85)
				m_state ++;
			break;
		 case 6:
			 ctrls = maintain_heading (rs, 90, TC);
			
			if ( rs.y > 10.5)
				m_state ++;
			break;
		 case 7:
			ctrls.lp = -1;
			ctrls.rp = -1;
			
			if ( rs.y < 10)
				m_state ++;
			break;
		 case 8:
		 	ctrls.lp = 1;
			ctrls.rp = -1;
			
			if ( rs.h < 5)
				m_state ++;
			break;
		 case 9:
			 ctrls = maintain_heading (rs, 0, TC);
			
			if ( rs.x > 8)
				m_state ++;
			break;
		 case 10:
			ctrls.lp = -0.5;
			ctrls.rp = 1;
			
			if ( rs.h > 85)
				m_state ++;
			break;
		 case 11:
			 ctrls = maintain_heading (rs, 90, TC);
			 
			if ( rs.y > 10.5)
				m_state ++;
			break;
		 case 12:
			ctrls.lp = -1;
			ctrls.rp = -1;
			
			if ( rs.y < 9)
				m_state ++;
			break;
		 case 13:
			ctrls.lp = -0.5;
			ctrls.rp = 1;
			
			if ( rs.h < 0)
				m_state ++;
			break;
		 case 14:
			ctrls.lp = -0.5;
			ctrls.rp = 1;
			
			if ( rs.h > -140)
				m_state ++;
			break;
		 case 15:
			 ctrls = maintain_heading (rs, -140, TC);
			
			if ( rs.y < 7)
				m_state ++;
			break;
		 case 16:
			ctrls.lp = 1;
			ctrls.rp = -1;
			
			if ( rs.h > 0)
				m_state ++;
			break;
		 case 17:
			ctrls.lp = 1;
			ctrls.rp = -1;
			
			if ( rs.h < 135)
				m_state ++;
			break;
		 case 18:
			 ctrls = maintain_heading (rs, 135, TC);
			 
			if ( rs.y > 10.4)
				m_state ++;
			break;
		default:
		
			ctrls = null;
		}
		
		
		return ctrls;
		
	}

}
