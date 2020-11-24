package util;

import java.util.ArrayList;
import java.util.List;

public class CustomerTypePercentage {
	private int NORMAL_PERC;
	private int VIP_PERC;
	public CustomerTypePercentage(int nORMAL_PERC, int vIP_PERC) {
		super();
		NORMAL_PERC = nORMAL_PERC;
		VIP_PERC = vIP_PERC;
	}
	public CustomerType getRandomType() {
		List<Integer> percentageList = new ArrayList<Integer>();
		percentageList.add(NORMAL_PERC);
		percentageList.add(VIP_PERC);
		RandomPro randomPro = new RandomPro();
		int randomTypeInt = randomPro.getRandomIntOnPercentages(percentageList);
		return CustomerType.values()[randomTypeInt];
	}
}

