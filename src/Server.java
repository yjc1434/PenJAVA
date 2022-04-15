import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class Server{
    Connection con;
    Statement stmt;
    public String server = "localhost"; // MySQL 서버 주소
    public String database = "penjava"; // MySQL DATABASE 이름
    public String user_name = "root"; //  MySQL 서버 아이디
    public String password = "penjava2020!"; // MySQL 서버 비밀번호

    Server(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! <JDBC 오류> Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }
        // 2.연결
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?serverTimezone=Asia/Seoul&useSSL=false &allowPublicKeyRetrieval=true&useSSL=false", user_name, password);
            stmt = con.createStatement();
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(50601);
            System.out.println("Server Ready!");
            while(true) {
                System.out.println("Wait for client connecting...");

                socket = serverSocket.accept();
                System.out.println("New client connecting!");

                Receiver receiver = new Receiver(socket, stmt);
                receiver.start();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Server ser = new Server();
    }
}

class Receiver extends Thread{
    Socket socket;
    ResultSet rs;
    Statement stmt;
    String Message;
    String[] parsedString;
    String query;
    String _data = "";
    byte[] buffer;
    public Receiver(Socket socket,Statement stmt) {
        this.socket = socket;
        this.stmt = stmt;
    }

     synchronized public void run() {
        try {
            while(true) {
                System.out.println("대기중 ....");
                buffer = new byte[4096];

                socket.getInputStream().read(buffer);
                _data = new String(buffer);
                System.out.println(_data);

                if (_data.startsWith("!login")) {
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    String pass = parsedString[2].trim();
                    //Server.users.get(findIndex()).setID(id);
                    query = "SELECT * FROM user where id= '" + id + "';";
                    rs = stmt.executeQuery(query);
                    // 레코드가 있는지 검사
                    if (rs.next()) {
                        // 텍스트필드에 쓴값과 데이터베이스에 있는 패스워드 값을 비교한다.
                        System.out.println(rs.getString("id"));
                        System.out.println(rs.getString("password"));
                        if (pass.equals(rs.getString("password"))) {
                            if(rs.getString("state").trim().equals("0")){
                            System.out.println("비로그인 상태");
                            System.out.println("어서오세요 " + rs.getString("name") + "님!");
                            Message = "!login " + rs.getString("name") + " " + rs.getString("birthday") + " " + rs.getString("gender");
                            socket.getOutputStream().write(Message.getBytes());
                            query = "UPDATE user SET state = '1' WHERE id = '" + id + "';";
                            stmt.executeUpdate(query);
                            }else{
                                System.out.println("\n이미 접속한 계정입니다.\n");
                                socket.getOutputStream().write(("!connected").getBytes());
                            }
                        } else {
                            System.out.println("\n비밀번호가 틀립니다.\n");
                            socket.getOutputStream().write(("!password").getBytes());
                        }
                    } else {
                        System.out.println("\n아이디가 존재하지 않습니다.\n");
                        socket.getOutputStream().write(("!id").getBytes());
                    }
                }
                else if (_data.startsWith("!id")) {
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    query = "SELECT id From user where id= '" + id + "';";
                    rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        System.out.println(rs.getString("id") + "계정 존재");
                        socket.getOutputStream().write(("!yes").getBytes());
                    } else {
                        socket.getOutputStream().write(("!no").getBytes());
                    }
                }
                else if (_data.startsWith("!signup")) {
                    parsedString = _data.split(" ");
                    String name = parsedString[1];
                    String id = parsedString[2];
                    String pw = parsedString[3];
                    String birthday = parsedString[4];
                    String gender = parsedString[5].trim();
                    // TODO : 쿼리로 보내기
                    query = "INSERT INTO user (`name`,`id`,`password`,`birthday`,`gender`,`timepiece`,`state`) " +
                            "VALUES ('" + name + "' , '" + id + "' , '" + pw + "' , '" + birthday + "' , '" + gender + "', '1' , '0');";
                    stmt.executeUpdate(query);
                }
                else if (_data.startsWith("!withdrawal")) {
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    query = "DELETE FROM user WHERE id='" + id.trim() + "';";
                    stmt.executeUpdate(query);
                    query = "DELETE FROM timepiece WHERE id='" + id.trim() + "';";
                    stmt.executeUpdate(query);
                    query = "DELETE FROM comment WHERE id='" + id.trim() + "';";
                    stmt.executeUpdate(query);
                    query = "DELETE FROM addfriend WHERE userid='" + id.trim() + "' AND friendid='" + id.trim() + "';";
                    stmt.executeUpdate(query);

                }
                else if(_data.startsWith("!password")){
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    String pw = parsedString[2].trim();
                    query = "UPDATE user set  password = '" + pw + "' where id = '" + id + "';";
                    stmt.executeUpdate(query);
                }

                else if (_data.startsWith("!modify")) {
                    parsedString = _data.split(" ");
                    String name = parsedString[1].trim();
                    String id = parsedString[2].trim();
                    String pw = parsedString[3].trim();
                    String birthday = parsedString[4].trim();
                    String gender = parsedString[5].trim();
                    query = "UPDATE user set name = '" + name + "', password = '" + pw + "', birthday = '" + birthday + "', gender = '" + gender + "' where id = '" + id + "';";
                    stmt.executeUpdate(query);
                }
                else if(_data.startsWith("!deleteTimepiece")){
                    parsedString = _data.split(" ");
                    String iamgePath = parsedString[1].trim();
                    System.out.println(iamgePath);
                    query = "DELETE FROM timepiece WHERE `image` = '" + iamgePath.trim() + "';";
                    stmt.executeUpdate(query);
                    query = "DELETE FROM comment WHERE `image` = '" + iamgePath.trim() + "';";
                    stmt.executeUpdate(query);
                    System.out.println("성공");
                    //sleep(100);
                }
                else if (_data.startsWith("!timeline")) {
                    System.out.println("타임라인 접속");
                    socket.getOutputStream().write("!success".getBytes());
                    buffer = new byte[4096];
                    socket.getInputStream().read(buffer);
                    _data = new String(buffer);
                    System.out.println(_data);

                    query = "select * from timepiece where " + _data.trim() + "order by time DESC;";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    for(int i = 0; i < 10; i++){
                        if (rs.next()) {
                            socket.getOutputStream().write("!yes ".getBytes());
                            //글
                            Message = rs.getString("name") + " " + rs.getString("time") + " " +
                                    rs.getString("like") + " " + rs.getString("id") + " " +
                                    rs.getString("contents");
                            socket.getOutputStream().write(Message.getBytes());
                            System.out.println(Message);
                            //글의 데이터를 다 보낼때 까지 기다리기

                            sleep(100);

                            Message = rs.getString("image").trim();
                            socket.getOutputStream().write(Message.getBytes());

                            sleep(200);
                        } else {
                            socket.getOutputStream().write("!no ".getBytes());
                            System.out.println("!no 보냄");
                            System.out.println("게시글 없음");
                            break;
                        }
                        if(i==9){
                            socket.getOutputStream().write("!no ".getBytes());
                        }
                    }
                    break;
                }
                else if (_data.startsWith("!imagein")) {
                    parsedString = _data.split(" ");
                    String filename = parsedString[1].trim();
                    //이미지
                    System.out.println("파일이름 : " + filename);
                    Thread.sleep(200);
                    BufferedImage bi = ImageIO.read(new File("server/" + filename));
                    BufferedOutputStream bout = new BufferedOutputStream(socket.getOutputStream());
                    ImageIO.write(bi, "PNG", bout);
                    Thread.sleep(200);
                    bout.flush();
                    System.out.println("전송완료");
                    break;
                }
                else if (_data.startsWith("!imageout")) {
                    String str;
                    String id;
                    str = _data;
                    str.trim(); // 똥값 제거~
                    Vector<String> vecString = new Vector<String>(); // 0 = 구분문, 1 = ID, str = 내용
                    for (int i = 0; i < 2; i++) {
                        vecString.add(str.substring(0, str.indexOf(" ")));
                        str = str.substring(str.indexOf(" ") + 1);
                    }
                    id = vecString.get(1);
                    System.out.println(str+"1");
                    // 타임피스 카운트 저장
                    query = "select * from user where id = '" + id + "';";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    int timepiece = Integer.parseInt(rs.getString("timepiece"));
                    String imgPath = id + "(" + timepiece + ").PNG";
                    String name = rs.getString("name");
                    // 타임피스 저장
                    query = "INSERT INTO timepiece(`time`,`name`,`id`,`image`,`contents`, `comment`,`like` )" +
                            "VALUES(now(),'"+ name + "','" + id + "','" + imgPath + "','" + str.trim() + "', 0, 0);";
                    System.out.println(query);
                    stmt.executeUpdate(query);
                    // 타임피스 카운트 증가
                    query = "UPDATE user set timepiece = '" + (timepiece + 1) + "' where id = '" + id + "';";
                    System.out.println(query);
                    stmt.executeUpdate(query);
                    //이미지 저장
                    BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                    BufferedImage image = ImageIO.read(ImageIO.createImageInputStream(bis));
                    ImageIO.write(image, "PNG", new File("server/" + id + "(" + timepiece + ").PNG"));
                    buffer = new byte[4096];
                    socket.getInputStream().read(buffer);
                    socket.getOutputStream().write("!성공!".getBytes());
                    System.out.println("이미지 보내기 성공");
                }
                else if (_data.startsWith("!friend")) {
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    Vector<String> friendId = new Vector<>();
                    query = "select friendid from friend where userid = '" + id + "';";
                    rs = stmt.executeQuery(query);
                    while (true) {
                        if (rs.next()) {
                            friendId.add(rs.getString("friendid").trim());
                        }
                        else break;
                    }
                    for(int i =0; i<friendId.size(); i++){
                        query = "select * from user where id = '" + friendId.get(i) + "';";
                        rs = stmt.executeQuery(query);
                        rs.next();
                        socket.getOutputStream().write("!yes".getBytes());
                        sleep(100);
                        Message = friendId.get(i).trim() + " " + rs.getString("name");
                        socket.getOutputStream().write(Message.getBytes());
                        System.out.println(Message);
                        sleep(100);
                    }
                    socket.getOutputStream().write("!no".getBytes());
                }
                else if (_data.startsWith("!commentIn")) {
                    System.out.println("commentIn");
                    parsedString = _data.split(" ");
                    String image = parsedString[1].trim();
                    query = "select * from comment where image = '"+ image +"' order by time ASC";
                    rs = stmt.executeQuery(query);
                    while(true){
                        if(rs.next()){
                            socket.getOutputStream().write("!yes ".getBytes());
                            sleep(100);
                            Message = rs.getString("name") + " " + rs.getString("id") + " "
                                    + rs.getString("time") + " " + rs.getString("number") + " "
                                    + rs.getString("comment");
                            System.out.println("Message");
                            socket.getOutputStream().write(Message.getBytes());
                            sleep(100);
                        }else{
                            socket.getOutputStream().write("!no ".getBytes());
                            break;
                        }
                    }
                }
                else if(_data.startsWith("!commentOut")) {
                    String str;
                    str = _data;
                    str.trim(); // 똥값 제거~
                    Vector<String> vecString = new Vector<String>(); // 0 = 구분문, 1 = 경로. 2 = ID, 3 = 이름, str = comment
                    for (int i = 0; i < 4; i++) {
                        vecString.add(str.substring(0, str.indexOf(" ")));
                        str = str.substring(str.indexOf(" ") + 1);
                    }
                    parsedString = _data.split(" ");
                    String image = vecString.get(1).trim();
                    String id = vecString.get(2).trim();
                    String name = vecString.get(3).trim();
                    String comment = str.trim();
                    query = "select * from timepiece where image = '"+image+"';";
                    System.out.println(query);
                    rs = stmt.executeQuery(query);
                    rs.next();
                    int count = Integer.parseInt(rs.getString("comment")) + 1;
                    String number = image + "("+ count +")";

                    query = "INSERT INTO comment(`number`,`time`,`image`,`id`,`name`,`comment`) VALUES "+
                            "('"+number+"' , now() , '"+image+"' , '"+id+"' , '" + name+ "' , '" + comment.trim() +"');";
                    System.out.println(query);
                    stmt.executeUpdate(query);
                    query = "UPDATE timepiece set comment = '" + count + "' where image = '" + image + "';";
                    stmt.executeUpdate(query);
                    System.out.println(query);
                }
                else if(_data.startsWith("!commentDelete")){
                    parsedString = _data.split(" ");
                    String number = parsedString[1].trim();
                    query = "DELETE FROM comment WHERE `number` = '" + number + "';";
                    stmt.executeUpdate(query);
                    System.out.println(number);
                }
                else if(_data.startsWith("!islike")){
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    String imagePath= parsedString[2].trim();
                    String ImageId = imagePath+id;
                    System.out.println(imagePath);
                    query = "select * from timepiece where image = '" + imagePath + "';";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    int count = Integer.parseInt(rs.getString("like"));
                    query = "SELECT * FROM `like` where id = '" + ImageId + "';";
                    rs = stmt.executeQuery(query);
                    sleep(100);
                    if(rs.next()){
                        System.out.println("이미 좋아요 누름");
                        socket.getOutputStream().write("!yes".getBytes());
                        count--;
                    }
                    else{
                        System.out.println("좋아요 없음");
                        socket.getOutputStream().write("!no".getBytes());
                        count ++;
                    }
                }
                else if(_data.startsWith("!like")){
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    String imagePath= parsedString[2].trim();
                    String ImageId = imagePath+id;
                    System.out.println(imagePath);
                    query = "select * from timepiece where image = '" + imagePath + "';";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    int count = Integer.parseInt(rs.getString("like"));
                    query = "SELECT * FROM `like` where id = '" + ImageId + "';";
                    rs = stmt.executeQuery(query);
                    if(rs.next()){
                        System.out.println("이미 좋아요 누름");
                        query = "DELETE FROM `like` WHERE id = '" + ImageId + "';";
                        stmt.executeUpdate(query);
                        socket.getOutputStream().write("!yes".getBytes());
                        count--;
                    }
                    else{
                        System.out.println("좋아요 없음");
                        query = "INSERT INTO `like`(`id`)VALUES('" + ImageId + "');";
                        stmt.executeUpdate(query);
                        socket.getOutputStream().write("!no".getBytes());
                        count ++;
                    }
                    query = "UPDATE timepiece SET `like` = '"+ count +"' WHERE `image` = '"+ imagePath +"';";
                    stmt.executeUpdate(query);
                } else if(_data.startsWith("!addFriend")){
                    parsedString = _data.split(" ");
                    String userId = parsedString[1].trim();
                    String friendId= parsedString[2].trim();
                    System.out.println(userId + friendId);
                    query = "select * from user where id ='" + friendId+"';";
                    rs = stmt.executeQuery(query);
                    if(rs.next()){
                        query = "select * from friend where userid = '"+ userId + "' and friendid = '"+ friendId +"'";
                        rs = stmt.executeQuery(query);
                        if(rs.next()){
                            System.out.println("이미 친구입니다.");
                            socket.getOutputStream().write("!no".getBytes());
                        } else{
                            System.out.println("친구가 아닙니다.");
                            query = "select * from addfriend where userid = '" + friendId + "' and friendid = '" + userId + "';";
                            rs = stmt.executeQuery(query);
                            if(rs.next()){
                                System.out.println("이미 친구신청 했습니다.");
                                socket.getOutputStream().write("!already".getBytes());
                            }else{
                                query = "INSERT INTO addfriend (`userid`,`friendid`)VALUES('" + friendId + "','" + userId + "');";
                                stmt.executeUpdate(query);
                                socket.getOutputStream().write("!yes".getBytes());
                            }
                        }
                    }
                    else{
                        System.out.println("존재하지 않는 계정입니다.");
                        socket.getOutputStream().write("!empty".getBytes());
                    }
                } else if(_data.startsWith("!acceptfriend")){
                    parsedString = _data.split(" ");
                    String userId = parsedString[1].trim();
                    System.out.println(userId);
                    String query = "Select * from addfriend where userid = '" + userId + "';";
                    rs = stmt.executeQuery(query);
                    if(rs.next()){
                        int count = 1;
                        Message = "!yes " + rs.getString("friendid");

                        while(true) {
                            if(rs.next()){
                                count++;
                                Message = Message + " " + rs.getString("friendid");
                            }
                            else break;
                        }
                        socket.getOutputStream().write(Integer.toString(count).getBytes());
                        sleep(100);
                        socket.getOutputStream().write(Message.getBytes());
                        sleep(100);
                    } else{
                        socket.getOutputStream().write(Integer.toString(0).getBytes());
                        sleep(100);
                        socket.getOutputStream().write("!no".getBytes());
                    }
                } else if(_data.startsWith("!exe")){
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    query = "select * from user where id = '" + id + "';";
                    rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        socket.getOutputStream().write(rs.getString("name").getBytes());
                        System.out.println(rs.getString("name"));
                    }

                } else if(_data.startsWith("!accept")) {
                    parsedString = _data.split(" ");
                    String userId = parsedString[1].trim();
                    String friendId = parsedString[2].trim();
                    try {
                        query = "DELETE FROM addfriend where userid = '" + userId + "' and friendid = '" + friendId + "';";
                        stmt.executeUpdate(query);
                        query = "INSERT INTO friend (`userid`,`friendid`) VALUES('" + userId + "', '" + friendId + "');";
                        stmt.executeUpdate(query);
                        query = "INSERT INTO friend (`userid`,`friendid`) VALUES('" + friendId + "', '" + userId + "');";
                        stmt.executeUpdate(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println(query);
                } else if(_data.startsWith("!reject")) {
                    parsedString = _data.split(" ");
                    String userId = parsedString[1].trim();
                    String friendId = parsedString[2].trim();
                    try {
                        query = "DELETE FROM addfriend where userid = '" + userId + "' and friendid = '" + friendId + "';";
                        stmt.executeUpdate(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if(_data.startsWith("!search")) {
                    int mode = 0;
                    String re;
                    if(_data.startsWith("!searchPW")) {
                        mode = 1;
                    }
                    String str = _data;
                    Vector <String> vecString = new Vector<>();
                    _data = new String(buffer);
                    for (int i = 0; i < 2 +(1*mode); i++) { // 0 = 구분문, 1 = 항목1, 2 = 항목2, _data = 항목3
                        vecString.add(_data.substring(0, _data.indexOf(" ")));
                        _data = _data.substring(_data.indexOf(" ") + 1);
                    }
                    if(mode == 0) {
                        query = "select * from user where name = '"+ vecString.get(1) +"' and birthday = '"+ _data +"'";
                    }
                    else {
                        query = "select * from user where name = '"+ vecString.get(1) +"' and id = '" + vecString.get(2) +"' and birthday = '" + _data + "'";
                    }
                    rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        if(mode ==0) {
                            re = rs.getString("id");
                        }
                        else {
                            re = rs.getString("password");
                        }
                        socket.getOutputStream().write(re.getBytes());
                    }
                    else {
                        socket.getOutputStream().write("!fail".getBytes());
                    }
                } else if(_data.startsWith("!Success")) {
                    String id = _data.substring(_data.indexOf(" ") + 1);
                    System.out.println(id + " 접속완료!!!");
                } else if(_data.startsWith("!exit")){
                    parsedString = _data.split(" ");
                    String userId = parsedString[1].trim();
                    System.out.println("접속 종료" + userId);
                    query = "UPDATE user SET state = '0' WHERE id = '" + userId + "';";
                    stmt.executeUpdate(query);
                }
            }
        } catch(IOException e) {
            //e.printStackTrace();
        } catch(InterruptedException e) {
            //e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
        } finally {
            System.out.println("통신 종료");
        }
    }
}