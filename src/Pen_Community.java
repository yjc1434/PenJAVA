import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pen_Community extends JPanel{
	ImageIcon img_home = new ImageIcon("image/home.jpg");
	ImageIcon main_img = new ImageIcon("image/홈버튼.png");
	ImageIcon menu_btn = new ImageIcon("image/메뉴 버튼.png");

	static Menu menu = new Menu();
	static Pen_Post pen_p;
	static Friend_add fa;
	static Draw pen_Draw;

	static Pen_Friend pen_f;
	static Friend_add pen_fa;
	
	static JButton main_bt;
	static JButton menu_bt;

	public Pen_Community(Draw draw) {
		setLayout(null);
		setBounds(0,0,1280,720);
		
		menu.setLocation(-256,0);
		menu.setVisible(false);
		add(menu);
		
		pen_p = new Pen_Post();
		pen_Draw = draw;
		pen_p.setLocation(250,68);
		
		pen_Draw.setLocation(250, 68);
		pen_Draw.setVisible(false);
		
		pen_f = new Pen_Friend();
		pen_f.setLocation(1000,188);
		
		fa = new Friend_add();
		fa.setLocation(1000,68);
		add(fa);
		
		menu_bt = new JButton(menu_btn);	//메뉴 버튼
		menu_bt.setBounds(0,0,59,61);
		menu_bt.setBorderPainted(false); 		//외곽선 제거
		menu_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		menu_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		menu_bt.addActionListener(new ActionListener() { //메뉴 열기
			@Override
			public void actionPerformed(ActionEvent e) {
				buThread th = new buThread(menu,-256, 0);
				th.start();
				menu.close_menu_bt.setVisible(true);
			}
		});
		add(menu_bt);
		
		menu.close_menu_bt.addActionListener(new ActionListener() { //메뉴 닫기
			@Override
			public void actionPerformed(ActionEvent e) {
				buThread2 th2 = new buThread2(menu,-16, 0);
				th2.start();
				menu.close_menu_bt.setVisible(false);
			}
		});
		
		JLabel home_bg = new JLabel(img_home);
		home_bg.setBounds(0,0,1280,720);
		Info_Myinfo info_my = new Info_Myinfo("내 정보");
		
		main_bt = new JButton(main_img); //메인버튼
		main_bt.setBounds(610,0,61,61);
		main_bt.setBorderPainted(false); 		//외곽선 제거
		main_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
		main_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
		main_bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Menu.mainmenu_bt.doClick();
			}
		});

		pen_fa.myinfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				info_my.setVisible(true);
			}
		});

		add(main_bt);

		menu.mainmenu_bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buThread2 th2 = new buThread2(menu,-16, 0);
				th2.start();
				pen_Draw.setVisible(false);
				pen_Draw.clear();
				pen_p.setVisible(true);
			}
		});

		menu.postmenu_bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buThread2 th2 = new buThread2(menu,-16, 0);
				th2.start();
				pen_Draw.setVisible(true);
				pen_p.setVisible(false);
			}
		});

		menu.diary_bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buThread2 th2 = new buThread2(menu,-16, 0);
				th2.start();
				pen_p.setVisible(false);
				pen_p.setVisible(true);
			}
		});

		Draw.savebt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				pen_Draw.setVisible(false);
				pen_p.setVisible(true);
			}
		});




		add(pen_p);
		add(pen_f);

		add(pen_Draw);
		add(home_bg);
		
		setSize(1280,720);
		setVisible(false);
	}
	
	class buThread extends Thread{
		ImageIcon ballon = new ImageIcon("images/메뉴 패널.png");
		private JPanel menu;
		private int x,y;
		public buThread(JPanel menu,int x, int y) {
			this.menu=menu;
			this.x = x;
			this.y = y;
		}
		public void run() {
			menu.setLocation(x,y);
			menu.setVisible(true);
			while(true) {
				try {
					Thread.sleep(20);
					if(x >= -16) {
						return;
					}
					else {
						x += 20;
						menu.setLocation(x,y);
						menu.setVisible(true);
					}
				} catch(InterruptedException e) {
					return;
				}
			}
		}
	}
	
	class buThread2 extends Thread{
		ImageIcon ballon = new ImageIcon("images/메뉴 패널.png");
		private JPanel menu;
		private int x,y;
		public buThread2(JPanel menu,int x, int y) {
			this.menu=menu;
			this.x = x;
			this.y = y;
		}
		public void run() {
			while(true) {
				menu.setLocation(x,y);
				menu.setVisible(true);
				try {
					Thread.sleep(20);
					if(x <= -270) {
						return;
					}
					else {
						x -= 20;
						menu.setLocation(x,y);
						menu.setVisible(false);
					}
				} catch(InterruptedException e) {
					return;
				}
			}
		}
	}
}