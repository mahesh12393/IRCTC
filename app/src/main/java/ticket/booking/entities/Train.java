package ticket.booking.entities;

import java.util.Map;
import java.util.List;
import java.time.LocalTime;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats; // seat matrix
    private Map<String, LocalTime> stationTimes; // train station timings
    private List<String> stations;
}