/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

/**
 *
 * @author Marko
 */
import beans.Advertisement;
import beans.Kompanija;
import database.HibernateUtil;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

@ManagedBean(name = "basicTimelineView")
@ViewScoped
public class BasicTimelineView implements Serializable {

    private TimelineModel model;

    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;

    @PostConstruct
    protected void initialize() {
        model = new TimelineModel();

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.Name,package.PackName,donationcontract.DueDate FROM kompanija,donationcontract,package\n"
                    + "WHERE kompanija.PIB = donationcontract.PIB AND donationcontract.IdP = package.IdP AND donationcontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH) AND donationcontract.DueDate > now()\n"
                    + "      \n"
                    + "UNION \n"
                    + "\n"
                    + "SELECT kompanija.Name,package.PackName,moneycontract.DueDate FROM kompanija,moneycontract,package\n"
                    + "WHERE kompanija.PIB = moneycontract.PIB AND moneycontract.IdP = package.IdP  AND moneycontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH) AND moneycontract.DueDate> now()";

            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();

            for (Object object : data) {
                Map row = (Map) object;
                String cmpName = (String) row.get("Name");
                String pckName = (String) row.get("PackName");
                Date dueDate = (Date) row.get("DueDate");
                System.out.println("SVASTA: " + cmpName + " " + pckName + " " + dueDate);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String text = df.format(dueDate);
                System.out.println(text);

                model.add(new TimelineEvent(cmpName + "-" + pckName, dueDate));

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
    }

    public void onSelect(TimelineSelectEvent e) {
        System.out.println("CLICK");
        TimelineEvent timelineEvent = e.getTimelineEvent();
        
        Kompanija k = null;

        /*   FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:", timelineEvent.getData().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);*/
        System.out.println(timelineEvent.getData().toString());
        String s = timelineEvent.getData().toString();
        String arr[] = s.split("-", 2);
        String company = arr[0];
        
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            
            Criteria cr = session.createCriteria(Kompanija.class);
            cr.add(Restrictions.eq("name", company));
            List addList = cr.list();

            for (Iterator iterator = addList.iterator(); iterator.hasNext();) {
                Kompanija a = (Kompanija) iterator.next();
                k = a;
            }
            
            
            tx.commit();
        } catch (Exception x) {
            if (tx != null) {
                tx.rollback();
            }
            x.printStackTrace();
        } finally {
            session.close();
        }
        

        try {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("company", (Kompanija) k);
                FacesContext.getCurrentInstance().getExternalContext().redirect("dosie.xhtml");
        } catch (Exception x) {
            x.printStackTrace();
        }

    }

    public TimelineModel getModel() {
        return model;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isZoomable() {
        return zoomable;
    }

    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isStackEvents() {
        return stackEvents;
    }

    public void setStackEvents(boolean stackEvents) {
        this.stackEvents = stackEvents;
    }

    public String getEventStyle() {
        return eventStyle;
    }

    public void setEventStyle(String eventStyle) {
        this.eventStyle = eventStyle;
    }

    public boolean isAxisOnTop() {
        return axisOnTop;
    }

    public void setAxisOnTop(boolean axisOnTop) {
        this.axisOnTop = axisOnTop;
    }

    public boolean isShowCurrentTime() {
        return showCurrentTime;
    }

    public void setShowCurrentTime(boolean showCurrentTime) {
        this.showCurrentTime = showCurrentTime;
    }

    public boolean isShowNavigation() {
        return showNavigation;
    }

    public void setShowNavigation(boolean showNavigation) {
        this.showNavigation = showNavigation;
    }
}
