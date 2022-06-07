

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

public class AddressBook2 extends JFrame implements ActionListener{
	
	private JPanel northPnl , southPnl, eastPnl, confirmPnl,inputPnl,operationPnl;
	private JButton insertBtn, updateBtn,deleteBtn,cancelBtn;
	private JLabel idLb , nameLb,addLb;
	private JTextField nameFld,idFld,addFld;
	private JTable addTbl;
	private DefaultTableModel addTblM;
	private JScrollPane addScp;
	private  int index;
	private String [] colName = {"id","name","address"};
	private MouseHandler mouseHandler;
	
	private Connection con;
	private PreparedStatement pstmt;
    private ResultSet rs;
    
    private final String url = "jdbc:oracle:thin:@localhost:1521/xe";
    private final String id = "green01";
    private final String pw = "1234";
    private final String driver = "oracle.jdbc.OracleDriver";
    
    
	public AddressBook2() {
		setTitle("주소록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(430,300);
		
		//confrim
		confirmPnl = new JPanel();
		cancelBtn = new JButton("끝");
		confirmPnl.add(cancelBtn);
		
		//input
		inputPnl = new JPanel(new GridLayout(3,2));
		
		idLb = new JLabel("아이디");
		nameLb = new JLabel("이름");
		addLb = new JLabel("주소");
		
		idFld = new JTextField(8);
		nameFld = new JTextField(20);
		addFld = new JTextField(100);
		
		inputPnl.add(idLb);
		inputPnl.add(idFld);
		inputPnl.add(nameLb);
		inputPnl.add(nameFld);
		inputPnl.add(addLb);
		inputPnl.add(addFld);
		
		//operation
		operationPnl = new JPanel();
		
		insertBtn = new JButton("등록");
		updateBtn = new JButton("수정");
		deleteBtn = new JButton("삭제"); 
		
		operationPnl.add(insertBtn);
		operationPnl.add(updateBtn);
		operationPnl.add(deleteBtn);
		
		//southPnl
		
		southPnl = new JPanel(new BorderLayout());
		
		southPnl.add(confirmPnl,BorderLayout.SOUTH);
		southPnl.add(inputPnl,BorderLayout.CENTER);
		southPnl.add(operationPnl,BorderLayout.NORTH);
		
		
		//northPNl
		northPnl = new JPanel(new BorderLayout());
		
		addTbl = new JTable();
		addTblM = new DefaultTableModel(colName,0);
		addScp = new JScrollPane(addTbl);
		addTbl.setModel(addTblM);
		
		northPnl.add(addScp,BorderLayout.CENTER);
		
		//eastPnl
		eastPnl = new JPanel(new GridLayout(2,1));
		
		eastPnl.add(northPnl);
		eastPnl.add(southPnl);
		
		this.getContentPane().add(eastPnl,BorderLayout.CENTER);
		this.setVisible(true);
		DbCon();
		
		//action
		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		mouseHandler = new MouseHandler();
		addTbl.addMouseListener(mouseHandler);
	}
	
	

	public static void main(String[] args) {
		new AddressBook2();
		

	}
	

	
	public void DbCon() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,id,pw);
			setData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setData() {
		try {
			pstmt= con.prepareStatement("select * from addressbook");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String data [ ]= {rs.getString(1),rs.getString(2),rs.getString(3)};
				addTblM.addRow(data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetIndex(int x) {
		this.index = x;
	}
	
	public  int getIndex() {
		return this.index;
	}
	
	public void setIdFld(boolean f) {
		idFld.setEditable(f);
	}
	
	public void clearFld() {
		idFld.setText("");
		nameFld.setText("");
		addFld.setText("");
	}
		
	public class MouseHandler extends MouseAdapter{
		public  void mouseClicked(MouseEvent e) {
			
			SetIndex(addTbl.getSelectedRow());
			
			idFld.setText(addTblM.getValueAt(getIndex(), 0).toString());
			nameFld.setText(addTblM.getValueAt(getIndex(), 1).toString());
			addFld.setText(addTblM.getValueAt(getIndex(), 2).toString());
			
			setIdFld(false);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==insertBtn) {
			insert();
		}else if(e.getSource()==updateBtn) {
			if(getIndex()!=-1) {
				update();
			}
		}else if(e.getSource()==deleteBtn) {
			if(getIndex()!=-1) {
				delete();
			}
		}else if(e.getSource()==cancelBtn) {
			System.exit(0);
		}
		
	}
	
	public void insert() {
		try {
			pstmt = con.prepareStatement("insert into addressbook values(?,?,?)");
			String data[] = {idFld.getText(),nameFld.getText(),addFld.getText()};
			
			pstmt.setString(1, data[0]);
			pstmt.setString(2, data[1]);
			pstmt.setString(3, data[2]);
			
			addTblM.addRow(data);
			pstmt.executeUpdate();			
			
			clearFld();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete() {
		try {
			pstmt = con.prepareStatement("delete from addressbook where id=?");
			pstmt.setString(1, idFld.getText());
			
			addTblM.removeRow(getIndex());
			pstmt.executeUpdate();			
			clearFld();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void update() {
		try {
			pstmt = con.prepareStatement("update addressbook set name=? , address=? where id=?");
			pstmt.setString(1, nameFld.getText());
			pstmt.setString(2,addFld.getText());
			pstmt.setString(3, idFld.getText());
			
			
			addTblM.setValueAt(nameFld.getText(), getIndex(), 1);
			addTblM.setValueAt(addFld.getText(), getIndex(), 2);
			
			pstmt.executeUpdate();			
			clearFld();

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
