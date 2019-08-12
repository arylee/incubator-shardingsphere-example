package cn.com.yusys.yufs;

public class Main {
	
	public static void main(String[] args) {
		if (args.length >= 3 && args.length <=5) {
			String sperator = "\\|@!";
			String quota = "\"";			
			String input = args[0];
			int num = Integer.parseInt(args[1]);
			int offset = Integer.parseInt(args[2]);
			if (args.length > 3) {
				sperator = args[3];
			}
			if (args.length > 4) {
				quota = args[4];
			}
			FileSplitter splitter = new FileSplitter(input, num, offset, sperator, quota);
			splitter.run();
			System.out.println("Done.");
		} else {
			System.out.println("Arguments:");
			System.out.println("\t{input-file} {split-cout} {index} <sperate-string> <quota>");
		}
	}

}
