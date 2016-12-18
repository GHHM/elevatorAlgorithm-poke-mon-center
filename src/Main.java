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
			person[i].time = ((int) (Math.random() * (totalTime / totalTime) * ((totalTime/24)*11)))+((totalTime/24)*4);//5인데 4로 줄임
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
		while (currentTime != totalTime) {//현재 시간이 전체시간과 같지 않을 때 까지 돌림
			time =time + fraction*60 ;
			System.out.println("time: "+time);
			frame.timeDisplay(time);
			if (currentTime >= (totalTime/24)*8 && currentTime < (totalTime/24)*11)
			{
				frame.startOfficeGoing();// 이때는 출근시간이니까 출근시간이라고 해주고!
				frame.setStateText("commute");
			}
			if (currentTime >= (totalTime/24)*16 && currentTime< (totalTime/24)*20)
			{
				frame.startQuittingTime();// 이때는 퇴근시간이니까 퇴근시간이라고 해주고!
				frame.setStateText("commute");
			}
			if (currentTime < (totalTime / 24) * 8 || currentTime >= (totalTime / 24) * 11)
			{
				frame.endOfficeGoing();//이땐 출근시간이 아니야
			}
			if (currentTime < (totalTime / 24) * 16 || currentTime >= (totalTime / 24) * 20)
			{
				frame.endQuittingTime();//퇴근시간이 아니야!
			}
			if((currentTime < (totalTime / 24) * 8 || currentTime >= (totalTime / 24) * 11)&&(currentTime < (totalTime / 24) * 16 || currentTime >= (totalTime / 24) * 20))
			{
				frame.setStateText("normal");
			}
			// Change icon! sun to moon vice versa
			
			System.out.println("현재 시간 : " + currentTime);// checking current time
														
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
							if (newC.pressButton(person[j].getSource(), Direction.UP, person[j].Emergency) == 1) {//pressButton의 return 값이 1이여야만 엘베 버튼을 누를 수 있음.
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
									person[j].Emergency) == 2) {// return 값이 2이면 emergency 상황이라는 것이고 이걸 처리하려면 다른 함수안으로 들어가야 함.
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