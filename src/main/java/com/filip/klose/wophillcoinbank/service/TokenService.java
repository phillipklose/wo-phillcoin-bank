package com.filip.klose.wophillcoinbank.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {

    @Autowired
    private Logger logger;

    private static final String TOKEN_SECRET = "6sutmyx785at6vw0zfth";

    public String createToken(ObjectId userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create()
                    .withClaim("userId", userId.toString())
                    .withClaim("createdAt", new Date())
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException exception) {
            logger.error(String.format("Could not create MHAC256 with sercret key '%s'", TOKEN_SECRET));
        } catch (JWTCreationException exception) {
            logger.error("Could not sign JWT token with HMAC256 alghoritm");
        }
        return null;
    }

    public String getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (UnsupportedEncodingException exception) {
            logger.error("Wrong encoding message");
        } catch (JWTVerificationException exception) {
            logger.error("Token verification failed");
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        String userId = getUserIdFromToken(token);
        return userId != null;
    }

}
