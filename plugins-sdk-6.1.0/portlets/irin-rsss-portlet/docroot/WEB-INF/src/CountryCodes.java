
import java.util.*;

public  class CountryCodes

    {

       public static Map<String, String> lookup = new HashMap<String,String>();
       
       public static Map<String, String> codeByCtryMap = new HashMap<String,String>(); 

        public static String getCountry(String code)
        {
            if (code != null)
            {
            	code = code.toUpperCase();
                if (lookup.containsKey(code))
                    return lookup.get( code );
            }
            return "United Arab Emirates";

        }
        public static String getCountryCodeByName(String cName)
        {
            if (cName != null)
            {
            	cName = cName.toUpperCase().trim();
                if (codeByCtryMap.containsKey(cName))
                    return codeByCtryMap.get( cName );
            }
            return "AE";

        }

 

        private static void Add(String country, String code)
        {
            //lookup[country] = code;
            lookup.put( code, country );
            codeByCtryMap.put( country , code);

        }

        // from http://userpage.chemie.fu-berlin.de/diverse/doc/ISO_3166.html

        public CountryCodes()

        {

            Add("AALAND ISLANDS", "AX");

            Add("AFGHANISTAN", "AF");

            Add("ALBANIA", "AL");

            Add("ALGERIA", "DZ");

            Add("AMERICAN SAMOA", "AS");

            Add("ANDORRA", "AD");

            Add("ANGOLA", "AO");

            Add("ANGUILLA", "AI");

            Add("ANTARCTICA", "AQ");

            Add("ANTIGUA AND BARBUDA", "AG");

            Add("ARGENTINA", "AR");

            Add("ARMENIA", "AM");

            Add("ARUBA", "AW");

            Add("AUSTRALIA", "AU");

            Add("AUSTRIA", "AT");

            Add("AZERBAIJAN", "AZ");

            Add("BAHAMAS", "BS");

            Add("BAHRAIN", "BH");

            Add("BANGLADESH", "BD");

            Add("BARBADOS", "BB");

            Add("BELARUS", "BY");

            Add("BELGIUM", "BE");

            Add("BELIZE", "BZ");

            Add("BENIN", "BJ");

            Add("BERMUDA", "BM");

            Add("BHUTAN", "BT");

            Add("BOLIVIA", "BO");

            Add("BOSNIA AND HERZEGOWINA", "BA");

            Add("BOTSWANA", "BW");

            Add("BOUVET ISLAND", "BV");

            Add("BRAZIL", "BR");

            Add("BRITISH INDIAN OCEAN TERRITORY", "IO");

            Add("BRUNEI DARUSSALAM", "BN");

            Add("BULGARIA", "BG");

            Add("BURKINA FASO", "BF");

            Add("BURUNDI", "BI");

            Add("CAMBODIA", "KH");

            Add("CAMEROON", "CM");

            Add("CANADA", "CA");

            Add("CAPE VERDE", "CV");

            Add("CAYMAN ISLANDS", "KY");

            Add("CENTRAL AFRICAN REPUBLIC", "CF");

            Add("CHAD", "TD");

            Add("CHILE", "CL");

            Add("CHINA", "CN");

            Add("CHRISTMAS ISLAND", "CX");

            Add("COCOS (KEELING) ISLANDS", "CC");

            Add("COLOMBIA", "CO");

            Add("COMOROS", "KM");

            Add("CONGO, Democratic Republic of (was Zaire)", "CD");

            Add("CONGO, Republic of", "CG");

            Add("COOK ISLANDS", "CK");

            Add("COSTA RICA", "CR");

            Add("COTE D'IVOIRE", "CI");

            Add("CROATIA (local name: Hrvatska)", "HR");

            Add("CUBA", "CU");

            Add("CYPRUS", "CY");

            Add("CZECH REPUBLIC", "CZ");

            Add("DENMARK", "DK");

            Add("DJIBOUTI", "DJ");

            Add("DOMINICA", "DM");

            Add("DOMINICAN REPUBLIC", "DO");

            Add("ECUADOR", "EC");

            Add("EGYPT", "EG");

            Add("EL SALVADOR", "SV");

            Add("EQUATORIAL GUINEA", "GQ");

            Add("ERITREA", "ER");

            Add("ESTONIA", "EE");

            Add("ETHIOPIA", "ET");

            Add("FALKLAND ISLANDS (MALVINAS)", "FK");

            Add("FAROE ISLANDS", "FO");

            Add("FIJI", "FJ");

            Add("FINLAND", "FI");

            Add("FRANCE", "FR");

            Add("FRENCH GUIANA", "GF");

            Add("FRENCH POLYNESIA", "PF");

            Add("FRENCH SOUTHERN TERRITORIES", "TF");

            Add("GABON", "GA");

            Add("GAMBIA", "GM");

            Add("GEORGIA", "GE");

            Add("GERMANY", "DE");

            Add("GHANA", "GH");

            Add("GIBRALTAR", "GI");

            Add("GREECE", "GR");

            Add("GREENLAND", "GL");

            Add("GRENADA", "GD");

            Add("GUADELOUPE", "GP");

            Add("GUAM", "GU");

            Add("GUATEMALA", "GT");

            Add("GUINEA", "GN");

            Add("GUINEA-BISSAU", "GW");

            Add("GUYANA", "GY");

            Add("HAITI", "HT");

            Add("HEARD AND MC DONALD ISLANDS", "HM");

            Add("HONDURAS", "HN");

            Add("HONG KONG", "HK");

            Add("HUNGARY", "HU");

            Add("ICELAND", "IS");

            Add("INDIA", "IN");

            Add("INDONESIA", "ID");

            Add("IRAN (ISLAMIC REPUBLIC OF)", "IR");

            Add("IRAQ", "IQ");

            Add("IRELAND", "IE");

            Add("ISRAEL", "IL");

            Add("ITALY", "IT");

            Add("JAMAICA", "JM");

            Add("JAPAN", "JP");

            Add("JORDAN", "JO");

            Add("KAZAKHSTAN", "KZ");

            Add("KENYA", "KE");

            Add("KIRIBATI", "KI");

            Add("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF", "KP");

            Add("KOREA, REPUBLIC OF", "KR");

            Add("KUWAIT", "KW");

            Add("KYRGYZSTAN", "KG");

            Add("LAO PEOPLE'S DEMOCRATIC REPUBLIC", "LA");

            Add("LATVIA", "LV");

            Add("LEBANON", "LB");

            Add("LESOTHO", "LS");

            Add("LIBERIA", "LR");

            Add("LIBYAN ARAB JAMAHIRIYA", "LY");

            Add("LIECHTENSTEIN", "LI");

            Add("LITHUANIA", "LT");

            Add("LUXEMBOURG", "LU");

            Add("MACAU", "MO");

            Add("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF", "MK");

            Add("MADAGASCAR", "MG");

            Add("MALAWI", "MW");

            Add("MALAYSIA", "MY");

            Add("MALDIVES", "MV");

            Add("MALI", "ML");

            Add("MALTA", "MT");

            Add("MARSHALL ISLANDS", "MH");

            Add("MARTINIQUE", "MQ");

            Add("MAURITANIA", "MR");

            Add("MAURITIUS", "MU");

            Add("MAYOTTE", "YT");

            Add("MEXICO", "MX");

            Add("MICRONESIA, FEDERATED STATES OF", "FM");

            Add("MOLDOVA, REPUBLIC OF", "MD");

            Add("MONACO", "MC");

            Add("MONGOLIA", "MN");

            Add("MONTSERRAT", "MS");

            Add("MOROCCO", "MA");

            Add("MOZAMBIQUE", "MZ");

            Add("MYANMAR", "MM");

            Add("NAMIBIA", "NA");

            Add("NAURU", "NR");

            Add("NEPAL", "NP");

            Add("NETHERLANDS", "NL");

            Add("NETHERLANDS ANTILLES", "AN");

            Add("NEW CALEDONIA", "NC");

            Add("NEW ZEALAND", "NZ");

            Add("NICARAGUA", "NI");

            Add("NIGER", "NE");

            Add("NIGERIA", "NG");

            Add("NIUE", "NU");

            Add("NORFOLK ISLAND", "NF");

            Add("NORTHERN MARIANA ISLANDS", "MP");

            Add("NORWAY", "NO");

            Add("OMAN", "OM");

            Add("PAKISTAN", "PK");

            Add("PALAU", "PW");

            Add("PALESTINIAN TERRITORY, Occupied", "PS");

            Add("PANAMA", "PA");

            Add("PAPUA NEW GUINEA", "PG");

            Add("PARAGUAY", "PY");

            Add("PERU", "PE");

            Add("PHILIPPINES", "PH");

            Add("PITCAIRN", "PN");

            Add("POLAND", "PL");

            Add("PORTUGAL", "PT");

            Add("PUERTO RICO", "PR");

            Add("QATAR", "QA");

            Add("REUNION", "RE");

            Add("ROMANIA", "RO");

            Add("RUSSIAN FEDERATION", "RU");

            Add("RWANDA", "RW");

            Add("SAINT HELENA", "SH");

            Add("SAINT KITTS AND NEVIS", "KN");

            Add("SAINT LUCIA", "LC");

            Add("SAINT PIERRE AND MIQUELON", "PM");

            Add("SAINT VINCENT AND THE GRENADINES", "VC");

            Add("SAMOA", "WS");

            Add("SAN MARINO", "SM");

            Add("SAO TOME AND PRINCIPE", "ST");

            Add("SAUDI ARABIA", "SA");

            Add("SENEGAL", "SN");

            Add("SERBIA AND MONTENEGRO", "CS");

            Add("SEYCHELLES", "SC");

            Add("SIERRA LEONE", "SL");

            Add("SINGAPORE", "SG");

            Add("SLOVAKIA", "SK");

            Add("SLOVENIA", "SI");

            Add("SOLOMON ISLANDS", "SB");

            Add("SOMALIA", "SO");

            Add("SOUTH AFRICA", "ZA");

            Add("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS", "GS");

            Add("SPAIN", "ES");

            Add("SRI LANKA", "LK");

            Add("SUDAN", "SD");

            Add("SURINAME", "SR");

            Add("SVALBARD AND JAN MAYEN ISLANDS", "SJ");

            Add("SWAZILAND", "SZ");

            Add("SWEDEN", "SE");

            Add("SWITZERLAND", "CH");

            Add("SYRIAN ARAB REPUBLIC", "SY");

            Add("TAIWAN", "TW");

            Add("TAJIKISTAN", "TJ");

            Add("TANZANIA, UNITED REPUBLIC OF", "TZ");

            Add("THAILAND", "TH");

            Add("TIMOR-LESTE", "TL");

            Add("TOGO", "TG");

            Add("TOKELAU", "TK");

            Add("TONGA", "TO");

            Add("TRINIDAD AND TOBAGO", "TT");

            Add("TUNISIA", "TN");

            Add("TURKEY", "TR");

            Add("TURKMENISTAN", "TM");

            Add("TURKS AND CAICOS ISLANDS", "TC");

            Add("TUVALU", "TV");

            Add("UGANDA", "UG");

            Add("UKRAINE", "UA");

            Add("UNITED ARAB EMIRATES", "AE");

            Add("UNITED KINGDOM", "GB");

            Add("UNITED STATES", "US");

            Add("UNITED STATES MINOR OUTLYING ISLANDS", "UM");

            Add("URUGUAY", "UY");

            Add("UZBEKISTAN", "UZ");

            Add("VANUATU", "VU");

            Add("VATICAN CITY STATE (HOLY SEE)", "VA");

            Add("VENEZUELA", "VE");

            Add("VIET NAM", "VN");

            Add("VIRGIN ISLANDS (BRITISH)", "VG");

            Add("VIRGIN ISLANDS (U.S.)", "VI");

            Add("WALLIS AND FUTUNA ISLANDS", "WF");

            Add("WESTERN SAHARA", "EH");

            Add("YEMEN", "YE");

            Add("ZAMBIA", "ZM");

            Add("ZIMBABWE", "ZW");

        }

    }

 
 
/*
  

 

    static class LatLngfromCountryCode

    {

        private static Dictionary<String, LatLngCountry> TheDictionary = new Dictionary<String,LatLngCountry>();

 

        private static void Add(LatLngCountry country)

        {

            TheDictionary[country.CountryCode] = country;

        }

 

        public static LatLngCountry Get(String code)

        {

            LatLngCountry country;

            if (TheDictionary.TryGetValue(code, out country))

                return country;

            return Get("GB");

        }

 

        public static LatLngCountry Get(int userId)

        {

           // String country = DotNetNuke.Entities.Users.UserController.GetUser(0, userId, true).Profile.Country;

            return Get(CountryCodes.GetCode(country));

        }

 

        // from http://www.maxmind.com/app/country_latlon

         LatLngfromCountryCode()

        {

            Add(new LatLngCountry("AD", 42.5000, 1.5000));

            Add(new LatLngCountry("AE", 24.0000, 54.0000));

            Add(new LatLngCountry("AF", 33.0000, 65.0000));

            Add(new LatLngCountry("AG", 17.0500, -61.8000));

            Add(new LatLngCountry("AI", 18.2500, -63.1667));

            Add(new LatLngCountry("AL", 41.0000, 20.0000));

            Add(new LatLngCountry("AM", 40.0000, 45.0000));

            Add(new LatLngCountry("AN", 12.2500, -68.7500));

            Add(new LatLngCountry("AO", -12.5000, 18.5000));

            Add(new LatLngCountry("AP", 35.0000, 105.0000));

            Add(new LatLngCountry("AQ", -90.0000, 0.0000));

            Add(new LatLngCountry("AR", -34.0000, -64.0000));

            Add(new LatLngCountry("AS", -14.3333, -170.0000));

            Add(new LatLngCountry("AT", 47.3333, 13.3333));

            Add(new LatLngCountry("AU", -27.0000, 133.0000));

            Add(new LatLngCountry("AW", 12.5000, -69.9667));

            Add(new LatLngCountry("AZ", 40.5000, 47.5000));

            Add(new LatLngCountry("BA", 44.0000, 18.0000));

            Add(new LatLngCountry("BB", 13.1667, -59.5333));

            Add(new LatLngCountry("BD", 24.0000, 90.0000));

            Add(new LatLngCountry("BE", 50.8333, 4.0000));

            Add(new LatLngCountry("BF", 13.0000, -2.0000));

            Add(new LatLngCountry("BG", 43.0000, 25.0000));

            Add(new LatLngCountry("BH", 26.0000, 50.5500));

            Add(new LatLngCountry("BI", -3.5000, 30.0000));

            Add(new LatLngCountry("BJ", 9.5000, 2.2500));

            Add(new LatLngCountry("BM", 32.3333, -64.7500));

            Add(new LatLngCountry("BN", 4.5000, 114.6667));

            Add(new LatLngCountry("BO", -17.0000, -65.0000));

            Add(new LatLngCountry("BR", -10.0000, -55.0000));

            Add(new LatLngCountry("BS", 24.2500, -76.0000));

            Add(new LatLngCountry("BT", 27.5000, 90.5000));

            Add(new LatLngCountry("BV", -54.4333, 3.4000));

            Add(new LatLngCountry("BW", -22.0000, 24.0000));

            Add(new LatLngCountry("BY", 53.0000, 28.0000));

            Add(new LatLngCountry("BZ", 17.2500, -88.7500));

            Add(new LatLngCountry("CA", 60.0000, -95.0000));

            Add(new LatLngCountry("CC", -12.5000, 96.8333));

            Add(new LatLngCountry("CD", 0.0000, 25.0000));

            Add(new LatLngCountry("CF", 7.0000, 21.0000));

            Add(new LatLngCountry("CG", -1.0000, 15.0000));

            Add(new LatLngCountry("CH", 47.0000, 8.0000));

            Add(new LatLngCountry("CI", 8.0000, -5.0000));

            Add(new LatLngCountry("CK", -21.2333, -159.7667));

            Add(new LatLngCountry("CL", -30.0000, -71.0000));

            Add(new LatLngCountry("CM", 6.0000, 12.0000));

            Add(new LatLngCountry("CN", 35.0000, 105.0000));

            Add(new LatLngCountry("CO", 4.0000, -72.0000));

            Add(new LatLngCountry("CR", 10.0000, -84.0000));

            Add(new LatLngCountry("CU", 21.5000, -80.0000));

            Add(new LatLngCountry("CV", 16.0000, -24.0000));

            Add(new LatLngCountry("CX", -10.5000, 105.6667));

            Add(new LatLngCountry("CY", 35.0000, 33.0000));

            Add(new LatLngCountry("CZ", 49.7500, 15.5000));

            Add(new LatLngCountry("DE", 51.0000, 9.0000));

            Add(new LatLngCountry("DJ", 11.5000, 43.0000));

            Add(new LatLngCountry("DK", 56.0000, 10.0000));

            Add(new LatLngCountry("DM", 15.4167, -61.3333));

            Add(new LatLngCountry("DO", 19.0000, -70.6667));

            Add(new LatLngCountry("DZ", 28.0000, 3.0000));

            Add(new LatLngCountry("EC", -2.0000, -77.5000));

            Add(new LatLngCountry("EE", 59.0000, 26.0000));

            Add(new LatLngCountry("EG", 27.0000, 30.0000));

            Add(new LatLngCountry("EH", 24.5000, -13.0000));

            Add(new LatLngCountry("ER", 15.0000, 39.0000));

            Add(new LatLngCountry("ES", 40.0000, -4.0000));

            Add(new LatLngCountry("ET", 8.0000, 38.0000));

            Add(new LatLngCountry("EU", 47.0000, 8.0000));

            Add(new LatLngCountry("FI", 64.0000, 26.0000));

            Add(new LatLngCountry("FJ", -18.0000, 175.0000));

            Add(new LatLngCountry("FK", -51.7500, -59.0000));

            Add(new LatLngCountry("FM", 6.9167, 158.2500));

            Add(new LatLngCountry("FO", 62.0000, -7.0000));

            Add(new LatLngCountry("FR", 46.0000, 2.0000));

            Add(new LatLngCountry("GA", -1.0000, 11.7500));

            Add(new LatLngCountry("GB", 54.0000, -2.0000));

            Add(new LatLngCountry("GD", 12.1167, -61.6667));

            Add(new LatLngCountry("GE", 42.0000, 43.5000));

            Add(new LatLngCountry("GF", 4.0000, -53.0000));

            Add(new LatLngCountry("GH", 8.0000, -2.0000));

            Add(new LatLngCountry("GI", 36.1833, -5.3667));

            Add(new LatLngCountry("GL", 72.0000, -40.0000));

            Add(new LatLngCountry("GM", 13.4667, -16.5667));

            Add(new LatLngCountry("GN", 11.0000, -10.0000));

            Add(new LatLngCountry("GP", 16.2500, -61.5833));

            Add(new LatLngCountry("GQ", 2.0000, 10.0000));

            Add(new LatLngCountry("GR", 39.0000, 22.0000));

            Add(new LatLngCountry("GS", -54.5000, -37.0000));

            Add(new LatLngCountry("GT", 15.5000, -90.2500));

            Add(new LatLngCountry("GU", 13.4667, 144.7833));

            Add(new LatLngCountry("GW", 12.0000, -15.0000));

            Add(new LatLngCountry("GY", 5.0000, -59.0000));

            Add(new LatLngCountry("HK", 22.2500, 114.1667));

            Add(new LatLngCountry("HM", -53.1000, 72.5167));

            Add(new LatLngCountry("HN", 15.0000, -86.5000));

            Add(new LatLngCountry("HR", 45.1667, 15.5000));

            Add(new LatLngCountry("HT", 19.0000, -72.4167));

            Add(new LatLngCountry("HU", 47.0000, 20.0000));

            Add(new LatLngCountry("ID", -5.0000, 120.0000));

            Add(new LatLngCountry("IE", 53.0000, -8.0000));

            Add(new LatLngCountry("IL", 31.5000, 34.7500));

            Add(new LatLngCountry("IN", 20.0000, 77.0000));

            Add(new LatLngCountry("IO", -6.0000, 71.5000));

            Add(new LatLngCountry("IQ", 33.0000, 44.0000));

            Add(new LatLngCountry("IR", 32.0000, 53.0000));

            Add(new LatLngCountry("IS", 65.0000, -18.0000));

            Add(new LatLngCountry("IT", 42.8333, 12.8333));

            Add(new LatLngCountry("JM", 18.2500, -77.5000));

            Add(new LatLngCountry("JO", 31.0000, 36.0000));

            Add(new LatLngCountry("JP", 36.0000, 138.0000));

            Add(new LatLngCountry("KE", 1.0000, 38.0000));

            Add(new LatLngCountry("KG", 41.0000, 75.0000));

            Add(new LatLngCountry("KH", 13.0000, 105.0000));

            Add(new LatLngCountry("KI", 1.4167, 173.0000));

            Add(new LatLngCountry("KM", -12.1667, 44.2500));

            Add(new LatLngCountry("KN", 17.3333, -62.7500));

            Add(new LatLngCountry("KP", 40.0000, 127.0000));

            Add(new LatLngCountry("KR", 37.0000, 127.5000));

            Add(new LatLngCountry("KW", 29.3375, 47.6581));

            Add(new LatLngCountry("KY", 19.5000, -80.5000));

            Add(new LatLngCountry("KZ", 48.0000, 68.0000));

            Add(new LatLngCountry("LA", 18.0000, 105.0000));

            Add(new LatLngCountry("LB", 33.8333, 35.8333));

            Add(new LatLngCountry("LC", 13.8833, -61.1333));

            Add(new LatLngCountry("LI", 47.1667, 9.5333));

            Add(new LatLngCountry("LK", 7.0000, 81.0000));

            Add(new LatLngCountry("LR", 6.5000, -9.5000));

            Add(new LatLngCountry("LS", -29.5000, 28.5000));

            Add(new LatLngCountry("LT", 56.0000, 24.0000));

            Add(new LatLngCountry("LU", 49.7500, 6.1667));

            Add(new LatLngCountry("LV", 57.0000, 25.0000));

            Add(new LatLngCountry("LY", 25.0000, 17.0000));

            Add(new LatLngCountry("MA", 32.0000, -5.0000));

            Add(new LatLngCountry("MC", 43.7333, 7.4000));

            Add(new LatLngCountry("MD", 47.0000, 29.0000));

            Add(new LatLngCountry("ME", 42.0000, 19.0000));

            Add(new LatLngCountry("MG", -20.0000, 47.0000));

            Add(new LatLngCountry("MH", 9.0000, 168.0000));

            Add(new LatLngCountry("MK", 41.8333, 22.0000));

            Add(new LatLngCountry("ML", 17.0000, -4.0000));

            Add(new LatLngCountry("MM", 22.0000, 98.0000));

            Add(new LatLngCountry("MN", 46.0000, 105.0000));

            Add(new LatLngCountry("MO", 22.1667, 113.5500));

            Add(new LatLngCountry("MP", 15.2000, 145.7500));

            Add(new LatLngCountry("MQ", 14.6667, -61.0000));

            Add(new LatLngCountry("MR", 20.0000, -12.0000));

            Add(new LatLngCountry("MS", 16.7500, -62.2000));

            Add(new LatLngCountry("MT", 35.8333, 14.5833));

            Add(new LatLngCountry("MU", -20.2833, 57.5500));

            Add(new LatLngCountry("MV", 3.2500, 73.0000));

            Add(new LatLngCountry("MW", -13.5000, 34.0000));

            Add(new LatLngCountry("MX", 23.0000, -102.0000));

            Add(new LatLngCountry("MY", 2.5000, 112.5000));

            Add(new LatLngCountry("MZ", -18.2500, 35.0000));

            Add(new LatLngCountry("NA", -22.0000, 17.0000));

            Add(new LatLngCountry("NC", -21.5000, 165.5000));

            Add(new LatLngCountry("NE", 16.0000, 8.0000));

            Add(new LatLngCountry("NF", -29.0333, 167.9500));

            Add(new LatLngCountry("NG", 10.0000, 8.0000));

            Add(new LatLngCountry("NI", 13.0000, -85.0000));

            Add(new LatLngCountry("NL", 52.5000, 5.7500));

            Add(new LatLngCountry("NO", 62.0000, 10.0000));

            Add(new LatLngCountry("NP", 28.0000, 84.0000));

            Add(new LatLngCountry("NR", -0.5333, 166.9167));

            Add(new LatLngCountry("NU", -19.0333, -169.8667));

            Add(new LatLngCountry("NZ", -41.0000, 174.0000));

            Add(new LatLngCountry("OM", 21.0000, 57.0000));

            Add(new LatLngCountry("PA", 9.0000, -80.0000));

            Add(new LatLngCountry("PE", -10.0000, -76.0000));

            Add(new LatLngCountry("PF", -15.0000, -140.0000));

            Add(new LatLngCountry("PG", -6.0000, 147.0000));

            Add(new LatLngCountry("PH", 13.0000, 122.0000));

            Add(new LatLngCountry("PK", 30.0000, 70.0000));

            Add(new LatLngCountry("PL", 52.0000, 20.0000));

            Add(new LatLngCountry("PM", 46.8333, -56.3333));

            Add(new LatLngCountry("PR", 18.2500, -66.5000));

            Add(new LatLngCountry("PS", 32.0000, 35.2500));

            Add(new LatLngCountry("PT", 39.5000, -8.0000));

            Add(new LatLngCountry("PW", 7.5000, 134.5000));

            Add(new LatLngCountry("PY", -23.0000, -58.0000));

            Add(new LatLngCountry("QA", 25.5000, 51.2500));

            Add(new LatLngCountry("RE", -21.1000, 55.6000));

            Add(new LatLngCountry("RO", 46.0000, 25.0000));

            Add(new LatLngCountry("RS", 44.0000, 21.0000));

            Add(new LatLngCountry("RU", 60.0000, 100.0000));

            Add(new LatLngCountry("RW", -2.0000, 30.0000));

            Add(new LatLngCountry("SA", 25.0000, 45.0000));

            Add(new LatLngCountry("SB", -8.0000, 159.0000));

            Add(new LatLngCountry("SC", -4.5833, 55.6667));

            Add(new LatLngCountry("SD", 15.0000, 30.0000));

            Add(new LatLngCountry("SE", 62.0000, 15.0000));

            Add(new LatLngCountry("SG", 1.3667, 103.8000));

            Add(new LatLngCountry("SH", -15.9333, -5.7000));

            Add(new LatLngCountry("SI", 46.0000, 15.0000));

            Add(new LatLngCountry("SJ", 78.0000, 20.0000));

            Add(new LatLngCountry("SK", 48.6667, 19.5000));

            Add(new LatLngCountry("SL", 8.5000, -11.5000));

            Add(new LatLngCountry("SM", 43.7667, 12.4167));

            Add(new LatLngCountry("SN", 14.0000, -14.0000));

            Add(new LatLngCountry("SO", 10.0000, 49.0000));

            Add(new LatLngCountry("SR", 4.0000, -56.0000));

            Add(new LatLngCountry("ST", 1.0000, 7.0000));

            Add(new LatLngCountry("SV", 13.8333, -88.9167));

            Add(new LatLngCountry("SY", 35.0000, 38.0000));

            Add(new LatLngCountry("SZ", -26.5000, 31.5000));

            Add(new LatLngCountry("TC", 21.7500, -71.5833));

            Add(new LatLngCountry("TD", 15.0000, 19.0000));

            Add(new LatLngCountry("TF", -43.0000, 67.0000));

            Add(new LatLngCountry("TG", 8.0000, 1.1667));

            Add(new LatLngCountry("TH", 15.0000, 100.0000));

            Add(new LatLngCountry("TJ", 39.0000, 71.0000));

            Add(new LatLngCountry("TK", -9.0000, -172.0000));

            Add(new LatLngCountry("TM", 40.0000, 60.0000));

            Add(new LatLngCountry("TN", 34.0000, 9.0000));

            Add(new LatLngCountry("TO", -20.0000, -175.0000));

            Add(new LatLngCountry("TR", 39.0000, 35.0000));

            Add(new LatLngCountry("TT", 11.0000, -61.0000));

            Add(new LatLngCountry("TV", -8.0000, 178.0000));

            Add(new LatLngCountry("TW", 23.5000, 121.0000));

            Add(new LatLngCountry("TZ", -6.0000, 35.0000));

            Add(new LatLngCountry("UA", 49.0000, 32.0000));

            Add(new LatLngCountry("UG", 1.0000, 32.0000));

            Add(new LatLngCountry("UM", 19.2833, 166.6000));

            Add(new LatLngCountry("US", 38.0000, -97.0000));

            Add(new LatLngCountry("UY", -33.0000, -56.0000));

            Add(new LatLngCountry("UZ", 41.0000, 64.0000));

            Add(new LatLngCountry("VA", 41.9000, 12.4500));

            Add(new LatLngCountry("VC", 13.2500, -61.2000));

            Add(new LatLngCountry("VE", 8.0000, -66.0000));

            Add(new LatLngCountry("VG", 18.5000, -64.5000));

            Add(new LatLngCountry("VI", 18.3333, -64.8333));

            Add(new LatLngCountry("VN", 16.0000, 106.0000));

            Add(new LatLngCountry("VU", -16.0000, 167.0000));

            Add(new LatLngCountry("WF", -13.3000, -176.2000));

            Add(new LatLngCountry("WS", -13.5833, -172.3333));

            Add(new LatLngCountry("YE", 15.0000, 48.0000));

            Add(new LatLngCountry("YT", -12.8333, 45.1667));

            Add(new LatLngCountry("ZA", -29.0000, 24.0000));

            Add(new LatLngCountry("ZM", -15.0000, 30.0000));

            Add(new LatLngCountry("ZW", -20.0000, 30.0000));

        }

    }*/