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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestStackedChart2 {

	protected Shell shell;
	protected StackedChart chart;
	protected ISeries s1;
	protected int count = 1;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Path path = FileSystems.getDefault().getPath(".\\src_test\\", "MIB_H.txt");
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
							window.s1.add(new Sample(++count, b.open, b.high, b.low, b.close).setMain(OHLCSeries.CLOSE));
							window.chart.getMainChart().adjustRange();
							window.chart.redraw();
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 3000, 1000);
			
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
		
		public Bar(long time, double high, double low, double open, double close) {
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
		s1 = chart.getMainChart().attachSeries("price", Type.Ohlc);
	}
}
