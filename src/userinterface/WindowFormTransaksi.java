package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import object.Product;
import object.DetilTransaksi;
import object.Transaksi;
import object.User;
import system.*;
import userinterface.listener.ActionListen;
import userinterface.listener.KeyListen;

import java.awt.SystemColor;

import javax.swing.ImageIcon;

public class WindowFormTransaksi extends JFrame {

	final int TGL = 0, KASIR = 1, BARANG = 2, HARGA = 3, JUMLAH = 4;
	
	private Core core;
	
	private User user;
	private Transaksi t;

	private JPanel panLeft, panGrand;
	private JTextField tfTgl, tfKasir, tfHarga, tfJumlah;
	private JLabel l[] = new JLabel[5];
	private JComboBox cb;
	private JButton btnTambahProduct, btnTambahTransaksi, btnReset;
	private JTable tbl;

	private DefaultTableModel model;

	private Container container;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<String> nmProduct = new Vector<String>();
	private Vector<Product> product = new Vector<Product>();

	public WindowFormTransaksi(Core core) {
		super("Window Transaksi | " + core.getDateAsString());
		getContentPane().setBackground(Color.GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;
		this.user = core.getLoggedInUser();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(810, 450);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		container = this.getContentPane();
		setDefaultCloseOperation(0);
		model = new DefaultTableModel();
		model.addColumn("Nama Item");
		model.addColumn("Jumlah Item");
		model.addColumn("Total Harga");
		ResultSet rs = Operator.getListProduct(core.getConnection());
		nmProduct.removeAllElements();
		product.removeAllElements();
		try {
			while (rs.next()) {
				product.add(new Product(rs.getString(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getInt(5)));
				if (product.lastElement().getStok() > 0)
					nmProduct.add(product.lastElement().getNama());
				else
					product.removeElement(product.lastElement());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		cb = new JComboBox(nmProduct);
		tfTgl = new JTextField(core.getDateAsString());
		tfKasir = new JTextField(user.getUsername());
		tfJumlah = new JTextField();
		tfHarga = new JTextField();
		fillFormByIndex(cb.getSelectedIndex());
		panGrand = new JPanel(null);
		panGrand.setBackground(Color.GRAY);
		panGrand.setBounds(10, 11, 465, 378);
		panLeft = new JPanel(null);
		panLeft.setBackground(Color.GRAY);
		panLeft.setBounds(474, 11, 320, 378);

		JMenuBar menu = new JMenuBar();
		menu.setBackground(SystemColor.scrollbar);
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().isAdmin() ? "Supervisor " : "Kasir "
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.setIcon(new ImageIcon(WindowFormTransaksi.class.getResource("/Image/logout.png")));
		miLogOut.addActionListener(new ActionListen(core, this, miLogOut,
				ActionListen.LOGOUT));

		
		JMenu menuProduct = new JMenu("Product");
		JMenuItem miProductData = new JMenuItem("Data Product");
		miProductData.setIcon(new ImageIcon(WindowFormTransaksi.class.getResource("/Image/products.png")));
		miProductData.addActionListener(new ActionListen(core, this,
				miProductData, ActionListen.SHOW_DATA_PRODUCT));
		
		menu.add(menuUser);
		menuUser.add(miLogOut);

		
		menu.add(menuProduct);
		menuProduct.add(miProductData);
		JLabel lbJudul = new JLabel ("<HTML><H2>Penjualan Jamu Herbal</H2></HTML>");
		lbJudul.setForeground(Color.DARK_GRAY);
		
		JLabel logokasir = new JLabel();
		logokasir.setIcon(new ImageIcon(WindowLogin.class.getResource("/Image/kasir.png")));
		
		l[TGL] = new JLabel("Tanggal");
		l[KASIR] = new JLabel("Nama Kasir");
		l[BARANG] = new JLabel("Nama Product");
		l[HARGA] = new JLabel("Harga Rp.");
		l[JUMLAH] = new JLabel("Jumlah Item");

		tfTgl.setEnabled(false);
		tfKasir.setEnabled(false);
		tfHarga.setEnabled(false);
		tfTgl.setBounds(125, 153, 170, 25);
		tfKasir.setBounds(125, 183, 170, 25);
		cb.setBounds(125, 213, 170, 25);
		tfHarga.setBounds(125, 243, 170, 25);
		tfJumlah.setBounds(125, 273, 170, 25);

		lbJudul.setBounds(70, 122, 240, 20);
		logokasir.setBounds(108, 11, 128, 108);
		l[TGL].setBounds(5, 153, 100, 20);
		l[KASIR].setBounds(5, 183, 100, 20);
		l[BARANG].setBounds(5, 213, 100, 20);
		l[HARGA].setBounds(5, 243, 100, 20);
		l[JUMLAH].setBounds(5, 273, 100, 20);

		btnTambahProduct = new JButton("<HTML><H4>Tambah ke Keranjang</H4></HTML>");
		btnTambahProduct.setBounds(86, 320, 170, 30);
		btnTambahProduct.setForeground(new Color(255, 69, 0));
		btnTambahProduct.setBackground(SystemColor.scrollbar);
		btnTambahProduct.addActionListener(new ActionListen(this,
				btnTambahProduct));
		btnTambahProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnTambahTransaksi.setEnabled(true);
				btnReset.setEnabled(true);
			}
		});
		btnTambahTransaksi = new JButton("<HTML><H4>Save|Print</H4></HTML>");
		btnTambahTransaksi.setBounds(324, 337, 120, 30);
		btnTambahTransaksi.setForeground(new Color(255, 69, 0));
		btnTambahTransaksi.setBackground(SystemColor.scrollbar);
		btnReset = new JButton("<HTML><H4>Reset</H4></HTML>");
		btnReset.setBounds(194, 337, 120, 30);
		btnReset.setForeground(new Color(255, 69, 0));
		btnReset.setBackground(SystemColor.scrollbar);
		btnTambahTransaksi.setEnabled(false);
		btnReset.setEnabled(false);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetForm();
				btnTambahTransaksi.setEnabled(false);
				btnReset.setEnabled(false);
			}
		});
		btnTambahTransaksi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnTambahTransaksi.setEnabled(false);
				btnReset.setEnabled(false);
			}
		});
		btnTambahTransaksi.addActionListener(new ActionListen(core, this,
				btnTambahTransaksi));
		tfJumlah.addKeyListener(new KeyListen(this, tfJumlah,
				KeyListen.NUMBER_ONLY));
		tfJumlah.addKeyListener(new KeyListen(this, tfJumlah,
				KeyListen.ON_STOCK));
		cb.addActionListener(new ActionListen(this, cb));
		panLeft.add(lbJudul);
		panLeft.add(logokasir);
		panLeft.add(cb);
		panLeft.add(tfTgl);
		panLeft.add(tfKasir);
		panLeft.add(tfHarga);
		panLeft.add(tfJumlah);
		for (int i = 0; i < l.length; i++) {
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			panLeft.add(l[i]);
		}
		panGrand.add(btnTambahTransaksi);
		panGrand.add(btnReset);
		panLeft.add(btnTambahProduct);
		container.add(panGrand);
		tbl = new JTable(model);
		container.add(panLeft);
		
		
		Operator.disableTableEdit(tbl);
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(10, 11, 452, 315);
		panGrand.add(scrollPane);

		resetForm();
	}

	public void fillFormByIndex(int index) {
		tfJumlah.setText("1");
		tfHarga.setText(product.get(index).getHarga() * Integer.parseInt(tfJumlah.getText()) + "");
	}

	public void resetForm() {
		int row = tbl.getRowCount() - 1;
		for (int i = row; i >= 0; i--)
			((DefaultTableModel) tbl.getModel()).removeRow(i);
		t = new Transaksi(core.getDate(), user);
	}

	public void addProductToTable(DetilTransaksi dt) {
		for (int i = 0; i < tbl.getRowCount(); i++) {
			// test
		}
		model.addRow(new String[] { dt.getProduk().getNama(),
				dt.getQuantity() + "",
				dt.getProduk().getHarga() * dt.getQuantity() + "" });
		t.addDetilTransaksi(dt);
	}

	public Vector<Product> getListProduct() {
		return product;
	}

	public Product getSelectedProduct() {
		return product.get(cb.getSelectedIndex());
	}

	public int getQtyProduct() {
		return Integer.parseInt(tfJumlah.getText());
	}

	public Transaksi getTransaksi() {
		return t;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return t.getDetilTransaksi();
	}

	public void submitToDB() {
		if (Operator.tambahTransaksi(getTransaksi(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		if (JOptionPane.showConfirmDialog(this, "Cetak transaksi?", "",
				JOptionPane.YES_NO_OPTION) == 0) {
			core.printReport(t);
		}
		resetForm();
	}
}
