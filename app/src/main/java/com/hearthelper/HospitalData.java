package com.hearthelper;

import java.io.Serializable;
import java.util.List;

public class HospitalData implements Serializable {
    private static final long serialVersionUID = -1213625537935747242L;
    private boolean success;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuccess() {
        return success;
    }

    private ResultData result;

    public ResultData getResult() {
        return result;
    }

    public void setResult(ResultData result) {
        this.result = result;
    }

    public class ResultData{
        private List<InfoData> records;

        public  class InfoData{
            private String Location;
            private String LocationName;
            private String AddressLine;
            private String Postcode;

            public String getLocation() {
                return Location;
            }

            public void setLocation(String location) {
                Location = location;
            }

            public String getLocationName() {
                return LocationName;
            }

            public void setLocationName(String locationName) {
                LocationName = locationName;
            }

            public String getAddressLine() {
                return AddressLine;
            }

            public void setAddressLine(String addressLine) {
                AddressLine = addressLine;
            }

            public String getPostcode() {
                return Postcode;
            }

            public void setPostcode(String postcode) {
                Postcode = postcode;
            }
        }

        public List<InfoData> getRecords() {
            return records;
        }

    }
}
