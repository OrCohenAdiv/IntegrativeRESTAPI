package smartspace.infra;

import smartspace.dao.EnhancedElementDao;
import smartspace.data.ElementEntity;

public class RoomServiceImpl implements RoomService {
	
	private EnhancedElementDao<String> elementDao;

	@Override
	public int CheckIn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void CheckOut(int roomNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RoomServiceReservation(ElementEntity room) throws Exception {
		if(!room.getType().equals("ROOM")) {
			throw new Exception ("only ROOM can use room service");
		}
		int price = (int)room.getMoreAttributes().get("price");
		room.getMoreAttributes().put("price",price + 50);	
	}

	@Override
	public int totalPrice(ElementEntity room) {
		int price = (int)room.getMoreAttributes().get("price");
		room.getMoreAttributes().put("price",price + 50);
		return 0;
	}

}
