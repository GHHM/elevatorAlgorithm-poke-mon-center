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
		}//�������� Ÿ�� �� ���� �� ����...��Ȳ�� ������ �ƴ�����... ���� �����ִ� �ڵ�!
		
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
			// ����� ������ �ٲٱ�!
			
			System.out.println("���� �ð� : " + currentTime);// ����ð� üũ��! ��..������ ���ٰž�!
														
			for (int j = 0; j < person.length; j++) {//����� ���� ��ŭ ��� for���� ����
				if (person[j].time <= currentTime && person[j].getEntered() != 1) {
					if (person[j].wait == 1) {//wait�� 1�̸� �� ����� ������ ��ٸ��� �ִ� ����� 
						System.out.println("Person " + person[j].getPersonNum() + " (" + person[j].getSource() + " , "
								+ person[j].getDestination() + " )");// �� ����� �������� Ż ���� �������
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
				if (newC.elevator1.getRequests().isEmpty() || !newC.elevator2.getRequests().isEmpty()
						|| !newC.elevator3.getRequests().isEmpty())//�� ������Ʈ�� ���ٸ� ���� ������ �ȿ�����
					newC.go(person, currentTime, totalTime);
			}
			currentTime++;


		}
		frame.endQuittingTime();
		frame.setStateText("end");
		
		System.out.println("Time out");
		int num = 0;// �Ϸ�� ��� ���� ������
		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() == 1)
				num++;

		}
		System.out.println(num);
	}
}