
public class PersonInfo {
	private int source;
	private int dest;
	private int PersonNum;
	private int finished;// ��ü ó�� Ȯ�� ��//���� ��� ����
	private int entered;// ���� ž�� Ȯ�ο�//���� ���� ��� ����
	private int elevatorNum;// ���� ź ���� ��ȣ Ȯ�ο�
	public int time;// ���� ���������� ���ؼ�...
	public int wait;// 1:���� ��ٸ��� ���� 0:�ذ� ��
	public Direction direction;
	public Direction Emergency;

	PersonInfo(int s, int d, int num) {
		this.source = s;
		this.dest = d;
		this.PersonNum = num;
		this.finished = 0;
		this.elevatorNum = 0;
		this.entered = 0;
		this.time = 0;
		this.wait = 1;
	}

	public void setFinished(int num) {
		this.finished = num;
	}

	public void setSource(int num) {
		this.source = num;
	}

	public void setElevatorNum(int num) {
		this.elevatorNum = num;
	}

	public int getElevatorNum() {
		return this.elevatorNum;
	}

	public int getSource() {
		return this.source;
	}

	public int getDestination() {
		return this.dest;
	}

	public int getPersonNum() {
		return this.PersonNum;
	}

	public int getFinished() {
		return this.finished;
	}

	public int getEntered() {
		return this.entered;
	}

	public void setEntered(int num) {
		this.entered = num;
	}

}
