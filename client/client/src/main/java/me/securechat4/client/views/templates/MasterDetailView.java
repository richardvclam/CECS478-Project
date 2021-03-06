package me.securechat4.client.views.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import me.securechat4.client.Window;
import me.securechat4.client.views.View;

public class MasterDetailView extends JSplitPane {

	public MasterDetailView(JPanel masterView, JPanel detailView) {
		super(JSplitPane.HORIZONTAL_SPLIT, masterView, detailView);
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		setBorder(null);
		setDividerSize(0);
		setDividerLocation(Window.WINDOW_WIDTH / 2);
		setContinuousLayout(true);
		setEnabled(false);
		
		masterView.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(142, 142, 146)));
	}
	

}
