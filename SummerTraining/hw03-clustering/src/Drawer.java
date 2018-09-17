import java.util.ArrayList;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Drawer {
    public void drawScatter2D(ArrayList<Point> data, ArrayList<ArrayList<Integer>> result){
        // Create Chart
        XYChart chart = new XYChartBuilder().width(600).height(500).title("DBSCAN").xAxisTitle("X").yAxisTitle("Y").build();
        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setYAxisGroupPosition(0, Styler.YAxisPosition.Right);
        // Series
        for(int i = 0; i < result.size(); i++){
            double[] xData = new double[result.get(i).size()];
            double[] yData = new double[result.get(i).size()];
            for (int j = 0; j < result.get(i).size(); j++) {
                xData[j] = data.get(result.get(i).get(j)).getData()[0];
                yData[j] = data.get(result.get(i).get(j)).getData()[1];
            }
            XYSeries series = chart.addSeries("Cluster" + i, xData, yData);
            series.setMarker(SeriesMarkers.CIRCLE);
        }

        new SwingWrapper<XYChart>(chart).displayChart();
    }
}
