import org.junit.Test;

import java.util.*;

public class InventoryAllocatorTest {

    // inventory of first warehouse
    final static Map<String, Integer> inventory1 = new HashMap<>();

    // inventory of second warehouse
    final static Map<String, Integer> inventory2 = new HashMap<>();

    // inventory of first warehouse
    final static Map<String, Integer> emptyInventory = new HashMap<>();

    // productList of a small order
    final static Map<String, Integer> smallProductList = new HashMap<>();

    // productList of a medium order
    final static Map<String, Integer> mediumProductList = new HashMap<>();

    // productList of a large order
    final static Map<String, Integer> largeProductList = new HashMap<>();

    // productList of a special order with products that do not exist in all warehouses
    final static Map<String, Integer> specialProductList = new HashMap<>();

    // empty productList
    final static Map<String, Integer> emptyProductList = new HashMap<>();

    // empty productList
    final static Map<String, Integer> wrongDataProductList = new HashMap<>();

    // Add products to inventories and productLists
    static {
        inventory1.put("Apple", 5);
        inventory1.put("Banana", 5);
        inventory1.put("Cherry", 5);

        inventory2.put("Apple", 5);
        inventory2.put("Banana", 5);
        inventory2.put("Cherry", 5);

        smallProductList.put("Apple", 5);
        smallProductList.put("Banana", 5);
        smallProductList.put("Cherry", 5);

        mediumProductList.put("Apple", 5);
        mediumProductList.put("Banana", 10);
        mediumProductList.put("Cherry", 10);

        largeProductList.put("Apple", 5);
        largeProductList.put("Banana", 15);
        largeProductList.put("Cherry", 15);

        specialProductList.put("Apple", 5);
        specialProductList.put("Banana", 5);
        specialProductList.put("Mango", 1);

        wrongDataProductList.put("Apple", -1);
        wrongDataProductList.put("Banana", 0);
        wrongDataProductList.put("Cherry", 1);

    }

    // Initialize 2 warehouses
    Warehouse warehouse1 = new Warehouse("First Warehouse", inventory1);
    Warehouse warehouse2 = new Warehouse("Second Warehouse", inventory2);
    Warehouse emptyWarehouse = new Warehouse("Empty Warehouse", emptyInventory);

    /**
     * This will test the case where splitting an item across warehouses this the only way that works.
     * Products will be shipped.
     */
    @Test
    public void allocatorTestEnoughInventory() {
        System.out.println("Enough Inventory Test(Multi Inventories work together):");
        List<Warehouse> warehouseList = new ArrayList<>(Arrays.asList(warehouse1, warehouse2));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(mediumProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where required number of item in productList is invalid, e.g. "Apple" -1, "Banana" 0.
     * Products with valid required amount will still be shipped.
     */
    @Test
    public void allocatorTestInvalidProduct() {
        System.out.println("Invalid amount of Item in productList Test(Assumed that products with valid required amount will still be shipped):");
        List<Warehouse> warehouseList = new ArrayList<>(Arrays.asList(warehouse1, warehouse2));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(wrongDataProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where the amount of product is NOT enough across warehouses.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestNotEnoughInventory() {
        System.out.println("Not Enough Inventory Test:");
        List<Warehouse> warehouseList = new ArrayList<>(Arrays.asList(warehouse1, warehouse2));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(largeProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where the amount of product is enough in single warehouse.
     * Products will be shipped.
     */
    @Test
    public void allocatorTestSingleEnoughInventory() {
        System.out.println("Single Enough Inventory Test:");
        List<Warehouse> singleWarehouseList = new ArrayList<>(Collections.singletonList(warehouse1));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(smallProductList, singleWarehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where the product does NOT exist across warehouses, e.g. "Mango".
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestProductsNotExist() {
        System.out.println("Products Not Exist:");
        List<Warehouse> warehouseList = new ArrayList<>(Arrays.asList(warehouse1, warehouse2));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(specialProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where inventory is empty.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestEmptyInventory() {
        System.out.println("Empty Inventory Test:");
        List<Warehouse> warehouseList = new ArrayList<>(Collections.singletonList(emptyWarehouse));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(smallProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where productList is empty.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestEmptyProductList() {
        System.out.println("Empty Product List Test:");
        List<Warehouse> warehouseList = new ArrayList<>(Collections.singletonList(emptyWarehouse));
        InventoryAllocator inventoryAllocator = new InventoryAllocator(emptyProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where warehouseList is empty.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestEmptyWarehouseList() {
        System.out.println("Empty Warehouse List Test:");
        List<Warehouse> warehouseList = new ArrayList<>();
        InventoryAllocator inventoryAllocator = new InventoryAllocator(smallProductList, warehouseList);
        inventoryAllocator.getAllocatorInfo();
    }

    /**
     * This will test the case where the inputs are invalid when constructing Warehouse Objects.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestBadInputForWarehouse() {
        System.out.println("Bad Input Test for Warehouse:");
        Warehouse badWarehouse = new Warehouse(null, null);
    }

    /**
     * This will test the case where the inputs are invalid when constructing InventoryAllocator Objects.
     * Products will NOT be shipped.
     */
    @Test
    public void allocatorTestBadInputForInventoryAllocator() {
        System.out.println("Bad Input Test for InventoryAllocator:");
        InventoryAllocator inventoryAllocator = new InventoryAllocator(null, null);
    }
}
