

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddressBook1 extends JFrame implements ActionListener {

	private JPanel operationPnl, inputPnl, confirmPnl;
	private JPanel northPnl, eastPnl, southPnl;
	private JButton cancelBtn, insertBtn, updateBtn, deleteBtn;
	private JLabel idLb, nameLb, addLb;
	private JTextField idFld, nameFld, addFld;
	private JTable addTbl;
	private DefaultTableModel addTblM;
	private JScrollPane addScp;
	private MouseHandler mouseHandler;
	private int index;
	private String[] colName = { " ID ", " NAME ", " ADDRESS " };

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private final String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private final String id = "green01";
	private final String pw = "1234";
	private final String driver = "oracle.jdbc.OracleDriver";

	public AddressBook1() throws Exception {
		setTitle("주소록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// confrimPnl
		confirmPnl = new JPanel();
		cancelBtn = new JButton("종료");
		confirmPnl.add(cancelBtn);

		// inputPnl
		inputPnl = new JPanel(new GridLayout(3, 2));

		idLb = new JLabel("id");
		idFld = new JTextField(8);
		nameLb = new JLabel("name");
		nameFld = new JTextField(20);
		addLb = new JLabel("address");
		addFld = new JTextField(100);

		inputPnl.add(idLb);
		inputPnl.add(idFld);
		inputPnl.add(nameLb);
		inputPnl.add(nameFld);
		inputPnl.add(addLb);
		inputPnl.add(addFld);

		// operationPnl
		operationPnl = new JPanel();

		insertBtn = new JButton("등록");
		updateBtn = new JButton("수정");
		deleteBtn = new JButton("삭제");

		operationPnl.add(insertBtn);
		operationPnl.add(updateBtn);
		operationPnl.add(deleteBtn);

		// southPnl
		southPnl = new JPanel(new BorderLayout());

		southPnl.add(confirmPnl, BorderLayout.SOUTH);
		southPnl.add(inputPnl, BorderLayout.CENTER);
		southPnl.add(operationPnl, BorderLayout.NORTH);

		// 테이블 생성
		northPnl = new JPanel(new BorderLayout());

		addTbl = new JTable();
		addTblM = new DefaultTableModel(colName, 0);
		addScp = new JScrollPane(addTbl);
		addTbl.setModel(addTblM);

		northPnl.add(addScp, BorderLayout.CENTER);

		// eastPnl
		eastPnl = new JPanel(new GridLayout(2, 1));
		eastPnl.add(northPnl);
		eastPnl.add(southPnl);

		// 이벤트
		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		mouseHandler = new MouseHandler();
		addTbl.addMouseListener(mouseHandler);

		this.getContentPane().add(eastPnl, BorderLayout.CENTER);

		this.setSize(430, 330);
		this.setVisible(true);
		DbConnection();
	}

	public static void main(String[] args) throws Exception {
		new AddressBook1();

	}

	public void DbConnection() throws Exception {
		Class.forName(driver);
		con = DriverManager.getConnection(url, id, pw);
		setTblData();

	}

	public void setTblData() throws Exception {
		String query = "select * from addressbook ";
		pstmt = con.prepareStatement(query);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			String[] data = { rs.getString(1), rs.getString(2), rs.getString(3) };
			addTblM.addRow(data);
		}

	}

	public void setIdFld(boolean flag) {
		idFld.setEditable(flag);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;

	}

	public void clearFld() throws Exception {
		this.idFld.setText("");
		this.nameFld.setText("");
		this.addFld.setText("");

		setIndex(-1);
		setIdFld(true);

	}

	public class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {

			setIndex(addTbl.getSelectedRow());

			idFld.setText(addTbl.getValueAt(getIndex(), 0).toString());
			nameFld.setText(addTbl.getValueAt(getIndex(), 1).toString());
			addFld.setText(addTbl.getValueAt(getIndex(), 2).toString());
			setIdFld(false);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == insertBtn) {
			insert();
		} else if (src == updateBtn) {
			if (getIndex() != -1) {

				update();
			}
		} else if (src == deleteBtn) {
			if (getIndex() != -1) {
				delete();

			}
		} else if (src == cancelBtn) {
			System.exit(0);
		}
	}

	private void insert() {
		try {
			pstmt = con.prepareStatement("insert into addressbook values(?,?,?)");
			String data[] = { idFld.getText(), nameFld.getText(), addFld.getText() };

			pstmt.setString(1, data[0]);
			pstmt.setString(2, data[1]);
			pstmt.setString(3, data[2]);

			addTblM.addRow(data);

			pstmt.executeUpdate();

			clearFld();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void update() {

		try {
			pstmt = con.prepareStatement("update addressbook set name=?,address=? where id=?");
			pstmt.setString(1, nameFld.getText());
			pstmt.setString(2, addFld.getText());
			pstmt.setString(3, idFld.getText());

			addTbl.setValueAt(nameFld.getText(), getIndex(), 1);
			addTbl.setValueAt(addFld.getText(), getIndex(), 2);
			pstmt.executeUpdate();

			clearFld();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete() {
		try {
			pstmt = con.prepareStatement("delete from ADDRESSBOOK where ID=?");
			pstmt.setString(1, idFld.getText());

			pstmt.executeUpdate();
			// con.commit();

			addTblM.removeRow(getIndex());

			clearFld();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
