package transfer;

public class TransferPlayer {
    private int playerId;
    public int weaponType;
    private String nickName;
    public int locX, locY;
    public double faceAngle;
    public int HP;
    public int killCount, deadCount;
    public int bulletCount;
    public boolean attackFlag;
    public boolean attackedFlag;
    public boolean collisionFlag;
    public boolean isDead;

    public TransferPlayer(int playerId, String nickName) {
        this.playerId = playerId;
        this.nickName = nickName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getNickName() {
        return nickName;
    }
}
