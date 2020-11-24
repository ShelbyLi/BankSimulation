package entity;
import util.TaskType;
//public class Task implements Runnable{
public class Task{
	private int id;
	private TaskType taskType;
	private Customer customer;
	// ����ʱ��
	private long createTime;
	// ���ڴ���
	private long processWindow;
	// ��ʼ����ʱ��
	private long processStartTime;
	// ��������ʱ��
	private long processEndTime;
	// ����ʱ��
//	public int testProcessTime = 0;  // ���Ը������Ƿ��ظ�����
	
	public Task(int id, TaskType taskType, Customer customer) {
		super();
		this.id = id;
		this.taskType = taskType;
		this.customer = customer;
		createTime = System.currentTimeMillis();
	}



	public TaskType getTaskType() {
		return taskType;
	}


	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}


	public long getProcessWindow() {
		return processWindow;
	}


	public void setProcessWindow(long processWindow) {
		this.processWindow = processWindow;
	}


	public long getProcessStartTime() {
		return processStartTime;
	}


	public void setProcessStartTime(long processStartTime) {
		this.processStartTime = processStartTime;
	}


	public long getProcessEndTime() {
		return processEndTime;
	}


	public void setProcessEndTime(long processEndTime) {
		this.processEndTime = processEndTime;
	}
	

	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public long getProcessTime() {
		return this.processEndTime - this.createTime;  // ����Ϊ�ӵ��ﵽ������� Ϊ�˿Ͱ���ʱ��
	}

	public boolean isExpressTask() {
		if (this.taskType.equals(TaskType.WITHDRAW) ||
			this.taskType.equals(TaskType.DEPOSIT) ||
			this.taskType.equals(TaskType.OPEN_ONLINE_BANKING) ||
			this.taskType.equals(TaskType.PAY_BILLS) ||
			this.taskType.equals(TaskType.RIMITTANCE)) {
			return true;
		} else {
			return false;
		}
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
