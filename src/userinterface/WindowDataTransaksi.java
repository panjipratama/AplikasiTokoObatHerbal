package userinterface;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import object.Product;
import object.Transaksi;
import system.*;
import userinterface.listener.ActionListen;

public class WindowDataTransaksi extends JFrame {
	private Core core;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<Product> product = new Vector<Product>();
	private Vector<String> nmProduct = new Vector<String>();

	private JTable tbl;

	public WindowDataTransaksi(Core core) {
		super("Data Transaksi");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;
		setResizable(false);

		setSize(430, 296);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.GRAY);
		JPanel panTbl = new JPanel();
		panTbl.setLayout(new BorderLayout());
		panTbl.setBounds(0, 0, 425, 219);
		panTbl.setBackground(Color.WHITE);
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.setBounds(30, 230, 170, 30);
		buttonDelete.setForeground(new Color(255, 69, 0));
		buttonDelete.setBackground(SystemColor.scrollbar);
		
		JButton buttonDetail = new JButton("Detail");
		buttonDetail.setBounds(220, 230, 170, 30);
		buttonDetail.setForeground(new Color(255, 69, 0));
		buttonDetail.setBackground(SystemColor.scrollbar);
		
		container.add(buttonDelete);
		container.add(buttonDetail);
		container.add(panTbl);
		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getListTransaksi(core.getConnection())));
		Operator.disableTableEdit(tbl);
		JScrollPane scrollPane = new JScrollPane(tbl);
		panTbl.add(scrollPane, BorderLayout.CENTER);
		buttonDelete.addActionListener(new ActionListen(core, this, tbl,
				buttonDelete, ActionListen.HAPUS_TRANS));
		buttonDetail.addActionListener(new ActionListen(core, this, tbl,
				buttonDetail, ActionListen.SHOW_DETIL_TRANSAKSI));
	}

	public Transaksi getTransaksi() {
		if (tbl.getSelectedRow() >= 0) {
			String val = tbl.getValueAt(tbl.getSelectedRow(), 0).toString();
			
			return new Transaksi(val);
		} else {
			return null;
		}
	}

	public void resetForm() {
		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}
}
