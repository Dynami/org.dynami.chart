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

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.dynami.chart.ISeries.Type;
import org.dynami.chart.plot.series.OHLCSeries;
import org.dynami.core.utils.CArray;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestStackedChart2 {

	protected Shell shell;
	protected StackedChart chart;
	protected ISeries s1 , s2, reg;
	protected int count = 1;
	
	protected CArray prices = new CArray(10);
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Path path = FileSystems.getDefault().getPath(".\\src_test\\", "FTSEMIB_1M.txt");
			final List<String> data = Files.readAllLines(path);
			
			TestStackedChart2 window = new TestStackedChart2();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				long count = 0;
				@Override
				public void run() {
					try {
						Bar b = parse(data.get(window.count++));
//						System.out.println(b.toString());
						Display.getDefault().asyncExec(()->{
							Sample s = new Sample(++count, b.open, b.high, b.low, b.close).setMain(OHLCSeries.CLOSE);
							window.prices.add(b.close);
							window.s1.add(s);
							window.s2.add(new Sample(count, window.prices.mean()));
							window.reg.add(new Sample(count, window.prices.slope()));
							
							window.chart.getMainChart().adjustRange();
							window.chart.getChart("Indexes").adjustRange();
							window.chart.redraw();
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 3000, 500);
			
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	public static Bar parse(String line) throws Exception {
		String[] splitted = line.split("\t");
		return new Bar(
				df.parse(splitted[0]).getTime(),
				Double.parseDouble(splitted[1].replace(',', '.')),
				Double.parseDouble(splitted[2].replace(',', '.')),
				Double.parseDouble(splitted[3].replace(',', '.')),
				Double.parseDouble(splitted[4].replace(',', '.'))
				);
	}
	
	public static class Bar{
		public final long time;
		public final double open, high, low, close;
		
		public Bar(long time, double open, double high, double low, double close) {
			this.time= time;
			this.open = open;
			this.high = high;
			this.low= low;
			this.close=close;
		}

		@Override
		public String toString() {
			return String.format("%5.2f\t%5.2f\t%5.2f\t%5.2f\t", open, high, low, close);
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.exit(1);
			}
		});
		
		chart = new StackedChart(shell, SWT.NONE, "Stock");
		Font f = getFont("Courier", 7, SWT.NONE);
		chart.setFont(f, true);
		s1 = chart.getMainChart().attachSeries("price", Type.Ohlc);
		s2 = chart.getMainChart().attachSeries("mavg", Type.Line);
		reg = chart.addNewChart("Indexes", 40).attachSeries("RSI", Type.Line);
	}
	
	public static Font getFont(final String name, final int size, final int style){
		return new Font(Display.getCurrent(), new FontData(name, size, style));
	}
}
