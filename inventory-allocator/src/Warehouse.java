import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to store name and inventory of a warehouse.
 */
public class Warehouse {
    // Stores productName of a product and its amount.
    private Map<String, Integer> inventory;

    // The name of a warehouse.
    private String name;

    /**
     * Constructs a warehouse and initializes its name with input parameter.
     * @param name the name of warehouse
     */
    public Warehouse(String name) {
        try {
            if(name == null) throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Bad Input for Warehouse.");
            System.out.println("---------------------------");
            return ;
        }
        this.name = name;
        this.inventory = new HashMap<>();
    }

    /**
     * Constructs a warehouse and initializes its name and inventory with input parameters.
     * @param name the name of warehouse
     * @param inventory the inventory used to initialize inventory of current instance
     */
    public Warehouse(String name, Map<String, Integer> inventory) {
        try {
            if(name == null || inventory == null) throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Bad Input for Warehouse.");
            System.out.println("---------------------------");
            return ;
        }
        this.name = name;
        this.inventory = inventory;
    }

    /**
     * Returns inventory of current instance.
     * @return inventory of current instance
     */
    public Map<String, Integer> getMap() {
        return inventory;
    }

    /**
     * Sets inventory with input parameter.
     * @param inventory an inventory used to initialize inventory of current instance
     */
    public void setMap(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns name of current instance.
     * @return the name of current warehouse
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name with input parameter.
     * @param name the name of warehouse
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates product in inventory with specified number.
     * @param productName the name of a product
     * @param number the amount of a product
     */
    public void update(String productName, int number) {
        inventory.put(productName, number);
    }

    /**
     * Returns true if there is enough amount of specified product stored in inventory.
     * @param productName the name of a product
     * @param number the amount of a product
     * @return true if there is enough amount of specified product stored in inventory
     */
    public boolean hasEnough(String productName, int number) {
        if(!inventory.containsKey(productName)) return false;
        return inventory.get(productName) >= number;
    }

    /**
     *
     * @param productName the name of a product
     * @return true if there is at least 1 unit of specified product in inventory.
     */
    public boolean has(String productName) {
        return inventory.containsKey(productName);
    }

    /**
     *
     * @param productName the name of a product
     * @return the amount of specified product store in inventory.
     */
    public int getAmount(String productName) {
        return this.inventory.getOrDefault(productName, 0);
    }
}
