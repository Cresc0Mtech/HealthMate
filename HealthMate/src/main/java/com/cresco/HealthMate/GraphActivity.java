package com.cresco.HealthMate;

 
import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.cresco.HealthMate.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
 
public class GraphActivity extends TitleBar_Activity 
{
	private static final int SERIES_NR = 2;
	int[] lowlist ,highlist;
	int size;
	private String[] mMonth;
	
	LinearLayout linearChart;
	private GraphicalView mChart;
	Context context;
 
@Override
protected void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.graph);
	
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	context = this;
	
	size = Integer.parseInt(getIntent().getStringExtra(MainActivity.SIZE));
	
	lowlist = new int[size];
	highlist = new int[size];
	lowlist = getIntent().getIntArrayExtra(MainActivity.LOW_LIST);
	highlist = getIntent().getIntArrayExtra(MainActivity.HIGH_LIST);
	mMonth = getIntent().getStringArrayExtra(MainActivity.DATE_LIST);
	
	openChart();
	
	//XYMultipleSeriesRenderer renderer = getTruitonBarRenderer();
	//myChartSettings(renderer);
	//Intent intent = ChartFactory.getBarChartIntent(this, getTruitonBarDataset(), renderer, Type.DEFAULT);
	//startActivity(intent);
}

private void openChart()
{
	
	XYSeries lowSeries = new XYSeries("Low");
    XYSeries highSeries = new XYSeries("High");
  
   //////////////
    XYSeries l1 = new XYSeries("");
    XYSeries l2 = new XYSeries("");
    ////////////
    /*for(int i=0;i<size;i++)
    {
    	lowSeries.add(i,lowlist[i]);
        highSeries.add(i,highlist[i]);
        
        l1.add(i,80);
        l2.add(i,120);
    }*/
    for (int i = 0, j = 0; i < size; i++, j+= 2) 
    {
        lowSeries.add(j, lowlist[i]);
        highSeries.add(j + 0.65, highlist[i]);
       // l1.add(j-0.3,80);
        //l2.add(j+0.3,120);
        
    }
    for (int i = 0, j = 0; i <= size; i++, j+= 2) 
    {
    	if(i== size)
    	{
    		j-=1;
    		l1.add(j,80);
            l2.add(j,120);
    	}
    	else
    	{
    		l1.add(j-0.35,80);
    		l2.add(j+0.35,120);
    	}
        
    }
    
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    
    ///****************
    
   /* 
    
    // Color of each Pie Chart Sections
    int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                    Color.YELLOW };

    // Instantiating CategorySeries to plot Pie Chart
    CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
    for(int i=0 ;i < size;i++){
        // Adding a slice with its values and name to the Pie Chart
        distributionSeries.add(mMonth[i], lowlist[i]);
    }

    // Instantiating a renderer for the Pie Chart
    DefaultRenderer defaultRenderer  = new DefaultRenderer();
    for(int i = 0 ;i<size;i++)
    {
        SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
        seriesRenderer.setColor(colors[i]);
        seriesRenderer.setDisplayChartValues(true);
        // Adding a renderer for a slice
        defaultRenderer.addSeriesRenderer(seriesRenderer);
    }

    defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
    defaultRenderer.setChartTitleTextSize(20);
    defaultRenderer.setZoomButtonsVisible(true);*/
    
    //*********//
    
    ////////////////////
   /*
     XYSeries serieslow[] = new XYSeries[size];
    XYSeries serieshigh[] = new XYSeries[size];
    XYSeriesRenderer[] Rendererlow = new XYSeriesRenderer[size];
    XYSeriesRenderer[] Rendererhigh = new XYSeriesRenderer[size];
    
    for(int i=0;i<size;i++)
    {
    	serieslow[i] = new XYSeries(i+"serieslow");
    	Rendererlow[i] = new XYSeriesRenderer();
    	serieslow[i].add(i,lowlist[i]);
    	
    	serieshigh[i] = new XYSeries(i+"serieshigh");
    	Rendererhigh[i] = new XYSeriesRenderer();
    	serieshigh[i].add(i,highlist[i]);
    	
    	
    	if(lowlist[i] > 85 || lowlist[i] < 75)
    	{
    		Rendererlow[i].setColor(Color.RED);
    	}
    	else
    	{
    		Rendererlow[i].setColor(Color.CYAN);
    	}
    	
    	if(highlist[i] > 125 || highlist[i] < 115)
    	{
    		Rendererhigh[i].setColor(Color.RED);
    	}
    	else
    	{
    		Rendererhigh[i].setColor(Color.MAGENTA);
    	}
    	
    	Rendererlow[i].setFillPoints(true);
    	Rendererlow[i].setLineWidth(2);
    	Rendererlow[i].setDisplayChartValues(true);
    	Rendererlow[i].setChartValuesSpacing(10.0f);
    	Rendererlow[i].setChartValuesTextSize(15);
    	
    	Rendererhigh[i].setFillPoints(true);
    	Rendererhigh[i].setLineWidth(2);
    	Rendererhigh[i].setDisplayChartValues(true);
    	Rendererhigh[i].setChartValuesSpacing(10.0f);
    	Rendererhigh[i].setChartValuesTextSize(15);
    }
    
    for(int i=0;i<size;i++)
    {
    	dataset.addSeries(serieslow[i]);
    	dataset.addSeries(serieshigh[i]);
    }*/
    
    //////////////////////
    
    dataset.addSeries(lowSeries);
    dataset.addSeries(highSeries);
    dataset.addSeries(l1);
    dataset.addSeries(l2);
    
    XYSeriesRenderer lowRenderer = new XYSeriesRenderer();
   
    lowRenderer.setColor(Color.CYAN);
    lowRenderer.setFillPoints(true);
    //lowRenderer.setLineWidth(1);
    lowRenderer.setDisplayChartValues(true);
    //lowRenderer.setChartValuesSpacing(10.0f);
    lowRenderer.setChartValuesTextSize(15);
    //lowRenderer.setPointStyle(PointStyle.TRIANGLE);
    
    XYSeriesRenderer highRenderer = new XYSeriesRenderer();
    highRenderer.setColor(Color.MAGENTA);
    highRenderer.setFillPoints(true);
    //highRenderer.setLineWidth(1);
    highRenderer.setDisplayChartValues(true);
    //highRenderer.setChartValuesSpacing(10.0f);
    highRenderer.setChartValuesTextSize(15);
    //highRenderer.setPointStyle(PointStyle.DIAMOND);
    
    XYSeriesRenderer l1Renderer = new XYSeriesRenderer();
    l1Renderer.setColor(Color.RED);
    l1Renderer.setFillPoints(true);
    //l1Renderer.setLineWidth(1);
    //l1Renderer.setChartValuesSpacing(10.0f);
    
    XYSeriesRenderer l2Renderer = new XYSeriesRenderer();
    l2Renderer.setColor(Color.RED);
    l2Renderer.setFillPoints(true);
    //l2Renderer.setLineWidth(1);
    //l2Renderer.setChartValuesSpacing(10.0f);
    
    
    XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
    
    multiRenderer.setChartTitle("Blood Pressure Measures");
    multiRenderer.setAxesColor(Color.rgb(0, 204, 0));
    //multiRenderer.setZoomButtonsVisible(true);
    multiRenderer.setApplyBackgroundColor(true);
    multiRenderer.setBackgroundColor(Color.WHITE);
    multiRenderer.setMarginsColor(Color.WHITE);
    multiRenderer.setAxisTitleTextSize(16);
    multiRenderer.setChartTitleTextSize(20);
    multiRenderer.setLabelsTextSize(15);
    multiRenderer.setLegendTextSize(20);
    multiRenderer.setBarSpacing(2); 
    multiRenderer.setShowGrid(true);
    //multiRenderer.setGridColor(Color.GRAY);
    //multiRenderer.setXAxisMin(0, 1);
    //multiRenderer.setXAxisMax(100, 1);
    
    
    multiRenderer.setXLabels(0);
    multiRenderer.setXTitle("Dates");
    multiRenderer.setXLabelsColor(Color.BLUE);
    
    multiRenderer.setYTitle("Range");
    multiRenderer.setYLabelsColor(0, context.getResources().getColor(R.color.healthmate_green));
    multiRenderer.setYLabelsAlign(Align.RIGHT);
    multiRenderer.setYLabelsPadding(5.0f);
   
    
    
    
    /*for(int i=0; i< size;i++)
    {
        //multiRenderer.addXTextLabel(i, mMonth[i]);
    	multiRenderer.addXTextLabel(i , mMonth[i]);
    }*/
    for (int i = 0, j = 1; i < size; i++, j += 2) 
    {
        multiRenderer.addXTextLabel(j - 0.69, mMonth[i]);
    }

    // Adding Renderer to multipleRenderer
    // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
    // should be same

		multiRenderer.addSeriesRenderer(lowRenderer);
    	multiRenderer.addSeriesRenderer(highRenderer);
    	multiRenderer.addSeriesRenderer(l1Renderer);
    	multiRenderer.addSeriesRenderer(l2Renderer);
    /*for(int i=0;i<size;i++)
    {
    	multiRenderer.addSeriesRenderer(Rendererlow[i]);
    	multiRenderer.addSeriesRenderer(Rendererhigh[i]);
    }*/

    // Creating an intent to plot bar chart using dataset and multipleRenderer
    //Intent intent = ChartFactory.getBarChartIntent(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);

    //startActivity(intent);
    //View view = ChartFactory.getBarChartView(this, dataset, multiRenderer, Type.DEFAULT);
   // View view = ChartFactory.getPieChartView(this, distributionSeries , defaultRenderer);
    	String[] types = new String[] {BarChart.TYPE, BarChart.TYPE ,LineChart.TYPE, LineChart.TYPE};
    	mChart = (GraphicalView) ChartFactory.getCombinedXYChartView(this, dataset, multiRenderer, types);
    
	setContentView(mChart);
}



private XYMultipleSeriesDataset getTruitonBarDataset() 
{
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	final int nr = 4;
	Random r = new Random();
	ArrayList<String> legendTitles = new ArrayList<String>();
	legendTitles.add("Sales");
	legendTitles.add("Expenses");
	for (int i = 0; i < SERIES_NR; i++) 
	{
		CategorySeries series = new CategorySeries(legendTitles.get(i));
		for (int k = 0; k < nr; k++) 
		{
			series.add(100 + r.nextInt() % 100);
		}
		dataset.addSeries(series.toXYSeries());
	}
	return dataset;
}
 
public XYMultipleSeriesRenderer getTruitonBarRenderer() 
{
	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	renderer.setAxisTitleTextSize(16);
	renderer.setChartTitleTextSize(20);
	renderer.setLabelsTextSize(15);
	renderer.setLegendTextSize(15);
	renderer.setMargins(new int[] { 30, 40, 15, 0 });
	/*SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	r.setColor(Color.BLUE);
	renderer.addSeriesRenderer(r);
	r = new SimpleSeriesRenderer();
	r.setColor(Color.RED);
	renderer.addSeriesRenderer(r);*/
	
	// Creating XYSeriesRenderer to customize incomeSeries
    XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
    incomeRenderer.setColor(Color.WHITE);
    incomeRenderer.setPointStyle(PointStyle.CIRCLE);
    incomeRenderer.setFillPoints(true);
    incomeRenderer.setLineWidth(2);
    incomeRenderer.setDisplayChartValues(true);

    // Creating XYSeriesRenderer to customize expenseSeries
    XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
    expenseRenderer.setColor(Color.YELLOW);
    expenseRenderer.setPointStyle(PointStyle.CIRCLE);
    expenseRenderer.setFillPoints(true);
    expenseRenderer.setLineWidth(2);
    expenseRenderer.setDisplayChartValues(true);
    
    renderer.addSeriesRenderer(incomeRenderer);
    renderer.addSeriesRenderer(expenseRenderer);
	return renderer;
}
 
private void myChartSettings(XYMultipleSeriesRenderer renderer) 
{
	
	
	renderer.setChartTitle("Truiton's Performance by AChartEngine BarChart");
	renderer.setXAxisMin(0.5);
	renderer.setXAxisMax(10.5);
	renderer.setYAxisMin(0);
	renderer.setYAxisMax(210);
	renderer.addXTextLabel(1, "2010");
	renderer.addXTextLabel(2, "2011");
	renderer.addXTextLabel(3, "2012");
	renderer.addXTextLabel(4, "2013");
	renderer.setYLabelsAlign(Align.RIGHT);
	renderer.setBarSpacing(0.5);
	renderer.setXTitle("Years");
	renderer.setYTitle("Performance");
	renderer.setShowGrid(true);
    renderer.setGridColor(Color.GRAY);
    renderer.setXLabels(0); // sets the number of integer labels to appear
    
    
}
 
}