package com.zxx.diamondlive.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class Live implements Serializable{

    /**
     * result : {"list":[{"created_at":1502852670789,"updated_at":1502852692489,"id":1389339209695233,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499052935&di=4b43075864539f827ea7abe846856be8&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.beicai.com%2Fbm%2Ffiles%2Flogo.jpg","live_name":"哈哈"},"uid":1387930930184194,"user":{"user_data":{"user_name":"wang","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"哈哈哈"},"id":1387930930184194,"created_at":1502768730360,"updated_at":1502768730397}},{"created_at":1502768806207,"updated_at":1502768865257,"id":1387932205252612,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499052935&di=4b43075864539f827ea7abe846856be8&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.beicai.com%2Fbm%2Ffiles%2Flogo.jpg","live_name":"dbbsh"},"uid":1387931819376643,"user":{"user_data":{"user_name":"小元最帅","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"一嘴毛"},"id":1387931819376643,"created_at":1502768783685,"updated_at":1502768783701}},{"created_at":1498448243411,"updated_at":1499503875708,"id":1315445186560001,"data":{"live_name":"北财教研会","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499052935&di=4b43075864539f827ea7abe846856be8&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.beicai.com%2Fbm%2Ffiles%2Flogo.jpg","live_type":1,"status":1},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489639863137&di=d33fd6e1ab1d1959e4d5c011fe0e93d7&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"我就是不一样的烟火a"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489629804387}},{"created_at":1498381803182,"updated_at":1498650393293,"id":1314330508328962,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097746&di=4b17a1b6b9da3a6818177f33fabebf31&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2015%2F04%2F20150408zwc_7%2Fimage021_S.jpg","live_name":"哈哈"},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489639863137&di=d33fd6e1ab1d1959e4d5c011fe0e93d7&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"我就是不一样的烟火a"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489629804387}},{"created_at":1498385217284,"updated_at":1498385243887,"id":1314387785744388,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097746&di=4b17a1b6b9da3a6818177f33fabebf31&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2015%2F04%2F20150408zwc_7%2Fimage021_S.jpg","live_name":"被"},"uid":1297895899856897,"user":{"user_data":{"user_name":"xiaoyuan","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"123456"},"id":1297895899856897,"created_at":1497402224604,"updated_at":1497402224636}},{"created_at":1497402358581,"updated_at":1498383891051,"id":1297898148003842,"data":{"live_name":"fuck","pic":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=763856467,4231999936&fm=23&gp=0.jpg","live_type":1,"status":1},"uid":1297895899856897,"user":{"user_data":{"user_name":"xiaoyuan","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"123456"},"id":1297895899856897,"created_at":1497402224604,"updated_at":1497402224636}},{"created_at":1498381511685,"updated_at":1498381773729,"id":1314325609381889,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097748&di=727e483a3ad03951b22b6b4e51ea2039&imgtype=0&src=http%3A%2F%2Fattimg.dospy.com%2Fimg%2Fday_110719%2F20110719_7f2ed5138ca5ea16b17eKJMcApTUtDJN.jpg","live_name":"贝贝"},"uid":1297895899856897,"user":{"user_data":{"user_name":"xiaoyuan","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"123456"},"id":1297895899856897,"created_at":1497402224604,"updated_at":1497402224636}},{"created_at":1497861916178,"updated_at":1497861927513,"id":1305608251834369,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097749&di=44c8dce130d718071bcefc508c2ffb6f&imgtype=0&src=http%3A%2F%2Fq.115.com%2Fimgload%3Fr%3DADFCE0A0CBCDD585454FECD908C3215E7BDBEC9B%26u%3DwNnOPm%26s%3DsibMXXEQjUHME4hUTToiMQ%26e%3D5%26st%3D0","live_name":"尽量"},"uid":1222813982720099,"user":{"user_data":{"user_name":"邢子岩","avatar":"http://ofdx4t772.bkt.clouddn.com/1222654313955418?imageView2","sign":"我叫邢子岩"},"id":1222813982720099,"created_at":1492926993366,"updated_at":1492926993397}},{"created_at":1497792157976,"updated_at":1497792162290,"id":1304437890023426,"data":{"status":1,"live_type":1,"pic":"http://img4.imgtn.bdimg.com/it/u=3547796456,889162116&fm=23&gp=0.jpg","live_name":"考虑"},"uid":1294993005281281,"user":{"user_data":{"user_name":"1\n1","avatar":"http://img4.imgtn.bdimg.com/it/u=3547796456,889162116&fm=23&gp=0.jpg","sign":"僵僵：\u201c我是单身僵啊，好巧吧，那我们一起吖\u201d。  XX：\u201c那你也吃草啊？\u201d "},"id":1294993005281281,"created_at":1497229198088,"updated_at":1497229198131}},{"created_at":1497596873429,"updated_at":1497597245336,"id":1301161568174084,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097748&di=727e483a3ad03951b22b6b4e51ea2039&imgtype=0&src=http%3A%2F%2Fattimg.dospy.com%2Fimg%2Fday_110719%2F20110719_7f2ed5138ca5ea16b17eKJMcApTUtDJN.jpg","live_name":"测试直播"},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489639863137&di=d33fd6e1ab1d1959e4d5c011fe0e93d7&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"我就是不一样的烟火a"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489629804387}},{"created_at":1497596280227,"updated_at":1497596315111,"id":1301151619284995,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097749&di=91358f4b4f0b5d31b9b0f4563e7d9f55&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3De0256a9a740e0cf3a0f74ef33a47f23d%2Fc63cfaedab64034f0887058ea9c379310b551d33.jpg","live_name":"我叫小源"},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489639863137&di=d33fd6e1ab1d1959e4d5c011fe0e93d7&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"我就是不一样的烟火a"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489629804387}},{"created_at":1489060239349,"updated_at":1497508607412,"id":1157940615643145,"data":{"status":1,"live_name":"小源直播","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127888&di=00f1a4155009eaf200a0e9d354a57953&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0dd7912397dda1444e88deafb0b7d0a20cf4862e.jpg","live_type":1},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489639863137&di=d33fd6e1ab1d1959e4d5c011fe0e93d7&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20160330%2F025d57c8e840fb9aa71d839d368688fe.jpg","sign":"我就是不一样的烟火a"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489629804387}},{"created_at":1497229980646,"updated_at":1497229980646,"id":1295006125064195,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493035940319&di=ca71f33fa997d16b8af53d134b9e252f&imgtype=0&src=http%3A%2F%2Fphotos.pkone.cn%2Fwx%2FSHIRE_IMAGESIGN_MEDIUM%2F2009%2F02%2F10%2F9818df2f-e2e6-4b3c-90d6-89959520284b%2F5ead0bb9-6a58-4670-970f-c4cb9a9a946c.jpg","live_name":"雪尘"},"uid":1223916111593475,"user":{"user_data":{"user_name":"123","avatar":"http://ofdx4t772.bkt.clouddn.com/1223915373395969?imageView2/1/w/300/h/300","sign":"自己写"},"id":1223916111593475,"created_at":1492992685600,"updated_at":1492992685642}},{"created_at":1495108872703,"updated_at":1495108875142,"id":1259419837988873,"data":{"status":1,"live_type":1,"pic":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=763856467,4231999936&fm=23&gp=0.jpg","live_name":"饿了SQL"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108864461,"updated_at":1495108866815,"id":1259419703771144,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097746&di=4b17a1b6b9da3a6818177f33fabebf31&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2015%2F04%2F20150408zwc_7%2Fimage021_S.jpg","live_name":"都是"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108856446,"updated_at":1495108858439,"id":1259419569553415,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097749&di=91358f4b4f0b5d31b9b0f4563e7d9f55&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3De0256a9a740e0cf3a0f74ef33a47f23d%2Fc63cfaedab64034f0887058ea9c379310b551d33.jpg","live_name":"分了手"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108845918,"updated_at":1495108848692,"id":1259419385004038,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097749&di=91358f4b4f0b5d31b9b0f4563e7d9f55&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3De0256a9a740e0cf3a0f74ef33a47f23d%2Fc63cfaedab64034f0887058ea9c379310b551d33.jpg","live_name":"医药"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108804982,"updated_at":1495108807686,"id":1259418697138180,"data":{"status":1,"live_type":1,"pic":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=763856467,4231999936&fm=23&gp=0.jpg","live_name":"哦KTV"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108755167,"updated_at":1495108762556,"id":1259417875054594,"data":{"status":1,"live_type":1,"pic":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=763856467,4231999936&fm=23&gp=0.jpg","live_name":"阿狸"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}},{"created_at":1495108740295,"updated_at":1495108743392,"id":1259417623396353,"data":{"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489589097749&di=44c8dce130d718071bcefc508c2ffb6f&imgtype=0&src=http%3A%2F%2Fq.115.com%2Fimgload%3Fr%3DADFCE0A0CBCDD585454FECD908C3215E7BDBEC9B%26u%3DwNnOPm%26s%3DsibMXXEQjUHME4hUTToiMQ%26e%3D5%26st%3D0","live_name":"牛"},"uid":1256445036265473,"user":{"user_data":{"user_name":"a","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"qwe"},"id":1256445036265473,"created_at":1494931560688,"updated_at":1494931560835}}]}
     * error_code : 0
     */

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
            /**
             * created_at : 1502852670789
             * updated_at : 1502852692489
             * id : 1389339209695233
             * data : {"status":1,"live_type":1,"pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499052935&di=4b43075864539f827ea7abe846856be8&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.beicai.com%2Fbm%2Ffiles%2Flogo.jpg","live_name":"哈哈"}
             * uid : 1387930930184194
             * user : {"user_data":{"user_name":"wang","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"哈哈哈"},"id":1387930930184194,"created_at":1502768730360,"updated_at":1502768730397}
             */

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
                 * live_type : 1
                 * pic : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499052935&di=4b43075864539f827ea7abe846856be8&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.beicai.com%2Fbm%2Ffiles%2Flogo.jpg
                 * live_name : 哈哈
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
                 * user_data : {"user_name":"wang","avatar":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg","sign":"哈哈哈"}
                 * id : 1387930930184194
                 * created_at : 1502768730360
                 * updated_at : 1502768730397
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
                     * user_name : wang
                     * avatar : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2608404801,4206400884&f=23&gp=0.jpg
                     * sign : 哈哈哈
                     */

                    private String user_name;
                    private String avatar;
                    private String sign;

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
    }
}
