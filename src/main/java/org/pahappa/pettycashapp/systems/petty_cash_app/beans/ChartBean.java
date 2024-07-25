package org.pahappa.pettycashapp.systems.petty_cash_app.beans;


import org.pahappa.pettycashapp.systems.petty_cash_app.services.BudgetLineService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.RequisitionService;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.UserService;

import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.primefaces.model.charts.pie.PieChartOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.donut.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Service
@SessionScope
public class ChartBean implements Serializable {
    private static final long serialVersionUID = 3L;

    private PieChartModel pieModel;
    private BarChartModel barModel;
    private DonutChartModel donutModel;
    private DonutChartModel donutModel2;


    @Autowired
    RequisitionService requisitionService;

    @Autowired
    private BudgetLineService budgetLineService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        createBarModel();
        createDonutModel();
        createPieModel();
        createDonutModel2();
    }

    public void updateCharts() {
        createBarModel();
        createDonutModel();
        createPieModel();
        createDonutModel2();
    }

    public DonutChartModel getDonutModel2() {
        return donutModel2;
    }

    public void setDonutModel2(DonutChartModel donutModel2) {
        this.donutModel2 = donutModel2;
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
        values.add(requisitionService.countRequisitionsWithRequests());
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
        labels.add("Requests");
        data.setLabels(labels);
        pieModel.setData(data);
        PieChartOptions options = new PieChartOptions();

        //Title

        Title title = new Title();
        title.setDisplay(true);
        title.setText("REQUISITION STATUS");
        title.setFontFamily("Arial");
        title.setFontSize(16);
        title.setFontColor("#000000");
        title.setPadding(10);
        pieModel.setOptions(options);
        options.setAnimateRotate(true);

        // Legend Configuration
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        legend.setLabels(new LegendLabel() {{
            setFontColor("#000000");
            setFontSize(12);
        }});
        // Tooltip Configuration
        Tooltip tooltip = new Tooltip();
        tooltip.setEnabled(true);
        tooltip.setBackgroundColor("rgba(0,0,0,0.7)");
        tooltip.setBodyFontColor("#ffffff");
        tooltip.setBodyFontFamily("Arial");
        tooltip.setBodyFontSize(12);
        tooltip.setTitleFontColor("#ffffff");
        tooltip.setTitleFontFamily("Arial");
        tooltip.setTitleFontSize(14);
        tooltip.setCaretSize(5);
        tooltip.setCornerRadius(3);


        options.setTitle(title);
        options.setTooltip(tooltip);
        options.setLegend(legend);
        pieModel.setExtender("pieChartExtender");

        pieModel.setOptions(options);
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Expenditure Per Budget Line");

        List<Number> values = new ArrayList<>();
        values = budgetLineService.getExpenditurePerBudgetLine();
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        bgColor.add("#7a5195");  // Dark Cyan
        bgColor.add("#f95d6a");  // Goldenrod
        bgColor.add("#ff7c43");  // Dark Orange
        bgColor.add("#2a9d8f");  // Medium Sea Green
        bgColor.add("#e9c46a");  // Coral
        bgColor.add("#00aaff");
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
        barModel.setExtender("barChartExtender");

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setBeginAtZero(true);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("EXPENDITURE PER BUDGET LINE");
        title.setFontFamily("Arial");
        title.setFontSize(16);
        title.setFontColor("#000000");
        title.setPadding(10);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(12);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

    private void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        // Create and configure a dataset for the donut chart
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(requisitionService.countAllRequisitions());
        values.add(budgetLineService.countAllBL());
        values.add(userService.countAllUsers());
        dataSet.setData(values);

        // Set the background colors for each section of the donut
        List<String> backgroundColors = new ArrayList<>();
        backgroundColors.add("#7a5195");  // Dark Cyan
        backgroundColors.add("#f95d6a");  // Goldenrod
        backgroundColors.add("#ff7c43");  // Dark Orange
        backgroundColors.add("#2a9d8f");  // Medium Sea Green
        backgroundColors.add("#e9c46a");  // Coral
        backgroundColors.add("#00aaff");
        dataSet.setBackgroundColor(backgroundColors);

        // Optionally set border colors for each section
        List<String> borderColors = new ArrayList<>();
        borderColors.add("rgba(255, 99, 132, 1)"); // Example border color for Requisitions
        borderColors.add("rgba(54, 162, 235, 1)"); // Example border color for Budget Lines
        borderColors.add("rgba(75, 192, 192, 1)"); // Example border color for Users
        dataSet.setBorderColor(borderColors);


        // Add the dataset to the chart data
        data.addChartDataSet(dataSet);

        // Set labels for each section
        List<String> labels = new ArrayList<>();
        labels.add("Requisitions");
        labels.add("Budget Lines");
        labels.add("Users");
        data.setLabels(labels);

        // Set the chart data to the donut model
        donutModel.setData(data);

        // Optional: Set additional options for the donut chart if required
        DonutChartOptions options = new DonutChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setFontFamily("Arial");
        title.setFontSize(16);
        title.setFontColor("#000000");
        title.setPadding(10);
        title.setText("USERS, BUDGETLINES AND REQUISITIONS");
        options.setTitle(title);
        options.setCutout(75);
        donutModel.setOptions(options);
    }

    private void createDonutModel2() {
        donutModel2 = new DonutChartModel();
        ChartData data = new ChartData();

        // Create and configure a dataset for the donut chart
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(requisitionService.countAllRejectedRequisitions());
        values.add(requisitionService.countAllApprovedRequisitions());
        dataSet.setData(values);

        // Set the background colors for each section of the donut
        List<String> backgroundColors = new ArrayList<>();
        // backgroundColors.add("#7a5195");  // Dark Cyan
        backgroundColors.add("#f95d6a");  // Goldenrod
        backgroundColors.add("#e9c46a");  // Coral
        dataSet.setBackgroundColor(backgroundColors);

        // Optionally set border colors for each section
        List<String> borderColors = new ArrayList<>();
        borderColors.add("rgba(255, 99, 132, 1)"); // Example border color for Requisitions
        borderColors.add("rgba(54, 162, 235, 1)"); // Example border color for Budget Lines
        borderColors.add("rgba(75, 192, 192, 1)"); // Example border color for Users
        dataSet.setBorderColor(borderColors);

        // Add the dataset to the chart data
        data.addChartDataSet(dataSet);

        // Set labels for each section
        List<String> labels = new ArrayList<>();
        labels.add("Rejected Requisitions");
        labels.add("Approved Requisitions");
        data.setLabels(labels);

        // Set the chart data to the donut model
        donutModel2.setData(data);

        // Optional: Set additional options for the donut chart if required
        DonutChartOptions options = new DonutChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setFontFamily("Arial");
        title.setFontSize(16);
        title.setFontColor("#000000");
        title.setPadding(10);
        title.setText("APPROVED REQUISITIONS VS REJECTED REQUISITIONS");
        options.setTitle(title);
        options.setCutout(75);
        donutModel2.setOptions(options);
    }


}
