import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddFriend_Dialog extends JDialog{
	ImageIcon bgimg = new ImageIcon("image/addfriendmain.png");
	ImageIcon btimg = new ImageIcon("image/addfriendbt.png");
	ImageIcon rollbtimg = new ImageIcon("image/rolladdfriendbt.png");
	JLabel bglb = new JLabel(bgimg);
	public AddFriend_Dialog(String title) {
		setLayout(null);
		bglb.setBounds(0,0,350, 100);
		JTextField addidtf = new JTextField(50);
		JButton addbt = new JButton(btimg);
		addbt.setRolloverIcon(rollbtimg);
		addbt.setBorderPainted(false); 		//외곽선 제거
		addbt.setFocusPainted(false); 		//내용 영역 채우기 안함
		addbt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		addbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(addidtf.getText());
				try {
					if(Info_Myinfo.idlb.getText().trim().equals(addidtf.getText().trim())) {
						showMessageDialog(null, "자신의 계정입니다.");
					}
					else {
						String Message = "!addFriend " + Info_Myinfo.idlb.getText().trim() + " " + addidtf.getText().trim();
						System.out.println(Message);
						Main.soc.getOutputStream().write(Message.getBytes());
						byte[] buffer = new byte[1048];
						Main.soc.getInputStream().read(buffer);
						String _data = new String(buffer);
						System.out.println(_data);
						if (_data.startsWith("!yes")) {
							showMessageDialog(null, "친구신청완료");
							setVisible(false);
						} else if (_data.startsWith("!already")) {
							showMessageDialog(null, "이미 친구신청 하셨습니다.");
						} else if (_data.startsWith("!no")) {
							showMessageDialog(null, "이미 친구입니다.");
						} else if (_data.startsWith("!empty")) {
							showMessageDialog(null, "존재하지않는 계정입니다.");
						}
					}
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
		addidtf.setBounds(84, 30, 250, 25);
		addbt.setBounds(125, 60, 100, 30);
		add(addidtf);
		add(addbt);
		add(bglb);

		setSize(366, 138);
		setResizable(false);
		setLocationRelativeTo(null);//사이즈 정한뒤 해줘야함
	}
}