import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

public class AcceptFriend_Dialog extends JDialog{
	Vector<JLabel> AcceptfriendId = new Vector<>();
	Vector<String> friendnid = new Vector<>();
	Vector<String> friendname = new Vector<>();
	JPanel Acceptfriends = new JPanel();
	ImageIcon human_img = new ImageIcon("image/human1.png");
	ImageIcon acceptbg_img = new ImageIcon("image/Acceptbg.png");
	ImageIcon addbt_img = new ImageIcon("image/addbt.png");
	ImageIcon nobt_img = new ImageIcon("image/nobt.png");
	ImageIcon rolladdbt_img = new ImageIcon("image/rolladdbt.png");
	JLabel top = new JLabel(acceptbg_img);

	public AcceptFriend_Dialog(String title) {
		setLayout(null);
		Acceptfriends.setBackground(new Color(78,88,107));
		if(AcceptfriendId.size() <= 14 )
			Acceptfriends.setLayout(new GridLayout(100,0,0,5));
		else
			Acceptfriends.setLayout(new GridLayout(AcceptfriendId.size(),1));

		Acceptfriends.add(top);

		setFriendId();

		JScrollPane jScrollPane = new JScrollPane(Acceptfriends);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(0,0,280,363);
		jScrollPane.getVerticalScrollBar().setUnitIncrement(20);	//마우스 스크롤 속도
		add(jScrollPane);

		setSize(275, 400);
		setResizable(false);
		setLocationRelativeTo(null);//사이즈 정한뒤 해줘야함
	}
	void set(){
		try {
			String Message = "!acceptfriend " + Info_Myinfo.idlb.getText();
			Main.soc.getOutputStream().write(Message.getBytes());
			byte[] buffer = new byte[1048];
			Main.soc.getInputStream().read(buffer);
			int count = Integer.parseInt( new String(buffer).trim());
			System.out.println(count);

			buffer = new byte[1048];
			Main.soc.getInputStream().read(buffer);
			String _data = new String(buffer);
			System.out.println(_data);
			String[] parsedString;
			parsedString = _data.split(" ");
			AcceptfriendId = new Vector<>();
			friendnid = new Vector<>();
			for(int i =0; i<count; i++){
				friendnid.add(parsedString[i+1].trim());

				Message = "!exe " + friendnid.get(i).trim();
				Main.soc.getOutputStream().write(Message.getBytes());
				buffer = new byte[1048];

				Main.soc.getInputStream().read(buffer);
				_data = new String(buffer);
				friendname.add(_data.trim());

				System.out.println(friendname.get(i));
				AcceptfriendId.add(new JLabel(friendname.get(i)));
			}
			for(int i =0; i<count; i++){

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void setFriendId(){
		set();
		Font f = new Font("나눔스퀘어 Bold", Font.PLAIN, 18);
		for(int i=0; i< AcceptfriendId.size(); i++){
			int count = i;
			JPanel friend = new JPanel();
			friend.setLayout(null);
			friend.setBackground(new Color(78,88,107));

			JLabel hlb = new JLabel(human_img);
			hlb.setBounds(15,0, 35, 35);
			friend.add(hlb);

			JLabel namelb = AcceptfriendId.get(i);
			namelb.setBounds(60, 0, 50,30);
			namelb.setForeground(Color.white);
			namelb.setFont(f);
			friend.add(AcceptfriendId.get(i));

			JButton chatbt = new JButton(addbt_img);
			chatbt.setBorderPainted(false); 		//외곽선 제거
			chatbt.setFocusPainted(false); 		//내용 영역 채우기 안함
			chatbt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
			chatbt.setBounds(135, 5, 50, 20);
			chatbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null,friendnid.get(count)+"님 친구요청을 수락 하시겠습니까?",
							"친구추가",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						String Message = "!accept " + Info_Myinfo.idlb.getText() + " " + friendnid.get(count);
						try {
							Main.soc.getOutputStream().write(Message.getBytes());
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						dispose();
					}
				}
			});

			JButton chatbt1 = new JButton(nobt_img);
			chatbt1.setBorderPainted(false); 		//외곽선 제거
			chatbt1.setFocusPainted(false); 		//내용 영역 채우기 안함
			chatbt1.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
			chatbt1.setBounds(195, 5, 50, 20);
			chatbt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null,friendnid.get(count)+"님 친구요청을 취소 하시겠습니까?",
							"친구추가",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						String Message = "!reject " + Info_Myinfo.idlb.getText() + " " + friendnid.get(count);
						try {
							Main.soc.getOutputStream().write(Message.getBytes());
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						dispose();
					}
				}
			});

			friend.add(chatbt);
			friend.add(chatbt1);
			Acceptfriends.add(friend);
		}
	}
}
