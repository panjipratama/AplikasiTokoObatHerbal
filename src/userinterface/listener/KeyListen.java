package userinterface.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import system.Core;
import system.Operator;
import userinterface.WindowFormProduct;
import userinterface.WindowFormTransaksi;
import userinterface.WindowReorder;

public class KeyListen implements KeyListener {

	public static final int NUMBER_ONLY = 0, ON_STOCK = 1;
	private int mode;
	private JTextField jf;
	private WindowFormTransaksi frmFormTrans;
	private WindowFormProduct frmFormProduct;
	private WindowReorder frmReorder;
	private JButton btn;
	private Core core;

	public KeyListen(WindowFormTransaksi frmFormTrans, JTextField jf,
			int mode) {
		this.frmFormTrans = frmFormTrans;
		this.jf = jf;
		this.mode = mode;
	}

	public KeyListen(Core core, WindowFormProduct frm, JTextField jf,
			int mode) {
		this.core = core;
		this.frmFormProduct = frm;
		this.jf = jf;
		this.mode = mode;
	}

	public KeyListen(Core core, WindowReorder frm, JTextField jf,
			int mode) {
		this.core = core;
		this.frmReorder = frm;
		this.jf = jf;
		this.mode = mode;
	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent ev) {
		switch (mode) {
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedProduct().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				jf.setText("" + LIMIT);
			}
			break;
		case NUMBER_ONLY:
			if (jf.getText().equalsIgnoreCase("")) {
				jf.setText("1");
			}
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent ev) {
		switch (mode) {
		case NUMBER_ONLY:
			if (ev.getKeyChar() < '0' || ev.getKeyChar() > '9') {
				ev.consume();
			}
			break;
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedProduct().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				ev.consume();
			}
			break;
		}

	}
}
