/*
 * Copyright 2014 Alessandro Atria
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dynami.chart;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.dynami.chart.ISeries.Type;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TestStackedChart {

	protected Shell shell;
	protected StackedChart chartWidget;
	protected ISeries s1, s2, s3, s4, s5;
	protected double count = .1;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestStackedChart window = new TestStackedChart();
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				int count = 1;
				final Calendar cal = Calendar.getInstance();
				
				@Override
				public void run() {
					Display.getDefault().asyncExec(()->{
						double val1 = Math.sin(window.count+=.05)*30;
						double val2 = Math.cos(window.count)*25;
						count++;
						cal.add(Calendar.DAY_OF_MONTH, 1);
						window.s1.add(new Sample(cal.getTimeInMillis(), val1));
						window.s2.add(new Sample(cal.getTimeInMillis(), val2));
						if(count%2==0){
							window.s3.add(new Sample(cal.getTimeInMillis(), Math.random()*15));
						}
						double val4 = Math.sin(window.count+=.05)*90;
						window.s4.add(new Sample(cal.getTimeInMillis(), val4));
						
						window.s5.add(new Sample(cal.getTimeInMillis(), val4));
						
						window.chartWidget.getMainChart().adjustRange();
						window.chartWidget.getChart("CCO").adjustRange();
						window.chartWidget.getChart("CCI").adjustRange();
						window.chartWidget.redraw();
					});
				}
			}, 3000, 100);
			
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.exit(1);
			}
		});
		shell.setSize(450, 384);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		chartWidget = new StackedChart(shell, SWT.NONE, "Sample", true);
		s1 = chartWidget.getMainChart().attachSeries("Prices", Type.Line);
		s2 = chartWidget.getMainChart().attachSeries("Cos", Type.Line);
		s2.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));
		
		s3 = chartWidget.getMainChart().attachSeries("RSI", Type.Line);
		s3.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		
		s4 = chartWidget.addNewChart("CCO", 50).attachSeries("cco", Type.Bar);
		
		s5 = chartWidget.addNewChart("CCI", 50).attachSeries("ccI", Type.Line);
	}
}
