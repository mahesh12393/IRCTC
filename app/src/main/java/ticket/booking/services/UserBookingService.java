package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.FileResourceUtil;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserBookingService {
    private User user;

    private List<User> userList;
    private static final String USERS_PATH="/ticket/booking/localDb/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService() throws IOException{
//        loadUsers();
        try {
            // Your file loading code here
           List<User> userList = loadUsers();
            System.out.println("Loaded " + userList.size() + " users"); // Debug line
        } catch (Exception e) {
            System.out.println("Error in UserBookingService constructor: " + e.getMessage());
            e.printStackTrace(); // This will show the full stack trace
            throw new IOException("Failed to initialize UserBookingService", e);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBookingService(User fetched_user) throws IOException {
        this.user = fetched_user;
        loadUsers();
    }

    public List<User> loadUsers() throws IOException{
        File users_info = FileResourceUtil.getUsersFile();
        //mapping data of local db of snake case to camelCase -> serialization
        userList = objectMapper.readValue(users_info, new TypeReference<List<User>>() {});
        return userList;
    }

    public Optional<User> getUserByName(String name) throws IOException{
        List<User> users_list = loadUsers();
        return users_list.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    public List<Train> getTrains(String source, String destination) throws IOException{
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        } catch (Exception ex){
            System.out.println("Can't get the list , getting this error of " + ex);
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train_selected){
        return train_selected.getSeats();
    }

    public Boolean bookTrainSeat(Train train_selected, int row, int col){
        List<List<Integer>> seat_matrix = fetchSeats(train_selected);
        if(seat_matrix.get(row).get(col) == 1){
            return Boolean.FALSE;
        }
        User current_user = this.user;
        List<Ticket> existing_tickets = current_user.getTicketsBooked();
        List<String> stations_list = train_selected.getStations();
        String date_of_travel = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String ticket_id = UUID.randomUUID().toString();
        Ticket created_ticket = new Ticket(ticket_id,this.user.getUserId(),stations_list.get(0),
                stations_list.get(stations_list.size()-1),date_of_travel,train_selected);

        existing_tickets.add(created_ticket);
        return Boolean.TRUE;
    }

    public Boolean loginUser(){
        // using optional here as so when user is not there it won;t throw the null pointer exception.
        Optional<User> foundUser = userList.stream().filter(user1 ->{
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        return foundUser.isPresent();
    }


    public Boolean signUp(User user1) throws IOException{
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            System.out.println("Getting this error while signinig up user of " + ex);
            return Boolean.FALSE;
        }
    }


    public void saveUserListToFile() throws IOException{
        File users_info_file = FileResourceUtil.getUsersFile();
        objectMapper.writeValue(users_info_file,userList);
    }

    public void fetchBookings(){
        this.user.printTickets();
    }

    public boolean cancelBooking(String ticketId) throws IOException{
        //to remove this ticket from the list of ticketsBooked and reupdate the local db file.

        List<Ticket> user_booked_tickets = this.user.getTicketsBooked();
        boolean ticket_removed = user_booked_tickets.removeIf(ticket -> ticket.getTicketId().equals(ticketId));

        if(!ticket_removed){
            //when ticket does not even exist
            return false;
        }

        //updating the local db file.
        List<Ticket> updated_booked_tickets = this.user.getTicketsBooked();
        String user_id = this.user.getUserId();

        File users_info_file = FileResourceUtil.getUsersFile();

        List<User> users_list = loadUsers();

        for(User user1 : users_list){
            if (user1.getUserId().equals(user_id)){
                user1.setTicketsBooked(updated_booked_tickets);
            }
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(users_info_file,users_list);

        // if everything is success
        return true;
    }

}