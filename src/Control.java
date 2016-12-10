import java.util.ArrayList;
import java.util.Calendar;


public class Control {
   Elevator elevator1 = new Elevator(1);
   Elevator elevator2 = new Elevator(2);
   Elevator elevator3 = new Elevator(3);
   Elevator elevatorList[] = {elevator1,elevator2,elevator3};
   int a;
   void go(PersonInfo[] person,int currentTime,int totalTime) throws Exception{
	      System.out.println("Go 들어가기 전\n"+elevator1.toString());
	      System.out.println(elevator2.toString());
	      System.out.println(elevator3.toString());// 엘베 어떻게 되나 확인용임!
	  
      for (Elevator e : elevatorList){//모든 엘리베이터 리스트를 불러옴
    	 if (e.isIdle()){//만약에 엘리베이터가 비어있으면
            
            if (currentTime >= (totalTime/24)*8 && currentTime < (totalTime/24)*11&&!e.isEmergency())
            {//출근시간에 걸려있는데 비어있으면 무조건 1층으로 보냄. 출근시간엔 올라가는 사람이 많으니까.
            	if(e.getFloor()!=e.Top_Of_Floors){
            		e.addRequest(1);
            		e.setFirstDirection(e.getDirection());
            	}
            }else if (currentTime >= (totalTime/24)*16 && currentTime< (totalTime/24)*20&&!e.isEmergency())
               if(e.getFloor()!=e.Top_Of_Floors){//마찬가지로 퇴근시간엔 무조건 맨 끝층으로 보냄.
            	   e.addRequest(e.Top_Of_Floors);
            	   e.setFirstDirection(e.getDirection());
               }
         }
         //*/// 출퇴근 시간 검사 코드
         if (!e.getRequests().isEmpty()){ 
            if (e.getRequests().peek() == e.getFloor())
            {	
            	if(e.isEmergency())
            		{//emergency 상황에서는 우선 탑승을 위해 기다리지 않고 출발함.
            		e.getPassengers(person,e.getFloor(),currentTime);
            		continue;
            		} 
            		if(e.EwaitTime>=5)//혹시나 오류로 인해 늘어날까봐 해놓은 것 
            			a=1;
            		e.EwaitTime++;//그 층에 도착한 뒤 사람을 태워야 하기 때문에 기다리는 시간!
                	if(e.EwaitTime>1)//엘베는 0 1 2 즉 3초를 리퀘스트 받은 층에서 기다리고 출발함.
                		e.getPassengers(person,e.getFloor(),currentTime); 
            }
            else
            {		
            		e.EwaitTime=0;//움직이면 기다리는 시간을 초기화해줌.
            		e.move();
            }
         }
      }
      System.out.println("Go 들어간 후\n"+elevator1.toString());
      System.out.println(elevator2.toString());
      System.out.println(elevator3.toString());//엘리베이터 동작 확인용
     // }
   }

   int pressButton(int floor, Direction direction,Direction Emergency){
      int min=0;
      int shortestd1 =0, shortestd2=0;
    
      if(Emergency==Direction.EMERGENCY)//emergency면 비어있거나 비어있지 않아도 무조건 빠른 걸 골라야하기 때문에 조금 다른 알고리즘을 가지고 있어 다르게 다뤄줌.
    	  return 2;
      Elevator closestElevator = getClosestEnrouteElevator(floor,direction);//움직이고 있는 것 중 가장 가까운 엘리베이터
      Elevator closestElevator2 = getClosestIdleElevator(floor);//비어있는 것 중 가장 가까운 엘리베이터
      if(closestElevator!=null&&closestElevator2!=null)// 둘다 아닌 경우 둘 중에 뭐가 좋은가 비교 코드!
      {
    	  shortestd1 = Math.abs(closestElevator.getFloor() - floor);
    	  shortestd2 = Math.abs(closestElevator2.getFloor() - floor);
    	  min = Integer.min(shortestd1,shortestd2);
    	  
    	  if(min==shortestd1)
    	  {
    	     closestElevator.addRequest(floor);
    	  }
    	  else
    	  {
    		 closestElevator2.setFirstDirection(direction);
    	     closestElevator2.addRequest(floor);
    	  }
      }
      else if(closestElevator!=null&&closestElevator2==null)//다 enroute이야
      {
 	     closestElevator.addRequest(floor);
      }
      else if(closestElevator==null&&closestElevator2!=null)//Idle이야 다
      {
 	     closestElevator2.setFirstDirection(direction);
 	     closestElevator2.addRequest(floor);
      }

      else
    	  return -1;
      return 1;
  }

   private Elevator getClosestIdleElevator(int floor){//idle이면 방향이 없고 정지해 있으므로 가장 가까운 친구를 가져옴.
      Elevator closestElevator = null;
      int shortestDistance = Elevator.Top_Of_Floors;
      for (Elevator e : getNotEmergencyElevators()){
         if (e.isIdle()){
            if (Math.abs(e.getFloor() - floor) < shortestDistance){
               closestElevator = e;
               shortestDistance = Math.abs(e.getFloor() - floor);
            }
         }
      }
      return closestElevator;
   }

   private Elevator getClosestEnrouteElevator(int floor, Direction direction){
	   //움직이는 엘리베이터는 방향과 엘리베이터가 열려있는 시간 그리고 엘리베이터 안의 인원수를 고려해야 함.
      Elevator closestElevator = null;
      int shortestDistance = Elevator.Top_Of_Floors;
      for (Elevator e : getNotEmergencyElevators()){//emergency엘리베이터에 응급 환자외의 request가 받아지면 안되므로
    	  if(direction ==e.getFirstDirection()){
         if (e.getDirection() == Direction.UP&& direction == Direction.UP&&e.personNum<6)//만원 수
        	 {
            if (floor >= e.getFloor()&& floor <= Elevator.Top_Of_Floors){
            	if(e.getFloor()==floor)
            	{
            		if(e.EwaitTime<=2)//층이 같은 경우 열려있는 그 시간안에 타야하기 때문 0123이었는데 012로 고침!
            		{
                        if (Math.abs(e.getFloor() - floor) < shortestDistance){
                            closestElevator = e;
                            shortestDistance = Math.abs(e.getFloor() - floor);
                         }
            		}
            	}
            	else if(floor >e.getFloor())
            	{
                    if (Math.abs(e.getFloor() - floor) < shortestDistance){
                        closestElevator = e;
                        shortestDistance = Math.abs(e.getFloor() - floor);
                     }
            	}

            }
         }
         else if (e.getDirection() == Direction.DOWN&&direction == Direction.DOWN&&e.personNum<6&&e.EwaitTime<3){
            if (floor <= e.getFloor() && floor >= 1){
            	if(floor==e.getFloor())
            	{
            		if(e.EwaitTime<=2)//층이 같은 경우 열려있는 그 시간안에 타야하기 때문 0123이었는데 012로 고침!
            		{
                        if (Math.abs(e.getFloor() - floor) < shortestDistance){
                            closestElevator = e;
                            shortestDistance = Math.abs(e.getFloor() - floor);
                         }
            		}
            	}
            	else if(floor<e.getFloor())
            	{
                    if (Math.abs(e.getFloor() - floor) < shortestDistance){
                        closestElevator = e;
                        shortestDistance = Math.abs(e.getFloor() - floor);
                     }
            	}
            }
         }
      }
      }
      return closestElevator;
   }
   
   
   public ArrayList<Elevator> getNotEmergencyElevators(){//emergency를 제외한 엘리베이터를 찾는 코드
      ArrayList<Elevator> activeElevators = new ArrayList<Elevator>();
      for (Elevator e : elevatorList){
         if (!e.isEmergency())
            activeElevators.add(e);
      }
      return activeElevators;
   }
   public void EmergencyHandling(PersonInfo[] person,int pressedPersonNum)
   {
	      Elevator closestElevator = null;
	      int shortestDistance = Elevator.Top_Of_Floors;
	      for (Elevator e : elevatorList){//타고 있거나 비어있거나 상관 없이 request에서 가장 가까운 엘베를 고름
	    	  if (Math.abs(e.getFloor() - person[pressedPersonNum].getSource()) < shortestDistance){
                  closestElevator = e;
                  shortestDistance = Math.abs(e.getFloor() - person[pressedPersonNum].getSource());
               }
	       }
	      
	      closestElevator.setEmergency();//엘리베이터를 emergency로 상태를 바꿔줌
	      
	      for(int i=0;i<person.length;i++)
	      {
	    	  if(person[i].getFinished()!=1&&(closestElevator.getFirstDirection()==person[i].direction||closestElevator.isIdle()))
	    	  {
	    		  if(person[i].getEntered()==0&&person[i].wait==0)//아직 안들어갔고 버튼은 눌러진 상태
	    		  {
	    			  if(closestElevator.getRequests().contains(person[i].getSource()))
	    				{
	    				person[i].wait=1;//다시 버튼을 누르도록 함(새로운 엘리베이터 할당받게)
	    				}
	    		  }
	    		  else if(person[i].getEntered()==1&&person[i].getElevatorNum()==closestElevator.ElevatorNum)
	    		  {
	    			  if(closestElevator.getRequests().contains(person[i].getDestination()))
	    			  {
	    				  System.out.println("Person "+i+"  Dest: "+person[i].getDestination()+" is lefted because of Emergency situation.");
	    				  frame.MovePassengerToWait(i, closestElevator.getFloor());
	    				  //여기는 엘베 안에 탄 사람들을 handle해주는 과정입니다!
	    				  
	    				  if(person[i].getDestination()==closestElevator.getFloor())
	    				  {
	    					  person[i].setFinished(1);//목적지에 왔는데 중간에 emergency걸려서 4층에서 4층 가고 싶던 친구가 있어서 넣은 코드
	    					  closestElevator.personNum--;
	    					  continue;
	    				  }
	    				  person[i].setEntered(0);
	    				  closestElevator.personNum--;
	    				  person[i].wait=1;
	    				  person[i].setSource(closestElevator.getFloor());//타는 층이 원래 탄 층이 아니라 지금 내린 층으로 바뀌어야 하므로 재설정
	    			  }
	    		  }
	    	  }
	      }
	      
	      while(!closestElevator.getRequests().isEmpty())
	      {
	    	  closestElevator.getRequests().remove();//모든 사람이 내렸으므로 모든 request를 다시 지워주고!
	      }
	      person[pressedPersonNum].wait=0;//응급상황 요청이 된 사람은 리퀘스트를 받았으므로 기다리지 않게 합니다!
	      closestElevator.addRequest(person[pressedPersonNum].getSource());//응급 엘리베이터에 리퀘스트를 주고
	      closestElevator.setFirstDirection(person[pressedPersonNum].direction);
   }
}