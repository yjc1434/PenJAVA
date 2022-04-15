import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Friend_add extends JPanel {
    static JButton atm_bt, add_bt, myinfo, out_bt;
    ImageIcon info_img = new ImageIcon("image/human.png");
    ImageIcon bg_img = new ImageIcon("image/������.png");
    ImageIcon fadd_bt_img = new ImageIcon("image/ģ���߰�.png");
    ImageIcon list_bt_img = new ImageIcon("image/��û���.png");
    ImageIcon out_bt_img = new ImageIcon("image/�α׾ƿ�.png");
    static JLabel namelb;
    static JLabel idlb;
        public Friend_add() {
            setLayout(null);
            
            Font f = new Font("���������� Bold", Font.PLAIN, 35);

                AddFriend_Dialog addfriend = new AddFriend_Dialog("ģ�� ��û");
                //// ��û ��� ////
                atm_bt = new JButton(list_bt_img);
                atm_bt.setBounds(95,80,79,34);
                atm_bt.setBorderPainted(false);       //�ܰ��� ����
                atm_bt.setFocusPainted(false);       //���� ���� ä��� ����
                atm_bt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
                atm_bt.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) { 
                        	AcceptFriend_Dialog accept_F = new AcceptFriend_Dialog("���� ��� ���");
                            if(accept_F.isVisible() == true) {
                                  accept_F.setVisible(false);
                             }
                            else {
                               accept_F.setVisible(true);
                            }
                        }
                });
                //// ģ�� �߰� ////
                add_bt = new JButton(fadd_bt_img);
                add_bt.setBounds(10,80,79,34);
                add_bt.setBorderPainted(false);       //�ܰ��� ����
                add_bt.setFocusPainted(false);       //���� ���� ä��� ����
                add_bt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
                add_bt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                addfriend.setVisible(true);
                        }
                });
                //// �� ���� ////
                myinfo = new JButton(info_img);
                myinfo.setBounds(13, 10, 69, 68);
                myinfo.setBorderPainted(false);       //�ܰ��� ����
                myinfo.setFocusPainted(false);       //���� ���� ä��� ����
                myinfo.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
                	
                namelb = new JLabel("�躴����");
                namelb.setFont(f);
                namelb.setForeground(Color.white);
                namelb.setBounds(97,-10,300,100);
                /// �α� �ƿ�///
                out_bt = new JButton(out_bt_img);
                out_bt.setBounds(180,80,79,35);
                out_bt.setBorderPainted(false);       //�ܰ��� ����
                out_bt.setFocusPainted(false);       //���� ���� ä��� ����
                out_bt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
                out_bt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                
                JLabel bglb = new JLabel(bg_img);
                bglb.setBounds(0,0,265,118);

                add(myinfo);
                add(namelb);
                add(out_bt);
                add(add_bt);
                add(atm_bt);
                add(bglb);

                setSize(265,118);
                setVisible(true);
        }
}