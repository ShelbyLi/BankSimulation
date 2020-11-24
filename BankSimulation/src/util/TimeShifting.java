package util;

import java.util.Date;

public class TimeShifting {
//	public static String mills2Date(long mills) {
//		int s = (int) (mills % 60);
//		mills /= 60;
//		int m = (int) mills;
////		mills /= 60;
////		int h = (int) (mills / 60);
//		System.out.println( "" + m + " " + s);
//		
//		return null;
//	}
	private static int getSeconds(long mills) {
		return (int) (mills % 60);
	}
	
	private static int getMinute(long mills) {
		mills /= 60;
		return (int) (mills % 60);
	}
	private static int getHour(long mills) {
		mills /= 60;
		return (int) (mills / 60);
	}
	public static String mills2MinS(long mills) {
		
		return (getHour(mills) * 60 +getMinute(mills)) + "'" + getSeconds(mills) + "''";
	}
	public static String mills2HMinS(long startTime, long endTime) {
		long interval = endTime - startTime;
//		interval = 3605;
		String h = String.format("%02d", getHour(interval)+8);
		String m = String.format("%02d", getMinute(interval));
		String s = String.format("%02d", getSeconds(interval));
//		return (getHour(interval)+8)+":" + getMinute(interval) + ":" + getSeconds(interval) + "   ";
		return h + ":" + m + ":" + s;
	}

	public static void main(String[] args) {
//		TimeShifting.mills2Date(125);
		System.out.println(mills2MinS(125));
		System.out.println(mills2HMinS(1, 2));
	}
}
