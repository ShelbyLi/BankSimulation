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
		System.out.println("\t\t\t|\t业务编号\t客户等级\t办理业务");
		int customerNum = 0;  // 假设每个用户只来一次
		int taskNum = 0;
		while (System.currentTimeMillis() - bank.getStartTime()< bank.getOpeningHours()) {
			bank.getLock().lock();
			try {
				CustomerType randomCustomerType = bank.getCustomerTypePercentage().getRandomType();
				Customer c = new Customer(customerNum, randomCustomerType, "Customer"+customerNum++);  // 生成随机客户
				TaskType randomTaskType = bank.getTaskTypePercentage().getRandomType();  // 生成随机任务类型
				
				if (c.getCustomerType().equals(CustomerType.NORMAL)) {  // 普通用户
					bank.getWaitingNotVIPTasks().add(new Task(taskNum++, randomTaskType, c));  // 
				} else {
					bank.getWaitingVIPTasks().add(new Task(taskNum++, randomTaskType, c));
				}
				System.out.println("\t\t\t|\t" + String.format("%03d", (taskNum-1)) + "\t" + c.getCustomerType() + "\t" + randomTaskType);
//						"\t此时任务长度: " + bank.getWaitingNotVIPTasks().size()  + "\t此时vip任务长度: " + bank.getWaitingVIPTasks().size());
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
//				Thread.sleep((int)(Math.random() * bank.getCUSTOMER_INTERVAL()));  // 到下一个顾客来的时间间隔
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			
		}
	}
	
}
