package weatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;



public class JsonHandling {
	
	public JsonHandling() {
	}
	
	public DataHolder getWeatherFromCity(String city) {
		
		JsonHandling jsonHandler = new JsonHandling();
		JSONObject json =  jsonHandler.readJsonFromCity(city);
		DataHolder newEntry = new DataHolder(jsonHandler.getHumidityRaw(json), 
				jsonHandler.getTempRaw(json), jsonHandler.getCity(json));
		return newEntry;
	}
	
	  public JSONObject readJsonFromCity(String city) {
			try {
				String url = "http://api.openweathermap.org/data/2.5/weather?q="+
				city+"&APPID=5585c9a1202a2c2db9304f26e8f783ad";
//				String url = "http://api.openweathermap.org/data/2.5/weather?id=3081368&APPID=5585c9a1202a2c2db9304f26e8f783ad";
				JSONObject json = readJsonFromUrl(url);
				return json;
			}
			catch (Exception e) {				
			}
			return null;
		}
	  
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
			  System.out.println("timestamp: "+dt);
	          Date date = Date.from( Instant.ofEpochSecond( Integer.parseInt(dt) ) );
	          return date.toString();
		  }
		  
		  public static String getCity(JSONObject localJson) {
			  String city = localJson.get("name").toString();
			  System.out.println("city: "+city);
			  return city;
		  }
		  
		  public static String getTemp(JSONObject localJson) {
			  JSONObject jsonTmp = (JSONObject) localJson.get("main");
			  String temp = jsonTmp.get("temp").toString();
			  System.out.println("temp in K: "+temp);
			  Float tempC = Float.parseFloat(temp) - (float)273.15;
			  return String.format("%.1f C", tempC);
		  }
		  
		  public static float getTempRaw(JSONObject localJson) {
			  JSONObject jsonTmp = (JSONObject) localJson.get("main");
			  String temp = jsonTmp.get("temp").toString();
			  System.out.println("temp in K: "+temp);
			  Float tempC = Float.parseFloat(temp) - (float)273.15;
			  
			  try {
				  String round_temp = String.format("%.2f", tempC);
				  round_temp.replaceAll(",","\\.");
//				  System.out.println("str "+round_temp);
//				  tempC = Float.parseFloat(round_temp);
				  tempC = new Float(round_temp);
//				  System.out.println("float "+tempC);
			  }
			  catch (Exception e) {}
			  return tempC;
		  }
		  
		  public static String getHumidity(JSONObject localJson) {
			  JSONObject jsonTmp = (JSONObject) localJson.get("main");
			  String humidity = jsonTmp.get("humidity").toString();
			  System.out.println("humidity: "+humidity);
			  return humidity + "%";
		  }
		  
		  public static float getHumidityRaw(JSONObject localJson) {
			  JSONObject jsonTmp = (JSONObject) localJson.get("main");
			  float humidity = Float.parseFloat(jsonTmp.get("humidity").toString());
			  System.out.println("humidity: "+humidity);
			  
			  try {
				  String round_hum = String.format("%.2f", humidity);
				  humidity = Float.valueOf(round_hum).floatValue();
			  }
			  catch (Exception e) {}
			  return humidity;
		  }
}
