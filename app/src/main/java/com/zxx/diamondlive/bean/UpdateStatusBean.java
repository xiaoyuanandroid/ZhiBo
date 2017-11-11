package com.zxx.diamondlive.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class UpdateStatusBean {

    /**
     * result : [{"created_at":1504056721436,"updated_at":1504056761703,"id":1409539833397252,"data":{"live_name":"哈哈","pic":"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E9%AB%98%E6%B8%85%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2376399524,1027148780&os=3394626356,734881254&simid=3381914893,452118831&pn=16&rn=1&di=2883016510&ln=1974&fr=&fmq=1504054920740_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=480&height=300&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_1_2901919701D617510272_23.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1504054982945^3_1349X635%1","live_type":1,"status":1},"uid":1406568118681603}]
     * error_code : 0
     */

    private int error_code;
    private String error_msg;
    private List<NotUpdatedBean> not_updated;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    private List<ResultBean> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * created_at : 1504056721436
         * updated_at : 1504056761703
         * id : 1409539833397252
         * data : {"live_name":"哈哈","pic":"http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E9%AB%98%E6%B8%85%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2376399524,1027148780&os=3394626356,734881254&simid=3381914893,452118831&pn=16&rn=1&di=2883016510&ln=1974&fr=&fmq=1504054920740_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=480&height=300&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_1_2901919701D617510272_23.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1504054982945^3_1349X635%1","live_type":1,"status":1}
         * uid : 1406568118681603
         */

        private long created_at;
        private long updated_at;
        private long id;
        private DataBean data;
        private long uid;

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public static class DataBean {
            /**
             * live_name : 哈哈
             * pic : http://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E9%AB%98%E6%B8%85%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2376399524,1027148780&os=3394626356,734881254&simid=3381914893,452118831&pn=16&rn=1&di=2883016510&ln=1974&fr=&fmq=1504054920740_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=480&height=300&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fm.qqzhi.com%2Fupload%2Fimg_1_2901919701D617510272_23.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1504054982945^3_1349X635%1
             * live_type : 1
             * status : 1
             */

            private String live_name;
            private String pic;
            private int live_type;
            private int status;

            public String getLive_name() {
                return live_name;
            }

            public void setLive_name(String live_name) {
                this.live_name = live_name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getLive_type() {
                return live_type;
            }

            public void setLive_type(int live_type) {
                this.live_type = live_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }

    public List<NotUpdatedBean> getNot_updated() {
        return not_updated;
    }

    public void setNot_updated(List<NotUpdatedBean> not_updated) {
        this.not_updated = not_updated;
    }

    public static class NotUpdatedBean {
        /**
         * id : 0
         * error : no result found by given conditions
         */

        private int id;
        private String error;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
