package vanilla;

public class settings {
    /**
     * 0 is OFF, 1 is ON
     */
    int debug;
    
    /**
     * INT between 1 to 8, represents the left wheel
     */
    int wheelLeft;
    /**
     * INT between 1 to 8, represents the middle wheel
     */
    int wheelMid;
    /**
     * INT between 1 to 8, represents the right wheel
     */
    int wheelRight;
    
    /**
     * Ringstellung/Ring setting, 3 letter String. e.g. "AAA"
     */
    String ring;
    /**
     * Grundstellung/Ring setting, 3 letter String. e.g. "AAA"
     */
    String ground;
    
    /**
     * Part of the pair of the plug board, 
     * each letter should swap with the same index on the "plugout".
     * e.g. plugin = "ABC" and plugout = "XYZ" means A-X, B-Y and C-Z
     */
    String plugin;
    
    /**
     * Part of the pair of the plug board, 
     * each letter should swap with the same index on the "plugin".
     * e.g. plugin = "ABC" and plugout = "XYZ" means A-X, B-Y and C-Z
     */
    String plugout;
    
    
    public settings(String settingsString) {
        // 1 2 3 K D B A A A 
        //0 1 2 3 4 5 6 7 8 9
        wheelLeft = Integer.parseInt(settingsString.substring(0, 1));
        wheelMid = Integer.parseInt(settingsString.substring(1, 2));
        wheelRight = Integer.parseInt(settingsString.substring(2, 3));
        ring = settingsString.substring(3, 6);
        ground = settingsString.substring(6, 9);
        plugin = "";
        plugout = "";
    }
}
