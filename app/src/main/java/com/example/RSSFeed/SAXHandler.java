package com.example.RSSFeed;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	private String tempVal, locationVal, conditionVal;

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d("test", "endElement: " + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String s = new String(ch, start, length);
        Log.d("test", "characters: " + s);

    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException {
        Log.d("test", "startElement: " + qName);
		if (qName.equals("yweather:condition")) {
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
		}
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
