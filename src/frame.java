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
	
	frame() {
		setTitle("Pocketmon Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(){
			public void paintComponent(Graphics g) {// Graphics객체는 그릴수 있는 도구.
				// 이미지처리. 배경
				Image backImg = new ImageIcon("image1.png").getImage();
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
		
		JPanel monsterball = new JPanel(){
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		contentPane.add(monsterball);
		monsterball.setSize(110, 105);
		monsterball.setLayout(null);
		monsterball.setBounds(100
				, 100, 110, 105);
		monsterball.setVisible(true);
		

	
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

}
