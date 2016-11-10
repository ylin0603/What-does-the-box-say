package whatdoestheboxsay;

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
}