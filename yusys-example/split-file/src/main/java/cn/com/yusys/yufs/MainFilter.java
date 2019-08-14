package cn.com.yusys.yufs;

public class MainFilter {
	
	public static void main(String[] args) {
		if (args.length >= 3 && args.length <=5) {
			String sperator = "\\|@!";
			String quota = "\"";			
			String input = args[0];
			String hashfile = args[1];
			int index = Integer.parseInt(args[2]);
			if (args.length > 3) {
				sperator = args[3];
			}
			if (args.length > 4) {
				quota = args[4];
			}
			FileFilter filter = new FileFilter(input, hashfile, index, sperator, quota);
			filter.run();
			System.out.println("Done.");
		} else {
			System.out.println("Arguments:");
			System.out.println("\t{input-file} {hash-file} {index} <sperate-string> <quota>");
		}
	}

}
