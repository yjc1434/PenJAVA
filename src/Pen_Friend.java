import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Pen_Friend extends JPanel {
	static Vector<JLabel> friendId = new Vector<>();
	static Vector<JLabel> friendName = new Vector<>();
	JPanel friends;
	Vector <FriendPanel> friend = new Vector<>();
	JScrollPane jScrollPane;
	String userID;
	static ImageIcon img_fi = new ImageIcon("image/친구.png");
	static ImageIcon img_ci = new ImageIcon("image/채팅.png");

	public Pen_Friend() {
		setLayout(null);
		jScrollPane = new JScrollPane(friends);
		add(jScrollPane);
		setFriendId();
		setSize(275,660);
		setVisible(true);
	}

	void setUserID(String str) {
		userID = str;
	}

	void setFriendId(){
		friend = new Vector<>();
		friends = new JPanel();
		friends.setBackground(new Color(78,88,107));
		if(friendId.size() <= 14 )
			friends.setLayout(new GridLayout(14,1,0,5));
		else
			friends.setLayout(new GridLayout(friendId.size(),1));

		remove(jScrollPane);

		Font f = new Font("맑은 고딕", Font.PLAIN, 15);

		for(int i = 0; i< friendId.size(); i++){
			friend.add(new FriendPanel(i));
			friends.add(friend.get(i));
		}

		jScrollPane = new JScrollPane(friends);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(0,0,280,494);
		jScrollPane.getVerticalScrollBar().setUnitIncrement(20);   //마우스 스크롤 속도
		add(jScrollPane);
	}

	class FriendPanel extends JPanel {
		JLabel label;
		JButton button;
		JLabel fimg;
		Font f1 = new Font("나눔스퀘어 Bold", Font.PLAIN, 18);

		FriendPanel(int i) {
			setBackground(new Color(78,88,107));
			setLayout(new FlowLayout(50));
			label = friendName.get(i);
			label.setFont(f1);
			label.setForeground(Color.white);
			fimg = new JLabel(img_fi);
			button = new JButton(img_ci);
			button.setPreferredSize(new Dimension(39, 36));
			button.setBorderPainted(false);       //외곽선 제거
			button.setFocusPainted(false);       //내용 영역 채우기 안함
			button.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean find = false;
					int j;
					for (j = 0; j < Main.msg.size(); j++) {
						if (Main.msg.get(j).getFriendID().trim().equals(friendId.get(i).getText().trim())) {
							find = true;
							break;
						}
					}
					if(!find) {
						Pen_Messenger msg = new Pen_Messenger();
						msg.setTitle(friendId.get(i).getText().trim() +"와의 채팅방");
						msg.tButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								outputMessage();
								msg.tField.setText("");
								msg.tField.setFocusable(true);
								msg.tField.requestFocus();
							}

							void outputMessage() {
								try {
									String str = "", time = "", Message;
									Calendar cal;
									str = msg.tField.getText();
									cal = Calendar.getInstance(); // 현재 시간을 받아오기 위함
									time = cal.getTime().toString();
									Message = "!message " + userID + " " + friendId.get(i).getText().trim() + " " + time + " " + str;
									System.out.println(Message);
									Main.chattingsoc.getOutputStream().write(Message.getBytes());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});

						msg.tField.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								if (e.getKeyCode() == e.VK_ENTER) {
									msg.tButton.doClick();
								}
							}
						});

						msg.addWindowListener( new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
								if (result == JOptionPane.OK_OPTION) {
									for (int k = 0; k < Main.msg.size(); k++) {
										if(Main.msg.get(k).getFriendID().trim().equals(friendId.get(i).getText().trim())) {
											Main.msg.get(k).getMessenger().dispose();
											Main.msg.remove(k);
											break;
										}

									}
								}
							}
						});
						Main.msg.add(new Messenger(msg,userID,friendId.get(i).getText().trim()));
					}
					else {
						System.out.println("이미 켜져있음");
					}
				}
			});
			add(fimg);
			add(label);
			add(button);
		}
	}
}