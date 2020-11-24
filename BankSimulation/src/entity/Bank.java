package entity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import util.CustomerTypePercentage;
import util.TaskTypePercentage;
import util.TimeShifting;
import util.WindowAllocation;
import util.TaskType;
import util.WindowType;

public class Bank{  
	private long OPENING_HOURS = 9*60*60;
	private long CUSTOMER_INTERVAL = 800;
	private int UNIT_TIME = 800;  // ��λʱ��
	private TaskTypePercentage taskTypePercentage;
	private CustomerTypePercentage customerTypePercentage;
	private WindowAllocation windowAllocation;
	private Date date;
	
	private Lock lock = new ReentrantLock();
	private List<Task> processedTasks;
	private List<Task> waitingNotVIPTasks;
	private List<Task> waitingVIPTasks;
	private long startTime;	

	public Bank(TaskTypePercentage taskTypePercentage, CustomerTypePercentage customerTypePercentage,
		WindowAllocation windowAllocation) {
		super();
		this.taskTypePercentage = taskTypePercentage;
		this.customerTypePercentage = customerTypePercentage;
		this.windowAllocation = windowAllocation;
		this.startTime = System.currentTimeMillis();
		this.waitingNotVIPTasks = new ArrayList<>();
		this.waitingVIPTasks = new ArrayList<>();
		this.processedTasks = new ArrayList<>();
		this.date = new Date();
	}


	public Bank(TaskTypePercentage taskTypePercentage, CustomerTypePercentage customerTypePercentage,
			WindowAllocation windowAllocation, Date date) {
		this.taskTypePercentage = taskTypePercentage;
		this.customerTypePercentage = customerTypePercentage;
		this.windowAllocation = windowAllocation;
		this.startTime = System.currentTimeMillis();
		this.waitingNotVIPTasks = new ArrayList<>();
		this.waitingVIPTasks = new ArrayList<>();
		this.processedTasks = new ArrayList<>();
		this.date = date;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		if (sdf.format(date).equals("15")) {
			this.setCUSTOMER_INTERVAL(1000);
			
		}
	}


	public void OneDay() {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		// ����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		System.out.println("==================" + sdf.format(date) + " ��ʼӪҵ=================" );
//		Bank bank = new Bank();
//		bank.taskTypePercentage = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
//		bank.customerTypePercentage = new CustomerTypePercentage(80,  20);
		
		// ��4������
		windowGenerator(executor, this);
		// ���ɿͻ���ҵ��
		executor.execute(new TaskGenerator(this));
		// ����
		executor.shutdown();  // ���ٽ����߳�����
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);  // �ȴ��̳߳��߳�ִ�����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("==================" + sdf.format(date) + " ����Ӫҵ=================" );
		// ��������Ӫҵ��� ��ͳ��
//		dailyReport();
//		dailyStatistics();
	}
	
	public void OneDay(int randomSeed) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		// ����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		System.out.println("==================" + sdf.format(date) + " ��ʼӪҵ=================" );
//		Bank bank = new Bank();
//		bank.taskTypePercentage = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
//		bank.customerTypePercentage = new CustomerTypePercentage(80,  20);
		
		// ��4������
		windowGenerator(executor, this);
		// ���ɿͻ���ҵ��
		executor.execute(new TaskGenerator(this, randomSeed));
		// ����
		executor.shutdown();  // ���ٽ����߳�����
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);  // �ȴ��̳߳��߳�ִ�����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("==================" + sdf.format(date) + " ����Ӫҵ=================" );
		
		// ��������Ӫҵ��� ��ͳ��
//		dailyReport();
//		dailyStatistics();
	}


	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		// ����
		System.out.println("=================Open!=================");
//		Bank bank = new Bank();
//		bank.taskTypePercentage = new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10);
//		bank.customerTypePercentage = new CustomerTypePercentage(80,  20);
		Bank bank = new Bank(new TaskTypePercentage(20, 20, 10, 10, 5, 15, 10, 10),
							new CustomerTypePercentage(80,  20),
							new WindowAllocation(1, 2, 1));
		
//		CountDownLatch countDownLatch = new CountDownLatch();
		// ��4������
		bank.windowGenerator(executor, bank);
		// ���ɿͻ���ҵ��
//		excutor.execute(new TaskGenerator());
//		bank.taskGenerator(bank);
		executor.execute(new TaskGenerator(bank));
		// ����
		executor.shutdown();  // ���ٽ����߳�����
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);  // �ȴ��̳߳��߳�ִ�����
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ��������Ӫҵ��� ��ͳ��
		bank.dailyReport();
		bank.dailyStatistics();
	}



	public void dailyReport() {
		System.out.println("--------------Ӫҵ״��--------------");
		System.out.println("�ͻ�����\t\t����ʱ��\t\tҵ������\t\t����ʱ��");
		for (Task task: this.processedTasks) {
			System.out.println(task.getCustomer().getCustomerName() + "\t"
					+ TimeShifting.mills2HMinS(this.getStartTime(), task.getCreateTime()) + "\t" 
					+ task.getTaskType() + "\t\t"
					+ TimeShifting.mills2MinS(task.getProcessTime()));
		}
	}
	
	public void dailyStatistics() {
		int[] counts = countTaskType();
		System.out.println("--------------����ͳ��--------------");
		System.out.println("�˿�ƽ������ʱ��:\t" + TimeShifting.mills2MinS(this.averageTime()));
		System.out.println("��ҵ����ռ����:");
		for (int i = 0; i < TaskType.values().length; i++) {
			String percentage = String.format("%.2f", ((1.0 * counts[i] / this.getProcessedTasks().size())*100));
			System.out.println(TaskType.values()[i] + ":\t" + percentage + "%");
		}
		System.out.println();
		
	}


	public int[] countTaskType() {
		int[] counts = new int[TaskType.values().length];
		for (Task task: this.getProcessedTasks()) {
			counts[task.getTaskType().ordinal()]++;
		}
		return counts;
	}
	
	public long averageTime() {
		long avgTime = this.totalTime() / this.getProcessedTasks().size();
		
		return avgTime;
	}
	
	public long totalTime() {
		long sum = 0;
		for (Task task: this.getProcessedTasks()) {
			sum += task.getProcessTime();
		}
		return sum;
	}


	public void windowGenerator(ExecutorService excutor, Bank bank) {
		int winNum = 0; 
		for (int i = 0; i < bank.windowAllocation.getWindowNumber()[WindowType.NORMAL.ordinal()]; i++) {
			excutor.execute(new Window(winNum++, WindowType.NORMAL, bank));
		}
		for (int i = 0; i < bank.windowAllocation.getWindowNumber()[WindowType.EXPRESS.ordinal()]; i++) {
			excutor.execute(new Window(winNum++, WindowType.EXPRESS, bank));
		}
		for (int i = 0; i < bank.windowAllocation.getWindowNumber()[WindowType.VIP.ordinal()]; i++) {
			excutor.execute(new Window(winNum++, WindowType.VIP, bank));
		}
//		excutor.execute(new Window(winNum++, WindowType.EXPRESS, bank));
//		excutor.execute(new Window(winNum++, WindowType.EXPRESS, bank));
//		excutor.execute(new Window(winNum++, WindowType.VIP, bank));
	}



	public void taskGenerate(Bank bank) {
		
		
	}



	public List<Task> getWaitingNotVIPTasks() {
		return waitingNotVIPTasks;
	}

	public void setWaitingNotVIPTasks(List<Task> waitingNotVIPTasks) {
		this.waitingNotVIPTasks = waitingNotVIPTasks;
	}

	public List<Task> getWaitingVIPTasks() {
		return waitingVIPTasks;
	}
	
	public void setWaitingVIPTasks(List<Task> waitingVIPTasks) {
		this.waitingVIPTasks = waitingVIPTasks;
	}

	public List<Task> getProcessedTasks() {
		return processedTasks;
	}

	public void setProcessedTasks(List<Task> processedTasks) {
		this.processedTasks = processedTasks;
	}

	public Lock getLock() {
		return lock;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getUNIT_TIME() {
		return UNIT_TIME;
	}

	public long getOpeningHours() {
		return OPENING_HOURS;
	}

	public TaskTypePercentage getTaskTypePercentage() {
		return taskTypePercentage;
	}

	public void setTaskTypePercentage(TaskTypePercentage taskTypePercentage) {
		this.taskTypePercentage = taskTypePercentage;
	}

	public CustomerTypePercentage getCustomerTypePercentage() {
		return customerTypePercentage;
	}
	
	public void setCustomerTypePercentage(CustomerTypePercentage customerTypePercentage) {
		this.customerTypePercentage = customerTypePercentage;
	}

	public long getCUSTOMER_INTERVAL() {
		return CUSTOMER_INTERVAL;
	}


	public WindowAllocation getWindowAllocation() {
		return windowAllocation;
	}


	public void setWindowAllocation(WindowAllocation windowAllocation) {
		this.windowAllocation = windowAllocation;
	}


	public void setCUSTOMER_INTERVAL(long cUSTOMER_INTERVAL) {
		CUSTOMER_INTERVAL = cUSTOMER_INTERVAL;
	}


//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		Bank bankCloned = (Bank) super.clone();
//		// deep clone
//		bankCloned.windowAllocation = (WindowAllocation) windowAllocation.clone();  // deep clone
//
//		return super.clone();
//	}
	
}
