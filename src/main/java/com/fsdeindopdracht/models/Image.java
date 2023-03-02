package com.fsdeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
@Getter
@Setter

@Entity
@Table(name= "images")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] docFile;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "product_name")
    private Product product;

    public Image() {
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
