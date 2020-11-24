package util;
public enum TaskType {
	DEPOSIT(0.5, 1.5, "���"), 
	WITHDRAW(0.5, 1.5, "ȡ��"), 
	PAY_THE_FINE(1.2, 2, "���ɷ���"), 
	OPEN_ONLINE_BANKING(5, 8, "��ͨ����"),
	PAY_BILLS(1.5, 2, "��ˮ���"), 
	PURCHASE_FUND(2, 3, "�������"), 
	RIMITTANCE(3, 4, "ת�˻��"), 
	PERSONAL_LOAN_REPAYMENT(2, 4, "��������");
	
	private double lowerBound;
	private double upperBound;
	private String name;
//	TaskType(double lowerBound, double upperBound) {
//		this.lowerBound = lowerBound;
//		this.upperBound = upperBound;
//	}
	
	private TaskType(double lowerBound, double upperBound, String name) {
	this.lowerBound = lowerBound;
	this.upperBound = upperBound;
	this.name = name;
	}
	public double getLowerBound() {
		return lowerBound;
	}
	public double getUpperBound() {
		return upperBound;
	}	
	
	public double getInterval() {
		return upperBound - lowerBound;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
