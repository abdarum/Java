package weatherData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;


import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JLabel;

public class MainLayout extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JButton btnDeleteDb = new JButton("Delete database");;
	private JButton btnLoadDb = new JButton("Load database");;
	private JButton btnSaveToDatabase = new JButton("Get weather for city");
	private JPanel contentPane = new JPanel();;
	private JTextField textFieldCityToCheckWeatherOf = new JTextField();;
	private JTable weatherTable = new JTable();;

	private DataHolderSet weatherDataSet = new DataHolderSet();
	private JsonHandling openWeatherMapConnector = new JsonHandling();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnDeleteRow = new JButton("Delete row");
	private final JTextField textFieldDeleteRow = new JTextField();
	
	private int id_value;
	private final JButton btnSetPeriod = new JButton("Set period");
	private final JTextField textFieldSetPeriod = new JTextField();
	private final JLabel lblSeparator = new JLabel("");
	
	private int period = 5;
	private Timer timer = new Timer();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainLayout frame = new MainLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainLayout() {
		DatabaseHandler.createConnection();
		final int id = DatabaseHandler.selectId();
		id_value = id;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				DatabaseHandler.shutdown();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 327);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow][fill][][][]"));
		
		contentPane.add(scrollPane, "cell 0 0,growx");
		scrollPane.setViewportView(weatherTable);
		contentPane.add(btnSaveToDatabase, "flowx,cell 0 2,alignx left,aligny center");
		contentPane.add(textFieldCityToCheckWeatherOf, "cell 0 2,growx");
		contentPane.add(btnLoadDb, "flowx,cell 0 4");
		contentPane.add(btnDeleteDb, "cell 0 4");
		
		contentPane.add(btnDeleteRow, "cell 0 2");
		
		contentPane.add(textFieldDeleteRow, "cell 0 2,grow");
		
		contentPane.add(lblSeparator, "cell 0 4,growx");
		
		contentPane.add(btnSetPeriod, "cell 0 4,alignx right,growy");
		textFieldSetPeriod.setHorizontalAlignment(SwingConstants.TRAILING);
		
		contentPane.add(textFieldSetPeriod, "cell 0 4,grow");
		btnDeleteDb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DatabaseHandler.deleteAllEntries();
				weatherDataSet.eraseaccess();
				weatherDataSet.trimEntryId();
				id_value = 0;
			}
		});
		btnLoadDb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<DataHolder> newaccess = null;
				newaccess = DatabaseHandler.selectWeatherEntries();
				weatherDataSet.eraseaccess();
				for (DataHolder e : newaccess) {
					weatherDataSet.addEntry(e);
				}
				weatherDataSet.trimEntryId();
			}
		});
		
		btnSaveToDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String city = textFieldCityToCheckWeatherOf.getText();
					DataHolder entry = openWeatherMapConnector.getWeatherFromCity(city);	
					weatherDataSet.addEntry(entry);
					if (id != 0) {
						id_value = id_value + 1;
						entry.setId(id_value);
					}
					DatabaseHandler.insertEntry(entry);	
						
				} catch(Exception ex){
					textFieldCityToCheckWeatherOf.setText("wroclaw");
				}
				
			}
		});
		
		btnDeleteRow.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String idToRemove = textFieldDeleteRow.getText();
                int idToRemoveInt = 0;
                try {
                	idToRemoveInt = Integer.parseInt(idToRemove);
                } catch (Exception exc) {
                	System.out.println("Wrong format of id. It should be int");
                	idToRemoveInt = -1;
                }

                weatherDataSet.removeEntryWithId(idToRemoveInt);
                DatabaseHandler.removeEntryWithId(idToRemoveInt);

                // refresh view
				List<DataHolder> newaccess = null;
				newaccess = DatabaseHandler.selectWeatherEntries();
				weatherDataSet.eraseaccess();
				for (DataHolder e1 : newaccess) {
					weatherDataSet.addEntry(e1);
				}
				weatherDataSet.trimEntryId();
            }
        });
		
		btnSetPeriod.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	String parsePeriodStr = textFieldSetPeriod.getText();
            	System.out.println(parsePeriodStr);
                int parsePeriod = 0;
                try {
                	parsePeriod = Integer.parseInt(parsePeriodStr);
                } catch (Exception exc) {
                	System.out.println("Wrong format of period. It should be int");
                }
                
                if (parsePeriod < 0) {
    	            timer.cancel();
    	            timer.purge();                	
                }
                else {
                	timer.cancel();
    	            timer.purge();   
                	period = parsePeriod;
                	timer = new Timer();
            		timer.scheduleAtFixedRate(new TimerTask() {
            			@Override
            			public void run() {
            				try {
            					String city = "London";
            					DataHolder entry = openWeatherMapConnector.getWeatherFromCity(city);	
            					weatherDataSet.addEntry(entry);
            					if (id != 0) {
            						id_value = id_value + 1;
            						entry.setId(id_value);
            					}
            					DatabaseHandler.insertEntry(entry);	
            						
            				} catch(Exception ex){
            					textFieldCityToCheckWeatherOf.setText("wroclaw");
            				}
            			}
            		}
            		,100, period*1000);
                }
				
			}
		});
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					String city = "London";
					DataHolder entry = openWeatherMapConnector.getWeatherFromCity(city);	
					weatherDataSet.addEntry(entry);
					if (id != 0) {
						id_value = id_value + 1;
						entry.setId(id_value);
					}
					DatabaseHandler.insertEntry(entry);	
						
				} catch(Exception ex){
					textFieldCityToCheckWeatherOf.setText("wroclaw");
				}
			}
		}
		,100, period*1000);
	
		initDataBindings();
		
	}
	protected void initDataBindings() {
		BeanProperty<DataHolderSet, List<DataHolder>> weatherDatabaseBeanProperty = BeanProperty.create("access");
		JTableBinding<DataHolder, DataHolderSet, JTable> jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, weatherDataSet, weatherDatabaseBeanProperty, weatherTable);
		//
		BeanProperty<DataHolder, Integer> weatherDataEntryBeanProperty_4 = BeanProperty.create("id");
		jTableBinding.addColumnBinding(weatherDataEntryBeanProperty_4).setColumnName("Id");
		//
		BeanProperty<DataHolder, Timestamp> weatherDataEntryBeanProperty = BeanProperty.create("timestamp");
		jTableBinding.addColumnBinding(weatherDataEntryBeanProperty).setColumnName("Timestamp");
		//
		BeanProperty<DataHolder, Float> weatherDataEntryBeanProperty_2 = BeanProperty.create("temperature");
		jTableBinding.addColumnBinding(weatherDataEntryBeanProperty_2).setColumnName("Temperature");
		//
		BeanProperty<DataHolder, Float> weatherDataEntryBeanProperty_3 = BeanProperty.create("humidity");
		jTableBinding.addColumnBinding(weatherDataEntryBeanProperty_3).setColumnName("Humidity");
		//
		BeanProperty<DataHolder, String> weatherDataEntryBeanProperty_1 = BeanProperty.create("city");
		JTableBinding<DataHolder, DataHolderSet, JTable>.ColumnBinding 
		columnBinding = jTableBinding.addColumnBinding(weatherDataEntryBeanProperty_1);
		columnBinding.setColumnName("City");
		//
		jTableBinding.bind();
	}
}
