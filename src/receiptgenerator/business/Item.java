package receiptgenerator.business;

public class Item {
	private String name;
	private double cost;
	private boolean is2for1;
	private boolean isFruitOrVegetable;

	public Item(String name, double cost, boolean is2for1, boolean isFruitOrVegetable) {
		this.name = name;
		this.cost = cost;
		this.is2for1 = is2for1;
		this.isFruitOrVegetable = isFruitOrVegetable;
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}

	public boolean is2for1() {
		return is2for1;
	}

	public boolean isFruitOrVegetable() {
		return isFruitOrVegetable;
	}
}
