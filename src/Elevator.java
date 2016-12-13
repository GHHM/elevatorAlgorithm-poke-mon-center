import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator {
	private Direction direction = Direction.IDLE;// ���������Ͱ� ���� �����̴� ����
	private Direction firstDirection = Direction.IDLE;// idle�� �� ���� ��� ���������Ͱ�
														// �ö���� ������ ���� �������� ����
														// ���� ���� ���� ����! ���߿� �ΰ���
														// ������.
	private Direction Emergency = Direction.IDLE;
	private int firstPressedFloor = 0;// idle�� �� �� ó������ ���� ��!
	private int floor;// ������������ ���� ��
	public static final int Top_Of_Floors = 5;// �� ����� ��
	public int ElevatorNum = 0;
	public int personNum = 0;
	public int EwaitTime = 0;// ���������Ͱ� ������ �� ��ٸ��� �ð� ������!

	public int GetInPassengerList[] = { 0, 0, 0, 0, 0, 0,0,0,0,0 };// ���� �ִ� ��� ���� ��� ��
															// ��� ����Ʈ ���ÿ� �����̰�
															// �Ϸ��� �ִ°���!
	public int GetOutPassengerList[] = { 0, 0, 0, 0, 0, 0,0,0,0,0};// ���� ��� ����Ʈ ���ÿ�
															// �����̰� �Ϸ��� �ִ°���!
	public int GetInPassengerNum = 0;// �� ������ Ż ��� ������� ���� ����
	public int GetOutPassengerNum = 0;// �� ������ ���� ��� ������� ���� ����

	Elevator(int num) {
		this.ElevatorNum = num;
		this.personNum = 0;
		if (num == 1)// ������ 135�� ��ġ�ϵ��� �ؼ� ������� request�� ȿ�������� ó���� �� �ֵ��� ��.
			this.floor = 1;
		else if (num == 2)
			this.floor = 3;
		else
			this.floor = 5;
	}

	private PriorityQueue<Integer> requests = new PriorityQueue<Integer>(Top_Of_Floors, new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {// ������Ʈ�� min heap max heap����
													// �������ִ� ����!
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
				else // ���� ���
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
		this.removeRequest(floor);// �ϴ� �����ϸ� Ż �ְ� �ִ� ���� ������Ʈ�� ������ �ϹǷ� ���� ���� ����.
		if (this.firstPressedFloor == this.getFloor()) {// idle������ ���� ���� ���� �����
														// ���� ���� ������������ ������ �ٸ�
														// ���� �����Ƿ�
			this.setFirstPressedFloor(0);// ���� �ʱ�ȭ ���ְ�
			this.setDirection(this.getFirstDirection());// ������ ������ �������� ������������
														// ������ �ٲ���.
		}
		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() != 1) {
				if (person[i].time <= currentTime && person[i].direction == this.getFirstDirection()) {
					if (person[i].getEntered() != 1 && person[i].getSource() == this.getFloor()) {// ������
																									// �¿��
																									// �ڵ�

						if (this.isEmergency()) {// ���޻�Ȳ�� ��� �ƹ��� �� ���ǿ� ��ġ�Ѵٰ� �ص�
													// �¿� �� �����Ƿ� �ѱ�.
							if (person[i].Emergency != Emergency)
								continue;
						}

						if (this.personNum < 10)// ���� ��
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
						// ����� �¿�� ���� : �� ����� ���纸�� ���� �ð��� �԰�, ���� Ÿ�� �ְų� �Ϸ�� ���°�
						// �ƴҰ�쿡 �¿��.
						else {
							person[i].wait = 1;
							// �� ��ư�� ��������... ���� �����̶� ��Ÿ�� �ٽ� ��ٷ��� �� ���� ���ο� ���������͸�
							// �Ҵ�޾ƾ� �ϹǷ� wait�� 1�� ����.
						}
					} else if (person[i].getEntered() == 1 && person[i].getDestination() == this.getFloor()) {
						// ����� �������ϴ� ���� : �� ����� ź ���������Ͷ� ���� ������ȣ�� ����, ���� Ÿ�� �ִ�
						// ����! �Ϸ�� ���¸� ������ ����.
						if (person[i].getElevatorNum() == this.ElevatorNum) {
							person[i].setFinished(1);
							this.personNum--;
							frame.setInElevator(this.ElevatorNum, this.personNum);
							System.out.println(" Person " + i + " has left on Elevator " + this.ElevatorNum
									+ " in Floor " + this.getFloor() + "(" + this.personNum + ")");
							this.GetOutPassengerList[GetInPassengerNum] = i;
							GetOutPassengerNum++;
						}
						if (personNum == 0 && this.getRequests().isEmpty()) {// ��ü
																				// ��
																				// ����
																				// �������
																				// ���
																				// �Ѵ�
																				// idle��
																				// �ٲ��ְ�
							this.setDirection(Direction.IDLE);
							this.setFirstDirection(Direction.IDLE);
							if (this.isEmergency())// emergency�� ���� �Ϸ�Ǹ�
							{
								this.setIdle();
								for (Elevator e : Main.newC.getNotEmergencyElevators()) {
									notEmergencyNum++;
								}
								if (notEmergencyNum == 3) {
									frame.Sound("Recovery.wav", false);
								} else
									frame.Sound("Emergency_bgm.wav", true);
							} // emergency ���¸� idle�� �����ش�.
						}
					}
				}
			}
		}
		if (this.getRequests().isEmpty()) {// �� �κ��� �ߺ��Ǵ� �ڵ��ε� ���������� �־��� �ɷ� ����Ѵ�.
											// ��Ȯ�ϰ� ����� ���� �ʴ´�.
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
	}// ���޻�Ȳ������ �ٽ� ��� ���·� �����ִ� �ڵ�.

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
	}// ������������ ���� ���¸� �˷��ִ� �ڵ�.

}