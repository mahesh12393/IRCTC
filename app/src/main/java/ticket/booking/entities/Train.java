package ticket.booking.entities;

import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats; // seat matrix
    private Map<String, String> stationTimes; // train station timings
    private List<String> stations;

    //constructor
    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String,String> stationTimes, List<String> stations){
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    //getter functions


    public String getTrainId() {
        return trainId;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public List<String> getStations() {
        return stations;
    }

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }


    //setter functions for all the properties

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    //other functionality as needed
    public String getTrainInfo(){
        return String.format("Train Id: %s and Train No: %s", trainId, trainNo);
    }
}