package ticket.booking.utils;

import java.util.List;

public class TrainServiceUtil {

    public static int[] getIndices(List<String> array, String value1, String value2) {
        int index1 = -1;
        int index2 = -1;

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(value1)) {
                index1 = i;
            }
            if (array.get(i).equals(value2)) {
                index2 = i;
            }
        }

        return new int[]{index1, index2};
    }

}
