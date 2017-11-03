package com.example.yan.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast extends Activity {
    private ProgressBar pBar;
    private String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        pBar = (ProgressBar)findViewById(R.id.progress_bar);
        pBar.setVisibility(View.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String stringValue, stringMin, stringMax, stringIcon;
        Bitmap bMap;

        @Override
        protected String doInBackground(String ... urlstring) {
            try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            XmlPullParser parser = Xml.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(conn.getInputStream(), null);
            parser.nextTag();

            List entries = new ArrayList();
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String tag = parser.getName();
                // Starts by looking for the entry tag
                if (tag.equals("temperature")) {
                    stringValue = parser.getAttributeValue(null, "value");
                    publishProgress(25, 50, 75);
                    stringMin = parser.getAttributeValue(null, "min");
                    publishProgress(25, 50, 75);
                    stringMax = parser.getAttributeValue(null, "max");
                    publishProgress(25, 50, 75);
  //                  entries.add(readEntry(parser));
                } else {
   //                 skip(parser);
                }
                if (tag.equals("weather")) {
                    stringIcon = parser.getAttributeValue(null, "icon");
                    publishProgress(25, 50, 75);
                } else {            }
            }
            }
            catch (XmlPullParserException e) {        }
            catch (IOException ex) {}
            return stringValue;
         }

        @Override
        protected void onPostExecute(String result) {
            pBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }
    }


}
