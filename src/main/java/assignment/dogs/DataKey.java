package assignment.dogs;

public class DataKey {
	private String dogName;
	private int dogSize;

	// default constructor
	public DataKey() {
		this(null, 0);
	}
        
	public DataKey(String name, int size) {
		dogName = name;
		dogSize = size;
	}

	public String getDogName() {
		return dogName;
	}

	public int getDogSize() {
		return dogSize;
	}

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
            if (this.getDogSize() == k.getDogSize()) {
                int compare = this.dogName.compareTo(k.getDogName());
                if (compare == 0){
                     return 0;
                } 
                else if (compare < 0) {
                    return -1;
                }
            }
            else if(this.getDogSize() < k.getDogSize()){
                    return -1;
            }
            return 1;
            
	}
}

