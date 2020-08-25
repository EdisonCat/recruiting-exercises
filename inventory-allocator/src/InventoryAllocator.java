import java.util.*;

public class InventoryAllocator {

    /**
     * This class stores productName and amount of a product that should be fetched in a warehouse.
     * Variable key is productName, val is amount.
     */
    private static class Pair {
        String productName;
        int amount;
        Pair(String productName, int amount) {
            this.productName = productName;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return this.productName + "\t" + this.amount;
        }
    }
    // Stores the list of products need to shipped.
    Map<String, Integer> productList;

    // Store all the Warehouse objects.
    List<Warehouse> warehouseList;

    // Stores the name of warehouse and the list of products that can be fetched in that warehouse.
    Map<String, List<Pair>> allocatorInfo;

    /**
     * Constructs an InventoryAllocator and initializes its productList with input parameter.
     * @param productList map used to initialize productList of current instance
     */
    public InventoryAllocator(Map<String, Integer> productList) {
        try {
            if(productList == null) throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Bad Input for InventoryAllocator.");
            return ;
        }
        this.productList = productList;
        this.warehouseList = new ArrayList<>();
        this.allocatorInfo = new LinkedHashMap<>();
    }

    /**
     * Constructs an InventoryAllocator and initializes its productList, warehouseList with input parameters, and
     * calls method compute() to generate allocatorInfo.
     * @param productList map used to initialize productList of current instance
     * @param warehouseList list of warehouses
     */
    public InventoryAllocator(Map<String, Integer> productList, List<Warehouse> warehouseList) {
        try {
            if(productList == null) throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Bad Input for InventoryAllocator.");
            System.out.println("---------------------------");
            return ;
        }
        this.productList = productList;
        this.warehouseList = warehouseList;
        this.allocatorInfo = new LinkedHashMap<>();
        compute();
    }

    /**
     * Queries the inventory according to productList and generates allocatorInfo if all products can be found in
     * single warehouse or across warehouses.
     */
    public void compute() {
        if(this.productList == null || this.warehouseList == null
                || this.productList.size() < 1 || this.warehouseList.size() < 1) {
            System.out.println("No products need to be shipped or no warehouses exist.");
            return ;
        }

        // Assumes that the amount of product stored across warehouses is enough.
        boolean enough = true;

        // Iterates productList
        for(String productName: this.productList.keySet()) {
            if(this.productList.get(productName) <= 0) continue;

            // Iterates warehouseList
            for(Warehouse warehouse: this.warehouseList) {

                // Gets the remaining amount of a product that has not been .
                int amount = this.productList.get(productName);

                // If current warehouse can handle the amount, add the productName and amount to allocatorInfo, break
                // and continue with the next product.
                if(warehouse.hasEnough(productName, amount)) {
                    this.allocatorInfo.computeIfAbsent(warehouse.getName(), function -> new ArrayList<>()).add(new Pair(productName, amount));
                    this.productList.put(productName, 0);
                    break;
                }
                else {
                    // If current product exists in the warehouse, fetch them all and continue with the next warehouse.
                    if(warehouse.has(productName)) {
                        int exist = warehouse.getAmount(productName);
                        this.allocatorInfo.computeIfAbsent(warehouse.getName(), function -> new ArrayList<>()).add(new Pair(productName, exist));
                        this.productList.put(productName, this.productList.get(productName) - exist);
                    }
                }
            }

            // If the amount of a product is 0, which means it has been handled by one or more warehouse.
            // If the amount is NOT 0, all products will NOT be shipped anyway, stop the iteration to save time.
            if(this.productList.get(productName) != 0) {
                enough = false;
                break;
            }
        }

        // If not all products can be fetched across warehouses, all products will not be shipped.
        if(!enough) {
            System.out.println("Products will NOT be shipped.");
            this.allocatorInfo.clear();
        }
    }

    /**
     * Constructs an InventoryAllocator with no properties initialized.
     */
    public InventoryAllocator() {
        this.productList = new HashMap<>();
        this.warehouseList = new ArrayList<>();
        this.allocatorInfo = new HashMap<>();
    }

    /**
     * Returns productList of current instance.
     * @return productList of current instance
     */
    public Map<String, Integer> getProductList() {
        return productList;
    }

    /**
     * Sets productList with input parameter.
     * @param productList map used to set productList of current instance
     */
    public void setProductList(Map<String, Integer> productList) {
        this.productList = productList;
    }

    /**
     * Returns warehouseList of current instance.
     * @return list of warehouses of current instance
     */
    public List<Warehouse> getWarehouseList() {
        return warehouseList;
    }

    /**
     * Sets warehouseList with input parameter.
     * @param warehouseList list of warehouses used to set warehouseList of current instance
     */
    public void setWarehouseList(List<Warehouse> warehouseList) {
        this.warehouseList = warehouseList;
    }

    /**
     * Prints productName and amount that should be fetched in each warehouse from allocatorInfo.
     */
    public void getAllocatorInfo() {
        if(this.allocatorInfo == null) {
            System.out.println("Products will NOT be shipped due to bad inputs.");
            return ;
        }
        for(String key: this.allocatorInfo.keySet()) {
            System.out.println(key + ":");
            for(Pair pair: this.allocatorInfo.get(key)) System.out.println("\t" + pair);
        }
        System.out.println("---------------------------");
    }
}
