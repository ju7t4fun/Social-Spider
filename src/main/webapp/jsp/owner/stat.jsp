<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<%--for charts--%>
<%--line diagram lib--%>
<script src="${pageContext.request.contextPath}/js/highstock.js"></script>
<script src="${pageContext.request.contextPath}/js/exporting.js"></script>
<%--gender and pie diagram lib--%>
<script src="${pageContext.request.contextPath}/js/highcharts.js"></script>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="modal_stat" class="modal fade">

    <div class="modal-dialog">
        <div class="modal-content" style="width: 1000px; margin-left: -200px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="charts"/></h4>
            </div>

            <div>
                <div class="row" style="margin-left: 270px">
                    <div class="col-lg-3">
                        <input id="fromDate" class="form-control" type="date">
                    </div>
                    <div class="col-lg-3">
                        <input id="toDate" class="form-control" type="date">
                    </div>
                    <input class="btn btn-default" type="button" onclick="redrawChart()" value="Show">
                </div>
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
                    <li role="presentation">
                        <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a>
                    </li>
                    <li role="presentation">
                        <a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="home" style="height: 490px">
                        <div class="modal-body">
                            <div class="row">
                                <div id="line-diagram" style="height: 450px; min-width: 998px"></div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="profile" style="height: 490px">
                        <div class="modal-body">
                            <div class="row">
                                <div id="gender-diagram"
                                     style="min-width: 700px; max-width: 700px; height: 450px; margin: auto 150px"></div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="messages" style="height: 490px">
                        <div class="modal-body">
                            <div class="row">
                                <div id="pie-diagram"
                                     style="min-width: 480px; height: 400px; max-width: 480px;"></div>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div id="city-diagram"
                                     style="min-width: 480px; height: 400px; max-width: 480px; position: relative;
                                     left: 480px;  top: -426px"></div>
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="settings">...</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    function drawLineDiagram(data) {
        $('#line-diagram').highcharts('StockChart', {
            rangeSelector: {
                inputEnabled: false,
                buttons: [{
                    type: 'week',
                    count: 1,
                    text: 'week'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                }, {
                    type: 'month',
                    count: 3,
                    text: '3m'
                }, {
                    type: 'month',
                    count: 6,
                    text: '6m'
                }, {
                    type: 'year',
                    count: 1,
                    text: '1y'
                }, {
                    type: 'all',
                    text: 'All'
                }],
                selected: 1
            },

            series: [{
                name: 'Views',
                data: data.views
            }, {
                name: 'Visitors',
                data: data.visitors
            }]
        });
    }

    function drawGenderDiagram(data) {
        var categories = ['12-18', '18-21', '21-24', '24-27',
            '27-30', '30-35', '35-45', ' 45-100'];
        $('#gender-diagram').highcharts({
            chart: {
                type: 'bar'
            },

            xAxis: [{
                categories: categories,
                reversed: false,
                labels: {
                    step: 1
                }
            }, { // mirror axis on right side
                opposite: true,
                reversed: false,
                categories: categories,
                linkedTo: 0,
                labels: {
                    step: 1
                }
            }],
            yAxis: {
                title: {
                    text: null
                },
                labels: {
                    formatter: function () {
                        return Math.abs(this.value) + '%';
                    }
                }
            },

            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },

            series: [{
                name: 'Male',
                data: data.male
            }, {
                name: 'Female',
                data: data.female
            }]
        });
    }

    function drawCountryDiagram(data) {
        $('#pie-diagram').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Country'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Brands",
                colorByPoint: true,
                data: data
            }]
        });
    }

    function drawCityDiagram(data) {
        $('#city-diagram').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'City'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: "Brands",
                colorByPoint: true,
                data: data
            }]
        });
    }

</script>