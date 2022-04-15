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
    ImageIcon bg_img = new ImageIcon("image/내정보.png");
    ImageIcon fadd_bt_img = new ImageIcon("image/친구추가.png");
    ImageIcon list_bt_img = new ImageIcon("image/요청목록.png");
    ImageIcon out_bt_img = new ImageIcon("image/로그아웃.png");
    static JLabel namelb;
    static JLabel idlb;
        public Friend_add() {
            setLayout(null);
            
            Font f = new Font("나눔스퀘어 Bold", Font.PLAIN, 35);

                AddFriend_Dialog addfriend = new AddFriend_Dialog("친구 요청");
                //// 요청 목록 ////
                atm_bt = new JButton(list_bt_img);
                atm_bt.setBounds(95,80,79,34);
                atm_bt.setBorderPainted(false);       //외곽선 제거
                atm_bt.setFocusPainted(false);       //내용 영역 채우기 안함
                atm_bt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
                atm_bt.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) { 
                        	AcceptFriend_Dialog accept_F = new AcceptFriend_Dialog("수락 대기 목록");
                            if(accept_F.isVisible() == true) {
                                  accept_F.setVisible(false);
                             }
                            else {
                               accept_F.setVisible(true);
                            }
                        }
                });
                //// 친구 추가 ////
                add_bt = new JButton(fadd_bt_img);
                add_bt.setBounds(10,80,79,34);
                add_bt.setBorderPainted(false);       //외곽선 제거
                add_bt.setFocusPainted(false);       //내용 영역 채우기 안함
                add_bt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
                add_bt.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                addfriend.setVisible(true);
                        }
                });
                //// 내 정보 ////
                myinfo = new JButton(info_img);
                myinfo.setBounds(13, 10, 69, 68);
                myinfo.setBorderPainted(false);       //외곽선 제거
                myinfo.setFocusPainted(false);       //내용 영역 채우기 안함
                myinfo.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
                	
                namelb = new JLabel("김병관엘");
                namelb.setFont(f);
                namelb.setForeground(Color.white);
                namelb.setBounds(97,-10,300,100);
                /// 로그 아웃///
                out_bt = new JButton(out_bt_img);
                out_bt.setBounds(180,80,79,35);
                out_bt.setBorderPainted(false);       //외곽선 제거
                out_bt.setFocusPainted(false);       //내용 영역 채우기 안함
                out_bt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
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