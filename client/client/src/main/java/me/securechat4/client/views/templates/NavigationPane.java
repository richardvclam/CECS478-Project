package me.securechat4.client.views.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NavigationPane extends JPanel {
	
	private JPanel header;
	private JLabel headerLabel;
	
	public NavigationPane(String headerStr, Component content) {
		setLayout(new BorderLayout());
		
		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		header = new JPanel(new BorderLayout());
		header.setBackground(new Color(245, 245, 245));
		header.setBorder(BorderFactory.createLineBorder(new Color(237, 237, 237), 1));
		
		headerLabel = new JLabel(headerStr);
		headerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		headerLabel.setBackground(new Color(245, 245, 245));
		headerLabel.setFont(labelFont);
		headerLabel.setHorizontalAlignment(JLabel.CENTER);
		headerLabel.setVerticalAlignment(JLabel.CENTER);
		header.add(headerLabel, BorderLayout.CENTER);

		
		if (content == null) {
			content = new JPanel();
		}
		
		content.setBackground(Color.WHITE);
		
		add(content, BorderLayout.CENTER);
		add(header, BorderLayout.NORTH);
	}
	
	public JPanel getHeader() {
		return header;
	}
	
	public JLabel getHeaderLabel() {
		return headerLabel;
	}
	
	public void setHeaderLabel(String header) {
		this.headerLabel.setText(header);
	}

}
