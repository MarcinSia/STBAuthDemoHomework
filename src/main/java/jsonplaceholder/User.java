package jsonplaceholder;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String name;
    private String userName;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
}
