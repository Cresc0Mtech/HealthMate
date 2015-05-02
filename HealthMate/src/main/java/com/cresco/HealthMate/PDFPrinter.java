package com.cresco.HealthMate;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;

//import com.cresco.mAccountsFT.M_Accounts;
//import com.cresco.mAccountsFT.Services.DateUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDFPrinter 
{
	private final static String TAG = "PDFPrinter";
	
	public final static int REPORT_TRIAL_BALANCE	= 1;
	public final static int REPORT_BALANCE_SHEET	= 2;
	public final static int REPORT_PROFIT_LOSS		= 3;
	public final static int REPORT_LEDGER_STATEMENT	= 4;
	public final static int REPORT_GROUP_TRIAL_BAL	= 5;
	public final static int REPORT_OPENING_TRIAL_BAL= 6;
	
	public static int PERC_MARGIN_TOP		= 5;
	public static int PERC_MARGIN_LEFT		= 5;
	public static int PERC_MARGIN_RIGHT		= 5;
	public static int PERC_MARGIN_BOTTOM	= 5;
	
	public static float PERC_COL_PADDING	= 1;
	
	public static final float CELL_PADDING_LEFT		= 5.0f;
	public static final float CELL_PADDING_RIGHT	= 5.0f;
	
	public int docWidth		= -1;
	public int docHeight	= -1;
		
	public static final float HEIGHT_COMP_NAME		= 25.0f;
	public static final float HEIGHT_FROM_TO		= 16.0f;
	public static final float HEIGHT_TABLE_ROW 		= 16.0f;
	public static final float HEIGHT_TABLE_HEADER	= 18.0f;
	public static final float HEIGHT_TABLE_XTRA_HDR	= 18.0f;
	
	public static final float TEXT_HT_COMP_NAME	= 18.0f;
	public static final float TEXT_HT_FROM_TO	= 12.0f;
	public static final float TEXT_HT_TABLE_ROW = 10.0f;
	public static final float TEXT_HT_TABLE_HDR	= 10.0f;
	
	public static final String CONFIG_CELL			= "cell_config";
	
	public static final String CONFIG_FONT_SIZE		= "config_font";
	public static final String CONFIG_BORDER		= "config_border";
	public static final String CONFIG_FONT_WEIGHT	= "config_weight";
	public static final String CONFIG_H_ALIGN		= "h_align";
	public static final String CONFIG_V_ALIGN		= "v_align";
	public static final String CONFIG_FIXED_HEIGHT	= "fixed_height";
	
	static Font compFont, fromToFont, tableHeaderFont, rowFont, totalFont, pageFooterFont, xtraHdrFont;
	
	Document doc;
	ByteArrayOutputStream baos;
	//M_Accounts mApp;
	
	/**
	 * 
	 * @return Document on which data is to be printed.
	 * 			 Takes care of margins, page size etc.
	 * @throws java.io.FileNotFoundException
	 * @throws DocumentException 
	 */
	public PdfWriter getPrintDocument(String fileName)
	{
		Document doc = new Document();
		PdfWriter pdfWriter = null;
		//mApp = (M_Accounts)M_Accounts.getInstance();
		
		doc.bottom(80);
		
		Rectangle rect = doc.getPageSize();
		
		int docWidth  = (int) rect.getWidth();
		int docHeight = (int) rect.getHeight();
		
		float marginLeft 	= doc.leftMargin();
		float marginRight 	= doc.rightMargin();
		float marginTop 	= doc.topMargin();
		float marginBottom 	= doc.bottomMargin();
		
		this.docWidth 	= (int) (docWidth - (marginLeft + marginRight));
		this.docHeight	= (int) (docHeight - (marginTop + marginBottom));
		
		try
		{
			File dir = new File(Environment.getExternalStorageDirectory() + "/mAccounts", "");
			
			if(!dir.exists()) dir.mkdirs();
			File file = new File(dir, fileName);

			Log.d("PDFCreator", "PDF Path: " + file.getAbsolutePath());
			baos = new ByteArrayOutputStream();

			pdfWriter = PdfWriter.getInstance(doc, baos);
			this.doc = doc;
			
			doc.open();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		
		return pdfWriter;
	}
	/*
	public static PdfPTable getPageFooterTable(int x, int y, String timeStamp, Context context) 
	{
        PdfPTable table = new PdfPTable(3);
        Font font = getPageFooterFont(context);
        
        table.setTotalWidth(527);
        table.setLockedWidth(true);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setFixedHeight(30);
        table.getDefaultCell().setBorder(Rectangle.TOP);
        table.addCell(new Phrase(timeStamp, font));
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase("Generated from mAccounts", font));
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(new Phrase(String.format("Page %d of %d", x, y), font));
        return table;
    }

	public void addPageFooter(String fileName, Context context) throws IOException, DocumentException
	{
		fileName = Environment.getExternalStorageDirectory() + "/mAccounts/" + fileName;
		// SECOND PASS, ADD THE FOOTER
        // Create a reader
        PdfReader reader = new PdfReader(baos.toByteArray());
        // Create a stamper
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileName));
        // Loop over the pages and add a header to each page
        int n = reader.getNumberOfPages();
        
        String timeStamp = DateUtils.getTimeStamp();
        
        for (int i = 1; i <= n; i++) 
        {
        	getPageFooterTable(i, n, timeStamp, context).writeSelectedRows(0, -1, 36, 36, stamper.getOverContent(i));
        }
        // Close the stamper
        stamper.close();
        reader.close();
	}
	
	public static void createMAccountsDir()
	{
		File file = new File(Environment.getExternalStorageDirectory() + "/mAccounts");
		file.mkdirs();
	}
	
	public static String getFileName(int reportType)
	{
		String fileName = null;
		switch(reportType)
		{
		case REPORT_TRIAL_BALANCE :
			fileName =  "Trial Balance " + DateUtils.getTodaysDate() + ".pdf";
			break;
		case REPORT_BALANCE_SHEET :
			fileName =  "Balance Sheet " + DateUtils.getTodaysDate() + ".pdf";
			break;
		case REPORT_PROFIT_LOSS :
			fileName =  "Profit Loss " + DateUtils.getTodaysDate() + ".pdf";
			break;
		case REPORT_LEDGER_STATEMENT :
			fileName =  "Ledger Statement " + DateUtils.getTodaysDate() + ".pdf";
			break;
		case REPORT_GROUP_TRIAL_BAL :
			fileName =  "Group Trial Balance " + DateUtils.getTodaysDate() + ".pdf";
			break;
		case REPORT_OPENING_TRIAL_BAL :
			fileName =  "Opening Trial Balance " + DateUtils.getTodaysDate() + ".pdf";
			break;
		}
		
		return fileName;
	}
	
	public void drawLine(Document doc, PdfWriter pdfWriter)
	{
		PdfContentByte cb = pdfWriter.getDirectContent();
		cb.moveTo(doc.leftMargin(), pdfWriter.getVerticalPosition(true));
		cb.lineTo(doc.rightMargin(), pdfWriter.getVerticalPosition(true));
	}
	*/
	
	public Document getPrintDoc()
	{
		return this.doc;
	}

	public int getDocWidth() 
	{
		return docWidth;
	}
	
	public int getDocHeight() 
	{
		return docHeight;
	}

	public static BaseFont getBaseFont(Context context)
	{
		InputStream inputStream = context.getResources().openRawResource(R.raw.distproth);
        //InputStream inputStream =  context.getAssets().
		byte[] buffer = null;
		BaseFont bf = null;
		try {
			buffer = new byte[inputStream.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			inputStream.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bf = BaseFont.createFont("agencyfb.ttf", BaseFont.IDENTITY_H, true, false, buffer, null);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bf;
	}


	/*
	public static PdfPCell getRowConfig(JSONObject config) throws NumberFormatException, JSONException
	{
		PdfPCell cell = new PdfPCell();
		
		if(config.has(CONFIG_BORDER))
		{
			cell.setBorder(config.getInt(CONFIG_BORDER));
		}
		else
		{
			cell.setBorder(Rectangle.NO_BORDER);
		}
		if(config.has(CONFIG_V_ALIGN))
		{
			cell.setVerticalAlignment(config.getInt(CONFIG_V_ALIGN));
			Log.v(TAG, "Setting V align");
		}
		if(config.has(CONFIG_H_ALIGN))
		{
			cell.setHorizontalAlignment(config.getInt(CONFIG_H_ALIGN));
			Log.v(TAG, "Setting H align");
		}
			
		return cell;
	}
	*/

	public static Font getCompFont(Context context) 
	{
		compFont = new Font(getBaseFont(context), 18.0f, Font.BOLD);
        return compFont;
	}
		
	public static Font getFromToFont(Context context) 
	{
		fromToFont = new Font(getBaseFont(context), 12.0f, Font.BOLD);
		return fromToFont;
	}
	
	public static Font getTableHeaderFont(Context context)
	{
		tableHeaderFont = new Font(getBaseFont(context), 10.0f, Font.BOLD);
		return tableHeaderFont;
	}
	
	public static Font getTotalFont(Context context) 
	{		
		totalFont = new Font(getBaseFont(context), 10.0f, Font.BOLD);
		return totalFont;
	}
	
	public static Font getTotalOSFont(Context context) 
	{		
		totalFont = new Font(getBaseFont(context), 7.0f, Font.BOLD);
		return totalFont;
	}
	
	public static Font getRowFont(Context context,float fontSize)
	{
		rowFont = new Font(getBaseFont(context), fontSize);
		return rowFont;
	}
	
	public static Font getPageFooterFont(Context context)
	{
		pageFooterFont = new Font(getBaseFont(context), 9.0f);
		return pageFooterFont;
	}

}
