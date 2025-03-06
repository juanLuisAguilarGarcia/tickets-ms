package com.dvp.infra.api.router.controller;

import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.api.router.controller.dto.GenericResponseDTO;
import com.dvp.infra.api.router.controller.dto.request.CreateAndUpdateTicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import com.dvp.infra.api.router.controller.error.exception.TicketException;
import com.dvp.infra.api.router.controller.mapper.TicketDtoMapper;
import com.dvp.infra.api.router.RouterConsts;
import com.dvp.infra.api.router.facade.TicketsFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.dvp.infra.api.router.RouterConsts.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@CrossOrigin(RouterConsts.CROSS_ORIGIN)
@RestController
@RequestMapping(path = RouterConsts.CONTROLLER_PATH)
@Tag(name = RouterConsts.API)
public class TicketsController {

    @Autowired
    private TicketsFacade ticketsFacade;

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = RouterConsts.API_OPERATION_CREATE_TICKET, description = RouterConsts.NOTE_API_OPERATION_CREATE_TICKET)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
                    content =  { @Content( schema = @Schema(implementation =  TicketDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<TicketDto> createTicket(
            @Parameter(description = API_PARAM_REQUEST_CREATE_TICKET, required = true) @Valid @RequestBody(required = true) CreateAndUpdateTicketDto ticketDto) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "create",  ticketDto.getUserId()));

        TicketDto response = ticketsFacade.createTicket(TicketDtoMapper.toEntity(ticketDto));

        log.info(String.format(MSG_PROCESS, "end", "create",  ticketDto.getUserId()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = RouterConsts.API_OPERATION_GET_TICKET_BY_ID, description = RouterConsts.NOTE_API_OPERATION_GET_BY_ID_TICKET)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
            content =  { @Content( schema = @Schema(implementation =  TicketDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<TicketDto> getTikectById(
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET, required = true) @PathVariable(name = PARAM_TICKET_ID ) Long ticketId) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "get",  ticketId));

        TicketDto response = ticketsFacade.getTicketById(ticketId);

        log.info(String.format(MSG_PROCESS, "end", "get",  ticketId));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = API_OPERATION_GET_PAGINATION_TICKTES, description = NOTE_API_OPERATION_GET_PAGINATION_TICKETS)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
            content =  { @Content( schema = @Schema(implementation =  TicketPaginationDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<TicketPaginationDto> getTikects(
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET_PAGE, required = true) @RequestParam(name = PARAM_TICKET_PAGE ) Integer page,
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET_SIZE_PAGE, required = true) @RequestParam(name = PARAM_TICKET_SIZE) Integer size
    ) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "get", " all pagination tickets"));

        TicketPaginationDto response = ticketsFacade.getTickets(page-1, size);

        log.info(String.format(MSG_PROCESS, "end", "get", "all pagination tickets"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = API_OPERATION_DELETE_TICKET, description = NOTE_API_OPERATION_DELETE_TICKET)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
            content =  { @Content( schema = @Schema(implementation =  GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<GenericResponseDTO> deleteTicket(
            @Parameter(description = RouterConsts.API_PARAM_REQUEST_GET_TICKET, required = true) @PathVariable(name = PARAM_TICKET_ID) Long tikectId) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "delete",  tikectId));

        ticketsFacade.deleteTicket(tikectId);

        log.info(String.format(MSG_PROCESS, "end", "delete",  tikectId));
        return ResponseEntity.ok(new GenericResponseDTO(String.valueOf(HttpStatus.OK.value()), MSG_CONFIRMATION_DELETE));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = API_OPERATION_UPDATE_TICKET, description = NOTE_API_OPERATION_UPDATE_TICKET)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
            content =  { @Content( schema = @Schema(implementation =  TicketDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<TicketDto> updateTicket(
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET, required = true) @PathVariable(name = PARAM_TICKET_ID) Long ticketId,
            @Parameter(description = API_PARAM_REQUEST_UPDATE_TICKET, required = true) @Valid @RequestBody(required = true) CreateAndUpdateTicketDto ticketDto) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "update",  ticketId));

        TicketDto response = ticketsFacade.updateTicket(TicketDtoMapper.updateToEntity(ticketDto, ticketId));

        log.info(String.format(MSG_PROCESS, "init", "update",  ticketId));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/filter", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = API_OPERATION_UPDATE_TICKET, description = NOTE_API_OPERATION_UPDATE_TICKET)
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = RouterConsts.API_RESPONSE_COD_200,
            content =  { @Content( schema = @Schema(implementation =  TicketDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = RouterConsts.API_RESPONSE_COD_400,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = RouterConsts.API_RESPONSE_COD_404,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = RouterConsts.API_RESPONSE_COD_422,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = RouterConsts.API_RESPONSE_COD_500,
                    content =  { @Content( schema = @Schema(implementation = GenericResponseDTO.class), mediaType = APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<TicketDto> getByFilter(
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET_FILTER_USER_ID) @RequestParam(name = PARAM_TICKET_USER_ID, required = false) Long userId,
            @Parameter(description = API_PARAM_REQUEST_GET_TICKET_FILTER_STATUS) @RequestParam(name = PARAM_TICKET_STATUS, required = false) StatusEnum status) throws TicketException {
        log.info(String.format(MSG_PROCESS, "init", "get tikect by filter",  userId));

        TicketDto response = ticketsFacade.getByFilter(userId, status );

        log.info(String.format(MSG_PROCESS, "init", "get tikect by filter",  userId));
        return ResponseEntity.ok(response);
    }
}
