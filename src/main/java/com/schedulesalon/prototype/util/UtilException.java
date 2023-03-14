package com.schedulesalon.prototype.util;

public class UtilException {

    public static final String ROLES_WITH_COUNT_ZERO = "Nâo foram passados cargos como parâmetros.";
    public static final String ALL_PARAMS_ARE_NOT_FILLED = "Todos os parâmetros não estão preenchidos.";
    public static final String THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA = "Já existe um registro com esses dados.";
    public static final String USER_NOT_FOUND = "Usuário não encontrado.";
    public static final String ROLE_NOT_FOUND = "Cargo não encontrado.";
    public static final String ROLE_CLIENT_AMONG_OTHER_ROLES = "Cargo de cliente não pode ser adicionado junto de outros cargos.";

    public static void throwDefault(String messageKey) throws Exception {
        throw new Exception(messageKey);
    }
}
