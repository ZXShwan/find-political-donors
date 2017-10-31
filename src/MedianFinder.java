import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * class for calculating median
 * @author zx
 */
public class MedianFinder {
	
	private final PriorityQueue<Integer> minH;
	private final PriorityQueue<Integer> maxH;
	
	/**
	 * minH: store the min half value of data set
	 * maxH: store the max half value of data set
	 */
	public MedianFinder() {
		this.minH = new PriorityQueue<Integer>();
		this.maxH = new PriorityQueue<Integer>(1, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1.intValue() > o2.intValue()) { 
					return -1;
				}else if(o1.intValue() < o2.intValue()) {
					return 1;
				} else {
					return 0;
				}
			}
			
		});
	}
	
	/**
	 * add new data
	 * @param num new number to be added
	 */
	public void addNum(int num) {
		if(minH.size() == 0 && maxH.size() == 0) {
			minH.add(num);
		} else if(minH.size() > maxH.size()) {
			if(num > minH.peek()) {
				maxH.add(minH.poll());
				minH.add(num);
			} else {
				maxH.add(num);
			}
		} else if(minH.size() < maxH.size()) {
			if(num < maxH.peek()) {
				minH.add(maxH.poll());
				maxH.add(num);
			} else {
				minH.add(num);
			}
		} else {
			if(num < maxH.peek()) {
				maxH.add(num);
			} else {
				minH.add(num);
			}
		}
	}
	
	/**
	 * find the median number of the data set
	 * @return the median number
	 */
	public int getMedian() {
		if(minH.size() == 0 && maxH.size() == 0) {
			return 0;
		} else if(minH.size() > maxH.size()) {
			return minH.peek();
		} else if(minH.size() < maxH.size()) {
			return maxH.size();
		} else {
			return (int) Math.round((minH.peek() + maxH.peek()) / 2.0);
		}
	}
	
	/**
	 * calculate the sum of the data set
	 * @return the sum
	 */
	public int getTotalAmount() {
		int total = 0;
		for(int num : minH) {
			total += num;
		}
		for(int num : maxH) {
			total += num;
		}
		return total;
	}
	
	/**
	 * get the size of the data set
	 * @return the size
	 */
	public int getSize() {
		return minH.size() + maxH.size();
	}
	
}
