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
	Vector<Point> vPoint = new Vector<Point>(); // ��� x,y��ǥ�� ��
	Vector<Integer> vWidth = new Vector<Integer>();// �� ���Ⱑ ��
	Vector<Color> vcol = new Vector<Color>();// ���� ���� ��
	Vector<Point> BackupP = new Vector<Point>(); // ���
	Vector<Integer> BackupW = new Vector<Integer>(); // ���
	Vector<Color> BackupC = new Vector<Color>(); // ���

	static JButton savebt;
	static BufferedImage bi;

	Color col = Color.BLACK;
	Color bcol = col;
	int drawWidth = 3; // �� ���� ����
	boolean isEdited = false;
	boolean isEraser = false;
	int sDrawWidth = drawWidth;

	static void BI(){
		bi = new BufferedImage(draw_p.getWidth(), draw_p.getHeight(), BufferedImage.TYPE_INT_RGB);
		draw_p.printAll(bi.getGraphics());
		System.out.println("BufferedImage ����Ϸ�");
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
	// �׸����� ������ ��ư�̳� �ؽ�Ʈ �ʵ� �� GUI�г�
	class GuiPanel extends JPanel {
		ImageIcon gui_img = new ImageIcon("image/�׸��� ������.png");
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
			JButton clearbt = new JButton("��ü �����");
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

			undobt.setBorderPainted(false);       //�ܰ��� ����
			undobt.setFocusPainted(false);       //���� ���� ä��� ����
			undobt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			reundobt.setBorderPainted(false);       //�ܰ��� ����
			reundobt.setFocusPainted(false);       //���� ���� ä��� ����
			reundobt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			penbt.setBorderPainted(false);       //�ܰ��� ����
			penbt.setRolloverIcon(rollpencil_img);
			penbt.setFocusPainted(false);       //���� ���� ä��� ����
			penbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			eraserbt.setBorderPainted(false);       //�ܰ��� ����
			eraserbt.setRolloverIcon(rolleaser_img);
			eraserbt.setFocusPainted(false);       //���� ���� ä��� ����
			eraserbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			newbt.setBorderPainted(false);       //�ܰ��� ����
			newbt.setRolloverIcon(rolldelete_img);
			newbt.setFocusPainted(false);       //���� ���� ä��� ����
			newbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			undobt.setBorderPainted(false);       //�ܰ��� ����
			undobt.setRolloverIcon(rollundo_img);
			undobt.setFocusPainted(false);       //���� ���� ä��� ����
			undobt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			reundobt.setBorderPainted(false);       //�ܰ��� ����
			reundobt.setRolloverIcon(rollreundo_img);
			reundobt.setFocusPainted(false);       //���� ���� ä��� ����
			reundobt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			redbt.setBorderPainted(false);       //�ܰ��� ����
			redbt.setRolloverIcon(rollred_img);
			redbt.setFocusPainted(false);       //���� ���� ä��� ����
			redbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			greenbt.setBorderPainted(false);       //�ܰ��� ����
			greenbt.setRolloverIcon(rollgreen_img);
			greenbt.setFocusPainted(false);       //���� ���� ä��� ����
			greenbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			bluebt.setBorderPainted(false);       //�ܰ��� ����
			bluebt.setRolloverIcon(rollblue_img);
			bluebt.setFocusPainted(false);       //���� ���� ä��� ����
			bluebt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			blackbt.setBorderPainted(false);       //�ܰ��� ����
			blackbt.setRolloverIcon(rollblack_img);
			blackbt.setFocusPainted(false);       //���� ���� ä��� ����
			blackbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			colorbt.setBorderPainted(false);       //�ܰ��� ����
			colorbt.setRolloverIcon(rollcolor_img);
			colorbt.setFocusPainted(false);       //���� ���� ä��� ����
			colorbt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������

			//////��ġ ũ��
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

			for (int i = 1; i < 5; i++) // �޺��ڽ��� �� items ����
				items.add(i * 2 - 1); // (1,3,5,7)

			//////////////////// ��ư ���� ���� /////////////////////
			redbt.setBackground(Color.RED);
			redbt.setForeground(Color.WHITE);
			greenbt.setBackground(Color.GREEN);
			greenbt.setForeground(Color.WHITE);
			bluebt.setBackground(Color.BLUE);
			bluebt.setForeground(Color.WHITE);
			blackbt.setBackground(Color.BLACK);
			blackbt.setForeground(Color.WHITE);
			///////////////////////////////////////////////////////

			widthcb.setEditable(true); // ����ٿ��Ʈ -> �޺��ڽ� ����
			widthcb.setSelectedIndex(1); // �⺻�� ���� -> 3

			widthcb.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() { // JComboBox�� ���� Editor�� �ƴϱ� ������
				// EditorComponent�� ��ȯ���� ��
				// KeyListener �����.
				@Override
				public void keyReleased(KeyEvent e) {
					try { // JComboBox�� �Է°��� Int�� ��ȯ���� �ʴ� ���� �Էµ� �� �ֱ� ������ Try-Catch���� ���.
						if (!widthcb.getEditor().getItem().equals("")) {
							drawWidth = (int) widthcb.getEditor().getItem();
							sDrawWidth = drawWidth;
						} else {
							sDrawWidth = -1;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���", "����", JOptionPane.WARNING_MESSAGE);
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

			clearbt.addActionListener(new ActionListener() {// draw_p�г��� ��ü�� �����
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

			penbt.addActionListener(new ActionListener() { // ���ʷ� ���ƿö�
				@Override
				public void actionPerformed(ActionEvent e) {
					col = bcol;
					isEraser = false;
				}
			});

			eraserbt.addActionListener(new ActionListener() {// ���찳 ������ ���� �Ͼ������ �̿��ؼ� �������� ����
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
					int cnt = vPoint.size(); // ��ü ���� ������ ����
					int ni = 0; // ������null�� ���°���� ����

					for (int i = 0; i < vPoint.size(); i++) { // ���� ������ null
						if (vPoint.get(i) == null) {
							ni = i; // ������ null��ġ�� ������ ����
						}
					}
					for (int i = ni; i < cnt; i++) { // ������null��ġ�������� ������ ũ�����
						vPoint.remove(ni); // ���ʹ� �����ϸ� �ڿ��� ������ ���� �� �ڸ����� ��� ������
						vWidth.remove(ni);
						vcol.remove(ni);
					}
					cnt = 0;
					ni = 0;
					draw_p.repaint();
				}

			});
			reundobt.addActionListener(new ActionListener() {
				int ncnt = 0; // �������� ���� �����Ǥ� null ����
				int bncnt = 0; // �����ϱ��� ��� ������ null ����
				int n = 0; // ���� ������ ���� ���� ������ ����
				int in = 0; // ��� ������ �ҷ��� ���� ���� ������ ����

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < vPoint.size(); i++) {
						n++;
						if (vPoint.get(i) == null) {
							ncnt++;
						}
					}
					for (int i = 0; i < BackupP.size(); i++) { // �ҷ��ü��� �״����� ���� �˱�����
						in++;
						if (BackupP.get(i) == null) {
							bncnt++;
							if (ncnt + 2 == bncnt) { // �״��� ��null�� �˸� �� null�� -1�� �ϸ� �������������̴�
								break;
							}
						}
					}
					for (int i = n; i < in - 1; i++) {// in�� �ҷ��ü� �״��� ����null���� �̴� -1����..
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

	// �׸��ǿ� �׷����Ե� �г�
	class DrawPanel extends JPanel {

		private int x, y;

		public DrawPanel() {
			setLayout(null);
			setBounds(0, 50, 748, 520);
			setBackground(Color.WHITE);
			////////////////////////// ���콺 Ŀ���� ���ֱ� ���� �۾� ///////////////////////////
			Toolkit tk = Toolkit.getDefaultToolkit();
			Cursor csr = tk.createCustomCursor(tk.createImage(""), new Point(), null);
			setCursor(csr);
			////////////////////////// ���콺 Ŀ���� ���ֱ� ���� �۾� ///////////////////////////

			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
					repaint();
				}
			});

			addMouseListener(new MouseAdapter() { // ó�� �������� ù ��ŸƮ ����(null)�� ù ��ǥ�� ����
				@Override
				public void mousePressed(MouseEvent e) {
					if (sDrawWidth <= 0) {
						JOptionPane.showMessageDialog(null, "0�̻��� ���ڸ� �Է����ּ���", "����", JOptionPane.WARNING_MESSAGE);
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
				public void mouseExited(MouseEvent e) { // ���콺�� ������Ʈ�� ������� ���콺�� ����ٴϴ°� ����
					x = -100;
					y = -100;
					repaint();
				}
			});

			addMouseMotionListener(new MouseMotionAdapter() {// �巡�װ� �Ǵ� �߿� ����ؼ� ��ǥ�� �����ϰ� repaint�� ȣ��
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
				if ((vPoint.get(i - 1) == null) || (vPoint.get(i) == null)) { // null�� ������� ���콺�� ���� ���� ������ �Ѿ�� ���̶�
					// if������ �����
					continue;
				} else {
					Graphics2D g2 = (Graphics2D) g;
					// �Ϲ�����Graphics�� ����ϸ� �汽�⳪ ���� �����Ҽ� ���� Grapchics2D���
					// ������ graphics������ Graphics2D�� ��ȯ�� Graphics2D�� �ʱ�ȭ
					// �����ϱ� ���Ͽ� Graphics2D Ŭ������ ��üȭ��
					g.setColor(vcol.get(i - 1));
					g2.setStroke(new BasicStroke(vWidth.get(i - 1), BasicStroke.CAP_ROUND, 0)); // �� ���⸦ ����
					g.drawLine((int) vPoint.get(i - 1).getX(), (int) vPoint.get(i - 1).getY(),
							(int) vPoint.get(i).getX(), (int) vPoint.get(i).getY());
					// ������ null������ ���� �׸�
					g2.setStroke(new BasicStroke(drawWidth, BasicStroke.CAP_ROUND, 0)); // �� ���⸦ ����
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
			
			ImageIcon gui2_img = new ImageIcon("image/�׸��� ������2.png");
			JLabel gui2lb = new JLabel(gui2_img);
			gui2lb.setBounds(0, 0, 748, 50);
			
			ImageIcon save_img = new ImageIcon("image/save.png");
			ImageIcon rollsave_img = new ImageIcon("image/rollsave.png");
			savebt = new JButton(save_img);
			savebt.setRolloverIcon(rollsave_img);
			savebt.setBorderPainted(false);       //�ܰ��� ����
			savebt.setFocusPainted(false);       //���� ���� ä��� ����
			savebt.setContentAreaFilled(false);    //���� �Ǿ����� �׵θ� ������
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