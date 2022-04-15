import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class Info_Myinfo extends JDialog {
	private ImageIcon bgimg = new ImageIcon("image/info.png");
	static ImageIcon okbt_img = new ImageIcon("image/infobt2.png");
	static ImageIcon cancelbt_img = new ImageIcon("image/infobt3.png");

	private JLabel bglb = new JLabel(bgimg);
	static JLabel namelb = new JLabel("관리자");
	static JLabel idlb = new JLabel("root");
	static JLabel pwlb = new JLabel("123455");
	private JLabel blb = new JLabel("생년월일 :");
	static JLabel birthdaylb = new JLabel("98888");
	private JLabel glb = new JLabel("성별 :");
	static JLabel genderlb = new JLabel("남");
	static JButton okbt = new JButton(okbt_img);
	static JButton cancelbt = new JButton(cancelbt_img);
	static JButton delete;

	public Info_Myinfo( String title) {
		setLayout(null);

		bglb.setBounds(0,0, 416, 285);
		okbt.setVisible(false);
		cancelbt.setVisible(false);

		Font f = new Font("나눔스퀘어 Bold", Font.BOLD, 50);

		namelb.setFont(f);
		namelb.setForeground(Color.WHITE);
		namelb.setBounds(175, 3, 200, 100);

		f = new Font("나눔스퀘어 Bold", Font.BOLD, 16);
		idlb.setFont(f);
		idlb.setForeground(Color.WHITE);
		idlb.setBounds(185, 76, 200, 50);

		f = new Font("나눔스퀘어 Bold", Font.BOLD, 20);

		blb.setFont(f);
		blb.setForeground(Color.WHITE);
		blb.setBounds(160, 115, 130, 60);

		birthdaylb.setFont(f);
		birthdaylb.setForeground(Color.WHITE);
		birthdaylb.setBounds(260, 115, 130, 60);

		glb.setFont(f);
		glb.setForeground(Color.WHITE);
		glb.setBounds(160, 150, 100, 60);

		genderlb.setFont(f);
		genderlb.setForeground(Color.WHITE);
		genderlb.setBounds(220, 150, 60, 60);

		delete = new JButton("회원탈퇴");
		delete.setBorderPainted(false); 		//외곽선 제거
		delete.setFocusPainted(false); 		//내용 영역 채우기 안함
		delete.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		delete.setForeground(Color.WHITE);
		delete.setFont(f);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		delete.setBounds(245,205,200,30);

		JButton change = new JButton("비밀번호 변경");
		change.setBorderPainted(false); 		//외곽선 제거
		change.setFocusPainted(false); 		//내용 영역 채우기 안함
		change.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		change.setForeground(Color.WHITE);
		change.setFont(f);
		change.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String PW = JOptionPane.showInputDialog(null, "바꿀 비밀번호를 입력해주세요").trim();
				if(PW.equals(null)) {
					try {
						String Message = "!password " + Info_Myinfo.idlb.getText().trim() + " " + PW.trim();
						Main.soc.getOutputStream().write(Message.getBytes());
					} catch (IOException ioException) {
						//ioException.printStackTrace();
					}
				}
			}
		});
		change.setBounds(100,205,200,30);
		add(change);
		add(delete);
		add(namelb);
		add(idlb);
		add(blb);
		add(birthdaylb);
		add(glb);
		add(genderlb);
		add(bglb);
		setSize(416, 285);
		setResizable(false);
		setLocationRelativeTo(null);//사이즈 정한뒤 해줘야함
	}
}