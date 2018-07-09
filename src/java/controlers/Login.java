package controlers;

import database.HibernateUtil;
import beans.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@ManagedBean
@SessionScoped
public class Login {

    private String username;
    private String password;
    private String updatedPass;
    private String error;
    private String typeOfUser;

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getUpdatedPass() {
        return updatedPass;
    }

    public void setUpdatedPass(String updatedPass) {
        this.updatedPass = updatedPass;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void login() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        session.beginTransaction();
        //    System.out.println(this.username + " " + this.password);    
        try {

            byte[] bytesOfMessage = password.getBytes("UTF-8");
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < thedigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & thedigest[i]));
            }

            Criteria query = session.createCriteria(User.class);
            User user = (User) query.add(Restrictions.eq("username", username)).add(Restrictions.eq("password", hexString.toString())).uniqueResult();
            session.getTransaction().commit();
            if (user != null) {
                this.typeOfUser = user.getType();
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("user", user);
                try {
                    if (user.getType().equals("User")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("userpage.xhtml");
                    }
                    if (user.getType().equals("Admin")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("adminpage.xhtml");
                    }
                    if (user.getType().equals("IT")) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("itpage.xhtml");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                FacesContext.getCurrentInstance().responseComplete();
                this.error = "";
            }  
            this.error = "No such user";
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.error = "Error occurred";
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public String logout() {
        System.out.println("LOG OUT");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public void updatePass() {
        System.out.println(username + updatedPass);
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            boolean check = passCheck(updatedPass);
            if (!check) {
                throw new Exception();
            }
            byte[] bytesOfMessage = updatedPass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < thedigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & thedigest[i]));
            }

            tx = session.beginTransaction();
            User user = (User) session.get(User.class, username);
            if (user == null) throw new Exception();
            user.setPassword(hexString.toString());
            session.update(user);
            tx.commit();
            this.error = "Password successfully updated";

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            this.error = "Error occurred";
        } finally {
            session.close();
        }
    }

    public void logInAsGuest() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("logedin.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    private boolean passCheck(String pass) {

        int capitalCount = 0;
        int notCapitalCount = 0;
        int numberCount = 0;
        int specialChar = 0;

        int length = pass.length();
        if (length < 8 || length > 12) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(pass.charAt(i))) {
                capitalCount++;
            }
        }
        if (capitalCount == 0) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Character.isLowerCase(pass.charAt(i))) {
                notCapitalCount++;
            }
        }
        if (notCapitalCount < 3) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Character.isDigit(pass.charAt(i))) {
                numberCount++;
            }
        }
        if (numberCount == 0) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isLetter(pass.charAt(i)) && !Character.isDigit(pass.charAt(i))) {
                specialChar++;
            }
        }
        if (specialChar == 0) {
            return false;
        }

        for (int i = 0; i < length - 2; i++) {
            if (Character.isLetter(pass.charAt(i)) && Character.isLetter(pass.charAt(i + 1)) && Character.isLetter(pass.charAt(i + 2))) {
                return false;
            }
        }

        return true;
    }
}
