package CDC;

public class Collision {
    final static double PlayerSize = Cdc.BOX_SIZE / 2;

    // no use
    public static boolean isCollison(ClientPlayerFeature player1,
            ClientPlayerFeature player2) {
        int p1LocX = player1.getLocX();
        int p1LocY = player1.getLocY();
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        return isCollison(p1LocX, p1LocY, p2LocX, p2LocY);
    }

    // for attack player
    public static boolean isCollison(ClientItemFeature item,
            ClientPlayerFeature player2) {
        int p1LocX = item.getLocX();
        int p1LocY = item.getLocY();
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        return isCollison(p1LocX, p1LocY, p2LocX, p2LocY);
    }

    // for normal player collision
    public static boolean isCollison(int p1LocX, int p1LocY,
            ClientPlayerFeature player2) {
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        return isCollison(p1LocX, p1LocY, p2LocX, p2LocY);
    }

    // for attack box
    public static boolean isCollison(ClientItemFeature item1,
            ClientItemFeature item2) {
        int p1LocX = item1.getLocX();
        int p1LocY = item1.getLocY();
        int p2LocX = item2.getLocX();
        int p2LocY = item2.getLocY();
        return isCollison(p1LocX, p1LocY, p2LocX, p2LocY);
    }

    // for respawn box
    public static boolean isCollison(int p1LocX, int p1LocY,
            ClientItemFeature item2) {
        int p2LocX = item2.getLocX();
        int p2LocY = item2.getLocY();
        return isCollison(p1LocX, p1LocY, p2LocX, p2LocY);
    }

    // for all
    public static boolean isCollison(int p1LocX, int p1LocY, int p2LocX,
            int p2LocY) {
        double size = ((PlayerSize * 2) * 1.2);
        if (distance1d(p1LocX, p2LocX) > size
                || distance1d(p1LocY, p2LocY) > size)
            return false;
        if (distance2d(p1LocX, p1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
            return true;
        return false;
    }

    // for box revenge
    public static boolean isCollison(int p1LocX, int p1LocY, double p1Size,
            ClientPlayerFeature player2) {
        double size = ((PlayerSize + p1Size) * 1.2);
        System.out.println(size + " " + (PlayerSize + p1Size) * 1.2);
        // this may change to not either 內切圓 外切圓 should be 混和圓
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        if (distance1d(p1LocX, p2LocX) > size
                || distance1d(p1LocY, p2LocY) > size)
            return false;
        if (distance2d(p1LocX, p1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
            return true;
        return false;
    }

    public static int distance1d(int loc1, int loc2) {
        return Math.abs(loc1 - loc2);
    }

    public static double distance2d(int loc1X, int loc1Y, int loc2X,
            int loc2Y) {
        return (Math.pow((loc1X - loc2X), 2) + Math.pow((loc1Y - loc2Y), 2));
    }
}
