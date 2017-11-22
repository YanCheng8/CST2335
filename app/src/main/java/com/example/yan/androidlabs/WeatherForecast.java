package com.example.yan.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private ProgressBar pBar;
    private static final String urlString =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    private TextView currentT, minT, maxT;
    private ImageView imv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        pBar = (ProgressBar)findViewById(R.id.progress_bar);
        pBar.setVisibility(View.VISIBLE);
        pBar.setProgress(0);
        currentT = (TextView)findViewById(R.id.current_temp) ;
        minT = (TextView)findViewById(R.id.min_temp) ;
        maxT = (TextView)findViewById(R.id.max_temp) ;
        imv1       = (ImageView)findViewById(R.id.current_weather) ;
        new ForecastQuery().execute(urlString);
    }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public boolean fileExistance(String fname){

        File file = getBaseContext().getFileStreamPath(fname+".png");
        return file.exists();   }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String stringValue, stringMin, stringMax, stringIcon;
        Bitmap image;

        @Override
        protected String doInBackground(String ... urls) {
            try{
                //         InputStream stream = downloadUrl(urlString);
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

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String tag = parser.getName();
                    // Starts by looking for the entry tag
                    if (tag.equals("temperature")) {
                        stringValue = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        try{Thread.sleep(1000);}//SystemClock.sleep(1000);
                        catch (InterruptedException e){e.printStackTrace();}

                        stringMin = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        try{Thread.sleep(1000);}
                        catch (InterruptedException e){e.printStackTrace();}

                        stringMax = parser.getAttributeValue(null, "max");
                        parser.nextTag();
                        publishProgress(75);
                        try{Thread.sleep(1000);}
                        catch (InterruptedException e){e.printStackTrace();}
                    }
                    else {
                        if (tag.equals("weather"))
                            stringIcon = parser.getAttributeValue(null, "icon");
                        else
                            skip(parser);
                    }
                }
            }catch (XmlPullParserException e) {  e.printStackTrace();      }
            catch (IOException ex) {ex.printStackTrace();}

            if(!fileExistance(stringIcon)) {
                String ImageURL = "http://openweathermap.org/img/w/" + stringIcon + ".png";
                image  = getImage(ImageURL);
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput( stringIcon + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (FileNotFoundException e) {e.printStackTrace();}
                catch (IOException e) {e.printStackTrace(); }
                Log.i(ACTIVITY_NAME, stringIcon+ ".png" + " is downloaded");
            }
            else
                Log.i(ACTIVITY_NAME, stringIcon+ ".png" + " is found in the local storage directory");

            FileInputStream fis = null;
            try {
                fis = openFileInput(stringIcon+ ".png");   }
            catch (FileNotFoundException e) {    e.printStackTrace();  }
            image = BitmapFactory.decodeStream(fis);
            publishProgress(100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            currentT.setText(stringValue);
            minT.setText(stringMin);
            maxT.setText(stringMax);
            imv1.setImageBitmap(image);
            pBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}


