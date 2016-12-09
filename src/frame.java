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
	JPanel contentPane;
	static JPanel mb1;
	static JPanel mb2;
	static JPanel mb3;

	frame() {
		setTitle("Pocketmon Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {// Graphics객체는 그릴수 있는 도구.
				// 이미지처리. 배경
				Image backImg = new ImageIcon("image.png").getImage();
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

		mb1 = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb2 = new JPanel() {
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		mb3 = new JPanel() {
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

	}

	public static void moveElevatorUP(int i) {

		for(int j=0;j<180;j++){
			if(i==1)
				mb1.setBounds(273, mb1.getY()-1, 115, 110);
			else if(i==2)
				mb2.setBounds(619, mb2.getY()-1, 115, 110);
			else
				mb3.setBounds(980, mb3.getY()-1, 115, 110);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
	public static void moveElevatorDOWN(int i) {

		for(int j=0;j<180;j++){
			if(i==1)
				mb1.setBounds(273, mb1.getY()+1, 115, 110);
			else if(i==2)
				mb2.setBounds(619, mb2.getY()+1, 115, 110);
			else
				mb3.setBounds(980, mb3.getY()+1, 115, 110);
			
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	
}
