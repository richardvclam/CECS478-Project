package me.securechat4.client.views.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import me.securechat4.client.Window;
import me.securechat4.client.views.View;

public class NavigationView extends JSplitPane {
	
	public NavigationView(JPanel navBar, JPanel content) {
		super(JSplitPane.VERTICAL_SPLIT, navBar, content);
		navBar.setBackground(Color.WHITE);
		content.setBackground(Color.WHITE);
		
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		setBorder(null);
		setDividerSize(1);
		setDividerLocation(50);
		setContinuousLayout(true);
		setEnabled(false);
	}

}
