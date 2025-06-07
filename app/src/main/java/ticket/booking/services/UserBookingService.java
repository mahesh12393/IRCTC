package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserBookingService {
    private User user;

    private List<User> userList;
    private static final String USERS_PATH="../localDb/users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingService(User fetched_user) throws IOException {
        this.user = fetched_user;
        File users_info = new File(USERS_PATH);
        //mapping data of local db of snake case to camelCase
        userList = objectMapper.readValue(users_info, new TypeReference<List<User>>() {});
    }
}