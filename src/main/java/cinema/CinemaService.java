package cinema;


import cinema.seats.Seat;
import cinema.seats.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
    @Autowired
    SeatsService seatsService;

    public Seat purchaseTicket(int row, int col) {
        return seatsService.purchaseSeat(row, col);
    }
}
