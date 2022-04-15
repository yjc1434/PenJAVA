import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.plaf.ColorUIResource;

public class Pen_Sign extends JPanel{
    ImageIcon img_sign = new ImageIcon("image/login (3).png");
    ImageIcon [] backGround = {new ImageIcon("image/main1.JPG"),new ImageIcon("image/main2.JPG"),
            new ImageIcon("image/main3.JPG"),new ImageIcon("image/main4.JPG")};
    ImageIcon img_backbt = new ImageIcon("image/button9.png");
    ImageIcon rolling_backbt = new ImageIcon("image/button10.png");
    ImageIcon img_signbtn = new ImageIcon("image/button3.png");
    ImageIcon rolling_signbtn = new ImageIcon("image/button5.png");

    java.util.Calendar cal = java.util.Calendar.getInstance();

    static JButton sign_bt, back_bt;
    static JTextField sname_tf,sps_tf,sid_tf;
    static JComboBox<String> year_combo,month_combo,date_combo;
    static JRadioButton male_radio,female_radio;
    static ButtonGroup group;
    static JLabel name_lb, id_lb, ps_lb, birth_lb;
    JLabel year,month,date;

    public Pen_Sign() {
        setLayout(null);
        setBounds(0,0,1280,720);

        JLabel main_bg = new JLabel(img_sign);
        main_bg.setBounds(0,0,1280,720);
        JLabel sub_bg = new JLabel(backGround[2]);
        sub_bg.setBounds(0,0,1280,720);

        name_lb = new JLabel();
        name_lb.setForeground(ColorUIResource.RED);
        name_lb.setBounds(685,175,200,50);

        id_lb = new JLabel();
        id_lb.setForeground(ColorUIResource.RED);
        id_lb.setBounds(700,250,200,50);

        ps_lb = new JLabel();
        ps_lb.setForeground(ColorUIResource.RED);
        ps_lb.setBounds(659,325,200,50);

        birth_lb = new JLabel();
        birth_lb.setForeground(ColorUIResource.RED);
        birth_lb.setBounds(685,400,200,50);

        sign_bt = new JButton(img_signbtn);
        sign_bt.setBounds(487,557,306,46);
        sign_bt.setBorderPainted(false);       //외곽선 제거
        sign_bt.setFocusPainted(false);       //내용 영역 채우기 안함
        sign_bt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
        sign_bt.setRolloverIcon(rolling_signbtn);

        back_bt = new JButton(img_backbt);
        back_bt.setBounds(0,7,131,41);
        back_bt.setBorderPainted(false);       //외곽선 제거
        back_bt.setFocusPainted(false);       //내용 영역 채우기 안함
        back_bt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
        back_bt.setRolloverIcon(rolling_backbt);

        sname_tf = new JTextField(10);
        sname_tf.setBounds(500,212,280,45);
        sname_tf.setOpaque(false);
        sname_tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());//텍스트필드 경계선 제거

        sid_tf = new JTextField(10);
        sid_tf.setBounds(500,287,280,45);
        sid_tf.setOpaque(false);
        sid_tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());//텍스트필드 경계선 제거

        sps_tf = new JTextField(10);
        sps_tf.setBounds(500,362,280,45);
        sps_tf.setOpaque(false);
        sps_tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());//텍스트필드 경계선 제거

        year_combo = new JComboBox<String>();
        year_combo.setBounds(493,447,80,30);
        ((JLabel)year_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
        year_combo.setBackground(Color.white);
        year = new JLabel("년");
        year.setBounds(580,457,15,15);

        month_combo = new JComboBox<String>();
        month_combo.setBounds(600,447,60,30);
        ((JLabel)month_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
        month_combo.setBackground(Color.white);
        month = new JLabel("월");
        month.setBounds(665,457,15,15);

        date_combo = new JComboBox<String>();
        date_combo.setBounds(700,447,60,30);
        ((JLabel)date_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
        date = new JLabel("일");
        date.setBounds(765,457,15,15);

        for(int i=2020; i>= 1920; i--) {
            year_combo.addItem(Integer.toString(i));
        }
        for(int i=1; i<= 12; i++) {
            month_combo.addItem(Integer.toString(i));
        }
        for(int i=1; i<= 31; i++) {
            date_combo.addItem(Integer.toString(i));
        }
        year_combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    int year = Integer.parseInt((String)e.getItem());
                    int month = Integer.parseInt((String) month_combo.getSelectedItem());
                    date_combo.removeAllItems();
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        for(int i=1; i<= 31; i++) {
                            date_combo.addItem(Integer.toString(i));
                        }
                    }
                    else if (month == 2) {
                        if(year % 4 == 0) {
                            for(int i=1; i<= 29; i++) {
                                date_combo.addItem(Integer.toString(i));
                            }
                        }
                        else {
                            for(int i=1; i<= 28; i++) {
                                date_combo.addItem(Integer.toString(i));
                            }
                        }
                    }
                    else {
                        for(int i=1; i<= 30; i++) {
                            date_combo.addItem(Integer.toString(i));
                        }
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        month_combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    int year = Integer.parseInt((String) year_combo.getSelectedItem());
                    int month = Integer.parseInt((String)e.getItem());
                    date_combo.removeAllItems();
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        for(int i=1; i<= 31; i++) {
                            date_combo.addItem(Integer.toString(i));
                        }
                    }
                    else if (month == 2) {
                        if(year % 4 == 0) {
                            for(int i=1; i<= 29; i++) {
                                date_combo.addItem(Integer.toString(i));
                            }
                        }
                        else {
                            for(int i=1; i<= 28; i++) {
                                date_combo.addItem(Integer.toString(i));
                            }
                        }
                    }
                    else {
                        for(int i=1; i<= 30; i++) {
                            date_combo.addItem(Integer.toString(i));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        date_combo.setBackground(Color.white);
        date_combo.setOpaque(false);

        Font font = new Font("나눔스퀘어 Bold", Font.PLAIN, 17);
        male_radio = new JRadioButton("남성");
        male_radio.setBounds(495,515,90,30);
        male_radio.setBackground(Color.white);
        male_radio.setFont(font);
        male_radio.setSelected(true);

        female_radio = new JRadioButton("여성");
        female_radio.setBounds(600,515,90,30);
        female_radio.setBackground(Color.white);
        female_radio.setFont(font);
        female_radio.setSelected(false);

        group = new ButtonGroup();
        group.add(male_radio);
        group.add(female_radio);

        add(male_radio);
        add(female_radio);
        add(year_combo);
        add(month_combo);
        add(date_combo);
        add(sname_tf);
        add(sps_tf);
        add(sid_tf);
        add(back_bt);
        add(sign_bt);
        add(name_lb);
        add(id_lb);
        add(ps_lb);
        add(birth_lb);
        add(year);
        add(month);
        add(date);
        add(main_bg);
        add(sub_bg);

        setSize(1280,720);
        setVisible(false);
    }
}