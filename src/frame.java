import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class frame extends JFrame {

	ImageIcon icon;

	frame() {
		setTitle("Pocketmon Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		makeUI();
		setSize(1920, 1080);
		setResizable(false);
		setVisible(true);
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

	public void paint(Graphics g) {// Graphics객체는 그릴수 있는 도구.
		// 이미지처리. 배경
		Image backImg = new ImageIcon("image.png").getImage();
		g.drawImage(backImg, 0, 0, getWidth(), getHeight(), this);
	}

	private void makeUI() {
		this.setLayout(null);
		this.setUndecorated(true);
		JPanel floor = new JPanel();
		//floor.setBounds(0,0,0);
	}

}
