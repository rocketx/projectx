package com.example.jonathan.ttcxmlparser;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MyActivity extends Activity
{

    String finalUrl = " http://webservices.nextbus.com/service/publicXMLFeed?command=routeList&a=ttc";
    private HandleXML obj;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public void open(View view)    //change this method to a OnClick method later
    {
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while (obj.parsingComplete) ;

        List<String> myList = obj.getRouteList();   // get the list of routes

        for (int i = 0; i < myList.size(); i++)
        {
            System.out.println(myList.get(i));
        }
    }
}
