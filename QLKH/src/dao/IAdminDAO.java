package dao;

import model.Admin;

public interface IAdminDAO {
    Admin login(String username,String password);
}
