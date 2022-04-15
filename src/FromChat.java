import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

public class FromChat extends Thread {
	String ID,friendID;

	FromChat(String ID) {
		this.ID = ID;
	}

	public void run() {
		String str;
		String _data;
		String time;
		String date;
		String friendName = null;
		byte[] buffer;
		boolean my;
		try {
			System.out.println("FromChat ����");
			while (true) {
				buffer = new byte[4096];
				Main.chattingsoc.getInputStream().read(buffer);
				_data = new String(buffer);
				System.out.println("From Chat : " + _data);
				if(_data.startsWith("!message")) {
					str = _data;
					str.trim();
					Vector<String> vecString = new Vector<String>(); // 0 = ���й�, 1 = FROM, 2 = TO, 3 ~ 8 = TIME
					for (int i = 0; i < str.length(); i++) {
						if (i <= 8) {
							vecString.add(str.substring(0, str.indexOf(" ")));
							str = str.substring(str.indexOf(" ") + 1);
						}
					}
					System.out.println("�Ľ��� ���� : " + vecString);
					date = vecString.get(8) + "�� " + parsingTime(vecString.get(4)) + "�� " + vecString.get(5) + "�� " + parsingTime(vecString.get(3)) + "����";
					System.out.println(parsingTime(vecString.get(4)));
					time = vecString.get(6);
					for (int i = 0; i < Pen_Friend.friendId.size(); i++) {
						if (Pen_Friend.friendId.get(i).getText().trim().equals(vecString.get(1).trim())) {
							friendName = Pen_Friend.friendName.get(i).getText().trim();
						}
					}

					if(ID.trim().equals(vecString.get(1).trim())) { //���� ���� �޽��� -> �޴� ���(TO)�� ģ��
						my = true;
						if(findIndex(vecString.get(2).trim()) != -1){
							Main.msg.get(findIndex(vecString.get(2).trim())).getMessenger().add(friendName, str.trim(), date.trim(), time.trim(), my);
						}
					}
					else if (ID.trim().equals(vecString.get(2).trim())) { //���� �޽��� -> ���� ���(FROM)�� ģ��
						my = false;
						if(findIndex(vecString.get(1).trim()) != -1){
							Main.msg.get(findIndex(vecString.get(1).trim())).getMessenger().add(friendName, str.trim(), date.trim(), time.trim(), my);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int findIndex(String id) {
		for (int i = 0; i < Main.msg.size(); i++) {
			if(Main.msg.get(i).getFriendID().trim().equals(id.trim())) { //ģ�� �̸� == vecString.get(1) <FROM �������>
				return i;
			}
		}
		return -1;
	}

	String parsingTime(String message) {
		message = message.trim();
		if (message.equals("SUN"))
			return "��";
		else if (message.equals("Mon"))
			return "��";
		else if (message.equals("Tue"))
			return "ȭ";
		else if (message.equals("Wed"))
			return "��";
		else if (message.equals("Thu"))
			return "��";
		else if (message.equals("Fri"))
			return "��";
		else if (message.equals("Sat"))
			return "��";
			//���� �� �� ����
		else if (message.equals("Jan"))
			return "1";
		else if (message.equals("Fen"))
			return "2";
		else if (message.equals("Mar"))
			return "3";
		else if (message.equals("Apr"))
			return "4";
		else if (message.equals("May"))
			return "5";
		else if (message.equals("Jun"))
			return "6";
		else if (message.equals("Jul"))
			return "7";
		else if (message.equals("Aug"))
			return "8";
		else if (message.equals("Sep"))
			return "9";
		else if (message.equals("Oct"))
			return "10";
		else if (message.equals("Nov"))
			return "11";
		else if (message.equals("Dec"))
			return "12";
		return message;
	}
}