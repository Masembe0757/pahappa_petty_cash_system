package org.pahappa.pettycashapp.systems.petty_cash_app.beans;


import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RequisitionService;
import org.primefaces.model.charts.pie.PieChartOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.donut.*;
import org.springframework.web.context.annotation.SessionScope;


import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component
@SessionScope
public class ChartBean implements Serializable {
    private static final long serialVersionUID = 3L;

    private PieChartModel pieModel;
    private BarChartModel barModel;
    private DonutChartModel donutModel;

    @Autowired
    RequisitionService requisitionService;

    @Autowired
    private BudgetLineService budgetLineService;

    @PostConstruct
    public void init() {
        createBarModel();
//        createDonutModel();
        createPieModel();
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(requisitionService.countApprovedRequisitions());
        values.add(requisitionService.countFulfilledRequisitions());
        values.add(requisitionService.countRejectedRequisitions());
        values.add(requisitionService.countStagedRequisitions());
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(75, 192, 192)");  // Teal
        bgColors.add("rgb(255, 206, 86)");  // Yellow
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Approved");
        labels.add("Paid Out");
        labels.add("Rejected");
        labels.add("Staged");
        data.setLabels(labels);
        pieModel.setData(data);
    }

    public void createBarModel(){
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Expenditure Per Budget Line");

        List<Number> values = new ArrayList<>();
        values = budgetLineService.getExpenditurePerBudgetLine();
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels = budgetLineService.getBLnames();
        data.setLabels(labels);

        //Data
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setStepSize(1);
        linearAxes.setTicks(ticks);
        linearAxes.setBeginAtZero(true);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Users With and Without Dependants");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

//    private void createDonutModel() {
//        donutModel = new DonutChartModel();
//        ChartData data = new ChartData();
//
//        DonutChartModel dataSet = new DonutChartModel();
//        List<Number> values = new ArrayList<>();
//        values.add(femaleDependantsCount());
//        values.add(maleDependantsCount());
//        dataSet.setData((ChartData) values);
//
//        List<String> bgColors = new ArrayList<>();
//        bgColors.add("rgb(255, 99, 132)");
//        bgColors.add("rgb(54, 162, 235)");
//        dataSet.(bgColors);
//
//        data.addChartDataSet(dataSet);
//        List<String> labels = new ArrayList<>();
//        labels.add("Female");
//        labels.add("Male");
//        data.setLabels(labels);
//
//        donutModel.setData(data);
//    }


}
