package com.cresco.HealthMate;

import android.content.Context;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import harmony.java.awt.Color;

public class Txn_PDF
{
	public final static String TAG = "Txn_PDF";

	// defining column widths in percentage
	public final static int PERC_DATE		    = 8;
	public final static int PERC_TIME		    = 14;
    public final static int PERC_DYSTOLIC		= 14;
	public final static int PERC_SYSTOLIC	    = 25;
	public final static int PERC_PULSE		    = 14;
	public final static int PERC_POSITION		= 14;
	public final static int PERC_LOCATION	    = 25;
    public final static int PERC_REMARK		    = 14;

	public final static int NUM_COLS	        = 15;

	public final static float[] PERC_ARRAY	= new float[] {	PERC_DATE, 	PDFPrinter.PERC_COL_PADDING,
            PERC_TIME,  	PDFPrinter.PERC_COL_PADDING,
            PERC_DYSTOLIC,  PDFPrinter.PERC_COL_PADDING,
            PERC_SYSTOLIC, 	PDFPrinter.PERC_COL_PADDING,
            PERC_PULSE, 	PDFPrinter.PERC_COL_PADDING,
            PERC_POSITION,  PDFPrinter.PERC_COL_PADDING,
            PERC_LOCATION, 	PDFPrinter.PERC_COL_PADDING,
            PERC_REMARK};

	public static final String TAG_COMP_NAME	= "company_name";
	public static final String TAG_FROM_TO		= "from_to";
    public static final String DETAILS	        = "details";
    public static final String REPORT_TS		= "report_ts";
    public static final String HEADING	        = "heading";

	public static final String TAG_TABLE_HEADER	= "table_header";
	public static final String TAG_TABLE_ROWS	= "table_rows";

	Context context;

	//int[] colAligns 	= new int[] {Table.ALIGN_LEFT, -1, Table.ALIGN_CENTER, -1, Table.ALIGN_LEFT, -1, Table.ALIGN_RIGHT, -1, Table.ALIGN_RIGHT, -1, Table.ALIGN_LEFT};

    int[] colAligns 	= new int[] {Table.ALIGN_CENTER, -1, Table.ALIGN_CENTER, -1, Table.ALIGN_RIGHT, -1, Table.ALIGN_RIGHT, -1, Table.ALIGN_RIGHT, -1, Table.ALIGN_LEFT, -1, Table.ALIGN_LEFT, -1, Table.ALIGN_LEFT};

    String[] tableHdrs	= new String[NUM_COLS];
	String[] pageHdrs	= new String[3];


    //private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
    //        Font.BOLD);
    //private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
   //         Font.NORMAL, BaseColor.RED);
    //private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
    //        Font.BOLD);
   // private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
    //        Font.NORMAL);
    //private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 14,
     //       Font.NORMAL);
	public Txn_PDF(Context context)
	{
		this.context = context;
	}

	/**
	 * function used to get column widths according to the page size. 
	 * uses the defined column percentages
	 * 
	 * @return colWidths float array containing the sizes of the columns
	 */
	public float[] getColWidths()
	{
		float colWidths[] = new float[PERC_ARRAY.length];

		for(int i=0 ; i<colWidths.length ; i++)
		{
			colWidths[i] = PERC_ARRAY[i] * new PDFPrinter().getDocWidth();
		}

		return colWidths;
	}

	public void setTableHdrs(JSONObject headerObj) 
	{
		tableHdrs = new String[NUM_COLS];

		for(int i=0 ; i<NUM_COLS ; i++)
		{
			try 
			{
				tableHdrs[i] = headerObj.getString("header"+i);
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public PdfPTable getTableHeader() throws DocumentException
	{
		PdfPTable table = new PdfPTable(NUM_COLS);
		table.setHorizontalAlignment(Table.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.setWidths(getColWidths());
		table.setSpacingBefore(5.0f);
		table.setSpacingAfter(5.0f);

		PdfPCell cell = new PdfPCell();
		cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(PDFPrinter.HEIGHT_TABLE_HEADER);

		Font headerFont = PDFPrinter.getTableHeaderFont(context);		

		for(int i=0 ; i<tableHdrs.length ; i++)
		{	
			cell.setPhrase(new Phrase(tableHdrs[i], headerFont));
			cell.setHorizontalAlignment(colAligns[i]);		
			table.addCell(cell);
		}

		return table;
	}

	public void printData(JSONObject printData, String fileName) throws DocumentException, JSONException, IOException
	{
		//Log.v(TAG, "Before creating PDF");

		PDFPrinter pdfPrinter = new PDFPrinter();
		PdfWriter pdfWriter = pdfPrinter.getPrintDocument(fileName);
		pdfWriter.setPageEvent(MyPEH);
		Document doc = pdfPrinter.getPrintDoc();

		setTableHdrs(printData.getJSONObject(TAG_TABLE_HEADER));

		pageHdrs = new String[7];
		pageHdrs[0]	= printData.getString(TAG_COMP_NAME);

		//Log.v(TAG, "Company name: "+ pageHdrs[0]);
		pageHdrs[1]	= printData.getString(TAG_FROM_TO);
        pageHdrs[2]	= " ";
        pageHdrs[3]	= printData.getString(DETAILS);
        pageHdrs[4]	= printData.getString(REPORT_TS);
        pageHdrs[5]	= " ";
        pageHdrs[6]	= printData.getString(HEADING);
		//Log.v(TAG, "From to name: "+ pageHdrs[1]);

		PdfPTable table = getPageHeader();
		doc.add(table);

		addRowTable(printData, pdfWriter, doc);

		doc.close();

		//pdfPrinter.addPageFooter(fileName, context);

		//Log.v(TAG, "After creating PDF");
	}

	public PdfPTable getPageHeader() throws JSONException, DocumentException
	{
		PdfPTable table = new PdfPTable(16);
		table.setWidthPercentage(100);

		PdfPCell cell = new PdfPCell(new Phrase(pageHdrs[0], PDFPrinter.getCompFont(context)));
		//Log.v(TAG, "Comp name: "+ pageHdrs[0]);
		cell.setColspan(16);
		cell.setBorderColor(Color.WHITE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		cell.setFixedHeight(PDFPrinter.HEIGHT_COMP_NAME);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase(pageHdrs[1], PDFPrinter.getFromToFont(context)));
		cell.setColspan(16);
		cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
		cell.setBorderColor(Color.WHITE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		table.addCell(cell);

		cell.setPhrase(new Phrase(pageHdrs[2], PDFPrinter.getFromToFont(context)));
		cell.setColspan(16);
		cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
		cell.setBorderColor(Color.WHITE);
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
		table.addCell(cell);

        cell.setPhrase(new Phrase(pageHdrs[3], PDFPrinter.getFromToFont(context)));
        cell.setColspan(16);
        cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
        cell.setBorderColor(Color.WHITE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase(pageHdrs[4], PDFPrinter.getFromToFont(context)));
        cell.setColspan(16);
        cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
        cell.setBorderColor(Color.WHITE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase(pageHdrs[5], PDFPrinter.getFromToFont(context)));
        cell.setColspan(16);
        cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
        cell.setBorderColor(Color.WHITE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase(pageHdrs[6], PDFPrinter.getFromToFont(context)));
        cell.setColspan(16);
        cell.setFixedHeight(PDFPrinter.HEIGHT_FROM_TO);
        cell.setBorderColor(Color.WHITE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        table.addCell(cell);

		table.setSpacingAfter(10.0f);

		return table;
	}

	// function to add rows to our reports	 
	public void addRowTable(JSONObject jsonObject, PdfWriter pdfWriter, Document doc) throws DocumentException, JSONException
	{
		PdfPTable table = getTableHeader();

		/************************************ TABLE HEADER ************************************/
		doc.add(table);

		/************************************ TABLE ROWS ************************************/

		PdfPCell cell = new PdfPCell(), tempCell = new PdfPCell();
		
		cell.setBorder(Cell.NO_BORDER);
		
		tempCell.setFixedHeight(11.0f);
		tempCell.setBorder(Cell.NO_BORDER);

		JSONArray rows = jsonObject.getJSONArray(TAG_TABLE_ROWS);
		JSONObject rowObj;

		table = new PdfPTable(NUM_COLS);
		table.setHorizontalAlignment(Table.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.setWidths(getColWidths());

		Font rowFont = PDFPrinter.getRowFont(context,10.0f);
		
		String text;
		
		//int docNo = rows.getJSONObject(0).getInt("col0");
		
		// not adding the last row
		// adding it after the loop
		for(int i=0 ; i< rows.length() - 1 ; i++)
		{			
			rowObj = rows.getJSONObject(i);
						
		//Log.v(TAG, "current row as JSON: "+ rowObj);
			
		// adding space after every two rows
			//if(rowObj.getInt("col0") != docNo)
			//{
				//docNo = rowObj.getInt("col0");
				
				for(int j=0 ; j<NUM_COLS ; j++)
				{
					tempCell.setPhrase(new Phrase(""));
					table.addCell(tempCell);
				}
			//}

			for(int j=0 ; j<NUM_COLS ; j++)
			{
				text = rowObj.get("col"+j).toString();

				if(text == null || text.equals("null"))
				{
					text = "";
				}
				cell.setPhrase(new Phrase(text, rowFont));
				cell.setHorizontalAlignment(colAligns[j]);
				table.addCell(cell);
			}
		}
		
		// adding last row with an extra line

		rowObj = rows.getJSONObject(rows.length() - 1);
		cell.setFixedHeight(PDFPrinter.HEIGHT_TABLE_HEADER);
		cell.setBorder(Rectangle.BOTTOM);
		
		for(int j=0 ; j<NUM_COLS ; j++)
		{
			text = rowObj.get("col"+j).toString();

			if(text == null || text.equals("null"))
			{
				text = "";
			}
			cell.setPhrase(new Phrase(text, rowFont));
			cell.setHorizontalAlignment(colAligns[j]);
			table.addCell(cell);
		}
		
		doc.add(table);
	}
	
	PdfPageEventHelper MyPEH = new PdfPageEventHelper()
	{
		public void onStartPage(PdfWriter pdfWriter, Document doc)
		{
			//Log.v(TAG, "New page !");
			PdfPTable table = null;
			try
			{
				table = getPageHeader();
				doc.add(table);
				table = getTableHeader();
				doc.add(table);
			} 
			catch (DocumentException e) 
			{
				e.printStackTrace();
			} 
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		};
	};
}
