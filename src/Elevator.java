import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {
	private Direction direction = Direction.IDLE;// 엘리베이터가 현재 움직이는 방향
	private Direction firstDirection = Direction.IDLE;// idle일 때 예를 들어 엘리베이터가
														// 올라오고 있지만 나는 내려가고 싶을
														// 때를 위해 넣은 방향! 나중엔 두개가
														// 같아짐.
	private Direction Emergency = Direction.IDLE;
	private int firstPressedFloor = 0;// idle일 때 맨 처음으로 누른 층!
	private int floor;// 엘리베이터의 현재 층
	public static final int Top_Of_Floors = 5;// 맨 꼭대기 층
	public int ElevatorNum = 0;
	public int personNum = 0;
	public int EwaitTime = 0;// 엘리베이터가 열렸을 때 기다리는 시간 조절용!

	public int GetInPassengerList[] = { 0, 0, 0, 0, 0, 0,0,0,0,0 };// 들어와 있는 사람 말고 들어 올
															// 사람 리스트 동시에 움직이게
															// 하려고 넣는거임!
	public int GetOutPassengerList[] = { 0, 0, 0, 0, 0, 0,0,0,0,0};// 나갈 사람 리스트 동시에
															// 움직이게 하려고 넣는거임!
	public int GetInPassengerNum = 0;// 이 층에서 탈 사람 몇명인지 세기 위함
	public int GetOutPassengerNum = 0;// 이 층에서 나갈 사람 몇명인지 세기 위함

	Elevator(int num) {
		this.ElevatorNum = num;
		this.personNum = 0;
		if (num == 1)// 엘베를 135에 위치하도록 해서 사람들의 request를 효과적으로 처리할 수 있도록 함.
			this.floor = 1;
		else if (num == 2)
			this.floor = 3;
		else
			this.floor = 5;
	}

	private PriorityQueue<Integer> requests = new PriorityQueue<Integer>(Top_Of_Floors, new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {// 리퀘스트를 min heap max heap으로
													// 정리해주는 과정!
			if (direction == Direction.DOWN) {
				if (a < b)
					return 1;
				if (a > b)
					return -1;
			} else if (direction == Direction.UP) {
				if (a < b)
					return -1;
				if (a > b)
					return 1;
			}
			return 0;
		}
	});

	public void addRequest(int floor) {
		if (!requests.contains(floor)) {
			if (requests.isEmpty()) {
				if (this.floor < floor)
					setDirection(Direction.UP);
				else if (this.floor > floor)
					setDirection(Direction.DOWN);
				else // 같은 경우
					setDirection(this.firstDirection);
			}
			requests.add(floor);
		}
	}

	public void removeRequest(int floor) {
		if (requests.contains(floor))
			requests.remove();
	}

	public void move() {
		if (direction == Direction.UP && floor < Top_Of_Floors) {
			floor++;
		} else if (direction == Direction.DOWN && floor > 1) {
			floor--;
		}
	}

	public void getPassengers(PersonInfo[] person, int floor, int currentTime) {
		int notEmergencyNum = 0;
		this.removeRequest(floor);// 일단 도착하면 탈 애가 있던 없던 리퀘스트를 지워야 하므로 가장 먼저 지움.
		if (this.firstPressedFloor == this.getFloor()) {// idle이지만 내가 가고 싶은 방향과
														// 지금 현재 엘리베이터의 방향이 다를
														// 수도 있으므로
			this.setFirstPressedFloor(0);// 값을 초기화 해주고
			this.setDirection(this.getFirstDirection());// 앞으로 움직일 방향으로 엘리베이터의
														// 방향을 바꿔줌.
		}
		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() != 1) {
				if (person[i].time <= currentTime && person[i].direction == this.getFirstDirection()) {
					if (person[i].getEntered() != 1 && person[i].getSource() == this.getFloor()) {// 엘베에
																									// 태우는
																									// 코드

						if (this.isEmergency()) {// 응급상황인 경우 아무리 이 조건에 일치한다고 해도
													// 태울 수 없으므로 넘김.
							if (person[i].Emergency != Emergency)
								continue;
						}

						if (this.personNum < 10)// 만원 수
						{
							person[i].setElevatorNum(this.ElevatorNum);
							this.personNum++;
							frame.setInElevator(this.ElevatorNum, this.personNum);
							Main.waitingPersonNum[person[i].getSource()]--;
							frame.setFloorWaitingNum(person[i].getSource());
							person[i].setEntered(1);
							this.addRequest(person[i].getDestination());
							System.out.println(" Person " + i + " entered in Elevator " + this.ElevatorNum + "("
									+ this.personNum + ")");
							this.GetInPassengerList[GetInPassengerNum] = i;
							GetInPassengerNum++;
						}
						// 사람을 태우는 조건 : 그 사람이 현재보다 작은 시간에 왔고, 현재 타고 있거나 완료된 상태가
						// 아닐경우에 태운다.
						else {
							person[i].wait = 1;
							// 난 버튼을 눌렀지만... ㅇㅇ 만원이라 못타서 다시 기다려야 할 때는 새로운 엘리베이터를
							// 할당받아야 하므로 wait을 1로 해줌.
						}
					} else if (person[i].getEntered() == 1 && person[i].getDestination() == this.getFloor()) {
						// 사람을 내리게하는 조건 : 그 사람이 탄 엘레베이터랑 현재 엘베번호가 같고, 현재 타고 있는
						// 상태! 완료된 상태면 내리지 않음.
						if (person[i].getElevatorNum() == this.ElevatorNum) {
							person[i].setFinished(1);
							this.personNum--;
							frame.setInElevator(this.ElevatorNum, this.personNum);
							System.out.println(" Person " + i + " has left on Elevator " + this.ElevatorNum
									+ " in Floor " + this.getFloor() + "(" + this.personNum + ")");
							this.GetOutPassengerList[GetInPassengerNum] = i;
							GetOutPassengerNum++;
						}
						if (personNum == 0 && this.getRequests().isEmpty()) {// 전체
																				// 다
																				// 일이
																				// 비워졌을
																				// 경우
																				// 둘다
																				// idle로
																				// 바꿔주고
							this.setDirection(Direction.IDLE);
							this.setFirstDirection(Direction.IDLE);
							if (this.isEmergency())// emergency도 일이 완료되면
							{
								this.setIdle();
								for (Elevator e : Main.newC.getNotEmergencyElevators()) {
									notEmergencyNum++;
								}
								if (notEmergencyNum == 3) {
									frame.Sound("Recovery.wav", false);
								} else
									frame.Sound("Emergency_bgm.wav", true);
							} // emergency 상태를 idle로 고쳐준다.
						}
					}
				}
			}
		}
		if (this.getRequests().isEmpty()) {// 이 부분이 중복되는 코드인데 오류때문에 넣었던 걸로 기억한다.
											// 정확하게 기억이 나지 않는다.
			this.setDirection(Direction.IDLE);
			this.setFirstDirection(Direction.IDLE);
			if (this.isEmergency())
				this.setIdle();
		}

	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		if (direction == Direction.UP) {
			if (!this.isEmergency())
				frame.setUp(ElevatorNum);
		}

		else if (direction == Direction.DOWN) {
			if (!this.isEmergency())
				frame.setDown(ElevatorNum);
		} else
			frame.setIdle(ElevatorNum);
	}

	public void setFirstDirection(Direction direction) {
		this.firstDirection = direction;
	}

	public boolean isIdle() {
		return direction == Direction.IDLE;
	}

	public boolean isEmergency() {
		return this.Emergency == Direction.EMERGENCY;
	}

	public void setEmergency() {
		this.Emergency = Direction.EMERGENCY;
		frame.setEmergency(this.ElevatorNum);
	}

	public void setIdle() {
		this.Emergency = Direction.IDLE;
		frame.setIdle(this.ElevatorNum);
	}// 응급상황끝나면 다시 평소 상태로 정해주는 코드.

	public int getFloor() {
		return floor;
	}

	public int getFirstPressedFloor() {
		return this.firstPressedFloor;
	}

	public void setFirstPressedFloor(int num) {
		this.firstPressedFloor = num;
	}

	public PriorityQueue<Integer> getRequests() {
		return requests;
	}

	public Direction getDirection() {
		return direction;
	}

	public Direction getFirstDirection() {
		return firstDirection;
	}

	public String toString() {
		return "\nElevator " + this.ElevatorNum + "\nEwaitTime : " + this.EwaitTime + "\nPerson NUM : " + this.personNum
				+ "\nDirection: " + direction + "\nEmergency: " + Emergency + "\nFloor: " + floor
				+ "\nRequested floors: " + requests;
	}// 엘리베이터의 현재 상태를 알려주는 코드.

}