public class Main {

	public static Control newC = new Control();
	static int pNum = 100;
	public static PersonInfo[] person;
	public static int []waitingPersonNum={0,0,0,0,0,0};
	public static void main(String[] args) throws Exception {
		person = new PersonInfo[pNum];

		int source = 0, dest = 0;
		int currentTime=0, totalTime = 240;

		frame f = new frame();

		for (int i = 0; i < person.length; i++) {
			if(i>(pNum*0.1)&&i<(pNum*0.5))//office-going
			{
			source = (int) (Math.random() * 2) + 1;
			dest = (int) (Math.random() * 3) + 3;
			}
		else if(i>(pNum*0.6)&&i<(pNum*0.9))//quitting
		{
			dest = (int) (Math.random() * 2) + 1;
			source = (int) (Math.random() * 3) + 3;	
		}
		else
		{
			source = (int) (Math.random() * 5) + 1;
			dest = (int) (Math.random() * 5) + 1;
		}
		if (source == dest && source <= 3)
			dest += 1;
		else if (source == dest && source > 3)
			dest -= 1;
		person[i] = new PersonInfo(source, dest, i);
		
		if(i<(pNum*0.1))
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*8)));
		else if(i>(pNum*0.1)&&i<(pNum*0.5))
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*8)))+((totalTime/24)*4);
		else if(i>(pNum*0.4)&&i<(pNum*0.6))
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*11)))+((totalTime/24)*4);//5�ε� 4�� ����
		else if(i>(pNum*0.6)&&i<(pNum*0.9))
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*16)))+((totalTime/24)*4);
		else
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*20)))+((totalTime/24)*3);
		if (i % 50 == 0 && i != 0)
			person[i].Emergency = Direction.EMERGENCY;
		}//Assign emergency state
		
		double fraction = 24/(double)totalTime;	//0.5
		double time = 0;
		System.out.println("fraction : "+fraction);
		while (currentTime != totalTime) {//���� �ð��� ��ü�ð��� ���� ���� �� ���� ����
			time =time + fraction*60 ;
			System.out.println("time: "+time);
			frame.timeDisplay(time);
			if (currentTime >= (totalTime/24)*8 && currentTime < (totalTime/24)*11)
			{
				frame.startOfficeGoing();// �̶��� ��ٽð��̴ϱ� ��ٽð��̶�� ���ְ�!
				frame.setStateText("commute");
			}
			if (currentTime >= (totalTime/24)*16 && currentTime< (totalTime/24)*20)
			{
				frame.startQuittingTime();// �̶��� ��ٽð��̴ϱ� ��ٽð��̶�� ���ְ�!
				frame.setStateText("commute");
			}
			if (currentTime < (totalTime / 24) * 8 || currentTime >= (totalTime / 24) * 11)
			{
				frame.endOfficeGoing();//�̶� ��ٽð��� �ƴϾ�
			}
			if (currentTime < (totalTime / 24) * 16 || currentTime >= (totalTime / 24) * 20)
			{
				frame.endQuittingTime();//��ٽð��� �ƴϾ�!
			}
			if((currentTime < (totalTime / 24) * 8 || currentTime >= (totalTime / 24) * 11)&&(currentTime < (totalTime / 24) * 16 || currentTime >= (totalTime / 24) * 20))
			{
				frame.setStateText("normal");
			}
			// Change icon! sun to moon vice versa
			
			System.out.println("���� �ð� : " + currentTime);// checking current time
														
			for (int j = 0; j < person.length; j++) {
				if(person[j].time==currentTime&& person[j].getEntered() != 1)
				{
					waitingPersonNum[person[j].getSource()]++;
					frame.setFloorWaitingNum(person[j].getSource());
				}
				if (person[j].time <= currentTime && person[j].getEntered() != 1) {
					if (person[j].wait == 1) {// if 'wait' is 1, the person is waiting elevator 
						System.out.println("Person " + person[j].getPersonNum() + " (" + person[j].getSource() + " , "
								+ person[j].getDestination() + " )");// print on screen source and destination floor
						if (person[j].getSource() - person[j].getDestination() < 0) {
							if (newC.pressButton(person[j].getSource(), Direction.UP, person[j].Emergency) == 1) {//pressButton�� return ���� 1�̿��߸� ���� ��ư�� ���� �� ����.
								person[j].wait = 0;
								person[j].direction = Direction.UP;
							} else if (newC.pressButton(person[j].getSource(), Direction.UP,
									person[j].Emergency) == 2) {
								person[j].direction = Direction.UP;
								newC.EmergencyHandling(person, j);
							}
						} else if (person[j].getSource() - person[j].getDestination() > 0) {
							if (newC.pressButton(person[j].getSource(), Direction.DOWN, person[j].Emergency) == 1) {
								person[j].wait = 0;
								person[j].direction = Direction.DOWN;
							} else if (newC.pressButton(person[j].getSource(), Direction.UP,
									person[j].Emergency) == 2) {// return ���� 2�̸� emergency ��Ȳ�̶�� ���̰� �̰� ó���Ϸ��� �ٸ� �Լ������� ���� ��.
								person[j].direction = Direction.DOWN;
								newC.EmergencyHandling(person, j);
							}
						}
					}
				}
			}
			if (currentTime > 1) {
					newC.go(person, currentTime, totalTime);
			}
			currentTime++;


		}
		frame.endQuittingTime();
		frame.setStateText("end");
		
		System.out.println("Time out");
		int num = 0;// number of completed request
		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() == 1)
				num++;

		}
		System.out.println(num);
	}
}