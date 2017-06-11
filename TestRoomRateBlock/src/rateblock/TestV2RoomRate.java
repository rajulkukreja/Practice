/**
 * 
 */
package rateblock;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author praveenk
 *
 */
public class TestV2RoomRate {
	
	public static int HOTEL_ID_INDEX = 1;
	public static int HOTEL_NAME_INDEX = 2;
	public static int CITY_ID_INDEX = 3;
	public static int CITY_NAME_INDEX = 4;	
	public static int SUPPLIER_ID_INDEX = 5;
	public static int SUPPLIER_NAME_INDEX = 6;
	public static int WIDTH_INDEX = 7;
	public static int HEIGHT_INDEX = 8;
	public static int ROOM_TYPE_INDEX = 9;
	public static int NO_OF_ROOMS_INDEX = 11;
	
	public static int RICH_WIDTH_MIN = 640;
	public static int RICH_HEIGHT_MIN = 480;
	public static float RICH_ASPECT_RATIO_MIN = 1.33f;
			

	public static String CSV_FILE = "/home/rajulkukreja/hotels.csv";
    
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String query = "select distinct(hgv.hotel_id), hgv.HOTEL_NAME, hgv.CITY_ID, hgv.CITY, hs.ID, hs.SUPPLIER_NAME, hii.O_WIDTH, hii.O_HEIGHT,hii.image_alt, hii.IMAGE_CATEGORY_ID,(SELECT COUNT(ROOM_TYPE_ID) FROM PLACES.HOTEL_ROOM_INFO  WHERE hotel_id = hgv.hotel_id AND status =1 ) noOfRooms "
				+ " FROM PLACES.HOTEL_GEOGRAPHY_VW hgv, PLACES.HOTEL_SUPPLIER_MAP hsm, PLACES.HOTEL_SUPPLIER hs, PLACES.HOTEL_IMAGE_INFO hii"
				+ " where hgv.HOTEL_ID = hsm.HOTEL_ID and hs.id = hsm.SUPPLIER_ID and hgv.HOTEL_ID = hii.HOTEL_ID "
				+ " and (hs.id = 5 or hs.id = 14)  and hii.image_alt is not null and hii.IMAGE_CATEGORY_ID=115601"
				+ " and hgv.HOTEL_ID  in "
				+ " (SELECT hotel_id  "
				+ " FROM (SELECT hotel_id, (SELECT COUNT(ROOM_TYPE_ID) FROM PLACES.HOTEL_ROOM_INFO WHERE hotel_id = hgv.hotel_id AND status =1) rmcnt, "
				+ " (SELECT COUNT(DISTINCT image_alt) FROM places.hotel_image_info WHERE hotel_id = hgv.hotel_id AND IMAGE_CATEGORY_ID=115601 ) imgcnt "
				+ " FROM PLACES.HOTEL_GEOGRAPHY_VW hgv WHERE COUNTRY_CODE = 'IN' ) WHERE rmcnt = imgcnt AND rmcnt > 0 AND imgcnt > 0) order by hgv.hotel_id ";

		System.out.println("Query :" + query);
		long startTime = System.currentTimeMillis();
		List<Hotel> hotelList = executeQuery(query);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time taken : " + elapsedTime);

		
		try {
			/*File file = new File (csvFile);
			if (!file.exists()) {
				file.createNewFile();
			}*/
			FileWriter writer = new FileWriter(CSV_FILE);
		
			 CsvUtils.writeLine(writer, Arrays.asList("Hotel Id", "Hotel name", "City Id", "City Name", "Num of Rooms", "Supplier", "Room rates block type"));
			 for (Hotel hotel : hotelList) {
				 System.out.println(
							"Hotel Id : " + hotel.getHotelId() + ",Hotel name :" + hotel.getHotelName() + ",City name : "
									+ hotel.getCityName() + ",No of rooms :" + hotel.getNoOfRooms() + ",Supplier name :"
									+ hotel.getSupplierName() + ",Room Block status : " + hotel.getRoomRateBlockStatus());
				CsvUtils.writeLine(writer, Arrays.asList(String.valueOf(hotel.getHotelId()), hotel.getHotelName(),
						String.valueOf(hotel.getCityId()), hotel.getCityName(), String.valueOf(hotel.getNoOfRooms()),
						hotel.getSupplierName(), hotel.getRoomRateBlockStatus()));
			 }
			  writer.flush();
		      writer.close();
			 System.out.println("Written to csv file :" + CSV_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		

	}
	
	
	private static void setRoomTypeFlag(Hotel hotel) {
		String status = "";
		Map<String, List<Room>> map = hotel.getRoomDetails().getRoomTypeMap();
		
		for (Map.Entry<String, List<Room>> entry : map.entrySet()) {
			if (status.equalsIgnoreCase("Lean")) {
				hotel.setRoomRateBlockStatus(status);
				return;
			}
			List<Room> roomList = entry.getValue();
			for (Room room : roomList) {
				if (room.getImgHeight() == 0 || room.getImgWidth() == 0) {
					hotel.setRoomRateBlockStatus("Invalid data");
					return;
				}
				float aspectRatio = (float) room.getImgWidth() / room.getImgHeight();
				if (aspectRatio > RICH_ASPECT_RATIO_MIN && room.getImgWidth() > RICH_WIDTH_MIN
						&& room.getImgHeight() > RICH_HEIGHT_MIN) {
					status = "Rich";
					break;
				} else {
					status = "Lean";
				}
				
			}
		}
		hotel.setRoomRateBlockStatus(status);
		
		
	}
	
	private static void setRoomInfo(Hotel hotel, ResultSet rs) throws SQLException {
		
		RoomDetails roomDetails = hotel.getRoomDetails();
		if (roomDetails == null) {
			roomDetails = new RoomDetails();
			hotel.setRoomDetails(roomDetails);
		}
		roomDetails.setRoomType(rs.getString(ROOM_TYPE_INDEX));
		
		
		Room room = new Room();
		room.setImgHeight(rs.getInt(HEIGHT_INDEX));
		room.setImgWidth(rs.getInt(WIDTH_INDEX));
		
		Map<String, List<Room>> roomTypeMap = roomDetails.getRoomTypeMap();
		if (roomTypeMap == null) {
			roomTypeMap = new HashMap<String, List<Room>>();
			roomDetails.setRoomTypeMap(roomTypeMap);
		} 
		
		if (roomTypeMap.get(roomDetails.getRoomType()) != null) {
			List<Room> roomList = roomTypeMap.get(roomDetails.getRoomType());
			roomList.add(room);
		} else {
			List<Room> roomList = new ArrayList<Room>();
			roomList.add(room);
			roomTypeMap.put(roomDetails.getRoomType(), roomList);
		}
		
	}
	
	public static List<Hotel> executeQuery(String query) {
		Connection con = OracleCon.getConnection();
		List<Hotel> hotelList = new ArrayList<Hotel>();
		int prevHotelId = 0;
		int hotelId = 0;
		Hotel prevHotel = null;
		if (con != null) {
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				Hotel hotel = null;
				while (rs.next()) {
					hotelId = rs.getInt(HOTEL_ID_INDEX);
					if (prevHotelId == 0 || prevHotelId != hotelId) {
						
						if (prevHotelId != 0) {
							//set the flag
							setRoomTypeFlag(prevHotel);
						}
						prevHotelId = hotelId;
						
						hotel = new Hotel();
						hotel.setHotelId(hotelId);
						hotel.setHotelName(rs.getString(HOTEL_NAME_INDEX));
						hotel.setCityId(rs.getInt(CITY_ID_INDEX));
						hotel.setCityName(rs.getString(CITY_NAME_INDEX));
						hotel.setNoOfRooms(rs.getInt(NO_OF_ROOMS_INDEX));
						hotel.setSupplierName(rs.getString(SUPPLIER_NAME_INDEX));					
						
						setRoomInfo(hotel, rs);
						hotelList.add(hotel);
						
					} else  {
						setRoomInfo(prevHotel, rs);
						//prevHotel.getRoomDetailsList().add(roomDetails);
					}
					prevHotel = hotel;
				
				}
				setRoomTypeFlag(prevHotel);
				System.out.println("Successfully executed the query");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
						con = null;
						System.out.println("Closing the connection");
					}	
				} catch (SQLException sqle) {
					// TODO Auto-generated catch block
					sqle.printStackTrace();
				}
			}
		}
		return hotelList;
	}

}
