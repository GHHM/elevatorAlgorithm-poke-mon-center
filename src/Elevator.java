import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
 

public class Elevator {
      private Direction direction = Direction.IDLE;
      private Direction firstDirection = Direction.IDLE;
      private Direction Emergency = Direction.IDLE;
      private int firstPressedFloor=0;
      private int floor=1; //all elevators start on the first floor
      public static final int NUM_OF_STORIES = 7;
      public int ElevatorNum=0;
      public int personNum=0;
      public int EwaitTime=0;//엘레베이터가 열렸을 때 기다리는 시간 조절용!
       Elevator(int num)
       {
    	   this.ElevatorNum = num;
    	   this.personNum=0;
    	   if(num==1)
    		   this.floor = 1;
    	   else if(num==2)
    		   this.floor = NUM_OF_STORIES/2;
    	   else
    		   this.floor = NUM_OF_STORIES;//*///잠시 전부 다 1층으로 바꿀게여 
       }
       
       private PriorityQueue<Integer> requests = new PriorityQueue<Integer>(NUM_OF_STORIES, new Comparator<Integer>(){
              public int compare(Integer a,Integer b){
                     if (direction == Direction.DOWN){
                           if (a < b)
                                  return 1;
                           if (a > b)
                                  return -1;
                     }
                     else if (direction == Direction.UP){
                           if (a < b)
                                  return -1;
                           if (a > b)
                                  return 1;
                     }
                     return 0;
              }
       });
      
       //A button is pressed INSIDE the elevator to request a floor. 
       public void addRequest(int floor){
          if (!requests.contains(floor)){ //only continue if the requested floor is not already in requests
             if (requests.isEmpty()){ //if this is the only request, set the direction based on where the request is going
                if (this.floor < floor)
                   setDirection(Direction.UP);
                else if (this.floor > floor)
                   setDirection(Direction.DOWN);
                else //request is same as current floor
                   setDirection(this.firstDirection);
             }
          requests.add(floor);
          }
       }
       
       public void removeRequest(int floor){
          if (requests.contains(floor))
             requests.remove(); // the head of the priority queue is removed
       }
      
       //move the elevator up or down one floor
       public void move(){
          if (direction == Direction.UP && floor < NUM_OF_STORIES)
               floor += 1;
          else if (direction == Direction.DOWN && floor > 1)
               floor -= 1;
       }
       
       public void getPassengers(PersonInfo[] person,int floor,int currentTime){
          this.removeRequest(floor);
		  if(this.firstPressedFloor==this.getFloor())
		  {
			  this.setFirstPressedFloor(0);
			  this.setDirection(this.getFirstDirection());
		  }
          for(int i=0;i<person.length;i++)
          {
        	  if(person[i].getFinished()!=1){
        		  if(person[i].time<=currentTime&&person[i].direction==this.getFirstDirection()){
        			  if(person[i].getEntered()!=1&&person[i].getSource()==this.getFloor()){//엘베에 태우는 코드
        			  
        				 if(this.personNum<15)
        				 {
        					 person[i].setElevatorNum(this.ElevatorNum);
        					 this.personNum++;
        					 person[i].setEntered(1);
        					 this.addRequest(person[i].getDestination());
        					 System.out.println(currentTime+" Person "+i+" entered in Elevator "+this.ElevatorNum+"("+this.personNum+")");
        				 }
        				 else
        				 {
        					 person[i].wait=1;
        				 }
        		  }
        			  else if(person[i].getEntered()==1&&person[i].getDestination()==this.getFloor())
        			  {
        				  if(person[i].getElevatorNum()==this.ElevatorNum){
        					  person[i].setFinished(1);
        					  this.personNum--;
        					  System.out.println(currentTime+" Person "+i+" has left on Elevator "+this.ElevatorNum +" in Floor "+this.getFloor()+"("+this.personNum+")");
        					 }
        				  if(personNum==0&&this.getRequests().isEmpty())
        				  {
        					  this.setDirection(Direction.IDLE);
        					  this.setFirstDirection(Direction.IDLE);
        					  if(this.isEmergency())
        						  this.setIdle();
        				  }
        			  }
        			  }
        		  }
        	  }
          if(this.getRequests().isEmpty())
          {
        	  this.setDirection(Direction.IDLE);
			  this.setFirstDirection(Direction.IDLE);
			  if(this.isEmergency())
				  this.setIdle();
          }
        	  
       }
       
       //SETTERS
       public void setDirection(Direction direction){
          this.direction = direction;
       }
       public void setFirstDirection(Direction direction){
           this.firstDirection = direction;
        }
      
       //GETTERS
       public boolean isIdle(){
              return direction == Direction.IDLE;
       }
       public boolean isEmergency(){
           return Emergency == Direction.EMERGENCY;
       }
       public void setEmergency(){
          this.Emergency = Direction.EMERGENCY;
       }
       public void setIdle(){
    	  this.Emergency = Direction.IDLE;
       }//응급상황끝나면 다시 평소 상태로 정해주는 거얌!
       public int getFloor(){
              return floor;
       }
       public int getFirstPressedFloor(){
           return this.firstPressedFloor;
       }
       public void setFirstPressedFloor(int num){
           this.firstPressedFloor = num;
       }
       
       public PriorityQueue<Integer> getRequests(){
          return requests;
       }
       
       public Direction getDirection(){
          return direction;
       }
       public Direction getFirstDirection(){
           return firstDirection;
        }
       
       //toString
       public String toString(){
          return "\nElevator "+this.ElevatorNum+"\nEwaitTime : "+this.EwaitTime+"\nPerson NUM : "+this.personNum+"\nDirection: " + direction+"\nEmergency: " + Emergency + "\nFloor: " + floor + "\nRequested floors: " + requests;
       }


}