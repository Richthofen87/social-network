package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private final TokenStore tokenStore;

    public boolean revokeUserTokensByEmail(String email) {
        try {
            tokenStore.findTokensByClientIdAndUserName("client", email).forEach(token -> {
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(token.getValue());
                if (accessToken != null) {
                    tokenStore.removeAccessToken(accessToken);
                    if (accessToken.getRefreshToken() != null) {
                        tokenStore.removeRefreshToken(accessToken.getRefreshToken());
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean revokeAllTokens() {
        try {
            Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("ID");
            tokens.forEach(tokenStore::removeAccessToken);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

}
