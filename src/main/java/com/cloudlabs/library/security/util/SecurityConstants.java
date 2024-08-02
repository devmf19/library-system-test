package com.cloudlabs.library.security.util;

public class SecurityConstants {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BAD_CREDENTIALS = "El usuario o contraseña son incorrectos";
    public static final String BEARER = "Bearer ";
    public static final int BEARER_LENGTH = 7;
    public static final String CREATED_USER = "Usuario registrado";
    public static final String DENIED_ACCESS = "Acceso denegado";
    public static final String DISABLED_USER = "Usuario inhabilitado";
    public static final String EMPTY_TOKEN = "Token vacío";
    public static final String ERROR = "Error: ";
    public static final String EXPIRATION_TOKEN = "${jwt.expirationInMs}";
    public static final String EXPIRED_TOKEN = "Token expirado";
    public static final String INVALID_TOKEN = "Token inválido";
    public static final String NOT_FOUND_USERNAME = "No se ha encontrado el usuario ";
    public static final String TOKEN_SIGNATURE_ERROR = "Error en la firma del token";
    public static final String REQUIRED_PASSWORD = "Debe ingresar una contraseña";
    public static final String REQUIRED_USERNAME = "Debe ingresar un usuario";
    public static final String ROLES = "roles";
    public static final String SUCCESS_LOGIN = "Ha iniciado sesión correctamente";
    public static final String SECRET_TOKEN = "${jwt.secret}";
    public static final String UNAUTHORIZED = "No autorizado";
    public static final String UNSUPPORTED_TOKEN = "Token no soportado";
}
