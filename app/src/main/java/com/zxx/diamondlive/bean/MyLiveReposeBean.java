package com.zxx.diamondlive.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class MyLiveReposeBean {
    private ResultBean result;
    private int error_code;

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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            private long created_at;
            private long updated_at;
            private long id;
            private DataBean data;
            private long uid;
            private UserBean user;

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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class DataBean {
                /**
                 * status : 1
                 * live_type : 2
                 * pic : http://img1.imgtn.bdimg.com/it/u=2376399524,1027148780&fm=214&gp=0.jpg
                 * live_name : 把
                 */

                private int status;
                private int live_type;
                private String pic;
                private String live_name;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getLive_type() {
                    return live_type;
                }

                public void setLive_type(int live_type) {
                    this.live_type = live_type;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getLive_name() {
                    return live_name;
                }

                public void setLive_name(String live_name) {
                    this.live_name = live_name;
                }
            }

            public static class UserBean {
                /**
                 * user_data : {"user_name":"123","avatar":
                 * "http://ofdx4t772.bkt.clouddn.com/1406568101904386?imageView2/1/w/300/h/300","sign":"啊",
                 * "phone":"13212349876"}
                 * id : 1406568118681603
                 * created_at : 1503879593148
                 * updated_at : 1503879593185
                 */

                private UserDataBean user_data;
                private long id;
                private long created_at;
                private long updated_at;

                public UserDataBean getUser_data() {
                    return user_data;
                }

                public void setUser_data(UserDataBean user_data) {
                    this.user_data = user_data;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

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

                public static class UserDataBean {
                    /**
                     * user_name : 123
                     * avatar : http://ofdx4t772.bkt.clouddn.com/1406568101904386?imageView2/1/w/300/h/300
                     * sign : 啊
                     * phone : 13212349876
                     */

                    private String user_name;
                    private String avatar;
                    private String sign;
                    private String phone;

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

                    public String getPhone() {
                        return phone;
                    }

                    public void setPhone(String phone) {
                        this.phone = phone;
                    }
                }
            }
        }
    }
}
