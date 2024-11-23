package cinema.domain.tickets;

import cinema.domain.archivedTicket.ArchivedTicket;
import cinema.domain.archivedTicket.ArchivedTicketRepository;
import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsMapper;
import cinema.domain.seats.SeatsService;
import cinema.rest.tickets.ReturnTicketResponse;
import cinema.domain.exceptions.WrongTokenException;
import cinema.rest.tickets.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final ArchivedTicketRepository archivedTicketRepository;
    private final SeatsService seatsService;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository, SeatsService seatsService, ArchivedTicketRepository archivedTicketRepository) {
        this.ticketsRepository = ticketsRepository;
        this.seatsService = seatsService;
        this.archivedTicketRepository = archivedTicketRepository;
    }

    @Cacheable(value = "tickets", key = "#token")
    public Ticket getTicketByToken(UUID token) {
        return ticketsRepository.findTicketByToken(token.toString())
            .orElseThrow(() -> new WrongTokenException(ErrorMessages.WRONG_TOKEN.getMessage()));
    }

    @CacheEvict(value = {"tickets", "statistics"}, allEntries = true)
    public TicketResponse purchaseTicket(int row, int col) {
        Seat seat = seatsService.purchaseSeat(row, col);
        Ticket ticket = new Ticket();
        ticket.setTicketSeat(seat);

        Ticket addedTicket = ticketsRepository.save(ticket);

        return new TicketMapper().toTicketResponse(addedTicket);
    }

    @Caching(evict = {
        @CacheEvict(value = "tickets", key = "#token"),
        @CacheEvict(value = {"seats", "statistics"}, allEntries = true)
    })
    public ReturnTicketResponse returnTicket(UUID token) {
        Ticket deletedTicket = ticketsRepository.deleteTicketByToken(token.toString());

        if (deletedTicket == null) {
            throw new WrongTokenException(ErrorMessages.WRONG_TOKEN.getMessage());
        } else {
            ArchivedTicket archivedTicket = new TicketMapper().toArchiveTicket(deletedTicket);

            archivedTicketRepository.save(archivedTicket);
            seatsService.freeSeat(archivedTicket.getTicketSeat().getRow(), archivedTicket.getTicketSeat().getColumn());

            ReturnTicketResponse returnTicketResponse = new ReturnTicketResponse();
            returnTicketResponse.setTicketSeat(new SeatsMapper().toResponse(archivedTicket.getTicketSeat()));


            return returnTicketResponse;
        }
    }

    @Cacheable(value = "statistics", key = "'income'")
    public BigDecimal countTicketsIncome() {
        List<Ticket> allTickets = ticketsRepository.findAll();
        int ticketsPrice = allTickets.stream().mapToInt(item -> item.getTicketSeat().getPrice()).sum();

        return new BigDecimal(ticketsPrice);
    }

    @Cacheable(value = "statistics", key = "'purchased_count'")
    public int getPurchasedTickets() {
        return ticketsRepository.findAll().size();
    }

}
