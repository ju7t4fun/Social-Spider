<script src="${pageContext.request.contextPath}/assets/chart-master/Chart.js"></script>
<%@ taglib prefix="l" uri="http://lab.epam.com/spider/locale" %>
<script src="${pageContext.request.contextPath}/js/chartjs-custom.js"></script>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="modal_stat" class="modal fade">

    <div class="modal-dialog">
        <div class="modal-content" style="width: 1000px; margin-left: -200px;">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
                <h4 class="modal-title"><l:resource key="charts" /></h4>
            </div>

            <div class="modal-body">
                <div class="row" style="margin-left: 110px;">
                    <!-- chart morris start -->

                    <div class="col-lg-12">
                        <section class="panel">
                            <%--<header class="panel-heading" style="position: fixed; left: 300px; top:300px;">--%>
                                <%--<Char>General Chart</Char>--%>
                            <%--</header>--%>
                            <div style="position: fixed; top: 100px; left: 30px;">
                                <label for="startDate" style="position: fixed; left: 20px; top: 105px"><l:resource key="charts.from" /></label>
                                <l:resource key="charts.startdate"><input id="startDate" name="date" type="date" max="2015-06-30" value="2015-06-30"
                                       placeholder="" class="form-control"
                                       style="width: 200px;  position: fixed; left: 60px; top: 100px;"></l:resource>
                                <label for="endDate" style="position:
                                       fixed; left: 280px; top: 105px"><l:resource key="charts.to" /></label>
                                <l:resource key="charts.enddate"><input id="endDate" name="date" type="date" max="2015-06-30" value="2015-06-30"
                                       placeholder="" class="form-control" style="width: 200px;  position:
                                       fixed; left: 305px; top: 100px;"></l:resource>

                                <a onclick="redrawChart()" style="width: 60px;  position:
                                       fixed; left: 530px; top: 100px;" class="btn btn-default"><l:resource key="charts.update" /></a>
                            </div>
                            <div class="panel-body" style="margin-top: 50px;">
                                <div class="tab-pane" id="chartjs">
                                    <div class="row">
                                        <!-- Line -->
                                        <div class="col-lg-6" style="margin-left: -150px;">
                                            <section class="panel">
                                                <header class="panel-heading" style="margin-left: 50px;">
                                                    <l:resource key="charts.uniquevisitsdaily" />: 10
                                                    <l:resource key="charts.uniquevisits" /> 30 <l:resource key="charts.days" />: 300
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="line" height="500" width="930"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <!-- Bar -->
                                    <div class="row">
                                        <div class="col-lg-6"  style="margin-left: -150px;">
                                            <section class="panel">
                                                <header class="panel-heading"  style="margin-left: 50px;">
                                                    <l:resource key="charts.age" />
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="bar" height="500" width="930"></canvas>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <!-- Pie -->
                                        <div class="col-lg-6">
                                            <section class="panel">
                                                <header class="panel-heading">
                                                    <l:resource key="charts.geografy" />
                                                </header>
                                                <div class="panel-body text-center">
                                                    <canvas id="pie" height="300" width="400"></canvas>
                                                </div>
                                            </section>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>