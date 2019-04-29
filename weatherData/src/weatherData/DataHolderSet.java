package weatherData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataHolderSet extends AbstractModelObject {
	private List<DataHolder> access = new ArrayList<DataHolder>();
	
	DataHolderSet(){	
	}
	
	public void addEntry(DataHolder entry) {
		List<DataHolder> oldValue = access;
		access = new ArrayList<DataHolder>(access);
		access.add(entry);
		firePropertyChange("access", oldValue, access);
		firePropertyChange("accessCount", oldValue.size(), access.size());
	}
	
	public List<DataHolder> getaccess() {
		return access;
	}
	
	public DataHolder getEntry(int idx) {
		return access.get(idx);
	}
	
	public void removeEntry(int idx) {
		access.remove(idx);
	}

	public void removeEntryWithId(int id) {
		List<DataHolder> oldValue = access;
		Iterator iter = access.iterator();
		while (iter.hasNext()) {
			DataHolder tmp = (DataHolder) iter.next();
			if (tmp.getId() == id) {

				iter.remove();
				System.out.println("Entry with Id=" + id + " removed");
			}
		}
		firePropertyChange("access", oldValue, access);
		firePropertyChange("accessCount", oldValue.size(), access.size());
		System.out.println("Can not remove Entry with Id=" + id + " removed");

	}
	
	public void eraseaccess() {
		List<DataHolder> oldValue = access;
		access = new ArrayList<DataHolder>();
		firePropertyChange("access", oldValue, access);
		firePropertyChange("accessCount", oldValue.size(), access.size());
	}
	
	public int getaccessCount() {
		return access.size();
	}
	
	public void trimEntryId() {
		int largestId = 0;
		for(DataHolder tmp: access) {
			if (tmp.getId() > largestId) {
				largestId = tmp.getId();
			}
		}
		DataHolder.setIdAvaliable(largestId+1);
	}
}
