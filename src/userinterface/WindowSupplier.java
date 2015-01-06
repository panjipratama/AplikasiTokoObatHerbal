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

import object.Supplier;
import object.User;
import system.*;
import userinterface.listener.ActionListen;

import java.awt.SystemColor;

public class WindowSupplier extends JFrame {
	private Core core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;
	private JTextField tfNama, tfIDSupplier;
	private JLabel lbNama, lbIDSupplier;
	private Vector<Supplier> Supplier = new Vector<Supplier>();
	public WindowSupplier(Core core) {
		super("Maintenance Suppliers");
		getContentPane().setBackground(Color.GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;
		
		setResizable(false);
		setSize(750, 360);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		
		ResultSet rs = Operator.getListSupplier(core.getConnection());
		try {
			while (rs.next()) {
				Supplier.add(new Supplier(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getListSupplier(core.getConnection())));
		Operator.disableTableEdit(tbl);
		getContentPane().setLayout(null);
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(10, 10, getWidth()-270, 300);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);

		JLabel logosupplier = new JLabel();
		logosupplier.setIcon(new ImageIcon(WindowLogin.class.getResource("/Image/supplier.png")));
		
		lbNama = new JLabel("Nama");
		lbIDSupplier = new JLabel("ID Supplier");
		
		lbIDSupplier.setBounds(514, 155, 80, 25);
		lbNama.setBounds(514, 185, 80, 25);
		logosupplier.setBounds(550, 9, 128, 135);
		
		tfIDSupplier = new JTextField();
		tfNama = new JTextField();
		
		tfIDSupplier.setBounds(599, 155, 120, 25);
		tfNama.setBounds(599, 185, 120, 25);
		
		JButton buttonTambah = new JButton("Tambah");
		JButton buttonDelete = new JButton("Delete");
		
		buttonTambah.setBounds(630, 232, 80, 25);
		buttonTambah.setForeground(new Color(255, 69, 0));
		buttonTambah.setBackground(SystemColor.scrollbar);
		buttonTambah.addActionListener(new ActionListen(core, this,tbl,
				buttonTambah, ActionListen.TAMBAH_SUPPLIER));
		buttonDelete.setBounds(530, 232, 80, 25);
		buttonDelete.setForeground(new Color(255, 69, 0));
		buttonDelete.setBackground(SystemColor.scrollbar);
		buttonDelete.addActionListener(new ActionListen(core, this,tbl,
				buttonDelete, ActionListen.HAPUS_SUPPLIER));
		
		
		getContentPane().add(lbIDSupplier);
		getContentPane().add(tfIDSupplier);
		getContentPane().add(lbNama);
		getContentPane().add(tfNama);
		getContentPane().add(logosupplier);
		getContentPane().add(buttonTambah);
		getContentPane().add(buttonDelete);
		getContentPane().add(pan);
	}
	
	public Vector<Supplier> getListSupplier() {
		return Supplier;
	}
	
	public Supplier getSelectedSupplier() {
		return Supplier.get(tbl.getSelectedRow());
	}

	public void submitToDB() {
		if (Operator.tambahSupplier(getSupplier(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data Supplier telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfIDSupplier.getText(),tfNama.getText()});

		tfIDSupplier.setText("");
		tfNama.setText("");
	}

	public void resetForm() {
		tfIDSupplier.setText("");
		tfNama.setText("");
		
		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}

	public Supplier getSupplier() {
		return new Supplier(tfIDSupplier.getText(),tfNama.getText());
	}
	
}
