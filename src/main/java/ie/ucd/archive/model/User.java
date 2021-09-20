package ie.ucd.archive.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    private String address;
    @Column
    private Integer membership_years;
    @Column
    private Boolean is_full_member;
    @Column
    private Boolean is_child;
    @OneToMany(mappedBy = "user")
    private List<Transaction> all_transactions;

    public User() {
    }


    public User( String username, String password, String role, String address, Integer membership_years, Boolean is_full_member, Boolean is_child) {

        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
        this.membership_years = membership_years;
        this.is_full_member = is_full_member;
        this.is_child = is_child;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMembership_years() {
        return membership_years;
    }

    public void setMembership_years(Integer membership_years) {
        this.membership_years = membership_years;
    }

    public Boolean getIs_full_member() {
        return is_full_member;
    }

    public void setIs_full_member(Boolean is_full_member) {
        this.is_full_member = is_full_member;
    }

    public Boolean getIs_child() {
        return is_child;
    }

    public void setIs_child(Boolean is_child) {
        this.is_child = is_child;
    }

    public List<Transaction> getAll_transactions() {
        return all_transactions;
    }

    public void setAll_transactions(List<Transaction> all_transactions) {
        this.all_transactions.clear();
        for (Transaction transaction : all_transactions) {
            transaction.setUser(this);
            this.all_transactions.add(transaction);
        }
    }

    public String user_details() {
        String details = "";
        details += "USER DETAILS :\n";
        details += "-NAME " + this.username + "\n";
        details += "-ROLE " + this.role + "\n";
        details += "-PASSWORD " + this.password + "\n";
        if (all_transactions != null) {
            details += this.all_transactions.isEmpty() + "\n";
            for (Transaction element : this.all_transactions) {
                details += "--T" + element.transaction_details();
            }
            details += "\n";
        }
        return details;
    }
}
