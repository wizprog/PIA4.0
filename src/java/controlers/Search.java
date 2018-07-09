package controlers;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import beans.Advertisement;
import beans.Kompanija;
import beans.Lectures;
import beans.Package;
import beans.User;
import database.HibernateUtil;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Search {

    private String company;
    private List<Kompanija> companyList;
    private List<Package> packageList;
    private List<Advertisement> adds;
    private List<Lectures> lect;
    private List<Lectures> pastlect;
    private String option;

    public List<Lectures> getPastlect() {
        return pastlect;
    }

    public void setPastlect(List<Lectures> pastlect) {
        this.pastlect = pastlect;
    }

    public List<Lectures> getLect() {
        return lect;
    }

    public void setLect(List<Lectures> lect) {
        this.lect = lect;
    }

    public List<Advertisement> getAdds() {
        return adds;
    }

    public void setAdds(List<Advertisement> adds) {
        this.adds = adds;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<Kompanija> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Kompanija> companyList) {
        this.companyList = companyList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    public void getCompanies(String searchString) {
        List<Kompanija> ls = new ArrayList<Kompanija>();
        List<Package> ls2 = new ArrayList<Package>();

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String help = "";
            if (searchString.length() != 0) {
                help = "WHERE Name LIKE " + searchString + "%";
            }
            
            Criteria cr = session.createCriteria(Kompanija.class);
            cr.add(Restrictions.like("name", "%"+searchString+"%")); 
            List companies = cr.list();
            
            
            List packages = session.createQuery("FROM Package").list();

            for (Iterator iterator = companies.iterator(); iterator.hasNext();) {
                Kompanija company = (Kompanija) iterator.next();
                ls.add(company);
            }

            for (Iterator iterator2 = packages.iterator(); iterator2.hasNext();) {
                Package pack = (Package) iterator2.next();
                ls2.add(pack);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        this.companyList = ls;
        this.packageList = ls2;
    }

    public void getPackage() {
        System.out.println("PAKETI Opcija:" + this.option);
        List<Package> ls = new ArrayList<Package>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT DISTINCT package.PackName,package.Price FROM package,kompanija,moneycontract,donationcontract WHERE (moneycontract.IdP = package.IdP OR donationcontract.IdP=package.IdP) and moneycontract.PIB=kompanija.PIB";

            if (this.option != null && this.option.equals("1")) {
                sql = "SELECT DISTINCT package.PackName,package.Price FROM package,kompanija,moneycontract,donationcontract WHERE (moneycontract.IdP = package.IdP OR donationcontract.IdP=package.IdP) and moneycontract.PIB=kompanija.PIB ORDER BY package.PackName ASC";
            }
            if (this.option != null && this.option.equals("2")) {
                sql = "SELECT DISTINCT package.PackName,package.Price FROM package,kompanija,moneycontract,donationcontract WHERE (moneycontract.IdP = package.IdP OR donationcontract.IdP=package.IdP) and moneycontract.PIB=kompanija.PIB ORDER BY package.PackName DESC";
            }
            if (this.option != null && this.option.equals("3")) {
                sql = "SELECT DISTINCT package.PackName,package.Price FROM package,kompanija,moneycontract,donationcontract WHERE (moneycontract.IdP = package.IdP OR donationcontract.IdP=package.IdP) and moneycontract.PIB=kompanija.PIB ORDER BY package.Price ASC";
            }
            if (this.option != null && this.option.equals("4")) {
                sql = "SELECT DISTINCT package.PackName,package.Price FROM package,kompanija,moneycontract,donationcontract WHERE (moneycontract.IdP = package.IdP OR donationcontract.IdP=package.IdP) and moneycontract.PIB=kompanija.PIB ORDER BY package.Price DESC";
            }
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            for (Object object : data) {
                Map row = (Map) object;
                String name = (String) row.get("PackName");
                int price = (int) row.get("Price");
                Package p = new Package(name, price, 0, 0);
                ls.add(p);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        this.packageList = ls;
    }

    public void getMeAdds() {
        List<Advertisement> ls = new ArrayList<Advertisement>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Advertisement.class);
            cr.addOrder(Order.desc("enterdate"));
            List addList = cr.list();

            for (Iterator iterator = addList.iterator(); iterator.hasNext();) {
                Advertisement a = (Advertisement) iterator.next();
                System.out.println("Reklama: " + a.getName());
                ls.add(a);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        this.adds = ls;
    }

    public void getMeLectures() {
        List<Lectures> ls = new ArrayList<Lectures>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Lectures.class);

            cr.addOrder(Order.desc("date"));

            Date date = new Date();
            cr.add(Restrictions.gt("date", date));

            List lectList = cr.list();

            for (Iterator iterator = lectList.iterator(); iterator.hasNext();) {
                Lectures a = (Lectures) iterator.next();
                ls.add(a);
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        this.lect = ls;
        this.passedLectures();
    }

    public void passedLectures() {
        List<Lectures> ls = new ArrayList<Lectures>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Lectures.class);

            Date date = new Date();
            cr.add(Restrictions.lt("date", date));

            List lectList = cr.list();

            for (Iterator iterator = lectList.iterator(); iterator.hasNext();) {
                Lectures a = (Lectures) iterator.next();
                System.out.println(a.getName());
                ls.add(a);
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        this.pastlect = ls;
    }

}
