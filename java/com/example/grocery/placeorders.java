package com.example.grocery;

public class placeorders {
String location;
    String oid;
    String ordered;
    String shipped;
    String intransit;
    String checkpoint;
    String out_for_delivery;
  //  String oid;
String uref;
String tprice;
    public placeorders(String location, String oid, String ordered, String shipped, String intransit, String out_for_delivery,String checkpoint,String uref,String tprice) {
        this.location = location;
        this.oid = oid;
        this.ordered = ordered;
        this.shipped = shipped;
        this.intransit = intransit;
        this.out_for_delivery = out_for_delivery;
        this.checkpoint=checkpoint;
        this.uref=uref;
        this.tprice=tprice;
     //   this.oid=oid;

    }

    public placeorders() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
    public String getUref() {
        return uref;
    }

    public void setUref(String uref) {
        this.uref = uref;
    }
    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getCheckpoint() {
        return checkpoint;
    }
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getIntransit() {
        return intransit;
    }

    public void setIntransit(String intransit) {
        this.intransit = intransit;
    }

    public String getOut_for_delivery() {
        return out_for_delivery;
    }

    public void setOut_for_delivery(String out_for_delivery) {
        this.out_for_delivery = out_for_delivery;
    }
}
