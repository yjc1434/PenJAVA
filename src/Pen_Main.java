import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pen_Main extends JPanel{
    ImageIcon img_main = new ImageIcon("image/login (1).png");
    ImageIcon [] img_bg = {new ImageIcon("image/main1.JPG"),new ImageIcon("image/main2.JPG"),
            new ImageIcon("image/main3.JPG"),new ImageIcon("image/main4.JPG")};
    ImageIcon img_signbt = new ImageIcon("image/button3.png");
    ImageIcon img_loginbt = new ImageIcon("image/button4.png");
    ImageIcon rollimg_signbt = new ImageIcon("image/button5.png");
    ImageIcon rollimg_loginbt = new ImageIcon("image/button6.png");

    static JButton sign_bt,login_bt;
    public Pen_Main() {
        setLayout(null);
        setBounds(0,0,1280,720);

        JLabel main_bg = new JLabel(img_main);
        main_bg.setBounds(0,0,1280,720);

        JLabel sub_bg = new JLabel(img_bg[2]);
        sub_bg.setBounds(0,0,1280,720);

        sign_bt = new JButton(img_signbt);
        sign_bt.setBounds(487,360,306,46);
        sign_bt.setRolloverIcon(rollimg_signbt);

        login_bt = new JButton(img_loginbt);
        login_bt.setBounds(487,408,306,46);
        login_bt.setRolloverIcon(rollimg_loginbt);

        sign_bt.setBorderPainted(false); 		//외곽선 제거
        sign_bt.setFocusPainted(false); 		//내용 영역 채우기s 안함
        sign_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함

        login_bt.setBorderPainted(false); 		//외곽선 제거
        login_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
        login_bt.setContentAreaFilled(false);	//선택 되었을때 테두리 사용안함

        add(sign_bt);
        add(login_bt);
        add(main_bg);
        add(sub_bg);

        setSize(1280,720);
        setVisible(true);
    }
}