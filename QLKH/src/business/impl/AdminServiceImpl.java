// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package business.impl;

import business.IAdminService;
import dao.IAdminDAO;
import dao.impl.AdminDAOImpl;
import model.Admin;

public class AdminServiceImpl implements IAdminService {
    private final IAdminDAO adminDAO = new AdminDAOImpl();

    public AdminServiceImpl() {
    }

    public Admin login(String var1, String var2) {
        return this.adminDAO.login(var1, var2);
    }
}
