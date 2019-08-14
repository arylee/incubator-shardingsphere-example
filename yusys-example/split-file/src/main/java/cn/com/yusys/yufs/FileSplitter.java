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
	
	private int index;
	
	private List<BufferedWriter> writers;
		
	public FileSplitter(String input, int num, int index, String sperator, String quota) {
		this.input = input;
		this.num = num;
		this.index = index;
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
				int wirterIndex = Math.abs(StrUtils.removeQuotation(strArray[index], quota).hashCode() % num);
				writers.get(wirterIndex).write(line);
				writers.get(wirterIndex).newLine();
			}
		} catch (IOException e) {

		} finally {
			destory();
		}		
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
