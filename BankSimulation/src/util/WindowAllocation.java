package util;

public class WindowAllocation implements Cloneable{
	private int[] windowNumber;
	
	public WindowAllocation(int normalWindowNum, int expressWindowNum, int vipWindowNum) {
		super();
		int windowTypeNum = WindowType.values().length;
		windowNumber = new int[windowTypeNum];
		
		windowNumber[WindowType.NORMAL.ordinal()] = normalWindowNum;
		windowNumber[WindowType.EXPRESS.ordinal()] = expressWindowNum;
		windowNumber[WindowType.VIP.ordinal()] = vipWindowNum;
		
	}
	
	public int[] getWindowNumber() {
		return windowNumber;
	}
	
	public void setWindowNumber(int[] windowNumber) {
		this.windowNumber = windowNumber;
	}

	@Override
	public String toString() {
		
		
		return "" + WindowType.NORMAL + ": " + windowNumber[WindowType.NORMAL.ordinal()] + "\n"
				+ WindowType.EXPRESS + ": " + windowNumber[WindowType.EXPRESS.ordinal()] + "\n"
				+ WindowType.VIP + ": " + windowNumber[WindowType.VIP.ordinal()] + "\n";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		WindowAllocation windowAllocationClone = (WindowAllocation) super.clone();
		windowAllocationClone.windowNumber = new int[this.windowNumber.length];  // important
		System.arraycopy(windowNumber, 0, windowAllocationClone.windowNumber, 0, windowNumber.length);
		return windowAllocationClone;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		WindowAllocation test = new WindowAllocation(1, 2, 1);
		WindowAllocation test2 = (WindowAllocation) test.clone();
		
		System.out.println("test" + test + test.windowNumber);
		System.out.println("test2" + test2 + test2.windowNumber);
		test.windowNumber[1] ++;
		System.out.println("test" + test);
		System.out.println("test2" + test2);
	}
}
