import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

public class Pen_Messenger extends JFrame {

	JPanel toolBar = new JPanel();
	JPanel tPanel = new JPanel();
	JTextField tField = new JTextField(20);
	ImageIcon img_sendbt = new ImageIcon("image/전송버튼1.png");
	ImageIcon img_sendbt_roll = new ImageIcon("image/전송버튼2.png");
	ImageIcon img_icon = new ImageIcon("image/human.png");
	JLabel tLabel = new JLabel("입력창>>  ");
	JButton tButton = new JButton(img_sendbt);
	Vector<Chatting> chat = new Vector<>();
	JScrollPane jScrollPane;
	String lastDay = "";
	int size = 7;

	public Pen_Messenger() {
		setTitle("MyMessage");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(null);
		tPanel.setLayout(new GridLayout(size,1));
		tPanel.setBackground(new Color(77,89,108));
		tPanel.setBounds(20, 0, 280, 670);
		toolBar.setBounds(-10, 670, 400, 90);
		tButton.setRolloverIcon(img_sendbt_roll);
		tButton.setPreferredSize(new Dimension(63,27));
		tButton.setBorderPainted(false); 		//외곽선 제거
		tButton.setFocusPainted(false); 		//내용 영역 채우기 안함
		tButton.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		addScroll();
		add(toolBar);
		toolBar.setLayout(new FlowLayout());
		toolBar.add(tLabel);
		tLabel.setForeground(Color.WHITE);
		toolBar.add(tField);
		toolBar.add(tButton);
		toolBar.setBackground(new Color(48,55,68));

		setSize(400, 750);
		setResizable(false);
		setLocationRelativeTo(null);//사이즈 정한뒤 해줘야함
		setVisible(true);
	}

	void add(String user, String message, String date, String time, boolean my) {
		int mine = 1;
		remove(jScrollPane);
		tPanel = new JPanel();
		tPanel.setBackground(new Color(77,89,108));
		if (chat.size() >= size) {
			tPanel.setLayout(new GridLayout(chat.size() + 1,1));
		}
		else {
			tPanel.setLayout(new GridLayout(size, 1));
		}
		if (my) {
			mine = 0;
		}
		if (!lastDay.equals(date)) {
			chat.add(new Chatting("",date,"",2));
			lastDay = date;
		}
		chat.add(new Chatting(user,message,time,mine));
		for (int i = 0; i < chat.size(); i++) {
			tPanel.add(chat.get(i));
		}
		addScroll();
		jScrollPane.getVerticalScrollBar().setValue(100);
		jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
	}

	void addScroll() {
		jScrollPane = new JScrollPane(tPanel);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(0, 0, 400, 670);
		jScrollPane.getVerticalScrollBar().setUnitIncrement(20);	//마우스 스크롤 속도
		jScrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				for (int i = 0; i < chat.size(); i++) {
					chat.get(i).repaint();
				}
			}
		});
		add(jScrollPane);
	}

	class Chatting extends JPanel{
		int type;
		JLabel user, message, time, blank, icon;
		public Chatting(String user, String message, String time, int tpy) {
			int hour,min,sec;
			String nHour,nMin;
			boolean AM = true;
			type = tpy;
			if(type != 2) {
				hour = Integer.parseInt(time.substring(0,time.indexOf(":")));
				time = time.substring(time.indexOf(":") + 1);
				min = Integer.parseInt(time.substring(0,time.indexOf(":")));
				sec = Integer.parseInt(time.substring(time.indexOf(":") + 1));
				if(hour > 12) {
					AM = false;
					hour -= 12;
				}
				else if (hour == 12) {
					AM = false;
				}
				else if (hour == 0) {
					hour = 12;
				}
				if (hour < 10) {
					nHour = "0" + hour;
				}
				else {
					nHour = Integer.toString(hour);
				}
				if (min < 10) {
					nMin = "0" + min;
				}
				else {
					nMin = Integer.toString(min);
				}
				if(AM) {
					time = "오전 " + nHour + "시 " + nMin + "분";
				}
				else {
					time = "오후 " + nHour + "시 " + nMin + "분";
				}
			}
			this.user = new JLabel("                 " + user + "   ");
			this.message = new JLabel("             " + message + "  ");
			this.time = new JLabel("                 " + time + "  ");
			this.user.setForeground(Color.WHITE);
			this.message.setForeground(Color.WHITE);
			this.time.setForeground(Color.WHITE);
			blank = new JLabel(" ");
			setLayout(new GridLayout(4,1));
			setBackground(new Color(77,89,108));

			if(type == 0) {
				this.user.setHorizontalAlignment(SwingConstants.RIGHT);
				this.message.setHorizontalAlignment(SwingConstants.RIGHT);
				this.time.setHorizontalAlignment(SwingConstants.RIGHT);
				this.message.setFont(this.message.getFont().deriveFont(15.0f));
				add(this.message);
				add(this.time);
			}
			else if (type == 1){
				add(this.user);
				this.message.setFont(this.message.getFont().deriveFont(15.0f));
				add(this.message);
				add(this.time);
			}
			else if (type == 2) { //시간
				add(blank);
				this.message.setHorizontalAlignment(SwingConstants.CENTER);
				add(this.message);
			}
		}

		public void paintComponent(Graphics g) {
			if (type == 1) {
				super.paintComponent(g);
				Image img = img_icon.getImage();
				g.drawImage(img, 5, 10, 35, 35, null); //이미지를 그린다
			}
			jScrollPane.repaint();
		}
	}
}
