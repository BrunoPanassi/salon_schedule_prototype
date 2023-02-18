package com.schedulesalon.prototype.util;

public class UtilException {

    public static String ALL_PARAMS_ARE_NOT_FILLED = "Todos os parâmetros não estão preenchidos.";
    public static String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA = "Já existe um registro com esses dados.";
    public static String USER_NOT_FOUND = "Usuário não encontrado.";
    public static String ROLE_NOT_FOUND = "Cargo não encontrado.";

    public static void throwDefault(String messageKey) throws Exception {
        throw new Exception(messageKey);
    }
}
