import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static javax.swing.JOptionPane.showMessageDialog;

public class SearchDialog extends JDialog{

    JPanel bg = new JPanel();
    Vector <TextPanel> text = new Vector<>();
    ComboPanel combo = new ComboPanel();
    JButton button;

    public SearchDialog(JFrame frame, int mode) { //mode == 0 (아이디 찾기) / == 1 (비밀번호 찾기)
        super(frame,"",false);

        if (mode == 0) {
            setTitle("아이디 찾기");
        }
        else if (mode == 1) {
            setTitle("비밀번호 찾기");
        }
        setLayout(new GridLayout(1,1));
        setBackground(new Color(78,88,107));
        add(bg);
        bg.setLayout(new GridLayout(3 + mode,1, 10, 10));
        bg.setBackground(new Color(78,88,107));
        if(mode == 0) {
            text.add(new TextPanel("이름 입력"));
        }
        else if (mode == 1) {
            text.add(new TextPanel("이름 입력"));
            text.add(new TextPanel("아이디 입력"));
        }
        for (int i = 0; i < text.size(); i++) {
            bg.add(text.get(i));
            bg.add(combo);
        }
        ImageIcon img_ci = new ImageIcon("image/찾기.png");
        button = new JButton(img_ci);
        button.setBorderPainted(false);       //외곽선 제거
        button.setFocusPainted(false);       //내용 영역 채우기 안함
        button.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
        button.addActionListener(new ActionListener() {
            String Message = new String();
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean notEmpty = false;
                    for (int i = 0; i < text.size(); i++) {
                        if(text.get(i).field.getText().equals("")) {
                            notEmpty = true;
                            break;
                        }
                    }
                    if(!notEmpty) {
                        String birth = combo.year_combo.getSelectedItem().toString() + "-" + combo.month_combo.getSelectedItem().toString() + "-" + combo.date_combo.getSelectedItem().toString();
                        if(mode == 1) {
                            Message = "!searchPW " + text.get(0).field.getText() + " " + text.get(1).field.getText() + " " + birth; // "!searchPW 관리자 root 2020-10-01"
                        }
                        else {
                            Message = "!searchID " + text.get(0).field.getText() + " " + birth; // "!searchID 관리자 2020-10-01"
                        }
                        Main.soc.getOutputStream().write(Message.getBytes());
                        String _data;
                        byte[] buffer = new byte[4096];
                        Main.soc.getInputStream().read(buffer);
                        _data = new String(buffer);
                        if (_data.equals("!fail")) {
                            System.out.println(_data);
                        }
                        else {
                            if (mode == 0) {
                                showMessageDialog(null, "아이디 : " + _data.trim());
                            }
                            else if (mode == 1) {
                                showMessageDialog(null, "비밀번호 : " + _data.trim());
                            }
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        bg.add(button);

        setSize(390, 200+(40*mode));
        setLocationRelativeTo(frame);//사이즈 정한뒤 해줘야함
        setVisible(true);
    }

    public Insets getInsets() { //프레임에 여백
        Insets i = new Insets(50,30,20,30);
        return i;
    }

    class TextPanel extends JPanel {
        JLabel label;
        JTextField field;
        public TextPanel(String label) {
            Font f1 = new Font("나눔스퀘어 Bold", Font.PLAIN, 18);
            setLayout(new GridLayout(1,2));
            setBackground(new Color(78,88,107));
            this.label = new JLabel(label);
            this.label.setFont(f1);
            this.label.setForeground(Color.white);
            field = new JTextField(10);
            add(this.label);
            add(this.field);
        }
    }

    class ComboPanel extends JPanel {
        JLabel label,label1,label2,label3;
        JComboBox<String> year_combo,month_combo,date_combo;
        public ComboPanel() {
            setLayout(null);
            setBackground(new Color(78,88,107));
            label = new JLabel("생년월일");
            label1 = new JLabel("년");
            label2 = new JLabel("월");
            label3 = new JLabel("일");
            year_combo = new JComboBox<>();
            month_combo = new JComboBox<>();
            date_combo = new JComboBox<>();
            ((JLabel)year_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
            ((JLabel)month_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
            ((JLabel)date_combo.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);//콤보박스내 글자 우측 정렬
            year_combo.setBackground(Color.white);
            month_combo.setBackground(Color.white);
            date_combo.setBackground(Color.white);
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

            Font f1 = new Font("나눔스퀘어 Bold", Font.PLAIN, 18);

            year_combo.setBounds(72, 0, 60, 30);
            month_combo.setBounds(160, 0, 50, 30);
            date_combo.setBounds(249, 0, 50, 30);

            label.setBounds(0, -10, 100, 50);
            label.setFont(f1);
            label.setForeground(Color.white);

            label1.setBounds(140, -1, 30, 30);
            label1.setFont(f1);
            label1.setForeground(Color.white);

            label2.setBounds(220, -1, 30, 30);
            label2.setFont(f1);
            label2.setForeground(Color.white);

            label3.setBounds(310, -1, 30, 30);
            label3.setFont(f1);
            label3.setForeground(Color.white);

            add(label);
            add(year_combo);
            add(label1);
            add(month_combo);
            add(label2);
            add(date_combo);
            add(label3);
        }
    }
}