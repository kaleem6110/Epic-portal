package com.maxmind.geoip;


public class LatLngCountryBean

    {

        public double Lat;

        public double Long ;

        public String countryCode ;
        
        
        public void setLat(double lat )
        {
        	Lat = lat;
        }
        public double getLat()
        {
        	return Lat;
        }
        
        
        public void setLong(double lng )
        {
        	Long = lng;
        }
        public double getLong()
        {
        	return Long;
        }
        
        public void setcountryCode(String cc )
        {
        	countryCode = cc;
        }
        public String getcountryCode()
        {
        	return countryCode;
        }


        public LatLngCountryBean (String code, double lat, double lng)

          {

            Lat = lat;

            Long = lng;

            countryCode = code;

          }

    }