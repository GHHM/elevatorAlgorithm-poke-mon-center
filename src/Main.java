import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws Exception {
		Control newC = new Control();
		PersonInfo[] person = new PersonInfo[20];

		int source = 0, dest = 0;
		int currentTime=0, totalTime = 48;
		person[0] = new PersonInfo(0, 0, 0);

		frame f = new frame();

		for (int i = 0; i < person.length; i++) {
			source = (int) (Math.random() * 5) + 1;
			dest = (int) (Math.random() * 5) + 1;
			if (source == dest && source <= 3)
				dest += 1;
			else if (source == dest && source > 3)
				dest -= 1;
			person[i] = new PersonInfo(source, dest, i);
			person[i].time = (int) (Math.random() * (totalTime * 0.7));
			if (i % 19 == 0 && i != 0)
				person[i].Emergency = Direction.EMERGENCY;
		}//랜덤으로 타는 층 내릴 층 응급...상황은 랜덤이 아니지만... ㅇㅇ 정해주는 코드!
		
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
			// 출퇴근 아이콘 바꾸기!
			
			System.out.println("현재 시간 : " + currentTime);// 현재시간 체크용! 언..언젠가 없앨거야!
														
			for (int j = 0; j < person.length; j++) {//사람의 숫자 만큼 계속 for문을 돌림
				if (person[j].time <= currentTime && person[j].getEntered() != 1) {
					if (person[j].wait == 1) {//wait이 1이면 그 사람은 엘베를 기다리고 있는 사람임 
						System.out.println("Person " + person[j].getPersonNum() + " (" + person[j].getSource() + " , "
								+ person[j].getDestination() + " )");// 이 사람의 목적지와 탈 층을 출력해줌
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
				if (newC.elevator1.getRequests().isEmpty() || !newC.elevator2.getRequests().isEmpty()
						|| !newC.elevator3.getRequests().isEmpty())//아 리퀘스트가 없다면 굳이 엘베는 안움직임
					newC.go(person, currentTime, totalTime);
			}
			currentTime++;


		}
		frame.endQuittingTime();
		frame.setStateText("end");
		
		System.out.println("Time out");
		int num = 0;// 완료된 사람 숫자 세려고
		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() == 1)
				num++;

		}
		System.out.println(num);
	}
}