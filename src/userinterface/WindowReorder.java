package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import object.Product;
import object.Reorder;
import system.*;
import userinterface.listener.ActionListen;
import userinterface.listener.KeyListen;

import java.awt.SystemColor;

public class WindowReorder extends JFrame {
	private Core core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;
	private JTextField tfID, tfNama, tfStok, tfIDSupplier, tfHarga;
	private JLabel lbID, lbNama, lbStok, lbIDSupplier, lbHarga, lbJudul;
	
	private Vector<Reorder> reorder = new Vector<Reorder>();
	
	public WindowReorder(Core core) {
		super("Data Product Re-Order");
		getContentPane().setBackground(Color.GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;

		setResizable(false);
		setSize(750, 425);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		
		ResultSet rs = Operator.getReorder(core.getConnection());
		try {
			while (rs.next()) {
				reorder.add(new Reorder(rs.getString(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		getContentPane().setLayout(null);
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(10, 10, 480, 375);
		pan.setLayout(new BorderLayout());

		JLabel logosupplier = new JLabel();
		logosupplier.setIcon(new ImageIcon(WindowReorder.class.getResource("/Image/stok.png")));
		
		tfID = new JTextField();
		tfNama = new JTextField();
		tfIDSupplier = new JTextField();
		tfHarga = new JTextField();
		tfStok = new JTextField();
		
		lbID = new JLabel("ID");
		lbNama = new JLabel("Nama");
		lbIDSupplier = new JLabel("ID Supplier");
		lbHarga = new JLabel("Harga");
		lbStok = new JLabel("Re-Stok");
		
		lbID.setBounds(510, 155, 80, 25);
		lbNama.setBounds(510, 185, 80, 25);
		lbIDSupplier.setBounds(510, 215, 80, 25);
		lbHarga.setBounds(510, 245, 80, 25);
		lbStok.setBounds(510, 275, 80, 25);
		logosupplier.setBounds(550, 9, 128, 135);
		
		tfID.setBounds(595, 155, 120, 25);
		tfNama.setBounds(595, 185, 120, 25);
		tfIDSupplier.setBounds(595, 215, 120, 25);
		tfHarga.setBounds(595, 245, 120, 25);
		tfStok.setBounds(595, 275, 120, 25);
		tfStok.addKeyListener(new KeyListen(core, this, tfStok,
				KeyListen.NUMBER_ONLY));
		
		tfID.setEnabled(false);
		tfNama.setEnabled(false);
		tfIDSupplier.setEnabled(false);
		tfHarga.setEnabled(false);
		
		JButton buttonReStok = new JButton("Re-Stok");
		JButton buttonSave = new JButton("Save");
		
		buttonReStok.setBounds(510, 360, 100, 25);
		buttonReStok.setForeground(new Color(255, 69, 0));
		buttonReStok.setBackground(SystemColor.scrollbar);
		buttonSave.setBounds(635, 310, 80, 25);
		buttonSave.setForeground(new Color(255, 69, 0));
		buttonSave.setBackground(SystemColor.scrollbar);
		
		getContentPane().add(lbID);
		getContentPane().add(tfID);
		getContentPane().add(lbNama);
		getContentPane().add(tfNama);
		getContentPane().add(lbIDSupplier);
		getContentPane().add(tfIDSupplier);
		getContentPane().add(lbHarga);
		getContentPane().add(tfHarga);
		getContentPane().add(lbStok);
		getContentPane().add(tfStok);
		getContentPane().add(logosupplier);
		getContentPane().add(buttonReStok);
		getContentPane().add(buttonSave);
		getContentPane().add(pan);
		
		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getReorder(core.getConnection())));
		Operator.disableTableEdit(tbl);
		pan.add(tbl, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(tbl);
		pan.add(scrollPane, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		buttonReStok.addActionListener(new ActionListen(core, this,tbl,
				buttonReStok, ActionListen.RESTOK));
		buttonSave.addActionListener(new ActionListen(core, this,tbl,
				buttonSave, ActionListen.UPDATE_STOK));
	}
	
	public Vector<Reorder> getListReorder() {
		return reorder;
	}

	public Reorder getSelectedReorder() {
		return reorder.get(tbl.getSelectedRow());
	}
	
	public void ReStok(Reorder Re)
	{
		tfID.setText(Re.getId());
		tfNama.setText(Re.getNama());
		tfIDSupplier.setText(Re.getIDSupplier());
		tfHarga.setText("" + Re.getHarga() + "");
		tfStok.setText("" + Re.getStok() + "");
	}
	
	public void submitToDB() {
		if (Operator.Reorder(getReorder(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Stok Telah Bertambah !");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		tfID.setText("");
		tfNama.setText("");
		tfIDSupplier.setText("");
		tfHarga.setText("");
		tfStok.setText("");
		
		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}

	public void resetForm() {
		tfID.setText("");
		tfNama.setText("");
		tfIDSupplier.setText("");
		tfHarga.setText("");
		tfStok.setText("");
		
		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}

	public Reorder getReorder() {
		return new Reorder(tfID.getText(),tfNama.getText(),tfIDSupplier.getText(),Integer.parseInt(tfHarga.getText()),Integer.parseInt(tfStok.getText()));
	}
}
