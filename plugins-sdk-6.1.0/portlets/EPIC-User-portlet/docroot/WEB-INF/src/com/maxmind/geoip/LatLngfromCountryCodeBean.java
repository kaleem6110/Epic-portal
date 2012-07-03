package com.maxmind.geoip;


import java.util.*;
import com.maxmind.geoip.LatLngCountryBean;

public class LatLngfromCountryCodeBean
    {
	
        private static Map<String, LatLngCountryBean> map = new HashMap<String,LatLngCountryBean>();
        
        private static Map<String, String> countryCodeMap = new HashMap<String,String>();

        private static void Add(LatLngCountryBean country)
        {
        	
            map.put(country.countryCode, country );
           
        }

        public static LatLngCountryBean getBean(String code)
        {
            LatLngCountryBean country;
			code = code.toUpperCase();
            if (map.containsKey(code))
                return map.get(code);

            return getBean("GB");

        } 

        // from http://www.maxmind.com/app/country_latlon
        public LatLngfromCountryCodeBean()
        {

            Add(new LatLngCountryBean("AD", 42.5000, 1.5000));

            Add(new LatLngCountryBean("AE", 24.0000, 54.0000));

            Add(new LatLngCountryBean("AF", 33.0000, 65.0000));

            Add(new LatLngCountryBean("AG", 17.0500, -61.8000));

            Add(new LatLngCountryBean("AI", 18.2500, -63.1667));

            Add(new LatLngCountryBean("AL", 41.0000, 20.0000));

            Add(new LatLngCountryBean("AM", 40.0000, 45.0000));

            Add(new LatLngCountryBean("AN", 12.2500, -68.7500));

            Add(new LatLngCountryBean("AO", -12.5000, 18.5000));

            Add(new LatLngCountryBean("AP", 35.0000, 105.0000));

            Add(new LatLngCountryBean("AQ", -90.0000, 0.0000));

            Add(new LatLngCountryBean("AR", -34.0000, -64.0000));

            Add(new LatLngCountryBean("AS", -14.3333, -170.0000));

            Add(new LatLngCountryBean("AT", 47.3333, 13.3333));

            Add(new LatLngCountryBean("AU", -27.0000, 133.0000));

            Add(new LatLngCountryBean("AW", 12.5000, -69.9667));

            Add(new LatLngCountryBean("AZ", 40.5000, 47.5000));

            Add(new LatLngCountryBean("BA", 44.0000, 18.0000));

            Add(new LatLngCountryBean("BB", 13.1667, -59.5333));

            Add(new LatLngCountryBean("BD", 24.0000, 90.0000));

            Add(new LatLngCountryBean("BE", 50.8333, 4.0000));

            Add(new LatLngCountryBean("BF", 13.0000, -2.0000));

            Add(new LatLngCountryBean("BG", 43.0000, 25.0000));

            Add(new LatLngCountryBean("BH", 26.0000, 50.5500));

            Add(new LatLngCountryBean("BI", -3.5000, 30.0000));

            Add(new LatLngCountryBean("BJ", 9.5000, 2.2500));

            Add(new LatLngCountryBean("BM", 32.3333, -64.7500));

            Add(new LatLngCountryBean("BN", 4.5000, 114.6667));

            Add(new LatLngCountryBean("BO", -17.0000, -65.0000));

            Add(new LatLngCountryBean("BR", -10.0000, -55.0000));

            Add(new LatLngCountryBean("BS", 24.2500, -76.0000));

            Add(new LatLngCountryBean("BT", 27.5000, 90.5000));

            Add(new LatLngCountryBean("BV", -54.4333, 3.4000));

            Add(new LatLngCountryBean("BW", -22.0000, 24.0000));

            Add(new LatLngCountryBean("BY", 53.0000, 28.0000));

            Add(new LatLngCountryBean("BZ", 17.2500, -88.7500));

            Add(new LatLngCountryBean("CA", 60.0000, -95.0000));

            Add(new LatLngCountryBean("CC", -12.5000, 96.8333));

            Add(new LatLngCountryBean("CD", 0.0000, 25.0000));

            Add(new LatLngCountryBean("CF", 7.0000, 21.0000));

            Add(new LatLngCountryBean("CG", -1.0000, 15.0000));

            Add(new LatLngCountryBean("CH", 47.0000, 8.0000));

            Add(new LatLngCountryBean("CI", 8.0000, -5.0000));

            Add(new LatLngCountryBean("CK", -21.2333, -159.7667));

            Add(new LatLngCountryBean("CL", -30.0000, -71.0000));

            Add(new LatLngCountryBean("CM", 6.0000, 12.0000));

            Add(new LatLngCountryBean("CN", 35.0000, 105.0000));

            Add(new LatLngCountryBean("CO", 4.0000, -72.0000));

            Add(new LatLngCountryBean("CR", 10.0000, -84.0000));

            Add(new LatLngCountryBean("CU", 21.5000, -80.0000));

            Add(new LatLngCountryBean("CV", 16.0000, -24.0000));

            Add(new LatLngCountryBean("CX", -10.5000, 105.6667));

            Add(new LatLngCountryBean("CY", 35.0000, 33.0000));

            Add(new LatLngCountryBean("CZ", 49.7500, 15.5000));

            Add(new LatLngCountryBean("DE", 51.0000, 9.0000));

            Add(new LatLngCountryBean("DJ", 11.5000, 43.0000));

            Add(new LatLngCountryBean("DK", 56.0000, 10.0000));

            Add(new LatLngCountryBean("DM", 15.4167, -61.3333));

            Add(new LatLngCountryBean("DO", 19.0000, -70.6667));

            Add(new LatLngCountryBean("DZ", 28.0000, 3.0000));

            Add(new LatLngCountryBean("EC", -2.0000, -77.5000));

            Add(new LatLngCountryBean("EE", 59.0000, 26.0000));

            Add(new LatLngCountryBean("EG", 27.0000, 30.0000));

            Add(new LatLngCountryBean("EH", 24.5000, -13.0000));

            Add(new LatLngCountryBean("ER", 15.0000, 39.0000));

            Add(new LatLngCountryBean("ES", 40.0000, -4.0000));

            Add(new LatLngCountryBean("ET", 8.0000, 38.0000));

            Add(new LatLngCountryBean("EU", 47.0000, 8.0000));

            Add(new LatLngCountryBean("FI", 64.0000, 26.0000));

            Add(new LatLngCountryBean("FJ", -18.0000, 175.0000));

            Add(new LatLngCountryBean("FK", -51.7500, -59.0000));

            Add(new LatLngCountryBean("FM", 6.9167, 158.2500));

            Add(new LatLngCountryBean("FO", 62.0000, -7.0000));

            Add(new LatLngCountryBean("FR", 46.0000, 2.0000));

            Add(new LatLngCountryBean("GA", -1.0000, 11.7500));

            Add(new LatLngCountryBean("GB", 54.0000, -2.0000));

            Add(new LatLngCountryBean("GD", 12.1167, -61.6667));

            Add(new LatLngCountryBean("GE", 42.0000, 43.5000));

            Add(new LatLngCountryBean("GF", 4.0000, -53.0000));

            Add(new LatLngCountryBean("GH", 8.0000, -2.0000));

            Add(new LatLngCountryBean("GI", 36.1833, -5.3667));

            Add(new LatLngCountryBean("GL", 72.0000, -40.0000));

            Add(new LatLngCountryBean("GM", 13.4667, -16.5667));

            Add(new LatLngCountryBean("GN", 11.0000, -10.0000));

            Add(new LatLngCountryBean("GP", 16.2500, -61.5833));

            Add(new LatLngCountryBean("GQ", 2.0000, 10.0000));

            Add(new LatLngCountryBean("GR", 39.0000, 22.0000));

            Add(new LatLngCountryBean("GS", -54.5000, -37.0000));

            Add(new LatLngCountryBean("GT", 15.5000, -90.2500));

            Add(new LatLngCountryBean("GU", 13.4667, 144.7833));

            Add(new LatLngCountryBean("GW", 12.0000, -15.0000));

            Add(new LatLngCountryBean("GY", 5.0000, -59.0000));

            Add(new LatLngCountryBean("HK", 22.2500, 114.1667));

            Add(new LatLngCountryBean("HM", -53.1000, 72.5167));

            Add(new LatLngCountryBean("HN", 15.0000, -86.5000));

            Add(new LatLngCountryBean("HR", 45.1667, 15.5000));

            Add(new LatLngCountryBean("HT", 19.0000, -72.4167));

            Add(new LatLngCountryBean("HU", 47.0000, 20.0000));

            Add(new LatLngCountryBean("ID", -5.0000, 120.0000));

            Add(new LatLngCountryBean("IE", 53.0000, -8.0000));

            Add(new LatLngCountryBean("IL", 31.5000, 34.7500));

            Add(new LatLngCountryBean("IN", 20.0000, 77.0000));

            Add(new LatLngCountryBean("IO", -6.0000, 71.5000));

            Add(new LatLngCountryBean("IQ", 33.0000, 44.0000));

            Add(new LatLngCountryBean("IR", 32.0000, 53.0000));

            Add(new LatLngCountryBean("IS", 65.0000, -18.0000));

            Add(new LatLngCountryBean("IT", 42.8333, 12.8333));

            Add(new LatLngCountryBean("JM", 18.2500, -77.5000));

            Add(new LatLngCountryBean("JO", 31.0000, 36.0000));

            Add(new LatLngCountryBean("JP", 36.0000, 138.0000));

            Add(new LatLngCountryBean("KE", 1.0000, 38.0000));

            Add(new LatLngCountryBean("KG", 41.0000, 75.0000));

            Add(new LatLngCountryBean("KH", 13.0000, 105.0000));

            Add(new LatLngCountryBean("KI", 1.4167, 173.0000));

            Add(new LatLngCountryBean("KM", -12.1667, 44.2500));

            Add(new LatLngCountryBean("KN", 17.3333, -62.7500));

            Add(new LatLngCountryBean("KP", 40.0000, 127.0000));

            Add(new LatLngCountryBean("KR", 37.0000, 127.5000));

            Add(new LatLngCountryBean("KW", 29.3375, 47.6581));

            Add(new LatLngCountryBean("KY", 19.5000, -80.5000));

            Add(new LatLngCountryBean("KZ", 48.0000, 68.0000));

            Add(new LatLngCountryBean("LA", 18.0000, 105.0000));

            Add(new LatLngCountryBean("LB", 33.8333, 35.8333));

            Add(new LatLngCountryBean("LC", 13.8833, -61.1333));

            Add(new LatLngCountryBean("LI", 47.1667, 9.5333));

            Add(new LatLngCountryBean("LK", 7.0000, 81.0000));

            Add(new LatLngCountryBean("LR", 6.5000, -9.5000));

            Add(new LatLngCountryBean("LS", -29.5000, 28.5000));

            Add(new LatLngCountryBean("LT", 56.0000, 24.0000));

            Add(new LatLngCountryBean("LU", 49.7500, 6.1667));

            Add(new LatLngCountryBean("LV", 57.0000, 25.0000));

            Add(new LatLngCountryBean("LY", 25.0000, 17.0000));

            Add(new LatLngCountryBean("MA", 32.0000, -5.0000));

            Add(new LatLngCountryBean("MC", 43.7333, 7.4000));

            Add(new LatLngCountryBean("MD", 47.0000, 29.0000));

            Add(new LatLngCountryBean("ME", 42.0000, 19.0000));

            Add(new LatLngCountryBean("MG", -20.0000, 47.0000));

            Add(new LatLngCountryBean("MH", 9.0000, 168.0000));

            Add(new LatLngCountryBean("MK", 41.8333, 22.0000));

            Add(new LatLngCountryBean("ML", 17.0000, -4.0000));

            Add(new LatLngCountryBean("MM", 22.0000, 98.0000));

            Add(new LatLngCountryBean("MN", 46.0000, 105.0000));

            Add(new LatLngCountryBean("MO", 22.1667, 113.5500));

            Add(new LatLngCountryBean("MP", 15.2000, 145.7500));

            Add(new LatLngCountryBean("MQ", 14.6667, -61.0000));

            Add(new LatLngCountryBean("MR", 20.0000, -12.0000));

            Add(new LatLngCountryBean("MS", 16.7500, -62.2000));

            Add(new LatLngCountryBean("MT", 35.8333, 14.5833));

            Add(new LatLngCountryBean("MU", -20.2833, 57.5500));

            Add(new LatLngCountryBean("MV", 3.2500, 73.0000));

            Add(new LatLngCountryBean("MW", -13.5000, 34.0000));

            Add(new LatLngCountryBean("MX", 23.0000, -102.0000));

            Add(new LatLngCountryBean("MY", 2.5000, 112.5000));

            Add(new LatLngCountryBean("MZ", -18.2500, 35.0000));

            Add(new LatLngCountryBean("NA", -22.0000, 17.0000));

            Add(new LatLngCountryBean("NC", -21.5000, 165.5000));

            Add(new LatLngCountryBean("NE", 16.0000, 8.0000));

            Add(new LatLngCountryBean("NF", -29.0333, 167.9500));

            Add(new LatLngCountryBean("NG", 10.0000, 8.0000));

            Add(new LatLngCountryBean("NI", 13.0000, -85.0000));

            Add(new LatLngCountryBean("NL", 52.5000, 5.7500));

            Add(new LatLngCountryBean("NO", 62.0000, 10.0000));

            Add(new LatLngCountryBean("NP", 28.0000, 84.0000));

            Add(new LatLngCountryBean("NR", -0.5333, 166.9167));

            Add(new LatLngCountryBean("NU", -19.0333, -169.8667));

            Add(new LatLngCountryBean("NZ", -41.0000, 174.0000));

            Add(new LatLngCountryBean("OM", 21.0000, 57.0000));

            Add(new LatLngCountryBean("PA", 9.0000, -80.0000));

            Add(new LatLngCountryBean("PE", -10.0000, -76.0000));

            Add(new LatLngCountryBean("PF", -15.0000, -140.0000));

            Add(new LatLngCountryBean("PG", -6.0000, 147.0000));

            Add(new LatLngCountryBean("PH", 13.0000, 122.0000));

            Add(new LatLngCountryBean("PK", 30.0000, 70.0000));

            Add(new LatLngCountryBean("PL", 52.0000, 20.0000));

            Add(new LatLngCountryBean("PM", 46.8333, -56.3333));

            Add(new LatLngCountryBean("PR", 18.2500, -66.5000));

            Add(new LatLngCountryBean("PS", 32.0000, 35.2500));

            Add(new LatLngCountryBean("PT", 39.5000, -8.0000));

            Add(new LatLngCountryBean("PW", 7.5000, 134.5000));

            Add(new LatLngCountryBean("PY", -23.0000, -58.0000));

            Add(new LatLngCountryBean("QA", 25.5000, 51.2500));

            Add(new LatLngCountryBean("RE", -21.1000, 55.6000));

            Add(new LatLngCountryBean("RO", 46.0000, 25.0000));

            Add(new LatLngCountryBean("RS", 44.0000, 21.0000));

            Add(new LatLngCountryBean("RU", 60.0000, 100.0000));

            Add(new LatLngCountryBean("RW", -2.0000, 30.0000));

            Add(new LatLngCountryBean("SA", 25.0000, 45.0000));

            Add(new LatLngCountryBean("SB", -8.0000, 159.0000));

            Add(new LatLngCountryBean("SC", -4.5833, 55.6667));

            Add(new LatLngCountryBean("SD", 15.0000, 30.0000));

            Add(new LatLngCountryBean("SE", 62.0000, 15.0000));

            Add(new LatLngCountryBean("SG", 1.3667, 103.8000));

            Add(new LatLngCountryBean("SH", -15.9333, -5.7000));

            Add(new LatLngCountryBean("SI", 46.0000, 15.0000));

            Add(new LatLngCountryBean("SJ", 78.0000, 20.0000));

            Add(new LatLngCountryBean("SK", 48.6667, 19.5000));

            Add(new LatLngCountryBean("SL", 8.5000, -11.5000));

            Add(new LatLngCountryBean("SM", 43.7667, 12.4167));

            Add(new LatLngCountryBean("SN", 14.0000, -14.0000));

            Add(new LatLngCountryBean("SO", 10.0000, 49.0000));

            Add(new LatLngCountryBean("SR", 4.0000, -56.0000));

            Add(new LatLngCountryBean("ST", 1.0000, 7.0000));

            Add(new LatLngCountryBean("SV", 13.8333, -88.9167));

            Add(new LatLngCountryBean("SY", 35.0000, 38.0000));

            Add(new LatLngCountryBean("SZ", -26.5000, 31.5000));

            Add(new LatLngCountryBean("TC", 21.7500, -71.5833));

            Add(new LatLngCountryBean("TD", 15.0000, 19.0000));

            Add(new LatLngCountryBean("TF", -43.0000, 67.0000));

            Add(new LatLngCountryBean("TG", 8.0000, 1.1667));

            Add(new LatLngCountryBean("TH", 15.0000, 100.0000));

            Add(new LatLngCountryBean("TJ", 39.0000, 71.0000));

            Add(new LatLngCountryBean("TK", -9.0000, -172.0000));

            Add(new LatLngCountryBean("TM", 40.0000, 60.0000));

            Add(new LatLngCountryBean("TN", 34.0000, 9.0000));

            Add(new LatLngCountryBean("TO", -20.0000, -175.0000));

            Add(new LatLngCountryBean("TR", 39.0000, 35.0000));

            Add(new LatLngCountryBean("TT", 11.0000, -61.0000));

            Add(new LatLngCountryBean("TV", -8.0000, 178.0000));

            Add(new LatLngCountryBean("TW", 23.5000, 121.0000));

            Add(new LatLngCountryBean("TZ", -6.0000, 35.0000));

            Add(new LatLngCountryBean("UA", 49.0000, 32.0000));

            Add(new LatLngCountryBean("UG", 1.0000, 32.0000));

            Add(new LatLngCountryBean("UM", 19.2833, 166.6000));

            Add(new LatLngCountryBean("US", 38.0000, -97.0000));

            Add(new LatLngCountryBean("UY", -33.0000, -56.0000));

            Add(new LatLngCountryBean("UZ", 41.0000, 64.0000));

            Add(new LatLngCountryBean("VA", 41.9000, 12.4500));

            Add(new LatLngCountryBean("VC", 13.2500, -61.2000));

            Add(new LatLngCountryBean("VE", 8.0000, -66.0000));

            Add(new LatLngCountryBean("VG", 18.5000, -64.5000));

            Add(new LatLngCountryBean("VI", 18.3333, -64.8333));

            Add(new LatLngCountryBean("VN", 16.0000, 106.0000));

            Add(new LatLngCountryBean("VU", -16.0000, 167.0000));

            Add(new LatLngCountryBean("WF", -13.3000, -176.2000));

            Add(new LatLngCountryBean("WS", -13.5833, -172.3333));

            Add(new LatLngCountryBean("YE", 15.0000, 48.0000));

            Add(new LatLngCountryBean("YT", -12.8333, 45.1667));

            Add(new LatLngCountryBean("ZA", -29.0000, 24.0000));

            Add(new LatLngCountryBean("ZM", -15.0000, 30.0000));

            Add(new LatLngCountryBean("ZW", -20.0000, 30.0000));

        }

    }