import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
	    static JButton close_menu_bt;
		
		static JButton mainmenu_bt;// 홈 버튼
		static JButton postmenu_bt;// 글쓰기 버튼
		static JButton diary_bt;// 내일기 버튼
		static JLabel menu_bg;// 내일기 버튼
		
		ImageIcon img_menu = new ImageIcon("image/메뉴 패널.png");
		ImageIcon close_bt = new ImageIcon("image/메뉴 닫기.png");
		ImageIcon img_menu1 = new ImageIcon("image/메뉴버튼1.png");
		ImageIcon img_menu2 = new ImageIcon("image/메뉴버튼2.png");
		ImageIcon img_menu3 = new ImageIcon("image/메뉴버튼3.png");
		ImageIcon rollingimg_menu1 = new ImageIcon("image/선택메뉴버튼1.png");
		ImageIcon rollingimg_menu2 = new ImageIcon("image/선택메뉴버튼2.png");
		ImageIcon rollingimg_menu3 = new ImageIcon("image/선택메뉴버튼3.png");
	
	public Menu() {
		setLayout(null);
        setBounds(0,0,1280,720);
		
        menu_bg = new JLabel(img_menu);
		menu_bg.setBounds(0,0,265,720);
		
		close_menu_bt = new JButton(close_bt);
		close_menu_bt.setBounds(214,0,51,61);
		close_menu_bt.setBorderPainted(false); 		//외곽선 제거
		close_menu_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		close_menu_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		
		mainmenu_bt = new JButton(img_menu1); //홈 버튼
		mainmenu_bt.setBounds(0,264,265,52);
		mainmenu_bt.setBorderPainted(false); 		//외곽선 제거
		mainmenu_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		mainmenu_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		mainmenu_bt.setRolloverIcon(rollingimg_menu1);
		
		postmenu_bt = new JButton(img_menu2);
		postmenu_bt.setBounds(0,316,265,52);
		postmenu_bt.setBorderPainted(false); 		//외곽선 제거
		postmenu_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		postmenu_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		postmenu_bt.setRolloverIcon(rollingimg_menu2);
		
		diary_bt = new JButton(img_menu3);
		diary_bt.setBounds(0,368,265,52);
		diary_bt.setBorderPainted(false); 		//외곽선 제거
		diary_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		diary_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		diary_bt.setRolloverIcon(rollingimg_menu3);
		
		add(close_menu_bt);
		add(mainmenu_bt);
		add(postmenu_bt);
		add(diary_bt);
		add(menu_bg);
		
        setSize(265,720);
        setVisible(true);
	}
}
