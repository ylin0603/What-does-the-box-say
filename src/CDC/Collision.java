package CDC;

public class Collision {
    final static double PlayerSize = Cdc.BOX_SIZE / 2;

    // for normal player collision
    // for normal attack
    public static boolean isPlayersCollison(ClientPlayerFeature player1,
            ClientPlayerFeature player2) {
        double size = ((PlayerSize * 2) * 1.2);
        int p1LocX = player1.getLocX();
        int p1LocY = player1.getLocY();
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        if (distance1d(p1LocX, p2LocX) > size
                || distance1d(p1LocY, p2LocY) > size)
            return false;
        if (distance2d(p1LocX, p1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
            return true;
        return false;
    }

    public static boolean isItemPlayerCollison(ClientItemFeature item,
            ClientPlayerFeature player2) {
        double size = ((PlayerSize * 2) * 1.2);
        int p1LocX = item.getLocX();
        int p1LocY = item.getLocY();
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        if (distance1d(p1LocX, p2LocX) > size
                || distance1d(p1LocY, p2LocY) > size)
            return false;
        if (distance2d(p1LocX, p1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
            return true;
        return false;
    }

    public static boolean isItemItemCollison(ClientItemFeature item1,
            ClientItemFeature item2) {
        double size = ((PlayerSize * 2) * 1.2);
        int p1LocX = item1.getLocX();
        int p1LocY = item1.getLocY();
        int p2LocX = item2.getLocX();
        int p2LocY = item2.getLocY();
        if (distance1d(p1LocX, p2LocX) > size
                || distance1d(p1LocY, p2LocY) > size)
            return false;
        if (distance2d(p1LocX, p1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
            return true;
        return false;
    }

    // for box player collision
    // for eat pack
    // for bullet attack
    public static boolean isCirclePlayerCollison(int c1LocX, int c1LocY,
            int c1Size, ClientPlayerFeature player2) {
        double size = ((PlayerSize + c1Size) * 1.2);
        System.out.println(size + " " + (PlayerSize + c1Size) * 1.2);
        // this may change to not either 內切圓 外切圓 should be 混和圓
        int p2LocX = player2.getLocX();
        int p2LocY = player2.getLocY();
        if (distance1d(c1LocX, p2LocX) > size
                || distance1d(c1LocY, p2LocY) > size)
            return false;
        if (distance2d(c1LocX, c1LocY, p2LocX, p2LocY) < Math.pow(size, 2))
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
