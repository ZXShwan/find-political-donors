import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		FileProcessor fp = new FileProcessor();
		String inputFile = args[0];
		String outputZipFile = args[1];
		String outputDateFile = args[2];

		// String inputFile = "../input/itcont.txt";
		// String outputZipFile = "../output/medianvals_by_zip.txt";
		// String outputDateFile = "../output/medianvals_by_date.txt";
		
		System.out.println("Processing data...");
		fp.readFileAndWriteZipData(inputFile, outputZipFile);
		fp.writeDateData(outputDateFile);
		System.out.println("Done!");
	}

}
