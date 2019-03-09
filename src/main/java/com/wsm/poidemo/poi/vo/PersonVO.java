package com.wsm.poidemo.poi.vo;

import java.io.Serializable;

import java.io.Serializable;



    /**
     * 人员基本信息表
     *
     * @author hyf
     * @email pgsky.happy@163.com
     * @date 2019-01-11 16:09:17
     */
    public class PersonVO implements Serializable {
        private static final long serialVersionUID = 1L;

        //人员编码
        private String personNo;
        //姓名
        private String personName;
        //单位及职务
        private String post;
        //身份证号
        private String idNumber;
        private Integer OrderID;

        public String getPersonNo() {
            return personNo;
        }

        public void setPersonNo(String personNo) {
            this.personNo = personNo;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public Integer getOrderID() {
            return OrderID;
        }

        public void setOrderID(Integer orderID) {
            OrderID = orderID;
        }

        @Override
        public String toString() {
            return "PersonVO{" +
                    "personNo='" + personNo + '\'' +
                    ", personName='" + personName + '\'' +
                    ", post='" + post + '\'' +
                    ", idNumber='" + idNumber + '\'' +
                    '}';
        }
    }
