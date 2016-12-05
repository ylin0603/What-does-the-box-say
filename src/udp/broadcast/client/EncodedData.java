package udp.broadcast.client;

public class EncodedData {
	private String type;
	private String data;

	EncodedData(String type, String data) {
		this.type = type;
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public String getData() {
		return data;
	}
}
