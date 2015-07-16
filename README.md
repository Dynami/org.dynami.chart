# README #
Welcome to org.dynami.chart, the project aims to deliver an open source charting library for Java/SWT.

Currently supported chart style:
* Line
* Bar
* OHLC (open, high, low, close)
Chart styles can be stacked and mixed.

* **Quick summary**
The project architecture takes inspiration from org.swtchart library but is specialized on stacked charts with the same x-axis.

* **Version** 1.0


```
#!java

StackedChart chart = new StackedChart(shell, SWT.NONE, "Stock");
ISeries s1 = chart.getMainChart().attachSeries("price", Type.Ohlc);
ISeries s2 = chart.getMainChart().attachSeries("mavg", Type.Line);
ISeries reg = chart.addNewChart("Indexes", 40).attachSeries("RSI", Type.Line);
```
Sample class is used to pass data to ISeries. Sample accepts one Y value and one or more Y values. In case of multiple Y values you can set which is the most important to be displayed on the left of the chart, whether the first Y value is defaulted.

```
#!java
Bar b = ....;
Sample s = new Sample(++count, b.open, b.high, b.low, b.close).setMain(OHLCSeries.CLOSE);
s1.add(s);
```


### How do I get set up? ###

* **Summary of set up** import source in your project, link eclipse project or add jar in your project classpath
* **Configuration**: runs only with Java 1.8
* **Dependencies**: different version and OS depending library can be applied
* * org.eclipse.swt_3.103.1.v20140903-1938.jar
* * org.eclipse.swt.win32.win32.x86_3.103.1.v20140903-1947.jar
* **Database configuration**: no database configuration is required
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact