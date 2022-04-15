
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class CUsers {
    String ID;
    Socket socket;
    public CUsers(String ID, Socket socket) {
        this.ID = ID;
        this.socket = socket;
    }
    void setID(String ID){
        this.ID=ID;
    }
    Socket returnSocket() {
        return socket;
    }

    String returnID() {
        return ID;
    }
}

public class ChattingServer {
    static Vector<CUsers> users = new Vector<CUsers>(); // 유저들을 저장하기 위한 벡터

    ChattingServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(50602);
            System.out.println("Server Ready!");
            while (true) {
                System.out.println("Wait for client connecting...");

                socket = serverSocket.accept();
                System.out.println("New client connecting!");

                CReceiver receiver = new CReceiver(socket);

                receiver.start();

                System.out.println("add");
            }
        }
        catch (IOException ex) {
            System.out.println("error");
        }
    }
    public static void main(String[] args) {
        new ChattingServer();
    }
}

class CReceiver extends Thread{
    Socket socket;
    String _data;
    byte[] buffer = new byte[4096];
    CUsers user;
    String[] parsedString;

    public CReceiver(Socket socket) {
        this.socket = socket;
        user = new CUsers(null,socket);
        ChattingServer.users.add(user);
    }

    int findIndex() {
        for (int i = 0; i < ChattingServer.users.size(); i++) {
            if(ChattingServer.users.get(i).equals(user))
                return i;
        }
        return -1;
    }

    public void run() {
        try {
            String str;
            System.out.println("메시지 소켓 접속");
            while(true) {
                System.out.println("대기중 ....");
                buffer = new byte[4096];
                System.out.println(ChattingServer.users);

                socket.getInputStream().read(buffer);
                _data = new String(buffer);
                System.out.println(_data);

                if(_data.startsWith("!Success")){
                    parsedString = _data.split(" ");
                    String id = parsedString[1].trim();
                    ChattingServer.users.get(findIndex()).setID(id);
                    System.out.println(ChattingServer.users.get(findIndex()).returnID()+ "접속 완료");
                }
                else if(_data.startsWith("!message")) {
                    try {
                        str = _data;
                        str.trim();
                        System.out.println("입력됨!\n구문>>" + _data);
                        Vector<String> vecString = new Vector<String>(); // 0 = 구분문, 1 = FROM, 2 = TO, 3 ~ 8 = TIME
                        for (int i = 0; i < 9; i++) { //메시지 파싱
                            vecString.add(str.substring(0, str.indexOf(" ")));
                            str = str.substring(str.indexOf(" ") + 1);
                        }
                        for (int i = 0; i < ChattingServer.users.size(); i++) {
                            if(vecString.get(2).equals("all")) { //전체에게 보내기일때
                                ChattingServer.users.get(i).returnSocket().getOutputStream().write(_data.getBytes());
                            }
                            else { //아닐때
                                if (ChattingServer.users.get(i).returnID().trim().equals(vecString.get(1).trim())) { //귓속말을 보낸 사람
                                    ChattingServer.users.get(i).returnSocket().getOutputStream().write(_data.getBytes());
                                }
                                else if (ChattingServer.users.get(i).returnID().trim().equals(vecString.get(2).trim())) { //귓속말을 받는 사람
                                    ChattingServer.users.get(i).returnSocket().getOutputStream().write(_data.getBytes());
                                }
                            }
                        }
                    }
                    catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                }
            }
        }
        catch (Exception Ex) {
            Ex.getStackTrace();
        }finally {
            System.out.println(ChattingServer.users);
            ChattingServer.users.remove(findIndex());
            System.out.println(ChattingServer.users);
            System.out.println("통신 종료");
        }
    }
}