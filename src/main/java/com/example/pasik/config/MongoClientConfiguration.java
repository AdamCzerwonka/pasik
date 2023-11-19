package com.example.pasik.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
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
    public MongoClient getMongoClient(MongoConfig mongoConfig) {
        ConnectionString connectionString = new ConnectionString(mongoConfig.getConnectionString());
        MongoCredential credential = MongoCredential
                .createCredential(
                        mongoConfig.getUsername(),
                        "admin",
                        mongoConfig.getPassword().toCharArray()
                );

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
                        pojoCodecRegistry)
                )
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase getMongoDatabase(MongoClient client, MongoConfig mongoConfig) {
        return client.getDatabase(mongoConfig.getDbName());
    }
}
