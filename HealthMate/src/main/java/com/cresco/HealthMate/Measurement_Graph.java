package com.cresco.HealthMate;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.LineChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.cresco.HealthMate.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class Measurement_Graph extends TitleBar_Activity 
{
	
	private static final int SERIES_NR = 2;
	int[] diastoliclist , systoliclist, pulse_list, fasting_list, pp_list;
	int size;
	private String[] mMonth;
	String tabclicked;
	
	LinearLayout linearChart;
	private GraphicalView mChart;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.graph);
		
		context = this;
		
		linearChart = (LinearLayout) findViewById(R.id.linearChart);
	
		size = Integer.parseInt(getIntent().getStringExtra(MeasurementList_Profile.SIZE));
		tabclicked = getIntent().getStringExtra(MeasurementList_Profile.TAB_CLICKED);
		
		if(tabclicked.equals(MeasurementList_Profile.PRESSURE))
		{
			diastoliclist = new int[size];
			systoliclist = new int[size];
			pulse_list = new int[size];
			diastoliclist = getIntent().getIntArrayExtra(MeasurementList_Profile.DIASTOLIC_LIST);
			systoliclist = getIntent().getIntArrayExtra(MeasurementList_Profile.SYSTOLIC_LIST);
			pulse_list = getIntent().getIntArrayExtra(MeasurementList_Profile.PULSE_LIST);
			mMonth = getIntent().getStringArrayExtra(MeasurementList_Profile.DATE_LIST);
		}
		else if(tabclicked.equals(MeasurementList_Profile.SUGAR))
		{
			fasting_list = new int[size];
			pp_list = new int[size];
			fasting_list = getIntent().getIntArrayExtra(MeasurementList_Profile.FASTING_LIST);
			pp_list = getIntent().getIntArrayExtra(MeasurementList_Profile.PP_LIST);
			mMonth = getIntent().getStringArrayExtra(MeasurementList_Profile.DATE_LIST);
	    }
		
		openChart();
		
	}
	
	
	private void openChart()
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		XYSeries diasSeries = new XYSeries("Diastolic");
	    XYSeries sysSeries = new XYSeries("Systolic");
	    XYSeries pulseSeries = new XYSeries("Pulse Rate");
	    
	    /*XYSeries l1 = new XYSeries("");
	    XYSeries l2 = new XYSeries("");
	    XYSeries l3 = new XYSeries("");*/
	    
	    XYSeries fastingSeries = new XYSeries("Sugar - Fasting");
	    XYSeries ppSeries = new XYSeries("Sugar - PP");
	    
	    if(tabclicked.equals(MeasurementList_Profile.PRESSURE))
	    {
		    for(int i=0;i<size;i++)
		    {
		    	diasSeries.add(i,diastoliclist[i]);
		    	sysSeries.add(i,systoliclist[i]);
		    	pulseSeries.add(i,pulse_list[i]);
		    }

		    
		    //////////////////
		   /* for (int i = 0, j = 0; i < size; i++, j+= 2) 
		    {
		    	diasSeries.add(j, diastoliclist[i]);
		    	sysSeries.add(j + 0.65, systoliclist[i]);
		    	pulseSeries.add(j + 1.3,pulse_list[i]);
		        
		    }
		    for (int i = 0, j = 0; i <= size; i++, j+= 2) 
		    {
		    	if(i== size)
		    	{
		    		j-=1;
		    		l1.add(j,80);
		            l2.add(j,120);
		            l3.add(j,72);
		    	}
		    	else
		    	{
		    		l1.add(j-0.35,80);
		    		l2.add(j+0.35,120);
		    		l3.add(j,72);
		    	}
		        
		    }
		    ///////////////////*/
		    
		    dataset.addSeries(diasSeries);
		    dataset.addSeries(sysSeries);
		    dataset.addSeries(pulseSeries);
		    /*dataset.addSeries(l1);
		    dataset.addSeries(l2);
		    dataset.addSeries(l3);*/
	    }
	    else if(tabclicked.equals(MeasurementList_Profile.SUGAR))
	    {
	    	for(int i=0;i<size;i++)
		    {
	    		fastingSeries.add(i,fasting_list[i]);
	    		ppSeries.add(i,pp_list[i]);
		    }
		    
		    dataset.addSeries(fastingSeries);
		    dataset.addSeries(ppSeries);
	    }
	    
	    
	    XYSeriesRenderer diasRenderer = new XYSeriesRenderer();
	    
	    diasRenderer.setColor(Color.RED);
	    diasRenderer.setFillPoints(true);
	    diasRenderer.setDisplayChartValues(true);
	    diasRenderer.setChartValuesSpacing(5.0f);
	    diasRenderer.setChartValuesTextSize(10);
	    
	    
	    XYSeriesRenderer sysRenderer = new XYSeriesRenderer();
	    
	    sysRenderer.setColor(Color.BLUE);
	    sysRenderer.setFillPoints(true);
	    sysRenderer.setDisplayChartValues(true);
	    sysRenderer.setChartValuesSpacing(5.0f);
	    sysRenderer.setChartValuesTextSize(10);
	    
	    XYSeriesRenderer pulseRenderer = new XYSeriesRenderer();
	    
	    pulseRenderer.setColor(Color.GREEN);
	    pulseRenderer.setFillPoints(true);
	    pulseRenderer.setDisplayChartValues(true);
	    pulseRenderer.setChartValuesSpacing(5.0f);
	    pulseRenderer.setChartValuesTextSize(10);
	    
	    XYSeriesRenderer fastingRenderer = new XYSeriesRenderer();
	    
	    fastingRenderer.setColor(Color.MAGENTA);
	    fastingRenderer.setFillPoints(true);
	    fastingRenderer.setDisplayChartValues(true);
	    fastingRenderer.setChartValuesSpacing(5.0f);
	    fastingRenderer.setChartValuesTextSize(10);
	    
	    XYSeriesRenderer ppRenderer = new XYSeriesRenderer();
	    
	    ppRenderer.setColor(Color.CYAN);
	    ppRenderer.setFillPoints(true);
	    ppRenderer.setDisplayChartValues(true);
	    ppRenderer.setChartValuesSpacing(5.0f);
	    ppRenderer.setChartValuesTextSize(10);
	    
	   /* XYSeriesRenderer l1r = new XYSeriesRenderer();
	    
	    l1r.setColor(Color.MAGENTA);
	    l1r.setFillPoints(true);
	    l1r.setDisplayChartValues(true);
	    l1r.setChartValuesSpacing(5.0f);
	    l1r.setChartValuesTextSize(15);
	    
	    XYSeriesRenderer l2r = new XYSeriesRenderer();
	    
	    l2r.setColor(Color.CYAN);
	    l2r.setFillPoints(true);
	    l2r.setDisplayChartValues(true);
	    l2r.setChartValuesSpacing(5.0f);
	    l2r.setChartValuesTextSize(15);
	    
	    XYSeriesRenderer l3r = new XYSeriesRenderer();
	    
	    l3r.setColor(Color.CYAN);
	    l3r.setFillPoints(true);
	    l3r.setDisplayChartValues(true);
	    l3r.setChartValuesSpacing(5.0f);
	    l3r.setChartValuesTextSize(15);*/
	    
	    
	    
	    XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	    
	    if(tabclicked.equals(MeasurementList_Profile.PRESSURE))
	    {
	    	multiRenderer.setChartTitle("Blood Pressure Measurement");
	    }
	    else
	    {
	    	multiRenderer.setChartTitle("Blood Sugar Measurement");
	    }
	    multiRenderer.setAxesColor(getResources().getColor(R.color.healthmate_green));
	    multiRenderer.setApplyBackgroundColor(true);
	    multiRenderer.setBackgroundColor(Color.WHITE);
	    multiRenderer.setMarginsColor(Color.WHITE);
	    multiRenderer.setAxisTitleTextSize(16);
	    multiRenderer.setChartTitleTextSize(20);
	    multiRenderer.setLabelsTextSize(15);
	    multiRenderer.setLegendTextSize(15);
	    //multiRenderer.setBarSpacing(2); 
	    multiRenderer.setShowGrid(true);
	    multiRenderer.setBarWidth(10);
	    multiRenderer.setXAxisMin(-0.5);
	    multiRenderer.setYAxisMin(-0.1);
	    
	    //multiRenderer.setRange(range)
	    //multiRenderer.setFitLegend(true);
	    
	    multiRenderer.setXLabels(0);
	    multiRenderer.setXTitle("Dates");
	   // multiRenderer.setc
	    
	    
	    multiRenderer.setXLabelsColor(getResources().getColor(R.color.healthmate_green));
	    
	    multiRenderer.setYTitle("Range");
	    multiRenderer.setYLabelsColor(0, getResources().getColor(R.color.healthmate_green));
	    multiRenderer.setYLabelsAlign(Align.RIGHT);
	    multiRenderer.setYLabelsPadding(5.0f);
	    
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/DistProTh.otf");
	    multiRenderer.setTextTypeface(typeface);
	    
	    
	    for(int i=0; i< size;i++)
	    {
	    	multiRenderer.addXTextLabel(i , mMonth[i]);
	    }
	    
	    if(tabclicked.equals(MeasurementList_Profile.PRESSURE))
	    {
	    	multiRenderer.addSeriesRenderer(diasRenderer);
	    	multiRenderer.addSeriesRenderer(sysRenderer);
	    	multiRenderer.addSeriesRenderer(pulseRenderer);
	    }
	    else
	    {
	    	multiRenderer.addSeriesRenderer(fastingRenderer);
	    	multiRenderer.addSeriesRenderer(ppRenderer);
	    }
	    
	    /*for (int i = 0, j = 1; i < size; i++, j += 2) 
	    {
	        multiRenderer.addXTextLabel(j - 0.69, mMonth[i]);
	    }
	    
	    multiRenderer.addSeriesRenderer(diasRenderer);
    	multiRenderer.addSeriesRenderer(sysRenderer);
    	multiRenderer.addSeriesRenderer(pulseRenderer);
    	multiRenderer.addSeriesRenderer(l1r);
    	multiRenderer.addSeriesRenderer(l2r);
    	multiRenderer.addSeriesRenderer(l3r);*/
	    
	      View view = ChartFactory.getBarChartView(this, dataset, multiRenderer, Type.DEFAULT);
    
    	//setContentView(view);
    	
    	linearChart.addView(view);
    	
    	//String[] types = new String[] {BarChart.TYPE, BarChart.TYPE ,BarChart.TYPE ,LineChart.TYPE, LineChart.TYPE, LineChart.TYPE};
    	//mChart = (GraphicalView) ChartFactory.getCombinedXYChartView(this, dataset, multiRenderer, types);
    
	//setContentView(mChart);
	}

}
