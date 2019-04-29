package weatherData;

import java.sql.Timestamp;
import java.text.DecimalFormat;

public class DataHolder extends AbstractModelObject{
	
	private int id;
	private java.sql.Timestamp timestamp;
	private float humidity;
	private float temperature;
	private String city;
	private static int idAvaliable = 1;

	private String country;

	public DataHolder() {
		super();
		city = "undefined";
	}

	public DataHolder(int id, java.sql.Timestamp timestamp, float humidity, float temperature, String city) {
		super();
		this.id = id;
		setTimestamp(timestamp);
		setHumidity(humidity);
		setTemperature(temperature);
		setCity(city);
	}

	public DataHolder(float humidity, float temperature, String city) {
		super();
		setId();
		setTimestamp(new Timestamp(System.currentTimeMillis()));
		setHumidity(humidity);
		setTemperature(temperature);
		setCity(city);
		}
	
	public int getId() {
		return id;
	}
	
	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public float getHumidity() {
		return humidity;
	}

	public double getTemperature() {
		return temperature;
	}

	public String getCity() {
		return city;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setId()
	{
		int oldValue = this.id;
		setId(DataHolder.idAvaliable); // set id to next available id
//		System.out.println("ID "+id);
//		System.out.println("ID avaliable before"+idAvaliable);
		DataHolder.idAvaliable++;
//		System.out.println("ID avaliable after"+idAvaliable);
		firePropertyChange("id", oldValue, id);
	}
	
	public static void setIdAvaliable(int id) {
		DataHolder.idAvaliable = id;
	}
	
	public static int getIdAvaliable()
	{
		return DataHolder.idAvaliable; //returns static field
	}
	
	public void setTimestamp(java.sql.Timestamp timestamp) {
		java.sql.Timestamp oldValue = this.timestamp;
		firePropertyChange("timestamp", oldValue, timestamp);
		this.timestamp = timestamp;
	}

	public void setHumidity(float humidity) {
		float oldValue = this.humidity;
//		String round_hum = String.format("%.2f", humidity);
//		humidity = Float.valueOf(round_hum);
		firePropertyChange("humidity", oldValue, humidity);
		this.humidity = humidity;
	}
	
	public void setTemperature(float temperature) {
		float oldValue = this.temperature;
		firePropertyChange("temperature", oldValue, temperature);
		this.temperature = temperature;
	}
	
	public void setCity(String city) {
		String oldValue = this.city;
		firePropertyChange("city", oldValue, city);
		this.city = city;
	}
	
	@Override
	public String toString() {
		return "WeatherData [id=" + id + ", timestamp=" + timestamp+
				", humidity=" + humidity +", temperature=" + temperature+
				", city=" + city +"]";
	} 
}
