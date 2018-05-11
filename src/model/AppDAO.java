package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppDAO {
	
DBConnect con = new DBConnect();
	
	public ObservableList<RoomBean> getAllRooms() {
		//should get and return all rooms as observable list
		
		ObservableList<RoomBean> list = FXCollections.observableArrayList();
		
		String get_rooms_query = "SELECT hotel.name, room.room_id, room.room_number, room.price, room.beds, room.smoking, room.room_size, room.view, room.adjointsTo "
				+ "FROM room "
				+ "JOIN hotel ON hotel.hotel_id = room.hotel_id "
				+ "ORDER BY hotel.name"; 
		
		ResultSet res;
		try {
			res = con.retrieveData(get_rooms_query);
			while(res.next()){
				String campus;
				/*
				if(res.getInt("hotel_id") == 2){
					campus="Vaxjo";
				}else*/
					campus= "Kalmar";
					
				String roomNumber= Integer.toString(res.getInt("room_number"));
					
	           list.add(new RoomBean(roomNumber, res.getInt("price"), res.getInt("beds"), res.getString("smoking"), res.getString("room_size"), res.getString("view"), campus, res.getString("adjointsTo")));
	            
	            
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(list.size()+"++++++++++++++++++++++");
		
		
		
		return list;
	}
	/*
	public ObservableList<RoomBean> getRoomsWithCriteria(int bedNumber,String smokingStatus,String roomSize,String view, String campus) throws Exception{
	
		ObservableList<RoomBean> list = FXCollections.observableArrayList();
		
		String get_room_query = "SELECT hotel.name, room.room_number, room.price, room.beds, room.smoking, room.room_size, room.view "
				+ "FROM room "
				+ "JOIN hotel ON hotel.hotel_id = room.hotel_id "
				+ "WHERE beds = '" + bedNumber + "' AND " + "smoking = '" + smokingStatus + "' AND " + "room_size = '" + roomSize + "' AND " + "view = '" + view + "' AND " + "city = '" + campus + "' ";
		
		ResultSet res = con.retrieveData(get_room_query);
		
		while(res.next()){
	           
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=res.getMetaData().getColumnCount(); i++){
               
                row.add(res.getString(i));
            }
            
            list.add(row);
        }
		
		return list; 
	}
	
	public ObservableList<ReservationBean> getReservationsByDate(Date start, Date end) throws Exception{
		
		ObservableList<ReservationBean> list = FXCollections.observableArrayList();
		
		String get_reservations_query = "SELECT guest.first_name, guest.last_name, hotel.name, room.room_number, room.price, room.beds, room.smoking, room.room_size, room.view, arrival_date, departure_date "
				+ "FROM reservation "
				+ "JOIN guest ON guest.guest_id = reservation.guest_id "
				+ "JOIN hotel ON hotel.hotel_id = reservation.hotel_id "
				+ "JOIN room ON room.room_id = reservation.room_id "
				+ "WHERE reservation.arrival_date >= '" + start + "' AND " + "reservation.departure_date <= '" + end + "' "
						+ "ORDER BY reservation.arrival_date";
		
		ResultSet res = con.retrieveData(get_reservations_query);
		
		while(res.next()){
	           
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=res.getMetaData().getColumnCount(); i++){
               
                row.add(res.getString(i));
            }
            
            list.add(row);
        }
		
		return list; 
		
	}
	
	public ReservationBean getReservationByGuestNameandDates(String guestName,String surname,String idNumber,Date start,Date end) throws Exception{
		
		String get_reservation_query = "SELECT room.room_number, room.price, room.beds, room.smoking, room.room_size, room.view, hotel.name, room.adjointsTo, "
				+ "guest.first_name, guest.last_name, guest.date_of_birth, guest.email, guest.phone, guest.id_type, guest.id_number, "
				+ "reservation.arrival_date, reservation.departure_date, reservation.isCheckedIn, reservation.isCheckedOut "
				+ "FROM reservation "
				+ "JOIN guest ON guest.guest_id = reservation.guest_id "
				+ "JOIN hotel ON hotel.hotel_id = reservation.hotel_id "
				+ "JOIN room ON room.room_id = reservation.room_id "
				+ "WHERE first_name = '" + guestName + "' AND " + "last_name = '" + surname + "' AND " + "id_number = '" + idNumber + "' AND " + "arrival_date = '" + start + "' AND " + "departure_date = '" + end + "'";
		
		ArrayList<Object> reservationArrList = con.getArrData(get_reservation_query);
		
		String roomNumber = (String) reservationArrList.get(0);
		int roomPrice = (int) reservationArrList.get(1);
		int bedNumber = (int) reservationArrList.get(2);
		String smokingStatus = (String) reservationArrList.get(3);
		String roomSize = (String) reservationArrList.get(4);
		String view = (String) reservationArrList.get(5);
		String campus = (String) reservationArrList.get(6);
		String adjointsTo = (String) reservationArrList.get(7);
		
		String firstName = (String) reservationArrList.get(8);
		String lastName = (String) reservationArrList.get(9);
		Date dateOfBirth = (Date) reservationArrList.get(10);
		String email = (String) reservationArrList.get(11);
		String phoneNumber = (String) reservationArrList.get(12);
		String idType = (String) reservationArrList.get(13);
		String idNum = (String) reservationArrList.get(14);
		Date startDate = (Date) reservationArrList.get(15);
		Date endDate = (Date) reservationArrList.get(16);
		boolean isCheckedIn = (boolean) reservationArrList.get(17);
		boolean isCheckedOut = (boolean) reservationArrList.get(18);
		
		RoomBean room = new RoomBean(roomNumber, roomPrice, bedNumber, smokingStatus, roomSize, view, campus, adjointsTo);
		Guest guest = new Guest(firstName, lastName, dateOfBirth, email, phoneNumber, idType, idNum);
		
		ReservationBean reservation = new ReservationBean(room, guest, startDate, endDate, isCheckedIn, isCheckedOut);
		return reservation;
	}
	
	public Guest findGuest(String guestName,String surname,String idNumber) throws Exception{
		
		String guest_search_query = "SELECT first_name, last_name, date_of_birth, email, phone, id_type, id_number "
				+ "FROM guest WHERE first_name = '" + guestName + "' AND " + "last_name = '" + surname + "' AND " + "id_number = '" + idNumber + "'";	
		
		ArrayList<Object> guestArrList = con.getArrData(guest_search_query);
		
		String firstName = (String) guestArrList.get(0);
		String lastName = (String) guestArrList.get(1);
		Date dateOfBirth = (Date) guestArrList.get(2);
		String email = (String) guestArrList.get(3);
		String phoneNumber = (String) guestArrList.get(4);
		String idType = (String) guestArrList.get(5);
		String idNum = (String) guestArrList.get(6);
				
		Guest guest = new Guest(firstName, lastName, dateOfBirth, email, phoneNumber, idType, idNum);
		
		return guest;
	}
	
	public void createRoom(RoomBean room){
		//here instead of a campus property, for the database's sake better to make hotel ID (int hotelId = 1 for Kalmar, int hotelId = 2 for Växjö)
		//so we need to add additional field or selection for the hotel ID, also updating and deleting rooms requires room ID that gets automatically 
		//assigned to a room after it's been created
		
		
		String create_room_query = "INSERT INTO room (hotel_id, room_number, price, beds, smoking, room_size, view) "
				+ "VALUES ('" + room.getHotelId() + "','" + room.getRoomNumber() + "','"+ room.getRoomPrice() + "','" + room.getBedNumber() + "','"+ room.getSmokingStatus() + "','"+ room.getRoomSize() + "','" + room.getView() + "','"+ room.getAdjointsTo() +"')";
		
		con.updateData(create_room_query);
		
	}
	
	public void updateRoom(RoomBean room){
		
		// updating rooms requires the room ID's value (TODO: room.getRoomId() in RoomBean)
		
		String update_room_query = "UPDATE room SET room_number = '" + room.getRoomNumber() + "', price = '" + room.getRoomPrice() + "', "
				+ "beds = '" + room.getBedNumber() + "', smoking = '" + room.getSmokingStatus() + "', room_size = '" + room.getRoomSize() + "', view = '" + room.getView() + "', adjointsTo = '" + room.getAdjointsTo() +"'"
									+ "WHERE room_id = '"+ room.getRoomId() + "'"; 
		
		con.updateData(update_room_query);
	   
	}
	
	public void deleteRoom(RoomBean room){
		
		// deleting rooms requires the room ID's value (TODO: room.getRoomId() in RoomBean)
		
		String delete_room_query = "DELETE FROM room WHERE room_id = " + room.getRoomId();
		con.updateData(delete_room_query);

	}*/
}
