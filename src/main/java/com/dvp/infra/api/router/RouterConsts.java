package com.dvp.infra.api.router;

public class RouterConsts {

    private RouterConsts(){}
    /**
     * componentes
     */
    public static final String COMPONENT_SCAN = "com.dvp";

    /**
     * Controller config
     */
    public static final String API = "Tickets";
    public static final String CROSS_ORIGIN = "*";
    public static final String CONTROLLER_PATH = "/ticket";

    /**
     * operaciones o metodos
     */
    public static final String API_OPERATION_CREATE_TICKET = "Create a new ticket";
    public static final String API_OPERATION_GET_TICKET_BY_ID = "Get ticket by id information";
    public static final String API_OPERATION_UPDATE_TICKET = "Update ticket information";
    public static final String API_OPERATION_DELETE_TICKET = "Delete ticket information";
    public static final String API_OPERATION_GET_PAGINATION_TICKTES = "Get pagination tickets information";
    public static final String API_OPERATION_GET_FILTER_TICKTES = "Get filter tickets information";

    /**
     * descripcion de las operaciones o metodos
     */
    public static final String NOTE_API_OPERATION_CREATE_TICKET = "In charge of create a new ticket.";
    public static final String NOTE_API_OPERATION_GET_BY_ID_TICKET = "In charge of get ticket information filter by Id.";
    public static final String NOTE_API_OPERATION_UPDATE_TICKET = "In charge of update ticket information.";
    public static final String NOTE_API_OPERATION_DELETE_TICKET = "In charge of delete ticket information.";
    public static final String NOTE_API_OPERATION_GET_PAGINATION_TICKETS = "In charge of get pagination tickets.";
    public static final String NOTE_API_OPERATION_GET_FILTER_TICKETS = "In charge of get tickets filtered by status and user.";

    /**
     * mensajes de respuesta de acuerdo al codigo http
     */
    public static final String API_RESPONSE_COD_200 = "successful process";
    public static final String API_RESPONSE_COD_400 = "Some parameter is missing in the header";
    public static final String API_RESPONSE_COD_404 = "source not found.";
    public static final String API_RESPONSE_COD_422 = "Functional error in the application";
    public static final String API_RESPONSE_COD_500 = "Unknown error";

    /**
     * params
     */
    public static final String PARAM_TICKET_ID = "id";
    public static final String PARAM_TICKET_USER_ID = "user_id";
    public static final String PARAM_TICKET_STATUS = "status";
    public static final String PARAM_TICKET_PAGE = "page";
    public static final String PARAM_TICKET_SIZE = "size";

    /**
     * swagger param
     */
    public static final String API_PARAM_REQUEST_CREATE_TICKET = "Body mapped to CreateAndUpdateTicketDto.";
    public static final String API_PARAM_REQUEST_UPDATE_TICKET = "Body update mapped to CreateAndUpdateTicketDto.";
    public static final String API_PARAM_REQUEST_GET_TICKET = "Id of ticket.";
    public static final String API_PARAM_REQUEST_GET_TICKET_PAGE = "Number of page.";
    public static final String API_PARAM_REQUEST_GET_TICKET_SIZE_PAGE = "Size of result in page.";
    public static final String API_PARAM_REQUEST_GET_TICKET_FILTER_STATUS = "Status of ticket.";
    public static final String API_PARAM_REQUEST_GET_TICKET_FILTER_USER_ID = "Id of user.";

    /**
     * messages
     */
    public static final String MSG_PROCESS = "%s %s ticket: %s.";
    public static final String MSG_CONFIRMATION_DELETE = "Record deleted successful.";
}
