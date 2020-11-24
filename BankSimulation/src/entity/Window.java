package entity;
import util.WindowType;

public class Window implements Runnable{
	private int id;
	private WindowType type;
	private Bank bank;
//	private Task processingTask;

	public Window(int id, WindowType type, Bank bank) {
		super();
		this.id = id;
		this.type = type;
		this.bank = bank;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Task task = getNextTask();
			// 如果目前没有task 等待
			if (task != null) {
				processTask(task);
			} else if (System.currentTimeMillis()-bank.getStartTime() > bank.getOpeningHours()) {
				// 没有处理任务且已过了下班时间
				System.out.println(this.id + "号窗口 暂停服务");
//				System.out.println("***window" + this.id + "下班" + 
//							"\t此时任务长度: " + bank.getWaitingNotVIPTasks().size() + 
//							"\t此时vip任务长度: " + bank.getWaitingVIPTasks().size());
				break;
			}
		}
	}
	

	private void processTask(Task task) {
		// TODO Auto-generated method stub
		task.setProcessStartTime(System.currentTimeMillis());
		task.setProcessWindow(this.id);
		long processingTime = (long)((Math.random()*task.getTaskType().getInterval() + task.getTaskType().getLowerBound())*bank.getUNIT_TIME());
		System.out.println("[Ding--]请 " + String.format("%03d", task.getId()) + " 号到 " + this.id + " 号窗口\t|");
//		System.out.println("Window" + this.id + " 开始处理 task" + task.getId());
		try {
			Thread.sleep(processingTime);  // 处理时间
//			task.testProcessTime++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		task.setProcessEndTime(System.currentTimeMillis());  // task的处理时间由真正的开始 结束时间相减 而不是直接记录得到的随机数
		
		bank.getLock().lock();
		try {
//			if (task != null)
//				System.out.println("----processTask  taskID = " + task.getId());
			bank.getProcessedTasks().add(task);
//			System.out.println("Window" + this.id + " 处理完 task" + task.getId());
//			System.out.println("Window" + this.id + " 处理完 task" + task.getId() + "\ttest次数: " + task.testProcessTime);
		} finally {
			bank.getLock().unlock();
		}
	}

	private Task getNextTask() {
		// 叫号
		bank.getLock().lock();  // 对tasks要进行操作前要上锁
		Task task = null;
		try {
			if (type.equals(WindowType.VIP)) {
				task = vipWindowGetNextTask();
			} else if (type.equals(WindowType.EXPRESS)){
				task = expressWindowGetNextTask();
			} else {
				task = normalWindowGetNextTask();
			}
//			if (task != null) {
//				System.out.println("window" + id + " 得到了task" + task.getId());
//				System.out.println("此时队列中 " + bank.getWaitingNotVIPTasks().size() + " " + bank.getWaitingVIPTasks().size());
//			}
//			if (task != null)
//				System.out.println("----getNextTask  taskID = " + task.getId());
			
		} finally {
			bank.getLock().unlock();
		}
		
		return task;
	}

	private Task vipWindowGetNextTask() {
		Task task = null;
		if (!bank.getWaitingVIPTasks().isEmpty()) {
			task = bank.getWaitingVIPTasks().remove(0);
		} else if (!bank.getWaitingNotVIPTasks().isEmpty()){
			task = bank.getWaitingNotVIPTasks().remove(0);
		}
		
		
		return task;
	}
	
//	private Task expressWindowGetNextTask() {
//		// FIXME 还是会出现重复的为什么啊啊啊啊
//		Task task = null;
//		for (int i = 0; i < bank.getWaitingNotVIPTasks().size(); i++) {
//			task = bank.getWaitingNotVIPTasks().get(i);
//			if (task.isExpressTask()) {
////				System.out.println("express window" + this.id + "处理notVIP前 " + bank.getWaitingNotVIPTasks().size() + " i=" + i);
//				task = bank.getWaitingNotVIPTasks().remove(i);
////				System.out.println("express window" + this.id + "处理notVOP后 taskid=" + task.getId() + "  " + bank.getWaitingNotVIPTasks().size());
//				break;
//			}
//		}
//		
//		for (int i = 0; i < bank.getWaitingVIPTasks().size(); i++) {  // 普通客户没有快速业务 vip在等待时 办理vip业务
//			task = bank.getWaitingVIPTasks().get(i);
//			if (task.isExpressTask()) {
////				System.out.println("express window" + this.id + "处理VIP前" + bank.getWaitingVIPTasks().size() + " i=" + i);
//				task = bank.getWaitingVIPTasks().remove(i);
////				System.out.println("express window" + this.id + "处理VIP后 taskid=" + task.getId() + bank.getWaitingVIPTasks().size());
//				
//				break;
//			}
//		}
////		if (task != null)
////		System.out.println("----expressWindowGetNextTask  taskID = " + task.getId());
//		return task;
//	}
	
	private Task expressWindowGetNextTask() {
		// 如果不走循环 跟普通一样取第一个的话
		Task task = null;
		if (!bank.getWaitingNotVIPTasks().isEmpty()) {  // 一定要加!!! 否则会重复  不知道为什么 空了再看
			for (int i = 0; i < bank.getWaitingNotVIPTasks().size(); i++) {
				if (bank.getWaitingNotVIPTasks().get(i).isExpressTask()) {
					task = bank.getWaitingNotVIPTasks().remove(i);
				}
			}
//			task = bank.getWaitingNotVIPTasks().remove(0);
		} else if (!bank.getWaitingVIPTasks().isEmpty()){
			for (int i = 0; i < bank.getWaitingVIPTasks().size(); i++) {
				if (bank.getWaitingVIPTasks().get(i).isExpressTask()) {
					task = bank.getWaitingVIPTasks().remove(i);
				}
			}
//			task = bank.getWaitingVIPTasks().remove(0);
		}
		
		return task;
	}
	
	private Task normalWindowGetNextTask() {
		Task task = null;
		if (!bank.getWaitingNotVIPTasks().isEmpty()) {
			task = bank.getWaitingNotVIPTasks().remove(0);
		} else if (!bank.getWaitingVIPTasks().isEmpty()){
			task = bank.getWaitingVIPTasks().remove(0);
		}
		
		return task;
		
	}
}

	
