package com.schedulesalon.prototype.util;

public class UtilException {

    private static final String MESSAGE_PARAM = "{}";
    private static final String SPACE = " ";

    public static final String ROLES_WITH_COUNT_ZERO = "Nâo foram passados cargos como parâmetros.";
    public static final String ALL_PARAMS_ARE_NOT_FILLED = "Todos os parâmetros não estão preenchidos.";
    public static final String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA = "Já existe um registro com esses dados.";
    public static final String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM = "Já existe um/uma ".concat(MESSAGE_PARAM).concat(" com esses dados.");
    public static final String USER_NOT_FOUND = "Usuário não encontrado.";
    public static final String ROLE_NOT_FOUND = "Cargo não encontrado.";
    public static final String ROLE_CLIENT_AMONG_OTHER_ROLES = "Cargo de cliente não pode ser adicionado junto de outros cargos.";
    public static final String PERSON_ALREADY_HAS_THIS_ROLE = "Essa pessoa já possui o cargo de ".concat(MESSAGE_PARAM);

    public static void throwDefault(String messageKey) throws Exception {
        throw new Exception(messageKey);
    }

    public static String ExceptionBuilder(String message, String[] params) {
        //TO DO: Check if message is null
        //TO DO: Check if params is equal to message params
        StringBuilder builder = new StringBuilder();
        String[] messageWords = message.split(UtilException.SPACE);
        int paramsIndex = 0;

        for(String word: messageWords) {
            if(word == UtilException.MESSAGE_PARAM) {
                builder.append(params[paramsIndex]);
                paramsIndex += 1;
            } else {
                builder.append(message);
            }
        }
        return builder.toString();
    }
}
