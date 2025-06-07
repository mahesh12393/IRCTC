package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.utils.TrainServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private Train train;

    private List<Train> trainsList;
    private static final String TRAINS_PATH="app/src/main/java/ticket/booking/localDb/trains.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public TrainService() throws IOException {
        loadTrains();
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public TrainService(Train fetched_train) throws IOException {
        this.train = fetched_train;
        loadTrains();
    }

    public List<Train> loadTrains() throws IOException{
        File trains_info = new File(TRAINS_PATH);
        //mapping data of local db of snake case to camelCase -> serialization
        trainsList = objectMapper.readValue(trains_info, new TypeReference<List<Train>>() {});
        return trainsList;
    }



    public List<Train> searchTrains(String source, String destination) throws IOException{
        List<Train> trains_list = loadTrains();

        List<Train> fetched_trains = trains_list.stream().filter((Train train)->{
            List<String> stations_list = train.getStations();
            int[] indices = TrainServiceUtil.getIndices(stations_list, source, destination);
            return indices[0] != -1 && indices[1] != -1 && indices[1] > indices[0];
        }).collect(Collectors.toList());

        return fetched_trains;
    }
}