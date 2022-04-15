import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class Pen_Post extends JPanel {
	ImageIcon img_commentbt = new ImageIcon("image/댓글.png");
	ImageIcon img_likebt = new ImageIcon("image/좋아요.png");
	ImageIcon img_likedbt = new ImageIcon("image/좋아요2.png");
	static Vector<JLabel> imagelb = new Vector<>();
	static Vector<String> imageName = new Vector<>();
	static Vector<String> lb = new Vector<>(), contentlb = new Vector<>(), like = new Vector<>(), id = new Vector<>(),
			dates = new Vector<>();
	static Vector<JButton> delete = new Vector<>();
	static Vector<SouthPanel> south = new Vector<>();
	ImageIcon img_icon = new ImageIcon("image/human.png");
	Font f = new Font("나눔스퀘어 Bold", Font.PLAIN, 18);
	Font f2 = new Font("나눔스퀘어", Font.PLAIN, 3);
	JPanel timeline;
	IDPanel north;
	JScrollPane jScrollPane;
	class IDPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image img = img_icon.getImage();
			g.drawImage(img, 10, 10, 45, 45, null); // 이미지를 그린다
			jScrollPane.repaint();
		}
	}

	public Pen_Post() {
		setLayout(null);
		jScrollPane = new JScrollPane(timeline);
		add(jScrollPane);
		setTimepiece();
		setSize(748, 778);
		setVisible(true);
	}

	void setTimepiece() { // 전부 삽입
		remove(jScrollPane);
		timeline = new JPanel();
		timeline.setBackground(Color.LIGHT_GRAY);
		timeline.setLayout(new GridLayout(lb.size(), 1, 10, 10));
		// 전체 타임라인

		for (int i = 0; i < lb.size(); i++) {
			JPanel timepiece = new JPanel();
			timepiece.setLayout(new BorderLayout());
			timepiece.setBackground(Color.WHITE);
			JLabel dummy1, dummy2;
			dummy1 = new JLabel(" ");
			dummy1.setFont(f2);
			dummy2 = new JLabel(" ");
			dummy2.setFont(f2);
			north = new IDPanel();
			north.setBackground(Color.LIGHT_GRAY);
			north.setLayout(new GridLayout(3, 1));
			JLabel lb1 = new JLabel("             " + lb.get(i).trim());
			lb1.setFont(f);
			lb1.setForeground(Color.WHITE);
			north.setBackground(new Color(78, 88, 107));
			north.add(dummy1);
			north.add(lb1);
			north.add(dummy2);
			south.add(new SouthPanel(like.get(i), i, contentlb.get(i).trim(), dates.get(i).trim()));
			timepiece.add(north, BorderLayout.NORTH);
			timepiece.add(imagelb.get(i), BorderLayout.CENTER);
			timepiece.add(south.get(i), BorderLayout.SOUTH);

			timeline.add(timepiece);
		}

		jScrollPane = new JScrollPane(timeline);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setBounds(0, 0, 765, 614);
		jScrollPane.getVerticalScrollBar().setUnitIncrement(20); // 마우스 스크롤 속도
		add(jScrollPane);
	}

	boolean liked(int i) {
		try {
			String Message = "!islike " + Info_Myinfo.idlb.getText() + " " + imageName.get(i);
			System.out.println(Message);
			Main.soc.getOutputStream().write(Message.getBytes());
			byte[] buffer = new byte[4096];
			Main.soc.getInputStream().read(buffer);
			String _data = new String(buffer);
			System.out.println(_data);
			if (_data.startsWith("!yes")) {
				return true;
			} else if (_data.startsWith("!no")) {
				return false;
			}
		} catch (IOException ex) {
			ex.getStackTrace();
		}
		return true;
	}

	class SouthPanel extends JPanel {
		ImageIcon img = new ImageIcon("image/menu2");
		JButton commentbt = new JButton(img_commentbt);
		JButton likebt = new JButton(img_likebt);
		JLabel contentlb, day;
		JLabel likelb = new JLabel("좋아요 갯수 : 0");
		int likeCount = 0;
		boolean like = false;

		SouthPanel(String like, int count, String contents, String date) {
			contentlb = new JLabel("     제목 : " + contents);
			contentlb.setFont(f);
			contentlb.setOpaque(true);
			contentlb.setForeground(Color.WHITE);
			contentlb.setBackground(new Color(78, 88, 107));

			day = new JLabel(date + "        ");
			day.setFont(f);
			day.setOpaque(true);
			day.setForeground(Color.WHITE);
			day.setBackground(new Color(78, 88, 107));
			day.setHorizontalAlignment(JLabel.RIGHT);

			likeCount = Integer.parseInt(like);
			setLayout(new GridLayout(3, 1));
			add(contentlb);
			add(day);
			JPanel sub = new JPanel();
			sub.setLayout(new FlowLayout());
			sub.setBackground(new Color(78, 88, 107));
			add(sub);
			setBackground(new Color(78, 88, 107));
			likelb.setText("좋아요 갯수 : " + String.valueOf(likeCount));
			likelb.setForeground(Color.WHITE);
			likelb.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 20));

			commentbt.setBorderPainted(false);       //외곽선 제거
			commentbt.setFocusPainted(false);       //내용 영역 채우기 안함
			commentbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
			commentbt.setPreferredSize(new Dimension(97, 39));
			commentbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("댓글");
					comment dialog = new comment(null, true, Info_Myinfo.idlb.getText(), count);
					dialog.setVisible(true);
				}
			});
			likebt.setBorderPainted(false);       //외곽선 제거
			likebt.setFocusPainted(false);       //내용 영역 채우기 안함
			likebt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
			likebt.setPreferredSize(new Dimension(107, 39));
			likebt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					likelb.setText("좋아요 갯수 : " + Integer.toString(likeCount));
					System.out.println("좋아요");
					try {
						String Message = "!like " + Info_Myinfo.idlb.getText() + " " + imageName.get(count);
						System.out.println(Message);
						Main.soc.getOutputStream().write(Message.getBytes());
						byte[] buffer = new byte[4096];
						Main.soc.getInputStream().read(buffer);
						String _data = new String(buffer);
						if (_data.startsWith("!yes")) {
							likeCount--;
							likebt.setIcon(img_likebt);
						} else if (_data.startsWith("!no")) {
							likeCount++;
							likebt.setIcon(img_likedbt);
						}
						likelb.setText("좋아요 갯수 : " + String.valueOf(likeCount));
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				}
			});
			/// 더미
			JLabel lb1 = new JLabel("");
			JLabel lb2 = new JLabel("");
			lb1.setPreferredSize(new Dimension(100, 30));
			lb2.setPreferredSize(new Dimension(100, 30));
			sub.add(likebt);
			sub.add(lb1);
			sub.add(likelb);
			sub.add(lb2);
			sub.add(commentbt);
			if (liked(count)) {
				likebt.setIcon(img_likedbt);
			}
			if (id.get(count).equals(Info_Myinfo.idlb.getText().trim()))
				sub.add(delete.get(count));
		}
	}
}