import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class frame extends JFrame {

	ImageIcon icon;
	static Image[] backImg = new Image[9];
	static JPanel contentPane;
	static JPanel mb1;
	static JPanel mb2;
	static JPanel mb3;
	static JPanel moon_b;
	static JPanel sun_b;
	static JPanel moon;
	static JPanel sun;
	static JPanel[] pocketMonImage = new JPanel[20];// 인원수 바꿀 때 여기 바꿔여
	static JLabel time_state = new JLabel("normal", JLabel.CENTER);
	static JLabel timer = new JLabel("", JLabel.CENTER);
	static JPanel[] emergency = new JPanel[3];
	static JPanel[] idle = new JPanel[3];
	static JPanel[] up = new JPanel[3];
	static JPanel[] down = new JPanel[3];
	static JPanel e1_state = new JPanel();
	static JPanel e2_state = new JPanel();
	static JPanel e3_state = new JPanel();
	static int e1Location = 273;
	static int e2Location = 619;
	static int e3Location = 980;
	static int time = 0;

	frame() {
		setTitle("Pocketmon Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {// Graphics객체는 그릴수 있는 도구.
				// 이미지처리. 배경
				Image backImg = new ImageIcon("background.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				setOpaque(false);
			}
		};

		contentPane.setVisible(true);
		setContentPane(contentPane);
		setLayout(null);
		setUndecorated(true);
		setSize(1920, 1080);
		setResizable(false);
		setVisible(true);
		
		/*timer*/
		timer.setSize(220,100);
		timer.setLayout(null);
		timer.setBounds(1615, 40, 300, 110);
		timer.setFont(new Font("hp-transistor",Font.BOLD,90));
		timer.setOpaque(true);	//글자색변경을 위해서는 true 해줘야함
		timer.setBackground(Color.WHITE);
		timer.setForeground(new Color(16,9,100));
		timer.setVisible(true);
		contentPane.add(timer);
		
		

		// 이제 한 사람당 한 패널의 포켓몬...? 할당하려고요! 기다려어어어어어엉
		// 모든 포켓몬==뚜벅초......ㅠ 흡
		for (int i = 0; i < 20; i++) {
			time = i;
			pocketMonImage[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					if (time % 9 == 0)
						backImg[0] = new ImageIcon("가디안.png").getImage();
					else if (time % 9 == 1)
						backImg[1] = new ImageIcon("뚜벅초.png").getImage();
					else if (time % 9 == 2)
						backImg[2] = new ImageIcon("메로에타.png").getImage();
					else if (time % 9 == 3)
						backImg[3] = new ImageIcon("뮤.png").getImage();
					else if (time % 9 == 4)
						backImg[4] = new ImageIcon("알로라.png").getImage();
					else if (time % 9 == 5)
						backImg[5] = new ImageIcon("쥬피썬더.png").getImage();
					else if (time % 9 == 6)
						backImg[6] = new ImageIcon("파이리.png").getImage();
					else if (time % 9 == 7)
						backImg[7] = new ImageIcon("푸린.png").getImage();
					else
						backImg[8] = new ImageIcon("피츄.png").getImage();

					g.drawImage(backImg[time % 9], 0, 0, getWidth(), getHeight(), this);
				}
			};
			pocketMonImage[i].setSize(120, 120);
			pocketMonImage[i].setLayout(null);
			pocketMonImage[i].setBounds(1500, 80, 120, 120);
			pocketMonImage[i].setVisible(true);
		}

		mb1 = new JPanel() {// 엘리베이터 1
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb2 = new JPanel() {// 엘리베이터 2
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb3 = new JPanel() {// 엘리베이터 3
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};

		mb1.setSize(120, 120);
		mb1.setLayout(null);
		mb1.setBounds(273, 800, 115, 110);
		mb1.setVisible(true);

		mb2.setSize(120, 120);
		mb2.setLayout(null);
		mb2.setBounds(619, 440, 115, 110);
		mb2.setVisible(true);

		mb3.setSize(10, 120);
		mb3.setLayout(null);
		mb3.setBounds(980, 80, 115, 110);
		mb3.setVisible(true);

		contentPane.add(mb1);
		contentPane.add(mb2);
		contentPane.add(mb3);

		moon_b = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				setSize(70, 70);
				Image backImg = new ImageIcon("moon_b.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		sun_b = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				setSize(80, 80);
				Image backImg = new ImageIcon("sun_b.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		moon = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				setSize(70, 70);
				Image backImg = new ImageIcon("moon.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		sun = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				setSize(80, 80);
				Image backImg = new ImageIcon("sun.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};

		moon.setSize(10, 120);
		moon.setLayout(null);
		moon.setBounds(1754, 324, 115, 110);
		moon.setVisible(false);

		sun.setSize(10, 120);
		sun.setLayout(null);
		sun.setBounds(1614, 319, 115, 110);
		sun.setVisible(false);

		moon_b.setSize(10, 120);
		moon_b.setLayout(null);
		moon_b.setBounds(1754, 324, 115, 110);
		moon_b.setVisible(true);

		sun_b.setSize(10, 120);
		sun_b.setLayout(null);
		sun_b.setBounds(1614, 319, 115, 110);
		sun_b.setVisible(true);

		contentPane.add(moon_b);
		contentPane.add(sun_b);
		contentPane.add(moon);
		contentPane.add(sun);

		for(int i = 0;i<3;i++)
		{
			emergency[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80,80);
					Image backImg = new ImageIcon("emergency.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};
			
			idle[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80,80);
					Image backImg = new ImageIcon("idle.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};
			
			up[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80,80);
					Image backImg = new ImageIcon("up.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};
			
			down[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80,80);
					Image backImg = new ImageIcon("down.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};
		}
		
		e1_state.setBounds(1697, 551, 115, 110);//e1 상태표시 위치
		e2_state.setBounds(1697, 670, 115, 110);//e2 상태표시 위치
		e3_state.setBounds(1697, 790, 115, 110);//e3 상태표시 위치
		
		
		for(int i = 0;i<3;i++)
		{
			emergency[i].setSize(10, 120);
			emergency[i].setLayout(null);
			emergency[i].setVisible(false);
			
			idle[i].setSize(10, 120);
			idle[i].setLayout(null);
			idle[i].setVisible(true);
			
			up[i].setSize(10, 120);
			up[i].setLayout(null);
			up[i].setVisible(false);
			
			down[i].setSize(10, 120);
			down[i].setLayout(null);
			down[i].setVisible(false);
			
			if(i==0)
			{
				emergency[i].setBounds(e1_state.getBounds());
				idle[i].setBounds(e1_state.getBounds());
				up[i].setBounds(e1_state.getBounds());
				down[i].setBounds(e1_state.getBounds());
			}
			else if(i==1)
			{
				emergency[i].setBounds(e2_state.getBounds());
				idle[i].setBounds(e2_state.getBounds());
				up[i].setBounds(e2_state.getBounds());
				down[i].setBounds(e2_state.getBounds());
			}
			else
			{
				emergency[i].setBounds(e3_state.getBounds());
				idle[i].setBounds(e3_state.getBounds());
				up[i].setBounds(e3_state.getBounds());
				down[i].setBounds(e3_state.getBounds());
			}
			
			contentPane.add(emergency[i]);
			contentPane.add(idle[i]);
			contentPane.add(up[i]);
			contentPane.add(down[i]);
		}
		
		time_state.setLayout(null);
		time_state.setFont(new Font("Octapost NBP",1,50));
		time_state.setBounds(1560, 210, 330, 83);
		time_state.setVisible(true);
		
		contentPane.add(time_state);
		
		init();
	}

	public void init() {
		Sound("pocketmon.wav", true);
	}

	public void Sound(String file, boolean Loop) {
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);
			// Loop 값이true면 사운드재생을무한반복시킵니다.
			// false면 한번만재생시킵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeUI() {

	}// 뭐 때문에 썼었는데 빈 함수가 되었을까...? //프레임 constructor 안에 너무 많은 코드가 있어서 옮기려고
		// 만들었었찌...

	public static void moveElevatorUP(int i) {

		for (int j = 0; j < 180; j++) {
			if (i == 1)
				mb1.setBounds(273, mb1.getY() - 1, 115, 110);
			else if (i == 2)
				mb2.setBounds(619, mb2.getY() - 1, 115, 110);
			else
				mb3.setBounds(980, mb3.getY() - 1, 115, 110);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void moveElevatorDOWN(int i) {

		for (int j = 0; j < 180; j++) {
			if (i == 1)
				mb1.setBounds(273, mb1.getY() + 1, 115, 110);
			else if (i == 2)
				mb2.setBounds(619, mb2.getY() + 1, 115, 110);
			else
				mb3.setBounds(980, mb3.getY() + 1, 115, 110);

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void getPassengerIn(int passengerNum, int elevatorNum, int source) {// 1920이려나
		int moveLocationTo = 0;
		int floorLocation = 80 + ((5 - source) * 180);
		if (elevatorNum == 1)
			moveLocationTo = e1Location;
		else if (elevatorNum == 2)
			moveLocationTo = e2Location;
		else
			moveLocationTo = e3Location;
		contentPane.add(pocketMonImage[passengerNum]);
		pocketMonImage[passengerNum].setVisible(true);
		while (pocketMonImage[passengerNum].getX() != moveLocationTo - 1) {
			pocketMonImage[passengerNum].setBounds(pocketMonImage[passengerNum].getX() - 1, floorLocation, 120, 120);
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pocketMonImage[passengerNum].setVisible(false);
	}

	public static void getPassengerOut(int passengerNum, int elevatorNum, int destination) {

		int moveLocationTo = 0;
		int floorLocation = 80 + ((5 - destination) * 180);
		if (elevatorNum == 1)
			moveLocationTo = e1Location;
		else if (elevatorNum == 2)
			moveLocationTo = e2Location;
		else
			moveLocationTo = e3Location;
		pocketMonImage[passengerNum].setVisible(true);
		contentPane.add(pocketMonImage[passengerNum]);
		pocketMonImage[passengerNum].setBounds(moveLocationTo, floorLocation, 120, 120);
		for (int j = moveLocationTo; j > 0; j--) {
			pocketMonImage[passengerNum].setBounds(pocketMonImage[passengerNum].getX() - 1, floorLocation, 120, 120);
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pocketMonImage[passengerNum].setVisible(false);
	}

	public static void MovePassengerToWait(int passengerNum, int currentFloor) {// 1920이려나

		int floorLocation = 80 + ((5 - currentFloor) * 180);
		contentPane.add(pocketMonImage[passengerNum]);
		pocketMonImage[passengerNum].setVisible(true);

		while (pocketMonImage[passengerNum].getX() != 1500) {
			pocketMonImage[passengerNum].setBounds(pocketMonImage[passengerNum].getX() + 1, floorLocation, 120, 120);
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pocketMonImage[passengerNum].setVisible(false);
	}

	public static void startOfficeGoing() {
		sun_b.setVisible(false);
		sun.setVisible(true);
	}

	public static void endOfficeGoing() {
		sun.setVisible(false);
		sun_b.setVisible(true);
	}

	public static void startQuittingTime() {
		moon_b.setVisible(false);
		moon.setVisible(true);
	}

	public static void endQuittingTime() {
		moon.setVisible(false);
		moon_b.setVisible(true);
	}

	public static void timeDisplay(double currentTime) {

		double h = currentTime / 60;
		double m = currentTime % 60;
	
		String timeLine;
		timeLine = "";
		timeLine += (h < 10) ? "0" + (int)h : (int)h;
		timeLine += ":";
		timeLine += (m < 10) ? "0" + (int)m : (int)m;
		timer.setText(timeLine);

	}


	public static void resetState(int eNum)
	{
		emergency[eNum].setVisible(false);
		idle[eNum].setVisible(false);
		up[eNum].setVisible(false);
		down[eNum].setVisible(false);
	}
	
	public static void setEmergency(int eNum)
	{
		resetState(eNum-1);
		emergency[eNum-1].setVisible(true);
	}
	
	public static void setIdle(int eNum)
	{
		resetState(eNum-1);
		idle[eNum-1].setVisible(true);
	}
	
	public static void setUp(int eNum)
	{
		resetState(eNum-1);
		up[eNum-1].setVisible(true);
	}
	
	public static void setDown(int eNum)
	{
		resetState(eNum-1);
		down[eNum-1].setVisible(true);
	}
	
	public static void setStateText(String str)
	{
		time_state.setText(str);
	}
	
}