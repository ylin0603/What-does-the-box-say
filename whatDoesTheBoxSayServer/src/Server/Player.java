package Server;

public class Player {

	// only client side
	int coolDownTime;
	int weaponType;

	// both use
	String nickName;
	int locX;
	int locY;
	int faceAngle;
	int locXPrev;
	int locYPrev;
	int HP;
	int killCount;
	int deadCount;
	int stayTime;
	int bulletCount;

	Attack attack;

	// move attack attacked bonus collision
	boolean moveFlag;
	boolean attackFlag;
	boolean attackedFlag;
	boolean bonusFlag;
	boolean collisionFlag;

	Player(int locX, int locY, int faceAngle) {

		this.locX = locX;
		this.locY = locY;
		this.faceAngle = faceAngle;
	}

	boolean getMoveFlag() {
		return moveFlag;
	}

	boolean getAttackFlag() {
		return attackFlag;
	}

	void setBonusFlag(boolean bonusResult) {
		bonusFlag = bonusResult;
	}

	void setCollisionFlag(boolean collisionResult) {
		collisionFlag = collisionResult;
	}
}