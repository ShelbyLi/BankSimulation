package entity;
import util.TaskType;
//public class Task implements Runnable{
public class Task{
	private int id;
	private TaskType taskType;
	private Customer customer;
	// 创建时间
	private long createTime;
	// 所在窗口
	private long processWindow;
	// 开始处理时间
	private long processStartTime;
	// 结束处理时间
	private long processEndTime;
	// 处理时间
//	public int testProcessTime = 0;  // 测试该任务是否被重复处理
	
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
		return this.processEndTime - this.createTime;  // 定义为从到达到办理完成 为顾客办理时间
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
