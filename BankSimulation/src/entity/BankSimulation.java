package entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.CustomerTypePercentage;
import util.TaskTypePercentage;
import util.TimeShifting;
import util.WindowAllocation;
import util.WindowType;

public class BankSimulation {
	public static void main(String[] args) {
		oneDay();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date date = sdf.parse("2020年11月15日");
			oneDay(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			simulate();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void oneDay(Date date) {
		TaskTypePercentage taskTypePercentage1;
		CustomerTypePercentage customerPercentage;
		WindowAllocation windowAllocation1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		if (sdf.format(date).equals("15")) {  // 可提前设置一些特殊日期的
			taskTypePercentage1 = new TaskTypePercentage(15, 35, 5, 5, 5, 15, 10, 10);
			customerPercentage = new CustomerTypePercentage(85,  15);
			windowAllocation1 = new WindowAllocation(1, 2, 1);
		} else {
			taskTypePercentage1 = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
			customerPercentage = new CustomerTypePercentage(75,  25);
			windowAllocation1 = new WindowAllocation(1, 2, 1);
		}
		Bank bank1 = new Bank(taskTypePercentage1, customerPercentage, windowAllocation1, date);
		bank1.OneDay();
		bank1.dailyReport();
		bank1.dailyStatistics();
	}

	public static void oneDay() {
		TaskTypePercentage taskTypePercentage1 = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
		CustomerTypePercentage customerPercentage = new CustomerTypePercentage(75,  25);
		WindowAllocation windowAllocation1 = new WindowAllocation(1, 2, 1);
		Bank bank1 = new Bank(taskTypePercentage1, customerPercentage, windowAllocation1);
		bank1.OneDay();
		bank1.dailyReport();
		bank1.dailyStatistics();
	}

	public static void simulate() throws CloneNotSupportedException {
		TaskTypePercentage taskTypePercentage1 = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
		TaskTypePercentage taskTypePercentage2 = new TaskTypePercentage(10, 10, 5, 5, 5, 40, 5, 20);
		CustomerTypePercentage customerPercentage = new CustomerTypePercentage(75,  25);
		WindowAllocation windowAllocation1 = new WindowAllocation(1, 2, 1);
		WindowAllocation windowAllocation2 = new WindowAllocation(1, 2, 1);  // or deep copy
		
		Bank bank1 = new Bank(taskTypePercentage1, customerPercentage, windowAllocation1);
		bank1.OneDay(0);  // 随机数种子为0
		bank1.dailyReport();
		bank1.dailyStatistics();
		
		
		Bank bank2 = new Bank(taskTypePercentage2, customerPercentage, windowAllocation2);
		System.out.println("********************************************");
		System.out.println("窗口分配情况: ");
		System.out.println(windowAllocation2);
		bank2.OneDay(0);
		bank1.dailyReport();
		bank1.dailyStatistics();
//		System.out.println("/-*/-/*-/*-/-/*-/*-/-" + windowAllocation2.getWindowNumber()[WindowType.EXPRESS.ordinal()]);
		
		long minGap = Math.abs(bank1.averageTime() - bank2.averageTime());
//		Bank minGapBank = (Bank) bank2.clone();
		WindowAllocation minGapWindowAllocation = (WindowAllocation) bank2.getWindowAllocation().clone();
		
		if (taskTypePercentage2.getIsExpressPercentage() < taskTypePercentage1.getIsExpressPercentage()) {
			// express task比例变小  
			while (windowAllocation2.getWindowNumber()[WindowType.EXPRESS.ordinal()] > 0) {
				// 循环得到最佳窗口配置
				windowAllocation2.getWindowNumber()[WindowType.EXPRESS.ordinal()]--;
				windowAllocation2.getWindowNumber()[WindowType.NORMAL.ordinal()]++;
				System.out.println("********************************************");
				System.out.println("窗口分配情况: ");
				System.out.println(windowAllocation2);
				bank2 = new Bank(taskTypePercentage2, customerPercentage, windowAllocation2);
				bank2.OneDay(0);
				bank1.dailyReport();
				bank1.dailyStatistics();
				long gap = Math.abs(bank1.averageTime() - bank2.averageTime());
//				System.out.println("*--*/-/-/-/-/-/-/-/" + gap);
//				System.out.println("与最小比较前" + minGapBank.getWindowAllocation());
//				System.out.println("minbank:" + minGapBank);
//				System.out.println("bank2:" + bank2);
				if (gap < minGap) {
					minGap = gap;
//					minGapBank = (Bank) bank2.clone();
					minGapWindowAllocation = (WindowAllocation) bank2.getWindowAllocation().clone();
					System.out.println("此次窗口开放情况与目标顾客平均办理时间更接近, 差为" + TimeShifting.mills2MinS(minGap) + "\n");
//					System.out.println(minGapBank);
//					System.out.println("此次窗口的开放情况为:\n" + minGapBank.getWindowAllocation());
//					System.out.println("minbank:" + minGapBank);
				}
			}
		} else {
			// express task 比例变大
			while (windowAllocation2.getWindowNumber()[WindowType.EXPRESS.ordinal()] >= 0) {
				// 循环得到最佳窗口配置
				windowAllocation2.getWindowNumber()[WindowType.EXPRESS.ordinal()]++;
				windowAllocation2.getWindowNumber()[WindowType.NORMAL.ordinal()]--;
				bank2 = new Bank(taskTypePercentage2, customerPercentage, windowAllocation2);
				bank2.OneDay(0);
				long gap = Math.abs(bank1.averageTime() - bank2.averageTime());
				if (gap < minGap) {
					minGap = gap;
					minGapWindowAllocation = (WindowAllocation) bank2.getWindowAllocation().clone();
					System.out.println("此次窗口开放情况与目标顾客平均办理时间更接近, 差为" + TimeShifting.mills2MinS(minGap) + "\n");
//					
				}
			}
		}
		
//		bank1.dailyReport();
//		bank1.dailyStatistics();
//		minGapBank.dailyReport();
//		minGapBank.dailyStatistics();
		System.out.println("********************************************");
		System.out.println("最佳窗口开放模式:\n" + minGapWindowAllocation);
//		System.out.println(minGapBank);
//		bank2.dailyReport();
//		bank2.dailyStatistics();
	}
};