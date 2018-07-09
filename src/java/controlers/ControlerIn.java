package controlers;

import java.security.MessageDigest;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.model.UploadedFile;

import beans.User;
import beans.Waiting;
import database.HibernateUtil;

@ManagedBean
@SessionScoped
public class ControlerIn {

    private String userUsername;
    private String userPass;
    private String userName;
    private String userSurname;
    private String userInstitution;
    private String userEmail;
    private String userGender;
    private String userLinkedIn;
    private Date userDate;
    private String file;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserInstitution() {
        return userInstitution;
    }

    public void setUserInstitution(String userInstitution) {
        this.userInstitution = userInstitution;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserLinkedIn() {
        return userLinkedIn;
    }

    public void setUserLinkedIn(String userLinkedIn) {
        this.userLinkedIn = userLinkedIn;
    }

    public void createAccount() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        System.out.println(userDate);
        try {
            boolean check = passCheck(userPass);
            if (!check) {
                throw new Exception();
            }
            byte[] bytesOfMessage = userPass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < thedigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & thedigest[i]));
            }

            tx = session.beginTransaction();
            Waiting w = new Waiting(userUsername, hexString.toString(), userName, userSurname, userInstitution, userEmail, userGender, userLinkedIn, userDate);
            w.setImage(this.file);
            //User u = new User(userUsername,hexString.toString(),userName,userSurname,userInstitution,userEmail,userGender,userLinkedIn,userDate,"User");
            session.save(w);
            this.msg = "Account created";
            clear();
            tx.commit();
        } catch (Exception e) {
            this.msg = "Error occurred";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void createAccountAdmin() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        System.out.println(userDate);
        try {
            boolean check = passCheck(userPass);
            if (!check) {
                throw new Exception();
            }
            byte[] bytesOfMessage = userPass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < thedigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & thedigest[i]));
            }

            tx = session.beginTransaction();
            User u = new User(userUsername, hexString.toString(), userName, userSurname, userInstitution, userEmail, userGender, userLinkedIn, userDate, "User");
            u.setImage(this.file);
            session.save(u);
            this.msg = "Account created";
            clear();
            tx.commit();
        } catch (Exception e) {
            this.msg = "Error occurred";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
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

    private void clear() {
        this.userUsername = null;
        this.userPass = null;
        this.userName = null;
        this.userSurname = null;
        this.userInstitution = null;
        this.userEmail = null;
        this.userGender = null;
        this.userLinkedIn = null;
        this.userDate = null;
        this.file = null;
    }

}
