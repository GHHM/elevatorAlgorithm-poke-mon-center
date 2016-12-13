
public class PersonInfo {
	private int source;
	private int dest;
	private int PersonNum;
	private int finished;// 전체 처리 확인 용//아직 사용 안함
	private int entered;// 엘베 탑승 확인용//역시 아직 사용 안함
	private int elevatorNum;// 내가 탄 엘베 번호 확인용
	public int time;// 언제 들어오는지에 대해서...
	public int wait;// 1:지금 기다리고 있음 0:해결 됨
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
