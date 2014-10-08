package com.example.RSSFeed;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SAXHandler extends DefaultHandler {
	private String tempVal, locationVal, conditionVal;

    private ArrayList<String> title; //titles of items in the free press feed

    //boooleans to keep track of what elements I'm in
    private boolean inItem, inTitle;

    public SAXHandler(){
        title = new ArrayList<String>();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d("test", "endElement: " + qName);
        if (qName.equals("title")) {
            inTitle = false;
        } else if (qName.equals("item")) {
            inItem = false;
        }



    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        //String s = new String(ch, start, length);
        //Log.d("test", "characters: " + s);

        if (inTitle && inItem){
            // add title to arrayList
            String s = new String(ch, start, length);
            title.add(s); // add a string buffer and do this in endElement instead, don't add here
        }

    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException {
        Log.d("test", "startElement: qName " + qName + ", localName " + localName + ", URI " + uri);

        if (qName.equals("item")){
            inItem = true;
        }
        if (qName.equals("title")) {
            inTitle = true;
        }

		/* if (qName.equals("yweather:condition")) {
			for (int i=0; i<attrs.getLength(); i++) {
				if (attrs.getQName(i).equals("temp")) {
					tempVal = attrs.getValue(i);
				} else if (attrs.getQName(i).equals("text")) {
					conditionVal = attrs.getValue(i);
				}
			}
		} else if (qName.equals("yweather:location")) {
			for (int i=0; i<attrs.getLength(); i++) {
				if (attrs.getQName(i).equals("city")) {
					locationVal = attrs.getValue(i);
				}
			}
		} */
	}
	
	public String getTemp() {
		return tempVal;
	}
	
	public String getLocation() {
		return locationVal;
	}
	
	public String getCondition() {
		return conditionVal;
	}
}
