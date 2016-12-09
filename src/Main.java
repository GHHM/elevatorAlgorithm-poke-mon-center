import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws Exception {
		Control newC = new Control();
		PersonInfo[] person = new PersonInfo[201];

		int source=0,dest=0;
		int currentTime=0, totalTime = 800;
		person[0] = new PersonInfo(0,0,0);
		
		frame f = new frame();
		
		for(int i=1;i<person.length;i++)
		{
			source= (int)(Math.random() * 5)+1;
			dest= (int)(Math.random() * 5)+1;
			if(source==dest&&source<=3)
				dest+=1;
			else if(source==dest&&source>3)
				dest-=1;
			person[i] = new PersonInfo(source,dest,i);
			person[i].time =(int)(Math.random() * (totalTime*0.7));
			if(i%500==0&&i!=0)
				person[i].Emergency= Direction.EMERGENCY;
		}
		int a=0;
		while(currentTime!=totalTime)
		{	
			System.out.println("현재 시간 : "+currentTime);//현재시간 체크용 없앨거임! 나중에... 언젠가??? 내코드는 언제 깔끔해질까...
			for(int j=1;j<person.length;j++){
			if(person[j].time<=currentTime&&person[j].getEntered()!=1){//뒤에서 문 열렸다 닫혔다 할 때 3초 더해서 미리 넣어주는게 있어서 그것 때문에 추가!
				if(person[j].wait==1){
				System.out.println("Person "+person[j].getPersonNum()+" ("+person[j].getSource()+" , "+person[j].getDestination()+" )");
				if(person[j].getSource()-person[j].getDestination()<0){
					if(newC.pressButton(person[j].getSource(), Direction.UP,person[j].Emergency)==1){
						person[j].wait=0;
						person[j].direction = Direction.UP;
						//System.out.println(j+" 가 request 받");
					}
					else if(newC.pressButton(person[j].getSource(), Direction.UP,person[j].Emergency)==2)
					{
						person[j].direction=Direction.UP;
						newC.EmergencyHandling(person,j);
					}
				}
				else if(person[j].getSource()-person[j].getDestination()>0){
					if(newC.pressButton(person[j].getSource(), Direction.DOWN,person[j].Emergency)==1){
						person[j].wait=0;
						person[j].direction = Direction.DOWN;
						//System.out.println(j+" 가 request 받");
					}
					else if(newC.pressButton(person[j].getSource(), Direction.UP,person[j].Emergency)==2)
					{
						person[j].direction=Direction.DOWN;
						newC.EmergencyHandling(person,j);
					}
				}
					}
				}
			}
			if(currentTime>1){
			if(newC.elevator1.getRequests().isEmpty()||!newC.elevator2.getRequests().isEmpty()||!newC.elevator3.getRequests().isEmpty())
				newC.go(person,currentTime,totalTime);
			}
		currentTime++;
		
		int c = 0;
		for(int i = 1;i<person.length;i++)
		{
			if(i==1)
				c=0;
			if(person[i].getFinished()==1)
				c++;
		}
		if((c+1)==person.length)
			break;
		}
		System.out.println("Time out");
		int num=0;//완료된 사람 숫자 세려고
		for(int i=0;i<person.length;i++)
		{
			if(person[i].getFinished()==1)
				num++;
				
		}
		System.out.println(num);
	}
}
