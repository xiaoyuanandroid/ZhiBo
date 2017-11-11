package com.zxx.diamondlive.bean;

import java.io.Serializable;

/**
 * Created by xiaoyuan on 16/11/10.
 */
public class UploadBean implements Serializable {

    /**
     * result : {"thumbnail_pic":"http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2/1/w/300/h/300","bmiddle_pic":"http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2/1/w/600/h/600","original_pic":"http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2","file_download_url":"http://ofdx4t772.bkt.clouddn.com/1404567653777411?attname=1404567653777411"}
     * error_code : 0
     */

    private ResultBean result;
    private int error_code;
    private String error_msg;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "thumbnail_pic='" + thumbnail_pic + '\'' +
                    ", bmiddle_pic='" + bmiddle_pic + '\'' +
                    ", original_pic='" + original_pic + '\'' +
                    ", file_download_url='" + file_download_url + '\'' +
                    '}';
        }

        /**
         * thumbnail_pic : http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2/1/w/300/h/300
         * bmiddle_pic : http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2/1/w/600/h/600
         * original_pic : http://ofdx4t772.bkt.clouddn.com/1404567653777411?imageView2
         * file_download_url : http://ofdx4t772.bkt.clouddn.com/1404567653777411?attname=1404567653777411
         */

        private String thumbnail_pic;
        private String bmiddle_pic;
        private String original_pic;
        private String file_download_url;

        public String getThumbnail_pic() {
            return thumbnail_pic;
        }

        public void setThumbnail_pic(String thumbnail_pic) {
            this.thumbnail_pic = thumbnail_pic;
        }

        public String getBmiddle_pic() {
            return bmiddle_pic;
        }

        public void setBmiddle_pic(String bmiddle_pic) {
            this.bmiddle_pic = bmiddle_pic;
        }

        public String getOriginal_pic() {
            return original_pic;
        }

        public void setOriginal_pic(String original_pic) {
            this.original_pic = original_pic;
        }

        public String getFile_download_url() {
            return file_download_url;
        }

        public void setFile_download_url(String file_download_url) {
            this.file_download_url = file_download_url;
        }
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "result=" + result +
                ", error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
