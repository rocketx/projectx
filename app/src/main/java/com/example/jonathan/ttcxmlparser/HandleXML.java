package com.example.jonathan.ttcxmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class HandleXML
{


    List<String> routeList = new ArrayList<String>();

    private String routes = "routes";
    private String urlString = null;         //the full xml url

    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url)
    {
        this.urlString = url;
    }

    public List<String> getRouteList()
    {
        return routeList;
    }


    //parse XML
    public void parseXMLAndStoreIt(XmlPullParser myParser)
    {

       /*
       Get the following information from the xml file:
       country
       temperature
       humidity
       pressure
       */


        int event;   //event number
        String text = null;
        try
        {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name = myParser.getName();        //name of the tag. (it can be null at times...)
                //System.out.println(""+name);
                switch (event)
                {
                    case XmlPullParser.START_TAG:     //start tag
                        break;
                    case XmlPullParser.TEXT:          //the "text" inside the surrounding tag
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:        //end tag
                        if (name.equals("route"))
                        {
                            routes = myParser.getAttributeValue(null, "title");    //<route tag="5" title="5-Avenue Rd"/>
                            routeList.add(routes);       //add route to routeList
                        }

                        else
                        {
                        }
                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void fetchXML()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);          //call the method "parseXMLAndStoreIt"
                    stream.close();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

}