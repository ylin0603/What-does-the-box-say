package DOM;

public class Character {

	int x, y;
	int dir;
	int speed;
	int clientno;

	public Character(int clientno) {
		this.clientno = clientno;
		Initialize();
	}

	public void Initialize() {
		this.x = 0;
		this.y = 0;
		this.dir = 0;
		this.speed = 0;
	}

}
