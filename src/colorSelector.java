import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class colorSelector extends JDialog{
	Thumnail p;
	JButton ok,cancle;
	JScrollBar scrollBar1,scrollBar2,scrollBar3;
	JLabel label1,label2,label3,label4,label5,label6,label7;
	static Color color;
	static int red,green,blue;
	
	public colorSelector() {
		super();
	}
	
	public colorSelector(JFrame f,Color c) {
		super(f,"색상 선택",true);

		setLayout(null);
		setResizable(false);
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
		
		p = new Thumnail();
		p.setBorder(new LineBorder(new Color(0, 0, 0)));
		p.setBounds(340, 30, 50, 50);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		label1 = new JLabel(Integer.toString(red));
		label1.setBounds(310, 15, 50, 10);
		label2 = new JLabel(Integer.toString(green));
		label2.setBounds(310, 40, 50, 10);
		label3= new JLabel(Integer.toString(blue));
		label3.setBounds(310, 65, 50, 10);
		label4 = new JLabel("Red");
		label4.setBounds(10, 15, 50, 10);
		label5 = new JLabel("Green");
		label5.setBounds(10, 40, 50, 10);
		label6 = new JLabel("Blue");
		label6.setBounds(10, 65, 50, 10);
		label7 = new JLabel("미리보기");
		label7.setBounds(340, 10, 60, 20);
		
		ok = new JButton("확인");
		ok.setBounds(205, 85,90,20);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				color = new Color(red,green,blue);
				dispose();
			}
			
		});
		
		cancle = new JButton("취소");
		cancle.setBounds(300, 85,90,20);
		cancle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				red = c.getRed();
				green = c.getGreen();
				blue = c.getBlue();
				dispose();
			}
			
		});

		scrollBar1 = new JScrollBar(0,red,1,0,256);
		scrollBar1.setBounds(50, 10, 255, 20);
		scrollBar1.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				red = scrollBar1.getValue();
				label1.setText(Integer.toString(red));
				p.repaint();
			}
		});
		
		scrollBar2 = new JScrollBar(0,green,1,0,256);
		scrollBar2.setBounds(50, 35, 255, 20);
		scrollBar2.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				green = scrollBar2.getValue();
				label2.setText(Integer.toString(green));
				p.repaint();
			}
		});
		
		scrollBar3 = new JScrollBar(0,blue,1,0,256);
		scrollBar3.setBounds(50, 60, 255, 20);
		scrollBar3.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				blue = scrollBar3.getValue();
				label3.setText(Integer.toString(blue));
				p.repaint();
			}
		});

		add(scrollBar1);
		add(scrollBar2);
		add(scrollBar3);
		add(p);
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		add(label5);
		add(label6);
		add(label7);
		add(ok);
		add(cancle);
		
		setSize(420,150);
		setLocationRelativeTo(f);
		setVisible(true);
	}
	
	Color showDialog(JFrame f,Color c) {
		new colorSelector(f,c);
		return color;
	}
	
	class Thumnail extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(red,green,blue));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
}