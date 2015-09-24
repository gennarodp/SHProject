package graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class LineGraph {

	private JFreeChart energyGraph;
	private XYDataset dataset;
	private XYSeries series;
	private ChartPanel chartPanel;
	private ApplicationFrame chartDisplay;
	
	public LineGraph(String title){
		series = new XYSeries("EnergyData");
		dataset = new XYSeriesCollection(series);
		energyGraph = ChartFactory.createXYLineChart(title, "Time", "Energy", dataset);
		chartPanel = new ChartPanel(energyGraph);
		chartDisplay = new ApplicationFrame("EnergyGraph");
		chartDisplay.getContentPane().add(chartPanel);
		chartDisplay.setVisible(true);
		chartDisplay.setSize(600, 600);
	}
	
	public void addPoint(double x, double y){
		series.add(x, y);
	}
}
