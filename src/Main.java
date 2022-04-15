import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.net.*;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;

class Messenger {
	Pen_Messenger pmsg;
	String userID,friendID;
	
	Messenger(Pen_Messenger msg, String userID, String friendID) {
		this.userID = userID;
		this.friendID = friendID;
		pmsg = msg;
	}
	
	String getUserID() {
		return userID;
	}
	String getFriendID() {
		return friendID;
	}
	
	Pen_Messenger getMessenger() {
		return pmsg;
	}
	
	void openMessenger() {
		pmsg = new Pen_Messenger();
	}

}

public class Main extends JFrame  {
    Draw draw = new Draw();
    Pen_Main pen_m = new Pen_Main();
    Pen_Sign pen_s = new Pen_Sign();
    Pen_Login pen_l = new Pen_Login();

    Pen_Community pen_c = new Pen_Community(draw);
    JFrame mainFrame;
    SearchDialog search;
    static Socket soc, chattingsoc;
    String _data;
    String Message;
    String userID;
    String[] parsedString;

    Vector<String> friendName = new Vector<>();
    static Vector<Messenger> msg = new Vector<>();

    public char gender = 'M';
    

    Main(){
        Container c = getContentPane();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        c.setLayout(null);
        mainFrame = this;

        ChattingConnect();
        connect();

        Pen_Main.sign_bt.addActionListener(new ActionListener(){    //  회원가입
            public void actionPerformed(ActionEvent e) {
                pen_m.setVisible(false);
                pen_s.setVisible(true);
            }
        });
        Pen_Main.login_bt.addActionListener(new ActionListener(){   //  로그인
            public void actionPerformed(ActionEvent e) {
                pen_m.setVisible(false);
                pen_l.setVisible(true);
            }
        });
        Pen_Login.back_bt.addActionListener(new ActionListener(){   //  로그임 뒤로가기
            public void actionPerformed(ActionEvent e) {
                Pen_Login.id_tf.setText("");
                Pen_Login.pw_tf.setText("");
                pen_l.setVisible(false);
                pen_m.setVisible(true);
            }
        });
        Pen_Sign.back_bt.addActionListener(new ActionListener(){    //  회원가입 뒤로가기
            public void actionPerformed(ActionEvent e) {
                Pen_Sign.name_lb.setText("");
                Pen_Sign.id_lb.setText("");
                Pen_Sign.ps_lb.setText("");
                Pen_Sign.birth_lb.setText("");
                Pen_Sign.sname_tf.setText("");
                Pen_Sign.sid_tf.setText("");
                Pen_Sign.sps_tf.setText("");
                Pen_Sign.male_radio.setSelected(true);
                Pen_Sign.female_radio.setSelected(false);
                pen_s.setVisible(false);
                pen_m.setVisible(true);
            }
        });
        //회원정보 수정

        // 성별 라디오버튼 값 받아오기
        Pen_Sign.male_radio.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(Pen_Sign.male_radio.isSelected()) { gender = '남';}
                else if(Pen_Sign.female_radio.isSelected()) { gender = '여';}
            }
        });
        Pen_Sign.female_radio.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(Pen_Sign.male_radio.isSelected()) { gender = '남';}
                else if(Pen_Sign.female_radio.isSelected()) { gender = '여';}
            }
        });
        Pen_Sign.sign_bt.addActionListener(new ActionListener(){    //  회원가입 뒤로가기
            public void actionPerformed(ActionEvent e) {
                if(Pen_Sign.sname_tf.getText().equals("")||Pen_Sign.sid_tf.getText().equals("")||Pen_Sign.sps_tf.getText().equals("")
                        || Objects.requireNonNull(Pen_Sign.year_combo.getSelectedItem()).equals("년")
                        || Objects.requireNonNull(Pen_Sign.month_combo.getSelectedItem()).equals("월")
                        || Objects.requireNonNull(Pen_Sign.date_combo.getSelectedItem()).equals("일")){
                    if (Pen_Sign.sname_tf.getText().equals("")) {
                        System.out.println("이름을 입력하시오!!!");
                        Pen_Sign.name_lb.setText("이름을 입력하시오");
                    } else Pen_Sign.name_lb.setText("");
                    if (Pen_Sign.sid_tf.getText().equals("")) {
                        System.out.println("ID를 입력하시오!!!");
                        Pen_Sign.id_lb.setText("ID를 입력하시오");
                    } else Pen_Sign.id_lb.setText("");
                    if (Pen_Sign.sps_tf.getText().equals("")) {
                        System.out.println("비밀번호를 입력하시오!!!");
                        Pen_Sign.ps_lb.setText("비밀번호를 입력하시오");
                    } else Pen_Sign.ps_lb.setText("");
                    if (Objects.requireNonNull(Pen_Sign.year_combo.getSelectedItem()).equals("년")
                            || Objects.requireNonNull(Pen_Sign.month_combo.getSelectedItem()).equals("월")
                            || Objects.requireNonNull(Pen_Sign.date_combo.getSelectedItem()).equals("일")) {
                        System.out.println("생일을 입력하시오!!!");
                        Pen_Sign.birth_lb.setText("생일을 입력하시오");
                    } else Pen_Sign.birth_lb.setText("");
                } else{
                    Pen_Sign.name_lb.setText("");
                    Pen_Sign.id_lb.setText("");
                    Pen_Sign.ps_lb.setText("");
                    Pen_Sign.birth_lb.setText("");
                    if(IdCheck(Pen_Sign.sid_tf.getText())==1) {
                        SignUp(Pen_Sign.sname_tf.getText(), Pen_Sign.sid_tf.getText(), Pen_Sign.sps_tf.getText(), Pen_Sign.year_combo.getSelectedItem()
                                + "-" + Pen_Sign.month_combo.getSelectedItem() + "-" + Pen_Sign.date_combo.getSelectedItem(), gender);
                        showMessageDialog(null,"회원가입 완료");
                        pen_s.setVisible(false);
                        pen_l.setVisible(true);
                    }
                }
            }
        });
        Pen_Login.login_bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj.equals(Pen_Login.login_bt)){
                    int check = LoingCheck(Pen_Login.id_tf.getText().trim(),String.valueOf(Pen_Login.pw_tf.getPassword()).trim());
                    if (check == 0) {
                        showMessageDialog(null, Info_Myinfo.namelb.getText() + "님 환영합니다");
                        clear();
                        Friend();
                        Pen_Community.pen_f.setFriendId();
                        Timeline();
                        deleteListener();
                        for(int i = 0; i<Pen_Post.imageName.size();i++){
                            Imagein(Pen_Post.imageName.get(i));
                        }
                        connect();
                        Pen_Community.pen_p.setTimepiece();
                        pen_l.setVisible(false);
                        pen_c.setVisible(true);


                        Pen_Community.pen_f.setUserID(userID);
                        try {
                            Message = "!Success "+ userID;
                            soc.getOutputStream().write(Message.getBytes());
                            chattingsoc.getOutputStream().write(Message.getBytes());
                            Friend_add.namelb.setText(Info_Myinfo.namelb.getText().trim());
                            FromChat from = new FromChat(userID);
                            from.start();
                        }
                        catch (IOException ex){
                            ex.printStackTrace();
                        }

                    } else if(check == 1){
                        System.out.println("ERROR : 비밀번호가 틀립니다.");
                        Pen_Login.login_bl.setText("비밀번호가 틀립니다!!!");
                    } else if(check == 2){
                        System.out.println("ERROR : 아이디가 존재하지 않습니다.");
                        Pen_Login.login_bl.setText("아이디가 존재하지 않습니다!!!");
                    } else if(check ==3 ){
                        System.out.println("ERROR : 접속된 아이디 입니다.");
                        Pen_Login.login_bl.setText("접속된 아이디 입니다!!!");
                    }
                }
            }
        });

        Draw.savebt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Main 에서 저장 리스너");
                Imageout();
                clear();
                Friend();
                Pen_Community.pen_f.setFriendId();
                Timeline();
                deleteListener();
                for(int i = 0; i<Pen_Post.imageName.size();i++){
                    Imagein(Pen_Post.imageName.get(i));
                }
                connect();
                try {
                    Message = "!Success "+ userID;
                    soc.getOutputStream().write(Message.getBytes());
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
                Pen_Community.pen_p.setTimepiece();
                Pen_Community.pen_Draw.clear();
            }
        });

        Menu.mainmenu_bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
                Friend();
                Pen_Community.pen_f.setFriendId();
                Timeline();
                deleteListener();
                for(int i = 0; i<Pen_Post.imageName.size();i++){
                    Imagein(Pen_Post.imageName.get(i));
                }
                connect();
                Pen_Community.pen_p.setTimepiece();
                try {
                    Message = "!Success "+ userID;
                    soc.getOutputStream().write(Message.getBytes());
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }

            }
        });
        Menu.diary_bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
                Friend();
                Pen_Community.pen_f.setFriendId();
                MyTimeline();
                deleteListener();
                for(int i = 0; i < Pen_Post.imageName.size(); i++){
                    Imagein(Pen_Post.imageName.get(i));
                }
                connect();
                Pen_Community.pen_p.setTimepiece();
                try {
                    Message = "!Success "+ userID;
                    soc.getOutputStream().write(Message.getBytes());
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        this.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    exit();
                    System.exit(0);
                }
            }
        });

        Pen_Login.find.id.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search = new SearchDialog(mainFrame,0);
            }
        });

        Pen_Login.find.pw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search = new SearchDialog(mainFrame,1);
            }
        });

        Friend_add.out_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    exit();
                    Pen_Login.id_tf.setText("");
                    Pen_Login.pw_tf.setText("");
                    Pen_Login.login_bl.setText("");
                    pen_c.setVisible(false);
                    pen_l.setVisible(true);
                }
            }
        });

        Info_Myinfo.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "회원탈퇴 하시겠습니까?", "회원탈퇴", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {

                        String Message = "!withdrawal "+ Info_Myinfo.idlb.getText().trim();
                        Main.soc.getOutputStream().write(Message.getBytes());
                        exit();
                        Pen_Login.id_tf.setText("");
                        Pen_Login.pw_tf.setText("");
                        Pen_Login.login_bl.setText("");
                        pen_c.setVisible(false);
                        pen_l.setVisible(true);

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        // 그림 그리기 저장 후
        c.add(pen_c);
        c.add(pen_m);
        c.add(pen_s);
        c.add(pen_l);
        setResizable(false);
        setSize(1280,720);
        setLocationRelativeTo(null);//사이즈 정한뒤 해줘야함
        setVisible(true);
    }

    static void connect(){
        String ip = "119.199.192.167";
        try {
            soc = new Socket(ip, 50601);  // 태희컴 접속할 때
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void exit(){
        try {
            Message = "!exit " + Info_Myinfo.idlb.getText().trim();
            soc.getOutputStream().write(Message.getBytes());
            System.out.println("접속 종료 " +Info_Myinfo.idlb.getText().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ChattingConnect(){
        String ip = "119.199.192.167";
        try {
            chattingsoc = new Socket(ip, 50602);  // 태희컴 접속할 때
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void clear(){
        Pen_Post.lb = new Vector<>();
        Pen_Post.contentlb = new Vector<>();
        Pen_Post.imagelb = new Vector<>();
        Pen_Post.like = new Vector<>();
        Pen_Post.imageName = new Vector<>();
        Pen_Post.id = new Vector<>();
        Pen_Post.delete = new Vector<>();
        Pen_Post.south = new Vector<>();
        Pen_Friend.friendId = new Vector<>();
        Pen_Friend.friendName = new Vector<>();
    }

    void deleteListener(){
        ImageIcon img_delbt = new ImageIcon("image/삭제.png");
        for(int i = 0; i<Pen_Post.imageName.size(); i++){
            int count = i;
            JButton delete = new JButton(img_delbt);
            delete.setPreferredSize(new Dimension(83, 39));
            delete.setBorderPainted(false);       //외곽선 제거
            delete.setFocusPainted(false);       //내용 영역 채우기 안함
            delete.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("timepiece 삭제 버튼");
                    int result = JOptionPane.showConfirmDialog(null, "게시글을 삭제하시겠습니까?", "게시글 삭제", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        deleteTimepiece(Pen_Post.imageName.get(count));
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        Menu.mainmenu_bt.doClick();
                    }
                }
            });
            Pen_Post.delete.add(delete);
        }
    }

    void SignUp(String name, String id, String pw, String birthday, char gender){
        try{
            connect();
            Message = "!signup "+ name + " " + id + " " + pw + " " + birthday + " " + gender;
            soc.getOutputStream().write(Message.getBytes());
        } catch (Exception ex){ ex.printStackTrace(); }
    }

    int IdCheck(String id){
        try {
            byte[] buffer = new byte[1024];
            connect();
            Message = "!id " + id;
            soc.getOutputStream().write(Message.getBytes());

            soc.getInputStream().read(buffer);
            _data = new String(buffer);
            if(_data.startsWith("!yes")){
                showMessageDialog(null,id + "는 존재하는 ID 입니다.");
                return 0;
            }
            else if(_data.startsWith("!no")){ return 1; }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return 1;
    }
    int LoingCheck(String id, String pw){
        try {
            Message = "!login " + id + " " + pw;
            soc.getOutputStream().write(Message.getBytes());
            byte[] buffer = new byte[1024];
            soc.getInputStream().read(buffer);
            _data = new String(buffer);
            String[] parsedString;
            if(_data.startsWith("!login")){
                parsedString= _data.split(" ");
                String name = parsedString[1].trim();
                String birthday = parsedString[2].trim();
                String gender = parsedString[3].trim();
                Info_Myinfo.namelb.setText(name);
                Info_Myinfo.idlb.setText(id);
                Info_Myinfo.pwlb.setText(pw);
                Info_Myinfo.birthdaylb.setText(birthday);
                Info_Myinfo.genderlb.setText(gender);
                userID = id; //클라이언트의 ID를 저장해준다.
                return 0;
            }
            else if(_data.startsWith("!password")){ return 1; }
            else if(_data.startsWith("!id")){ return 2; }
            else if(_data.startsWith("!connected")){ return 3; }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return 0;
    }

    void Modify(String name, String id, String pw, String birthday, String gender){
        try{
            Message = "!modify " + name + " " + id + " " + pw + " " + birthday + " " + gender;
            System.out.println(Message);
            soc.getOutputStream().write(Message.getBytes());
            System.out.println("소켓 전송");
        }
        catch(Exception ex) { ex.printStackTrace(); }
    }

    void Imagein(String filename){
        try {
            System.out.println("----------------------\n이미지 받는중...\n"+filename);
            JLabel img;
            connect();
            Message = "!imagein " +filename;
            soc.getOutputStream().write(Message.getBytes());

            BufferedInputStream bis1 = new BufferedInputStream(soc.getInputStream());
            BufferedImage image1 = ImageIO.read(ImageIO.createImageInputStream(bis1));
            ImageIcon imageIcon = new ImageIcon(image1);
            img = new JLabel(imageIcon);

            byte[] buffer = new byte[1024];
            soc.getInputStream().read(buffer);

            Pen_Post.imagelb.add(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Imageout(){
        try {
            Draw.BI();
            String Message = "!imageout "+ Info_Myinfo.idlb.getText().trim() + " " + Draw.WritePanel.titletf.getText();
            soc.getOutputStream().write(Message.getBytes());
            System.out.println(Message);
            //BufferedImage 보내기
            BufferedOutputStream bout = new BufferedOutputStream(soc.getOutputStream());
            if(JOptionPane.showConfirmDialog(null,"내 PC에 저장하시겠습니까?",
                    "저장",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ImageIO.write(Draw.bi,"PNG", new File("save/저장된사진.PNG"));
            }
            ImageIO.write(Draw.bi,"PNG",bout);
            System.out.println("이미지 보냄");
            byte[] buffer = new byte[1024];
            soc.getInputStream().read(buffer);
            _data = new String(buffer);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    void Timeline(){
        try {
            System.out.println("타임라인 접근");
            Message = "!timeline ";
            soc.getOutputStream().write(Message.getBytes());
            byte[] buffer = new byte[4096];
            soc.getInputStream().read(buffer);
            _data = new String(buffer);

            System.out.println(_data);

            String timeline = " id = '" + Info_Myinfo.idlb.getText().trim() + "'";
            for(int i = 0; i < Pen_Friend.friendId.size(); i++){
                String temp = timeline+" or id = '"+Pen_Friend.friendId.get(i).getText()+"' ";
                timeline = temp;
            }
            soc.getOutputStream().write(timeline.getBytes());
            System.out.println(timeline);

            while(true) {
                buffer = new byte[4096];
                soc.getInputStream().read(buffer);
                _data = new String(buffer);

                System.out.println(_data);
                if (_data.startsWith("!yes")) {
                    //글
                    String str;
                    buffer = new byte[4096];
                    soc.getInputStream().read(buffer);
                    _data = new String(buffer);
                    System.out.println(_data);

                    str = _data;
                    str.trim(); // 똥값 제거~
                    Vector<String> vecString = new Vector<String>(); // , 0 = 이름, 1= ID, 2 = 날짜, 3 = 시간, 4 = 좋아요, str = 내용
                    for (int i = 0; i < 5; i++) {
                        vecString.add(str.substring(0, str.indexOf(" ")));
                        str = str.substring(str.indexOf(" ") + 1);
                    }
                    String name  = vecString.get(0);//= parsedString[0].trim();
                    String day = vecString.get(1);;//= parsedString[1].trim();
                    String time = vecString.get(2);;//= parsedString[2].trim();
                    String like = vecString.get(3);;//= parsedString[3].trim();
                    String id = vecString.get(4);;//= parsedString[4].trim();
                    String content = str;//= parsedString[5].trim();

                    System.out.println("----------------------\ndata : " + _data);
                    Pen_Post.lb.add(name);
                    Pen_Post.dates.add(day+" "+time.trim());
                    Pen_Post.contentlb.add(content.trim());
                    Pen_Post.like.add(like);
                    Pen_Post.id.add(id);

                    buffer = new byte[4096];
                    soc.getInputStream().read(buffer);
                    _data = new String(buffer);
                    System.out.println("imagePath : "+_data+"\n----------------------");
                    Pen_Post.imageName.add(_data);

                } else if (_data.startsWith("!no")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void MyTimeline(){
        try {
            System.out.println("타임라인 접근");
            Message = "!timeline ";
            soc.getOutputStream().write(Message.getBytes());
            byte[] buffer = new byte[4096];
            soc.getInputStream().read(buffer);
            _data = new String(buffer);

            System.out.println(_data);

            String timeline = " id = '" + Info_Myinfo.idlb.getText().trim() + "'";
            soc.getOutputStream().write(timeline.getBytes());
            System.out.println(timeline);

            while(true) {
                buffer = new byte[4096];
                soc.getInputStream().read(buffer);
                _data = new String(buffer);

                System.out.println(_data);
                if (_data.startsWith("!yes")) {
                    //글
                    String str;
                    buffer = new byte[4096];
                    soc.getInputStream().read(buffer);
                    _data = new String(buffer);
                    System.out.println(_data);
                    str = _data;
                    str.trim(); // 똥값 제거~
                    Vector<String> vecString = new Vector<String>(); // , 0 = 이름, 1= ID, 2 = 날짜, 3 = 시간, 4 = 좋아요, str = 내용
                    for (int i = 0; i < 5; i++) {
                        vecString.add(str.substring(0, str.indexOf(" ")));
                        str = str.substring(str.indexOf(" ") + 1);
                    }

                    String name  = vecString.get(0);//= parsedString[0].trim();
                    String day = vecString.get(1);;//= parsedString[1].trim();
                    String time = vecString.get(2);;//= parsedString[2].trim();
                    String like = vecString.get(3);;//= parsedString[3].trim();
                    String id = vecString.get(4);;//= parsedString[4].trim();
                    String content = str;//= parsedString[5].trim();

                    System.out.println("----------------------\ndata : " + _data);
                    Pen_Post.lb.add(name);
                    Pen_Post.dates.add(day+" "+time.trim());
                    Pen_Post.contentlb.add(content.trim());
                    Pen_Post.like.add(like);
                    Pen_Post.id.add(id);

                    buffer = new byte[4096];
                    soc.getInputStream().read(buffer);
                    _data = new String(buffer);

                    System.out.println("imagePath : "+_data+"\n----------------------");
                    Pen_Post.imageName.add(_data);

                } else if (_data.startsWith("!no")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Friend(){
        try {
            Message = "!friend " + Info_Myinfo.idlb.getText().trim();
            soc.getOutputStream().write(Message.getBytes());
            while(true) {
                byte[] buffer = new byte[4096];
                soc.getInputStream().read(buffer);
                _data = new String(buffer);
                buffer = new byte[4096];
                System.out.println(_data);
                if (_data.startsWith("!yes")) {
                    //글
                    soc.getInputStream().read(buffer);
                    _data = new String(buffer);
                    parsedString = _data.split(" ");
                    String id = parsedString[0].trim();
                    String name = parsedString[1].trim();
                    Pen_Friend.friendId.add(new JLabel(id.trim()));
                    Pen_Friend.friendName.add(new JLabel(name.trim()));
                    System.out.println("----------------------\n친구 : " + _data + "\n----------------------");
                } else if (_data.startsWith("!no")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("catch 끝");
        }
    }
    void deleteTimepiece(String imagePath){
        try {
            Message = "!deleteTimepiece " + imagePath;
            soc.getOutputStream().write(Message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}