package me.securechat4.client;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	public static final int WINDOW_WIDTH = 630;
	public static final int WINDOW_HEIGHT = 430;
	private static final String WINDOW_TITLE = "SecureChat";
	
	public Window() {
		// Center window & set window dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(
				(int)(screenSize.getWidth() - WINDOW_WIDTH) / 2,
				(int)(screenSize.getHeight() - WINDOW_HEIGHT) / 2,
				WINDOW_WIDTH,
				WINDOW_HEIGHT
		);
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
}
