package com.tis.elac.conf;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;



@Configuration
public class ElasticsearchConfig extends ElasticsearchConfigurationSupport {

    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
            Arrays.asList(
              
          //     Static class initilization 
                new ZonedDateTimeWritingConverter(),
                new ZonedDateTimeReadingConverter(),
                new InstantWritingConverter(),
                new InstantReadingConverter(),
                new LocalDateWritingConverter(),
                new LocalDateReadingConverter()
            )
        );
    }

  @ReadingConverter
    static class LocalDateReadingConverter implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            if (source == null) {
                return null;
            }
            return LocalDate.parse(source);
        }
    }
  
   @WritingConverter
    static class LocalDateWritingConverter implements Converter<LocalDate, String> {

        @Override
        public String convert(LocalDate source) {
            if (source == null) {
                return null;
            }
            return source.toString();
        }
    }
  
    @WritingConverter
    static class ZonedDateTimeWritingConverter implements Converter<ZonedDateTime, String> {

        @Override
        public String convert(ZonedDateTime source) {
            if (source == null) {
                return null;
            }
            return source.toInstant().toString();
        }
    }

    @ReadingConverter
    static class ZonedDateTimeReadingConverter implements Converter<String, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(String source) {
            if (source == null) {
                return null;
            }
            return Instant.parse(source).atZone(ZoneId.systemDefault());
        }
    }

    @WritingConverter
    static class InstantWritingConverter implements Converter<Instant, String> {

        @Override
        public String convert(Instant source) {
            if (source == null) {
                return null;
            }
            return source.toString();
        }
    }

    @ReadingConverter
    static class InstantReadingConverter implements Converter<String, Instant> {

        @Override
        public Instant convert(String source) {
            if (source == null) {
                return null;
            }
            return Instant.parse(source);
        }
    }
    
}
