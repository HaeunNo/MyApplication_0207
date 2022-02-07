package com.example.myapplication;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Meta {
    /* SerializedName 에 있는 변수명으로 서버통신! */
    @SerializedName("same_name")
    private SameName sameName;

    @SerializedName("pageable_count")
    private int pageableCount;

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("is_end")
    private boolean isEnd;


    public class SameName {
        private List<String> region;
        private String keyword;
        private String selected_region;

        public List<String> getRegion() {
            return region;
        }

        public void setRegion(List<String> region) {
            this.region = region;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getSelected_region() {
            return selected_region;
        }

        public void setSelected_region(String selected_region) {
            this.selected_region = selected_region;
        }
    }

    public static class Documents {
        private String place_name;
        private String distance;
        private String place_url;
        private String category_name;
        private String address_name;
        private String road_address_name;
        private String id;
        private String phone;
        private String category_group_code;
        private String category_group_name;
        private String x;
        private String y;
        private String sort;
        private int size;

        public Documents(String place_name,String address_name,String phone,String x,String y){
            this.place_name = place_name;
            this.address_name = address_name;
            this.phone = phone;
            this.x = x;
            this.y = y;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPlace_url() {
            return place_url;
        }

        public void setPlace_url(String place_url) {
            this.place_url = place_url;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getRoad_address_name() {
            return road_address_name;
        }

        public void setRoad_address_name(String road_address_name) {
            this.road_address_name = road_address_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCategory_group_code() {
            return category_group_code;
        }

        public void setCategory_group_code(String category_group_code) {
            this.category_group_code = category_group_code;
        }

        public String getCategory_group_name() {
            return category_group_name;
        }

        public void setCategory_group_name(String category_group_name) {
            this.category_group_name = category_group_name;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public class KeyWord {
        private Meta meta;
        private List<Documents> documents;

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public List<Documents> getDocuments() {
            return documents;
        }

        public void setDocuments(List<Documents> documents) {
            this.documents = documents;
        }
    }
}