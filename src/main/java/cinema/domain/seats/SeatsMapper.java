package cinema.domain.seats;

import java.util.List;
import java.util.stream.Collectors;

import cinema.rest.seats.SeatResponse;
import org.springframework.data.domain.Page;

public class SeatsMapper {
    
    public List<SeatResponse> toResponseList(List<Seat> seats) {
        return seats.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SeatResponse toResponse(Seat seat) {
        return new SeatResponse(
            seat.getRow(),
            seat.getColumn(),
            seat.getPrice()
        );
    }
} 