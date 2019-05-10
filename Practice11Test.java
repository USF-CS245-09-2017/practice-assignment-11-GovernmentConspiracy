import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import java.util.ArrayList;


public class Practice11Test {
	
	
	public Practice11Test() {
	}
	
	
	public boolean storeTest() {
		String key = "kick";
		String val = "push";
		boolean success = false;
		
		Hashtable hashtable = new Hashtable();
		
		try {
			hashtable.put(key, val);
			if (hashtable.get(key).equals(val)) {
				hashtable.remove(key);
				success = true;
			}
		} catch (Exception e) {
			success = false;
		}
		return success;
	}
	
	
	public boolean notStoredTest() {
		String key = "American Terrorist";
		boolean success = false;
		
		Hashtable hashtable = new Hashtable();
		
		try {
			if (! hashtable.containsKey(key)) {
				if (hashtable.get(key).equals(null))
					success = true;
				else
					success = false;
			} else {
				System.out.println("       ALERT: Cannot run \"not present\" test!");
			}
		} catch (Exception e) {
			success = true;
		}
		return success;
	}
	
	
	public boolean basicRemoveTest() {
		String key = "daydream";
		String val = "I dream of you amid the flowers";
		boolean success = false;
		
		Hashtable hashtable = new Hashtable();
		
		try {
			hashtable.put(key, val);
			if (hashtable.remove(key).equals(val)) {
				if (hashtable.containsKey(key))
					success = false;
				else
					success = true;
			}
		} catch (Exception e) {
			success = false;
			System.out.println("Nope - " + e.getMessage());
		}
		return success;
	}
	
	
	public boolean advancedRemoveTest() {
		String key = "food for thought";
		boolean success = false;
		
		Hashtable hashtable = new Hashtable();
		
		try {
			if (! hashtable.containsKey(key)) {
				String shouldNotExist = hashtable.remove(key);
				if (shouldNotExist != null || shouldNotExist.length() > 0)
					success = false;
				else
					success = true;
			} else {
				System.out.println("       ALERT: Cannot run \"not present\" test!");
			}
		} catch (Exception e) {
			success = true;
		}
		return success;
	}
	
	
	public long timingTest() {
		File file = new File("/usr/share/dict/web2");
		BufferedReader reader = null;
		ArrayList<String> lookingFor = new ArrayList<String>();
		Random random = new Random();
		boolean success = true;
		long start = 0;
		long end = 0;
		
		Hashtable hashtable = new Hashtable();
		
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    start = System.currentTimeMillis();
		    while ((text = reader.readLine()) != null) {
				//System.out.println(text + "\t>Slot: " + hashtable.getSlot(text) + "\t>Size: " + hashtable.size() + "\t>Bucket: " + hashtable.bucketSize() + "\t>Load: " + hashtable.loadFactor() );
				//Thread.sleep(2000);
		    	hashtable.put(text, text);
		    	if (random.nextFloat() < 0.0002) {
		    		lookingFor.add(text);
		    	}
		    }

		    for (int i = 0; i < lookingFor.size(); i++) {
		    	if (! hashtable.get(lookingFor.get(i)).equals(lookingFor.get(i))) {
		    		System.out.println("       failed to get item from hashtable: " + lookingFor.get(i));
		    		success = false;
		    	}
		    }
		    end = System.currentTimeMillis();
		} catch (Exception e) {
			System.out.println("Unable to conduct timing test.");
			//System.out.println(e.getMessage());
			success = false;
		}
		
		if (! success)
			return Long.MAX_VALUE;
		
		return (end-start);
	}
	
	
	public void runTest () {
		int grade = 0;
		
		if (storeTest()) {
			grade += 10;
			System.out.println("[+10%] Passed insert test");
		} else {
			System.out.println("[    ] Failed insert test");
		}
		
		// notStoredTest
		
		if (notStoredTest()) {
			grade += 10;
			System.out.println("[+10%] Passed \"not present\" test");
		} else {
			System.out.println("[    ] Failed \"not present\" test");
		}
		
		if (basicRemoveTest()) {
			grade += 20;
			System.out.println("[+20%] Passed basic remove test");
		} else {
			System.out.println("[    ] Failed basic remove test");
		}
		
		if (advancedRemoveTest()) {
			grade += 25;
			System.out.println("[+25%] Passed advanced remove test");
		} else {
			System.out.println("[    ] Failed advanced remove test");
		}
		
		long time = timingTest();
		if (time < 200) {
			grade += 35;
			System.out.println("[+35%] Passed timing test in " + time + "ms.");
		} else {
			System.out.println("[    ] Failed timing test -- took " + time + "ms. (Should be less than 200ms.)");
		}
		
		System.out.println("Grade for this assignment: " + grade + "%");
	}
	
	
	public static void main(String[] args) {
		Practice11Test test = new Practice11Test();
		test.runTest();
	}
	
}
