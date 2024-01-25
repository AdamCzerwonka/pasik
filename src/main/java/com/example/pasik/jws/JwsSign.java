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

public class JwsSign {
    RSAKey rsaJWK;
    RSAKey publicKey;

    public JwsSign() {
        try {
            rsaJWK = new RSAKeyGenerator(2048)
                    .algorithm(JWSAlgorithm.RS256)
                    .keyUse(KeyUse.SIGNATURE)
                    .keyID("1")
                    .generate();

            publicKey = rsaJWK.toPublicJWK();
        } catch (Exception e) {
        }

    }
    public String sign(String toSign) {
        try {
            Payload payload = new Payload(toSign);
            JWSObjectJSON jwsObjectJSON = new JWSObjectJSON(payload);
            jwsObjectJSON.sign(new JWSHeader.Builder((JWSAlgorithm) rsaJWK.getAlgorithm()).keyID(rsaJWK.getKeyID()).build(), new RSASSASigner(rsaJWK));
            return jwsObjectJSON.serializeGeneral();
        } catch (Exception e) {
        }

        return null;
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
        }

        return false;
    }
}
