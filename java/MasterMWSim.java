////////////////////////////////////////////////////////////////////////////////
//
// Filename: 	MasterMWSim.java
//
// Project:	ROBOSIM, for learning how to control autonomoous FTC robots
//
// Purpose:		This is the master simulator (i.e. main program) for a mechanum
//				wheel based robot.  This component therefore connects other
//	components together
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
import java.applet.*;
import java.awt.*;

public class MasterMWSim extends Applet implements Runnable {
	Frame			m_win;
	boolean			m_done;
	RobotState		m_state;
	RMWController	m_controller;
	SimMWRobot		m_robot;
	RoboDisplay		m_display;
	long			m_count;
	
	private static final long serialVersionUID = 1;
	
	public	MasterMWSim(RMWController ctrl) {
		setLayout(new BorderLayout());
		m_win = null;
		m_done = false;
		m_controller = ctrl;
		m_robot = new SimMWRobot();
		m_display = new RoboDisplay();
	}
	
	public void	init() {
		if (m_win != null)
			m_win.add("Center", m_display);
		else
			this.add("Center", m_display);
	}
	
	public void	run() {
		try {
			m_count = 0;
			m_display.repaint();
			m_state = m_controller.init();
			m_robot.init(m_state);
			while(!m_done) {
				MWControlInput	inputs;
				System.out.print(String.format("Step: %8d, ", m_count));
				System.out.print(String.format("Moving, x= %6.2f y=%6.2f h=%9.4f", m_state.x, m_state.y, m_state.h));
				inputs = m_controller.run(m_state);
				if (inputs == null) {
					System.out.println();
					m_done = true;
				} else {
					System.out.println(String.format(" <<--- < %6.2f , %6.2f, %6.2f, %6.2f >",
							inputs.flp, inputs.frp, inputs.blp, inputs.brp));
					m_state = m_robot.apply(inputs);
					m_display.advance(m_state);

					if(m_robot.error())
						m_done = true;
					Thread.sleep(50);
				}
				m_count++;
			}
		} catch(InterruptedException ie) {
			System.err.println("Interrupt!");
		}
		
		System.out.println("Sim is complete");
		try {
			Thread.sleep(10000);
		} catch(InterruptedException ie) {
			System.err.println("Interrupt");
		}
		System.exit(0);
	}
	
	public	void	stop() {
		m_done = true;
	}
	public static void main(String[] args) {
		RMWController	ctl = new SMWPlanController();
		MasterMWSim	sim = new MasterMWSim(ctl);
		sim.m_win = new Frame("RoboSim");
		sim.init();
		sim.m_win.setSize(RoboDisplay.DIMENSION, RoboDisplay.DIMENSION+45);
		sim.m_win.setVisible(true);
		sim.run();
	}

}
