package object;

public class DetilTransaksi {
	private Product product;
	private int quantity;
	private Transaksi transaksi;

	public DetilTransaksi(Transaksi transaksi, Product product, int quantity) {
		this.transaksi = transaksi;
		this.product = product;
		this.quantity = quantity;
	}

	public DetilTransaksi(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduk() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Transaksi getTransaksi() {
		return transaksi;
	}

}
