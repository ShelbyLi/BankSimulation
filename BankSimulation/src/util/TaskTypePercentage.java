package util;

import java.util.ArrayList;
import java.util.List;

public class TaskTypePercentage {
		private int DEPOSIT_PERC;
		private int WITHDRAW_PERC;
		private int PAY_THE_FINE_PERC;
		private int OPEN_ONLINE_BANKING_PERC;
		private int PAY_BILLS;
		private int PURCHASE_FUND_PERC;
		private int RIMITTANCE_PERC;
		private int PERSONAL_LOAN_REPAYMENT_PERC;
		public TaskTypePercentage(int dEPOSIT_PERC, int wITHDRAW_PERC, int pAY_THE_FINE_PERC,
				int oPEN_THE_ONLINE_BANKING_PERC, int pAY_BILLS, int pURCHASE_FUND_PERC, int rIMITTANCE_PERC,
				int pERSONAL_LOAN_REPAYMENT_PERC) {
			super();
			DEPOSIT_PERC = dEPOSIT_PERC;
			WITHDRAW_PERC = wITHDRAW_PERC;
			PAY_THE_FINE_PERC = pAY_THE_FINE_PERC;
			OPEN_ONLINE_BANKING_PERC = oPEN_THE_ONLINE_BANKING_PERC;
			PAY_BILLS = pAY_BILLS;
			PURCHASE_FUND_PERC = pURCHASE_FUND_PERC;
			RIMITTANCE_PERC = rIMITTANCE_PERC;
			PERSONAL_LOAN_REPAYMENT_PERC = pERSONAL_LOAN_REPAYMENT_PERC;
		}
		
		public TaskType getRandomType() {
			List<Integer> percentageList = new ArrayList<Integer>();
			percentageList.add(DEPOSIT_PERC);
			percentageList.add(WITHDRAW_PERC);
			percentageList.add(PAY_THE_FINE_PERC);
			percentageList.add(OPEN_ONLINE_BANKING_PERC);
			percentageList.add(PAY_BILLS);
			percentageList.add(PURCHASE_FUND_PERC);
			percentageList.add(RIMITTANCE_PERC);
			percentageList.add(PERSONAL_LOAN_REPAYMENT_PERC);
			RandomPro randomPro = new RandomPro();
			int randomTypeInt = randomPro.getRandomIntOnPercentages(percentageList);
			return TaskType.values()[randomTypeInt];  // int -> enum
		}

		public double getIsExpressPercentage() {
			// 1 2 4 5 7是快捷服务
			double ret = 1.0 * (DEPOSIT_PERC + WITHDRAW_PERC + 
					OPEN_ONLINE_BANKING_PERC + PAY_BILLS + RIMITTANCE_PERC) / 100;
			return ret;
		}
}

