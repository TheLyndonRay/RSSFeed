package com.example.RSSFeed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class RSSFeed extends Activity {
	public static final String FEED_URL = "http://weather.yahooapis.com/forecastrss?w=2972&u=c";
	public URL xml_file;
	public BufferedReader in;
	RSSFeeder feedme;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        feedme = new RSSFeeder();
        feedme.execute();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.layout.menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.refresh:
    		feedme = new RSSFeeder();
    		feedme.execute();
    		break;
    	}
    	return true;
    }
	
    public class RSSFeeder extends AsyncTask<Void, Void, Void> {
    	SAXHandler handler;
		@Override
		protected Void doInBackground(Void... params) {
			try {
	        	xml_file = new URL(FEED_URL);
	        	in = new BufferedReader(new InputStreamReader(xml_file.openStream()));
	        	
	        	//yweather:condition
	        	SAXParserFactory spf = SAXParserFactory.newInstance();
	        	SAXParser sp = spf.newSAXParser();
	        	handler = new SAXHandler();
	        	
	        	sp.parse(new InputSource(in), handler);
	        	
	        	//Thread.sleep(10000);
	        } catch (MalformedURLException ex) {
	        	Log.e("Test", ex.getMessage());
			} catch (IOException ex) {
				Log.e("Test", ex.getMessage());
			} catch (SAXException ex) {
				Log.e("Test", ex.getMessage());
			} catch (ParserConfigurationException ex) {
				Log.e("Test", ex.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			TextView tvLabel = (TextView)findViewById(R.id.lblInfo);
        	tvLabel.setText("Current conditions in: " + handler.getLocation());
        	
        	TextView tvTemp = (TextView)findViewById(R.id.lblTemp);
        	tvTemp.setText("Temperature: " + handler.getTemp() + " C\nConditions: " + handler.getCondition());
		}
    }
}