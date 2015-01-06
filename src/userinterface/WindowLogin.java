package userinterface;

import java.awt.*;

import javax.swing.*;

import system.*;
import userinterface.listener.ActionListen;
import Image.*;

public class WindowLogin extends JFrame {

	final private Core core;

	private JButton btnLogin;
	private JTextField txUsr;
	private JPasswordField txPsw;
	private JLabel lblUsr, lblPsw, lbalamat;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public WindowLogin(Core core) {
		super("Login");
		getContentPane().setBackground(Color.GRAY);
		setTitle("Account Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		this.core = core;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 371);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setResizable(false);
		JLabel labelHeader = new JLabel("<HTML><H1>Toko Obat Herbal</H1></HTML>");
		labelHeader.setFont(new Font("Tekton Pro", Font.PLAIN, 43));
		labelHeader.setForeground(Color.BLACK);
		labelHeader.setBounds(59,11,180,30);
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(WindowLogin.class.getResource("/Image/drug_shop-128.png")));
		label.setBounds(82, 42, 128, 128);
		getContentPane().add(label);
		
		container = this.getContentPane();
		container.setLayout(null);
		//container.setBackground(Color.WHITE);
		btnLogin = new JButton("<HTML><H3>Login</H3></HTML>");
		btnLogin.setForeground(new Color(255, 69, 0));
		btnLogin.setBackground(SystemColor.scrollbar);
		txUsr = new JTextField(15);
		txUsr.setToolTipText("Masukkan Username");
		txPsw = new JPasswordField(15);
		txPsw.setToolTipText("Masukkan Password");
		lblUsr = new JLabel("<HTML><H3>Username</H3></HTML>");
		lblUsr.setForeground(Color.BLACK);
		lblPsw = new JLabel("<HTML><H3>Password</H3></HTML>");
		lblPsw.setForeground(Color.BLACK);
		lbalamat = new JLabel("Ds. Pagerngumbuk 06/02 Wonoayu Sidoarjo");
		lbalamat.setForeground(SystemColor.info);
		lblUsr.setHorizontalAlignment(JLabel.RIGHT);
		lblPsw.setHorizontalAlignment(JLabel.RIGHT);

		lblUsr.setBounds(10, 197, 70, 20);
		txUsr.setBounds(100, 197, 180, 25);
		lblPsw.setBounds(10, 227, 70, 20);
		txPsw.setBounds(100, 227, 180, 25);
		btnLogin.setBounds(30, 267, 235, 37);
		lbalamat.setBounds(23, 312, 263, 30);
		
		btnLogin.addActionListener(new ActionListen(core, this, btnLogin));
		container.add(labelHeader);
		container.add(lblUsr);
		container.add(txUsr);
		container.add(lblPsw);
		container.add(txPsw);
		container.add(btnLogin);
		container.add(lbalamat);
		
	}

	public String getUser() {
		return txUsr.getText();
	}

	public String getPass() {
		return txPsw.getText();
	}
}
