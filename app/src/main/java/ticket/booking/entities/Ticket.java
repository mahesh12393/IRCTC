package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    @JsonProperty("ticket_id")
    private String ticketId;

    @JsonProperty("user_id")
    private String userId;
    private String source;
    private String destination;

    @JsonProperty("date_of_travel")
    private String dateOfTravel;
    private Train train;


    public Ticket(){

    }

    //constructor
    public Ticket(String ticketId, String userId, String source, String destination, String dateOfTravel, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    //getter functions


    public String getTicketId() {
        return ticketId;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getUserId() {
        return userId;
    }

    public Train getTrain() {
        return train;
    }

    //setter functions


    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //other functionality as needed
    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User: %s to go from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }
}