package br.ufrgs.inf.pet.dinoapi.model.auth;

/**
 * Model para envio do token de acesso
 *
 * @author joao.silva
 */
public class AuthResponseModel {

    String accessToken;

    Long expireIn;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpireIn(Long expireIn) {
        this.expireIn = expireIn;
    }
}
