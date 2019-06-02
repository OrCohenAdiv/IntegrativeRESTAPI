package smartspace.plugin;

public class CostInput {
	
	private int roomPrice;
	private int roomServicePrice;
	
	public CostInput(){
	}
	
	public int getRoomPrice() {
		return roomPrice;
	}
	
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}

	public int getRoomServicePrice() {
		return roomServicePrice;
	}

	public void setRoomServicePrice(int roomServicePrice) {
		this.roomServicePrice = roomServicePrice;
	}
}
