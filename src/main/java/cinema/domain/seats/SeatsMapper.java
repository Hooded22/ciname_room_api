package cinema.domain.seats;

import java.util.List;
import java.util.stream.Collectors;

import cinema.rest.seats.SeatResponse;
import org.springframework.data.domain.Page;

public class SeatsMapper {
    
    public static List<SeatResponse> toResponseList(List<Seat> seats) {
        return seats.stream()
                .map(SeatsMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static SeatResponse toResponse(Seat seat) {
        return new SeatResponse(
            seat.getRow(),
            seat.getColumn(),
            seat.getPrice()
        );
    }
} 