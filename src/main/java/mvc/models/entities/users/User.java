package mvc.models.entities.users;

import java.math.BigDecimal;

public class User {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private BigDecimal balance;
    private Role role;
    private Long id;

    public User(String login, String password, String firstName, String lastName, String middleName,BigDecimal balance,Role role, Long id) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.balance = balance;
        this.role = role;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getId() { return id; }

    public Role getRole(){ return role; }

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

    public boolean isAdmin(){
        return role == Role.ADMIN;
    }
}
