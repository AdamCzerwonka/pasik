package com.example.pasik.jws;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObjectJSON;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class Jws {
    RSAKey rsaJWK;
    RSAKey publicKey;

    public Jws() {
        try {
            this.rsaJWK = new RSAKeyGenerator(2048)
                    .algorithm(JWSAlgorithm.RS256)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyID("1")
                    .generate();

            this.publicKey = rsaJWK.toPublicJWK();
        } catch (Exception ignored) {
        }

    }
    public String sign(String unsigned) {
        try {
            Payload payload = new Payload(unsigned);
            JWSObjectJSON jwsObjectJSON = new JWSObjectJSON(payload);
            jwsObjectJSON.sign(new JWSHeader.Builder((JWSAlgorithm) rsaJWK.getAlgorithm()).keyID(rsaJWK.getKeyID()).build(), new RSASSASigner(rsaJWK));
            return jwsObjectJSON.serializeGeneral();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verify(String json) {
        try {
            JWSObjectJSON jwsObjectJSON = JWSObjectJSON.parse(json);
            JWSObjectJSON.Signature signature = jwsObjectJSON.getSignatures().getFirst();

            if (publicKey.getKeyID().equals(signature.getHeader().getKeyID())) {
                if (!signature.verify(new RSASSAVerifier(publicKey))) {
                    return false;
                }
            }

            return JWSObjectJSON.State.VERIFIED.equals(jwsObjectJSON.getState());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyWithUnsigned(String json, String unsigned) {
        if (!verify(json)) {
            return false;
        }

        String signed = sign(unsigned);
        return json.equals(signed);
    }
}
