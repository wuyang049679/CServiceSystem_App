package com.hc_android.hc_css.entity;

import java.io.Serializable;
import java.util.List;

public class FileEntity implements Serializable {

    /**
     * type : text/plain
     * size : 178
     * name : USERNAME.txt
     * fileUrl : FgEjdz1hH31cMdaQxhmpSAF2VsR7.txt
     */


    private String type;
    private String name;
    private String fileUrl;
    private String picUrl;
    private int height;
    private int width;
    private long size;
    private FileEntity thumbImg;
    private float duration;
    private String key;
    private List<ListBean> list;
    private String dialogId;
    private int score;
    private String content;
    private boolean isLocalPath;
    private String bucket;


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public boolean isLocalPath() {
        return isLocalPath;
    }

    public void setLocalPath(boolean localPath) {
        isLocalPath = localPath;
    }

    /**
     * dialogId : 5df6e28dfafe723e3b6264cf
     * score : 2
     * content : 8aa
     */






    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public FileEntity getThumbImg() {
        return thumbImg;
    }

    public void setThumbImg(FileEntity thumbImg) {
        this.thumbImg = thumbImg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class ListBean implements Serializable{
        /**
         * title : 标题
         * picurl : FuHntaMx3W1Jsp8FjHk4T_R3iOaf.jpg
         * description : 描述价格
         * price : 100元
         * url : 这里是网站
         */

        private String title;
        private String picurl;
        private String description;
        private String price;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class VideoEntity implements Serializable{

        /**
         * type : video/quicktime
         * size : 927096
         * name : FlWpiYudwmKda5r3W1o54R3oGsxr.MOV
         * fileUrl : FlWpiYudwmKda5r3W1o54R3oGsxr.MOV
         * thumbImg : {"width":360,"height":480}
         */

        private String type;
        private long size;
        private String name;
        private String fileUrl;
        private ThumbImgBean thumbImg;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public ThumbImgBean getThumbImg() {
            return thumbImg;
        }

        public void setThumbImg(ThumbImgBean thumbImg) {
            this.thumbImg = thumbImg;
        }

        public static class ThumbImgBean {
            /**
             * width : 360
             * height : 480
             */

            private int width;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }

    public static class ImageEntity implements Serializable{

        /**
         * type : image/gif
         * size : 416761
         * width : 600
         * height : 550
         * name : timg.gif
         * picUrl : Frc8PxhJiukEJCgpcgRN-PjwW9KX.gif
         */

        private String type;
        private long size;
        private int width;
        private int height;
        private String name;
        private String picUrl;
        private String bucket;


        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }

    public static class VoiceEntity{

        /**
         * type : audio/mp3
         * size : 15703
         * key : FjWVDMthrFlbPHNi-TKhxoY3uMwq.mp3
         * duration : 7.800000
         */

        private String type;
        private long size;
        private String key;
        private String duration;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
