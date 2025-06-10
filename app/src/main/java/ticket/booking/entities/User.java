package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User{
    private String name;
    private String password;
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("hashed_password") // if your JSON has this field
    private String hashedPassword;

    @JsonProperty("tickets_booked")
    private List<Ticket> ticketsBooked;


    public User(String userId, String name, String password, String hashedPassword, List<Ticket> ticketsBooked){
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
    }

    public User(){
    //default constructor for placeholder
    }

    //getter functions
    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public String getUserId(){
        return userId;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void printTickets(){
        for(int i=0;i<ticketsBooked.size();i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }

    //setter functions
    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked){
        this.ticketsBooked = ticketsBooked;
    }

}