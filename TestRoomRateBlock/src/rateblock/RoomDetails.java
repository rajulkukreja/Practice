/**
 * 
 */
package rateblock;

import java.util.List;
import java.util.Map;

/**
 * @author praveenk
 *
 */
public class RoomDetails {  	
	
	
	private String roomType;
	
	private Map<String, List<Room>> roomTypeMap;	

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public int compareTo(RoomDetails roomDetails) {
		return roomType.compareTo(roomDetails.roomType);  
	}

	/**
	 * @return the roomTypeMap
	 */
	public Map<String, List<Room>> getRoomTypeMap() {
		return roomTypeMap;
	}

	/**
	 * @param roomTypeMap the roomTypeMap to set
	 */
	public void setRoomTypeMap(Map<String, List<Room>> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}		
	

}
