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
			public void paintComponent(Graphics g) {// Graphics��ü�� �׸��� �ִ� ����.
				// �̹���ó��. ���
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
		
		JPanel mb1 = new JPanel(){
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		JPanel mb2 = new JPanel(){
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		JPanel mb3 = new JPanel(){
			public void paintComponent(Graphics g) {
				setOpaque(false);
				Image backImg = new ImageIcon("monsterball.png").getImage();
				g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
			}
		};

		mb1.setSize(120, 120);
		mb1.setLayout(null);
		mb1.setBounds(273, 80, 115, 110);
		mb1.setVisible(true);
		
		mb2.setSize(120, 120);
		mb2.setLayout(null);
		mb2.setBounds(619, 80, 115, 110);
		mb2.setVisible(true);

		mb3.setSize(1700, 120);
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
			// Loop ����true�� ������������ѹݺ���ŵ�ϴ�.
			// false�� �ѹ��������ŵ�ϴ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeUI() {
		
	
	}

}
