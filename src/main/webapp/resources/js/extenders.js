<!-- EXTENDERS-->
function barChartExtender() {
    var cfg = this.cfg.options || {};

    // Ensure plugins object is present
    if (!cfg.plugins) {
        cfg.plugins = {};
    }

    // Configure data labels
    cfg.plugins.datalabels = {
        display: true,
        color: 'black',
        align: 'end',
        anchor: 'end',
        formatter: function(value, context) {
            return context.chart.data.labels[context.dataIndex] + ': ' + value;
        }
    };

    // Configure legend
    cfg.plugins.legend = cfg.plugins.legend || {};
    cfg.plugins.legend.display = true;
    cfg.plugins.legend.position = 'top';
    cfg.plugins.legend.labels = {
        color: 'black', // Updated from fontColor to color
        font: 'Ariel'
    };

    // Configure tooltips
    cfg.plugins.tooltip = cfg.plugins.tooltip || {};
    cfg.plugins.tooltip.enabled = true;
    cfg.plugins.tooltip.callbacks = {
        label: function(tooltipItem, data) {
            var label = data.labels[tooltipItem.dataIndex] || '';
            if (label) {
                label += ': ';
            }
            label += Math.round(tooltipItem.raw * 100) / 100;
            return label;
        }
    };

    // Configure layout padding
    cfg.layout = cfg.layout || {};
    cfg.layout.padding = {
        left: 10,
        right: 10,
        top: 10,
        bottom: 10
    };

    // Configure bar elements
    cfg.elements = cfg.elements || {};
    cfg.elements.bar = {
        borderWidth: 1,
        borderColor: '#ccc',
        backgroundColor: 'rgba(54, 162, 235, 0.2)'
    };

    // Configure scales
    cfg.scales = cfg.scales || {};
    cfg.scales.y = {
        beginAtZero: true,
        ticks: {
            stepSize: 10 // Adjust stepSize to avoid generating too many ticks
        }
    };

    // Configure animation
    cfg.animation = cfg.animation || {};
    cfg.animation.animateScale = true;
    cfg.animation.animateRotate = true;

    // Configure responsiveness and aspect ratio
    cfg.responsive = true;
    cfg.maintainAspectRatio = false;

    // Assign the configuration back
    this.cfg.options = cfg;
}


// PIE CHART EXTENDER
function pieChartExtender() {
    if (!this.cfg.options) {
        this.cfg.options = {};
    }
    if (!this.cfg.options.plugins) {
        this.cfg.options.plugins = {};
    }
    if (!this.cfg.options.plugins.datalabels) {
        this.cfg.options.plugins.datalabels = {};
    }
    if (!this.cfg.options.plugins.legend) {
        this.cfg.options.plugins.legend = {};
    }
    if (!this.cfg.options.plugins.tooltip) {
        this.cfg.options.plugins.tooltip = {};
    }
    if (!this.cfg.options.elements) {
        this.cfg.options.elements = {};
    }
    if (!this.cfg.options.elements.arc) {
        this.cfg.options.elements.arc = {};
    }
    if (!this.cfg.options.layout) {
        this.cfg.options.layout = {};
    }
    if (!this.cfg.options.animation) {
        this.cfg.options.animation = {};
    }

    // Data labels configuration
    this.cfg.options.plugins.datalabels = {
        display: true,
        color: 'black',
        formatter: function(value, context) {
            return context.chart.data.labels[context.dataIndex] + ': ' + value;
        }
    };

    // Legend configuration
    this.cfg.options.plugins.legend = {
        display: true,
        position: 'top',
        labels: {
            fontColor: 'black'
        }
    };

    // Tooltip configuration
    this.cfg.options.plugins.tooltip = {
        enabled: true,
        callbacks: {
            label: function(tooltipItem, data) {
                var label = data.labels[tooltipItem.dataIndex] || '';
                if (label) {
                    label += ': ';
                }
                label += Math.round(tooltipItem.raw * 100) / 100;
                return label;
            }
        }
    };

    // Layout configuration
    this.cfg.options.layout.padding = {
        left: 10,
        right: 10,
        top: 10,
        bottom: 10
    };

    // Elements configuration
    this.cfg.options.elements.arc.borderWidth = 2; // Border width of each slice
    this.cfg.options.elements.arc.borderColor = '#fff';
    this.cfg.options.elements.arc.radius = 0.5;

    // Animation configuration
    this.cfg.options.animation.animateScale = true;
    this.cfg.options.animation.animateRotate = true;

    // Responsive configuration
    this.cfg.options.responsive = true;
    this.cfg.options.maintainAspectRatio = false;
}


