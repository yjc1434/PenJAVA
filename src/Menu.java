import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
	    static JButton close_menu_bt;
		
		static JButton mainmenu_bt;// Ȩ ��ư
		static JButton postmenu_bt;// �۾��� ��ư
		static JButton diary_bt;// ���ϱ� ��ư
		static JLabel menu_bg;// ���ϱ� ��ư
		
		ImageIcon img_menu = new ImageIcon("image/�޴� �г�.png");
		ImageIcon close_bt = new ImageIcon("image/�޴� �ݱ�.png");
		ImageIcon img_menu1 = new ImageIcon("image/�޴���ư1.png");
		ImageIcon img_menu2 = new ImageIcon("image/�޴���ư2.png");
		ImageIcon img_menu3 = new ImageIcon("image/�޴���ư3.png");
		ImageIcon rollingimg_menu1 = new ImageIcon("image/���ø޴���ư1.png");
		ImageIcon rollingimg_menu2 = new ImageIcon("image/���ø޴���ư2.png");
		ImageIcon rollingimg_menu3 = new ImageIcon("image/���ø޴���ư3.png");
	
	public Menu() {
		setLayout(null);
        setBounds(0,0,1280,720);
		
        menu_bg = new JLabel(img_menu);
		menu_bg.setBounds(0,0,265,720);
		
		close_menu_bt = new JButton(close_bt);
		close_menu_bt.setBounds(214,0,51,61);
		close_menu_bt.setBorderPainted(false); 		//�ܰ��� ����
		close_menu_bt.setFocusPainted(false); 		//���� ���� ä��� ����
		close_menu_bt.setContentAreaFilled(false); 	//���� �Ǿ����� �׵θ� ������
		
		mainmenu_bt = new JButton(img_menu1); //Ȩ ��ư
		mainmenu_bt.setBounds(0,264,265,52);
		mainmenu_bt.setBorderPainted(false); 		//�ܰ��� ����
		mainmenu_bt.setFocusPainted(false); 		//���� ���� ä��� ����
		mainmenu_bt.setContentAreaFilled(false); 	//���� �Ǿ����� �׵θ� ������
		mainmenu_bt.setRolloverIcon(rollingimg_menu1);
		
		postmenu_bt = new JButton(img_menu2);
		postmenu_bt.setBounds(0,316,265,52);
		postmenu_bt.setBorderPainted(false); 		//�ܰ��� ����
		postmenu_bt.setFocusPainted(false); 		//���� ���� ä��� ����
		postmenu_bt.setContentAreaFilled(false); 	//���� �Ǿ����� �׵θ� ������
		postmenu_bt.setRolloverIcon(rollingimg_menu2);
		
		diary_bt = new JButton(img_menu3);
		diary_bt.setBounds(0,368,265,52);
		diary_bt.setBorderPainted(false); 		//�ܰ��� ����
		diary_bt.setFocusPainted(false); 		//���� ���� ä��� ����
		diary_bt.setContentAreaFilled(false); 	//���� �Ǿ����� �׵θ� ������
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
