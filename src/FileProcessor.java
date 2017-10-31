import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * class for read and write file
 * @author zx
 */
public class FileProcessor {
	
	private final Map<String, MedianFinder> zipMap;
	private final Map<String, MedianFinder> dateMap;
	
	/**
	 * zipMap: key: cmte_id+zip_code, value: data set of this key
	 * dateMap: key: cmte_id+transaction_dt, value: data set of this key
	 */
	public FileProcessor() {
		this.zipMap = new HashMap<String, MedianFinder>();
		this.dateMap = new TreeMap<String, MedianFinder>();
	}
	
	/**
	 * read file and write "medianvals_by_zip.txt" file by streaming
	 * @param inputFile "itcont.txt"
	 * @param outputFile "medianvals_by_zip.txt"
	 * @throws IOException
	 */
	public void readFileAndWriteZipData(String inputFile, String outputFile) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		BufferedWriter bw = null;
		try {
			inputStream = new FileInputStream(inputFile);
			sc = new Scanner(inputStream);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
			while(sc.hasNextLine()) {
				String data[] = sc.nextLine().split("\\|");
				if(data[10].length() == 0 || data[13].length() == 0 
						|| data[14].length() == 0 || data[15].length() != 0) {
					continue;
				}
				String zipDataLine = getZipDataLine(data);
				storeDateData(data);
				// System.out.println(zipDataLine);
				bw.write(zipDataLine + "\r\n");
			}
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
			if(sc != null) {
				sc.close();
			}
			if(bw != null) {
				bw.close();
			}
		}
	}
	
	/**
	 * write "medianvals_by_date.txt" file
	 * @param outputFile "medianvals_by_date.txt"
	 * @throws IOException
	 */
	public void writeDateData(String outputFile) throws IOException {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
			for(Entry<String, MedianFinder> entry : dateMap.entrySet()) {
				String dateDataLine = getDateDataLine(entry);
				bw.write(dateDataLine + "\r\n");
			}
		} finally {
			if(bw != null) {
				bw.close();
			}
		}
	}

	/**
	 * format each line of "medianvals_by_zip.txt" file to output
	 * @param data each line of "itcont.txt" file splited by "|"
	 * @return each line of "medianvals_by_zip.txt" file
	 */
	private String getZipDataLine(String[] data) {
		StringBuilder sb = new StringBuilder();
		String cmte_id = data[0];
		String zip_code = data[10].substring(0, 5);
		int trans_amt = Integer.parseInt(data[14]);
		
		String key = cmte_id + zip_code;
		if(!zipMap.containsKey(key)) { 
			zipMap.put(key, new MedianFinder());
		}
		MedianFinder mf = zipMap.get(key);
		mf.addNum(trans_amt);
		
		int count = mf.getSize();
		int total = mf.getTotalAmount();
		int median = mf.getMedian();
		
		sb.append(cmte_id).append("|");
		sb.append(zip_code).append("|");
		sb.append(median).append("|");
		sb.append(count).append("|");
		sb.append(total);
		return sb.toString();
	}
	
	/**
	 * format each line of "medianvals_by_date.txt" file to output
	 * @param entry dateMap entrySet
	 * @return each line of "medianvals_by_date.txt" file
	 */
	private String getDateDataLine(Entry<String, MedianFinder> entry) {
		StringBuilder sb = new StringBuilder();
		String cmte_id = entry.getKey().substring(0, 9);
		String trans_dt = entry.getKey().substring(9);
		MedianFinder mf = entry.getValue();
		
		int count = mf.getSize();
		int total = mf.getTotalAmount();
		int median = mf.getMedian();
		
		sb.append(cmte_id).append("|");
		sb.append(trans_dt).append("|");
		sb.append(median).append("|");
		sb.append(count).append("|");
		sb.append(total);
		return sb.toString();
	}
	
	/**
	 * store data related to "medianvals_by_date.txt" file
	 * @param data each line of "itcont.txt" file splited by "|"
	 */
	private void storeDateData(String[] data) {
		String cmte_id = data[0];
		String trans_dt = data[13];
		int trans_amt = Integer.parseInt(data[14]);
		
		String key = cmte_id + trans_dt;
		if(!dateMap.containsKey(key)) { 
			dateMap.put(key, new MedianFinder());
		}
		MedianFinder mf = dateMap.get(key);
		mf.addNum(trans_amt);
	}	
	
}
