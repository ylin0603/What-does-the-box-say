package CDC;

import transfer.TransferPlayer;

public class ClientPlayerFeature {

	private TransferPlayer transferPlayer;
	private int velocity = 2;
	private int lastShootTime;
	private int lastMoveTime;

	public ClientPlayerFeature(int clientNo, String nickName) {
		transferPlayer = new TransferPlayer(clientNo, nickName);

	}

	public TransferPlayer getTransferPlayer() {
		return transferPlayer;
	}

	public void setTransferPlayer(TransferPlayer transferPlayer) {
		this.transferPlayer = transferPlayer;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(int lastShootTime) {
		this.lastShootTime = lastShootTime;
	}

	public int getLastMoveTime() {
		return lastMoveTime;
	}

	public void setLastMoveTime(int lastMoveTime) {
		this.lastMoveTime = lastMoveTime;
	}


}
