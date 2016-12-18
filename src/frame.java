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

	static Clip clip;
	static Image[] backImg = new Image[9];
	static JPanel contentPane;
	static JPanel mb1;
	static JPanel mb2;
	static JPanel mb3;
	static JPanel moon_b;
	static JPanel sun_b;
	static JPanel moon;
	static JPanel sun;
	static JLabel time_state = new JLabel("normal", JLabel.CENTER);
	static JLabel timer = new JLabel("", JLabel.CENTER);
	static JPanel[] emergency = new JPanel[3];
	static JPanel[] idle = new JPanel[3];
	static JPanel[] up = new JPanel[3];
	static JPanel[] down = new JPanel[3];
	static JPanel e1_state = new JPanel();
	static JPanel e2_state = new JPanel();
	static JPanel e3_state = new JPanel();
	static JLabel[] FloorWaitingNum = new JLabel[6];

	static JPanel[] pocketMonImage = new JPanel[Main.pNum];

	static int e1Location = 273;
	static int e2Location = 619;
	static int e3Location = 980;
	static int[] FloorLocation = { 0, 800, 620, 440, 260, 80 };
	static int time1 = 0;
	static int time2 = 0;
	static int time3 = 0;

	static JPanel mb1_o;
	static JPanel mb2_o;
	static JPanel mb3_o;

	static JLabel[] e_people = new JLabel[3];

	frame() {
		setTitle("Pocketmon Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {// Graphics객체는 그릴수 있는 도구.
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

		/* timer */
		timer.setSize(220, 100);
		timer.setLayout(null);
		timer.setBounds(1571, 52, 305, 100);
		timer.setFont(new Font("hp-transistor", Font.BOLD, 100));
		timer.setOpaque(false); 
		timer.setForeground(new Color(16, 9, 100));
		timer.setVisible(true);
		contentPane.add(timer);

		mb1 = new JPanel() {// Elevator 1
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb2 = new JPanel() {// Elevator 2
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb3 = new JPanel() {// Elevator 3
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

		backImg[0] = new ImageIcon("가디안.png").getImage();
		backImg[1] = new ImageIcon("뚜벅초.png").getImage();
		backImg[2] = new ImageIcon("메로에타.png").getImage();
		backImg[3] = new ImageIcon("뮤.png").getImage();
		backImg[4] = new ImageIcon("알로라.png").getImage();
		backImg[5] = new ImageIcon("쥬피썬더.png").getImage();
		backImg[6] = new ImageIcon("파이리.png").getImage();
		backImg[7] = new ImageIcon("푸린.png").getImage();
		backImg[8] = new ImageIcon("피츄.png").getImage();
		for (int i = 0; i < Main.pNum; i++) {
			
			if (i % 9 == 0) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[0], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 1) {
				if (i%100==0) {
					pocketMonImage[i] = new JPanel() {
						public void paintComponent(Graphics g) {
							Image backImg = new ImageIcon("아픈토게피.png").getImage();
							setOpaque(false);
							g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
						}
					};
				}
				else
				{
					pocketMonImage[i] = new JPanel() {
						public void paintComponent(Graphics g) {
							setOpaque(false);
							g.drawImage(backImg[1], 0, 0, getWidth(), getHeight(), this);
						}
					};
				}
			}

			if (i % 9 == 2) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[2], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 3) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[3], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 4) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[4], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 5) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[5], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 6) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[6], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 7) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[7], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			if (i % 9 == 8) {
				pocketMonImage[i] = new JPanel() {
					public void paintComponent(Graphics g) {
						setOpaque(false);
						g.drawImage(backImg[8], 0, 0, getWidth(), getHeight(), this);
					}
				};
			}

			pocketMonImage[i].setSize(120, 120);
			pocketMonImage[i].setLayout(null);
			pocketMonImage[i].setBounds(1500, 80, 120, 120);
			pocketMonImage[i].setVisible(true);
		}

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

		for (int i = 0; i < 3; i++) {
			emergency[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80, 80);
					Image backImg = new ImageIcon("emergency.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};

			idle[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80, 80);
					Image backImg = new ImageIcon("idle.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};

			up[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80, 80);
					Image backImg = new ImageIcon("up.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};

			down[i] = new JPanel() {
				public void paintComponent(Graphics g) {
					setOpaque(false);
					setSize(80, 80);
					Image backImg = new ImageIcon("down.png").getImage();
					g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
				}
			};
		}

		e1_state.setBounds(1697, 551, 115, 110);// e1 상태표시 위치
		e2_state.setBounds(1697, 670, 115, 110);// e2 상태표시 위치
		e3_state.setBounds(1697, 790, 115, 110);// e3 상태표시 위치

		for (int i = 0; i < 3; i++) {
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

			if (i == 0) {
				emergency[i].setBounds(e1_state.getBounds());
				idle[i].setBounds(e1_state.getBounds());
				up[i].setBounds(e1_state.getBounds());
				down[i].setBounds(e1_state.getBounds());
			} else if (i == 1) {
				emergency[i].setBounds(e2_state.getBounds());
				idle[i].setBounds(e2_state.getBounds());
				up[i].setBounds(e2_state.getBounds());
				down[i].setBounds(e2_state.getBounds());
			} else {
				emergency[i].setBounds(e3_state.getBounds());
				idle[i].setBounds(e3_state.getBounds());
				up[i].setBounds(e3_state.getBounds());
				down[i].setBounds(e3_state.getBounds());
			}

			contentPane.add(emergency[i]);
			contentPane.add(idle[i]);
			contentPane.add(up[i]);
			contentPane.add(down[i]);

			e_people[i] = new JLabel();
			e_people[i].setLayout(null);
			if (i == 0)
				e_people[i].setBounds(e1_state.getX() + 130, e1_state.getY() - 25, 100, 100);
			if (i == 1)
				e_people[i].setBounds(e2_state.getX() + 130, e2_state.getY() - 25, 100, 100);
			if (i == 2)
				e_people[i].setBounds(e3_state.getX() + 130, e3_state.getY() - 25, 100, 100);
			e_people[i].setFont(new Font("hp-transistor", Font.BOLD, 100));
			e_people[i].setOpaque(false);
			e_people[i].setForeground(new Color(0, 0, 0));
			e_people[i].setText("0");
			e_people[i].setVisible(true);

			contentPane.add(e_people[i]);
		}

		time_state.setLayout(null);
		time_state.setFont(new Font("Octapost NBP", 1, 50));
		time_state.setBounds(1560, 210, 330, 83);
		time_state.setVisible(true);

		contentPane.add(time_state);

		// Floor waiting person Number setting
		for (int i = 1; i < 6; i++) {
			FloorWaitingNum[i] = new JLabel();
			FloorWaitingNum[i].setLayout(null);
			FloorWaitingNum[i].setFont(new Font("hp-transistor", Font.BOLD, 100));
			FloorWaitingNum[i].setBounds(1400, FloorLocation[i], 100, 100);
			FloorWaitingNum[i].setVisible(true);
			FloorWaitingNum[i].setText("0");
			contentPane.add(FloorWaitingNum[i]);
		}

		// 여기부터 추가함!
		mb1_o = new JPanel() {// 엘리베이터 1
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("open_monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb2_o = new JPanel() {// 엘리베이터 2
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("open_monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb3_o = new JPanel() {// 엘리베이터 3
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("open_monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb1_o.setSize(120, 120);
		mb1_o.setLayout(null);
		mb1_o.setBounds(273, 800, 115, 110);
		mb1_o.setVisible(false);

		mb2_o.setSize(120, 120);
		mb2_o.setLayout(null);
		mb2_o.setBounds(619, 440, 115, 110);
		mb2_o.setVisible(false);

		mb3_o.setSize(10, 120);
		mb3_o.setLayout(null);
		mb3_o.setBounds(980, 80, 115, 110);
		mb3_o.setVisible(false);

		contentPane.add(mb1_o);
		contentPane.add(mb2_o);
		contentPane.add(mb3_o);
		// 여기까지 추가함!

		setIdle(1);
		setIdle(2);
		setIdle(3);

		init();
	}

	public void init() {
		Sound("pocketmon.wav", true);
	}

	public static void Sound(String file, boolean Loop) {
		try {
			if (clip != null)
				clip.stop();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();

			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Loop == false) {
			try {
				AudioInputStream ais = AudioSystem
						.getAudioInputStream(new BufferedInputStream(new FileInputStream("pocketmon.wav")));
				clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
				if (true)
					clip.loop(-1);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void moveEveryThing() {// 하나에 다 넣으면.... 되지 않을까 싶은 마음으로 도저어어언
		allSetVisible(true);// ㅋ 되는데 넘나 복잡해.....ㅠㅠㅠㅠ
		int a;
		for (int j = 0; j < 180; j++) {

			// up
			if (Main.newC.Emove[1] == 1 && Main.newC.elevator1.getDirection() == Direction.UP
					&& mb1.getY() != FloorLocation[Main.newC.elevator1.getFloor()])
				mb1.setBounds(273, mb1.getY() - 1, 115, 110);
			if (Main.newC.Emove[2] == 1 && Main.newC.elevator2.getDirection() == Direction.UP
					&& mb2.getY() != FloorLocation[Main.newC.elevator2.getFloor()])
				mb2.setBounds(619, mb2.getY() - 1, 115, 110);
			if (Main.newC.Emove[3] == 1 && Main.newC.elevator3.getDirection() == Direction.UP
					&& mb3.getY() != FloorLocation[Main.newC.elevator3.getFloor()])
				mb3.setBounds(980, mb3.getY() - 1, 115, 110);
			// down
			if (Main.newC.Emove[1] == 1 && Main.newC.elevator1.getDirection() == Direction.DOWN
					&& mb1.getY() != FloorLocation[Main.newC.elevator1.getFloor()])
				mb1.setBounds(273, mb1.getY() + 1, 115, 110);
			if (Main.newC.Emove[2] == 1 && Main.newC.elevator2.getDirection() == Direction.DOWN
					&& mb2.getY() != FloorLocation[Main.newC.elevator2.getFloor()])
				mb2.setBounds(619, mb2.getY() + 1, 115, 110);
			if (Main.newC.Emove[3] == 1 && Main.newC.elevator3.getDirection() == Direction.DOWN
					&& mb3.getY() != FloorLocation[Main.newC.elevator3.getFloor()])
				mb3.setBounds(980, mb3.getY() + 1, 115, 110);

			// get in passenger
	         if (Main.newC.elevator1.GetInPassengerNum != 0)
	         {
	            openMonsterBall(1);
	            e1GetinPassenger();
	         }
	         if (Main.newC.elevator2.GetInPassengerNum != 0)
	         {
	            openMonsterBall(2);
	            e2GetinPassenger();
	         }
	         if (Main.newC.elevator3.GetInPassengerNum != 0)
	         {
	            openMonsterBall(3);
	            e3GetinPassenger();
	         }
	         
	         // get out passenger
	         if (Main.newC.elevator1.GetOutPassengerNum != 0)
	         {
	            openMonsterBall(1);
	            e1GetOutPassenger();
	         }   
	         if (Main.newC.elevator2.GetOutPassengerNum != 0)
	         {
	            openMonsterBall(2);
	            e2GetOutPassenger();
	         }
	         if (Main.newC.elevator3.GetOutPassengerNum != 0)
	         {
	            openMonsterBall(3);
	            e3GetOutPassenger();
	         }

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		allSetVisible(false);
		removeAllPassenger();
		closeMonsterBall(1);
	    closeMonsterBall(2);
	    closeMonsterBall(3);
	}

	public static void allSetVisible(boolean b) {
		for (int i = 0; i < 6; i++) {
			if (Main.newC.elevator1.GetInPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator1.GetInPassengerList[i]]);
				pocketMonImage[Main.newC.elevator1.GetInPassengerList[i]].setVisible(b);
			}
			if (Main.newC.elevator2.GetInPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator2.GetInPassengerList[i]]);
				pocketMonImage[Main.newC.elevator2.GetInPassengerList[i]].setVisible(b);
			}
			if (Main.newC.elevator3.GetInPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator3.GetInPassengerList[i]]);
				pocketMonImage[Main.newC.elevator3.GetInPassengerList[i]].setVisible(b);
			}
			if (Main.newC.elevator1.GetOutPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator1.GetOutPassengerList[i]]);
				pocketMonImage[Main.newC.elevator1.GetOutPassengerList[i]].setVisible(b);
			}
			if (Main.newC.elevator2.GetOutPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator2.GetOutPassengerList[i]]);
				pocketMonImage[Main.newC.elevator2.GetOutPassengerList[i]].setVisible(b);
			}
			if (Main.newC.elevator3.GetOutPassengerList[i] != 0) {
				contentPane.add(pocketMonImage[Main.newC.elevator3.GetOutPassengerList[i]]);
				pocketMonImage[Main.newC.elevator3.GetOutPassengerList[i]].setVisible(b);
			}
		}
		mb1.setVisible(true);
		mb2.setVisible(true);
		mb3.setVisible(true);
	}

	public static void removeAllPassenger() {
		for (int i = 0; i < 6; i++) {
			Main.newC.elevator1.GetInPassengerList[i] = 0;
			Main.newC.elevator2.GetInPassengerList[i] = 0;
			Main.newC.elevator3.GetInPassengerList[i] = 0;

			Main.newC.elevator1.GetOutPassengerList[i] = 0;
			Main.newC.elevator2.GetOutPassengerList[i] = 0;
			Main.newC.elevator3.GetOutPassengerList[i] = 0;
		}
		Main.newC.elevator1.GetInPassengerNum = 0;
		Main.newC.elevator2.GetInPassengerNum = 0;
		Main.newC.elevator3.GetInPassengerNum = 0;
		Main.newC.elevator1.GetOutPassengerNum = 0;
		Main.newC.elevator2.GetOutPassengerNum = 0;
		Main.newC.elevator3.GetOutPassengerNum = 0;
		Main.newC.Emove[1] = 0;
		Main.newC.Emove[2] = 0;
		Main.newC.Emove[3] = 0;
		time1 = 0;
		time2 = 0;
		time3 = 0;
	}

	public static void e1GetinPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator1.GetInPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= e1Location) {
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 7),
						80 + ((5 - Main.newC.elevator1.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
	}

	public static void e2GetinPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator2.GetInPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= e2Location) {
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 5),
						80 + ((5 - Main.newC.elevator2.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
	}

	public static void e3GetinPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator3.GetInPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= e3Location) {
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 3),
						80 + ((5 - Main.newC.elevator3.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
	}

	public static void e1GetOutPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator1.GetOutPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= 0) {
				if (time1 == 0)
					pocketMonImage[personNum[i]].setBounds(e1Location,
							80 + ((5 - Main.newC.elevator1.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 3),
						80 + ((5 - Main.newC.elevator1.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
		time1++;
	}

	public static void e2GetOutPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator2.GetOutPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= 0) {
				if (time2 == 0)
					pocketMonImage[personNum[i]].setBounds(e2Location,
							80 + ((5 - Main.newC.elevator2.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 5),
						80 + ((5 - Main.newC.elevator2.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
		time2++;
	}

	public static void e3GetOutPassenger() {
		int[] personNum = new int[6];
		for (int i = 0; i < 6; i++) {
			personNum[i] = Main.newC.elevator3.GetOutPassengerList[i];
			if (personNum[i] != 0 && pocketMonImage[personNum[i]].getX() >= 0) {
				if (time3 == 0)
					pocketMonImage[personNum[i]].setBounds(e3Location,
							80 + ((5 - Main.newC.elevator3.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setBounds(pocketMonImage[personNum[i]].getX() - (i + 7),
						80 + ((5 - Main.newC.elevator3.getFloor()) * 180), 120, 120);
				pocketMonImage[personNum[i]].setVisible(true);
			}
		}
		time3++;
	}

	public static void MovePassengerToWait(int passengerNum, int currentFloor) {// 1920이려나

		int floorLocation = 80 + ((5 - currentFloor) * 180);
		contentPane.add(pocketMonImage[passengerNum]);
		pocketMonImage[passengerNum].setVisible(true);

		while (pocketMonImage[passengerNum].getX() != 1500) {
			pocketMonImage[passengerNum].setBounds(pocketMonImage[passengerNum].getX() + 1, floorLocation, 120, 120);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pocketMonImage[passengerNum].setVisible(false);
	}

	public static void MoveEpassengerToFinish(int passengerNum, int currentFloor) {
		int floorLocation = 80 + ((5 - currentFloor) * 180);
		contentPane.add(pocketMonImage[passengerNum]);
		pocketMonImage[passengerNum].setVisible(true);

		while (pocketMonImage[passengerNum].getX() != 0) {
			pocketMonImage[passengerNum].setBounds(pocketMonImage[passengerNum].getX() - 1, floorLocation, 120, 120);
			try {
				Thread.sleep(1);
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
		timeLine += (h < 10) ? "0" + (int) h : (int) h;
		timeLine += ":";
		timeLine += (m < 10) ? "0" + (int) m : (int) m;
		timer.setText(timeLine);

	}

	public static void resetState(int eNum) {
		emergency[eNum].setVisible(false);
		idle[eNum].setVisible(false);
		up[eNum].setVisible(false);
		down[eNum].setVisible(false);
	}

	public static void setEmergency(int eNum) {
		resetState(eNum - 1);
		emergency[eNum - 1].setVisible(true);
	}

	public static void setIdle(int eNum) {
		resetState(eNum - 1);
		idle[eNum - 1].setVisible(true);
	}

	public static void setUp(int eNum) {
		resetState(eNum - 1);
		up[eNum - 1].setVisible(true);
	}

	public static void setDown(int eNum) {
		resetState(eNum - 1);
		down[eNum - 1].setVisible(true);
	}

	public static void setStateText(String str) {
		time_state.setText(str);
	}

	public static void setFloorWaitingNum(int Floor) {
		FloorWaitingNum[Floor].setText("" + Main.waitingPersonNum[Floor]);
		System.out.println("FLOOR : " + Floor + " PERSON NUM : " + Main.waitingPersonNum[Floor]);
	}

	// 추가함!
	public static void openMonsterBall(int eNum) {
		if (eNum == 1) {
			mb1_o.setBounds(mb1.getX(), mb1.getY(), 115, 110);
			mb1.setVisible(false);
			mb1_o.setVisible(true);
		} else if (eNum == 2) {
			mb2_o.setBounds(mb2.getX(), mb2.getY(), 115, 110);
			mb2.setVisible(false);
			mb2_o.setVisible(true);
		} else if (eNum == 3) {
			mb3_o.setBounds(mb3.getX(), mb3.getY(), 115, 110);
			mb3.setVisible(false);
			mb3_o.setVisible(true);
		}
	}

	// 추가함!
	public static void closeMonsterBall(int eNum) {
		if (eNum == 1) {
			mb1.setVisible(true);
			mb1_o.setVisible(false);
		} else if (eNum == 2) {
			mb2.setVisible(true);
			mb2_o.setVisible(false);
		} else if (eNum == 3) {
			mb3.setVisible(true);
			mb3_o.setVisible(false);
		}
	}

	public static void setInElevator(int eNum, int num) {
		if (num < 10)
			e_people[eNum - 1].setText("" + num);
		else
			e_people[eNum - 1].setText("F");
	}

}