package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;

    private List<User> userList;
    private static final String USERS_PATH="app/src/main/java/ticket/booking/localDb/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService() throws IOException{
        loadUsers();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBookingService(User fetched_user) throws IOException {
        this.user = fetched_user;
        loadUsers();
    }

    public List<User> loadUsers() throws IOException{
        File users_info = new File(USERS_PATH);
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
            return Boolean.FALSE;
        }
    }


    public void saveUserListToFile() throws IOException{
        File users_info_file = new File(USERS_PATH);
        objectMapper.writeValue(users_info_file,userList);
    }

    public void fetchBookings(){
        user.printTickets();
    }

    public boolean cancelBooking(String ticketId) throws IOException{
        //to remove this ticket from the list of ticketsBooked and reupdate the local db file.

        List<Ticket> user_booked_tickets = user.getTicketsBooked();
        boolean ticket_removed = user_booked_tickets.removeIf(ticket -> ticket.getTicketId().equals(ticketId));

        if(!ticket_removed){
            //when ticket does not even exist
            return false;
        }

        //updating the local db file.
        List<Ticket> updated_booked_tickets = user.getTicketsBooked();
        String user_id = user.getUserId();

        File users_info_file = new File(USERS_PATH);

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