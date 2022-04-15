import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class Pen_Login extends JPanel{
    ImageIcon img_login = new ImageIcon("image/login (2).png");
    ImageIcon [] img_bg = {new ImageIcon("image/main1.JPG"),new ImageIcon("image/main2.JPG"),
            new ImageIcon("image/main3.JPG"),new ImageIcon("image/main4.JPG")};
    ImageIcon img_loginbt = new ImageIcon("image/button7.png");
    ImageIcon rollimg_loginbt = new ImageIcon("image/button8.png");
    ImageIcon img_backbt = new ImageIcon("image/button9.png");
    static JTextField id_tf;
    static JPasswordField pw_tf;
    static JButton login_bt, back_bt;
    static JLabel login_bl;
    static FindPanel find;
    public Pen_Login(){
        setLayout(null);
        setBounds(0,0,1280,720);

        JLabel main_bg = new JLabel(img_login);
        main_bg.setBounds(0,0,1280,720);
        JLabel sub_bg = new JLabel(img_bg[2]);
        sub_bg.setBounds(0,0,1280,720);

        login_bt = new JButton(img_loginbt);
        login_bt.setBounds(486,433,306,46);
        login_bt.setRolloverIcon(rollimg_loginbt);
        login_bt.setBorderPainted(false); 		//외곽선 제거
        login_bt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        login_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
        login_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함

        back_bt = new JButton(img_backbt);
        back_bt.setBounds(0,7,131,41);
        back_bt.setBorderPainted(false); 		//외곽선 제거
        back_bt.setFocusPainted(false); 		//내용 영역 채우기 안함
        back_bt.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함

        id_tf = new JTextField(10);
        id_tf.setBounds(500,302,275,45);
        id_tf.setOpaque(false);
        id_tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());//텍스트필드 경계선 제거
        //id_tf.setText("root");

        pw_tf = new JPasswordField(10);
        pw_tf.setBounds(500,350,275,45);
        pw_tf.setOpaque(false);
        pw_tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());//텍스트필드 경계선 제거
        //pw_tf.setText("1q2w3e4r");

        login_bl = new JLabel();
        login_bl.setForeground(ColorUIResource.RED);
        login_bl.setBounds(486,390,200,50);

        find = new FindPanel();
        find.setBounds(486, 480, 306, 46);

        add(find);

        add(back_bt);
        add(id_tf);
        add(pw_tf);
        add(login_bt);
        add(login_bl);
        add(main_bg);
        add(sub_bg);

        setSize(1280,720);
        setVisible(false);
    }

    class FindPanel extends JPanel {
        JButton id,pw;
        Font f1 = new Font("나눔스퀘어 Bold", Font.PLAIN, 15);
        FindPanel() {
            setLayout(new GridLayout(1,2));
            setBackground(Color.white);
            id = new JButton("아이디 찾기");
            id.setFont(f1);
            pw = new JButton("비밀번호 찾기");
            pw.setFont(f1);
            id.setBorderPainted(false); 		//외곽선 제거
            id.setFocusPainted(false); 		//내용 영역 채우기 안함
            id.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
            pw.setBorderPainted(false); 		//외곽선 제거
            pw.setFocusPainted(false); 		//내용 영역 채우기 안함
            pw.setContentAreaFilled(false); 	//선택 되었을때 테두리 사용안함
            add(id);
            add(pw);
        }
    }
}