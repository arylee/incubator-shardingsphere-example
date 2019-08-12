package cn.com.yusys.yufs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {
	
	private String input;
	
	private String sperator;
	
	private String quota;
	
	private int num;
	
	private int offset;
	
	private List<BufferedWriter> writers;
		
	public FileSplitter(String input, int num, int offset, String sperator, String quota) {
		this.input = input;
		this.num = num;
		this.offset = offset;
		this.sperator = sperator;
		this.quota = quota;
		writers = new ArrayList<BufferedWriter>();
	}
		
	public void run() {
		try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
			init();
			String line = null;
			while ((line = reader.readLine()) != null) {	
				String[] strArray = line.split(sperator);
				int index = Math.abs(removeQuotation(strArray[offset], quota).hashCode() % num);
				writers.get(index).write(line);
				writers.get(index).newLine();
			}
		} catch (IOException e) {

		} finally {
			destory();
		}		
	}
	
	private String removeQuotation(String msg, String quota) {
		if (msg != null && quota != null && msg.startsWith(quota) && msg.endsWith(quota)) {
			int len = quota.length();
			return msg.substring(len, msg.length() - len);
		} 
		return msg;
	}
	
	private void init() throws IOException {
		for (int i = 0; i < num; i++) {
			BufferedWriter writer = new BufferedWriter(new FileWriter("output" + i + ".txt"));
			writers.add(writer);
		}
	}

	private void destory() {
		for (BufferedWriter writer : writers) {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) { }
			}
		}
	}

}
