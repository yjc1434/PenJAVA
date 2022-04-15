import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Vector;
import javax.swing.*;

public class Draw extends JPanel {
	JFrame frame = null;
	colorSelector cs = new colorSelector();
	static DrawPanel draw_p;
	GuiPanel gui_p = new GuiPanel();
	WritePanel wt_p = new WritePanel();
	Vector<Point> vPoint = new Vector<Point>(); // 곡선의 x,y좌표가 들어감
	Vector<Integer> vWidth = new Vector<Integer>();// 펜 굵기가 들어감
	Vector<Color> vcol = new Vector<Color>();// 펜의 색이 들어감
	Vector<Point> BackupP = new Vector<Point>(); // 백업
	Vector<Integer> BackupW = new Vector<Integer>(); // 백업
	Vector<Color> BackupC = new Vector<Color>(); // 백업

	static JButton savebt;
	static BufferedImage bi;

	Color col = Color.BLACK;
	Color bcol = col;
	int drawWidth = 3; // 펜 굵기 변수
	boolean isEdited = false;
	boolean isEraser = false;
	int sDrawWidth = drawWidth;

	static void BI(){
		bi = new BufferedImage(draw_p.getWidth(), draw_p.getHeight(), BufferedImage.TYPE_INT_RGB);
		draw_p.printAll(bi.getGraphics());
		System.out.println("BufferedImage 저장완료");
	}

	public Draw() {
		draw_p = new DrawPanel();
		setLayout(null);
		add(gui_p);
		add(draw_p);
		add(wt_p);
		draw_p.setFocusable(true);
		draw_p.requestFocus();
		setSize(748, 778);
	}

	public void clear() {
		vPoint.clear();
		vWidth.clear();
		vcol.clear();
		draw_p.repaint();
		isEraser = false;
		isEdited = true;
		drawWidth = 3;
		sDrawWidth = drawWidth;
		draw_p.setFocusable(true);
		draw_p.requestFocus();
	}
	// 그림판의 맨위의 번튼이나 텍스트 필드 등 GUI패널
	class GuiPanel extends JPanel {
		ImageIcon gui_img = new ImageIcon("image/그림판 도구판.png");
		ImageIcon pencil_img = new ImageIcon("image/pencil.png");
		ImageIcon rollpencil_img = new ImageIcon("image/rollpencil.png");
		ImageIcon easer_img = new ImageIcon("image/easer.png");
		ImageIcon rolleaser_img = new ImageIcon("image/rolleaser.png");
		ImageIcon delete_img = new ImageIcon("image/delete.png");
		ImageIcon rolldelete_img = new ImageIcon("image/rolldelete.png");
		ImageIcon undo_img = new ImageIcon("image/undo.png");
		ImageIcon rollundo_img = new ImageIcon("image/rollundo.png");
		ImageIcon reundo_img = new ImageIcon("image/reundo.png");
		ImageIcon rollreundo_img = new ImageIcon("image/rollreundo.png");
		ImageIcon lb_img = new ImageIcon("image/widthlb.png");
		ImageIcon red_img = new ImageIcon("image/red.png");
		ImageIcon rollred_img = new ImageIcon("image/rollred.png");
		ImageIcon green_img = new ImageIcon("image/green.png");
		ImageIcon rollgreen_img = new ImageIcon("image/rollgreen.png");
		ImageIcon blue_img = new ImageIcon("image/blue.png");
		ImageIcon rollblue_img = new ImageIcon("image/rollblue.png");
		ImageIcon black_img = new ImageIcon("image/black.png");
		ImageIcon rollblack_img = new ImageIcon("image/rollblack.png");
		ImageIcon color_img = new ImageIcon("image/color.png");
		ImageIcon rollcolor_img = new ImageIcon("image/rollcolor.png");
		
		public GuiPanel() {
			setBounds(0, 0, 748, 50);
			setLayout(null);
			setBackground(Color.LIGHT_GRAY);
			JLabel guilb = new JLabel(gui_img);
			JButton penbt = new JButton(pencil_img);
			JButton eraserbt = new JButton(easer_img);
			JButton clearbt = new JButton("전체 지우기");
			JButton colorbt = new JButton(color_img);
			JButton redbt = new JButton(red_img);
			JButton greenbt = new JButton(green_img);
			JButton bluebt = new JButton(blue_img);
			JButton blackbt = new JButton(black_img);
			JLabel widthlb = new JLabel(lb_img);
			JButton newbt = new JButton(delete_img);
			JButton undobt = new JButton(undo_img);
			JButton reundobt = new JButton(reundo_img);
			Vector<Integer> items = new Vector<Integer>();
			JComboBox<Integer> widthcb = new JComboBox<Integer>(items);

			undobt.setBorderPainted(false);       //외곽선 제거
			undobt.setFocusPainted(false);       //내용 영역 채우기 안함
			undobt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			reundobt.setBorderPainted(false);       //외곽선 제거
			reundobt.setFocusPainted(false);       //내용 영역 채우기 안함
			reundobt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			penbt.setBorderPainted(false);       //외곽선 제거
			penbt.setRolloverIcon(rollpencil_img);
			penbt.setFocusPainted(false);       //내용 영역 채우기 안함
			penbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			eraserbt.setBorderPainted(false);       //외곽선 제거
			eraserbt.setRolloverIcon(rolleaser_img);
			eraserbt.setFocusPainted(false);       //내용 영역 채우기 안함
			eraserbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			newbt.setBorderPainted(false);       //외곽선 제거
			newbt.setRolloverIcon(rolldelete_img);
			newbt.setFocusPainted(false);       //내용 영역 채우기 안함
			newbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			undobt.setBorderPainted(false);       //외곽선 제거
			undobt.setRolloverIcon(rollundo_img);
			undobt.setFocusPainted(false);       //내용 영역 채우기 안함
			undobt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			reundobt.setBorderPainted(false);       //외곽선 제거
			reundobt.setRolloverIcon(rollreundo_img);
			reundobt.setFocusPainted(false);       //내용 영역 채우기 안함
			reundobt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			redbt.setBorderPainted(false);       //외곽선 제거
			redbt.setRolloverIcon(rollred_img);
			redbt.setFocusPainted(false);       //내용 영역 채우기 안함
			redbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			greenbt.setBorderPainted(false);       //외곽선 제거
			greenbt.setRolloverIcon(rollgreen_img);
			greenbt.setFocusPainted(false);       //내용 영역 채우기 안함
			greenbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			bluebt.setBorderPainted(false);       //외곽선 제거
			bluebt.setRolloverIcon(rollblue_img);
			bluebt.setFocusPainted(false);       //내용 영역 채우기 안함
			bluebt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			blackbt.setBorderPainted(false);       //외곽선 제거
			blackbt.setRolloverIcon(rollblack_img);
			blackbt.setFocusPainted(false);       //내용 영역 채우기 안함
			blackbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			colorbt.setBorderPainted(false);       //외곽선 제거
			colorbt.setRolloverIcon(rollcolor_img);
			colorbt.setFocusPainted(false);       //내용 영역 채우기 안함
			colorbt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함

			//////위치 크기
			guilb.setBounds(0, 0, 748, 50);
			newbt.setBounds(0, 0, 54, 50);
			undobt.setBounds(60, 0, 54, 50);
			reundobt.setBounds(120, 0, 54, 50);
			penbt.setBounds(180, 0, 54, 50);
			eraserbt.setBounds(240, 0, 54, 50);
			widthlb.setBounds(300, 0, 67, 50);
			widthcb.setBounds(367, 15, 60, 23);
			redbt.setBounds(448, 0, 54, 50);
			greenbt.setBounds(508, 0, 54,50);
			bluebt.setBounds(568, 0, 54, 50);
			blackbt.setBounds(628, 0, 54, 50);
			colorbt.setBounds(688, 0 , 54 ,50);

			for (int i = 1; i < 5; i++) // 콤보박스에 들어갈 items 생성
				items.add(i * 2 - 1); // (1,3,5,7)

			//////////////////// 버튼 색상 설정 /////////////////////
			redbt.setBackground(Color.RED);
			redbt.setForeground(Color.WHITE);
			greenbt.setBackground(Color.GREEN);
			greenbt.setForeground(Color.WHITE);
			bluebt.setBackground(Color.BLUE);
			bluebt.setForeground(Color.WHITE);
			blackbt.setBackground(Color.BLACK);
			blackbt.setForeground(Color.WHITE);
			///////////////////////////////////////////////////////

			widthcb.setEditable(true); // 드랍다운리스트 -> 콤보박스 수정
			widthcb.setSelectedIndex(1); // 기본값 설정 -> 3

			widthcb.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() { // JComboBox는 본래 Editor가 아니기 때문에
				// EditorComponent로 변환해준 뒤
				// KeyListener 사용함.
				@Override
				public void keyReleased(KeyEvent e) {
					try { // JComboBox에 입력값이 Int로 변환되지 않는 값이 입력될 수 있기 때문에 Try-Catch문을 사용.
						if (!widthcb.getEditor().getItem().equals("")) {
							drawWidth = (int) widthcb.getEditor().getItem();
							sDrawWidth = drawWidth;
						} else {
							sDrawWidth = -1;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "숫자를 입력해주세요", "에러", JOptionPane.WARNING_MESSAGE);
						widthcb.getEditor().setItem(drawWidth);
					}
				}
			});

			widthcb.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					try {
						drawWidth = (int) e.getItem();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
			});

			clearbt.addActionListener(new ActionListener() {// draw_p패널을 전체를 지운다
				@Override
				public void actionPerformed(ActionEvent e) {
					vPoint.clear();
					vWidth.clear();
					vcol.clear();
					draw_p.repaint();
					isEraser = false;
					isEdited = true;
					draw_p.setFocusable(true);
					draw_p.requestFocus();
				}
			});

			penbt.addActionListener(new ActionListener() { // 연필로 돌아올때
				@Override
				public void actionPerformed(ActionEvent e) {
					col = bcol;
					isEraser = false;
				}
			});

			eraserbt.addActionListener(new ActionListener() {// 지우개 배경색과 같은 하얀색팬을 이용해서 지워지게 만듬
				@Override
				public void actionPerformed(ActionEvent e) {
					bcol = col;
					col = Color.WHITE;
					isEraser = true;
				}
			});

			colorbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					col = cs.showDialog(frame, col);
					if(col == null)
						col = bcol;
					isEraser = false;
				}
			});

			redbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					col = Color.RED;
					bcol = col;
					isEraser = false;
				}
			});

			greenbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					col = Color.GREEN;
					bcol = col;
					isEraser = false;
				}
			});

			bluebt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					col = Color.BLUE;
					bcol = col;
					isEraser = false;
				}
			});

			blackbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					col = Color.BLACK;
					bcol = col;
					isEraser = false;
				}
			});

			newbt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vPoint.clear();
					vWidth.clear();
					vcol.clear();
					draw_p.repaint();
					isEraser = false;
					isEdited = true;
					draw_p.setFocusable(true);
					draw_p.requestFocus();
				}

			});

			undobt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int cnt = vPoint.size(); // 전체 벡터 사이즈 저장
					int ni = 0; // 마지막null이 몇번째인지 저장

					for (int i = 0; i < vPoint.size(); i++) { // 먼저 마지막 null
						if (vPoint.get(i) == null) {
							ni = i; // 마지막 null위치를 변수에 저장
						}
					}
					for (int i = ni; i < cnt; i++) { // 마지막null위치에서부터 사이즈 크기까지
						vPoint.remove(ni); // 벡터는 삭제하면 뒤에께 앞으로 오니 그 자리에서 계속 삭제함
						vWidth.remove(ni);
						vcol.remove(ni);
					}
					cnt = 0;
					ni = 0;
					draw_p.repaint();
				}

			});
			reundobt.addActionListener(new ActionListener() {
				int ncnt = 0; // 삭제한후 원래 벡터의ㅏ null 갯수
				int bncnt = 0; // 삭제하기전 백업 벡터의 null 갯수
				int n = 0; // 원래 벡터의 끝을 수를 저장할 변수
				int in = 0; // 백업 벡터의 불러올 선의 끝을 저장할 변수

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < vPoint.size(); i++) {
						n++;
						if (vPoint.get(i) == null) {
							ncnt++;
						}
					}
					for (int i = 0; i < BackupP.size(); i++) { // 불러올선의 그다음선 전을 알기위함
						in++;
						if (BackupP.get(i) == null) {
							bncnt++;
							if (ncnt + 2 == bncnt) { // 그다음 선null을 알면 그 null의 -1을 하면 이전선끝까지이다
								break;
							}
						}
					}
					for (int i = n; i < in - 1; i++) {// in이 불러올선 그다음 선의null까지 이니 -1해줌..
						vPoint.add(BackupP.get(i));
						vWidth.add(BackupW.get(i));
						vcol.add(BackupC.get(i));
					}
					draw_p.repaint();
					n = 0;
					ncnt = 0;
					in = 0;
					bncnt = 0;
				}
			});

			add(newbt);
			add(undobt);
			add(reundobt);
			add(penbt);
			add(eraserbt);
			add(widthlb);
			add(widthcb);
			add(redbt);
			add(greenbt);
			add(bluebt);
			add(blackbt);
			add(colorbt);
			add(guilb);
		}
		public void PaintComponent(Graphics g) {
			super.paintComponent(g);
			Image img = gui_img.getImage();
			g.drawImage(img,0,0,getWidth(), getHeight(), this);
		}
	}

	// 그림판에 그려지게될 패널
	class DrawPanel extends JPanel {

		private int x, y;

		public DrawPanel() {
			setLayout(null);
			setBounds(0, 50, 748, 520);
			setBackground(Color.WHITE);
			////////////////////////// 마우스 커서를 없애기 위한 작업 ///////////////////////////
			Toolkit tk = Toolkit.getDefaultToolkit();
			Cursor csr = tk.createCustomCursor(tk.createImage(""), new Point(), null);
			setCursor(csr);
			////////////////////////// 마우스 커서를 없애기 위한 작업 ///////////////////////////

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
					repaint();
				}
			});

			addMouseListener(new MouseAdapter() { // 처음 눌렀을때 첫 스타트 지점(null)과 첫 좌표를 받음
				@Override
				public void mousePressed(MouseEvent e) {
					if (sDrawWidth <= 0) {
						JOptionPane.showMessageDialog(null, "0이상의 숫자를 입력해주세요", "에러", JOptionPane.WARNING_MESSAGE);
					} else {
						vPoint.add(null);
						vPoint.add(e.getPoint());
						vWidth.add(null);
						vWidth.add(drawWidth);
						vcol.add(null);
						vcol.add(col);
						BackupP.clear();
						BackupW.clear();
						BackupC.clear();
						for (int i = 0; i < vPoint.size(); i++) {
							BackupP.add(vPoint.get(i));
							BackupW.add(vWidth.get(i));
							BackupC.add(vcol.get(i));
						}
						isEdited = true;
						draw_p.setFocusable(true);
						draw_p.requestFocus();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) { // 마우스가 컴포넌트를 벗어났을때 마우스에 따라다니는걸 없앰
					x = -100;
					y = -100;
					repaint();
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {// 드래그가 되는 중에 계속해서 좌표를 저장하고 repaint를 호출
				@Override
				public void mouseDragged(MouseEvent e) {
					vPoint.add(e.getPoint());
					vWidth.add(drawWidth);
					vcol.add(col);
					BackupP.add(e.getPoint());
					BackupW.add(drawWidth);
					BackupC.add(col);
					x = e.getX();
					y = e.getY();
					repaint();
				}
			});
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (int i = 1; i < vPoint.size(); i++) {
				if ((vPoint.get(i - 1) == null) || (vPoint.get(i) == null)) { // null이 있을경우 마우스를 때고 다음 선으로 넘어가는 것이라
					// if문으로 잡아줌
					continue;
				} else {
					Graphics2D g2 = (Graphics2D) g;
					// 일반적인Graphics를 사용하면 펜굵기나 색을 설정할수 없어 Grapchics2D사용
					// 기존의 graphics변수를 Graphics2D로 변환후 Graphics2D에 초기화
					// 수행하기 위하여 Graphics2D 클래스를 객체화함
					g.setColor(vcol.get(i - 1));
					g2.setStroke(new BasicStroke(vWidth.get(i - 1), BasicStroke.CAP_ROUND, 0)); // 펜 굵기를 설정
					g.drawLine((int) vPoint.get(i - 1).getX(), (int) vPoint.get(i - 1).getY(),
							(int) vPoint.get(i).getX(), (int) vPoint.get(i).getY());
					// 백터의 null이후의 선을 그림
					g2.setStroke(new BasicStroke(drawWidth, BasicStroke.CAP_ROUND, 0)); // 펜 굵기를 설정
				}
			}
			if (!isEraser) {
				g.setColor(col);
				g.fillOval(x - (drawWidth / 2), y - (drawWidth / 2), drawWidth, drawWidth);
			}
			if (isEraser) {
				g.setColor(Color.BLACK);
				g.fillRect(x - (drawWidth / 2), y - (drawWidth / 2), drawWidth, drawWidth);
				g.setColor(Color.WHITE);
				g.fillRect((x - (drawWidth / 2)) + 1, (y - (drawWidth / 2)) + 1, drawWidth - 2, drawWidth - 2);
			}
		}
	}
	static class WritePanel extends JPanel{
		static JTextField titletf;
		public WritePanel() {
			setLayout(null);
			setBounds(0, 570, 748, 50);
			
			ImageIcon gui2_img = new ImageIcon("image/그림판 도구판2.png");
			JLabel gui2lb = new JLabel(gui2_img);
			gui2lb.setBounds(0, 0, 748, 50);
			
			ImageIcon save_img = new ImageIcon("image/save.png");
			ImageIcon rollsave_img = new ImageIcon("image/rollsave.png");
			savebt = new JButton(save_img);
			savebt.setRolloverIcon(rollsave_img);
			savebt.setBorderPainted(false);       //외곽선 제거
			savebt.setFocusPainted(false);       //내용 영역 채우기 안함
			savebt.setContentAreaFilled(false);    //선택 되었을때 테두리 사용안함
			savebt.setBounds(695, 0, 53, 50);

			titletf = new JTextField(100);
			titletf.setBounds(68, 7, 631, 30);
			add(savebt);
			add(titletf);
			add(gui2lb);
			setBackground(Color.LIGHT_GRAY);
		}
	}
}