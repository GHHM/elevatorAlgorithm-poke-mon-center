import java.util.ArrayList;
import java.util.Calendar;


public class Control {
   Elevator elevator1 = new Elevator(1);
   Elevator elevator2 = new Elevator(2);
   Elevator elevator3 = new Elevator(3);
   Elevator elevatorList[] = {elevator1,elevator2,elevator3};
   int a;
   void go(PersonInfo[] person,int currentTime,int totalTime) throws Exception{
	      System.out.println("Go ���� ��\n"+elevator1.toString());
	      System.out.println(elevator2.toString());
	      System.out.println(elevator3.toString());
	  
      for (Elevator e : elevatorList){
    	 if (e.isIdle()){
            //if current time is between 8am and 10am and elevator is idle, send the elevator to the first floor
            if (currentTime >= (totalTime/24)*8 && currentTime < (totalTime/24)*11&&!e.isEmergency())
            {
            	if(e.getFloor()!=e.NUM_OF_STORIES){
            		e.addRequest(1);
            		e.setFirstDirection(e.getDirection());
            	}
            }else if (currentTime >= (totalTime/24)*16 && currentTime< (totalTime/24)*20&&!e.isEmergency())
               if(e.getFloor()!=1){//��ü �� ���� 10������ �Ǿ�����
            	   e.addRequest(e.NUM_OF_STORIES);
            	   e.setFirstDirection(e.getDirection());
               }
         }
         //*/// ����� �ð� �˻� �ڵ�
         if (!e.getRequests().isEmpty()){ //check that the elevator has at least one request.
            if (e.getRequests().peek() == e.getFloor())
            {	
            		if(e.EwaitTime>=5)
            			a=1;
            		e.EwaitTime++;
                	if(e.EwaitTime>2)
                		e.getPassengers(person,e.getFloor(),currentTime); //the elevator is on a requested floor. Get the passengers
            }
            else
            {		
            		e.EwaitTime=0;
            		e.move();
            }
         }//����� �¿�� ���� : �� ����� ���纸�� ���� �ð��� �԰�, ���� Ÿ�� �ְų� �Ϸ�� ���°� �ƴҰ�쿡 �¿��(���� ������ �ִ�)!
         // ����� ������ ���� : �� ����� ź ���������Ͷ� ���� ������ȣ�� ����, ���� Ÿ�� �ִ� ����! �Ϸ�� ���¸� ������ ����.
      }
      System.out.println("Go �� ��\n"+elevator1.toString());
      System.out.println(elevator2.toString());
      System.out.println(elevator3.toString());//*/
     // }
   }

   /*
    * method to be run when an "up" or "down" button is pressed on a floor OUTSIDE the elevator
    */
   int pressButton(int floor, Direction direction,Direction Emergency){
      int min=0;
      int shortestd1 =0, shortestd2=0;
    
      if(Emergency==Direction.EMERGENCY)
    	  return 2;
      Elevator closestElevator = getClosestEnrouteElevator(floor,direction);
      Elevator closestElevator2 = getClosestIdleElevator(floor);
      if(closestElevator!=null&&closestElevator2!=null)// �Ѵ� �ƴ� ��� �� �߿� ���� ������ �� �ڵ�!
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
      else if(closestElevator!=null&&closestElevator2==null)//�� enroute�̾�
      {
 	     closestElevator.addRequest(floor);
      }
      else if(closestElevator==null&&closestElevator2!=null)//Idle�̾� ��
      {
 	     closestElevator2.setFirstDirection(direction);
 	     closestElevator2.addRequest(floor);
      }

      else
    	  return -1;
      return 1;
  }

   /*
    * helper method for pressButton to get the closest idle elevator
    */
   private Elevator getClosestIdleElevator(int floor){
      Elevator closestElevator = null;
      int shortestDistance = Elevator.NUM_OF_STORIES;
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
   
   /*
    * helper method for pressButton to get the closest enroute elevator
    */
   private Elevator getClosestEnrouteElevator(int floor, Direction direction){
      Elevator closestElevator = null;
      int shortestDistance = Elevator.NUM_OF_STORIES;
      for (Elevator e : getNotEmergencyElevators()){
    	  if(direction ==e.getFirstDirection()){
         if (e.getDirection() == Direction.UP&& direction == Direction.UP&&e.personNum<15){
            if (floor >= e.getFloor()&& floor <= Elevator.NUM_OF_STORIES){
            	if(e.getFloor()==floor)
            	{
            		if(e.EwaitTime<2)
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
         else if (e.getDirection() == Direction.DOWN&&direction == Direction.DOWN&&e.personNum<15&&e.EwaitTime<3){
            if (floor <= e.getFloor() && floor >= 1){
            	if(floor==e.getFloor())
            	{
            		if(e.EwaitTime<2)
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
   
   
   /*
    * return an array list of the elevators that are active
    */
   public ArrayList<Elevator> getNotEmergencyElevators(){
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
	      int shortestDistance = Elevator.NUM_OF_STORIES;
	      for (Elevator e : elevatorList){
	    	  if (Math.abs(e.getFloor() - person[pressedPersonNum].getSource()) < shortestDistance){
                  closestElevator = e;
                  shortestDistance = Math.abs(e.getFloor() - person[pressedPersonNum].getSource());
               }
	       }
	      
	      closestElevator.setEmergency();
	      
	      for(int i=0;i<person.length;i++)
	      {
	    	  if(person[i].getFinished()!=1&&(closestElevator.getFirstDirection()==person[i].direction||closestElevator.isIdle()))
	    	  {
	    		  if(person[i].getEntered()==0&&person[i].wait==0)//���� �ȵ��� ��ư�� ������ ����
	    		  {
	    			  if(closestElevator.getRequests().contains(person[i].getSource()))
	    				{
	    				person[i].wait=1;
	    				}
	    		  }
	    		  else if(person[i].getEntered()==1&&person[i].getElevatorNum()==closestElevator.ElevatorNum)
	    		  {
	    			  if(closestElevator.getRequests().contains(person[i].getDestination()))
	    			  {
	    			  person[i].setEntered(0);
	    			  person[i].wait=1;
	    			  person[i].setSource(closestElevator.getFloor());
	    			  }
	    		  }
	    	  }
	      }
	      
	      while(!closestElevator.getRequests().isEmpty())
	      {
	    	  closestElevator.getRequests().remove();
	      }
	      person[pressedPersonNum].wait=0;
	      closestElevator.addRequest(person[pressedPersonNum].getSource());
	      closestElevator.setFirstDirection(person[pressedPersonNum].direction);
	      
   }
}