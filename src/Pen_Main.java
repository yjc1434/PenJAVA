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

        sign_bt.setBorderPainted(false); 		//�ܰ��� ����
        sign_bt.setFocusPainted(false); 		//���� ���� ä���s ����
        sign_bt.setContentAreaFilled(false); 	//���� �Ǿ����� �׵θ� ������

        login_bt.setBorderPainted(false); 		//�ܰ��� ����
        login_bt.setFocusPainted(false); 		//���� ���� ä��� ����
        login_bt.setContentAreaFilled(false);	//���� �Ǿ����� �׵θ� ������

        add(sign_bt);
        add(login_bt);
        add(main_bg);
        add(sub_bg);

        setSize(1280,720);
        setVisible(true);
    }
}