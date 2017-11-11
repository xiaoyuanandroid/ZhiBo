package com.zxx.diamondlive.bean;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class LoginReposeBean {

    /**
     * result : {"created_at":1503627445060,"updated_at":1503627445150,"id":1402337777221635,"user_data":{"phone":"13212345678","user_name":"123","avatar":"https://timgsa.baidu.com/timg?image%26quality=80%26size=b9999_10000%26sec=1489639863137%26di=d33fd6e1ab1d1959e4d5c011fe0e93d7%26imgtype=0%26src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"qwe"}}
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    private String request;

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
        /**
         * created_at : 1503627445060
         * updated_at : 1503627445150
         * id : 1402337777221635
         * user_data : {"phone":"13212345678","user_name":"123","avatar":"https://timgsa.baidu.com/timg?image%26quality=80%26size=b9999_10000%26sec=1489639863137%26di=d33fd6e1ab1d1959e4d5c011fe0e93d7%26imgtype=0%26src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"qwe"}
         */

        private long created_at;
        private long updated_at;
        private long id;
        private UserDataBean user_data;

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

        public UserDataBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserDataBean user_data) {
            this.user_data = user_data;
        }

        public static class UserDataBean {
            /**
             * phone : 13212345678
             * user_name : 123
             * avatar : https://timgsa.baidu.com/timg?image%26quality=80%26size=b9999_10000%26sec=1489639863137%26di=d33fd6e1ab1d1959e4d5c011fe0e93d7%26imgtype=0%26src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg
             * sign : qwe
             */

            private String phone;
            private String user_name;
            private String avatar;
            private String sign;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
