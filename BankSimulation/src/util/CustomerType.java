package util;

public enum CustomerType {
	NORMAL("普通用户"),
	VIP("VIP用户");
	private String name;

	private CustomerType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
