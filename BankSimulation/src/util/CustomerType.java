package util;

public enum CustomerType {
	NORMAL("��ͨ�û�"),
	VIP("VIP�û�");
	private String name;

	private CustomerType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
