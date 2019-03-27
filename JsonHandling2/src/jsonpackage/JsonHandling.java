package jsonpackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;    

import gui.MainLayout;
import java.awt.event.ActionListener;

public class JsonHandling{

	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  public static String getDate(JSONObject localJson) {
		  String dt = localJson.get("dt").toString();
          Date date = Date.from( Instant.ofEpochSecond( Integer.parseInt(dt) ) );
          return date.toString();

	  }
	  
	  public static String getCity(JSONObject localJson) {
		  String city = localJson.get("name").toString();
		  return city;
	  }
	  
	  public static String getTemp(JSONObject localJson) {
		  JSONObject jsonTmp = (JSONObject) localJson.get("main");
		  String temp = jsonTmp.get("temp").toString();
		  Float tempC = Float.parseFloat(temp) - (float)273.15;
		  return String.format("%.1f C", tempC);
	  }
	  
	  public static String getHumidity(JSONObject localJson) {
		  JSONObject jsonTmp = (JSONObject) localJson.get("main");
		  String humidity = jsonTmp.get("humidity").toString();
		  return humidity + "%";
	  }

	  public static void main(String[] args) throws IOException, JSONException {
		MainLayout myGui = new MainLayout();
		myGui.setVisible(true);

		
		myGui.buttonDownloadData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JSONObject json = readJsonFromUrl(
							"http://api.openweathermap.org/data/2.5/weather?id=3081368&APPID=5585c9a1202a2c2db9304f26e8f783ad");
					
					myGui.setTimestamp(getDate(json));
					myGui.setCity(getCity(json));
					myGui.setTemp(getTemp(json));
					myGui.setHumidity(getHumidity(json));
				}
				catch (Exception exc) {
					
				}
			}
		});
	  

	  }
	}
