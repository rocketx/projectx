package com.example.jonathan.ttcxmlparser;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener
{

    String finalUrl = " http://webservices.nextbus.com/service/publicXMLFeed?command=routeList&a=ttc";
    private HandleXML obj;
    Button butt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        butt = (Button) findViewById(R.id.button1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public void onClick(View v)
    {

        if (v.getId() == butt.getId())
        {
            Toast.makeText(MyActivity.this, "button pressed", Toast.LENGTH_SHORT).show();
          //  if (DetectConnection.checkInternetConnection(MyActivity.this))   //if the device has wifi connection
         //   {
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;

                List<String> myList = obj.getRouteList();   // get the list of routes

                for (int i = 0; i < myList.size(); i++)  //print the list of routes on the terminal
                {
                    System.out.println(myList.get(i));
                }
         ///   }
          //  else
          //  {
         //       Toast.makeText(MyActivity.this, "You don't have internet connection!!!", Toast.LENGTH_SHORT).show();
          //  }


        }

    }
}
