package com.blackchopper.demo_onlite;

import com.hacknife.onlite.annotation.Column;
import com.hacknife.onlite.annotation.Table;

import java.util.List;

@Table("table_DDDDDDDDDDDDDDDD")
public class D {

    @Column(name = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC")
    String CreateAt;

    @Column
    String Description;

    @Column
    List<V> FirmwareVersion;

    @Column
    Boolean IsFirmwareUpdate;

    @Column
    Boolean IsMFICertification;

    @Column
    Boolean IsPublish;

    @Column
    String Name;

    @Column
    String ProductAppName;

    @Column
    String ProductDesc;

    @Column
    String ProductImageLink;

    @Column
    String ProductManualLink;

    @Column
    String ProductModel;

    @Column
    String ProductName;

    @Column(name = "__________ProductReadUuid___________________")
    String ProductReadUuid;

    @Column
    String ProductSearchUuid;

    @Column
    String ProductServiceUuid;

    @Column
    String ProductWriteUuid;

    @Column
    String UpdateAt;

    @Column
    Integer __v;

    @Column
    String _id;

    @Column
    Float _c;

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<V> getFirmwareVersion() {
        return FirmwareVersion;
    }

    public void setFirmwareVersion(List<V> firmwareVersion) {
        FirmwareVersion = firmwareVersion;
    }

    public Boolean getIsFirmwareUpdate() {
        return IsFirmwareUpdate;
    }

    public void setIsFirmwareUpdate(Boolean firmwareUpdate) {
        IsFirmwareUpdate = firmwareUpdate;
    }

    public Boolean getIsMFICertification() {
        return IsMFICertification;
    }

    public void setIsMFICertification(Boolean MFICertification) {
        IsMFICertification = MFICertification;
    }

    public Boolean getIsPublish() {
        return IsPublish;
    }

    public void setIsPublish(Boolean publish) {
        IsPublish = publish;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProductAppName() {
        return ProductAppName;
    }

    public void setProductAppName(String productAppName) {
        ProductAppName = productAppName;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductImageLink() {
        return ProductImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        ProductImageLink = productImageLink;
    }

    public String getProductManualLink() {
        return ProductManualLink;
    }

    public void setProductManualLink(String productManualLink) {
        ProductManualLink = productManualLink;
    }

    public String getProductModel() {
        return ProductModel;
    }

    public void setProductModel(String productModel) {
        ProductModel = productModel;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductReadUuid() {
        return ProductReadUuid;
    }

    public void setProductReadUuid(String productReadUuid) {
        ProductReadUuid = productReadUuid;
    }

    public String getProductSearchUuid() {
        return ProductSearchUuid;
    }

    public void setProductSearchUuid(String productSearchUuid) {
        ProductSearchUuid = productSearchUuid;
    }

    public String getProductServiceUuid() {
        return ProductServiceUuid;
    }

    public void setProductServiceUuid(String productServiceUuid) {
        ProductServiceUuid = productServiceUuid;
    }

    public String getProductWriteUuid() {
        return ProductWriteUuid;
    }

    public void setProductWriteUuid(String productWriteUuid) {
        ProductWriteUuid = productWriteUuid;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        UpdateAt = updateAt;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Float get_c() {
        return _c;
    }

    public void set_c(Float _c) {
        this._c = _c;
    }
}
