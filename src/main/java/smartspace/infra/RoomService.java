package smartspace.infra;

import smartspace.data.ElementEntity;

public interface RoomService {
	
	public int CheckIn();
	public void CheckOut(int roomNumber);
	public void RoomServiceReservation(ElementEntity room) throws Exception;
	public int totalPrice(int Order);
	int totalPrice(ElementEntity room);
	

}
