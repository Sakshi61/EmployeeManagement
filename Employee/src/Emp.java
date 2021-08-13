import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class Emp {

	private JFrame frame;
	private JTextField txtEmpName;
	private JTextField txtEmpAge;
	private JTextField txtDepID;
	private JTextField txtDepName;
	private JTable table;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Emp window = new Emp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Emp() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField textField;

	 public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/employee", "root","");
	        }
	        catch (ClassNotFoundException ex) 
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex) 
	        {
	        	   ex.printStackTrace();
	        }

	    }
	
	
	  public void table_load()
	    {
	    	try 
	    	{
		    pst = con.prepareStatement("select * from empinfo");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		} 
	    	catch (SQLException e) 
	    	 {
	    		e.printStackTrace();
		  } 
	    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 739, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(227, 55, 96, -22);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" Emplyoee Department Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(221, 19, 322, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Emplyoee information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 55, 339, 331);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel(" Emp Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 49, 74, 19);
		panel.add(lblNewLabel_2);
		
		txtEmpName = new JTextField();
		txtEmpName.setBounds(116, 49, 185, 20);
		panel.add(txtEmpName);
		txtEmpName.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel(" Emp Age");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(10, 119, 74, 19);
		panel.add(lblNewLabel_2_1);
		
		txtEmpAge = new JTextField();
		txtEmpAge.setColumns(10);
		txtEmpAge.setBounds(116, 119, 185, 20);
		panel.add(txtEmpAge);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Dep ID");
		lblNewLabel_2_1_1.setBounds(10, 181, 57, 19);
		panel.add(lblNewLabel_2_1_1);
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtDepID = new JTextField();
		txtDepID.setBounds(116, 181, 183, 20);
		panel.add(txtDepID);
		txtDepID.setColumns(10);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Dep Name");
		lblNewLabel_2_1_1_1.setBounds(10, 259, 81, 19);
		panel.add(lblNewLabel_2_1_1_1);
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtDepName = new JTextField();
		txtDepName.setBounds(116, 259, 183, 20);
		panel.add(txtDepName);
		txtDepName.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(20, 396, 329, 43);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Enter ID");
		lblNewLabel_3.setBounds(10, 11, 70, 21);
		panel_1.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(121, 11, 86, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String id = textField.getText();
				try {
				pst = con.prepareStatement("select EmpName,EmpAge,DepId,DepName from empinfo where DepId=? ");
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
				if(rs.next()==true) {
					String name = rs.getString(1);
					String Age = rs.getString(2);
					String did = rs.getString(3);
					String dname = rs.getString(4);
					
					txtEmpName.setText(name);
					txtEmpAge.setText(Age);
					txtDepID.setText(did);
					txtDepName.setText(dname);
				} 
				} 
		    	catch (SQLException ae) 
		    	 {
		    		ae.printStackTrace();
			  
				}
				
			}
		});
		
		JButton btnSave = new JButton("SAVE");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 12));
	    btnSave.addActionListener(new ActionListener() 
	    {
	    	
			public void actionPerformed(ActionEvent e) 
			{			
				String EmpName,EmpAge,DepID,DepName;
				
				EmpName = txtEmpName.getText();
				EmpAge = txtEmpAge.getText();
				DepID = txtDepID.getText();
				DepName = txtDepName.getText();
							
				 try {
					pst = con.prepareStatement("insert into empinfo(EmpName,EmpAge,DepId,DepName)values(?,?,?,?)");
					pst.setString(1, EmpName);
					pst.setString(2, EmpAge);
					pst.setString(3, DepID);
					pst.setString(4, DepName);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					table_load();
						           
					txtEmpName.setText("");
					txtEmpAge.setText("");
					txtDepID.setText("");
					txtDepName.setText("");
					txtEmpName.requestFocus();

					
				   }

				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
			};
	    });
		btnSave.setBounds(10, 450, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdit.addActionListener(new ActionListener() 
	    {
	    	
			public void actionPerformed(ActionEvent e) 
			{			
				String EmpName,EmpAge,DepID,DepName;
				
				EmpName = txtEmpName.getText();
				EmpAge = txtEmpAge.getText();
				DepID = txtDepID.getText();
				DepName = txtDepName.getText();
							
				 try {
					pst = con.prepareStatement("update empinfo set EmpName=?, EmpAge=?, DepID=?, DepName=? where id=?");
					pst.setString(1, EmpName);
					pst.setString(2, EmpAge);
					pst.setString(3, DepID);
					pst.setString(4, DepName);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!!!!");
					table_load();
						           
					txtEmpName.setText("");
					txtEmpAge.setText("");
					txtDepID.setText("");
					txtDepName.setText("");
					txtEmpName.requestFocus();

				   }

				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
			};
	    });
		btnEdit.setBounds(136, 450, 89, 23);
		frame.getContentPane().add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.addActionListener(new ActionListener() 
	    {
			public void actionPerformed(ActionEvent e)
			{
				String DepID;
		
				DepID = textField.getText();			
		 
			 try {
				pst = con.prepareStatement("delete from empinfo where DepID =?");
				pst.setString(1, DepID);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
				table_load();
					           
				txtEmpName.setText("");
				txtEmpAge.setText("");
				txtDepID.setText("");
				txtDepName.setText("");
				txtEmpName.requestFocus();	
			   }

			catch (SQLException e1) 
		        {
								
			e1.printStackTrace();
			}
			
		}
	    });
		btnDelete.setBounds(260, 450, 89, 23);
		frame.getContentPane().add(btnDelete);
		
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(388, 55, 339, 331);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.addActionListener(new ActionListener() 
	    {
		public void actionPerformed(ActionEvent e)
		{
			txtEmpName.setText("");
			txtEmpAge.setText("");
			txtDepID.setText("");
			txtDepName.setText("");
		}
	    });
		btnClear.setBounds(423, 418, 89, 47);
		frame.getContentPane().add(btnClear);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.addActionListener(new ActionListener() 
	    {
		public void actionPerformed(ActionEvent e)
		{
			String id;
			
			id = textField.getText();			
			 
				 try {
					pst = con.prepareStatement("update empinfo set EmpName=?, EmpAge=?, DepID=?, DepName=? where DepId=?");
					pst.setString(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record UPDATED!!!!!");
					table_load();
						           
					txtEmpName.setText("");
					txtEmpAge.setText("");
					txtDepID.setText("");
					txtDepName.setText("");
						
				   }

				catch (SQLException e1) 
			        {
									
				e1.printStackTrace();
				}
				
		}
	    });
		btnUpdate.setBounds(584, 420, 89, 43);
		frame.getContentPane().add(btnUpdate);
		
		

	}
}
