/**
 * 
 */
package rateblock;

import java.util.List;

/**
 * @author praveenk
 *
 */
public class Hotel {
	//select distinct(hgv.hotel_id), hgv.HOTEL_NAME,  hgv.CITY_ID, hgv.CITY, hri.NO_OF_ROOMS, hs.ID, hs.SUPPLIER_NAME, hii.O_WIDTH, hii.O_HEIGHT, hii.IMAGE_W_FILENAME "
	private  int hotelId;
	private String hotelName;
	private int cityId;
	private String cityName;
	private int noOfRooms;
	private String supplierName;
	private RoomDetails roomDetails;
	private String roomRateBlockStatus = "Rich";
	/**
	 * @return the hotelId
	 */
	public int getHotelId() {
		return hotelId;
	}
	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}
	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the noOfRooms
	 */
	public int getNoOfRooms() {
		return noOfRooms;
	}
	/**
	 * @param noOfRooms the noOfRooms to set
	 */
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}
	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	/**
	 * @return the roomRateBlockStatus
	 */
	public String getRoomRateBlockStatus() {
		return roomRateBlockStatus;
	}
	/**
	 * @param roomRateBlockStatus the roomRateBlockStatus to set
	 */
	public void setRoomRateBlockStatus(String roomRateBlockStatus) {
		this.roomRateBlockStatus = roomRateBlockStatus;
	}
	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the roomDetails
	 */
	public RoomDetails getRoomDetails() {
		return roomDetails;
	}
	/**
	 * @param roomDetails the roomDetails to set
	 */
	public void setRoomDetails(RoomDetails roomDetails) {
		this.roomDetails = roomDetails;
	}
	
	

}
