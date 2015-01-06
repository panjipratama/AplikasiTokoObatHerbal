package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import object.*;
import system.*;
import userinterface.listener.ActionListen;

import java.awt.SystemColor;

public class WindowUsers extends JFrame {
	private Core core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;
	private JCheckBox ckIsAdmin;
	private boolean admin;
	private JTextField tfUsername, tfPassword;
	private JLabel lbUsername, lbPassword, lbAdmin;

	private Vector<User> users = new Vector<User>();
	public WindowUsers(Core core) {
		super("Maintenance Users");
		getContentPane().setBackground(Color.GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;

		setResizable(false);
		setSize(750, 360);
		setLocation((screenSize.width - getWidth()) / 2,(screenSize.height - getHeight()) / 2);
		
		ResultSet rs = Operator.getListUser(core.getConnection());
		try {
			while (rs.next()) {
				users.add(new User(rs.getString(1), rs.getString(2), rs.getBoolean(3)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getListUser(core.getConnection())));
		Operator.disableTableEdit(tbl);
		getContentPane().setLayout(null);
		JPanel pan = new JPanel(null);
		pan.setBackground(Color.WHITE);
		pan.setBounds(10, 10, getWidth()-270, 300);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		
		JLabel logouser = new JLabel();
		logouser.setIcon(new ImageIcon(WindowLogin.class.getResource("/Image/users.png")));
		
		lbUsername = new JLabel("Username");
		lbPassword = new JLabel("Password");
		lbAdmin = new JLabel("Admin");
		
		lbUsername.setBounds(510, 122, 80, 25);
		lbPassword.setBounds(510, 152, 80, 25);
		lbAdmin.setBounds(510, 182, 80, 25);
		logouser.setBounds(550, 9, 128, 108);
		
		tfUsername = new JTextField();
		tfPassword = new JTextField();
		ckIsAdmin = new JCheckBox(" Yes");
		ckIsAdmin.setBackground(Color.GRAY);
		
		tfUsername.setBounds(595, 122, 120, 25);
		tfPassword.setBounds(595, 152, 120, 25);
		ckIsAdmin.setBounds(592, 182, 80, 25);
		
		JButton buttonTambah = new JButton("Tambah");
		JButton buttonDelete = new JButton("Delete");

		buttonTambah.setBounds(630, 232, 80, 25);
		buttonTambah.setForeground(new Color(255, 69, 0));
		buttonTambah.setBackground(SystemColor.scrollbar);
		buttonTambah.addActionListener(new ActionListen(core, this,tbl,
				buttonTambah, ActionListen.TAMBAH_USER));
		buttonDelete.setBounds(530, 232, 80, 25);
		buttonDelete.setForeground(new Color(255, 69, 0));
		buttonDelete.setBackground(SystemColor.scrollbar);
		buttonDelete.addActionListener(new ActionListen(core, this,tbl,
				buttonDelete, ActionListen.HAPUS_USER));
		
		getContentPane().add(lbUsername);
		getContentPane().add(tfUsername);
		getContentPane().add(lbPassword);
		getContentPane().add(tfPassword);
		getContentPane().add(lbAdmin);
		getContentPane().add(ckIsAdmin);
		getContentPane().add(logouser);
		getContentPane().add(buttonTambah);
		getContentPane().add(buttonDelete);
		getContentPane().add(pan);
	}
	
	public Vector<User> getListUser() {
		return users;
	}
	
	public User getSelectedUser() {
		return users.get(tbl.getSelectedRow());
	}

	public void submitToDB() {
		String isadmin;
		if (ckIsAdmin.isSelected() == true)
		{
			isadmin = "true";
		}
		else
		{
			isadmin = "false";
		}
		if (Operator.tambahUser(getUser(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data User telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfUsername.getText(),tfPassword.getText(),isadmin});

		tfUsername.setText("");
		tfPassword.setText("");
		ckIsAdmin.setSelected(false);
	}

	public void resetForm() {
		tfUsername.setText("");
		tfPassword.setText("");
		ckIsAdmin.setSelected(false);
		
		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}

	public User getUser() {
		boolean isadmin;
		if (ckIsAdmin.isSelected() == true)
		{
			isadmin = true;
		}
		else
		{
			isadmin = false;
		}
		return new User(tfUsername.getText(),tfPassword.getText(),isadmin);
	}
}
