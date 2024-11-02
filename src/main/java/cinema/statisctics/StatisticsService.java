package cinema.statisctics;

import cinema.seats.SeatsService;
import cinema.tickets.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatisticsService {
    @Autowired
    TicketsService ticketsService;
    @Autowired
    private SeatsService seatsService;

    public StatisticsResponseDTO getStatistics() {
        BigDecimal income = this.getIncomeFromTickets();
        int available = this.getAvailableSeats();
        int purchased = this.getPurchasedTickets();

        StatisticsResponseDTO statisticsDTO = new StatisticsResponseDTO(income, available, purchased);
        return statisticsDTO;
    }

    private BigDecimal getIncomeFromTickets() {
        return ticketsService.countTicketsIncome();
    }

    private int getAvailableSeats() {
        return seatsService.getNumberOfFreeSeats();
    }


    private int getPurchasedTickets() {
        return ticketsService.getPurchasedTickets();
    }

}
