## CSV Loader

Some Java classes to allow you to import data from multiple CSV files into MarkLogic Server

### Example: Loading the MaxMind GeoLite2 data

- Go to: http://dev.maxmind.com/geoip/geoip2/geolite2/
- Download the city file: http://geolite.maxmind.com/download/geoip/database/GeoLite2-City-CSV.zip
- Download the country file: http://geolite.maxmind.com/download/geoip/database/GeoLite2-Country-CSV.zip
- Unpack the zip file(s)
- Edit Config.java **BASE_DATA_DIR** and the **MARKLOGIC_XCC_URI** to add your path and to set your MarkLogic Database for the content.
- Run **LoadCSVs.java**

You should see something like this:

```
16:33:46.922 [main] INFO LoadCSVs - Starting Processing
16:33:46.925 [main] DEBUG LoadCSVs - Number of processors: 8
16:33:46.991 [pool-1-thread-1] INFO CSVFileProcessor - Processing file: GeoLite2-City-Blocks-IPv4.csv
16:33:46.991 [pool-1-thread-2] INFO CSVFileProcessor - Processing file: GeoLite2-City-Blocks-IPv6.csv
16:33:46.991 [pool-1-thread-3] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-de.csv
16:33:46.992 [pool-1-thread-4] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-en.csv
16:33:46.992 [pool-1-thread-5] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-es.csv
16:33:46.992 [pool-1-thread-6] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-fr.csv
16:33:46.992 [pool-1-thread-7] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-ja.csv
16:33:46.992 [pool-1-thread-8] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-pt-BR.csv
16:33:46.992 [pool-1-thread-9] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-ru.csv
16:33:46.993 [pool-1-thread-10] INFO CSVFileProcessor - Processing file: GeoLite2-City-Locations-zh-CN.csv
16:33:47.005 [pool-1-thread-2] DEBUG MarkLogicSessionProvider - Initialising ContentSource
```

This will create a large number of XML documents in your database (> 10M if you load both the City and Country CSV sets). An example of the XML output in MarkLogic includes:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<GeoLite2-City-Locations-en>
  <continent_name>Europe</continent_name>
  <subdivision_2_name/>
  <continent_code>EU</continent_code>
  <subdivision_1_iso_code>ENG</subdivision_1_iso_code>
  <time_zone>Europe/London</time_zone>
  <geoname_id>2643743</geoname_id>
  <subdivision_2_iso_code/>
  <locale_code>en</locale_code>
  <city_name>London</city_name>
  <country_iso_code>GB</country_iso_code>
  <metro_code/>
  <country_name>United Kingdom</country_name>
  <subdivision_1_name>England</subdivision_1_name>
</GeoLite2-City-Locations-en>
```