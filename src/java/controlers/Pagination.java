/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.util.*;
import beans.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marko
 */

@ManagedBean
@SessionScoped
public class Pagination {
    
    private List<Kompanija> companyList;

    public List<Kompanija> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Kompanija> companyList) {
        this.companyList = companyList;
    }
    
    
    
}
