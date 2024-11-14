package cinema.rest.cinema;

import cinema.domain.auth.PasswordValidationService;
import cinema.domain.seats.SeatsService;
import cinema.domain.statistics.StatisticsService;
import cinema.domain.tickets.Ticket;
import cinema.domain.tickets.TicketsService;
import cinema.rest.seats.PurchaseSeatBodyDTO;
import cinema.rest.seats.SeatDTO;
import cinema.rest.statisctics.StatisticsResponseDTO;
import cinema.rest.tickets.dto.ReturnTicketBodyDTO;
import cinema.rest.tickets.dto.ReturnTicketResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(controllers = CinemaController.class)
class CinemaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SeatsService seatsService;

    @MockBean
    TicketsService ticketsService;

    @MockBean
    StatisticsService statisticsService;

    @MockBean
    PasswordValidationService passwordValidationService;

    @Test
    void purchaseSeat_shouldReturnTicket() throws Exception {
        Ticket ticket = new Ticket(new SeatDTO(1,1,123));
        PurchaseSeatBodyDTO purchaseSeatBodyDTO = new PurchaseSeatBodyDTO(1,1);

        Mockito.when(ticketsService.purchaseTicket(purchaseSeatBodyDTO.getRow(), purchaseSeatBodyDTO.getColumn())).thenReturn(ticket);

        mvc.perform(
                post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseSeatBodyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket.row").value(1))
                .andExpect(jsonPath("$.ticket.column").value(1))
                .andDo(print());

    }

    @Test
    void returnTicket_shouldReturnSuccess() throws Exception {
        ReturnTicketBodyDTO returnTicketBodyDTO = new ReturnTicketBodyDTO();
        ReturnTicketResponseDTO returnTicketResponseDTO = new ReturnTicketResponseDTO(new SeatDTO(1, 1, 123));

        Mockito.when(ticketsService.returnTicket(returnTicketBodyDTO.getToken())).thenReturn(returnTicketResponseDTO);

        mvc.perform(post("/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnTicketBodyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket.row").value(1))
                .andExpect(jsonPath("$.ticket.column").value(1))
                .andDo(print());
    }

    @Test
    void getAllStats_shouldReturnStatistics() throws Exception {
        String password = "valid-password";
        StatisticsResponseDTO statisticsResponseDTO = new StatisticsResponseDTO(BigDecimal.valueOf(100), 10, 90);

        Mockito.doNothing().when(passwordValidationService).validatePassword(password);
        Mockito.when(statisticsService.getStatistics()).thenReturn(statisticsResponseDTO);

        mvc.perform(get("/stats")
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.income").value(100))
                .andExpect(jsonPath("$.available").value(10))
                .andExpect(jsonPath("$.purchased").value(90))
                .andDo(print());
    }
}