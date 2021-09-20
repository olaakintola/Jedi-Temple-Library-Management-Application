package ie.ucd.archive.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String title;
    @Column
    private String mediatext;
    @Column
    private String mediatype;
    @Column
    private Boolean is_child_suitable;
    @Column
    private Boolean is_loanable;
    @Column
    private Boolean is_reserved;
    @Column
    private Boolean isviewable;
    @Column
    private Integer version;
    @Column
    @CreationTimestamp
    private Date created_date;
    @Column
    @CreationTimestamp
    private Date last_updated_date;
    @OneToMany(mappedBy = "media")
    private List<Transaction> media_transactions;

    public Media() {
        this.is_loanable = true;
        this.is_reserved = false;
        this.is_child_suitable = false;
        this.isviewable = true;
    }

    public Media(String tile, String media_text, String media_type) {
        this.title = tile;
        this.mediatext = media_text;
        this.mediatype = media_type;
        this.is_loanable = true;
        this.is_reserved = false;
        this.is_child_suitable = false;
        this.isviewable = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediatext() {
        return mediatext;
    }

    public void setMediatext(String mediatext) {
        this.mediatext = mediatext;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public Boolean getIs_child_suitable() {
        return is_child_suitable;
    }

    public void setIs_child_suitable(Boolean is_child_suitable) {
        this.is_child_suitable = is_child_suitable;
    }

    public Boolean getIs_loanable() {
        return is_loanable;
    }

    public void setIs_loanable(Boolean is_loanable) {
        this.is_loanable = is_loanable;
    }

    public Boolean getIs_reserved() {
        return is_reserved;
    }

    public void setIs_reserved(Boolean is_reserved) {
        this.is_reserved = is_reserved;
    }

    public Boolean getIsviewable() {
        return isviewable;
    }

    public void setIsviewable(Boolean isviewable) {
        this.isviewable = isviewable;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(Date last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public List<Transaction> getMedia_transactions() {
        return media_transactions;
    }

    public void setMedia_transactions(List<Transaction> media_transactions) {
        this.media_transactions = media_transactions;
    }

    public String media_details() {
        String details = "";
        details += "MEDIA DETAILS :\n";
        details += "-ID " + this.id + "\n";
        details += "-TITLE " + this.title + "\n";
        details += "-TYPE " + this.mediatype + "\n";
        details += "-VIEW STATUS" + this.getIsviewable();
        details += "\n";
        return details;
    }
}
