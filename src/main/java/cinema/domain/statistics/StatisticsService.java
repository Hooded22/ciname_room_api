package cinema.domain.statistics;

import cinema.domain.seats.SeatsService;
import cinema.domain.tickets.TicketsService;
import cinema.rest.statisctics.StatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatisticsService {
    private final TicketsService ticketsService;
    private final SeatsService seatsService;

    @Autowired
    public StatisticsService(TicketsService ticketsService, SeatsService seatsService) {
        this.ticketsService = ticketsService;
        this.seatsService = seatsService;
    }

    @Cacheable(value = "statistics", key = "'full_stats'")
    public StatisticsResponse getStatistics() {
        BigDecimal income = this.getIncomeFromTickets();
        int available = this.getAvailableSeats();
        int purchased = this.getPurchasedTickets();

        return new StatisticsResponse(income, available, purchased);
    }

    private int getAvailableSeats() {
        return seatsService.getNumberOfFreeSeats();
    }

    private int getPurchasedTickets() {
        return ticketsService.getPurchasedTickets();
    }

    private BigDecimal getIncomeFromTickets() {
        return ticketsService.countTicketsIncome();
    }

}
