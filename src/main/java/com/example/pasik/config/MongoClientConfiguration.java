package com.example.pasik.config;

import com.example.pasik.utils.UUIDCodecProvider;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MongoClientConfiguration {
    @Bean
    public MongoClient getMongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "root".toCharArray());

        CodecRegistry pojoCodecRegistry =
                CodecRegistries.fromProviders(PojoCodecProvider.
                        builder().
                        automatic(true).
                        conventions(List.of(Conventions.ANNOTATION_CONVENTION)).
                        build());

        MongoClientSettings settings = MongoClientSettings
                .builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(new UUIDCodecProvider()),
                        pojoCodecRegistry)
                )
                .build();

        return MongoClients.create(settings);
    }
}
