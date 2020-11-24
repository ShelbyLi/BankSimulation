package entity;
import java.util.Random;

import util.CustomerType;
import util.TaskType;
public class TaskGenerator implements Runnable {

	Bank bank;
	Integer randomSeed;
	
	
	public TaskGenerator(Bank bank) {
		super();
		this.bank = bank;
	}


	public TaskGenerator(Bank bank, int randomSeed) {
		super();
		this.bank = bank;
		this.randomSeed = randomSeed;
	}


	@Override
	public void run() {
		System.out.println("\t\t\t|\tҵ����\t�ͻ��ȼ�\t����ҵ��");
		int customerNum = 0;  // ����ÿ���û�ֻ��һ��
		int taskNum = 0;
		while (System.currentTimeMillis() - bank.getStartTime()< bank.getOpeningHours()) {
			bank.getLock().lock();
			try {
				CustomerType randomCustomerType = bank.getCustomerTypePercentage().getRandomType();
				Customer c = new Customer(customerNum, randomCustomerType, "Customer"+customerNum++);  // ��������ͻ�
				TaskType randomTaskType = bank.getTaskTypePercentage().getRandomType();  // ���������������
				
				if (c.getCustomerType().equals(CustomerType.NORMAL)) {  // ��ͨ�û�
					bank.getWaitingNotVIPTasks().add(new Task(taskNum++, randomTaskType, c));  // 
				} else {
					bank.getWaitingVIPTasks().add(new Task(taskNum++, randomTaskType, c));
				}
				System.out.println("\t\t\t|\t" + String.format("%03d", (taskNum-1)) + "\t" + c.getCustomerType() + "\t" + randomTaskType);
//						"\t��ʱ���񳤶�: " + bank.getWaitingNotVIPTasks().size()  + "\t��ʱvip���񳤶�: " + bank.getWaitingVIPTasks().size());
			} finally {
				bank.getLock().unlock();
			}
			
			try {
				Random random;
				if (randomSeed != null) {
					random = new Random(0);
				} else {
					random = new Random();
				}
				
				Thread.sleep((int)(random.nextInt((int) bank.getCUSTOMER_INTERVAL())));
//				Thread.sleep((int)(Math.random() * bank.getCUSTOMER_INTERVAL()));  // ����һ���˿�����ʱ����
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			
		}
	}
	
}
