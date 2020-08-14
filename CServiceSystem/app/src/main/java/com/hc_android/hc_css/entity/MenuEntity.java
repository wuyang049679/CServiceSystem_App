package com.hc_android.hc_css.entity;

import java.util.List;

public class MenuEntity {


    /**
     * head_content : 菜单标题
     * tail_content :
     * list : [{"content":"点击1","type":"message","value":{"message":"顾客消息"}},{"content":"点击跳转","type":"link","value":{"link":"http://跳转"}},{"content":"点击转接","type":"message","value":{"message":"转客服"}},{"content":"点击2","type":"message","value":{"message":"安分分"}}]
     */

    private String head_content;
    private String tail_content;
    private List<ListBean> list;

    public String getHead_content() {
        return head_content;
    }

    public void setHead_content(String head_content) {
        this.head_content = head_content;
    }

    public String getTail_content() {
        return tail_content;
    }

    public void setTail_content(String tail_content) {
        this.tail_content = tail_content;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * content : 点击1
         * type : message
         * value : {"message":"顾客消息"}
         */

        private String content;
        private String type;
        private ValueBean value;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ValueBean getValue() {
            return value;
        }

        public void setValue(ValueBean value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * message : 顾客消息
             */

            private String message;
            private String link;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
