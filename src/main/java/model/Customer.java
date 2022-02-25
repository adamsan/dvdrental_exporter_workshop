package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Customer {
    public Integer id;
    public String name;
    public String address;
    public String zipCode;
    public String phone;
    public String city;
    public String country;
    public String notes;
    public Integer sid;
}
