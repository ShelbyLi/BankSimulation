package util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPro extends Random {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getRandomIntOnPercentages(List<Integer> percentageList) {
		int r = new Random().nextInt(100);
//		r = 96;
//		System.out.println(r);
		int count = 0;
		int sum = percentageList.get(0);
		while (sum <= r) {
//			System.out.println(sum);
			count++;
			sum += percentageList.get(count);
			
		}
		
//		for (int i = 0; i < percentageList.size(); i++) {
//			if (r < sum) {
//				break;
//			}
//			sum += percentageList
//		}
		
		return count;
	}
	
	public static void main(String[] args) {
		List<Integer> l = new ArrayList<>();
		l.add(20);
		l.add(20);
		l.add(10);
		l.add(5);
		l.add(15);
		l.add(10);
		l.add(10);
		l.add(10);
		RandomPro randomPro = new RandomPro();
		
		System.out.println(randomPro.getRandomIntOnPercentages(l));
	}
}
