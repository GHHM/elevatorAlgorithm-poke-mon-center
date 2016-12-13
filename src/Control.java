import java.util.ArrayList;

public class Control {
	Elevator elevator1 = new Elevator(1);
	Elevator elevator2 = new Elevator(2);
	Elevator elevator3 = new Elevator(3);
	Elevator elevatorList[] = { elevator1, elevator2, elevator3 };
	public int[] Emove = { 0, 0, 0, 0 };
	int a;

	void go(PersonInfo[] person, int currentTime, int totalTime) throws Exception {
		System.out.println("Go ���� ��\n" + elevator1.toString());
		System.out.println(elevator2.toString());
		System.out.println(elevator3.toString());// ���� ��� �ǳ� Ȯ�ο���!

		for (Elevator e : elevatorList) {// ��� ���������� ����Ʈ�� �ҷ���
			if (e.isIdle()) {// ���࿡ ���������Ͱ� ���������

				if (currentTime >= (totalTime / 24) * 8 && currentTime < (totalTime / 24) * 11 && !e.isEmergency()) {// ��ٽð���
																														// �ɷ��ִµ�
																														// ���������
																														// ������
																														// 1������
																														// ����.
																														// ��ٽð���
																														// �ö󰡴�
																														// �����
																														// �����ϱ�.
					if (e.getFloor() != e.Top_Of_Floors) {
						e.addRequest(1);
						e.setFirstDirection(e.getDirection());
					}
				} else if (currentTime >= (totalTime / 24) * 16 && currentTime < (totalTime / 24) * 20
						&& !e.isEmergency())
					if (e.getFloor() != e.Top_Of_Floors) {// ���������� ��ٽð��� ������ ��
															// �������� ����.
						e.addRequest(e.Top_Of_Floors);
						e.setFirstDirection(e.getDirection());
					}
			}
			// */// ����� �ð� �˻� �ڵ�
			if (!e.getRequests().isEmpty()) {
				if (e.getRequests().peek() == e.getFloor()) {
					if (e.isEmergency()) {// emergency ��Ȳ������ �켱 ž���� ���� ��ٸ��� �ʰ�
											// �����.
						e.getPassengers(person, e.getFloor(), currentTime);
						continue;
					}
					e.EwaitTime++;// �� ���� ������ �� ����� �¿��� �ϱ� ������ ��ٸ��� �ð�!
					if (e.EwaitTime > 1)// ������ 0 1 2 �� 3�ʸ� ������Ʈ ���� ������ ��ٸ��� �����.
						e.getPassengers(person, e.getFloor(), currentTime);
				} else {
					e.EwaitTime = 0;// �����̸� ��ٸ��� �ð��� �ʱ�ȭ����.
					Emove[e.ElevatorNum] = 1;
					e.move();
				}
			}
		}
		frame.moveEveryThing();
		System.out.println("Go �� ��\n" + elevator1.toString());
		System.out.println(elevator2.toString());
		System.out.println(elevator3.toString());// ���������� ���� Ȯ�ο�
		// }
	}

	int pressButton(int floor, Direction direction, Direction Emergency) {
		int min = 0;
		int shortestd1 = 0, shortestd2 = 0;

		if (Emergency == Direction.EMERGENCY)// emergency�� ����ְų� ������� �ʾƵ� ������ ����
												// �� �����ϱ� ������ ���� �ٸ� �˰����� ������
												// �־� �ٸ��� �ٷ���.
			return 2;
		Elevator closestElevator = getClosestEnrouteElevator(floor, direction);// �����̰�
																				// �ִ�
																				// ��
																				// ��
																				// ����
																				// �����
																				// ����������
		Elevator closestElevator2 = getClosestIdleElevator(floor);// ����ִ� �� �� ����
																	// ����� ����������
		if (closestElevator != null && closestElevator2 != null)// �Ѵ� �ƴ� ��� �� �߿�
																// ���� ������ �� �ڵ�!
		{
			shortestd1 = Math.abs(closestElevator.getFloor() - floor);
			shortestd2 = Math.abs(closestElevator2.getFloor() - floor);
			min = Integer.min(shortestd1, shortestd2);

			if (min == shortestd1) {
				closestElevator.addRequest(floor);
			} else {
				closestElevator2.setFirstDirection(direction);
				closestElevator2.addRequest(floor);
			}
		} else if (closestElevator != null && closestElevator2 == null)// ��
																		// enroute�̾�
		{
			closestElevator.addRequest(floor);
		} else if (closestElevator == null && closestElevator2 != null)// Idle�̾�
																		// ��
		{
			closestElevator2.setFirstDirection(direction);
			closestElevator2.addRequest(floor);
		}

		else
			return -1;
		return 1;
	}

	private Elevator getClosestIdleElevator(int floor) {// idle�̸� ������ ���� ������
														// �����Ƿ� ���� ����� ģ���� ������.
		Elevator closestElevator = null;
		int shortestDistance = Elevator.Top_Of_Floors;
		for (Elevator e : getNotEmergencyElevators()) {
			if (e.isIdle()) {
				if (Math.abs(e.getFloor() - floor) < shortestDistance) {
					closestElevator = e;
					shortestDistance = Math.abs(e.getFloor() - floor);
				}
			}
		}
		return closestElevator;
	}

	private Elevator getClosestEnrouteElevator(int floor, Direction direction) {
		// �����̴� ���������ʹ� ����� ���������Ͱ� �����ִ� �ð� �׸��� ���������� ���� �ο����� ����ؾ� ��.
		Elevator closestElevator = null;
		int shortestDistance = Elevator.Top_Of_Floors;
		for (Elevator e : getNotEmergencyElevators()) {// emergency���������Ϳ� ����
														// ȯ�ڿ��� request�� �޾�����
														// �ȵǹǷ�
			if (direction == e.getFirstDirection()) {
				if (e.getDirection() == Direction.UP && direction == Direction.UP && e.personNum < 10)// ����
																										// ��
				{
					if (floor >= e.getFloor() && floor <= Elevator.Top_Of_Floors) {
						if (e.getFloor() == floor) {
							if (e.EwaitTime <= 2)// ���� ���� ��� �����ִ� �� �ð��ȿ� Ÿ���ϱ� ����
													// 0123�̾��µ� 012�� ��ħ!
							{
								if (Math.abs(e.getFloor() - floor) < shortestDistance) {
									closestElevator = e;
									shortestDistance = Math.abs(e.getFloor() - floor);
								}
							}
						} else if (floor > e.getFloor()) {
							if (Math.abs(e.getFloor() - floor) < shortestDistance) {
								closestElevator = e;
								shortestDistance = Math.abs(e.getFloor() - floor);
							}
						}

					}
				} else if (e.getDirection() == Direction.DOWN && direction == Direction.DOWN && e.personNum < 10
						&& e.EwaitTime < 3) {
					if (floor <= e.getFloor() && floor >= 1) {
						if (floor == e.getFloor()) {
							if (e.EwaitTime <= 2)// ���� ���� ��� �����ִ� �� �ð��ȿ� Ÿ���ϱ� ����
													// 0123�̾��µ� 012�� ��ħ!
							{
								if (Math.abs(e.getFloor() - floor) < shortestDistance) {
									closestElevator = e;
									shortestDistance = Math.abs(e.getFloor() - floor);
								}
							}
						} else if (floor < e.getFloor()) {
							if (Math.abs(e.getFloor() - floor) < shortestDistance) {
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

	public ArrayList<Elevator> getNotEmergencyElevators() {// emergency�� ������
															// ���������͸� ã�� �ڵ�
		ArrayList<Elevator> activeElevators = new ArrayList<Elevator>();
		for (Elevator e : elevatorList) {
			if (!e.isEmergency())
				activeElevators.add(e);
		}
		return activeElevators;
	}

	public void EmergencyHandling(PersonInfo[] person, int pressedPersonNum) {
		int NormalElevatorNum = 0;
		Elevator closestElevator = null;
		int shortestDistance = Elevator.Top_Of_Floors;
		for (Elevator e : getNotEmergencyElevators()) {// Ÿ�� �ְų� ����ְų� ��� ����
														// request���� ���� ����� ������
														// ��
			if (Math.abs(e.getFloor() - person[pressedPersonNum].getSource()) < shortestDistance) {
				closestElevator = e;
				shortestDistance = Math.abs(e.getFloor() - person[pressedPersonNum].getSource());
			}
			NormalElevatorNum++;
		}
		if (closestElevator == null)
			return;// ���� �� emergency�� �� ��¿ �� ���� ��ٷ��� ��.

		if (NormalElevatorNum == 3)
			frame.Sound("Emergency_bgm.wav", true);

		closestElevator.setEmergency();// ���������͸� emergency�� ���¸� �ٲ���

		for (int i = 0; i < person.length; i++) {
			if (person[i].getFinished() != 1
					&& (closestElevator.getFirstDirection() == person[i].direction || closestElevator.isIdle())) {
				if (person[i].getEntered() == 0 && person[i].wait == 0)// ���� �ȵ��� ��ư�� ���� ����
				{
					if (closestElevator.getRequests().contains(person[i].getSource())) {
						person[i].wait = 1;// �ٽ� ��ư�� �������� ��(���ο� ���������� �Ҵ�ް�)
					}
				} else if (person[i].getEntered() == 1 && person[i].getElevatorNum() == closestElevator.ElevatorNum) {
					if (closestElevator.getRequests().contains(person[i].getDestination())) {
						System.out.println("Person " + i + "  Dest: " + person[i].getDestination()
								+ " is lefted because of Emergency situation.");
						// ����� ���� �ȿ� ź ������� handle���ִ� �����Դϴ�!

						if (person[i].getDestination() == closestElevator.getFloor()) {
							person[i].setFinished(1);// �������� �Դµ� �߰��� emergency�ɷ��� 4������ 4�� ���� �ʹ� ģ���� �־ ���� �ڵ�
							closestElevator.personNum--;
							frame.MoveEpassengerToFinish(i, closestElevator.getFloor());
							continue;
						}
						frame.MovePassengerToWait(i, closestElevator.getFloor());
						person[i].setEntered(0);
						closestElevator.personNum--;
						person[i].wait = 1;
						person[i].setSource(closestElevator.getFloor());//Ÿ�� ���� ���� ���� �ƴϴϱ� �ٲ�
						Main.waitingPersonNum[person[i].getSource()]++;
						frame.setFloorWaitingNum(person[i].getSource());
					}
				}
			}
		}

		while (!closestElevator.getRequests().isEmpty()) {
			closestElevator.getRequests().remove();// ��� ����� �������Ƿ� ��� request��
													// �ٽ� �����ְ�!
		}
		person[pressedPersonNum].wait = 0;// ���޻�Ȳ ��û�� �� ����� ������Ʈ�� �޾����Ƿ� ��ٸ��� �ʰ�
											// �մϴ�!
		closestElevator.addRequest(person[pressedPersonNum].getSource());// ����
																			// ���������Ϳ�
																			// ������Ʈ��
																			// �ְ�
		closestElevator.setFirstDirection(person[pressedPersonNum].direction);
	}
}