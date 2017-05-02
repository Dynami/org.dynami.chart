package org.dynami.chart;

import org.dynami.chart.ISeries.Type;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestSeriesBug {

	protected Shell shell;
	private StackedChart chart;
	private ISeries s1, s2;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestSeriesBug window = new TestSeriesBug();
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
		shell.setSize(600, 450);
		shell.setText("SWT Application");
		
		chart = new StackedChart(shell, SWT.NONE, "bug fixing", false);
		s1 = chart.getMainChart().attachSeries("Prices", Type.Line);
		s2 = chart.getMainChart().attachSeries("Cos", Type.Line);
		
		for(int i = 0; i < 200; i++){
			s1.add(new Sample(i+1, 4));
			if(i > 70){
				s2.add(new Sample(i+1, 6));
			}
		}
		
		chart.getMainChart().adjustRange();
		chart.redraw();
	}

}
