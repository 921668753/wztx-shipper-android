package com.ruitukeji.zwbh.entity.main.cargoinformation;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class DistanceBean {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * results : [{"origin_id":"1","dest_id":"1","distance":"260291","duration":"14520"}]
     */

    private String status;
    private String info;
    private String infocode;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public class ResultsBean {
        /**
         * origin_id : 1
         * dest_id : 1
         * distance : 260291
         * duration : 14520
         */

        private String origin_id;
        private String dest_id;
        private String distance;
        private String duration;

        public String getOrigin_id() {
            return origin_id;
        }

        public void setOrigin_id(String origin_id) {
            this.origin_id = origin_id;
        }

        public String getDest_id() {
            return dest_id;
        }

        public void setDest_id(String dest_id) {
            this.dest_id = dest_id;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
