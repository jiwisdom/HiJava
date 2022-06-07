

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VendingMachine extends JFrame implements ActionListener {
	private JPanel getPnl, changePnl, moneyPnl,selectPnl, drinkPnl, VendingMachinePnl;
	private JButton cokeBtn, milkBtn, pocariBtn,changeBtn;
	private JLabel moneyLb, CLb, MLb, PLb, drinkLb;
	private JTextField moneyFld, changeFld;

	public VendingMachine() {
		setTitle("자판기~~");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// getPnl
		getPnl = new JPanel();
		drinkLb = new JLabel();
		getPnl.add(drinkLb);

		// changePnl
		changePnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		changeBtn = new JButton("잔돈");
		changeFld = new JTextField(10);
		changePnl.add(changeBtn);
		changePnl.add(changeFld);
		
		

		// moneyPnl
		moneyPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		moneyLb = new JLabel("금액>>");

		moneyFld = new JTextField(10);

		moneyPnl.add(moneyLb);
		moneyPnl.add(moneyFld);

		// selectPnl

		selectPnl = new JPanel();

		cokeBtn = new JButton("콜라 700원");
		milkBtn = new JButton("우유 500원");
		pocariBtn = new JButton("포카리 1200원");

		selectPnl.add(cokeBtn);
		selectPnl.add(milkBtn);
		selectPnl.add(pocariBtn);

		// drinkPnl

		drinkPnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		JPanel cokePnl = new JPanel();
		JPanel milkPnl = new JPanel();
		JPanel pocaPnl = new JPanel();

		ImageIcon coke = new ImageIcon("images/콜라.png");
		ImageIcon milk = new ImageIcon("images/우유.png");
		ImageIcon pocari = new ImageIcon("images/포카리.png");

		CLb = new JLabel();
		MLb = new JLabel();
		PLb = new JLabel();

		Image c = coke.getImage();
		Image updateC = c.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
		ImageIcon updateIconC = new ImageIcon(updateC);

		Image m = milk.getImage();
		Image updateM = m.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
		ImageIcon updateIconM = new ImageIcon(updateM);

		Image p = pocari.getImage();
		Image updateP = p.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
		ImageIcon updateIconP = new ImageIcon(updateP);

		CLb.setIcon(updateIconC);
		MLb.setIcon(updateIconM);
		PLb.setIcon(updateIconP);

		drinkPnl.add(CLb);
		drinkPnl.add(MLb);
		drinkPnl.add(PLb);

		// vendingMachine
		VendingMachinePnl = new JPanel(new GridLayout(5, 1));
		VendingMachinePnl.add(drinkPnl);
		VendingMachinePnl.add(selectPnl);
		VendingMachinePnl.add(moneyPnl);
			
		VendingMachinePnl.add(changePnl);
		VendingMachinePnl.add(getPnl);

		// action
		cokeBtn.addActionListener(this);
		milkBtn.addActionListener(this);
		pocariBtn.addActionListener(this);
		changeBtn.addActionListener(this);

		this.getContentPane().add(VendingMachinePnl, BorderLayout.CENTER);
		this.setSize(350, 500);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new VendingMachine();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cokeBtn) {
			getCoke();

		} else if (e.getSource() == milkBtn) {
			getMilk();

		} else if (e.getSource() == pocariBtn) {
			getPoca();

		}else if(e.getSource()==changeBtn) {
			getChange();
		}

	}

	public void getChange() {
		changeFld.setText(moneyFld.getText());
		moneyFld.setText("");
		
		
		
	}

	private void getCoke() {
		
		
		int mn = Integer.parseInt(moneyFld.getText());

		if (mn < 700) {
			drinkLb.setText("금액이 부족합니다");

		} else if (mn >= 700) {
			drinkLb.setText("");
			int m = mn - 700;
			String money = String.valueOf(m);

			moneyFld.setText(money);
			

			ImageIcon coke = new ImageIcon("images/콜라.png");
			Image c = coke.getImage();
			Image updateC = c.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
			ImageIcon updateIconC = new ImageIcon(updateC);

			drinkLb.setIcon(updateIconC);
			
			
		}
		getPnl.add(drinkLb);
		

	}

	private void getMilk() {

		
		int mn = Integer.parseInt(moneyFld.getText());

		if (mn < 500) {
			drinkLb.setText("금액이 부족합니다");

			

		} else if (mn >= 500) {
			drinkLb.setText("");

			int m = mn - 500;
			String money = String.valueOf(m);

			moneyFld.setText(money);
			

			ImageIcon milk = new ImageIcon("images/우유.png");
			Image mk = milk.getImage();
			Image updateM = mk.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
			ImageIcon updateIconM = new ImageIcon(updateM);

			drinkLb.setIcon(updateIconM);
			

		}
		getPnl.add(drinkLb);

	}

	private void getPoca() {

		

		int mn = Integer.parseInt(moneyFld.getText());

		if (mn < 1200) {
			
			drinkLb.setText("금액이 부족합니다");
			
		} else if (mn >= 1200) {
			drinkLb.setText("");

			int m = mn - 1200;
			String money = String.valueOf(m);

			moneyFld.setText(money);
			
			ImageIcon poca = new ImageIcon("images/포카리.png");
			Image p = poca.getImage();
			Image updateP = p.getScaledInstance(60, 70, Image.SCALE_SMOOTH);
			ImageIcon updateIconP = new ImageIcon(updateP);

			drinkLb.setIcon(updateIconP);
			
		
			
		}
		getPnl.add(drinkLb);

	}

}
