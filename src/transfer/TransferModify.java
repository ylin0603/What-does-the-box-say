package transfer;

public class TransferModify {
	private int eventType;
	// move
	// spin
	// attack
	private boolean typeDetail;

	// move forward | backward
	// spin right | left
	// attack closeRange | longRange
	public TransferModify() {}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public boolean isTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(boolean typeDetail) {
		this.typeDetail = typeDetail;
	}

}
