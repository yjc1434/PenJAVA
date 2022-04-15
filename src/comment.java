import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;

public class comment extends JDialog{
    JTextField textField;
    JScrollPane jScrollPane;
    JPanel commentPanel,textPaenl;
    ImageIcon comimg = new ImageIcon("image/���۹�ư1.png");
    ImageIcon rollcomimg = new ImageIcon("image/���۹�ư2.png");
    String userID;
    static Vector<String> name = new Vector<>(), id = new Vector<>(), time = new Vector<>()
            , comment = new Vector<>(), number = new Vector<>();
    Vector <Comments> coVe = new Vector<>();
    String Message = new String();
    String _data = new String();
    int count;
    public comment(JFrame frame, Boolean modal, String userID, int count){
        super(frame,"���",modal);
        setLayout(null);
        setSize(500, 500);
        this.setBackground(new Color(78,88,107));
        this.userID = userID; //Dialog ���� ���ڿ� user�� ID�� ���� -> ���� ����� �ۼ��� ����̶�� ������ �����ϰ� ��ư�� ��� ��
        this.count = count;

        CommentIn(Pen_Post.imageName.get(count));
        setComment();
        commentsRefresh();

        jScrollPane = new JScrollPane(commentPanel);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(0,0,500,430);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(20);   //���콺 ��ũ�� �ӵ�
        add(jScrollPane);

        textPaenl = new JPanel();
        textPaenl.setBounds(0, 430, 500, 50);
        textPaenl.setLayout(null);
        textPaenl.setBackground(new Color(78,88,107));
        textField = new JTextField();
        textField.setBounds(0, 0, 355, 25);
        textField.setColumns(10);

        // textPaenl �� �߰� ��ư �߰�
        JButton button = new JButton(comimg);
        button.setBorderPainted(false);       //�ܰ��� ����
        button.setFocusPainted(false);       //���� ���� ä��� ����
        button.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
        button.setRolloverIcon(rollcomimg);
        button.setBounds(360, 0, 97, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("")) {
                    System.out.println(textField.getText());
                    CommentOut(Pen_Post.imageName.get(count).trim(), textField.getText());
                    System.out.println("��� ����");

                    remove(jScrollPane);

                    CommentIn(Pen_Post.imageName.get(count));
                    setComment();
                    commentsRefresh();
                    commentPanel.setBackground(new Color(78,88,107));
                    jScrollPane = new JScrollPane(commentPanel);
                    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    jScrollPane.setBounds(0,0,500,430);
                    jScrollPane.getVerticalScrollBar().setUnitIncrement(20);   //���콺 ��ũ�� �ӵ�
                    add(jScrollPane);
                    jScrollPane.requestFocus();
                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                    textField.setText("");
                }
            }
        });
        textPaenl.add(textField);
        textPaenl.add(button);
        add(textPaenl);


        setResizable(false);
        setLocationRelativeTo(null);//������ ���ѵ� �������
    }

    void setComment(){
        System.out.println("����");
        coVe = new Vector<>();
        for(int i = 0; i < name.size(); i++) {
            System.out.println("���ư�");
            System.out.println(name.get(i) + id.get(i) + time.get(i) + comment.get(i) + number.get(i));
            coVe.add(new Comments(name.get(i), id.get(i), time.get(i), comment.get(i), number.get(i)));
        }
    }

    void commentsRefresh() {
        System.out.println("����");
        commentPanel = new JPanel();
        commentPanel.setSize(500, 500);
        commentPanel.setBackground(new Color(78,88,107));
        if(coVe.size()>3) {
            commentPanel.setLayout(new GridLayout(coVe.size(),1,0,5));
        }
        else {

            commentPanel.setLayout(new GridLayout(3,1,0,5));
        }
        System.out.println(coVe.size());
        for (int i =0; i < coVe.size(); i++) {

            commentPanel.add(coVe.get(i));
        }
    }
    void deleteScrollPane(){
        this.remove(jScrollPane);
    }
    void addScrollPane(){
        this.add(jScrollPane);
    }
    class Comments extends JPanel{
        public Comments(String name,String ID, String time, String comment, String number) {
            setLayout(new GridLayout(4,1));
            setBackground(new Color(78,88,107));

            JLabel Label = new JLabel(name + "(" + ID + ")   " + time);
            JLabel commLabel = new JLabel(comment);
            JLabel delete = new JLabel(" ");
            JLabel cut = new JLabel("-------------------------------------------------------------------------------------------------------------------------");
            Label.setForeground(Color.WHITE);
            delete.setForeground(Color.WHITE);
            commLabel.setForeground(Color.WHITE);
            cut.setForeground(Color.GRAY);

            Label.setFont(new Font("���������� Bold", Font.PLAIN, 15));
            delete.setFont(new Font("���������� Bold", Font.PLAIN, 15));
            commLabel.setFont(new Font("���������� Bold", Font.PLAIN, 20));

            delete.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    if(ID.equals(Info_Myinfo.idlb.getText())) {
                        int result = JOptionPane.showConfirmDialog(null, "����� �����Ͻðڽ��ϱ�?", "��� ����", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            System.out.println(number);
                            CommentDelete(number);

                            deleteScrollPane();

                            CommentIn(Pen_Post.imageName.get(count));
                            setComment();
                            commentsRefresh();

                            jScrollPane = new JScrollPane(commentPanel);
                            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                            jScrollPane.setBounds(0,0,500,430);
                            jScrollPane.getVerticalScrollBar().setUnitIncrement(20);   //���콺 ��ũ�� �ӵ�
                            addScrollPane();
                            jScrollPane.requestFocus();
                            jScrollPane.getVerticalScrollBar().setValue(50);
                            jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                        }
                    }

                }
            });
            add(Label);
            add(commLabel);
            if(ID.equals(Info_Myinfo.idlb.getText())){
                delete.setText("                                                                                                                ����");
            }
            add(delete);
            add(cut);
            setSize(300,15);
        }
    }

    void CommentDelete(String number){
        try{
            Message = "!commentDelete " + number.trim();
            System.out.println(Message);
            Main.soc.getOutputStream().write(Message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void CommentOut(String imagePath,String comment){
        try{
            Message = "!commentOut "+ imagePath + " " + Info_Myinfo.idlb.getText() + " "
                    + Info_Myinfo.namelb.getText()+ " " + comment;
            System.out.println(Message);
            Main.soc.getOutputStream().write(Message.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void CommentIn(String imagePath){
        name = new Vector<>();
        id = new Vector<>();
        time = new Vector<>();
        comment = new Vector<>();
        number = new Vector<>();
        try{
            System.out.println("��� ����");
            Message = "!commentIn " + imagePath;
            Main.soc.getOutputStream().write(Message.getBytes());

            while(true){
                String str;
                byte[] buffer = new byte[4096];
                Main.soc.getInputStream().read(buffer);
                _data = new String(buffer);
                System.out.println(_data);
                if(_data.startsWith("!yes")){
                    buffer = new byte[4096];
                    Main.soc.getInputStream().read(buffer);
                    _data = new String(buffer);

                    str = _data;
                    str.trim(); // �˰� ����~
                    Vector<String> vecString = new Vector<String>(); // 0 = �̸�, 1 = ID, 2 = ��¥, 3 = �ð�, 4 = ��ȣ
                    for (int i = 0; i <5; i++) {
                        vecString.add(str.substring(0, str.indexOf(" ")));
                        str = str.substring(str.indexOf(" ") + 1);
                    }

                    name.add(vecString.get(0));
                    id.add(vecString.get(1));
                    time.add(vecString.get(2)+" "+vecString.get(3));
                    number.add(vecString.get(4));
                    comment.add(str);

                    System.out.println(_data);
                }
                else if(_data.startsWith("!no")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}