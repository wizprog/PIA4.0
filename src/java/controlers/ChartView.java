/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Kompanija;
import database.HibernateUtil;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class ChartView {

    private LineChartModel lineModel1;

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

    public void init() {
        createLineModels();
    }

    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Companies with expiring packages");
        //    lineModel1.setLegendPosition("e");
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Companies");

   /*     series1.set("2014-01-01", 51);
        series1.set("2014-01-06", 22);
        series1.set("2014-01-12", 65);
        series1.set("2014-01-18", 74);
        series1.set("2014-01-24", 24);
        series1.set("2014-01-30", 51);*/

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.Name,package.PackName,donationcontract.DueDate FROM kompanija,donationcontract,package \n"
                    + "WHERE kompanija.PIB = donationcontract.PIB AND donationcontract.IdP = package.IdP AND donationcontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH)\n"
                    + "\n"
                    + "UNION \n"
                    + "\n"
                    + "SELECT kompanija.Name,package.PackName,moneycontract.DueDate FROM kompanija,moneycontract,package \n"
                    + "WHERE kompanija.PIB = moneycontract.PIB AND moneycontract.IdP = package.IdP  AND moneycontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH)";

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
                
                series1.set(text, 25);
                
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

        model.addSeries(series1);
        model.getAxis(AxisType.Y).setLabel("Companies");
        model.getAxis(AxisType.Y).setMax(50);
        DateAxis axis = new DateAxis("Dates");
        axis.setTickFormat("%b %#d, %y");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 18);

        axis.setMax("2020-02-01");
        model.getAxes().put(AxisType.X, axis);
        return model;
    }

}
