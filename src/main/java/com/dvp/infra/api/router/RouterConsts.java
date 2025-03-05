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
    public static final String API = "Users";
    public static final String CROSS_ORIGIN = "*";
    public static final String CONTROLLER_PATH = "/user";

    /**
     * operaciones o metodos
     */
    public static final String API_OPERATION_CREATE_USER = "Create a new user";
    public static final String API_OPERATION_GET_USER_BY_ID = "Get user by id information";
    public static final String API_OPERATION_UPDATE_USER = "Update user information";
    public static final String API_OPERATION_GET_ALL_USER = "Get all users information";

    /**
     * descripcion de las operaciones o metodos
     */
    public static final String NOTE_API_OPERATION_CREATE_USER = "In charge of create a new user.";
    public static final String NOTE_API_OPERATION_GET_BY_ID_USER = "In charge of get user information filter by Id.";
    public static final String NOTE_API_OPERATION_UPDATE_USER = "In charge of update user information.";
    public static final String NOTE_API_OPERATION_GET_ALL_USER = "In charge of get all users.";

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
    public static final String PARAM_USER_ID = "id";

    /**
     * swagger param
     */
    public static final String API_PARAM_REQUEST_CREATE_USER = "Body mapped to CreateUserDto.";
    public static final String API_PARAM_REQUEST_UPDATE_USER = "Body update mapped to CreateUserDto.";
    public static final String API_PARAM_REQUEST_GET_USER = "Id of user.";

    /**
     * messages
     */
    public static final String MSG_PROCESS = "%s %s user: %s.";
}
