package ie.ucd.archive.model;
// I survived pepper spray and mustard gas, who am I?........................................................................................................A seasoned veteran ^.^

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne
    private Media media;
    @Column
    private Date created_date;
    @Column
    private Date return_date;
    @Column
    private String type; //"LOAN" or "RESERVATION"
    @Column
    private Boolean active;

    public Transaction() {
        this.created_date = new Date();
        this.return_date = this.addLoanDuration(created_date.getTime());
    }

    public Transaction(User user, Media media, String type, Boolean active) {
        this.user = user;
        this.media = media;
        this.created_date = new Date();
        this.return_date = this.addLoanDuration(created_date.getTime());
        this.type = type;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Date getCreated_date() {
        return created_date;
    }

     public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date addLoanDuration(Long current_milisconds) {
        return new Date(current_milisconds + 604800000);
    }

    public String transaction_details() {
        String details = "";
        details += "TRANSACTION DETAILS :\n";
        details += "-TRANS_ID: " + this.id + "\n";
        details += "-USER: " + "\n\t" + this.user.getId() + "\n\t" + this.user.getUsername() + "\n";
        details += "-MEDIA: " + "\n\t" + this.media.getId() + "\n\t" + this.media.getTitle() + "\n";
        details += "-CREATED DATE: " + "\n\t" + this.created_date + "\n";
        details += "-RETURN DATE: " + "\n\t" + this.return_date + "\n";
        details += "\n";
        return details;
    }
}
