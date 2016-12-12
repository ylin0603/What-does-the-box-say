package transfer;

public class TransferPlayer {
	private int playerId;
	private int weaponType;
	private String nickName;
	private int locX, locY;
	private double faceAngle;
	private int HP;
	private int killCount, deadCount;
	private int bulletCount;
	private boolean attackFlag;
	private boolean attackedFlag;
	private boolean collisionFlag;
	private boolean isDead;

	public TransferPlayer(int playerId, String nickName) {
		this.playerId = playerId;
		this.nickName = nickName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocY() {
		return locY;
	}

	public void setLocY(int locY) {
		this.locY = locY;
	}

	public double getFaceAngle() {
		return faceAngle;
	}

	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public int getDeadCount() {
		return deadCount;
	}

	public void setDeadCount(int deadCount) {
		this.deadCount = deadCount;
	}

	public int getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}

	public boolean getAttackFlag() {
		return attackFlag;
	}

	public void setAttackFlag(boolean attackFlag) {
		this.attackFlag = attackFlag;
	}

	public boolean getAttackedFlag() {
		return attackedFlag;
	}

	public void setAttackedFlag(boolean attackedFlag) {
		this.attackedFlag = attackedFlag;
	}

	public boolean getCollisionFlag() {
		return collisionFlag;
	}

	public void setCollisionFlag(boolean collisionFlag) {
		this.collisionFlag = collisionFlag;
	}

	public boolean getIsDead() {
		return isDead;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}
}
