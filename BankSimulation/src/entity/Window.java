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
			// ���Ŀǰû��task �ȴ�
			if (task != null) {
				processTask(task);
			} else if (System.currentTimeMillis()-bank.getStartTime() > bank.getOpeningHours()) {
				// û�д����������ѹ����°�ʱ��
				System.out.println(this.id + "�Ŵ��� ��ͣ����");
//				System.out.println("***window" + this.id + "�°�" + 
//							"\t��ʱ���񳤶�: " + bank.getWaitingNotVIPTasks().size() + 
//							"\t��ʱvip���񳤶�: " + bank.getWaitingVIPTasks().size());
				break;
			}
		}
	}
	

	private void processTask(Task task) {
		// TODO Auto-generated method stub
		task.setProcessStartTime(System.currentTimeMillis());
		task.setProcessWindow(this.id);
		long processingTime = (long)((Math.random()*task.getTaskType().getInterval() + task.getTaskType().getLowerBound())*bank.getUNIT_TIME());
		System.out.println("[Ding--]�� " + String.format("%03d", task.getId()) + " �ŵ� " + this.id + " �Ŵ���\t|");
//		System.out.println("Window" + this.id + " ��ʼ���� task" + task.getId());
		try {
			Thread.sleep(processingTime);  // ����ʱ��
//			task.testProcessTime++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		task.setProcessEndTime(System.currentTimeMillis());  // task�Ĵ���ʱ���������Ŀ�ʼ ����ʱ����� ������ֱ�Ӽ�¼�õ��������
		
		bank.getLock().lock();
		try {
//			if (task != null)
//				System.out.println("----processTask  taskID = " + task.getId());
			bank.getProcessedTasks().add(task);
//			System.out.println("Window" + this.id + " ������ task" + task.getId());
//			System.out.println("Window" + this.id + " ������ task" + task.getId() + "\ttest����: " + task.testProcessTime);
		} finally {
			bank.getLock().unlock();
		}
	}

	private Task getNextTask() {
		// �к�
		bank.getLock().lock();  // ��tasksҪ���в���ǰҪ����
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
//				System.out.println("window" + id + " �õ���task" + task.getId());
//				System.out.println("��ʱ������ " + bank.getWaitingNotVIPTasks().size() + " " + bank.getWaitingVIPTasks().size());
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
//		// FIXME ���ǻ�����ظ���Ϊʲô��������
//		Task task = null;
//		for (int i = 0; i < bank.getWaitingNotVIPTasks().size(); i++) {
//			task = bank.getWaitingNotVIPTasks().get(i);
//			if (task.isExpressTask()) {
////				System.out.println("express window" + this.id + "����notVIPǰ " + bank.getWaitingNotVIPTasks().size() + " i=" + i);
//				task = bank.getWaitingNotVIPTasks().remove(i);
////				System.out.println("express window" + this.id + "����notVOP�� taskid=" + task.getId() + "  " + bank.getWaitingNotVIPTasks().size());
//				break;
//			}
//		}
//		
//		for (int i = 0; i < bank.getWaitingVIPTasks().size(); i++) {  // ��ͨ�ͻ�û�п���ҵ�� vip�ڵȴ�ʱ ����vipҵ��
//			task = bank.getWaitingVIPTasks().get(i);
//			if (task.isExpressTask()) {
////				System.out.println("express window" + this.id + "����VIPǰ" + bank.getWaitingVIPTasks().size() + " i=" + i);
//				task = bank.getWaitingVIPTasks().remove(i);
////				System.out.println("express window" + this.id + "����VIP�� taskid=" + task.getId() + bank.getWaitingVIPTasks().size());
//				
//				break;
//			}
//		}
////		if (task != null)
////		System.out.println("----expressWindowGetNextTask  taskID = " + task.getId());
//		return task;
//	}
	
	private Task expressWindowGetNextTask() {
		// �������ѭ�� ����ͨһ��ȡ��һ���Ļ�
		Task task = null;
		if (!bank.getWaitingNotVIPTasks().isEmpty()) {  // һ��Ҫ��!!! ������ظ�  ��֪��Ϊʲô �����ٿ�
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

	
