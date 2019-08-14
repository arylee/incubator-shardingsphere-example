package cn.com.yusys.yufs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileFilter {
	
	private String input;
	
	private String hashfile;
	
	private String sperator;
	
	private String quota;
	
	private int index;
	
	private Map<String, Object> hashmap;
	
	public FileFilter(String input, String hashfile, int index, String sperator, String quota) {
		this.input = input;
		this.hashfile = hashfile;
		this.index = index;
		this.sperator = sperator;
		this.quota = quota;
		hashmap = new HashMap<String, Object>();
	}
	
	public void run() {
		try (BufferedReader readerHash = new BufferedReader(new FileReader(hashfile));
				BufferedReader readerInput = new BufferedReader(new FileReader(input));
				BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));) {
			String line = null;
			while ((line = readerHash.readLine()) != null) {
				hashmap.put(line, null);
			}
			while ((line = readerInput.readLine()) != null) {
				String[] strArray = line.split(sperator);
				if (hashmap.containsKey(StrUtils.removeQuotation(strArray[index], quota))) {
					writer.write(line);
					writer.newLine();
				}
			}
		} catch (IOException e) {

		} 
		
	}

}
