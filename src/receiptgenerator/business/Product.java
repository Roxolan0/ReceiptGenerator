package receiptgenerator.business;

public class Product {
	private String name;
	private double price;
	private boolean is2for1;
	private boolean isFruitOrVegetable;
	
	public Product(String name, double price, boolean is2for1, boolean isFruitOrVegetable) {
		this.name = name;
		this.price = price;
		this.is2for1 = is2for1;
		this.isFruitOrVegetable = isFruitOrVegetable;
	}
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public boolean isIs2for1() {
		return is2for1;
	}
	public boolean isFruitOrVegetable() {
		return isFruitOrVegetable;
	}
}
