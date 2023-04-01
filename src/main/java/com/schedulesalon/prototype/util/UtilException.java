package com.schedulesalon.prototype.util;

import jdk.jshell.execution.Util;

public class UtilException {

    // Variables
    private static final String MESSAGE_PARAM = "_PARAM_";
    private static final String SPACE = " ";

    // Hour
    public static final String INITIAL_HOUR_AND_FINAL_HOUR_ZERO_OR_EQUALS = "Cadastro de horários inválido. Horários de turnos iguais ou zerados";
    public static final String DAY_BIGGER_THAN_THE_MAX_OF_THE_MONTH = "Dia está maior que 31.";
    //TODO : Melhorar essa mensagem diminuindo os MESSAGE_PARAM
    public static final String DATE_CREATED_SMALLER_THAN_ACTUAL_WITH_PARAM = "A data ".concat(MESSAGE_PARAM).concat(" / ").concat(MESSAGE_PARAM).concat(" / ").concat(MESSAGE_PARAM).concat(" as ").concat(MESSAGE_PARAM).concat(" horas e ").concat(MESSAGE_PARAM).concat(" minutos está menor que a data atual");
    public static final String INTERVAL_BIGGER_THAN_INITIAL_AND_FINAL_HOUR_DIFFERENCE = "Intervalo maior que horario de inicio e fim";
    public static final String INITIAL_HOUR_SMALLER_THAN_FINAL_HOUR_WITH_PARAM = MESSAGE_PARAM.concat(" menor que ").concat(MESSAGE_PARAM);
    public static final String INITIAL_HOUR_BIGGER_THAN_FINAL_HOUR = "Cadastro de horários inválido. Horário inicial maior que horario final";
    public static final String INVALID_HOURS_TO_ADD = "Horários inválidos para cadastrar";

    //Professional
    public static final String PROFESSIONAL_ALREADY_HAVE_THIS_HOUR_WITH_PARAM = "O profissional ".concat(MESSAGE_PARAM).concat(" já possui o horário ").concat(MESSAGE_PARAM);

    // Salon
    public static final String CANT_INSERT_A_PROFESSIONAL_SALON_DOESNT_HAVE_A_MANAGER = "Não pode inserir o profissional. O Salão não possui um gerente.";
    public static final String THIS_SALON_ALREADY_HAVE_THIS_MANAGER = "Esse salão já possui esse gerente";

    // Roles
    public static final String PERSON_ALREADY_HAS_THIS_ROLE = "Essa pessoa já possui o cargo de ".concat(MESSAGE_PARAM);
    public static final String ROLES_WITH_COUNT_ZERO = "Nâo foram passados cargos como parâmetros.";
    public static final String ROLE_NOT_FOUND = "Cargo não encontrado.";
    public static final String ROLE_CLIENT_AMONG_OTHER_ROLES = "Cargo de cliente não pode ser adicionado junto de outros cargos.";

    // Util
    public static final String ALL_PARAMS_ARE_NOT_FILLED = "Todos os parâmetros não estão preenchidos.";
    public static final String ALL_PARAMS_ARE_NOT_FILLED_WITH_PARAM = "Todos os parâmetros não estão preenchidos em: ".concat(MESSAGE_PARAM);
    public static final String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA = "Já existe um registro com esses dados.";
    public static final String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM = "Já existe um/uma ".concat(MESSAGE_PARAM).concat(" com esses dados.");

    // User
    public static final String USER_NOT_FOUND = "Usuário não encontrado.";

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
            if(word.equals(UtilException.MESSAGE_PARAM)) {
                builder.append(params[paramsIndex].concat(UtilException.SPACE));
                paramsIndex += 1;
            } else {
                builder.append(word.concat(UtilException.SPACE));
            }
        }
        return builder.toString();
    }
}
